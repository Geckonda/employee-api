package com.example.employee_api.service;

import java.util.List;
import java.util.UUID;

import com.example.employee_api.dto.department.DepartmentDTO;

public interface DepartmentService {
    DepartmentDTO create(DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAll();
    DepartmentDTO getById(UUID id);
    DepartmentDTO update(UUID id, DepartmentDTO departmentDTO);
    void delete(UUID id);
}
