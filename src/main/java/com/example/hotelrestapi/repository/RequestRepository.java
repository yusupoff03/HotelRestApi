package com.example.hotelrestapi.repository;

import com.example.hotelrestapi.entity.request.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RequestRepository extends JpaRepository<BookingRequest, UUID> {

}
