package com.example.employee_api.dto.employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record EmployeeRequest(
        @NotBlank(message = "Имя обязательно")
        String firstName,

        @NotBlank(message = "Фамилия обязательна")
        String lastName,

        @Email(message = "Неверный формат E-mail")
        @NotBlank
        String email,

        @Size(max = 20)
        String phone,

        @Positive(message = "Зарплата не может быть отрицательной")
        Double salary,

        UUID departmentId,

        UUID positionId
){}
