package com.example.demo.service;

import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import com.example.demo.exception.InvalidSchoolId;
import com.example.demo.exception.StudentNotFound;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.transformer.StudentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {


    private final StudentRepository studentRepository;
    private final StudentTransformer studentTransformer;
    private final SchoolRepository schoolRepository;


    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) throws Exception{
        Student student = studentTransformer.studentRequestDtoToStudent(studentRequestDto);
        School school = schoolRepository.findById(studentRequestDto.getSchoolId())
                .orElseThrow(()-> new InvalidSchoolId("School not found with this school id :- " + studentRequestDto.getSchoolId()));

        student.setSchool(school);
        Student savedStudent = studentRepository.save(student);

        // set the student in school list also
        school.getStudentList().add(student);
        schoolRepository.save(school);

        return studentTransformer.studentToStudentResponseDto(savedStudent);
    }

    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentTransformer::studentToStudentResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDto getStudentById(Long id) {
        return studentRepository.findById(id).map(studentTransformer::studentToStudentResponseDto)
                .orElseThrow(()-> new StudentNotFound("Student Not Found"));
    }

    public List<StudentResponseDto> getStudentByName(String name) {
        return studentRepository.findAllStudentByFirstNameContaining(name)
                .stream().map(studentTransformer::studentToStudentResponseDto)
                .collect(Collectors.toList());
    }
}
