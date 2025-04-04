package com.example.smartpark.config;

import com.example.smartpark.api.model.*;
import com.example.smartpark.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Override
    public void run(String... args) throws Exception {
        // Parking lot
        ParkingLot lot1 = new ParkingLot();
        lot1.setLotId("LOT-000");
        lot1.setLocation("Downtown");
        lot1.setCapacity(5);
        lot1.setOccupiedSpaces(2);

        // Save parking lot
        ParkingLot savedLot = parkingLotRepository.save(lot1);

        // vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("ABC000");
        vehicle1.setType("Car");
        vehicle1.setOwnerName("John Doe");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("XYZ789");
        vehicle2.setType("Truck");
        vehicle2.setOwnerName("Mary Doe");

        // Save vehicles
        vehicleRepository.save(vehicle1);
        vehicleRepository.save(vehicle2);

        // Create parking spots and assign vehicles
        for (int i = 0; i < savedLot.getCapacity(); i++) {
            ParkingSpot spot = new ParkingSpot();
            spot.setParkingLot(savedLot);
            spot.setOccupied(i < 2);  // 2 spots are occupied for testing
            if (i < 2) {
                spot.setVehicle(i == 0 ? vehicle1 : vehicle2); // Assign vehicle to spots
            }
            parkingSpotRepository.save(spot);
        }

        System.out.println("Data has been loaded into the database.");
    }
}