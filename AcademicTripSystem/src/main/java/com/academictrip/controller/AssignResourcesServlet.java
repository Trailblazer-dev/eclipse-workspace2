package com.academictrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.academictrip.dao.DriverVehicleDAO;
import com.academictrip.dao.TripDAO;
import com.academictrip.model.DriverVehicle;

/**
 * Servlet implementation class AssignResourcesServlet
 */
//@WebServlet("/AssignResourcesServlet")
public class AssignResourcesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tripId = request.getParameter("tripId");
        String driverId = request.getParameter("driverId");
        String vehicleId = request.getParameter("vehicleId");

        try {
            // Create assignment
            DriverVehicle assignment = new DriverVehicle();
            assignment.setDriverId(driverId);
            assignment.setVehicleId(vehicleId);
            assignment.setAssignmentStart(LocalDate.now());
            assignment.setAssignmentEnd(LocalDate.now().plusDays(7));

            String dvId = new DriverVehicleDAO().insertAssignment(assignment);

            // Update trip
            new TripDAO().updateTripDriverVehicle(tripId, dvId);

            response.sendRedirect(request.getContextPath() +"/transport/assignResources.jsp");
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
