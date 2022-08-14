package com.api.todolist.application.mapper;

import com.api.todolist.common.enums.StatusEnum;
import com.api.todolist.domain.model.TaskRequest;
import com.api.todolist.domain.model.TaskResponse;
import com.api.todolist.provider.representation.TaskRequestRepresentation;
import com.api.todolist.provider.representation.TaskResponseRepresentation;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TodoListMapper {

    public static List<TaskResponseRepresentation> paraListTaskResponseRepresentation(List<TaskResponse> taskResponse) {
        final var list = new ArrayList<TaskResponseRepresentation>();

        taskResponse.forEach(t -> list.add(paraTaskResponseRepresentation(t)));

        return list;
    }

    public static TaskResponseRepresentation paraTaskResponseRepresentation(TaskResponse taskResponse) {
        return new TaskResponseRepresentation()
                .id(taskResponse.getId())
                .name(taskResponse.getName())
                .description(taskResponse.getDescription())
                .status(taskResponse.getStatusEnum().name())
                .user(taskResponse.getUser());
    }

    public static TaskRequest paraTaskRequest(TaskRequestRepresentation taskRequestRepresentation) {
        return TaskRequest.builder()
                .name(taskRequestRepresentation.getName())
                .description(taskRequestRepresentation.getDescription())
                .statusEnum(StatusEnum.getStatusName(taskRequestRepresentation.getStatus().name()))
                .build();
    }
}
