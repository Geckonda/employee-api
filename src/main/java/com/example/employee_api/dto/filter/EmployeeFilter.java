package com.example.employee_api.dto.filter;

import java.math.BigDecimal;

public record EmployeeFilter(
    String department,
    String status,
    BigDecimal minSalary,
    BigDecimal maxSalary
) {}
