package com.example.employee_api.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Событие, которое отправляется в Kafka когда создаётся новый сотрудник
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreatedEvent implements Serializable {

    @JsonProperty("employee_id")
    private UUID employeeId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("position")
    private String position;

    @JsonProperty("department_id")
    private UUID departmentId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("created_by")
    private String createdBy;
}
