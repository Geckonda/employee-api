package com.example.employee_api.dto.department;

import java.util.UUID;

public record DepartmentDTO(

        UUID id,
        String name,
        String description
) {}
