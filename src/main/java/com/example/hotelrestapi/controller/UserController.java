package com.example.hotelrestapi.controller;

import com.example.hotelrestapi.entity.user.UserEntity;
import com.example.hotelrestapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final UserService userService;
    @GetMapping(value = "/get-users")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getAllUsers(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(userService.findAll(page,size));
    }
    @PutMapping(value = "/block-user")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Boolean>blockUser(@RequestParam UUID userId){
        return ResponseEntity.ok(userService.blockUser(userId));
    }
}
