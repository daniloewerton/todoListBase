package com.daniloewerton.todolist.services.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
