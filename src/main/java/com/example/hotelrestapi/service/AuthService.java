package com.example.hotelrestapi.service;

import com.example.hotelrestapi.entity.user.UserEntity;
import com.example.hotelrestapi.exception.DataNotFoundException;
import com.example.hotelrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntityByEMail = userRepository.findUserEntityByEMail(username);
        if(userEntityByEMail==null){
            throw new DataNotFoundException("User not found");
        }
        return userEntityByEMail;
    }
}
