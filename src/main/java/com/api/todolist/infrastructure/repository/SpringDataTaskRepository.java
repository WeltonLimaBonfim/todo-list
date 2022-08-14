package com.api.todolist.infrastructure.repository;

import com.api.todolist.infrastructure.entity.TaskEntity;
import com.api.todolist.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataTaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByUser(UserEntity user, Sort sort);
}
