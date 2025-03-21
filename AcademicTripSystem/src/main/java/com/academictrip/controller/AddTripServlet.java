package com.academictrip.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.academictrip.dao.CourseDAO;
import com.academictrip.dao.DestinationDAO;
import com.academictrip.dao.FacultyDAO;
import com.academictrip.dao.InchargeDAO;
import com.academictrip.dao.InchargeGroupDAO;
import com.academictrip.dao.TripDAO;
import com.academictrip.dao.TripGroupDAO;
import com.academictrip.model.Course;
import com.academictrip.model.Destination;
import com.academictrip.model.Faculty;
import com.academictrip.model.Incharge;
import com.academictrip.model.InchargeGroup;
import com.academictrip.model.Trip;
import com.academictrip.model.TripGroup;

//
//@WebServlet("/AddTripServlet")

public class AddTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all form parameters
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String inchargeFirstName = request.getParameter("inchargeFirstName");
        String inchargeLastName = request.getParameter("inchargeLastName");
        String inchargePhone = request.getParameter("inchargePhone");
        String inchargeEmail = request.getParameter("inchargeEmail");
        String groupName = request.getParameter("groupName");
        String studentCountStr = request.getParameter("studentCount");
        String courseName = request.getParameter("courseName");
        String facultyName = request.getParameter("facultyName");
        String destinationName = request.getParameter("destinationName");

        // Validate required fields
        if (startDate == null || endDate == null || inchargeFirstName == null ||
            inchargeLastName == null || inchargePhone == null || inchargeEmail == null ||
            groupName == null || studentCountStr == null || courseName == null ||
            facultyName == null || destinationName == null) {

            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("/lecturer/addTrip.jsp").forward(request, response);
            return;
        }

        int studentCount;
        try {
            studentCount = Integer.parseInt(studentCountStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid student count");
            request.getRequestDispatcher("/lecturer/addTrip.jsp").forward(request, response);
            return;
        }

        try {
            System.out.println("=== STARTING TRIP CREATION ===");
            // 1. Faculty
            FacultyDAO facultyDAO = new FacultyDAO();
            Faculty faculty = facultyDAO.findFacultyByName(facultyName);
            if (faculty == null) {
                faculty = new Faculty();
                faculty.setName(facultyName);
                facultyDAO.insertFaculty(faculty); // Auto-generates faculty_id
            }

            // 2. Course
            CourseDAO courseDAO = new CourseDAO();
            Course course = courseDAO.findCourseByName(courseName);
            if (course == null) {
                course = new Course();
                course.setCourseName(courseName);
                course.setFacultyId(faculty.getFacultyId());
                courseDAO.insertCourse(course); // Auto-generates course_id
            }

            // 3. Incharge
            InchargeDAO inchargeDAO = new InchargeDAO();
            Incharge incharge = inchargeDAO.findInchargeByEmail(inchargeEmail);
            if (incharge == null) {
                incharge = new Incharge();
                incharge.setFirstName(inchargeFirstName);
                incharge.setLastName(inchargeLastName);
                incharge.setPhoneNumber(Integer.parseInt(inchargePhone));
                incharge.setEmail(inchargeEmail);
                incharge.setFacultyId(faculty.getFacultyId());
                inchargeDAO.insertIncharge(incharge); // Auto-generates incharge_id
            }


            // 4. Destination
            DestinationDAO destinationDAO = new DestinationDAO();
            Destination destination = destinationDAO.findDestinationByName(destinationName);
            if (destination == null) {
                destination = new Destination();
                destination.setName(destinationName);
                destinationDAO.insertDestination(destination); // Auto-generates destination_id
            }

            // 5. Trip_Group
            TripGroupDAO tripGroupDAO = new TripGroupDAO();
            TripGroup tripGroup = new TripGroup();
            tripGroup.setGroupName(groupName);
            tripGroup.setStudentNumber(studentCount);
            tripGroup.setCourseId(course.getCourseId());
            tripGroupDAO.insertTripGroup(tripGroup);

            // 6. Incharge_Group - MODIFIED TO ENSURE ID IS GENERATED
            InchargeGroupDAO inchargeGroupDAO = new InchargeGroupDAO();
            InchargeGroup inchargeGroup = new InchargeGroup();
            inchargeGroup.setInchargeId(incharge.getInchargeId());
            inchargeGroup.setGroupId(tripGroup.getGroupId());

            // Make sure to generate and set the incharge_group_id
            String inchargeGroupId = inchargeGroupDAO.insertInchargeGroup(inchargeGroup);

            // Explicitly set the incharge_group_id on our object
            inchargeGroup.setInchargeGroupId(inchargeGroupId);

            // Print debug information
            System.out.println("Generated incharge_group_id: " + inchargeGroupId);

            // Convert String dates to LocalDate objects
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startLocalDate = LocalDate.parse(startDate, formatter);
            LocalDate endLocalDate = LocalDate.parse(endDate, formatter);

            // 7. Trip
            TripDAO tripDAO = new TripDAO();
            Trip trip = new Trip();
            trip.setStartDate(startLocalDate);
            trip.setEndDate(endLocalDate);

            // Debug to verify incharge_group_id is not null
            System.out.println("Setting incharge_group_id on Trip: " + inchargeGroup.getInchargeGroupId());

            // Ensure we're setting the incharge_group_id properly
            trip.setInchargeGroupId(inchargeGroup.getInchargeGroupId());
            trip.setDestinationId(destination.getDestinationId());

            tripDAO.insertTrip(trip); // This now generates trip_id

            // After successful creation, redirect to the dashboard
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Trip created successfully!");
            response.sendRedirect(request.getContextPath() + "/lecturer/dashboard.jsp");

            // After each DAO operation
            System.out.println("Inserted Faculty: " + faculty);
            System.out.println("Inserted Course: " + course);
            System.out.println("Inserted Incharge: " + incharge);
            System.out.println("Inserted Destination: " + destination);

            System.out.println("=== TRIP CREATION SUCCESSFUL ===");

        } catch (SQLException e) {
            // Enhanced error logging
            System.err.println("SQL Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("errorMessage", "Failed to create trip: " + e.getMessage());
            request.getRequestDispatcher("/lecturer/addTrip.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("errorMessage", "Failed to create trip: " + e.getMessage());
            request.getRequestDispatcher("/lecturer/addTrip.jsp").forward(request, response);
        }
    }
}