package com.example.demo.transformer;

import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.entity.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentTransformer {

    public  Student studentRequestDtoToStudent(StudentRequestDto studentRequestDto){
        if(studentRequestDto == null){
            throw new NullPointerException("Request Dto Cannot be null !!");
        }
        return Student.builder()
                .age(studentRequestDto.getAge())
                .firstName(studentRequestDto.getFirstName())
                .lastName(studentRequestDto.getLastName())
                .rollNo(studentRequestDto.getRollNo())
                .build();
    }

    public  StudentResponseDto studentToStudentResponseDto(Student student){
        return StudentResponseDto.builder()
                .age(student.getAge())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .rollNo(student.getRollNo())
                .build();
    }
}
