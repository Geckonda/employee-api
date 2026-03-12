package com.example.employee_api.mapper;

import com.example.employee_api.dto.position.PositionDTO;
import com.example.employee_api.model.Position;

public class PositionMapper {
    public static PositionDTO toDTO(Position position) {

        return new PositionDTO(
                position.getId(),
                position.getName(),
                position.getDescription()
        );
    }
}
