package com.academictrip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.academictrip.model.Incharge;
import com.academictrip.model.InchargeGroup;
import com.academictrip.model.TripGroup;
import com.academictrip.util.DatabaseUtil;

public class InchargeGroupDAO {

    // Generate incharge_group_id (e.g., ING001) - Fixed prefix from IGP to ING
    private String generateInchargeGroupId() throws SQLException {
        String prefix = "ING"; // Changed from "IGP" to "ING" to match expected format
        String sql = "SELECT MAX(incharge_group_id) AS max_id FROM Incharge_Group";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String maxId = rs.getString("max_id");
                if (maxId == null) {
                    return prefix + "001";
                }
                int numericPart = Integer.parseInt(maxId.replace(prefix, ""));
                return String.format("%s%03d", prefix, numericPart + 1);
            }
            return prefix + "001";
        }
    }

    // Get incharge group by ID with incharge and trip group loaded
    public InchargeGroup getInchargeGroupById(String inchargeGroupId) {
        String sql = "SELECT ig.*, g.group_name, g.student_number, g.course_id " +
                     "FROM Incharge_Group ig " +
                     "JOIN Trip_Group g ON ig.group_id = g.group_id " +
                     "WHERE ig.incharge_group_id = ?";

        InchargeGroup inchargeGroup = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inchargeGroupId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    inchargeGroup = new InchargeGroup();
                    inchargeGroup.setInchargeGroupId(rs.getString("incharge_group_id"));
                    inchargeGroup.setInchargeId(rs.getString("incharge_id"));
                    inchargeGroup.setGroupId(rs.getString("group_id"));

                    // Load the related Incharge
                    InchargeDAO inchargeDAO = new InchargeDAO();
                    Incharge incharge = inchargeDAO.getInchargeById(inchargeGroup.getInchargeId());
                    inchargeGroup.setIncharge(incharge);

                    // Create and set the TripGroup
                    TripGroup tripGroup = new TripGroup();
                    tripGroup.setGroupId(rs.getString("group_id"));
                    tripGroup.setGroupName(rs.getString("group_name"));
                    tripGroup.setStudentNumber(rs.getInt("student_number"));
                    tripGroup.setCourseId(rs.getString("course_id"));
                    inchargeGroup.setTripGroup(tripGroup);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving incharge group: " + e.getMessage());
            e.printStackTrace();
        }

        return inchargeGroup;
    }

    // Get all incharge groups with incharges loaded
    public List<InchargeGroup> getAllInchargeGroups() {
        String sql = "SELECT ig.*, g.group_name, g.student_number, g.course_id " +
                     "FROM Incharge_Group ig " +
                     "JOIN Trip_Group g ON ig.group_id = g.group_id " +
                     "ORDER BY g.group_name";

        List<InchargeGroup> inchargeGroups = new ArrayList<>();
        InchargeDAO inchargeDAO = new InchargeDAO();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InchargeGroup inchargeGroup = new InchargeGroup();
                inchargeGroup.setInchargeGroupId(rs.getString("incharge_group_id"));
                inchargeGroup.setInchargeId(rs.getString("incharge_id"));
                inchargeGroup.setGroupId(rs.getString("group_id"));

                // Load the related Incharge
                Incharge incharge = inchargeDAO.getInchargeById(inchargeGroup.getInchargeId());
                inchargeGroup.setIncharge(incharge);

                // Create and set the TripGroup
                TripGroup tripGroup = new TripGroup();
                tripGroup.setGroupId(rs.getString("group_id"));
                tripGroup.setGroupName(rs.getString("group_name"));
                tripGroup.setStudentNumber(rs.getInt("student_number"));
                tripGroup.setCourseId(rs.getString("course_id"));
                inchargeGroup.setTripGroup(tripGroup);

                inchargeGroups.add(inchargeGroup);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all incharge groups: " + e.getMessage());
            e.printStackTrace();
        }

        return inchargeGroups;
    }

    // Add new incharge group
    public boolean addInchargeGroup(InchargeGroup inchargeGroup) {
        String sql = "INSERT INTO Incharge_Group (incharge_group_id, incharge_id, group_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inchargeGroup.getInchargeGroupId());
            stmt.setString(2, inchargeGroup.getInchargeId());
            stmt.setString(3, inchargeGroup.getGroupId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding incharge group: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Update incharge group
    public boolean updateInchargeGroup(InchargeGroup inchargeGroup) {
        String sql = "UPDATE Incharge_Group SET incharge_id = ?, group_id = ? WHERE incharge_group_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inchargeGroup.getInchargeId());
            stmt.setString(2, inchargeGroup.getGroupId());
            stmt.setString(3, inchargeGroup.getInchargeGroupId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating incharge group: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete incharge group
    public boolean deleteInchargeGroup(String inchargeGroupId) {
        String sql = "DELETE FROM Incharge_Group WHERE incharge_group_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inchargeGroupId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting incharge group: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Insert a new incharge group into the database
     * @param inchargeGroup InchargeGroup object to insert
     * @return The generated incharge_group_id
     * @throws SQLException if database error occurs
     */
    public String insertInchargeGroup(InchargeGroup inchargeGroup) throws SQLException {
        // Generate a new ID
        String inchargeGroupId = generateInchargeGroupId();
        inchargeGroup.setInchargeGroupId(inchargeGroupId);

        String sql = "INSERT INTO Incharge_Group (incharge_group_id, incharge_id, group_id) " +
                     "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inchargeGroup.getInchargeGroupId());
            stmt.setString(2, inchargeGroup.getInchargeId());
            stmt.setString(3, inchargeGroup.getGroupId());

            stmt.executeUpdate();

            System.out.println("Inserted InchargeGroup with ID: " + inchargeGroupId);
            return inchargeGroupId;
        }
    }
}