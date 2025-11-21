package com.crowdfunding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDto {
    private Long id;
    private Long campaignId;
    private String campaignTitle;
    private Long donorId;
    private String donorName;
    private Double amount;
    private String message;
    private Boolean isAnonymous;
    private LocalDateTime createdAt;
}
