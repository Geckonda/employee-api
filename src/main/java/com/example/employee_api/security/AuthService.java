package com.example.employee_api.security;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee_api.dto.auth.AuthRequest;
import com.example.employee_api.dto.auth.AuthResponse;
import com.example.employee_api.exception.NotFoundException;
import com.example.employee_api.model.Role;
import com.example.employee_api.model.User;
import com.example.employee_api.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initTestUser() {
        if (userRepository.findByEmail("admin@ya.ru").isEmpty()) {
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail("admin@ya.ru");
            // Хешируем пароль!
            user.setPassword(passwordEncoder.encode("212121"));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            System.out.println("Тестовый пользователь создан!");
        }
         if (userRepository.findByEmail("user@ya.ru").isEmpty()) {
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail("user@ya.ru");
            // Хешируем пароль!
            user.setPassword(passwordEncoder.encode("212121"));
            user.setRole(Role.USER);
            userRepository.save(user);
            System.out.println("Тестовый пользователь создан!");
        }
    }
    
    public AuthResponse login(AuthRequest request)
    {
        User user = userRepository
                        .findByEmail(request.email())
                        .orElseThrow(() ->
                                    new NotFoundException("Пользователь не найден"));

        if(!passwordEncoder.matches(request.password(), user.getPassword()))
        {
            throw new RuntimeException("Неверный пароль!");
        }
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
