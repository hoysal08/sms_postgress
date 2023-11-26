package com.example.SMS.controller;

import com.example.SMS.CustomExceptions.EntityNotFoundException;
import com.example.SMS.dto.CourseDTO;
import com.example.SMS.entity.Course;
import com.example.SMS.entity.CourseStatus;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO) {
        try {
            Course addedCourse = courseService.addCourse(courseDTO);
            CourseDTO courseDTO_return = GlobalHelper.courseToCourseDto(addedCourse);
            return ResponseEntity.status(HttpStatus.CREATED).body(courseDTO_return);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> allCourseList = courseService.getAllCourse();
        List<CourseDTO> allCourseReturnList = new ArrayList<>();
        for (Course crs : allCourseList) {
            allCourseReturnList.add(GlobalHelper.courseToCourseDto(crs));
        }
        return ResponseEntity.status(HttpStatus.OK).body(allCourseReturnList);
    }


    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long courseId) {
        try {
            Course updatedCourse = courseService.editCourse(courseDTO, courseId);
            CourseDTO updatedCourseDTO = GlobalHelper.courseToCourseDto(updatedCourse);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCourseDTO);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/register/{courseId}")
    public ResponseEntity<Boolean> registerForCourse(@RequestParam Long instructorId, @PathVariable Long courseId) {
        try {
            Boolean result = courseService.registerForCourse(instructorId, courseId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/withdraw/{courseId}")
    public ResponseEntity<Boolean> withdrawForCourse(@RequestParam Long instructorId, @PathVariable Long courseId) {
        try {
            Boolean result = courseService.withdrawForCourse(instructorId, courseId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/statusUpdate/{status}")
    public ResponseEntity<Void> setCourseStatus(@PathVariable CourseStatus status, @RequestParam Long courseId) {
        try {
            courseService.setStatus(status, courseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/getStatus/{courseId}")
    public ResponseEntity<CourseStatus> getCourseStatus(@PathVariable Long courseId) {
        try {
            CourseStatus status = courseService.getStatus(courseId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getStudentCourseCount")
    public ResponseEntity<List<Object[]>> countStudentsInCourses() {
        List<Object[]> result = courseService.countStudentsInCourses();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/getDetails/{courseId}")
    public ResponseEntity<List<Object[]>> getCourseDetailsByCourseId(@PathVariable Long courseId) {
        List<Object[]> result = courseService.getCourseDetailsByCourseId(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/getStudentsByCourseStatus")
    public ResponseEntity<List<String>> getStudentsByCourseStatus(@RequestParam CourseStatus courseStatus) {
        List<String> result = courseService.getStudentsByCourseStatus(courseStatus);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
