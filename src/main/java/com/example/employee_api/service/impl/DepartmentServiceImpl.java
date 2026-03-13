package com.example.employee_api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.employee_api.dto.department.DepartmentDTO;
import com.example.employee_api.mapper.DepartmentMapper;
import com.example.employee_api.model.Department;
import com.example.employee_api.repository.DepartmentRepository;
import com.example.employee_api.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
    implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        System.out.println(departmentDTO);
        
        Department department = Department.builder()
                .id(UUID.randomUUID())
                .name(departmentDTO.name())
                .description(departmentDTO.description())
                .build();

        departmentRepository.save(department);
        return DepartmentMapper.toDTO(department);
    }

    @Override
    public void delete(UUID id) {
            departmentRepository.deleteById(id);        
    }

    @Override
    public List<DepartmentDTO> getAll() {
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentMapper::toDTO)
                .toList();
    }

    @Override
    public DepartmentDTO getById(UUID id) {
        Department department = departmentRepository
                            .findById(id)
                            .orElseThrow();
            
        return DepartmentMapper.toDTO(department);
    }

    @Override
    public DepartmentDTO update(UUID id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository
                            .findById(id)
                            .orElseThrow();
        department.setName(departmentDTO.name());
        department.setDescription(departmentDTO.description());
        departmentRepository.save(department);
        
        return DepartmentMapper.toDTO(department);
    }
    
}
