package com.api.todolist.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

class TokenUtilTest {

    private String token;
    private User user;

    @BeforeEach
    void setup() {
        this.user = new User("aaaaaaaa", "bbbbbbb", new ArrayList<>());
        this.token = TokenUtil.generateToken(user, "bbbbbbb");
    }

    @Test
    void givenValidTokenWhenSendTokenThenReturnTrue() {
        final var isValidToken = TokenUtil.validateToken(token, "bbbbbbb", user);

        Assertions.assertTrue(isValidToken);
    }
}