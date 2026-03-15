package com.example.employee_api.controller;


import com.example.employee_api.dto.employee.EmployeeRequest;
import com.example.employee_api.dto.employee.EmployeeResponse;
import com.example.employee_api.dto.filter.EmployeeFilter;
import com.example.employee_api.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public EmployeeResponse create(
            @Valid @RequestBody EmployeeRequest request)
    {
        return employeeService.create(request);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getByid(
            @PathVariable UUID id){
        return employeeService.getById(id);
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<EmployeeResponse> getAll(EmployeeFilter filter, Pageable pageable){
        return employeeService.getAll(filter, pageable);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeRequest request){
        return employeeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        employeeService.delete(id);
    }
}
