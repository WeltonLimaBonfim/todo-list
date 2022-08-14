package com.api.todolist.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private final String clientId;
    private final String clientSecret;
    private final boolean flAdmin;
}
