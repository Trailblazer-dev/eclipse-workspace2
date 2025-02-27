package com.academictrip.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.academictrip.model.DriverVehicle;
import com.academictrip.util.DatabaseUtil;

public class DriverVehicleDAO {
	private String generateDriverVehicleId() throws SQLException {
        String prefix = "DV";
        String sql = "SELECT MAX(driver_vehicle_id) AS max_id FROM Driver_Vehicle";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                String maxId = resultSet.getString("max_id");
                if (maxId == null) {
					return prefix + "001";
				}
                int numericPart = Integer.parseInt(maxId.replace(prefix, ""));
                return String.format("%s%03d", prefix, numericPart + 1);
            }
            return prefix + "001";
        }
    }

    // Insert a new assignment
	public String insertAssignment(DriverVehicle assignment) throws SQLException {
        String driverVehicleId = generateDriverVehicleId();
        String sql = "INSERT INTO Driver_Vehicle (driver_vehicle_id, driver_id, vehicle_id, assignment_start, assigment_end) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, driverVehicleId);
            stmt.setString(2, assignment.getDriverId());
            stmt.setString(3, assignment.getVehicleId());
            stmt.setDate(4, Date.valueOf(LocalDate.now())); // assignment_start
            stmt.setDate(5, Date.valueOf(LocalDate.now().plusDays(1))); // assignment_end (example)
            stmt.executeUpdate();

            return driverVehicleId;
        }
    }

    // Get all assignments
    public List<DriverVehicle> getAllAssignments() throws SQLException {
    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust based on your DB date format
        List<DriverVehicle> assignments = new ArrayList<>();
        String sql = "SELECT * FROM Driver_Vehicle";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DriverVehicle assignment = new DriverVehicle();
                assignment.setDriverVehicleId(resultSet.getString("driver_vehicle_id"));
                assignment.setDriverId(resultSet.getString("driver_id"));
                assignment.setVehicleId(resultSet.getString("vehicle_id"));
                // Convert String to LocalDate
                String startDateStr = resultSet.getString("assignment_start");
                if (startDateStr != null) {
                    assignment.setAssignmentStart(LocalDate.parse(startDateStr, formatter));
                }

                String endDateStr = resultSet.getString("assigment_end");
                if (endDateStr != null) {
                    assignment.setAssignmentEnd(LocalDate.parse(endDateStr, formatter));
                }

                assignments.add(assignment);
            }
        }
        return assignments;
    }

//    // Generate a new driver_vehicle_id (e.g., DV001)
//    private String generateNewId(Connection connection) throws SQLException {
//        String sql = "SELECT MAX(CAST(SUBSTRING(driver_vehicle_id, 3) AS UNSIGNED)) AS max_id FROM Driver_Vehicle";
//        try (PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()) {
//            if (resultSet.next()) {
//                int maxId = resultSet.getInt("max_id");
//                return String.format("DV%03d", maxId + 1);
//            } else {
//                return "DV001";
//            }
//        }
//    }
}