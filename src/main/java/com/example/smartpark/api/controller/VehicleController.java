package com.example.smartpark.api.controller;

import com.example.smartpark.api.model.Vehicle;
import com.example.smartpark.service.VehicleService;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    @Autowired
    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        return vehicleService.getVehicle(id)
                .map(ResponseEntity::ok)  // Return the vehicle with a 200 OK if found
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());  // Return 404 Not Found if not found
    }

    @GetMapping(params = "licensePlate")
    public ResponseEntity<Vehicle> getByLicensePlate(@RequestParam String licensePlate) {
        return vehicleService.getLicensePlate(licensePlate)
                .map(ResponseEntity::ok)  // Return the vehicle with a 200 OK if found
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());  // Return 404 Not Found if not found
    }

    // POST Request: Add a new vehicle
    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody @Valid Vehicle vehicle) {
        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if vehicle licensePlate is missing
        }

        Vehicle newVehicle = vehicleService.addVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVehicle); // Return 201 Created for successful addition
    }


    @PostMapping("/{licensePlate}/check-in")
    public ResponseEntity<Vehicle> checkInVehicle(@PathVariable String licensePlate, @RequestParam @NotEmpty String parkingLotId) {
        try {
            Vehicle checkedInVehicle = vehicleService.checkInVehicle(licensePlate, parkingLotId);
            return ResponseEntity.ok(checkedInVehicle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if vehicle or parking lot is not found
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Return 403 if no available spots
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // Return 400 for other issues
        }
    }


    // Check-out a vehicle from a specific parking lot
    @PostMapping("/{licensePlate}/check-out")
    public ResponseEntity<Vehicle> checkOutVehicle(@PathVariable String licensePlate) {
        try {
            Vehicle checkedOutVehicle = vehicleService.checkOutVehicle(licensePlate);
            return ResponseEntity.ok(checkedOutVehicle); // Return 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Return 400 for other issues
        }
    }
}
