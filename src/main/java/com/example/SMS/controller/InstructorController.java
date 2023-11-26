package com.example.SMS.controller;

import com.example.SMS.dto.InstructorDTO;
import com.example.SMS.entity.Instructor;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.service.InstructorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping
    InstructorDTO addInstructor(@RequestBody InstructorDTO instructorDto) {
        Instructor addedInstructor = instructorService.addInstructor(instructorDto);
        InstructorDTO instructorDto_return = new InstructorDTO();
        BeanUtils.copyProperties(addedInstructor, instructorDto_return);
        instructorDto_return.setInstructorDob(addedInstructor.getInstructorDob().toString());
        return instructorDto_return;
    }

    @PutMapping("/update/{instructorId}")
    InstructorDTO updateInstructor(@RequestBody InstructorDTO instructorDto, @PathVariable Long instructorId) {
        InstructorDTO updatedInstructorDto = new InstructorDTO();
        Instructor updatedInstructor = instructorService.editInstructor(instructorDto, instructorId);
        BeanUtils.copyProperties(updatedInstructor, updatedInstructorDto);
        updatedInstructorDto.setInstructorDob(updatedInstructor.getInstructorDob().toString());
        return updatedInstructorDto;
    }

    @GetMapping
    List<InstructorDTO> getAllInstructors() {
        List<Instructor> allInstructorList = instructorService.getAllInstructors();
        List<InstructorDTO> allInstructorReturnList = new ArrayList<>();
        for (Instructor inst : allInstructorList) {
            allInstructorReturnList.add(GlobalHelper.instructorToInstructorDTO(inst));
        }
        return allInstructorReturnList;
    }

    @DeleteMapping("/delete/{instructorId}")
    void deleteInstructorById(@PathVariable Long instructorId) {
        instructorService.deleteInstructor(instructorId);
    }

    @GetMapping("/getInstructioncourseCount")
    List<Object[]> getInstructorsForCourses() {
        return instructorService.getInstructorsForCourses();
    }

}
