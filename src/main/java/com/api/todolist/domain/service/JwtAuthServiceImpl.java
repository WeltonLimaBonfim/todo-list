package com.api.todolist.domain.service;

import com.api.todolist.application.model.JwtResponse;
import com.api.todolist.common.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {

    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    private String secret;

    public JwtAuthServiceImpl(CustomUserDetailService customUserDetailService, @Lazy AuthenticationManager authenticationManager) {
        this.customUserDetailService = customUserDetailService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public JwtResponse generateToken(String clientId, String clientSecret) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(clientId, clientSecret));

        final var userDetails = customUserDetailService.loadUserByUsername(clientId);

        final var token = TokenUtil
                .generateToken(userDetails, secret);

        return JwtResponse.builder()
                .accessToken(token)
                .tokenType(TokenUtil.TOKEN_TYPE)
                .expiresIn(String.valueOf(TokenUtil.JWT_TOKEN_VALIDITY))
                .build();
    }
}
