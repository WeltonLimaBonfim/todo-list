package com.api.todolist.application.handler;

import com.api.todolist.common.exception.StatusNotFoundException;
import com.api.todolist.common.exception.TaskNotFoundException;
import com.api.todolist.common.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<?> statusNotFoundException(StatusNotFoundException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> taskNotFoundException(TaskNotFoundException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException e) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
