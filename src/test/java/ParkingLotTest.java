/*
 * Author: Prahlad_07
 * Created On: 2025-12-10
 */



import org.example.enums.SpotType;
import org.example.enums.VehicleType;
import org.example.models.ParkingSpot;
import org.example.models.ParkingTicket;
import org.example.models.Vehicle;
import org.example.service.ParkingLotManagerInMemImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    private ParkingLotManagerInMemImpl manager;

    @BeforeEach
    void setUp() {
        manager = new ParkingLotManagerInMemImpl();
    }

    @Test
    void testAddAndRemoveSpot() {
        ParkingSpot spot = new ParkingSpot("S1", SpotType.REGULAR, 1);
        assertTrue(manager.addParkingSpot(spot));
        assertNotNull(manager.getParkingSpot("S1"));

        assertTrue(manager.removeParkingSpot("S1"));
        assertNull(manager.getParkingSpot("S1"));
    }

    @Test
    void testSmartAllocation_MotorcyclePrefersCompact() {
        manager.addParkingSpot(new ParkingSpot("L1", SpotType.LARGE, 1));
        manager.addParkingSpot(new ParkingSpot("C2", SpotType.COMPACT, 2));

        Vehicle bike = new Vehicle("BIKE-1", VehicleType.MOTORCYCLE, "User");
        ParkingTicket ticket = manager.parkVehicle(bike, LocalDateTime.now());

        assertNotNull(ticket);
        assertEquals("C2", ticket.getSpotId());
    }

    @Test
    void testSmartAllocation_CarPrefersLowerFloor() {
        manager.addParkingSpot(new ParkingSpot("R2", SpotType.REGULAR, 2));
        manager.addParkingSpot(new ParkingSpot("R1", SpotType.REGULAR, 1));

        Vehicle car = new Vehicle("CAR-1", VehicleType.CAR, "User");
        ParkingTicket ticket = manager.parkVehicle(car, LocalDateTime.now());

        assertNotNull(ticket);
        assertEquals("R1", ticket.getSpotId());
    }

    @Test
    void testCarLogicFixed() {
        manager.addParkingSpot(new ParkingSpot("R1", SpotType.REGULAR, 1));

        Vehicle car = new Vehicle("CAR-TEST", VehicleType.CAR, "Owner");
        ParkingTicket ticket = manager.parkVehicle(car, LocalDateTime.now());

        assertNotNull(ticket);
    }

    @Test
    void testNullInputsAreHandledGracefully() {
        assertFalse(manager.addParkingSpot(null));
        assertFalse(manager.removeParkingSpot(null));
        assertNull(manager.parkVehicle(null, LocalDateTime.now()));
        assertFalse(manager.exitVehicle(null, LocalDateTime.now()));
    }

    @Test
    void testFindVehicleByLicense() {
        manager.addParkingSpot(new ParkingSpot("R1", SpotType.REGULAR, 1));

        Vehicle car = new Vehicle("ABC-123", VehicleType.CAR, "Owner");
        manager.parkVehicle(car, LocalDateTime.now());

        ParkingSpot spot = manager.findVehicleByLicense("ABC-123");
        assertNotNull(spot);
        assertEquals("R1", spot.getSpotId());
    }
}
