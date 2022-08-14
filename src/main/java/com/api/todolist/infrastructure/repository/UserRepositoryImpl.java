package com.api.todolist.infrastructure.repository;

import com.api.todolist.domain.repository.UserRepository;
import com.api.todolist.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public Optional<UserEntity> findByClientId(String clientId) {
        return springDataUserRepository.findByClientId(clientId);
    }
}
