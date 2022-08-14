package com.api.todolist.application.mapper;

import com.api.todolist.application.model.JwtResponse;
import com.api.todolist.provider.representation.AuthResponseRepresentation;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthMapper {

    public static AuthResponseRepresentation paraAuthResponseRepresentation(JwtResponse jwtResponse) {
        return new AuthResponseRepresentation()
                .accessToken(jwtResponse.getAccessToken())
                .tokenType(jwtResponse.getTokenType())
                .expiresIn(jwtResponse.getExpiresIn());
    }
}
