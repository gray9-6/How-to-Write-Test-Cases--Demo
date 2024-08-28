package com.example.demo.transformer;

import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTransformerTest {  // test class name

    // first declare the class/service/controller which you want to test
    private StudentTransformer studentTransformer;

    // thirdly, now initialize the class,
    @BeforeEach
    void setUp() {
        studentTransformer = new StudentTransformer();
    }

    // secondly , create the test method ,for which you want to write the test cases
    @Test
    void shouldMapStudentDtoToStudent(){
        // now create the object of the Student request Dto
        StudentRequestDto requestDto = new StudentRequestDto(
                "Ajay",
                "Yadav",
                27,
                "B.TechMe005",
                1L
        );

        // now call the transformer method,
        Student student = studentTransformer.studentRequestDtoToStudent(requestDto);

        // asserts
        assertEquals(requestDto.getFirstName(),student.getFirstName());
        assertEquals(requestDto.getLastName(),student.getLastName());
        assertEquals(requestDto.getAge(),student.getAge());
    }

    @Test
    void should_throw_nullPointerException_when_studentDto_is_null(){
        var exp = assertThrows(NullPointerException.class,()-> studentTransformer.studentRequestDtoToStudent(null));
        assertEquals("Request Dto Cannot be null !!",exp.getMessage());
    }

    @Test
    void shouldMapStudentToStudentResponseDto(){
        // create the object of the student
        Student student = Student.builder().firstName("Ajay").lastName("Yadav").age(27).rollNo("B.TechMe005").build();

        // now call the student transformer method
        StudentResponseDto studentResponseDto = studentTransformer.studentToStudentResponseDto(student);

        // asserts
        assertEquals(studentResponseDto.getFirstName(),student.getFirstName());
        assertEquals(studentResponseDto.getLastName(),student.getLastName());
        assertEquals(studentResponseDto.getAge(),student.getAge());
    }

}


/*
* 1. first declare the class/service/controller which you want to test
* 2. create the test method, for which you want to write the test case
* 3. initialize the classes for which you want to write the test cases, using before each
* */