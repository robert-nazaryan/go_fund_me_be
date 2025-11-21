package com.crowdfunding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationCreateRequest {

    @NotNull(message = "ID кампании обязателен")
    private Long campaignId;

    @NotNull(message = "Сумма обязательна")
    @Positive(message = "Сумма должна быть положительной")
    private Double amount;

    private String message;

    private Boolean isAnonymous = false;
}
