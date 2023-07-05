package com.example.hotelrestapi.repository;

import com.example.hotelrestapi.entity.room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
  RoomEntity findRoomEntityByNumber(String number);
  List<RoomEntity> findRoomEntitiesBySizeBetween(int  sizeUpperLimit,int sizeLowerLimit);
  List<RoomEntity>findRoomEntitiesByPriceBetween(Double priceUpperLimit,Double priceLowerLimit);
}
