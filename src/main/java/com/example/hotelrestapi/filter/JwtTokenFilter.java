package com.example.hotelrestapi.filter;

import com.example.hotelrestapi.service.AuthenticationService;
import com.example.hotelrestapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private AuthenticationService authenticationService;
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
      String token=request.getHeader("authentication");
      if(token==null||!token.startsWith("Bearer ")){
          filterChain.doFilter(request,response);
      }
      token=token.substring(7);
        Jws<Claims> claims=jwtService.extractToken(token);
        authenticationService.authenticate(claims.getBody(),request);
        filterChain.doFilter(request,response);
    }
}
