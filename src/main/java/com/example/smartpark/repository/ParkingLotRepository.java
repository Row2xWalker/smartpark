package com.example.smartpark.repository;

import com.example.smartpark.api.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    Optional<ParkingLot> findByLotId(String lotId);
}