package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {
    private String firstName;
    private String lastName;
    private int age;
    private String rollNo;
}
