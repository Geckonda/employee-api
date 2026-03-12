package com.example.employee_api.repository;

import com.example.employee_api.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository
        extends JpaRepository<Department, UUID> {
}
