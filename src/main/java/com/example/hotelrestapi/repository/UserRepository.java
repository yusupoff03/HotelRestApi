package com.example.hotelrestapi.repository;

import com.example.hotelrestapi.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
   Optional<UserEntity> findUserEntityByUsername(String username);
}