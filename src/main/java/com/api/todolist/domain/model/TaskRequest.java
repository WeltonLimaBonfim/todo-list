package com.api.todolist.domain.model;

import com.api.todolist.common.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskRequest {
    private final String name;
    private final String description;
    private final StatusEnum statusEnum;
}
