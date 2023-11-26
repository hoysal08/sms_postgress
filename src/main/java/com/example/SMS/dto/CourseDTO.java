package com.example.SMS.dto;

import com.example.SMS.entity.CourseStatus;
import lombok.Data;


@Data
public class CourseDTO {
    private Long courseId;

    private String courseName;
    private Long courseFee;
    private CourseStatus courseStatus;
    private Long instructorID;
}
