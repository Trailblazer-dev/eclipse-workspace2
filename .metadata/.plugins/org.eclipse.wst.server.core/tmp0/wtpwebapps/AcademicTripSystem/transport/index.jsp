<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Transport Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
    <div class="max-w-4xl mx-auto p-6">
        <header class="mb-8">
            <h1 class="text-3xl font-bold text-center text-gray-800">Transport Management</h1>
        </header>
        <nav class="flex justify-center space-x-8 bg-white shadow rounded-lg p-4">
            <a href="manageDrivers.jsp" class="text-blue-500 hover:text-blue-700 font-semibold">
                Manage Drivers
            </a>
            <a href="manageVehicles.jsp" class="text-blue-500 hover:text-blue-700 font-semibold">
                Manage Vehicles
            </a>
            <a href="assignResources.jsp" class="text-blue-500 hover:text-blue-700 font-semibold">
                Assign Resources
            </a>
            <a href="assignDriverVehicle.jsp" class="text-blue-500 hover:text-blue-700 font-semibold">
                Assign Drivers
            </a>
            <a href="viewAssignments.jsp" class="text-blue-500 hover:text-blue-700 font-semibold">
                View Assignments
            </a>
            
        </nav>
    </div>
</body>
</html>
