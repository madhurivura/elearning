package com.example.e_learning.controller;


import com.example.e_learning.dto.EnrollRequest;
import com.example.e_learning.dto.EnrollResponse;
import com.example.e_learning.dto.mycourses;
import com.example.e_learning.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    public EnrollResponse enrollStudent(@RequestBody EnrollRequest enrollRequest){
        return enrollmentService.enrollStudent(enrollRequest);
    }

    @GetMapping("/my-courses")
    @PreAuthorize("hasRole('STUDENT')")
    public List<mycourses> getMyCourses(){
        return enrollmentService.getMyCourses();
    }

}
