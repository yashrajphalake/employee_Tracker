<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PEPT Login</title>
    <!-- Load Tailwind CSS for styling -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body { font-family: 'Inter', sans-serif; background-color: #f3f4f6; }
        /* Ensuring full screen background for the login page */
        .login-container { min-height: 100vh; }
        .login-card { transition: all 0.3s ease; }
        .login-card:hover { box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04); }
    </style>
</head>
<body class="login-container flex justify-center items-center">
    <div class="login-card bg-white p-8 md:p-10 rounded-2xl shadow-xl max-w-sm w-full">
        <h1 class="text-4xl font-extrabold text-indigo-600 mb-2 text-center">PEPT</h1>
        <h2 class="text-xl font-medium text-gray-700 mb-8 text-center">Project & Employee Tracker</h2>

        <!-- Error Message Display -->
        <c:if test="${not empty errorMessage}">
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
                <p class="font-bold">Login Failed</p>
                <p class="text-sm">${errorMessage}</p>
            </div>
        </c:if>

        <form action="Auth" method="POST" class="space-y-6">
            <input type="hidden" name="action" value="login">
            
            <div>
                <label for="email" class="block text-sm font-medium text-gray-700">Email Address</label>
                <input type="email" id="email" name="email" required 
                       class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500" 
                       placeholder="you@company.com">
            </div>

            <div>
                <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
                <input type="password" id="password" name="password" required 
                       class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500" 
                       placeholder="••••••••">
            </div>

            <button type="submit" 
                    class="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-md text-lg font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-150">
                Sign In
            </button>
        </form>
        
        <p class="mt-8 text-center text-sm text-gray-500">
            <span class="font-semibold text-indigo-500">Demo Credentials:</span>
            <br>
            Manager: manager@pept.com / 123
            <br>
            Employee: employee@pept.com / 123
        </p>
    </div>
</body>
</html>