<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Manage Drivers</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center h-screen">
    <div class="max-w-lg w-full bg-white shadow-lg rounded-lg p-8">
        <h1 class="text-2xl font-bold text-center text-gray-800 mb-6">Driver Management</h1>
        <form action="AddDriverServlet" method="post" class="space-y-4">
            <div>
                <label for="firstName" class="block text-gray-700 font-medium mb-1">First Name</label>
                <input type="text" name="firstName" id="firstName" required 
                       class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300">
            </div>
            <div>
                <label for="lastName" class="block text-gray-700 font-medium mb-1">Last Name</label>
                <input type="text" name="lastName" id="lastName" required 
                       class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300">
            </div>
            <div>
                <label for="phone" class="block text-gray-700 font-medium mb-1">Phone</label>
                <input type="tel" name="phone" id="phone" required 
                       class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300">
            </div>
            <div>
                <label for="email" class="block text-gray-700 font-medium mb-1">Email</label>
                <input type="email" name="email" id="email" required 
                       class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300">
            </div>
            <div class="text-center">
                <input type="submit" value="Add Driver" 
                       class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-md transition duration-200">
            </div>
        </form>
    </div>
</body>
</html>
