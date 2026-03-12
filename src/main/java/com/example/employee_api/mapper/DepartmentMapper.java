package com.example.employee_api.mapper;

import com.example.employee_api.dto.department.DepartmentDTO;
import com.example.employee_api.model.Department;

public class DepartmentMapper {
    public static DepartmentDTO toDTO(Department department) {

        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getDescription()
        );
    }
}
