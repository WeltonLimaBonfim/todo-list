package com.api.todolist.domain.service;

import com.api.todolist.application.model.JwtResponse;

public interface JwtAuthService {

    JwtResponse generateToken(String clientId, String clientSecret);

}
