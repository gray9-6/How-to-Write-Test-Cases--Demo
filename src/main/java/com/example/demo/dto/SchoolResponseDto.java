package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolResponseDto {
    private String name;
    private String address;
}
