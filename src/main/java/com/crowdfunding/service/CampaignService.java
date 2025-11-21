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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {
    
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public CampaignDto createCampaign(CampaignCreateRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        
        Campaign campaign = new Campaign();
        campaign.setTitle(request.getTitle());
        campaign.setDescription(request.getDescription());
        campaign.setGoalAmount(request.getGoalAmount());
        campaign.setCurrentAmount(0.0);
        campaign.setImageUrl(request.getImageUrl());
        campaign.setCategory(request.getCategory());
        campaign.setStatus(Campaign.CampaignStatus.ACTIVE);
        campaign.setDeadline(request.getDeadline());
        campaign.setUser(user);
        
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
    public CampaignDto updateCampaign(Long id, CampaignUpdateRequest request, String userEmail) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Кампания не найдена"));
        
        // Проверка, что пользователь - владелец кампании
        if (!campaign.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Вы не можете редактировать эту кампанию");
        }
        
        if (request.getTitle() != null) {
            campaign.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            campaign.setDescription(request.getDescription());
        }
        if (request.getGoalAmount() != null) {
            campaign.setGoalAmount(request.getGoalAmount());
        }
        if (request.getImageUrl() != null) {
            campaign.setImageUrl(request.getImageUrl());
        }
        if (request.getCategory() != null) {
            campaign.setCategory(request.getCategory());
        }
        if (request.getStatus() != null) {
            campaign.setStatus(request.getStatus());
        }
        if (request.getDeadline() != null) {
            campaign.setDeadline(request.getDeadline());
        }
        
        campaign = campaignRepository.save(campaign);
        return mapToDto(campaign);
    }
    
    @Transactional
    public void deleteCampaign(Long id, String userEmail) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Кампания не найдена"));
        
        // Проверка, что пользователь - владелец кампании
        if (!campaign.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Вы не можете удалить эту кампанию");
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
        dto.setImageUrl(campaign.getImageUrl());
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
