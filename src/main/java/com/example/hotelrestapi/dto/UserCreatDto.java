package com.example.hotelrestapi.dto;

import com.example.hotelrestapi.entity.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreatDto {
    private String name;
    @NotBlank(message = "EMail cannot be blank")
    @NotNull(message = "EMail cannot be null")
    private String eMail;
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;
    @NotBlank(message = "Age cannot be blank")
    @NotNull(message = "Age cannot be null")
    private int age;
    private List<UserRole> roles;
}
