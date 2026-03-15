package com.example.employee_api.dto.auth;

public record AuthRequest(
    String email,
    String password
){}
