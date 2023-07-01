package com.daniloewerton.todolist.services.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(final String msg) {
        super(msg);
    }
}
