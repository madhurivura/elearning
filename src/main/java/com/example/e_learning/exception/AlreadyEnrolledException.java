package com.example.e_learning.exception;

public class AlreadyEnrolledException extends RuntimeException{
    public AlreadyEnrolledException(String message){
        super(message);
    }
}
