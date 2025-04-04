package com.example.smartpark.service;

import com.example.smartpark.api.dto.OccupancyResponse;
import com.example.smartpark.api.model.ParkingLot;
import com.example.smartpark.api.model.ParkingSpot;
import com.example.smartpark.api.model.Vehicle;
import com.example.smartpark.repository.ParkingLotRepository;
import com.example.smartpark.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingSpotRepository parkingSpotRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }


    public Optional<ParkingLot> findByLotId(String lotId) {
        return parkingLotRepository.findByLotId(lotId);
    }

    public ParkingLot addParkingLot(ParkingLot parkingLot) {
        ParkingLot savedLot = parkingLotRepository.save(parkingLot);

        // Automatically create the given number of spots
        for (int i = 0; i < savedLot.getCapacity(); i++) {
            ParkingSpot spot = new ParkingSpot();
            spot.setParkingLot(savedLot);
            spot.setOccupied(false);
            parkingSpotRepository.save(spot);
        }

        return savedLot;
    }

    public OccupancyResponse getOccupancy(String parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findByLotId(parkingLotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking lot not found"));

        int totalSpots = parkingSpotRepository.countByParkingLot(parkingLot);
        int occupiedSpots = parkingSpotRepository.countByParkingLotAndIsOccupiedTrue(parkingLot);
        int availableSpots = totalSpots - occupiedSpots;

        return new OccupancyResponse(totalSpots, occupiedSpots, availableSpots);
    }

    // Retrieve all vehicles currently parked in a given parking lot
    public List<Vehicle> getParkedVehicles(String parkingLotId) {
        // Fetch the parking lot by its ID (assuming it's a valid ID)
        ParkingLot parkingLot = parkingLotRepository.findByLotId(parkingLotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking lot not found"));

        // Get all occupied parking spots in the given parking lot
        List<ParkingSpot> occupiedSpots = parkingSpotRepository.findByParkingLotAndIsOccupiedTrue(parkingLot);

        // Extract the vehicles from the occupied parking spots
        return occupiedSpots.stream()
                .map(ParkingSpot::getVehicle)  // Get the vehicle parked in each spot
                .collect(Collectors.toList()); // Return the list of parked vehicles
    }
}
