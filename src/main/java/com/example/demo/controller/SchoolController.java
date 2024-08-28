package com.example.demo.controller;

import com.example.demo.dto.SchoolRequestDto;
import com.example.demo.dto.SchoolResponseDto;
import com.example.demo.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping("/add-school")
    public ResponseEntity<SchoolResponseDto> addSchool(@RequestBody SchoolRequestDto schoolRequestDto){
        SchoolResponseDto schoolResponseDto = schoolService.addSchool(schoolRequestDto);
        return new ResponseEntity<>(schoolResponseDto, HttpStatus.OK);
    }
}
