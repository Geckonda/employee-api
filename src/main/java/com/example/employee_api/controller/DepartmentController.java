package com.example.employee_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_api.dto.department.DepartmentDTO;
import com.example.employee_api.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentDTO create(
            @RequestBody DepartmentDTO departmentDTO)
    {
        System.err.println("\n\n\n\n\n" + departmentDTO + "\n\n\n\n\n");
        return departmentService.create(departmentDTO);
    }

    @GetMapping("/{id}")
    public DepartmentDTO getByid(
            @PathVariable UUID id){
        return departmentService.getById(id);
    }

    @GetMapping
    public List<DepartmentDTO> getAll(){
        return departmentService.getAll();
    }

    @PutMapping("/{id}")
    public DepartmentDTO update(
            @PathVariable UUID id,
            @RequestBody DepartmentDTO departmentDTO){
        return departmentService.update(id, departmentDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        departmentService.delete(id);
    }
}
