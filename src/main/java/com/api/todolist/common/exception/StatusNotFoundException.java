package com.api.todolist.common.exception;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(String msg) {
        super(msg);
    }
}
