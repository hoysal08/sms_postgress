package com.example.SMS.service.impl;

import com.example.SMS.CustomExceptions.EntityNotFoundException;
import com.example.SMS.dto.InstructorDTO;
import com.example.SMS.entity.Instructor;
import com.example.SMS.helper.GlobalHelper;
import com.example.SMS.repository.InstructorRepository;
import com.example.SMS.service.InstructorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    InstructorRepository instructorRepository;


    @Override
    public Instructor addInstructor(InstructorDTO instructorDto) {
        Instructor instructor = new Instructor();
        instructor.setInstructorName(instructorDto.getInstructorName());
        instructor.setInstructorDob(GlobalHelper.parseLocalDate(instructorDto.getInstructorDob(), "yyyy-MM-dd"));
        instructorRepository.save(instructor);
        return instructor;
    }

    @Override
    public Instructor editInstructor(InstructorDTO instructorDto, Long instructorId) {
        Optional<Instructor> originalInstructor = instructorRepository.findById(instructorId);
        if (originalInstructor.isPresent()) {
            Instructor ogInstructor = originalInstructor.get();
            BeanUtils.copyProperties(instructorDto, ogInstructor);
            ogInstructor.setInstructorId(instructorId);
            ogInstructor.setInstructorDob(GlobalHelper.parseLocalDate(instructorDto.getInstructorDob(), "yyyy-MM-dd"));

            return instructorRepository.save(ogInstructor);
        } else {
            throw new EntityNotFoundException("Instructor not found with ID: " + instructorId);
        }
    }

    @Override
    public void deleteInstructor(Long instructorId) {
        try {
            instructorRepository.deleteById(instructorId);
        } catch (Exception e) {
            throw new EntityNotFoundException("Instructor not found with ID: " + instructorId);
        }
    }

    @Override
    public List<Instructor> getAllInstructors() {
        Iterable<Instructor> allInstructors = instructorRepository.findAll();
        return GlobalHelper.iterableToList(allInstructors);
    }

    public Instructor getInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with ID: " + instructorId));
    }

    @Override
    public List<Object[]> getInstructorsForCourses() {
        return instructorRepository.getInstructorsForCourses();
    }

    @Override
    public long getTotalStudentCount() {
        return instructorRepository.count();
    }

}
