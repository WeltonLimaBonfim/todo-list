package com.api.todolist.infrastructure.repository;

import com.api.todolist.domain.repository.StatusRepository;
import com.api.todolist.infrastructure.entity.StatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatusRepositoryImpl implements StatusRepository {

    private final SpringDataStatusRepository springDataStatusRepository;

    @Override
    public Optional<StatusEntity> findByDescription(String description) {
        return springDataStatusRepository.findByDescription(description);
    }
}
