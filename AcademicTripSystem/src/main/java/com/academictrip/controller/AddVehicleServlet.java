package com.academictrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.academictrip.dao.VehicleDAO;
import com.academictrip.model.Vehicle;

/**
 * Servlet implementation class AddVehicleServlet
 */
//@WebServlet("/AddVehicleServlet")
public class AddVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(request.getParameter("make"));
        vehicle.setModel(request.getParameter("model"));
        vehicle.setYear(LocalDate.parse(request.getParameter("year")));
        vehicle.setCapacity(Integer.parseInt(request.getParameter("capacity")));
        vehicle.setPlateNumber(request.getParameter("plate"));

        try {
            new VehicleDAO().insertVehicle(vehicle);
            response.sendRedirect(request.getContextPath()+"/transport/manageVehicles.jsp");
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
