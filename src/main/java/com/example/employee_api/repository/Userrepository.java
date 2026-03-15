package com.example.employee_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employee_api.model.User;


public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
