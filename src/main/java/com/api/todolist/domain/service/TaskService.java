package com.api.todolist.domain.service;

import com.api.todolist.common.enums.StatusEnum;
import com.api.todolist.domain.model.TaskRequest;
import com.api.todolist.domain.model.TaskResponse;

import java.util.List;

public interface TaskService {

    List<TaskResponse> findByStatus(StatusEnum status);

    TaskResponse save(TaskRequest task);

    void delete(Long taskId);

    TaskResponse update(Long taskId, TaskRequest taskRequest);
}
