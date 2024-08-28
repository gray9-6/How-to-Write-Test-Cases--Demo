package com.example.demo.service;

import com.example.demo.dto.SchoolRequestDto;
import com.example.demo.dto.SchoolResponseDto;
import com.example.demo.entity.School;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.transformer.SchoolTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolResponseDto addSchool(SchoolRequestDto schoolRequestDto){
        School school = SchoolTransformer.schoolRequestDtoToSchool(schoolRequestDto);
        School savedSchool = schoolRepository.save(school);
        return SchoolTransformer.schoolToSchoolResponseDto(savedSchool);
    }
}
