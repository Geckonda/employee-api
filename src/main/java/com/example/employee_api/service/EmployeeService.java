package com.example.employee_api.service;

import com.example.employee_api.dto.employee.EmployeeRequest;
import com.example.employee_api.dto.employee.EmployeeResponse;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse getById(UUID id);

    List<EmployeeResponse> getAll();

    EmployeeResponse update(UUID id, EmployeeRequest request);

    void delete(UUID id);
}
