package org.example.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingTicket {
    private String ticketId;
    private String licensePlate;
    private String spotId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, String licensePlate, String spotId, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.licensePlate = licensePlate;
        this.spotId = spotId;
        this.entryTime = entryTime;
    }

    public String getTicketId() { return ticketId; }
    public String getLicensePlate() { return licensePlate; }
    public String getSpotId() { return spotId; }
    public LocalDateTime getEntryTime() { return entryTime; }

    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
    public LocalDateTime getExitTime() { return exitTime; }

    public double calculateDurationHours(LocalDateTime currentTime) {
        LocalDateTime endTime = (exitTime != null) ? exitTime : currentTime;
        long seconds = ChronoUnit.SECONDS.between(entryTime, endTime);
        return seconds / 3600.0;
    }
}
