package com.academictrip.controller;

import com.academictrip.dao.DriverVehicleDAO;
import com.academictrip.dao.TripDAO;
import com.academictrip.model.DriverVehicle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AssignDriverVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String driverId = request.getParameter("driverId");
        String vehicleId = request.getParameter("vehicleId");
        String tripId = request.getParameter("tripId"); // Added trip ID parameter

//        DriverVehicle assignment = new DriverVehicle();
//        assignment.setDriverId(driverId);
//        assignment.setVehicleId(vehicleId);
//        assignment.setAssignmentStart(LocalDate.now());
//        assignment.setAssignmentEnd(LocalDate.now().plusDays(7));

        try {
        	 // Create Driver-Vehicle assignment
            DriverVehicleDAO dvDAO = new DriverVehicleDAO();
            DriverVehicle assignment = new DriverVehicle();
            assignment.setDriverId(driverId);
            assignment.setVehicleId(vehicleId);
            assignment.setAssignmentStart(LocalDate.now());
            assignment.setAssignmentEnd(LocalDate.now().plusDays(7)); // Example
            String dvId = dvDAO.insertAssignment(assignment);

            // Update Trip with assignment
            TripDAO tripDAO = new TripDAO();
            tripDAO.updateTripDriverVehicle(tripId, dvId);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/transport/error.jsp");
            return;
        }
        
        response.sendRedirect("/transport/viewAssignments.jsp");
    }
}