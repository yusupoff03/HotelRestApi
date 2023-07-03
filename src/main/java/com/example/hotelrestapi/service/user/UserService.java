package com.example.hotelrestapi.service.user;

import com.example.hotelrestapi.dto.LoginDto;
import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.dto.response.JwtResponse;
import com.example.hotelrestapi.entity.user.UserEntity;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface UserService {
   ResponseEntity<UserEntity> signUp(UserCreatDto userCreatDto);
   JwtResponse signIn(LoginDto loginDto);

   JwtResponse getNewAccessToken(Principal principal);
}
