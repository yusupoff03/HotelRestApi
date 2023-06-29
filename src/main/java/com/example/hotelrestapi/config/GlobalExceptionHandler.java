package com.example.hotelrestapi.config;

import com.example.hotelrestapi.exception.DataNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<String> dataNotFoundExceptionHandler(
            DataNotFoundException e){
      return ResponseEntity.status(404).body(e.getMessage());
  }
}
