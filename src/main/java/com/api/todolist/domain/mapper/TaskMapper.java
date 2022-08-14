package com.api.todolist.domain.mapper;

import com.api.todolist.common.enums.StatusEnum;
import com.api.todolist.common.exception.TaskNotFoundException;
import com.api.todolist.domain.model.TaskRequest;
import com.api.todolist.domain.model.TaskResponse;
import com.api.todolist.infrastructure.entity.StatusEntity;
import com.api.todolist.infrastructure.entity.TaskEntity;
import com.api.todolist.infrastructure.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;


@UtilityClass
public class TaskMapper {

    public static List<TaskResponse> paraListTaskResponse(List<TaskEntity> taskEntityList) {
        final var list = new ArrayList<TaskResponse>();

        if (taskEntityList.isEmpty()) {
            throw new TaskNotFoundException("List task is empty");
        }

        taskEntityList.forEach(t -> list.add(paraTaskResponse(t)));

        return list;
    }

    public static TaskResponse paraTaskResponse(TaskEntity taskEntity) {
        return TaskResponse.builder()
                .id(taskEntity.getId())
                .name(taskEntity.getResume())
                .description(taskEntity.getDescription())
                .statusEnum(StatusEnum.getStatusName(taskEntity.getStatus().getDescription()))
                .user(taskEntity.getUser().getName())
                .build();
    }

    public static TaskEntity paraTaskEntity(TaskRequest taskRequest, UserEntity userEntity, StatusEntity statusEntity) {
        final var taskEntity = new TaskEntity();
        taskEntity.setResume(taskRequest.getName());
        taskEntity.setDescription(taskRequest.getDescription());
        taskEntity.setUser(userEntity);
        taskEntity.setStatus(statusEntity);
        return taskEntity;
    }
}
