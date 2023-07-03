package com.example.hotelrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {
    @NotBlank(message = "eMail cannot be blank")
    @NotNull(message = "eMail cannot be null")
    private String eMail;
    @NotBlank(message = "password cannot be blank")
    @NotNull(message = "password cannot be null")
    private String password;
}
