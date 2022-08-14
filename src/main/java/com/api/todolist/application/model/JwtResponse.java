package com.api.todolist.application.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private final String accessToken;
    private final String tokenType;
    private final String expiresIn;
}
