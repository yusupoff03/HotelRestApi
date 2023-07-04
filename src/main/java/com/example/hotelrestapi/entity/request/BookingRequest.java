package com.example.hotelrestapi.entity.request;

import com.example.hotelrestapi.entity.BaseEntity;
import com.example.hotelrestapi.entity.room.RoomEntity;
import com.example.hotelrestapi.entity.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "request")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingRequest extends BaseEntity {
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private RoomEntity room;
    private Double price;
    private RequestStatus status;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
}
