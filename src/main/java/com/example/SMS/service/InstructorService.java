package com.example.SMS.service;

import com.example.SMS.dto.InstructorDTO;
import com.example.SMS.entity.Instructor;

import java.util.List;

public interface InstructorService {
    Instructor addInstructor(InstructorDTO instructorDto);

    Instructor editInstructor(InstructorDTO instructorDto, Long instructorId);

    void deleteInstructor(Long instructorId);

    List<Instructor> getAllInstructors();

    Instructor getInstructorById(Long instructorId);

    List<Object[]> getInstructorsForCourses();

    long getTotalStudentCount();
}
