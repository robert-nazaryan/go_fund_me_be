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
public class CampaignCreateRequest {

    @NotBlank(message = "Название обязательно")
    private String title;

    @NotBlank(message = "Описание обязательно")
    private String description;

    @NotNull(message = "Целевая сумма обязательна")
    @Positive(message = "Сумма должна быть положительной")
    private Double goalAmount;

    private String imageUrl;

    @NotNull(message = "Категория обязательна")
    private Campaign.CampaignCategory category;

    private LocalDateTime deadline;
}
