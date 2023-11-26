package com.example.SMS.controller;


import com.example.SMS.service.CourseService;
import com.example.SMS.service.InstructorService;
import com.example.SMS.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Organisation")
public class OrganisationController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    InstructorService instructorService;

    @DeleteMapping("/delete/{courseId}")
    void deleteCourseById(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @GetMapping("/studentCount")
    Long getStudentCount() {
        return studentService.getTotalStudentCount();
    }

    @GetMapping("/getStudentCourseCount")
    List<Object[]> countStudentsInCourses() {
        return courseService.countStudentsInCourses();
    }

    @GetMapping
    List<Object[]> getInstructorsForCourses() {
        return instructorService.getInstructorsForCourses();
    }
}
