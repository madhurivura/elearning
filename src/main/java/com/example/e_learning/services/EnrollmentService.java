package com.example.e_learning.services;

import com.example.e_learning.dto.CourseResponse;
import com.example.e_learning.dto.EnrollRequest;
import com.example.e_learning.dto.EnrollResponse;
import com.example.e_learning.dto.mycourses;
import com.example.e_learning.entity.Course;
import com.example.e_learning.entity.Enrollment;
import com.example.e_learning.entity.User;
import com.example.e_learning.repositories.CourseRepository;
import com.example.e_learning.repositories.EnrollmentRepository;
import com.example.e_learning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    UserRepository userRepository;


    public EnrollResponse enrollStudent(EnrollRequest enrollRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(enrollRequest.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.findByStudent_UserIdAndCourse_CourseId(student.getUserId(), course.getCourseId()).isPresent()) {
            throw new RuntimeException("Already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .build();

        enrollmentRepository.save(enrollment);
        return new EnrollResponse(enrollment.getEnrollmentId(),"Successfully enrolled");

    }

    public List<mycourses> getMyCourses() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
        return enrollments.stream()
                .map(enrollment -> {
                    Course course = enrollment.getCourse();
                    return new mycourses(course.getCourseId(), course.getTitle(), course.getDescription(), course.getInstructor().getFullName());
                })
                .toList();
    }
}
