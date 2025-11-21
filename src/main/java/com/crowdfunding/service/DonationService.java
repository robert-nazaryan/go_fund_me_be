package com.crowdfunding.service;

import com.crowdfunding.dto.*;
import com.crowdfunding.entity.Campaign;
import com.crowdfunding.entity.Donation;
import com.crowdfunding.entity.User;
import com.crowdfunding.exception.ResourceNotFoundException;
import com.crowdfunding.repository.CampaignRepository;
import com.crowdfunding.repository.DonationRepository;
import com.crowdfunding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationService {
    
    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public DonationDto createDonation(DonationCreateRequest request, String donorEmail) {
        User donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        
        Campaign campaign = campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new ResourceNotFoundException("Кампания не найдена"));
        
        // Проверка статуса кампании
        if (campaign.getStatus() != Campaign.CampaignStatus.ACTIVE) {
            throw new IllegalArgumentException("Кампания не активна");
        }
        
        // Проверка виртуального баланса
        if (donor.getVirtualBalance() < request.getAmount()) {
            throw new IllegalArgumentException("Недостаточно средств на виртуальном балансе");
        }
        
        // Создание доната
        Donation donation = new Donation();
        donation.setCampaign(campaign);
        donation.setDonor(donor);
        donation.setAmount(request.getAmount());
        donation.setMessage(request.getMessage());
        donation.setIsAnonymous(request.getIsAnonymous());
        
        donation = donationRepository.save(donation);
        
        // Обновление суммы кампании
        campaign.setCurrentAmount(campaign.getCurrentAmount() + request.getAmount());
        
        // Проверка достижения цели
        if (campaign.getCurrentAmount() >= campaign.getGoalAmount()) {
            campaign.setStatus(Campaign.CampaignStatus.COMPLETED);
        }
        
        campaignRepository.save(campaign);
        
        // Обновление баланса донора
        donor.setVirtualBalance(donor.getVirtualBalance() - request.getAmount());
        userRepository.save(donor);
        
        return mapToDto(donation);
    }
    
    public List<DonationDto> getDonationsByCampaign(Long campaignId) {
        return donationRepository.findByCampaignId(campaignId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    public List<DonationDto> getUserDonations(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        
        return donationRepository.findByDonorId(user.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    private DonationDto mapToDto(Donation donation) {
        DonationDto dto = new DonationDto();
        dto.setId(donation.getId());
        dto.setCampaignId(donation.getCampaign().getId());
        dto.setCampaignTitle(donation.getCampaign().getTitle());
        dto.setDonorId(donation.getDonor().getId());
        dto.setDonorName(donation.getIsAnonymous() ? "Аноним" : donation.getDonor().getFullName());
        dto.setAmount(donation.getAmount());
        dto.setMessage(donation.getMessage());
        dto.setIsAnonymous(donation.getIsAnonymous());
        dto.setCreatedAt(donation.getCreatedAt());
        return dto;
    }
}
