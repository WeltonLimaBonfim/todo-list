package com.api.todolist.domain.repository;

import com.api.todolist.infrastructure.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByClientId(String clientId);
}
