//
//import org.example.enums.SpotType;
//import org.example.enums.VehicleType;
//import org.example.models.ParkingSpot;
//import org.example.models.ParkingTicket;
//import org.example.service.ParkingLotManagerInMemImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class ParkingLotTest {
//
//    private ParkingLotManagerInMemImpl manager;
//
//    @BeforeEach
//    void setUp() {
//        manager = new ParkingLotManagerInMemImpl();
//    }
//
//    @Test
//    void testAddAndRemoveSpot() {
//        ParkingSpot spot = new ParkingSpot("S1", SpotType.REGULAR, 1);
//        assertTrue(manager.addParkingSpot(spot));
//        assertNotNull(manager.getParkingSpot("S1"));
//
//        assertTrue(manager.removeParkingSpot("S1"));
//        assertNull(manager.getParkingSpot("S1"));
//    }
//
//    @Test
//    void testSmartAllocation_MotorcyclePrefersCompact() {
//        // Setup: Large spot on Floor 1, Compact on Floor 2
//        manager.addParkingSpot(new ParkingSpot("L1", SpotType.LARGE, 1));
//        manager.addParkingSpot(new ParkingSpot("C2", SpotType.COMPACT, 2));
//
//        Vehicle bike = new Vehicle("BIKE-1", VehicleType.MOTORCYCLE, "User");
//        ParkingTicket ticket = manager.parkVehicle(bike, LocalDateTime.now());
//
//        // Should pick C2 (Compact) to save space, even though L1 is lower floor
//        assertEquals("C2", ticket.getSpotId());
//    }
//
//    @Test
//    void testSmartAllocation_CarPrefersLowerFloor() {
//        // Setup: Two Regular spots, Floor 2 and Floor 1
//        manager.addParkingSpot(new ParkingSpot("R2", SpotType.REGULAR, 2));
//        manager.addParkingSpot(new ParkingSpot("R1", SpotType.REGULAR, 1));
//
//        var car = new com.parkinglot.models.Vehicle("CAR-1", VehicleType.CAR, "User");
//        ParkingTicket ticket = manager.parkVehicle(car, LocalDateTime.now());
//
//        // Should pick R1 (Floor 1)
//        assertEquals("R1", ticket.getSpotId());
//    }
//
//    @Test
//    void testCarLogicFixed() {
//        // This confirms the previous "Assertion Error" is fixed
//        manager.addParkingSpot(new ParkingSpot("R1", SpotType.REGULAR, 1));
//        com.parkinglot.models.Vehicle car = new com.parkinglot.models.Vehicle("CAR-TEST", VehicleType.CAR, "Owner");
//
//        ParkingTicket ticket = manager.parkVehicle(car, LocalDateTime.now());
//
//        assertNotNull(ticket, "Car should be able to park in Regular spot");
//    }
//
//    @Test
//    void testNullInputsAreHandledGracefully() {
//        // These calls should not crash the system (No NullPointerExceptions)
//        assertFalse(manager.addParkingSpot(null));
//        assertFalse(manager.removeParkingSpot(null));
//        assertNull(manager.parkVehicle(null, LocalDateTime.now()));
//        assertFalse(manager.exitVehicle(null, LocalDateTime.now()));
//    }
//
//    @Test
//    void testFindVehicleByLicense() {
//        manager.addParkingSpot(new ParkingSpot("R1", SpotType.REGULAR, 1));
//        com.parkinglot.models.Vehicle car = new com.parkinglot.models.Vehicle("ABC-123", VehicleType.CAR, "Owner");
//        manager.parkVehicle(car, LocalDateTime.now());
//
//        ParkingSpot spot = manager.findVehicleByLicense("ABC-123");
//        assertNotNull(spot);
//        assertEquals("R1", spot.getSpotId());
//    }
//}