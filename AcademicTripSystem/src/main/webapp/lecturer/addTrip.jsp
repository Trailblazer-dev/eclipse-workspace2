<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Trip</title>
    <link rel="stylesheet" type="text/css" href="../css/styles.css">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-100">
    <div class="max-w-lg mx-auto my-10 p-6 bg-white shadow-lg rounded-lg">
        <h2 class="text-2xl font-semibold text-center text-gray-700 mb-4">Add New Trip</h2>
        <form action="${pageContext.request.contextPath}/AddTripServlet" method="post" class="space-y-4">
            <!-- Trip Details -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Start Date</label>
                <input type="date" name="startDate" required 
                       class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300  outline-none">
            </div>
            <div>
                <label class="block text-sm font-medium text-gray-600">End Date</label>
                <input type="date" name="endDate" required 
                       class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
            </div>
            <!-- Incharge Details -->
            <div class="grid grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-600">First Name</label>
                    <input type="text" name="inchargeFirstName" required 
                           class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-600">Last Name</label>
                    <input type="text" name="inchargeLastName" required 
                           class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
                </div>
            </div>
            <div>
                <label class="block text-sm font-medium text-gray-600">Phone</label>
                <input type="tel" name="inchargePhone" required 
                       class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
            </div>
            <div>
                <label class="block text-sm font-medium text-gray-600">Email</label>
                <input type="email" name="inchargeEmail" required 
                       class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
            </div>
            <!-- Group Details -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Group Name</label>
                <input type="text" name="groupName" required 
                       class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
            </div>
            <div>
                <label class="block text-sm font-medium text-gray-600">Student Count</label>
                <input type="number" name="studentCount" required 
                       class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
            </div>
            <!-- Course & Faculty -->
            <div class="grid grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-600">Course Name</label>
                    <input type="text" name="courseName" required 
                           class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-600">Faculty Name</label>
                    <input type="text" name="facultyName" required 
                           class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
                </div>
            </div>
            <!-- Destination -->
            <div>
                <label class="block text-sm font-medium text-gray-600">Destination</label>
                <input type="text" name="destinationName" required 
                       class="w-full mt-1 p-2 border rounded-md focus:ring focus:ring-blue-300 outline-none">
            </div>
            <!-- Submit Button -->
            <div class="text-center w-fit mx-auto">
                <button type="submit" 
                        class="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-700 transition">
                    Submit Trip
                </button>
            </div>
        </form>
    </div>
</body>
</html>
