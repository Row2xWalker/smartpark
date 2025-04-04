package com.example.smartpark.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments ID
    private Long id;

    @NotNull
    @Size(min = 1, max = 50, message = "Lot Id must be between 1 and 50 characters")
    @Column(unique = true) // Ensures unique lot identifiers
    private String lotId;

    @NotNull
    private String location;
    @Min(value = 1, message = "Capacity should be greater than 0")
    private int capacity;
    private int occupiedSpaces;

    // A parking lot contains multiple parking spots.
    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParkingSpot> spots = new ArrayList<>();

    public ParkingLot() {}
    @JsonCreator
    public ParkingLot(
            @JsonProperty("lotId") String lotId,
            @JsonProperty("location") String location,
            @JsonProperty("capacity") int capacity,
            @JsonProperty("occupiedSpaces") int occupiedSpaces
    ) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupiedSpaces = occupiedSpaces;
    }


    public Long getId() {
        return id;
    }

    public String getLotId(){
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
        if (occupiedSpaces > this.capacity) {
            throw new IllegalArgumentException("Occupied spaces cannot exceed capacity.");
        }
        this.occupiedSpaces = occupiedSpaces;
    }
}
