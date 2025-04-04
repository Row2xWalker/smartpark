package com.example.smartpark.api.mapper;

import com.example.smartpark.api.dto.ParkingLotDTO;
import com.example.smartpark.api.model.ParkingLot;

public class ParkingLotMapper {

    public static ParkingLotDTO toDTO(ParkingLot entity) {
        return new ParkingLotDTO(
                entity.getLotId(),
                entity.getLocation(),
                entity.getCapacity(),
                entity.getOccupiedSpaces()
        );
    }

    public static ParkingLot toEntity(ParkingLotDTO dto) {
        return new ParkingLot(
                dto.getLotId(),
                dto.getLocation(),
                dto.getCapacity(),
                dto.getOccupiedSpaces()
        );
    }
}