package com.example.triapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::toString)
                .collect(Collectors.toList());
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation Failed", "Invalid request", details);
        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiError> handleSqlConstraint(SQLIntegrityConstraintViolationException ex) {
        ApiError err = new ApiError(HttpStatus.CONFLICT.value(), "Constraint Violation", ex.getMessage(), Collections.emptyList());
        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex) {
        ApiError err = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", ex.getMessage(), Collections.emptyList());
        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}