package com.crowdfunding.controller;

import com.crowdfunding.dto.*;
import com.crowdfunding.entity.Campaign;
import com.crowdfunding.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CampaignDto> createCampaign(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("goalAmount") Double goalAmount,
            @RequestParam("category") Campaign.CampaignCategory category,
            @RequestParam(value = "deadline", required = false) String deadline,
            @RequestParam("coverImage") MultipartFile coverImage,
            @RequestParam(value = "galleryImages", required = false) MultipartFile[] galleryImages,
            @RequestParam(value = "document", required = false) MultipartFile document,
            Authentication authentication) {

        String userEmail = authentication.getName();

        CampaignCreateRequest request = new CampaignCreateRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setGoalAmount(goalAmount);
        request.setCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(campaignService.createCampaign(request, coverImage, galleryImages, document, userEmail));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CampaignDto> updateCampaign(
            @PathVariable Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "goalAmount", required = false) Double goalAmount,
            @RequestParam(value = "category", required = false) Campaign.CampaignCategory category,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestParam(value = "galleryImages", required = false) MultipartFile[] galleryImages,
            @RequestParam(value = "document", required = false) MultipartFile document,
            @RequestParam(value = "removeDocument", required = false) Boolean removeDocument,
            Authentication authentication) {

        String userEmail = authentication.getName();

        CampaignUpdateRequest request = new CampaignUpdateRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setGoalAmount(goalAmount);
        request.setCategory(category);

        return ResponseEntity.ok(
                campaignService.updateCampaign(id, request, coverImage, galleryImages, document, removeDocument, userEmail)
        );
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(
            @PathVariable Long id,
            Authentication authentication) {
        String userEmail = authentication.getName();
        campaignService.deleteCampaign(id, userEmail);
        return ResponseEntity.noContent().build();
    }
}