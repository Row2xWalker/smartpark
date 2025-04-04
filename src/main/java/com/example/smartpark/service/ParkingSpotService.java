package com.example.smartpark.service;

import com.example.smartpark.api.dto.ParkingSpotDTO;
import com.example.smartpark.api.model.ParkingLot;
import com.example.smartpark.api.model.ParkingSpot;
import com.example.smartpark.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingLotService parkingLotService;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository, ParkingLotService parkingLotService) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.parkingLotService = parkingLotService;
    }

    public ParkingSpot addParkingSpot(ParkingSpotDTO parkingSpotDTO) {
        ParkingLot parkingLot = parkingLotService.findByLotId(parkingSpotDTO.getParkingLotId())
                .orElseThrow(() -> new IllegalArgumentException("Parking lot not found"));

        ParkingSpot newSpot = new ParkingSpot();
        newSpot.setParkingLot(parkingLot);
        newSpot.setOccupied(parkingSpotDTO.isOccupied());

        return parkingSpotRepository.save(newSpot);
    }
}
