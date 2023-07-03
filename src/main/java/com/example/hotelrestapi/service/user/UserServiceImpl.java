package com.example.hotelrestapi.service.user;

import com.example.hotelrestapi.dto.LoginDto;
import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.dto.response.JwtResponse;
import com.example.hotelrestapi.entity.user.UserEntity;
import com.example.hotelrestapi.exception.DataNotFoundException;
import com.example.hotelrestapi.exception.EmailAlreadyExistsException;
import com.example.hotelrestapi.repository.UserRepository;
import com.example.hotelrestapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<UserEntity> signUp(UserCreatDto userCreatDto) {
        UserEntity user = modelMapper.map(userCreatDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<UserEntity> userEntityByEMail = userRepository.findUserEntityByEmail(userCreatDto.getEmail());
        if(userEntityByEMail.isPresent()) {
            throw new EmailAlreadyExistsException("This email already exists");
        }
        UserEntity save = userRepository.save(user);
        return ResponseEntity.ok(save);
    }

    @Override
    public JwtResponse signIn(LoginDto loginDto) {
        UserEntity user = userRepository.findUserEntityByEmail(loginDto.getEMail()).orElseThrow(
                ()->new DataNotFoundException("User not found"));
        if (passwordEncoder.matches(loginDto.getPassword(),user.getPassword())) {
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return new JwtResponse(accessToken, refreshToken);
        }
        throw new DataNotFoundException("User not found");
    }

    @Override
    public JwtResponse getNewAccessToken(Principal principal) {
        UserEntity user = userRepository.findUserEntityByEmail(principal.getName())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        String accessToken=jwtService.generateAccessToken(user);
        return JwtResponse.builder().accessToken(accessToken).build();
    }
}
