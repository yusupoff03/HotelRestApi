package com.example.hotelrestapi.repository;

import com.example.hotelrestapi.entity.request.BookingRequest;
import com.example.hotelrestapi.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface RequestRepository extends JpaRepository<BookingRequest, UUID> {
 List<BookingRequest> findBookingRequestsByUser(UserEntity user);
}
