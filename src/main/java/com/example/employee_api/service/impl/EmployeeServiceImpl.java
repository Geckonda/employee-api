package com.example.employee_api.service.impl;

import com.example.employee_api.dto.employee.EmployeeRequest;
import com.example.employee_api.dto.employee.EmployeeResponse;
import com.example.employee_api.dto.filter.EmployeeFilter;
import com.example.employee_api.exception.NotFoundException;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.example.employee_api.repository.spec.EmployeeSpecification;
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
    @Cacheable(value = "employee", key = "#id")
    public EmployeeResponse getById(UUID id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Работник с таким Id не найден: " + id)
                );
        return  EmployeeMapper.toResponse(employee);
    }

    @Override
    public Page<EmployeeResponse> getAll(EmployeeFilter filter, Pageable pageable) {
       Specification<Employee> spec =
            Specification
                    .allOf(EmployeeSpecification.departmentEquals(filter.department()), 
                            EmployeeSpecification.statusEquals(filter.status()),
                            EmployeeSpecification.salaryGreaterThan(filter.minSalary()),
                            EmployeeSpecification.salaryLessThan(filter.maxSalary()));
        return employeeRepository
                .findAll(spec, pageable)
                .map(EmployeeMapper::toResponse);
    }

    @Override
    @CacheEvict(value = "employee", key = "#id")
    public EmployeeResponse update(UUID id, EmployeeRequest request) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Работник с таким Id не найден: " + id)
                );

        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        employee.setSalary(request.salary());

        employeeRepository.save(employee);

        return EmployeeMapper.toResponse(employee);
    }

    @Override
    @CacheEvict(value = "employee", key = "#id")
    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }
}
