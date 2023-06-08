package com.daniloewerton.todolist.services.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataIntegratyViolation extends RuntimeException {

    public DataIntegratyViolation(final String msg) {
        super(msg);
    }
}
