package com.example.employee_api.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.example.employee_api.model.Employee;
import com.example.employee_api.model.EmployeeStatus;

public class EmployeeSpecification {
    public static Specification<Employee> departmentEquals(String department)
    {
        return (root, query, cb) -> 
            department == null ? null:
                cb.equal(root.get("department").get("name"), department);
    }

    public static Specification<Employee> statusEquals(String statusStr)
    {
        return (root, query, cb) ->
           {
            if( statusStr == null){
                return null;
            }
                try {
                    EmployeeStatus status = EmployeeStatus.valueOf(statusStr.toUpperCase());
                    return cb.equal(root.get("status"), status);
                } catch (Exception e) {
                    return cb.disjunction();
                }
           };
    }

    public static Specification<Employee> salaryGreaterThan(BigDecimal salary)
    {
        return (root, query, cb) ->
            salary == null ? null :
                cb.greaterThanOrEqualTo(root.get("salary"), salary);
    }

    public static Specification<Employee> salaryLessThan(BigDecimal salary)
    {
        return (root, query, cb) ->
            salary == null ? null :
                cb.lessThanOrEqualTo(root.get("salary"), salary);
    }
}
