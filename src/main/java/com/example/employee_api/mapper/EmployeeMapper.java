package com.example.employee_api.mapper;

import com.example.employee_api.dto.employee.EmployeeResponse;
import com.example.employee_api.model.Employee;

public class EmployeeMapper {
    public static EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getSalary(),
                employee.getDepartment() != null
                    ? employee.getDepartment().getName()
                        : null,
                employee.getPosition() != null
                    ? employee.getPosition().getName()
                        :null,
                employee.getStatus().name()
        );
    }
}
