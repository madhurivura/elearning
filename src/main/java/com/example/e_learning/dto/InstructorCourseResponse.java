package com.example.e_learning.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorCourseResponse {
    Long courseId;
    String title;
    Long enrolledStudents;
}
