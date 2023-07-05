package com.example.hotelrestapi.service.user;

import com.example.hotelrestapi.dto.LoginDto;
import com.example.hotelrestapi.dto.UserCreatDto;
import com.example.hotelrestapi.dto.response.JwtResponse;
import com.example.hotelrestapi.entity.request.BookingRequest;
import com.example.hotelrestapi.entity.request.RequestStatus;
import com.example.hotelrestapi.entity.user.UserEntity;
import com.example.hotelrestapi.exception.DataNotFoundException;
import com.example.hotelrestapi.exception.EmailAlreadyExistsException;
import com.example.hotelrestapi.repository.RequestRepository;
import com.example.hotelrestapi.repository.UserRepository;
import com.example.hotelrestapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RequestRepository requestRepository;

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
            if(!user.getIsBlocked()) {
                String accessToken = jwtService.generateAccessToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);
                return new JwtResponse(accessToken, refreshToken);
            }
            throw new DataNotFoundException("You are blocked user");
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

    @Override
    public List<UserEntity> findAll(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        List<UserEntity> content = userRepository.findAll(pageable).getContent();
        if(content.isEmpty()){
            throw new DataNotFoundException("Users not found");
        }
        return content;
    }

    @Override
    public Boolean blockUser(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        if(!user.getIsBlocked()||user.getIsBlocked()==null){
            List<BookingRequest> bookingRequestsByUser = requestRepository.findBookingRequestsByUser(user);
            List<BookingRequest>bookingRequests=new ArrayList<>();
            for (BookingRequest bookingRequest : bookingRequestsByUser) {
                if(bookingRequest.getStatus().equals(RequestStatus.CANCELED)){
                    bookingRequests.add(bookingRequest);
                }
            }
            if(bookingRequests.size()<10){
                return false;
            }
            user.setIsBlocked(true);
            userRepository.save(user);
            return true;
        }
        return true;
    }

    @Override
    public Boolean unBlockUser(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        if(user.getIsBlocked()){
            user.setIsBlocked(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
