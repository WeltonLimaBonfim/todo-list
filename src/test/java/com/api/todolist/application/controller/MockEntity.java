package com.api.todolist.application.controller;

import com.api.todolist.infrastructure.entity.StatusEntity;
import com.api.todolist.infrastructure.entity.TaskEntity;
import com.api.todolist.infrastructure.entity.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MockEntity {

    public static TaskEntity mockTaskEntityStatusPending() {
        final var taskEntity = new TaskEntity();
        final var userEntity = new UserEntity();
        final var statusEntity = new StatusEntity();

        taskEntity.setResume("Test resume");
        taskEntity.setDescription("Test description");
        taskEntity.setId(1L);

        userEntity.setName("Test name");
        statusEntity.setDescription("PENDING");

        taskEntity.setStatus(statusEntity);
        taskEntity.setUser(userEntity);

        return taskEntity;
    }

    public static TaskEntity mockTaskEntityStatusCompleted() {
        final var taskEntity = new TaskEntity();
        final var userEntity = new UserEntity();
        final var statusEntity = new StatusEntity();

        taskEntity.setResume("Test resume");
        taskEntity.setDescription("Test description");
        taskEntity.setId(1L);

        userEntity.setName("Test name");
        statusEntity.setDescription("COMPLETED");

        taskEntity.setStatus(statusEntity);
        taskEntity.setUser(userEntity);

        return taskEntity;
    }

    public static StatusEntity mockStatusEntity() {
        final var statusEntity = new StatusEntity();

        statusEntity.setDescription("PENDING");
        statusEntity.setId(1L);

        return statusEntity;
    }
}
