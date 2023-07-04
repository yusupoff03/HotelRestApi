package com.example.hotelrestapi.entity.room;

import com.example.hotelrestapi.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity(name = "room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomEntity extends BaseEntity {
    @Column(unique = true)
    private String number;
    private Integer size;
    private Double price;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    private Boolean hasMonitor;
}
