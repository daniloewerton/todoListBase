package com.daniloewerton.todolist.services.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataIntegrityViolation extends RuntimeException {

    public DataIntegrityViolation(final String msg) {
        super(msg);
    }
}
