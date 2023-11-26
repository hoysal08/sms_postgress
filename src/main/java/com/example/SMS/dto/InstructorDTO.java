package com.example.SMS.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InstructorDTO {
    private Long instructorId;
    private String instructorName;
    private String instructorDob;
}
