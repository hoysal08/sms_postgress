package com.example.SMS.repository;

import com.example.SMS.entity.Instructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends CrudRepository<Instructor, Long> {

    @Query("SELECT c.courseName,c.courseId, i.instructorName FROM Course c JOIN c.instructor i")
    List<Object[]> getInstructorsForCourses();
}
