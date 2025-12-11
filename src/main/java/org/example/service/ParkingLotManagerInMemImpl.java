package org.example.service;

import org.example.enums.SpotStatus;
import org.example.enums.SpotType;
import org.example.enums.VehicleType;
import org.example.models.ParkingSpot;
import org.example.models.ParkingTicket;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLotManagerInMemImpl implements ParkingLotManager {

    private final Map<String, ParkingSpot> spotMap = new ConcurrentHashMap<>();
    private final Map<String, ParkingTicket> activeTicket = new ConcurrentHashMap<>();

    @Override
    public boolean addParkingSpot(ParkingSpot spot) {
        if (spot == null || spot.getSpotId() == null || spotMap.containsKey(spot.getSpotId())) {
            return false;
        }
        spotMap.put(spot.getSpotId(), spot);
        return true;
    }

    @Override
    public Boolean removeParkingSpot(String spotId) {
        if (spotId == null || !spotMap.containsKey(spotId)) {
            return false;
        }

        ParkingSpot spot = spotMap.get(spotId);
        if (spot != null && spot.getStatus() == SpotStatus.OCCUPIED) {
            return false;
        }
        spotMap.remove(spotId);
        return true;
    }

    @Override
    public ParkingTicket parkVehicle(com.parkinglot.models.Vehicle vehicle, LocalDateTime entryTime) {
        if (vehicle == null || vehicle.getLicensePlate() == null || activeTicket.containsKey(vehicle.getLicensePlate())) {
            return null;
        }

        ParkingSpot bestSpot = getBestSpot(vehicle.getVehicleType());
        if (bestSpot == null) {
            return null;
        }

        bestSpot.setStatus(SpotStatus.OCCUPIED);
        bestSpot.setParkedVehicleLicense(vehicle.getLicensePlate());

        String ticketId = UUID.randomUUID().toString();
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle.getLicensePlate(), bestSpot.getSpotId(), entryTime);

        activeTicket.put(vehicle.getLicensePlate(), ticket);

        return ticket;
    }

    @Override
    public Boolean exitVehicle(String licensePlate, LocalDateTime exitTime) {
        if (licensePlate == null || !activeTicket.containsKey(licensePlate)) {
            return false;
        }

        ParkingTicket ticket = activeTicket.get(licensePlate);
        ticket.setExitTime(exitTime);

        ParkingSpot spot = spotMap.get(ticket.getSpotId());
        if (spot != null) {
            spot.setStatus(SpotStatus.AVAILABLE);
            spot.setParkedVehicleLicense(null);
        }

        activeTicket.remove(licensePlate);
        return true;
    }

    @Override
    public ParkingSpot findVehicleByLicense(String licensePlate) {
        if (licensePlate != null && activeTicket.containsKey(licensePlate)) {
            ParkingTicket ticket = activeTicket.get(licensePlate);
            return spotMap.get(ticket.getSpotId());
        }
        return null;
    }

    @Override
    public List<ParkingSpot> getAvailableSpotsByType(SpotType spotType) {
        if (spotType == null) return new ArrayList<>();

        return spotMap.values().stream()
                .filter(s -> s.getSpotType() == spotType && s.getStatus() == SpotStatus.AVAILABLE)
                .sorted(Comparator.comparingInt(ParkingSpot::getFloor)
                        .thenComparing(ParkingSpot::getSpotId))
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSpot getParkingSpot(String spotId) {
        if (spotId == null) return null;
        return spotMap.get(spotId);
    }

    private ParkingSpot getBestSpot(VehicleType vehicleType) {
        if (vehicleType == null) return null;

        List<SpotType> allowedTypes = getCompatibleTypes(vehicleType);

        List<ParkingSpot> candidates = spotMap.values().stream()
                .filter(s -> s.getStatus() == SpotStatus.AVAILABLE)
                .filter(s -> allowedTypes.contains(s.getSpotType()))
                .collect(Collectors.toList());

        if (candidates.isEmpty()) return null;

        candidates.sort(Comparator
                .comparingInt(this::getSpotSizeValue)
                .thenComparingInt(ParkingSpot::getFloor)
                .thenComparing(ParkingSpot::getSpotId)
        );

        return candidates.get(0);
    }

    private List<SpotType> getCompatibleTypes(VehicleType type) {
        List<SpotType> types = new ArrayList<>();
        if (type == VehicleType.MOTORCYCLE) {
            types.add(SpotType.COMPACT);
            types.add(SpotType.REGULAR);
            types.add(SpotType.LARGE);
        }
        else if (type == VehicleType.CAR) {
            types.add(SpotType.REGULAR);
            types.add(SpotType.LARGE);
        }
        else if (type == VehicleType.TRUCK) {
            types.add(SpotType.LARGE);
        }
        return types;
    }

    private int getSpotSizeValue(ParkingSpot spot) {
        switch(spot.getSpotType()) {
            case COMPACT: return 1;
            case REGULAR: return 2;
            case LARGE: return 3;
            default: return 4;
        }
    }
}