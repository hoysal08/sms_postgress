package com.example.SMS.repository;

import com.example.SMS.entity.Course;
import com.example.SMS.entity.CourseStatus;
import com.example.SMS.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("SELECT c.courseName, COUNT(s) FROM Course c LEFT JOIN c.students s GROUP BY c")
    List<Object[]> countStudentsInCourses();

    //Serilization Error
    @Query("SELECT c.courseName, s.studentName, i.instructorName FROM Course c LEFT JOIN c.students s LEFT JOIN c.instructor i WHERE c.courseId = :courseId")
    List<Object[]> getCourseDetailsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT s.studentName FROM Student s JOIN s.courses c WHERE c.courseStatus = :courseStatus")
    List<String> getStudentsByCourseStatus(@Param("courseStatus") CourseStatus courseStatus);

}
