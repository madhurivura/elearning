package com.example.e_learning.controller;


import com.example.e_learning.dto.AdminResponse;
import com.example.e_learning.entity.User;
import com.example.e_learning.repositories.UserRepository;
import com.example.e_learning.services.CourseService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<AdminResponse>> getAllUsers(Authentication auth) {
        // Optional: Double-check role


        List<User> users = userRepository.findAll();

        List<AdminResponse> response = users.stream()
                .map(user -> new AdminResponse(user.getUserId(), user.getFullName(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/course/{courseId}")
    public String deleteById(@PathVariable Long courseId){
        return courseService.deleteById(courseId);
    }
}
