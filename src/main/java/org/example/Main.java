//package org.example;
//
//import org.example.enums.SpotType;
//import org.example.enums.VehicleType;
//import org.example.models.ParkingSpot;
//import org.example.models.ParkingTicket;
//import org.example.models.Vehicle;
//import org.example.service.ParkingLotManager;
//import org.example.service.ParkingLotManagerInMemImpl;
//
//import java.time.LocalDateTime;
//
//public class Main {
//    public static void main(String[] args) {
//        ParkingLotManager manager = new ParkingLotManagerInMemImpl();
//
//        manager.addParkingSpot(new ParkingSpot("F1-L1", SpotType.LARGE, 1));
//        manager.addParkingSpot(new ParkingSpot("F1-R1", SpotType.REGULAR, 1));
//        manager.addParkingSpot(new ParkingSpot("F2-C1", SpotType.COMPACT, 2));
//        manager.addParkingSpot(new ParkingSpot("F2-R1", SpotType.REGULAR, 2));
//
//        System.out.println("Spots added: F1-L1 (Large), F1-R1 (Regular), F2-C1 (Compact), F2-R1 (Regular)\n");
//   Vehicle moto = new Vehicle("MOTO-123", VehicleType.MOTORCYCLE, "Sonal");
//
////        if (ticket1 != null) {
////            System.out.println("Motorcycle parked at: " + ticket1.getSpotId());
////        } else {
////            System.out.println("Parking failed for Motorcycle.");
////        }
////        System.out.println("\n--- Scenario 2: Parking a Car ---");
////        com.parkinglot.models.Vehicle car = new Vehicle("CAR-999", VehicleType.CAR, "Alex");
//        ParkingTicket ticket2 = manager.parkVehicle(car, LocalDateTime.now());
//
//        if (ticket2 != null) {
//            System.out.println("Car parked at: " + ticket2.getSpotId());
//        } else {
//            System.out.println("Parking failed for Car.");
//        }
//        System.out.println("\n--- Scenario 3: Find Vehicle ---");
//        ParkingSpot spot = manager.findVehicleByLicense("MOTO-123");
//        if (spot != null) {
//            System.out.println("Vehicle MOTO-123 found at spot: " + spot.getSpotId() + " on Floor " + spot.getFloor());
//        } else {
//            System.out.println("Vehicle not found.");
//        }
//        System.out.println("\n--- Scenario 4: Exit Vehicle ---");
//        Boolean exitStatus = manager.exitVehicle("MOTO-123", LocalDateTime.now().plusHours(2));
//        if (exitStatus) {
//            System.out.println("MOTO-123 has exited successfully.");
//        } else {
//            System.out.println("Exit failed.");
//        }
//    }
//}