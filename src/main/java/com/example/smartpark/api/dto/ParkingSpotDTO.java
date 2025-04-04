package com.example.smartpark.api.dto;

public class ParkingSpotDTO {
    private String parkingLotId;
    private boolean occupied;

    // Constructors
    public ParkingSpotDTO() {}

    public ParkingSpotDTO(String parkingLotId, boolean occupied) {
        this.parkingLotId = parkingLotId;
        this.occupied = occupied;
    }

    // Getters and Setters
    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
