package com.example.SMS.controller;

import com.example.SMS.dto.CourseDTO;
import com.example.SMS.entity.Course;
import com.example.SMS.entity.CourseStatus;
import com.example.SMS.entity.Student;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    CourseDTO addCourse(@RequestBody CourseDTO courseDTO) {
        Course addedCourse = courseService.addCourse(courseDTO);
        CourseDTO courseDTO_return = GlobalHelper.courseToCourseDto(addedCourse);
        return courseDTO_return;
    }

    @GetMapping
    List<CourseDTO> getAllCourses() {
        List<Course> allCourseList = courseService.getAllCourse();
        List<CourseDTO> allCourseReturnList = new ArrayList<>();
        for (Course crs : allCourseList) {
            allCourseReturnList.add(GlobalHelper.courseToCourseDto(crs));
        }
        return allCourseReturnList;
    }

    @DeleteMapping("/delete/{courseId}")
    void deleteCourseById(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping("/update/{courseId}")
    CourseDTO updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long courseId) {
        Course updatedCourse = courseService.editCourse(courseDTO, courseId);
        CourseDTO updatedCourseDTO = GlobalHelper.courseToCourseDto(updatedCourse);
        return updatedCourseDTO;
    }

    @PostMapping("/register/{courseId}")
    Boolean registerForCourse(@RequestParam Long instructorId, @PathVariable Long courseId) {
        return courseService.registerForCourse(instructorId, courseId);
    }

    @PostMapping("/withdraw/{courseId}")
    Boolean withdrawForCourse(@RequestParam Long instructorId, @PathVariable Long courseId) {
        return courseService.withdrawForCourse(instructorId, courseId);
    }

    @PostMapping("/statusUpdate/{status}")
    void setCourseStatus(@PathVariable CourseStatus status, @RequestParam Long courseId) {
        courseService.setStatus(status, courseId);
    }

    @GetMapping("/getStatus/{courseId}")
    CourseStatus getCourseStatus(@PathVariable Long courseId) {
        return courseService.getStatus(courseId);
    }

    @GetMapping("/getStudentCourseCount")
    List<Object[]> countStudentsInCourses() {
        return courseService.countStudentsInCourses();
    }

    @GetMapping("/getDetails/{courseId}")
    List<Object[]> getCourseDetailsByCourseId(@PathVariable Long courseId) {
        return courseService.getCourseDetailsByCourseId(courseId);
    }

    @GetMapping("/getStudentsByCourseStatus")
    List<String> getStudentsByCourseStatus(CourseStatus courseStatus) {
        return courseService.getStudentsByCourseStatus(courseStatus);
    }
}
