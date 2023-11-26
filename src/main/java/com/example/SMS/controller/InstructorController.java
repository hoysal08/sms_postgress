package com.example.SMS.controller;

import com.example.SMS.CustomExceptions.EntityNotFoundException;
import com.example.SMS.dto.InstructorDTO;
import com.example.SMS.entity.Instructor;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.service.InstructorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorDTO> addInstructor(@RequestBody InstructorDTO instructorDto) {
        try {
            Instructor addedInstructor = instructorService.addInstructor(instructorDto);
            InstructorDTO instructorDto_return = new InstructorDTO();
            BeanUtils.copyProperties(addedInstructor, instructorDto_return);
            instructorDto_return.setInstructorDob(addedInstructor.getInstructorDob().toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(instructorDto_return);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{instructorId}")
    public ResponseEntity<InstructorDTO> updateInstructor(@RequestBody InstructorDTO instructorDto, @PathVariable Long instructorId) {
        try {
            InstructorDTO updatedInstructorDto = new InstructorDTO();
            Instructor updatedInstructor = instructorService.editInstructor(instructorDto, instructorId);
            BeanUtils.copyProperties(updatedInstructor, updatedInstructorDto);
            updatedInstructorDto.setInstructorDob(updatedInstructor.getInstructorDob().toString());
            return ResponseEntity.status(HttpStatus.OK).body(updatedInstructorDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        List<Instructor> allInstructorList = instructorService.getAllInstructors();
        List<InstructorDTO> allInstructorReturnList = new ArrayList<>();
        for (Instructor inst : allInstructorList) {
            allInstructorReturnList.add(GlobalHelper.instructorToInstructorDTO(inst));
        }
        return ResponseEntity.status(HttpStatus.OK).body(allInstructorReturnList);
    }

    @DeleteMapping("/delete/{instructorId}")
    public ResponseEntity<Void> deleteInstructorById(@PathVariable Long instructorId) {
        try {
            instructorService.deleteInstructor(instructorId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getInstructioncourseCount")
    public ResponseEntity<List<Object[]>> getInstructorsForCourses() {
        List<Object[]> instructors = instructorService.getInstructorsForCourses();
        return ResponseEntity.status(HttpStatus.OK).body(instructors);
    }
}
