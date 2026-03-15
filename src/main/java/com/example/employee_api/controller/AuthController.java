package com.example.employee_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_api.dto.auth.AuthRequest;
import com.example.employee_api.dto.auth.AuthResponse;
import com.example.employee_api.security.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {    
    private final AuthService authService;
    @PostMapping("/login")
    public AuthResponse postMethodName(@RequestBody AuthRequest request) {
        
        return authService.login(request)   ;
    }
    
}
