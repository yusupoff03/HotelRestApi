package com.example.hotelrestapi.repository;

import com.example.hotelrestapi.entity.card.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

}
