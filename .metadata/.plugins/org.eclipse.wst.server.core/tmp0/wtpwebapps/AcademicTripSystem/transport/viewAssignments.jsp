<%@ page import="com.academictrip.dao.DriverVehicleDAO" %>
<%@ page import="com.academictrip.model.DriverVehicle" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Assignments</title>
    <link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>
<body>
    <h1>Driver and Vehicle Assignments</h1>
    <table border="1">
        <tr>
            <th>Trip ID</th>
            <th>Driver ID</th>
            <th>Vehicle ID</th>
        </tr>
        <%
            DriverVehicleDAO driverVehicleDAO = new DriverVehicleDAO();
            List<DriverVehicle> assignments = driverVehicleDAO.getAllAssignments();
            for (DriverVehicle assignment : assignments) {
        %>
        <tr>
            <td><%= assignment.getDriverId() %></td>
            <td><%= assignment.getVehicleId() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <br>
    <a href="../transport/assignDriverVehicle.jsp">Assign Driver and Vehicle</a>
</body>
</html>