package com.example.employee_api.service;

import com.example.employee_api.dto.employee.EmployeeRequest;
import com.example.employee_api.dto.employee.EmployeeResponse;
import com.example.employee_api.dto.filter.EmployeeFilter;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse getById(UUID id);

    Page<EmployeeResponse> getAll(EmployeeFilter filter ,Pageable pageable);

    EmployeeResponse update(UUID id, EmployeeRequest request);

    void delete(UUID id);
}
