package com.example.hotelrestapi.service.room;

import com.example.hotelrestapi.dto.RoomCreatDto;
import com.example.hotelrestapi.entity.room.RoomEntity;

import java.util.List;

public interface RoomService {
    List<RoomEntity> getAll(int page, int size);

    List<RoomEntity> getAllBySort(int page, int size, boolean sortByNumber, boolean sortByPrice, boolean sortBySize);

    RoomEntity addRoom(RoomCreatDto roomCreatDto);
}
