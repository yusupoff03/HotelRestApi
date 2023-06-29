package com.example.hotelrestapi.service.user;

import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.entity.user.UserEntity;
import com.example.hotelrestapi.exception.EmailAlreadyExistsException;
import com.example.hotelrestapi.exception.RequestValidationException;
import com.example.hotelrestapi.repository.UserRepository;
import com.example.hotelrestapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<UserEntity> signUp(UserCreatDto userCreatDto) {
     UserEntity user=modelMapper.map(userCreatDto,UserEntity.class);
     user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userEntityByEMail = userRepository.findUserEntityByEMail(user.getEMail());
        if (userEntityByEMail!=null){
            UserEntity save = userRepository.save(user);
            return ResponseEntity.ok(save);
        }
        return ResponseEntity.status(422).body(new UserEntity());
    }
}
