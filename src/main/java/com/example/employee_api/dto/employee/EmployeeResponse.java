package com.example.employee_api.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Double salary,
        String department,
        String position,
        String status
) {}
