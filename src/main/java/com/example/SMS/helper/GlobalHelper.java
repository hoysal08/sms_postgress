package com.example.SMS.helper;

import com.example.SMS.dto.CourseDTO;
import com.example.SMS.dto.InstructorDTO;
import com.example.SMS.dto.StudentDTO;
import com.example.SMS.entity.Course;
import com.example.SMS.entity.Instructor;
import com.example.SMS.entity.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GlobalHelper {

    public static LocalDate parseLocalDate(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // Handle parsing exceptions here
            throw new IllegalArgumentException("Invalid date format or value: " + dateString, e);
        }
    }


    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public static StudentDTO studentToStudentDTO(Student student) {
        StudentDTO updatedStudentDto = new StudentDTO();
        BeanUtils.copyProperties(student, updatedStudentDto);
        updatedStudentDto.setStudentDob(student.getStudentDob().toString());
        return updatedStudentDto;
    }

    public static InstructorDTO instructorToInstructorDTO(Instructor instructor) {
        InstructorDTO instructorDto = new InstructorDTO();
        BeanUtils.copyProperties(instructor, instructorDto);
        instructorDto.setInstructorDob(instructor.getInstructorDob().toString());
        return instructorDto;
    }

    public static CourseDTO courseToCourseDto(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        Instructor courseInstructor = course.getInstructor();
        if (courseInstructor != null) {
            courseDTO.setInstructorID(courseInstructor.getInstructorId());
        }

        return courseDTO;
    }
}
