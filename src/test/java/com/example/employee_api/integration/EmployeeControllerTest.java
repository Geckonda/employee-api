package com.example.employee_api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.employee_api.dto.employee.EmployeeRequest;
import com.example.employee_api.dto.employee.EmployeeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class EmployeeControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void createEmployee() throws Exception {

        String body = """
        {
          "firstName": "Alex",
          "lastName": "Ivanov",
          "email": "alex@test.com",
          "salary": 3000
        }
        """;

        mockMvc.perform(
                post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("Alex"));
    }
    // ---------------- GET BY ID ----------------

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getById_shouldReturnEmployee() throws Exception {

        EmployeeRequest request = new EmployeeRequest(
                "John",
                "Doe",
                "john@test.com",
                4000.0
        );

        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        EmployeeResponse created =
                objectMapper.readValue(response, EmployeeResponse.class);

        mockMvc.perform(get("/employees/" + created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.id().toString()));
    }

    // ---------------- UPDATE ----------------

    @Test
    @WithMockUser(authorities = "ADMIN")
    void update_shouldModifyEmployee() throws Exception {

        EmployeeRequest request = new EmployeeRequest(
                "Old",
                "Name",
                "old@test.com",
                2000.0
        );

        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        EmployeeResponse created =
                objectMapper.readValue(response, EmployeeResponse.class);

        EmployeeRequest updateRequest = new EmployeeRequest(
                "New",
                "Name",
                "new@test.com",
                5000.0
        );

        mockMvc.perform(put("/employees/" + created.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("New"));
    }

    // ---------------- DELETE ----------------

    @Test
    @WithMockUser(authorities = "ADMIN")
    void delete_shouldRemoveEmployee() throws Exception {

        EmployeeRequest request = new EmployeeRequest(
                "Delete",
                "Me",
                "delete@test.com",
                1000.0
        );

        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        EmployeeResponse created =
                objectMapper.readValue(response, EmployeeResponse.class);

        mockMvc.perform(delete("/employees/" + created.id()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/" + created.id()))
                .andExpect(status().is4xxClientError());
    }
}
