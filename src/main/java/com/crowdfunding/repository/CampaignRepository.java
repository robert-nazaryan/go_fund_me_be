package com.crowdfunding.repository;

import com.crowdfunding.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByUserId(Long userId);
    List<Campaign> findByStatus(Campaign.CampaignStatus status);
    List<Campaign> findByCategory(Campaign.CampaignCategory category);
}
