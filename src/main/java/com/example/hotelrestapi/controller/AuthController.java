package com.example.hotelrestapi.controller;

import com.example.hotelrestapi.dto.LoginDto;
import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.dto.response.JwtResponse;
import com.example.hotelrestapi.entity.user.UserEntity;
import com.example.hotelrestapi.exception.EmailAlreadyExistsException;
import com.example.hotelrestapi.exception.RequestValidationException;
import com.example.hotelrestapi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<UserEntity> signUp(
            @Valid @RequestBody UserCreatDto userCreatDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        ResponseEntity<UserEntity> user = userService.signUp(userCreatDto);
        if (user.getStatusCode().value() == 422) {
            throw new EmailAlreadyExistsException("This Email already exists");
        }
        return ResponseEntity.status(201).body(user.getBody());
    }

    @GetMapping(value = "/sign-in")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody LoginDto loginDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return ResponseEntity.status(302).body(userService.signIn(loginDto));
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<JwtResponse> refreshAccessToken(
            Principal principal) {
        return ResponseEntity.ok(userService.getNewAccessToken(principal));
    }

}
