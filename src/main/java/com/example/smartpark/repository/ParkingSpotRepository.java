package com.example.smartpark.repository;

import com.example.smartpark.api.model.ParkingLot;
import com.example.smartpark.api.model.ParkingSpot;
import com.example.smartpark.api.model.Vehicle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    // Find all available spots in a given parking lot
    List<ParkingSpot> findByParkingLotAndIsOccupiedFalse(ParkingLot parkingLot);

    // Find the spot where a specific vehicle is parked
    Optional<ParkingSpot> findByVehicle(Vehicle vehicle);

    int countByParkingLot(ParkingLot parkingLot);
    int countByParkingLotAndIsOccupiedTrue(ParkingLot parkingLot);

    // Get all occupied parking spots for a specific parking lot
    List<ParkingSpot> findByParkingLotAndIsOccupiedTrue(ParkingLot parkingLot);
}