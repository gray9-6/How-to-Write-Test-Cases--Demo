package com.example.demo.transformer;

import com.example.demo.dto.SchoolRequestDto;
import com.example.demo.dto.SchoolResponseDto;
import com.example.demo.entity.School;

public class SchoolTransformer {

    public static School schoolRequestDtoToSchool(SchoolRequestDto schoolRequestDto){
        return School.builder()
                .name(schoolRequestDto.getName())
                .address(schoolRequestDto.getAddress())
                .build();
    }

    public static SchoolResponseDto schoolToSchoolResponseDto(School school){
        return SchoolResponseDto.builder()
                .name(school.getName())
                .address(school.getAddress())
                .build();
    }
}
