package com.example.e_learning.repositories;


import com.example.e_learning.entity.Course;
import com.example.e_learning.entity.Enrollment;
import com.example.e_learning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(User student);
    Optional<Enrollment> findByStudent_UserIdAndCourse_CourseId(Long userId, Long courseId);
    Long countByCourse(Course course);
}
