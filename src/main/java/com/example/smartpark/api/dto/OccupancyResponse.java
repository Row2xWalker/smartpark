package com.example.smartpark.api.dto;

public class OccupancyResponse {
    private final int totalSpots;
    private final int occupiedSpots;
    private final int availableSpots;

    public OccupancyResponse(int totalSpots, int occupiedSpots, int availableSpots) {
        this.totalSpots = totalSpots;
        this.occupiedSpots = occupiedSpots;
        this.availableSpots = availableSpots;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getOccupiedSpots() {
        return occupiedSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }
}
