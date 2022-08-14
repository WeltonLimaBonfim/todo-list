package com.api.todolist.infrastructure.repository;

import com.api.todolist.domain.repository.TaskRepository;
import com.api.todolist.infrastructure.entity.TaskEntity;
import com.api.todolist.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final SpringDataTaskRepository springDataTaskRepository;

    @Override
    public List<TaskEntity> findByUserAndStatus(UserEntity user, Sort sort) {
        return springDataTaskRepository.findByUser(user, sort);
    }

    @Override
    public List<TaskEntity> findAll(Sort sort) {
        return springDataTaskRepository.findAll(sort);
    }

    @Override
    public Optional<TaskEntity> findById(Long taskId) {
        return springDataTaskRepository.findById(taskId);
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return springDataTaskRepository.save(taskEntity);
    }

    @Override
    public void delete(TaskEntity taskEntity) {
        springDataTaskRepository.delete(taskEntity);
    }

}
