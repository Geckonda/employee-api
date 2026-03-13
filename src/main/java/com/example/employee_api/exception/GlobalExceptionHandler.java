package com.example.employee_api.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
        MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error -> 
              errors.put(
                error.getField(),
                error.getDefaultMessage()
              )  
            );
        
            ValidationErrorResponse response = 
                new ValidationErrorResponse(
                    Instant.now(),
                    400,
                    "Ошибка валидации",
                    errors
                );
                return ResponseEntity.badRequest().body(response);
    }
}
