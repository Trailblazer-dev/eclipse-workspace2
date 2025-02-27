package com.academictrip.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.academictrip.dao.UserDAO;
import com.academictrip.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on role
            if ("lecturer".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("lecturer/addTrip.jsp");
            } else if ("transport".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("transport/index.jsp");
            } else {
                response.sendRedirect("loginError.jsp");
            }
        } else {
            response.sendRedirect("loginError.jsp");
        }
    }
}
