package com.example.employee_api.dto.position;

import java.util.UUID;

public record PositionDTO(

        UUID id,
        String name,
        String description
) {}
