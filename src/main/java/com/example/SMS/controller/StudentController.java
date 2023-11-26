package com.example.SMS.controller;

import com.example.SMS.dto.StudentDTO;
import com.example.SMS.entity.CourseStatus;
import com.example.SMS.entity.Student;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.service.CourseService;
import com.example.SMS.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @PostMapping
    StudentDTO addStudent(@RequestBody StudentDTO student) {
        Student addedStudent = studentService.addStudent(student);
        StudentDTO returnStudent = new StudentDTO();
        BeanUtils.copyProperties(addedStudent, returnStudent);
        returnStudent.setStudentDob(addedStudent.getStudentDob().toString());
        return returnStudent;
    }

    @PutMapping("/update/{studentId}")
    StudentDTO updateStudent(@RequestBody StudentDTO student, @PathVariable String studentId) {
        StudentDTO updatedStudentDto = new StudentDTO();
        Student updatedtudent = studentService.editStudent(student, studentId);
        BeanUtils.copyProperties(updatedtudent, updatedStudentDto);
        updatedStudentDto.setStudentDob(updatedtudent.getStudentDob().toString());
        return updatedStudentDto;
    }

    @GetMapping
    List<StudentDTO> getAllStudents() {
        List<Student> allStudentsList = studentService.getAllStudents();
        List<StudentDTO> allStudentReturnList = new ArrayList<>();
        for (Student std : allStudentsList) {
            allStudentReturnList.add(GlobalHelper.studentToStudentDTO(std));
        }
        return allStudentReturnList;
    }

    @DeleteMapping("/delete/{studentId}")
    void deleteStudentById(@PathVariable String studentId) {
        if (!studentId.isEmpty()) {
            studentService.deleteStudent(studentId);
        }
    }

    @PostMapping("/register/{courseId}")
    Boolean registerForCourse(@RequestParam String studentId, @PathVariable Long courseId) {
        return studentService.registerForCourse(studentId, courseId);
    }

    @PostMapping("/withdraw/{courseId}")
    Boolean withdrawForCourse(@RequestParam String studentId, @PathVariable Long courseId) {
        return studentService.withdrawForCourse(studentId, courseId);
    }

    @GetMapping("/getStatus/{courseId}")
    CourseStatus getCourseStatus(@PathVariable Long courseId) {
        return courseService.getStatus(courseId);
    }
}
