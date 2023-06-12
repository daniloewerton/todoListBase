package com.daniloewerton.todolist.controllers.exception;

import com.daniloewerton.todolist.services.exceptions.DataIntegratyViolation;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import com.daniloewerton.todolist.services.exceptions.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(final ObjectNotFoundException exception, final HttpServletRequest request) {
        final StandardError standardError = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Object Not Found" ,exception.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(standardError);
    }

    @ExceptionHandler(DataIntegratyViolation.class)
    public ResponseEntity<StandardError> objectExists(final DataIntegratyViolation exception, final HttpServletRequest request) {
        final StandardError standardError = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Data Integraty violation", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(final MethodArgumentNotValidException exception, final HttpServletRequest request) {
        final ValidationError error = new ValidationError();
        final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(status.value());
        error.setError("Validation Exception");
        error.setMessage(exception.getMessage());

        for (FieldError f : exception.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage());
        }

        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
