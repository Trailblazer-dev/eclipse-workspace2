package com.academictrip.model;

import java.time.LocalDate;

public class Vehicle {
    private String vehicleId;
    private String make;
    private String model;
    private LocalDate year;
    private int capacity;
    private String plateNumber;

    // Constructors
    public Vehicle() {}
    public Vehicle(String vehicleId, String make, String model, LocalDate year, int capacity, String plateNumber) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.capacity = capacity;
        this.plateNumber = plateNumber;
    }

    // Getters and Setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public LocalDate getYear() { return year; }
    public void setYear(LocalDate year) { this.year = year; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    @Override
    public String toString() {
        return "Vehicle [vehicleId=" + vehicleId + ", make=" + make + ", model=" + model + "]";
    }
}