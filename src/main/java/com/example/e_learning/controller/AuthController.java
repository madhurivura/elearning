package com.example.e_learning.controller;

import com.example.e_learning.dto.LoginRequest;
import com.example.e_learning.dto.LoginResponse;
import com.example.e_learning.dto.RegisterRequest;
import com.example.e_learning.dto.RegisterResponse;
import com.example.e_learning.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest){
        return authService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest){
        return authService.loginUser(loginRequest);
    }
}
