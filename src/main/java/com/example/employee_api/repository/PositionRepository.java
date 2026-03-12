package com.example.employee_api.repository;

import com.example.employee_api.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionRepository
    extends JpaRepository<Position, UUID> {
}
