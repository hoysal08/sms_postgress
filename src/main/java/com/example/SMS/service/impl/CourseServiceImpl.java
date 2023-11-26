package com.example.SMS.service.impl;

import com.example.SMS.dto.CourseDTO;
import com.example.SMS.entity.Course;
import com.example.SMS.entity.CourseStatus;
import com.example.SMS.entity.Instructor;
import com.example.SMS.entity.Student;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.repository.CourseRepository;
import com.example.SMS.service.CourseService;
import com.example.SMS.service.InstructorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    @Lazy
    InstructorService instructorService;

    @Override
    public Course addCourse(CourseDTO courseDTO) {
        Course course = new Course();
        Instructor courseInstructor = null;
        BeanUtils.copyProperties(courseDTO, course);
        if (courseDTO.getInstructorID() != null) {
            courseInstructor = instructorService.getInstructorById(courseDTO.getInstructorID());
            course.setInstructor(courseInstructor);
        }
        courseRepository.save(course);
        return course;
    }

    @Override
    public Course editCourse(CourseDTO courseDTO, Long courseId) {
        Optional<Course> orginalCourse = courseRepository.findById(courseId);
        if (orginalCourse.isPresent()) {
            Course ogCourse = orginalCourse.get();
            BeanUtils.copyProperties(courseDTO, ogCourse);
            Instructor courseInstructor = instructorService.getInstructorById(courseDTO.getInstructorID());
            if (courseInstructor != null) {
                ogCourse.setInstructor(courseInstructor);
                ogCourse.setCourseId(courseId);
                courseRepository.save(ogCourse);
                return ogCourse;
            }
        }
        return null;
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> getAllCourse() {
        Iterable<Course> allCourses = courseRepository.findAll();
        return GlobalHelper.iterableToList(allCourses);
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    @Override
    public Boolean registerForCourse(Long instructorId, Long courseId) {
        Instructor instructor = instructorService.getInstructorById(instructorId);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        course.setInstructor(instructor);
        courseRepository.save(course);
        return Boolean.TRUE;
    }

    @Override
    public Boolean withdrawForCourse(Long instructorId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (course.getInstructor().getInstructorId() != null && course.getInstructor().getInstructorId() == instructorId) {
            course.setInstructor(null);
            courseRepository.save(course);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void setStatus(CourseStatus status, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        course.setCourseStatus(status);
        courseRepository.save(course);
    }

    @Override
    public CourseStatus getStatus(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        return course.getCourseStatus();
    }

    @Override
    public List<Object[]> countStudentsInCourses() {
        List<Object[]> studentCounts = courseRepository.countStudentsInCourses();

        return studentCounts;
    }

    @Override
    public List<Object[]> getCourseDetailsByCourseId(Long courseId) {
        List<Object[]> result = courseRepository.getCourseDetailsByCourseId(courseId);
        return result;
    }

    @Override
    public List<String> getStudentsByCourseStatus(CourseStatus courseStatus) {
        List<String> result = courseRepository.getStudentsByCourseStatus(courseStatus);
        return result;
    }

}
