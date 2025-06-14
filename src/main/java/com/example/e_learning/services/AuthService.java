package com.example.e_learning.services;

import com.example.e_learning.dto.LoginRequest;
import com.example.e_learning.dto.LoginResponse;
import com.example.e_learning.dto.RegisterRequest;
import com.example.e_learning.dto.RegisterResponse;
import com.example.e_learning.entity.User;
import com.example.e_learning.exception.AlreadyEnrolledException;
import com.example.e_learning.repositories.UserRepository;
import com.example.e_learning.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    public RegisterResponse registerUser(RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new AlreadyEnrolledException("Email already in use");
        }


        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return new RegisterResponse(user.getUserId(),"Successfully registered");
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
        );
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!!"));

        String token = jwtUtil.generateToken(user.getEmail(),user.getRole());

        return new LoginResponse(token, user.getUserId(),user.getRole());

    }
}
