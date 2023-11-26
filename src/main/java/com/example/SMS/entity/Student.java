package com.example.SMS.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = Student.TABLE_NAME)
@Getter
@Setter
public class Student {

    public static final String TABLE_NAME = "student";
    public static final String SEQ_GEN_ALIAS = "seq_gen_alias";
    public static final String SEQ_GEN_STRATEGY = "uuid2";

    @Id
    @GeneratedValue(generator = Student.SEQ_GEN_ALIAS)
    @GenericGenerator(name = Student.SEQ_GEN_ALIAS, strategy = Student.SEQ_GEN_STRATEGY)
    private String studentId;

    private String studentName;
    private LocalDate studentDob;

    // Other student attributes

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    )
    private List<Course> courses;
}
