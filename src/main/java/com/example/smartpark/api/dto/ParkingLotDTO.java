package com.example.smartpark.api.dto;

import jakarta.validation.constraints.*;

public class ParkingLotDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String lotId;

    @NotNull
    private String location;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    private int occupiedSpaces;

    // Constructors
    public ParkingLotDTO() {}

    public ParkingLotDTO(String lotId, String location, int capacity, int occupiedSpaces) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupiedSpaces = occupiedSpaces;
    }

    // Getters and Setters
    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupiedSpaces() {
        return occupiedSpaces;
    }

    public void setOccupiedSpaces(int occupiedSpaces) {
        this.occupiedSpaces = occupiedSpaces;
    }
}