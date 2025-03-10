package com.academictrip.model;

import java.time.LocalDate;

public class DriverVehicle {
    private String driverVehicleId;
    private String driverId;
    private String vehicleId;
    private LocalDate assignmentStart;
    private LocalDate assignmentEnd; // Note: Fix database typo "assigment_end" to "assignment_end"

    // Constructors
    public DriverVehicle() {}
    public DriverVehicle(String driverVehicleId, String driverId, String vehicleId, LocalDate assignmentStart, LocalDate assignmentEnd) {
        this.driverVehicleId = driverVehicleId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.assignmentStart = assignmentStart;
        this.assignmentEnd = assignmentEnd;
    }

    // Getters and Setters
    public String getDriverVehicleId() { return driverVehicleId; }
    public void setDriverVehicleId(String driverVehicleId) { this.driverVehicleId = driverVehicleId; }
    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    public LocalDate getAssignmentStart() { return assignmentStart; }
    public void setAssignmentStart(LocalDate assignmentStart) { this.assignmentStart = assignmentStart; }
    public LocalDate getAssignmentEnd() { return assignmentEnd; }
    public void setAssignmentEnd(LocalDate assignmentEnd) { this.assignmentEnd = assignmentEnd; }

    @Override
    public String toString() {
        return "DriverVehicle [driverVehicleId=" + driverVehicleId + ", driverId=" + driverId + ", vehicleId=" + vehicleId + "]";
    }
}