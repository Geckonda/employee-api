package com.example.employee_api.service.impl;

import com.example.employee_api.dto.employee.EmployeeRequest;
import com.example.employee_api.dto.employee.EmployeeResponse;
import com.example.employee_api.mapper.EmployeeMapper;
import com.example.employee_api.model.Department;
import com.example.employee_api.model.Employee;
import com.example.employee_api.model.EmployeeStatus;
import com.example.employee_api.model.Position;
import com.example.employee_api.repository.DepartmentRepository;
import com.example.employee_api.repository.EmployeeRepository;
import com.example.employee_api.repository.PositionRepository;
import com.example.employee_api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl  implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private  final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        Department department = null;
        Position position = null;

        if(request.departmentId() != null){
            department = departmentRepository
                    .findById(request.departmentId())
                    .orElseThrow();
        }
        if(request.positionId() != null){
            position = positionRepository
                    .findById(request.positionId())
                    .orElseThrow();
        }

        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .salary(request.salary())
                .department(department)
                .position(position)
                .status(EmployeeStatus.ACTIVE)
                .build();

        employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse getById(UUID id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow();
        return  EmployeeMapper.toResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return employeeRepository
                .findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    @Override
    public EmployeeResponse update(UUID id, EmployeeRequest request) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow();

        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        employee.setSalary(request.salary());

        employeeRepository.save(employee);

        return EmployeeMapper.toResponse(employee);
    }

    @Override
    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }
}
