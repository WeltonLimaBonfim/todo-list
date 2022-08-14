package com.api.todolist.domain.repository;

import com.api.todolist.infrastructure.entity.StatusEntity;

import java.util.Optional;

public interface StatusRepository {

    Optional<StatusEntity> findByDescription(String description);

}
