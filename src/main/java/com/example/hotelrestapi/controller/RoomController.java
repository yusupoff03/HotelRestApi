package com.example.hotelrestapi.controller;

import com.example.hotelrestapi.dto.RoomCreatDto;
import com.example.hotelrestapi.entity.room.RoomEntity;
import com.example.hotelrestapi.exception.RequestValidationException;
import com.example.hotelrestapi.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/room")
public class RoomController {
    private final RoomService roomService;
    @PostMapping(value = "/add-room")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<RoomEntity> addRoom(@Valid @RequestBody RoomCreatDto roomCreatDto
            ,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(roomService.addRoom(roomCreatDto));
    }
    @PutMapping(value = "/edit-room")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<RoomEntity> editRoom(
            @RequestBody RoomCreatDto roomCreatDto, @RequestParam UUID roomId){
      return ResponseEntity.ok(roomService.editRoom(roomCreatDto,roomId));
    }
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<RoomEntity>> getAll(int page,int size){
        return ResponseEntity.ok(roomService.getAll(page,size));
    }
    @GetMapping(value = "/get-all/sort")
    public ResponseEntity<List<RoomEntity>>getAllBySort(
            int page,int size,boolean sortByNumber
            ,boolean sortBySize,boolean sortByPrice){
        return ResponseEntity.ok(roomService.getAllBySort(page,size,sortByNumber,sortByPrice,sortBySize));
    }
    @DeleteMapping(value = "/delete-room")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Boolean>deleteRoomById(@RequestParam UUID roomId){
        return ResponseEntity.ok(roomService.deleteById(roomId));
    }
    @GetMapping(value = "/get-by-size")
    public ResponseEntity<List<RoomEntity>> getBySize(@RequestParam int size){
      return ResponseEntity.ok(roomService.getBySize(size));
    }
    @GetMapping(value = "/get-by-price")
    public ResponseEntity<List<RoomEntity>>getByPrice(
            @RequestParam Double UpperPrice,@RequestParam Double LowerPrice){
        return ResponseEntity.ok(roomService.getByPrice(UpperPrice,LowerPrice));
    }
}
