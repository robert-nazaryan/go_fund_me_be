package com.crowdfunding.dto;

import com.crowdfunding.entity.Campaign;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    private String imageUrl;
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
