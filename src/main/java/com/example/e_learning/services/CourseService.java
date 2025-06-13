package com.example.e_learning.services;

import com.example.e_learning.dto.CourseRequest;
import com.example.e_learning.dto.CourseResponse;
import com.example.e_learning.dto.InstructorCourseResponse;
import com.example.e_learning.entity.Course;
import com.example.e_learning.entity.User;
import com.example.e_learning.repositories.CourseRepository;
import com.example.e_learning.repositories.EnrollmentRepository;
import com.example.e_learning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    public CourseResponse addCourse(CourseRequest courseRequest, String userEmail) {
        User intrsuctor = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found!"));
        Course course = new Course();

        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setInstructor(intrsuctor);

        courseRepository.save(course);

        return new CourseResponse(course.getCourseId(), "Course created successfully");
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    public String deleteById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        courseRepository.delete(course);
        return "Course deleted successfully";
    }

    public List<InstructorCourseResponse> getCoursesByInstructor() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User instructor = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        List<Course> courses = courseRepository.findByInstructor(instructor);

        return courses.stream()
                .map(course -> {
                    Long enrolledCount = enrollmentRepository.countByCourse(course);
                    return new InstructorCourseResponse(
                            course.getCourseId(),
                            course.getTitle(),
                            enrolledCount
                    );
                }).toList();
    }
}
