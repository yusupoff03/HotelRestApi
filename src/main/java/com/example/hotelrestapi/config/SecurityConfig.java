package com.example.hotelrestapi.config;

import com.example.hotelrestapi.filter.JwtTokenFilter;
import com.example.hotelrestapi.service.AuthService;
import com.example.hotelrestapi.service.AuthenticationService;
import com.example.hotelrestapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
            .authorizeHttpRequests((authorizer)->{
        authorizer.requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated();
    }).sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtTokenFilter(authenticationService,jwtService),
                    UsernamePasswordAuthenticationFilter.class)
            .build();
    }


 @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
     AuthenticationManagerBuilder authenticationManagerBuilder=security
             .getSharedObject(AuthenticationManagerBuilder.class);
     authenticationManagerBuilder.userDetailsService(authService).passwordEncoder(passwordEncoder);
     return authenticationManagerBuilder.build();
 }
}
