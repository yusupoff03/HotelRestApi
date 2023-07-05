package com.example.hotelrestapi.service.room;

import com.example.hotelrestapi.dto.RoomCreatDto;
import com.example.hotelrestapi.entity.room.RoomEntity;
import com.example.hotelrestapi.exception.DataNotFoundException;
import com.example.hotelrestapi.exception.EmailAlreadyExistsException;
import com.example.hotelrestapi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoomEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomEntity> all = roomRepository.findAll(pageable);
        if (all.getTotalElements() == 0) {
            throw new DataNotFoundException("Room not found");
        }
        return all.getContent().stream().toList();
    }

    @Override
    public List<RoomEntity> getAllBySort(int page, int size, boolean sortByNumber
            , boolean sortByPrice, boolean sortBySize) {
        Pageable pageable;
        Sort sort;

        if (sortByNumber) {
            sort = Sort.by(Sort.Direction.ASC, "number");
        } else if (sortByPrice) {
            sort = Sort.by(Sort.Direction.ASC, "price");
        } else if (sortBySize) {
            sort = Sort.by(Sort.Direction.ASC, "size");
        } else {
            throw new IllegalArgumentException("No valid sorting parameter provided");
        }

        pageable = PageRequest.of(page, size, sort);
        Page<RoomEntity> all = roomRepository.findAll(pageable);

        if (all.getTotalElements() == 0) {
            throw new DataNotFoundException("Room not found");
        }

        return all.getContent();
    }

    @Override
    public RoomEntity addRoom(RoomCreatDto roomCreatDto) {
        RoomEntity room = modelMapper.map(roomCreatDto, RoomEntity.class);
        RoomEntity room1 = roomRepository.findRoomEntityByNumber(room.getNumber());
        if (room1 != null) {
            throw new EmailAlreadyExistsException("This room number already exists");
        }
        return roomRepository.save(room);
    }

    @Override
    public RoomEntity editRoom(RoomCreatDto roomCreatDto, UUID roomId) {
        RoomEntity room=modelMapper.map(roomCreatDto,RoomEntity.class);
        roomRepository.findById(roomId).orElseThrow(
                ()->new DataNotFoundException("Room not found"));
        room.setId(roomId);
        return roomRepository.save(room);
    }

    @Override
    public Boolean deleteById(UUID roomId) {
        roomRepository.findById(roomId)
                .orElseThrow(()->new DataNotFoundException("Room not found"));
        roomRepository.deleteById(roomId);
        return true;
    }

    @Override
    public List<RoomEntity> getBySize(int size) {
        List<RoomEntity> roomEntitiesBySizeBetween =
                roomRepository.findRoomEntitiesBySizeBetween(size + 5, size - 5);
        if(roomEntitiesBySizeBetween.isEmpty()){
            throw new DataNotFoundException("Room not found");
        }
        return roomEntitiesBySizeBetween;
    }

    @Override
    public List<RoomEntity> getByPrice(Double upperPrice, Double lowerPrice) {
        List<RoomEntity> roomEntitiesByPriceBetween =
                roomRepository.findRoomEntitiesByPriceBetween(upperPrice, lowerPrice);
        if(roomEntitiesByPriceBetween.isEmpty()){
            throw new DataNotFoundException("Room not found");
        }
        return roomEntitiesByPriceBetween;
    }
}
