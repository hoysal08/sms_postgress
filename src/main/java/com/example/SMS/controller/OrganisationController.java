package com.example.SMS.controller;

import com.example.SMS.CustomExceptions.EntityNotFoundException;
import com.example.SMS.service.CourseService;
import com.example.SMS.service.InstructorService;
import com.example.SMS.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> deleteCourseById(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/studentCount")
    public ResponseEntity<Long> getStudentCount() {
        long studentCount = studentService.getTotalStudentCount();
        return ResponseEntity.status(HttpStatus.OK).body(studentCount);
    }


    @GetMapping("/getStudentCourseCount")
    public ResponseEntity<List<Object[]>> countStudentsInCourses() {
        List<Object[]> studentCounts = courseService.countStudentsInCourses();
        return ResponseEntity.status(HttpStatus.OK).body(studentCounts);
    }

    @GetMapping("/getInstructorsForCourses")
    public ResponseEntity<List<Object[]>> getInstructorsForCourses() {
        List<Object[]> instructors = instructorService.getInstructorsForCourses();
        return ResponseEntity.status(HttpStatus.OK).body(instructors);
    }
}
