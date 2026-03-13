package com.example.employee_api.exception;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
        NotFoundException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            Instant.now(),
            404,
            ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(404).body(errorResponse);
    }
}
