package com.example.hotelrestapi.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    public void authenticate(Claims claims, HttpServletRequest httpServletRequest){
    List<String> roles= (List<String>) claims.get("roles");
    String username=claims.getSubject();

        UsernamePasswordAuthenticationToken authentication=
                new UsernamePasswordAuthenticationToken(
                        username,null,
                        getRoles(roles));
     authentication.setDetails(new WebAuthenticationDetailsSource());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  private List<SimpleGrantedAuthority> getRoles(List<String> roles){
      return roles
              .stream()
              .map(SimpleGrantedAuthority::new)
              .toList();
  }
}
