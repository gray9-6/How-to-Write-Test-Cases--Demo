package com.example.demo.controller;

import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody StudentRequestDto student){
        try {
            StudentResponseDto savedStudent = studentService.addStudent(student);
            return new ResponseEntity<>(savedStudent, HttpStatus.OK);
        }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getStudents")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents(){
        List<StudentResponseDto> students = studentService.getAllStudents();
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/getStudentById")
    public ResponseEntity<StudentResponseDto> getStudentById(Long id){
        StudentResponseDto students = studentService.getStudentById(id);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/getStudentByFirstName/{name}")
    public ResponseEntity<List<StudentResponseDto>> getStudentByName(@PathVariable String name){
        List<StudentResponseDto> responseDtoList = studentService.getStudentByName(name);
        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }
}
