package com.example.SMS.dto;

import lombok.Data;


@Data
public class CourseDTO {
    private Long courseId;

    private String courseName;
    private Long courseFee;
    private Long instructorID;
}
