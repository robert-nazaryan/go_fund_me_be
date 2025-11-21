package com.crowdfunding.controller;

import com.crowdfunding.dto.*;
import com.crowdfunding.service.DonationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {
    
    private final DonationService donationService;
    
    @PostMapping
    public ResponseEntity<DonationDto> createDonation(
            @Valid @RequestBody DonationCreateRequest request,
            Authentication authentication) {
        String donorEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(donationService.createDonation(request, donorEmail));
    }
    
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<DonationDto>> getDonationsByCampaign(@PathVariable Long campaignId) {
        return ResponseEntity.ok(donationService.getDonationsByCampaign(campaignId));
    }
    
    @GetMapping("/my")
    public ResponseEntity<List<DonationDto>> getUserDonations(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(donationService.getUserDonations(userEmail));
    }
}
