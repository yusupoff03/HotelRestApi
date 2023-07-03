package com.example.hotelrestapi.service;

import com.example.hotelrestapi.entity.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.access.expiry}")
    private long accessTokenExpiry;
    @Value("${jwt.refresh.expiry}")
    private long refreshTokenExpiry;
   public String generateAccessToken(UserEntity user){
       return Jwts.builder()
               .signWith(SignatureAlgorithm.HS512,secretKey)
               .setSubject(user.getUsername())
               .setIssuedAt(new Date())
               .setExpiration(new Date(new Date().getTime()+accessTokenExpiry))
               .addClaims( Map.of("roles",getRoles(user.getAuthorities())))
               .compact();
   }
   public String generateRefreshToken(UserEntity user){
       return Jwts.builder()
               .signWith(SignatureAlgorithm.HS512,secretKey)
               .setSubject(user.getUsername()).setIssuedAt(new Date())
               .setExpiration(new Date(new Date().getTime()+refreshTokenExpiry))
               .compact();
   }
   public Jws<Claims> extractToken(String token){
   return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
   }
   public List<String> getRoles(Collection<? extends GrantedAuthority> authorities){
    return authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
   }
}
