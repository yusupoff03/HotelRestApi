package com.example.hotelrestapi.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
  private String accessToken;
  private String refreshToken;
}
