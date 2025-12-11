package org.example.models;

import org.example.enums.VehicleType;

public class Vehicle {
    private String licensePlate;
    private VehicleType vehicleType;
    private String ownerName;

    public Vehicle(String licensePlate, VehicleType vehicleType, String ownerName) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.ownerName = ownerName;
    }

    public String getLicensePlate() { return licensePlate; }
    public org.example.enums.VehicleType getVehicleType() { return vehicleType; }
    public String getOwnerName() { return ownerName; }
}