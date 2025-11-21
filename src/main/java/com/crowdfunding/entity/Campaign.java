package com.crowdfunding.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campaigns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(name = "goal_amount", nullable = false)
    private Double goalAmount;
    
    @Column(name = "current_amount", nullable = false)
    private Double currentAmount = 0.0;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status = CampaignStatus.ACTIVE;
    
    @Column(name = "deadline")
    private LocalDateTime deadline;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Donation> donations = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum CampaignCategory {
        MEDICAL,      // Медицина
        EDUCATION,    // Образование
        EMERGENCY,    // Срочная помощь
        CREATIVE,     // Творчество
        CHARITY,      // Благотворительность
        OTHER         // Другое
    }
    
    public enum CampaignStatus {
        ACTIVE,       // Активная
        COMPLETED,    // Завершена (цель достигнута)
        CANCELLED     // Отменена
    }
    
    // Вспомогательный метод для расчета процента
    public double getProgressPercentage() {
        if (goalAmount == null || goalAmount == 0) {
            return 0;
        }
        return (currentAmount / goalAmount) * 100;
    }
}
