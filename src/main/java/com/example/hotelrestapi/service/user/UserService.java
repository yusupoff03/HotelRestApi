package com.example.hotelrestapi.service.user;

import com.example.hotelrestapi.dto.LoginDto;
import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.dto.response.JwtResponse;
import com.example.hotelrestapi.entity.user.UserEntity;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface UserService {
   ResponseEntity<UserEntity> signUp(UserCreatDto userCreatDto);
   JwtResponse signIn(LoginDto loginDto);

   JwtResponse getNewAccessToken(Principal principal);

    List<UserEntity> findAll(int page, int size);

    Boolean blockUser(UUID userId);
}
