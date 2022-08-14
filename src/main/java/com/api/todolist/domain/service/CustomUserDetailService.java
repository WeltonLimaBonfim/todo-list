package com.api.todolist.domain.service;

import com.api.todolist.common.exception.UserNotFoundException;
import com.api.todolist.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
        log.info("--- Find user by clientId ---");

        final var userEntity = userRepository.findByClientId(clientId);

        final var user = userEntity.map(u -> com.api.todolist.domain.model.User.builder()
                        .clientId(u.getClientId())
                        .clientSecret(u.getClientSecret())
                        .build())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

        log.info("--- Returning user ---");
        return new User(user.getClientId(), new BCryptPasswordEncoder().encode(user.getClientSecret()), new ArrayList<>());
    }
}
