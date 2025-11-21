package com.crowdfunding.dto;

import com.crowdfunding.entity.Campaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignUpdateRequest {

    private String title;
    private String description;
    private Double goalAmount;
    private String imageUrl;
    private Campaign.CampaignCategory category;
    private Campaign.CampaignStatus status;
    private LocalDateTime deadline;
}
