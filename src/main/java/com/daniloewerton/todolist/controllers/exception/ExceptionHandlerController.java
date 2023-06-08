package com.daniloewerton.todolist.controllers.exception;

import com.daniloewerton.todolist.services.exceptions.DataIntegratyViolation;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(final ObjectNotFoundException error, final HttpServletRequest request) {
        final StandardError standardError = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), error.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(standardError);
    }

    @ExceptionHandler(DataIntegratyViolation.class)
    public ResponseEntity<StandardError> objectExists(final DataIntegratyViolation error, final HttpServletRequest request) {
        final StandardError standardError = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), error.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(standardError);
    }
}
