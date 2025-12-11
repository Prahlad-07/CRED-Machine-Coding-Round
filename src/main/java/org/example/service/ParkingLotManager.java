package org.example.service;

import org.example.enums.SpotType;
import org.example.models.ParkingSpot;
import org.example.models.ParkingTicket;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingLotManager {
    boolean addParkingSpot(ParkingSpot spot);
    Boolean removeParkingSpot(String spotId);
    ParkingTicket parkVehicle(com.parkinglot.models.Vehicle vehicle, LocalDateTime entryTime);
    Boolean exitVehicle(String licensePlate, LocalDateTime exitTime);
    ParkingSpot findVehicleByLicense(String licensePlate);
    List<ParkingSpot> getAvailableSpotsByType(SpotType spotType);
    ParkingSpot getParkingSpot(String spotId);
}