package com.api.todolist.domain.repository;

import com.api.todolist.infrastructure.entity.TaskEntity;
import com.api.todolist.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<TaskEntity> findByUserAndStatus(UserEntity user, Sort sort);

    List<TaskEntity> findAll(Sort sort);

    Optional<TaskEntity> findById(Long taskId);

    TaskEntity save(TaskEntity task);

    void delete(TaskEntity taskEntity);

}
