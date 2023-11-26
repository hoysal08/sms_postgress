package com.example.SMS.controller;

import com.example.SMS.CustomExceptions.EntityNotFoundException;
import com.example.SMS.dto.StudentDTO;
import com.example.SMS.entity.CourseStatus;
import com.example.SMS.entity.Student;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.service.CourseService;
import com.example.SMS.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO student) {
            try {
                Student addedStudent = studentService.addStudent(student);
                StudentDTO returnStudent = new StudentDTO();
                BeanUtils.copyProperties(addedStudent, returnStudent);
                returnStudent.setStudentDob(addedStudent.getStudentDob().toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(returnStudent);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        @PutMapping("/update/{studentId}")
        public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO student, @PathVariable String studentId) {
            try {
                StudentDTO updatedStudentDto = new StudentDTO();
                Student updatedStudent = studentService.editStudent(student, studentId);
                if (updatedStudent != null) {
                    BeanUtils.copyProperties(updatedStudent, updatedStudentDto);
                    updatedStudentDto.setStudentDob(updatedStudent.getStudentDob().toString());
                    return ResponseEntity.status(HttpStatus.OK).body(updatedStudentDto);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        @GetMapping
        public ResponseEntity<List<StudentDTO>> getAllStudents() {
            List<Student> allStudentsList = studentService.getAllStudents();
            List<StudentDTO> allStudentReturnList = new ArrayList<>();
            for (Student std : allStudentsList) {
                allStudentReturnList.add(GlobalHelper.studentToStudentDTO(std));
            }
            return ResponseEntity.status(HttpStatus.OK).body(allStudentReturnList);
        }

        @DeleteMapping("/delete/{studentId}")
        public ResponseEntity<Void> deleteStudentById(@PathVariable String studentId) {
            try {
                if (!studentId.isEmpty()) {
                    studentService.deleteStudent(studentId);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            } catch (EntityNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        @PostMapping("/register/{courseId}")
        public ResponseEntity<Boolean> registerForCourse(@RequestParam String studentId, @PathVariable Long courseId) {
            try {
                Boolean result = studentService.registerForCourse(studentId, courseId);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } catch (EntityNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        @PostMapping("/withdraw/{courseId}")
        public ResponseEntity<Boolean> withdrawForCourse(@RequestParam String studentId, @PathVariable Long courseId) {
            try {
                Boolean result = studentService.withdrawForCourse(studentId, courseId);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } catch (EntityNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
}
