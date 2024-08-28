package com.example.demo.exception;

public class InvalidSchoolId extends RuntimeException{

    public InvalidSchoolId(String message){
        super(message);
    }
}
