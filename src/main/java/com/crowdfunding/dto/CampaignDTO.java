package com.crowdfunding.dto;

import com.crowdfunding.entity.Campaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDto {
    private Long id;
    private String title;
    private String description;
    private Double goalAmount;
    private Double currentAmount;
    private String coverImage;
    private String[] galleryImages;
    private Campaign.CampaignCategory category;
    private Campaign.CampaignStatus status;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userFullName;
    private Integer donationsCount;
    private Double progressPercentage;
}