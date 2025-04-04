package com.example.smartpark.service;

import com.example.smartpark.api.model.ParkingLot;
import com.example.smartpark.api.model.ParkingSpot;
import com.example.smartpark.api.model.Vehicle;
import com.example.smartpark.repository.ParkingLotRepository;
import com.example.smartpark.repository.ParkingSpotRepository;
import com.example.smartpark.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ParkingLotService parkingLotService;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    // Constructor with all dependencies
    public VehicleService(VehicleRepository vehicleRepository,
                          ParkingLotService parkingLotService,
                          ParkingLotRepository parkingLotRepository,
                          ParkingSpotRepository parkingSpotRepository) {
        this.vehicleRepository = vehicleRepository;
        this.parkingLotService = parkingLotService;
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public Optional<Vehicle> getVehicle(Long id) {
        return vehicleRepository.findById(id);
    }
    public Optional<Vehicle> getLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle checkInVehicle(String licensePlate, String parkingLotId) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with license plate: " + licensePlate));

        ParkingLot parkingLot = parkingLotService.findByLotId(parkingLotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking lot not found with id: " + parkingLotId));

        // Check if the vehicle is already parked
        Optional<ParkingSpot> existingSpot = parkingSpotRepository.findByVehicle(vehicle);
        if (existingSpot.isPresent()) {
            throw new IllegalStateException("Vehicle is already parked in this lot");
        }

        // Find an available parking spot
        List<ParkingSpot> availableSpots = parkingSpotRepository.findByParkingLotAndIsOccupiedFalse(parkingLot);
        if (availableSpots.isEmpty()) {
            throw new IllegalStateException("No available parking spots in this lot");
        }

        ParkingSpot spot = availableSpots.get(0);  // Pick the first available spot
        spot.setVehicle(vehicle);
        spot.setOccupied(true);
        parkingSpotRepository.save(spot); // Save the updated spot
        parkingSpotRepository.flush();

        // Update the ParkingLot's occupiedSpaces
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);
        parkingLotRepository.save(parkingLot); // Save the updated parking lot

        return vehicle;
    }

    @Transactional
    public Vehicle checkOutVehicle(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        ParkingSpot spot = parkingSpotRepository.findByVehicle(vehicle)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle is not parked"));

        // Mark the spot as free
        spot.setVehicle(null);
        spot.setOccupied(false);
        parkingSpotRepository.save(spot);  // Save the updated spot
        parkingSpotRepository.flush();

        // Update the ParkingLot's occupiedSpaces
        ParkingLot parkingLot = spot.getParkingLot();
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);
        parkingLotRepository.save(parkingLot);  // Save the updated parking lot

        return vehicle;
    }
}
