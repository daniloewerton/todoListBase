package com.daniloewerton.todolist.services.exceptions;

import com.daniloewerton.todolist.controllers.exception.StandardError;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private final List<FieldMessage> errors = new ArrayList<>();

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(final String fieldName, final String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}