package com.api.todolist.domain.model;

import com.api.todolist.common.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final StatusEnum statusEnum;
    private final String user;
}
