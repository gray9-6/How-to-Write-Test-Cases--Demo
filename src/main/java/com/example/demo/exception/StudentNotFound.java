package com.example.demo.exception;

public class StudentNotFound extends RuntimeException {

    public StudentNotFound(String message){
        super(message);
    }
}
