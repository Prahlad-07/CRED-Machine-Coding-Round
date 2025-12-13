/*
 * Author: Prahlad_07
 * Created On: 2025-12-10
 */


package org.example;

import org.example.enums.SpotType;
import org.example.enums.VehicleType;
import org.example.models.ParkingSpot;
import org.example.models.ParkingTicket;
import org.example.models.Vehicle;
import org.example.service.ParkingLotManager;
import org.example.service.ParkingLotManagerInMemImpl;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ParkingLotManager manager = new ParkingLotManagerInMemImpl();

        manager.addParkingSpot(new ParkingSpot("F1-L1", SpotType.LARGE, 1));
        manager.addParkingSpot(new ParkingSpot("F1-R1", SpotType.REGULAR, 1));
        manager.addParkingSpot(new ParkingSpot("F2-C1", SpotType.COMPACT, 2));
        manager.addParkingSpot(new ParkingSpot("F2-R1", SpotType.REGULAR, 2));

        Vehicle bike = new Vehicle("MOTO-123", VehicleType.MOTORCYCLE, "Sonal");
        ParkingTicket ticket1 = manager.parkVehicle(bike, LocalDateTime.now());

        Vehicle car = new Vehicle("CAR-999", VehicleType.CAR, "Alex");
        ParkingTicket ticket2 = manager.parkVehicle(car, LocalDateTime.now());

        manager.exitVehicle("MOTO-123", LocalDateTime.now().plusHours(2));
    }
}
