package com.example.smartpark.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented primary key
    private Long id;

    @NotNull
    @Column(unique = true) // Ensures license plate uniqueness
    private String licensePlate;

    @NotNull
    private String type; // "Car", "Motorcycle", "Truck"

    @NotNull
    @NotBlank(message = "Owner name cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name must contain letters and spaces only")
    private String ownerName;

    public Vehicle() {}

    @OneToOne
    @JoinColumn(name = "parking_spot_id") // The foreign key that references ParkingSpot
    private ParkingSpot parkingSpot;
    @JsonCreator
    public Vehicle(
            @JsonProperty("licensePlate") String licensePlate,
            @JsonProperty("type") String type,
            @JsonProperty("ownerName") String ownerName
    ) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerName = ownerName;
    }
    public Long getId() {
        return id;
    }
    public String getLicensePlate(){
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
