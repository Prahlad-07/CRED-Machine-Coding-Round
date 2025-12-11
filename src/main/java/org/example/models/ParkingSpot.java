package org.example.models;

import org.example.enums.SpotStatus;
import org.example.enums.SpotType;

public class ParkingSpot {
    private String spotId;
    private SpotType spotType;
    private int floor;
    private SpotStatus status;
    private String parkedVehicleLicense;

    public ParkingSpot(String spotId, SpotType spotType, int floor) {
        this.spotId = spotId;
        this.spotType = spotType;
        this.floor = floor;
        this.status = SpotStatus.AVAILABLE;
    }

    public String getSpotId() { return spotId; }
    public SpotType getSpotType() { return spotType; }
    public int getFloor() { return floor; }
    public SpotStatus getStatus() { return status; }
    public String getParkedVehicleLicense() { return parkedVehicleLicense; }

    public void setStatus(SpotStatus status) { this.status = status; }
    public void setParkedVehicleLicense(String parkedVehicleLicense) {
        this.parkedVehicleLicense = parkedVehicleLicense;
    }
}