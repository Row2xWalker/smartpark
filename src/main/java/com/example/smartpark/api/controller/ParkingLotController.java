package com.example.smartpark.api.controller;

import com.example.smartpark.api.dto.OccupancyResponse;
import com.example.smartpark.api.dto.ParkingLotDTO;
import com.example.smartpark.api.mapper.ParkingLotMapper;
import com.example.smartpark.api.model.ParkingLot;
import com.example.smartpark.api.model.Vehicle;
import com.example.smartpark.service.ParkingLotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lot")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @Autowired
    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping(params = "lotId")
    public ResponseEntity<ParkingLotDTO> getByLotId(@RequestParam String lotId) {
        return parkingLotService.findByLotId(lotId)
                .map(ParkingLotMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ParkingLotDTO> addParkingLot(@RequestBody @Valid ParkingLotDTO parkingLotDTO) {
        ParkingLot createdEntity = parkingLotService.addParkingLot(ParkingLotMapper.toEntity(parkingLotDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(ParkingLotMapper.toDTO(createdEntity));
    }

    @GetMapping("/{parkingLotId}/occupancy")
    public ResponseEntity<OccupancyResponse> getParkingLotOccupancy(@PathVariable String parkingLotId) {
        OccupancyResponse occupancy = parkingLotService.getOccupancy(parkingLotId);
        return ResponseEntity.ok(occupancy);
    }

    @GetMapping("/{parkingLotId}/parked-vehicles")
    public ResponseEntity<List<Vehicle>> getParkedVehicles(@PathVariable String parkingLotId) {
        try {
            List<Vehicle> vehicles = parkingLotService.getParkedVehicles(parkingLotId);
            return ResponseEntity.ok(vehicles);  // Return 200 OK with the list of vehicles
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if the parking lot isn't found
        }
    }
}
