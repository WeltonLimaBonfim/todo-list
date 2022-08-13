package com.api.todolist.infrastructure.repository;

import com.api.todolist.infrastructure.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataStatusRepository extends JpaRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByDescription(String description);
}
