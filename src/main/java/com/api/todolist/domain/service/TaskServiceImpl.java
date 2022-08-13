package com.api.todolist.domain.service;

import com.api.todolist.common.enums.StatusEnum;
import com.api.todolist.common.exception.StatusNotFoundException;
import com.api.todolist.common.exception.TaskNotFoundException;
import com.api.todolist.common.exception.UserNotFoundException;
import com.api.todolist.domain.mapper.TaskMapper;
import com.api.todolist.domain.model.TaskRequest;
import com.api.todolist.domain.model.TaskResponse;
import com.api.todolist.domain.repository.StatusRepository;
import com.api.todolist.domain.repository.TaskRepository;
import com.api.todolist.domain.repository.UserRepository;
import com.api.todolist.infrastructure.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<TaskResponse> findByStatus(StatusEnum status) {

        log.info("Get task into database");

        final var user = userRepository.findByClientId(getClientId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        final var sort = Sort.by(Sort.Direction.ASC, "status");

        if(user.isFlAdmin()) {
            log.info("--- Admin user found. Return all tasks ---");
            final var taskEntityList = filterList(taskRepository.findAll(sort), status);

            return TaskMapper.paraListTaskResponse(taskEntityList);
        }

        final var taskEntityList = filterList(taskRepository.findByUserAndStatus(user, sort), status);

        return TaskMapper.paraListTaskResponse(taskEntityList);
    }

    @Override
    public TaskResponse save(TaskRequest taskRequest) {

        log.info("--- Save Task into database ---");

        final var user = userRepository.findByClientId(getClientId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        final var status = statusRepository.findByDescription(taskRequest.getStatusEnum().name())
                .orElseThrow(() -> new StatusNotFoundException("Status not found for this description: " + taskRequest.getStatusEnum().name()));

        final var taskEntity = TaskMapper.paraTaskEntity(taskRequest, user, status);

        return TaskMapper.paraTaskResponse(taskRepository.save(taskEntity));
    }

    @Override
    public void delete(Long taskId) {
        log.info("--- Delete Task into database ---");

        final var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found for this id: " + taskId));

        taskRepository.delete(task);
    }

    @Override
    public TaskResponse update(Long taskId, TaskRequest taskRequest) {

        log.info("--- Update Task into database ---");

        final var taskFound = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found for this id: " + taskId));

        final var status = statusRepository.findByDescription(taskRequest.getStatusEnum().name())
                .orElseThrow(() -> new StatusNotFoundException("Status not found for this description: " + taskRequest.getStatusEnum().name()));

        taskFound.setResume(taskRequest.getName());
        taskFound.setDescription(taskRequest.getDescription());
        taskFound.setStatus(status);

        return TaskMapper.paraTaskResponse(taskRepository.save(taskFound));
    }

    private List<TaskEntity> filterList(List<TaskEntity> list, StatusEnum status){
        if(status == StatusEnum.PENDING || status == StatusEnum.COMPLETED){
            return list.stream()
                    .filter(t -> t.getStatus().getDescription().equalsIgnoreCase(status.name()))
                    .collect(Collectors.toList());
        }
        return list;
    }

    private String getClientId() {
        final var clientId = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientId.getUsername();
    }
}
