package com.crowdfunding.controller;

import com.crowdfunding.dto.*;
import com.crowdfunding.entity.Campaign;
import com.crowdfunding.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    
    private final CampaignService campaignService;
    
    @PostMapping
    public ResponseEntity<CampaignDto> createCampaign(
            @Valid @RequestBody CampaignCreateRequest request,
            Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(campaignService.createCampaign(request, userEmail));
    }
    
    @GetMapping
    public ResponseEntity<List<CampaignDto>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CampaignDto> getCampaignById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CampaignDto>> getCampaignsByStatus(
            @PathVariable Campaign.CampaignStatus status) {
        return ResponseEntity.ok(campaignService.getCampaignsByStatus(status));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CampaignDto>> getCampaignsByCategory(
            @PathVariable Campaign.CampaignCategory category) {
        return ResponseEntity.ok(campaignService.getCampaignsByCategory(category));
    }
    
    @GetMapping("/my")
    public ResponseEntity<List<CampaignDto>> getUserCampaigns(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(campaignService.getUserCampaigns(userEmail));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CampaignDto> updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody CampaignUpdateRequest request,
            Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(campaignService.updateCampaign(id, request, userEmail));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(
            @PathVariable Long id,
            Authentication authentication) {
        String userEmail = authentication.getName();
        campaignService.deleteCampaign(id, userEmail);
        return ResponseEntity.noContent().build();
    }
}
