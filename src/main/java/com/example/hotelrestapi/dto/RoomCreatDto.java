package com.example.hotelrestapi.dto;

import com.example.hotelrestapi.entity.room.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomCreatDto {
    @NotBlank(message = "Number cannot be blank")
    @NotNull(message = "Number cannot be null")
    private String number;
    @NotBlank(message = "Size cannot be blank")
    @NotNull(message = "Size cannot be null")
    private Integer size;
    @NotBlank(message = "Price cannot be blank")
    @NotNull(message = "Price cannot be null")
    private Double price;
    @NotBlank(message = "Type cannot be blank")
    @NotNull(message = "Type cannot be null")
    private RoomType type;
    private Boolean hasMonitor=false;
}
