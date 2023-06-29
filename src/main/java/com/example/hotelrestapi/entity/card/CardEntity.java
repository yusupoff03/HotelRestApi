package com.example.hotelrestapi.entity.card;

import com.example.hotelrestapi.entity.BaseEntity;
import com.example.hotelrestapi.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardEntity extends BaseEntity {
    @Column(unique = true,nullable = false)
    private String number;
    private LocalDateTime expiryDate;
    private Double balance=0D;
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity user;
    @Enumerated(EnumType.STRING)
    private CardType type;
}
