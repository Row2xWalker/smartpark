package com.example.smartpark.api.controller;

import com.example.smartpark.api.dto.ParkingSpotDTO;
import com.example.smartpark.api.model.ParkingSpot;
import com.example.smartpark.service.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking-spots")
public class ParkingSpotController {
    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> addParkingSpot(@RequestBody ParkingSpotDTO parkingSpotDTO) {
        try {
            ParkingSpot createdSpot = parkingSpotService.addParkingSpot(parkingSpotDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSpot);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
