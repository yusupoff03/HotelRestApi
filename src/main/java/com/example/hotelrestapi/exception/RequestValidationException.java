package com.example.hotelrestapi.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class RequestValidationException extends RuntimeException{
    String message;
    public RequestValidationException(List<ObjectError> errors){
    StringBuilder allErrors=new StringBuilder();
        for (ObjectError error : errors) {
            allErrors.append(error.getDefaultMessage()).append("\n");
        }
        this.message=allErrors.toString();
    }
    @Override
    public String getMessage() {
        return message;
    }
}
