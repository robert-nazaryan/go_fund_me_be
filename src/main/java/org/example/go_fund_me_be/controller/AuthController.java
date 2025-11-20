package org.example.go_fund_me_be.controller;

import lombok.RequiredArgsConstructor;
import org.example.go_fund_me_be.dto.AuthResponse;
import org.example.go_fund_me_be.dto.LoginRequest;
import org.example.go_fund_me_be.dto.RegisterRequest;
import org.example.go_fund_me_be.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}