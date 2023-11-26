package com.example.SMS.service;

import com.example.SMS.dto.StudentDTO;
import com.example.SMS.entity.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(StudentDTO student);

    Student editStudent(StudentDTO student, String studentId);

    void deleteStudent(String studentId);

    List<Student> getAllStudents();

    Boolean registerForCourse(String studentId, Long courseId);

    Boolean withdrawForCourse(String studentId, Long courseId);

    long getTotalStudentCount();
}
