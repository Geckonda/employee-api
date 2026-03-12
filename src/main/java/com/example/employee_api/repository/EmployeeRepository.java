package com.example.employee_api.repository;

import com.example.employee_api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository
    extends JpaRepository<Employee, UUID> {
}
