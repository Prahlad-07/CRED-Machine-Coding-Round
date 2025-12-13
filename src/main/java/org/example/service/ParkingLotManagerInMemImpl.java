/*
 * Author: Prahlad_07
 * Created On: 2025-12-10
 */

package org.example.service;

import org.example.enums.SpotStatus;
import org.example.enums.SpotType;
import org.example.enums.VehicleType;
import org.example.models.ParkingSpot;
import org.example.models.ParkingTicket;
import org.example.models.Vehicle;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLotManagerInMemImpl implements ParkingLotManager {

    private final Map<String, ParkingSpot> spotMap = new ConcurrentHashMap<>();
    private final Map<String, ParkingTicket> activeTickets = new ConcurrentHashMap<>();

    @Override
    public boolean addParkingSpot(ParkingSpot spot) {
        if (spot == null || spot.getSpotId() == null) {
            return false;
        }
        return spotMap.putIfAbsent(spot.getSpotId(), spot) == null;
    }

    @Override
    public boolean removeParkingSpot(String spotId) {
        if (spotId == null) {
            return false;
        }

        ParkingSpot spot = spotMap.get(spotId);
        if (spot == null || spot.getStatus() == SpotStatus.OCCUPIED) {
            return false;
        }

        spotMap.remove(spotId);
        return true;
    }

    @Override
    public ParkingTicket parkVehicle(Vehicle vehicle, LocalDateTime entryTime) {
        if (vehicle == null || vehicle.getLicensePlate() == null) {
            return null;
        }
        if (activeTickets.containsKey(vehicle.getLicensePlate())) {
            return null;
        }

        ParkingSpot spot = findBestSpot(vehicle.getVehicleType());
        if (spot == null) {
            return null;
        }

        spot.setStatus(SpotStatus.OCCUPIED);
        spot.setParkedVehicleLicense(vehicle.getLicensePlate());

        ParkingTicket ticket = new ParkingTicket(
                UUID.randomUUID().toString(),
                vehicle.getLicensePlate(),
                spot.getSpotId(),
                entryTime
        );

        activeTickets.put(vehicle.getLicensePlate(), ticket);
        return ticket;
    }

    @Override
    public boolean exitVehicle(String licensePlate, LocalDateTime exitTime) {
        if (licensePlate == null) {
            return false;
        }

        ParkingTicket ticket = activeTickets.remove(licensePlate);
        if (ticket == null) {
            return false;
        }

        ticket.setExitTime(exitTime);
        ParkingSpot spot = spotMap.get(ticket.getSpotId());
        if (spot != null) {
            spot.setStatus(SpotStatus.AVAILABLE);
            spot.setParkedVehicleLicense(null);
        }
        return true;
    }

    @Override
    public ParkingSpot findVehicleByLicense(String licensePlate) {
        if (licensePlate == null) {
            return null;
        }

        ParkingTicket ticket = activeTickets.get(licensePlate);
        return ticket == null ? null : spotMap.get(ticket.getSpotId());
    }

    @Override
    public List<ParkingSpot> getAvailableSpotsByType(SpotType spotType) {
        if (spotType == null) {
            return new ArrayList<>();
        }

        return spotMap.values().stream()
                .filter(s -> s.getStatus() == SpotStatus.AVAILABLE && s.getSpotType() == spotType)
                .sorted(Comparator.comparingInt(ParkingSpot::getFloor)
                        .thenComparing(ParkingSpot::getSpotId))
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSpot getParkingSpot(String spotId) {
        if (spotId == null) {
            return null;
        }
        return spotMap.get(spotId);
    }

    private ParkingSpot findBestSpot(VehicleType vehicleType) {
        if (vehicleType == null) {
            return null;
        }

        List<SpotType> compatibleTypes = getCompatibleSpotTypes(vehicleType);

        return spotMap.values().stream()
                .filter(s -> s.getStatus() == SpotStatus.AVAILABLE)
                .filter(s -> compatibleTypes.contains(s.getSpotType()))
                .sorted(Comparator.comparingInt(this::spotRank)
                        .thenComparingInt(ParkingSpot::getFloor)
                        .thenComparing(ParkingSpot::getSpotId))
                .findFirst()
                .orElse(null);
    }

    private List<SpotType> getCompatibleSpotTypes(VehicleType vehicleType) {
        if (vehicleType == VehicleType.MOTORCYCLE) {
            return List.of(SpotType.COMPACT, SpotType.REGULAR, SpotType.LARGE);
        }
        if (vehicleType == VehicleType.CAR) {
            return List.of(SpotType.REGULAR, SpotType.LARGE);
        }
        return List.of(SpotType.LARGE);
    }

    private int spotRank(ParkingSpot spot) {
        if (spot.getSpotType() == SpotType.COMPACT) return 1;
        if (spot.getSpotType() == SpotType.REGULAR) return 2;
        return 3;
    }
}
