/*
 * Author: Prahlad_07
 * Created On: 2025-12-10
 */

package org.example.service;

import org.example.enums.SpotType;
import org.example.models.ParkingSpot;
import org.example.models.ParkingTicket;
import org.example.models.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingLotManager {
    boolean addParkingSpot(ParkingSpot spot);
    boolean removeParkingSpot(String spotId);
    ParkingTicket parkVehicle(Vehicle vehicle, LocalDateTime entryTime);
    boolean exitVehicle(String licensePlate, LocalDateTime exitTime);
    ParkingSpot findVehicleByLicense(String licensePlate);
    List<ParkingSpot> getAvailableSpotsByType(SpotType spotType);
    ParkingSpot getParkingSpot(String spotId);
}
