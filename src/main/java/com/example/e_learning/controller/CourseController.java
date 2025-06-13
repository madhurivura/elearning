package com.example.e_learning.controller;

import com.example.e_learning.dto.CourseRequest;
import com.example.e_learning.dto.CourseResponse;
import com.example.e_learning.dto.InstructorCourseResponse;
import com.example.e_learning.entity.Course;
import com.example.e_learning.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/course")
    public CourseResponse addCourse(@RequestBody CourseRequest courseRequest, @AuthenticationPrincipal UserDetails userDetails){
        return courseService.addCourse(courseRequest, userDetails.getUsername());
    }

    @GetMapping("/course")
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/course/{courseId}")
    public String deleteById(@PathVariable Long courseId){
        return courseService.deleteById(courseId);
    }

    @GetMapping("/instructor/courses")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public List<InstructorCourseResponse> getInstructorCourses() {
        return courseService.getCoursesByInstructor();
    }

}