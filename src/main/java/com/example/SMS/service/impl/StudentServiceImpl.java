package com.example.SMS.service.impl;

import com.example.SMS.CustomExceptions.StudentRegistrationException;
import com.example.SMS.CustomExceptions.StudentWithdrawalException;
import com.example.SMS.dto.StudentDTO;
import com.example.SMS.entity.Course;
import com.example.SMS.entity.Student;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.repository.StudentRepository;
import com.example.SMS.service.CourseService;
import com.example.SMS.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseService courseService;


    @Override
    public Student addStudent(StudentDTO student) {
        Student studentInstance = new Student();
        studentInstance.setStudentName(student.getStudentName());
        LocalDate dob = GlobalHelper.parseLocalDate(student.getStudentDob(), "yyyy-MM-dd");
        studentInstance.setStudentDob(dob);
        return studentRepository.save(studentInstance);
    }

    @Override
    public Student editStudent(StudentDTO student, String studentId) {
        Optional<Student> originalStudent = studentRepository.findById(studentId);
        if (originalStudent.isPresent()) {
            Student ogStudent = originalStudent.get();
            BeanUtils.copyProperties(student, ogStudent);
            ogStudent.setStudentId(studentId);
            ogStudent.setStudentDob(GlobalHelper.parseLocalDate(student.getStudentDob(), "yyyy-MM-dd"));

            studentRepository.save(ogStudent);
            return ogStudent;
        } else {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }
    }

    @Override
    public void deleteStudent(String studentId) {

        try {
            studentRepository.deleteById(studentId);
        } catch (Exception e) {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }

    }

    @Override
    public List<Student> getAllStudents() {
        Iterable<Student> allStudents = studentRepository.findAll();
        return GlobalHelper.iterableToList(allStudents);
    }

    @Override
    public Boolean registerForCourse(String studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

        Course course = courseService.getCourseById(courseId);

        if (course == null) {
            throw new EntityNotFoundException("Course not found with ID: " + courseId);
        }

        if (student.getCourses().contains(course)) {
            throw new StudentRegistrationException("Student is already registered for the course.");
        }

        Set<Course> coursesToRegister = new HashSet<>();
        coursesToRegister.add(course);
        student.getCourses().addAll(coursesToRegister);
        studentRepository.save(student);
        return true;
    }

    @Override
    public Boolean withdrawForCourse(String studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

        Course course = courseService.getCourseById(courseId);

        if (course == null) {
            throw new EntityNotFoundException("Course not found with ID: " + courseId);
        }

        if (!student.getCourses().contains(course)) {
            throw new StudentWithdrawalException("Student is not registered for the course.");
        }

        boolean deleted = student.getCourses().remove(course);

        if (deleted) {
            studentRepository.save(student);
        }

        return deleted;
    }


    public long getTotalStudentCount() {
        return studentRepository.count();
    }
}
