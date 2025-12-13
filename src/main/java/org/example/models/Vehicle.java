/*
 * Author: Prahlad_07
 * Created On: 2025-12-10
 */

package org.example.models;

import org.example.enums.VehicleType;

public class Vehicle {
    private final String licensePlate;
    private final VehicleType vehicleType;
    private final String ownerName;

    public Vehicle(String licensePlate, VehicleType vehicleType, String ownerName) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.ownerName = ownerName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
