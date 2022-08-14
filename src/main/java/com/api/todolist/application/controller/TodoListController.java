package com.api.todolist.application.controller;

import com.api.todolist.application.mapper.AuthMapper;
import com.api.todolist.application.mapper.TodoListMapper;
import com.api.todolist.common.enums.StatusEnum;
import com.api.todolist.domain.service.JwtAuthService;
import com.api.todolist.domain.service.TaskService;
import com.api.todolist.provider.api.ApiApi;
import com.api.todolist.provider.representation.AuthRequestRepresentation;
import com.api.todolist.provider.representation.AuthResponseRepresentation;
import com.api.todolist.provider.representation.TaskRequestRepresentation;
import com.api.todolist.provider.representation.TaskResponseRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoListController implements ApiApi {

    private final TaskService taskService;
    private final JwtAuthService jwtAuthService;

    public TodoListController(@Lazy JwtAuthService jwtAuthService, TaskService taskService) {
        this.jwtAuthService = jwtAuthService;
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<AuthResponseRepresentation> postToken(AuthRequestRepresentation body) {
        log.info("---- generating jwt token ----");

        final var response = jwtAuthService.generateToken(body.getClientId(), body.getClientSecret());

        log.info("---- finish generating jwt token ----");
        return ResponseEntity.ok(AuthMapper.paraAuthResponseRepresentation(response));
    }

    @Override
    public ResponseEntity<List<TaskResponseRepresentation>> findTasksByUserAndStatus(String status) {
        log.info("---- Find Task By Status ----");

        final var response = taskService.findByStatus(StatusEnum.getStatusName(status));

        log.info("---- Finishing find Task By Status ----");
        return ResponseEntity.ok(TodoListMapper.paraListTaskResponseRepresentation(response));
    }

    @Override
    public ResponseEntity<TaskResponseRepresentation> insertTasksByUser(TaskRequestRepresentation body) {
        log.info("---- Insert Task ----");

        final var response = taskService.save(TodoListMapper.paraTaskRequest(body));

        log.info("---- Finishing Insert Task ----");
        return ResponseEntity.ok(TodoListMapper.paraTaskResponseRepresentation(response));
    }

    @Override
    public ResponseEntity<Void> updateTaskById(TaskRequestRepresentation body, Long taskId) {
        log.info("---- Update Task By Id ----");

        taskService.update(taskId, TodoListMapper.paraTaskRequest(body));

        log.info("---- Finishing Update Task By Id ----");
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteTaskById(Long taskId) {
        log.info("---- Delete Task By Id ----");

        taskService.delete(taskId);

        log.info("---- Finishing Delete Task By Id ----");
        return ResponseEntity.ok().build();
    }
}



