package com.crowdfunding.service;

import com.crowdfunding.dto.*;
import com.crowdfunding.entity.Campaign;
import com.crowdfunding.entity.User;
import com.crowdfunding.exception.ResourceNotFoundException;
import com.crowdfunding.repository.CampaignRepository;
import com.crowdfunding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public CampaignDto createCampaign(CampaignCreateRequest request,
                                      MultipartFile coverImage,
                                      MultipartFile[] galleryImages,
                                      String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));


        Campaign campaign = new Campaign();
        campaign.setTitle(request.getTitle());
        campaign.setDescription(request.getDescription());
        campaign.setGoalAmount(request.getGoalAmount());
        campaign.setCurrentAmount(0.0);
        campaign.setCategory(request.getCategory());
        campaign.setStatus(Campaign.CampaignStatus.ACTIVE);
        campaign.setDeadline(request.getDeadline());
        campaign.setUser(user);

        // Save cover image
        String coverImagePath = fileStorageService.storeFile(coverImage, "covers");
        campaign.setCoverImage(coverImagePath);

        // Save gallery images
        if (galleryImages != null && galleryImages.length > 0) {
            List<String> galleryPaths = new ArrayList<>();
            for (MultipartFile file : galleryImages) {
                if (!file.isEmpty()) {
                    String path = fileStorageService.storeFile(file, "gallery");
                    galleryPaths.add(path);
                }
            }
            campaign.setGalleryImages(galleryPaths.toArray(new String[0]));
        }

        campaign = campaignRepository.save(campaign);
        return mapToDto(campaign);
    }

    public List<CampaignDto> getAllCampaigns() {
        return campaignRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CampaignDto> getCampaignsByStatus(Campaign.CampaignStatus status) {
        return campaignRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CampaignDto> getCampaignsByCategory(Campaign.CampaignCategory category) {
        return campaignRepository.findByCategory(category).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CampaignDto> getUserCampaigns(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        return campaignRepository.findByUserId(user.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CampaignDto getCampaignById(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Кампания не найдена"));
        return mapToDto(campaign);
    }

    @Transactional
    public void deleteCampaign(Long id, String userEmail) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Кампания не найдена"));

        if (!campaign.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Вы не можете удалить эту кампанию");
        }

        // Delete files
        fileStorageService.deleteFile(campaign.getCoverImage());
        if (campaign.getGalleryImages() != null) {
            for (String path : campaign.getGalleryImages()) {
                fileStorageService.deleteFile(path);
            }
        }

        campaignRepository.delete(campaign);
    }

    private CampaignDto mapToDto(Campaign campaign) {
        CampaignDto dto = new CampaignDto();
        dto.setId(campaign.getId());
        dto.setTitle(campaign.getTitle());
        dto.setDescription(campaign.getDescription());
        dto.setGoalAmount(campaign.getGoalAmount());
        dto.setCurrentAmount(campaign.getCurrentAmount());
        dto.setCoverImage(campaign.getCoverImage());
        dto.setGalleryImages(campaign.getGalleryImages());
        dto.setCategory(campaign.getCategory());
        dto.setStatus(campaign.getStatus());
        dto.setDeadline(campaign.getDeadline());
        dto.setCreatedAt(campaign.getCreatedAt());
        dto.setUpdatedAt(campaign.getUpdatedAt());
        dto.setUserId(campaign.getUser().getId());
        dto.setUserFullName(campaign.getUser().getFullName());
        dto.setDonationsCount(campaign.getDonations().size());
        dto.setProgressPercentage(campaign.getProgressPercentage());
        return dto;
    }
}