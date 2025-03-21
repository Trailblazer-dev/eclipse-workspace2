package com.academictrip.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.academictrip.model.Vehicle;
import com.academictrip.util.DatabaseUtil;
import com.academictrip.util.DateUtil;

public class VehicleDAO {

    // Generate vehicle_id (e.g., VEH001)
    private String generateVehicleId() throws SQLException {
        String prefix = "VEH";
        String sql = "SELECT MAX(vehicle_id) FROM Vehicle";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
					return prefix + "001";
				}
                int numericPart = Integer.parseInt(maxId.replace(prefix, ""));
                return String.format("%s%03d", prefix, numericPart + 1);
            }
            return prefix + "001";
        }
    }

    public void insertVehicle(Vehicle vehicle) throws SQLException {
        String vehicleId = generateVehicleId();
        vehicle.setVehicleId(vehicleId);

        String sql = "INSERT INTO Vehicle (vehicle_id, make, model, year, capacity, plate_number) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getVehicleId());
            stmt.setString(2, vehicle.getMake());
            stmt.setString(3, vehicle.getModel());
            stmt.setDate(4, Date.valueOf(vehicle.getYear()));
            stmt.setInt(5, vehicle.getCapacity());
            stmt.setString(6, vehicle.getPlateNumber());

            stmt.executeUpdate();
        }
    }

    public Vehicle getDefaultVehicle() throws SQLException {
        String sql = "SELECT * FROM Vehicle WHERE vehicle_id = 'DEFAULT'";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new Vehicle(
                    rs.getString("vehicle_id"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getDate("year").toLocalDate(),
                    rs.getInt("capacity"),
                    rs.getString("plate_number")
                );
            }
            return null;
        }
    }
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicle";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vehicles.add(new Vehicle(
                    rs.getString("vehicle_id"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getDate("year").toLocalDate(),
                    rs.getInt("capacity"),
                    rs.getString("plate_number")
                ));
            }
        }
        return vehicles;
    }

    /**
     * Get vehicle by ID
     * @param id the vehicle ID
     * @return Vehicle object or null if not found
     */
    public Vehicle getVehicleById(String id) throws SQLException {
        String sql = "SELECT * FROM Vehicle WHERE vehicle_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setVehicleId(rs.getString("vehicle_id"));
                    vehicle.setMake(rs.getString("make"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setYear(DateUtil.getLocalDate(rs, "year"));
                    vehicle.setCapacity(rs.getInt("capacity"));
                    vehicle.setPlateNumber(rs.getString("plate_number"));
                    return vehicle;
                }
            }
        }
        return null;
    }

    /**
     * Gets multiple vehicles by their IDs
     * @param vehicleIds Set of vehicle IDs to fetch
     * @return Map of vehicle ID to Vehicle object
     * @throws SQLException if database error occurs
     */
    public Map<String, Vehicle> getVehiclesByIds(Set<String> vehicleIds) throws SQLException {
        Map<String, Vehicle> vehiclesMap = new HashMap<>();

        if (vehicleIds == null || vehicleIds.isEmpty()) {
            return vehiclesMap;
        }

        // Create SQL with placeholders for each ID
        StringBuilder sql = new StringBuilder("SELECT * FROM vehicle WHERE vehicle_id IN (");
        for (int i = 0; i < vehicleIds.size(); i++) {
            sql.append(i > 0 ? ", ?" : "?");
        }
        sql.append(")");

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Set all the IDs as parameters
            int i = 1;
            for (String id : vehicleIds) {
                stmt.setString(i++, id);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setVehicleId(rs.getString("vehicle_id"));
                    vehicle.setMake(rs.getString("make"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setYear(DateUtil.getLocalDate(rs, "year"));
                    vehicle.setCapacity(rs.getInt("capacity"));
                    vehicle.setPlateNumber(rs.getString("plate_number"));
                    vehiclesMap.put(vehicle.getVehicleId(), vehicle);
                }
            }
        }

        return vehiclesMap;
    }

    /**
     * Get all vehicles that are not currently assigned to any driver
     * @return List of available vehicles
     * @throws SQLException if a database error occurs
     */
    public List<Vehicle> getAllAvailableVehicles() throws SQLException {
        List<Vehicle> availableVehicles = new ArrayList<>();

        String sql = "SELECT v.* FROM Vehicle v " +
                     "LEFT JOIN Driver_Vehicle dv ON v.vehicle_id = dv.vehicle_id " +
                     "AND CURRENT_DATE BETWEEN dv.assignment_start AND dv.assigment_end " +
                     "WHERE dv.vehicle_id IS NULL";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getString("vehicle_id"));
                vehicle.setMake(rs.getString("make"));
                vehicle.setModel(rs.getString("model"));

                // Convert SQL date to LocalDate for year
                java.sql.Date yearDate = rs.getDate("year");
                if (yearDate != null) {
                    vehicle.setYear(yearDate.toLocalDate());
                }

                vehicle.setCapacity(rs.getInt("capacity"));
                vehicle.setPlateNumber(rs.getString("plate_number"));
                vehicle.setAvailable(true); // Since we're getting only available vehicles

                availableVehicles.add(vehicle);
            }
        }

        return availableVehicles;
    }

    /**
     * Update an existing vehicle in the database
     * @param vehicle The vehicle object with updated values
     * @return boolean indicating success or failure
     * @throws SQLException if a database access error occurs
     */
    public boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE Vehicle SET make = ?, model = ?, year = ?, " +
                     "capacity = ?, plate_number = ? WHERE vehicle_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getMake());
            stmt.setString(2, vehicle.getModel());
            stmt.setDate(3, vehicle.getYear() != null ? Date.valueOf(vehicle.getYear()) : null);
            stmt.setInt(4, vehicle.getCapacity());
            stmt.setString(5, vehicle.getPlateNumber());
            stmt.setString(6, vehicle.getVehicleId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Delete a vehicle from the database by its ID
     * @param vehicleId The ID of the vehicle to delete
     * @return boolean indicating success or failure
     * @throws SQLException if a database access error occurs
     */
    public boolean deleteVehicle(String vehicleId) throws SQLException {
        // Check if vehicle is assigned to any driver-vehicle assignments first
        String checkSql = "SELECT COUNT(*) FROM Driver_Vehicle WHERE vehicle_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, vehicleId);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Vehicle is still assigned, cannot delete
                    return false;
                }
            }

            // If not assigned, proceed with deletion
            String deleteSql = "DELETE FROM Vehicle WHERE vehicle_id = ?";

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setString(1, vehicleId);
                int rowsAffected = deleteStmt.executeUpdate();
                return rowsAffected > 0;
            }
        }
    }

    /**
     * Check if a vehicle is currently assigned to a trip
     * @param vehicleId The ID of the vehicle to check
     * @return true if vehicle is assigned, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isVehicleAssigned(String vehicleId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Driver_Vehicle dv " +
                     "JOIN Trip t ON t.driver_vehicle_id = dv.driver_vehicle_id " +
                     "WHERE dv.vehicle_id = ? AND " +
                     "CURRENT_DATE BETWEEN dv.assignment_start AND dv.assigment_end";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicleId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}