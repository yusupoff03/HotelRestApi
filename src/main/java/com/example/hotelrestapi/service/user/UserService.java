package com.example.hotelrestapi.service.user;

import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.entity.user.UserEntity;
import org.springframework.http.ResponseEntity;

public interface UserService {
   ResponseEntity<UserEntity> signUp(UserCreatDto userCreatDto);
}
