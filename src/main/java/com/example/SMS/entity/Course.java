package com.example.SMS.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseName;
    private Long courseFee;
    private CourseStatus courseStatus;

    // Other course attributes

    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}

