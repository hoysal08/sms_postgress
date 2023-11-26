package com.example.SMS.service;

import com.example.SMS.dto.CourseDTO;
import com.example.SMS.entity.Course;
import com.example.SMS.entity.CourseStatus;
import com.example.SMS.entity.Instructor;
import com.example.SMS.entity.Student;

import java.util.List;

public interface CourseService {
    Course addCourse(CourseDTO courseDTO);

    Course editCourse(CourseDTO courseDTO, Long courseId);

    void deleteCourse(Long courseId);

    List<Course> getAllCourse();

    Course getCourseById(Long courseId);

    Boolean registerForCourse(Long instructorId, Long courseId);

    Boolean withdrawForCourse(Long instructorId, Long courseId);

    void setStatus(CourseStatus status, Long courseId);

    CourseStatus getStatus(Long courseId);

    List<Object[]> countStudentsInCourses();

    List<Object[]> getCourseDetailsByCourseId(Long courseId);

    List<String> getStudentsByCourseStatus(CourseStatus courseStatus);
}
