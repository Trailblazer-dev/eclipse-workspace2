package com.academictrip.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.academictrip.model.Course;
import com.academictrip.model.Destination;
import com.academictrip.model.Faculty;
import com.academictrip.model.Trip;
import com.academictrip.model.TripGroup;
import com.academictrip.util.DatabaseUtil;

public class TripDAO {

    // Generate the next trip ID (e.g., TRP001)
    private String generateTripId() throws SQLException {
        String prefix = "TRP";
        String sql = "SELECT MAX(trip_id) FROM Trip";
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

    // Insert a new trip
    public void insertTrip(Trip trip) throws SQLException {
        String tripId = generateTripId();
        trip.setTripId(tripId); // Set generated ID

        String sql = "INSERT INTO Trip (trip_id, start_date, end_date, incharge_group_id, destination_id) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trip.getTripId());
            // Convert LocalDate to SQL Date
            stmt.setDate(2, trip.getStartDate() != null ? Date.valueOf(trip.getStartDate()) : null);
            stmt.setDate(3, trip.getEndDate() != null ? Date.valueOf(trip.getEndDate()) : null);
            stmt.setString(4, trip.getInchargeGroupId());
            stmt.setString(5, trip.getDestinationId());

            stmt.executeUpdate();
        }
    }

    public boolean updateTripDriverVehicle(String tripId, String driverVehicleId) throws SQLException {
        String sql = "UPDATE Trip SET driver_vehicle_id = ? WHERE trip_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (driverVehicleId == null) {
                stmt.setNull(1, Types.VARCHAR);
            } else {
                stmt.setString(1, driverVehicleId);
            }

            stmt.setString(2, tripId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Update an existing trip in the database
     * @param trip The trip object with updated values
     * @return boolean indicating success or failure
     * @throws SQLException if a database access error occurs
     */
    public boolean updateTrip(Trip trip) throws SQLException {
        String sql = "UPDATE Trip SET start_date = ?, end_date = ?, incharge_group_id = ?, " +
                     "driver_vehicle_id = ?, destination_id = ? WHERE trip_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convert LocalDate to SQL Date
            stmt.setDate(1, trip.getStartDate() != null ? Date.valueOf(trip.getStartDate()) : null);
            stmt.setDate(2, trip.getEndDate() != null ? Date.valueOf(trip.getEndDate()) : null);
            stmt.setString(3, trip.getInchargeGroupId());
            stmt.setString(4, trip.getDriverVehicleId());
            stmt.setString(5, trip.getDestinationId());
            stmt.setString(6, trip.getTripId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Get all trips
    public List<Trip> getAllTrips() throws SQLException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM Trip";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setTripId(resultSet.getString("trip_id"));

                // Safe handling of start_date
                java.sql.Date sqlStartDate = resultSet.getDate("start_date");
                if (sqlStartDate != null) {
                    trip.setStartDate(sqlStartDate.toLocalDate());
                }

                // Safe handling of end_date
                java.sql.Date sqlEndDate = resultSet.getDate("end_date");
                if (sqlEndDate != null) {
                    trip.setEndDate(sqlEndDate.toLocalDate());
                }

                trip.setDestinationId(resultSet.getString("destination_id"));
                trip.setDriverVehicleId(resultSet.getString("driver_vehicle_id"));
                trip.setInchargeGroupId(resultSet.getString("incharge_group_id"));
                trips.add(trip);
            }
        }
        return trips;
    }

    public List<Trip> getUnassignedTrips() throws SQLException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM Trip WHERE driver_vehicle_id IS NULL";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trip trip = new Trip();
                trip.setTripId(rs.getString("trip_id"));

                // Safe handling of start_date
                java.sql.Date startDate = rs.getDate("start_date");
                if (startDate != null) {
                    trip.setStartDate(startDate.toLocalDate());
                }

                // Safe handling of end_date
                java.sql.Date endDate = rs.getDate("end_date");
                if (endDate != null) {
                    trip.setEndDate(endDate.toLocalDate());
                }

                trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                trip.setDestinationId(rs.getString("destination_id"));
                trips.add(trip);
            }
        }
        return trips;
    }

    public Trip getTripById(String tripId) throws SQLException {
        String sql = "SELECT t.*, d.name as destination_name FROM Trip t " +
                     "LEFT JOIN Destination d ON t.destination_id = d.destination_id " +
                     "WHERE t.trip_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tripId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Trip trip = new Trip();
                    trip.setTripId(rs.getString("trip_id"));

                    // Safe handling of dates
                    java.sql.Date startDate = rs.getDate("start_date");
                    if (startDate != null) {
                        trip.setStartDate(startDate.toLocalDate());
                    }

                    java.sql.Date endDate = rs.getDate("end_date");
                    if (endDate != null) {
                        trip.setEndDate(endDate.toLocalDate());
                    }

                    trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                    trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                    trip.setDestinationId(rs.getString("destination_id"));

                    // Set destination information if available
                    Destination destination = new Destination();
                    destination.setDestinationId(trip.getDestinationId());
                    destination.setName(rs.getString("destination_name"));
                    trip.setDestination(destination);

                    // Load additional trip data
                    loadTripRelatedData(trip);

                    return trip;
                }
            }
        }
        return null;
    }

    private void loadTripRelatedData(Trip trip) throws SQLException {
        // Load incharge group data if needed
        if (trip.getInchargeGroupId() != null) {
            InchargeGroupDAO inchargeGroupDAO = new InchargeGroupDAO();
            trip.setInchargeGroup(inchargeGroupDAO.getInchargeGroupById(trip.getInchargeGroupId()));
            
            // If we have an incharge group and it has a trip group
            if (trip.getInchargeGroup() != null && trip.getInchargeGroup().getTripGroup() != null) {
                TripGroup tripGroup = trip.getInchargeGroup().getTripGroup();
                
                // If the trip group has a courseId, load the course
                if (tripGroup.getCourseId() != null && !tripGroup.getCourseId().isEmpty()) {
                    CourseDAO courseDAO = new CourseDAO();
                    Course course = courseDAO.getCourseById(tripGroup.getCourseId());
                    
                    if (course != null) {
                        // Set course on trip and trip group
                        trip.setCourse(course);
                        tripGroup.setCourse(course);
                        
                        // If course has a facultyId, load the faculty
                        if (course.getFacultyId() != null && !course.getFacultyId().isEmpty()) {
                            FacultyDAO facultyDAO = new FacultyDAO();
                            Faculty faculty = facultyDAO.getFacultyById(course.getFacultyId());
                            
                            if (faculty != null) {
                                // Set faculty on trip
                                trip.setFaculty(faculty);
                            }
                        }
                    }
                }
            }
        }

        // Load driver-vehicle data if assigned
        if (trip.getDriverVehicleId() != null) {
            DriverVehicleDAO driverVehicleDAO = new DriverVehicleDAO();
            trip.setDriverVehicle(driverVehicleDAO.getDriverVehicleById(trip.getDriverVehicleId()));
        }

        // Load destination data if not already loaded
        if (trip.getDestination() == null && trip.getDestinationId() != null) {
            DestinationDAO destinationDAO = new DestinationDAO();
            trip.setDestination(destinationDAO.getDestinationById(trip.getDestinationId()));
        }
    }

    /**
     * Gets a mapping of driver-vehicle IDs to trip IDs
     * @return Map with driver-vehicle ID as key and trip ID as value
     * @throws SQLException if database error occurs
     */
    public Map<String, String> getDriverVehicleToTripMapping() throws SQLException {
        Map<String, String> dvToTripMap = new HashMap<>();
        String sql = "SELECT trip_id, driver_vehicle_id FROM Trip WHERE driver_vehicle_id IS NOT NULL";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tripId = rs.getString("trip_id");
                String dvId = rs.getString("driver_vehicle_id");
                dvToTripMap.put(dvId, tripId);
            }
        }

        return dvToTripMap;
    }

    /**
     * Assign resources (driver and vehicle) to a trip
     * @param tripId ID of the trip to assign resources to
     * @param driverVehicleId ID of the driver_vehicle assignment
     * @return true if assignment was successful, false otherwise
     */
    public boolean assignResources(String tripId, String driverVehicleId) {
        try {
            // Use the existing updateTripDriverVehicle method since that's what we're actually doing
            return updateTripDriverVehicle(tripId, driverVehicleId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all trips that have not been assigned to a driver and vehicle
     * @return List of unassigned trips
     * @throws SQLException if a database error occurs
     */
    public List<Trip> getAllUnassignedTrips() throws SQLException {
        List<Trip> unassignedTrips = new ArrayList<>();

        String sql = "SELECT t.*, d.name as destination_name " +
                     "FROM Trip t " +
                     "JOIN Destination d ON t.destination_id = d.destination_id " +
                     "WHERE t.driver_vehicle_id IS NULL";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trip trip = new Trip();
                trip.setTripId(rs.getString("trip_id"));

                // Convert java.sql.Date to java.time.LocalDate before setting
                java.sql.Date startDate = rs.getDate("start_date");
                if (startDate != null) {
                    trip.setStartDate(startDate.toLocalDate());
                }

                java.sql.Date endDate = rs.getDate("end_date");
                if (endDate != null) {
                    trip.setEndDate(endDate.toLocalDate());
                }

                trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                trip.setDestinationId(rs.getString("destination_id"));

                // Set the Destination object
                Destination destination = new Destination();
                destination.setDestinationId(rs.getString("destination_id"));
                destination.setName(rs.getString("destination_name"));
                trip.setDestination(destination);

                unassignedTrips.add(trip);
            }
        }

        return unassignedTrips;
    }

    /**
     * Get trips with assignments filtered by date range
     * @param startDateStr the start date for filtering (optional)
     * @param endDateStr the end date for filtering (optional)
     * @return List of trips with driver-vehicle assignments
     * @throws SQLException if a database error occurs
     */
    public List<Trip> getTripsWithAssignmentsByDate(String startDateStr, String endDateStr) throws SQLException {
        List<Trip> assignedTrips = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT t.*, d.name as destination_name " +
            "FROM Trip t " +
            "JOIN Destination d ON t.destination_id = d.destination_id " +
            "WHERE t.driver_vehicle_id IS NOT NULL");

        // Add date filters if provided
        if (startDateStr != null && !startDateStr.isEmpty()) {
            sql.append(" AND t.start_date >= ?");
        }

        if (endDateStr != null && !endDateStr.isEmpty()) {
            sql.append(" AND t.end_date <= ?");
        }

        sql.append(" ORDER BY t.start_date DESC");

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (startDateStr != null && !startDateStr.isEmpty()) {
                LocalDate parsedStartDate = LocalDate.parse(startDateStr);
                stmt.setDate(paramIndex++, Date.valueOf(parsedStartDate));
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                LocalDate parsedEndDate = LocalDate.parse(endDateStr);
                stmt.setDate(paramIndex++, Date.valueOf(parsedEndDate));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trip trip = new Trip();
                    trip.setTripId(rs.getString("trip_id"));

                    // Convert java.sql.Date to java.time.LocalDate before setting
                    java.sql.Date startDateVal = rs.getDate("start_date");
                    if (startDateVal != null) {
                        trip.setStartDate(startDateVal.toLocalDate());
                    }

                    java.sql.Date endDateVal = rs.getDate("end_date");
                    if (endDateVal != null) {
                        trip.setEndDate(endDateVal.toLocalDate());
                    }

                    trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                    trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                    trip.setDestinationId(rs.getString("destination_id"));

                    // Set the Destination object
                    Destination destination = new Destination();
                    destination.setDestinationId(rs.getString("destination_id"));
                    destination.setName(rs.getString("destination_name"));
                    trip.setDestination(destination);

                    assignedTrips.add(trip);
                }
            }
        }

        return assignedTrips;
    }

    /**
     * Get trips with assignments filtered by status and destination
     * @param statusFilter the status for filtering (optional)
     * @param destinationFilter the destination for filtering (optional)
     * @return List of trips with driver-vehicle assignments
     * @throws SQLException if a database error occurs
     */
    public List<Trip> getTripsWithAssignmentsByFilters(String statusFilter, String destinationFilter) throws SQLException {
        List<Trip> assignedTrips = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT t.*, d.name as destination_name " +
            "FROM Trip t " +
            "JOIN Destination d ON t.destination_id = d.destination_id ");

        // Build the WHERE clause
        List<String> whereClauses = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        // Include trips with assignments
        // Note: We don't restrict to only assigned trips - the UI will handle filtering by status

        // Add destination filter if provided
        if (destinationFilter != null && !destinationFilter.isEmpty()) {
            whereClauses.add("d.name = ?");
            parameters.add(destinationFilter);
        }

        // Add the WHERE clause if any conditions exist
        if (!whereClauses.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }

        sql.append(" ORDER BY t.start_date DESC");

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trip trip = new Trip();
                    trip.setTripId(rs.getString("trip_id"));

                    // Convert java.sql.Date to java.time.LocalDate before setting
                    java.sql.Date startDateVal = rs.getDate("start_date");
                    if (startDateVal != null) {
                        trip.setStartDate(startDateVal.toLocalDate());
                    }

                    java.sql.Date endDateVal = rs.getDate("end_date");
                    if (endDateVal != null) {
                        trip.setEndDate(endDateVal.toLocalDate());
                    }

                    trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                    trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                    trip.setDestinationId(rs.getString("destination_id"));

                    // Set the Destination object
                    Destination destination = new Destination();
                    destination.setDestinationId(rs.getString("destination_id"));
                    destination.setName(rs.getString("destination_name"));
                    trip.setDestination(destination);

                    assignedTrips.add(trip);
                }
            }
        }

        return assignedTrips;
    }

    /**
     * Get a list of unique destination names from all trips
     * @return List of unique destination names
     * @throws SQLException if a database error occurs
     */
    public List<String> getUniqueDestinations() throws SQLException {
        List<String> destinations = new ArrayList<>();
        String sql = "SELECT DISTINCT d.name FROM Destination d " +
                     "JOIN Trip t ON d.destination_id = t.destination_id " +
                     "ORDER BY d.name";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                destinations.add(rs.getString("name"));
            }
        }

        return destinations;
    }

    // Get trips by incharge ID
    public List<Trip> getTripsByInchargeId(String inchargeId) throws SQLException {
        List<Trip> trips = new ArrayList<>();

        String sql = "SELECT t.*, d.name as destination_name " +
                     "FROM Trip t " +
                     "JOIN Incharge_Group ig ON t.incharge_group_id = ig.incharge_group_id " +
                     "LEFT JOIN Destination d ON t.destination_id = d.destination_id " +
                     "WHERE ig.incharge_id = ? " +
                     "ORDER BY t.start_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inchargeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trip trip = new Trip();
                    trip.setTripId(rs.getString("trip_id"));

                    // Convert java.sql.Date to java.time.LocalDate
                    java.sql.Date startDate = rs.getDate("start_date");
                    if (startDate != null) {
                        trip.setStartDate(startDate.toLocalDate());
                    }

                    java.sql.Date endDate = rs.getDate("end_date");
                    if (endDate != null) {
                        trip.setEndDate(endDate.toLocalDate());
                    }

                    trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                    trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                    trip.setDestinationId(rs.getString("destination_id"));

                    // Set the destination name directly
                    Destination destination = new Destination();
                    destination.setDestinationId(trip.getDestinationId());
                    destination.setName(rs.getString("destination_name"));
                    trip.setDestination(destination);

                    trips.add(trip);
                }
            }
        }

        return trips;
    }

    /**
     * Get most recent trips by incharge ID with a limit
     * @param inchargeId ID of the incharge to filter by
     * @param limit maximum number of trips to return
     * @return List of most recent trips associated with the specified incharge
     * @throws SQLException if a database error occurs
     */
    public List<Trip> getRecentTripsByInchargeId(String inchargeId, int limit) throws SQLException {
        List<Trip> trips = new ArrayList<>();

        String sql = "SELECT t.*, d.name as destination_name " +
                     "FROM Trip t " +
                     "JOIN Incharge_Group ig ON t.incharge_group_id = ig.incharge_group_id " +
                     "LEFT JOIN Destination d ON t.destination_id = d.destination_id " +
                     "WHERE ig.incharge_id = ? " +
                     "ORDER BY t.start_date DESC " +
                     "LIMIT ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inchargeId);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trip trip = new Trip();
                    trip.setTripId(rs.getString("trip_id"));

                    // Convert java.sql.Date to java.time.LocalDate
                    java.sql.Date startDate = rs.getDate("start_date");
                    if (startDate != null) {
                        trip.setStartDate(startDate.toLocalDate());
                    }

                    java.sql.Date endDate = rs.getDate("end_date");
                    if (endDate != null) {
                        trip.setEndDate(endDate.toLocalDate());
                    }

                    trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                    trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                    trip.setDestinationId(rs.getString("destination_id"));

                    // Set the destination name directly
                    Destination destination = new Destination();
                    destination.setDestinationId(trip.getDestinationId());
                    destination.setName(rs.getString("destination_name"));
                    trip.setDestination(destination);

                    trips.add(trip);
                }
            }
        }

        return trips;
    }

    /**
     * Delete a trip by its ID
     * @param tripId The ID of the trip to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteTrip(String tripId) {
        String sql = "DELETE FROM Trip WHERE trip_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tripId);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get trips by lecturer ID
    public List<Trip> getTripsByLecturerId(String lecturerId) {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT t.*, d.name as destination_name " +
                     "FROM Trip t " +
                     "JOIN Incharge_Group ig ON t.incharge_group_id = ig.incharge_group_id " +
                     "JOIN Incharge i ON ig.incharge_id = i.incharge_id " +
                     "JOIN Destination d ON t.destination_id = d.destination_id " +
                     "WHERE i.incharge_id = ? " +
                     "ORDER BY t.start_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lecturerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trip trip = new Trip();
                    trip.setTripId(rs.getString("trip_id"));

                    // Convert java.sql.Date to java.time.LocalDate
                    java.sql.Date startDate = rs.getDate("start_date");
                    if (startDate != null) {
                        trip.setStartDate(startDate.toLocalDate());
                    }

                    java.sql.Date endDate = rs.getDate("end_date");
                    if (endDate != null) {
                        trip.setEndDate(endDate.toLocalDate());
                    }

                    trip.setInchargeGroupId(rs.getString("incharge_group_id"));
                    trip.setDriverVehicleId(rs.getString("driver_vehicle_id"));
                    trip.setDestinationId(rs.getString("destination_id"));

                    // Set the destination
                    Destination destination = new Destination();
                    destination.setDestinationId(rs.getString("destination_id"));
                    destination.setName(rs.getString("destination_name"));
                    trip.setDestination(destination);

                    trips.add(trip);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving trips for lecturer: " + e.getMessage());
            e.printStackTrace();
        }

        return trips;
    }

    /**
     * Get trips by lecturer ID (int version)
     * @param lecturerId ID of the lecturer as integer
     * @return List of trips for the lecturer
     */
    public List<Trip> getTripsByLecturerId(int lecturerId) {
        return getTripsByLecturerId(String.valueOf(lecturerId));
    }

    /**
     * Cancels a trip by updating its status in the database
     * @param tripId the ID of the trip to cancel
     * @return true if successfully canceled, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean cancelTrip(String tripId) throws SQLException {
        // First check if the trip exists and can be cancelled
        Trip trip = getTripById(tripId);
        if (trip == null) {
            return false;
        }
        
        // If trip is already assigned resources, it shouldn't be cancelled
        if (trip.getDriverVehicleId() != null && !trip.getDriverVehicleId().isEmpty()) {
            return false;
        }
        
        String sql = "UPDATE Trip SET status = 'CANCELLED' WHERE trip_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tripId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}