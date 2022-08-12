package com.api.todolist.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/auth/oauth/token-jwt")
    public void auth() {

    }
}
