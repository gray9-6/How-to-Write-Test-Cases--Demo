package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {

    private String firstName;
    private String lastName;
    private int age;
    private String rollNo;

    private Long schoolId;

}
