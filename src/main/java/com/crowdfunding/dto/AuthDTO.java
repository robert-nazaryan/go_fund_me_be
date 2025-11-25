package com.crowdfunding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class AuthDTO {
    
    @Data
    public static class RegisterRequest {
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;
        
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;
        
        @NotBlank(message = "Full name is required")
        @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
        private String fullName;
    }
    
    @Data
    public static class LoginRequest {
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;
        
        @NotBlank(message = "Password is required")
        private String password;
    }
    
    @Data
    public static class AuthResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String email;
        private String fullName;
        private Double virtualBalance;
        
        public AuthResponse(String token, Long id, String email, String fullName, Double virtualBalance) {
            this.token = token;
            this.id = id;
            this.email = email;
            this.fullName = fullName;
            this.virtualBalance = virtualBalance;
        }
    }
}
