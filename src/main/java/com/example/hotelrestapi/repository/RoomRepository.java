package com.example.hotelrestapi.repository;

import com.example.hotelrestapi.entity.room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
}
