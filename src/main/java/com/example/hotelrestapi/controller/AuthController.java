package com.example.hotelrestapi.controller;

import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.entity.user.UserEntity;
import com.example.hotelrestapi.exception.EmailAlreadyExistsException;
import com.example.hotelrestapi.exception.RequestValidationException;
import com.example.hotelrestapi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
   private final UserService userService;
  @PostMapping(value = "/sign-up")
    public ResponseEntity<UserEntity> signUp(
          @Valid @RequestBody UserCreatDto userCreatDto,
          BindingResult bindingResult){
      if(bindingResult.hasErrors()){
        throw new RequestValidationException(bindingResult.getAllErrors());
      }
    ResponseEntity<UserEntity> user= userService.signUp(userCreatDto);
      if(user.getStatusCode().value()==422){
        throw new EmailAlreadyExistsException("This Email already exists");
      }
      return ResponseEntity.ok(user.getBody());
  }

}
