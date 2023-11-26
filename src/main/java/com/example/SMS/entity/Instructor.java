package com.example.SMS.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    private String instructorName;
    private LocalDate instructorDob;

    @OneToOne(mappedBy = "instructor",fetch = FetchType.LAZY)
    private Course course;
}

