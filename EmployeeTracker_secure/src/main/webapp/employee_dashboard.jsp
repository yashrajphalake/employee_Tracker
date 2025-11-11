<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Dashboard - PEPT</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .inter-font { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-gray-50 inter-font min-h-screen">

    <div class="container mx-auto p-4 sm:p-8">
        <!-- Header -->
        <header class="flex justify-between items-center pb-6 border-b border-gray-200 mb-8">
            <h1 class="text-3xl font-bold text-gray-800">PEPT - Employee Portal</h1>
            <div class="flex items-center space-x-4">
                <span class="text-lg text-gray-600 font-medium">Welcome, ${employeeName}!</span>
                <a href="${pageContext.request.contextPath}/logout" class="px-4 py-2 bg-red-600 text-white rounded-lg shadow hover:bg-red-700 transition duration-150 ease-in-out">
                    Logout
                </a>
            </div>
        </header>

        <!-- Main Dashboard Content -->
        <main>
            <h2 class="text-2xl font-semibold text-gray-700 mb-6">Your Assigned Tasks</h2>

            <!-- Task List Table -->
            <div class="bg-white p-6 rounded-xl shadow-lg overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                        <tr>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Due Date</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Priority</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <c:forEach var="task" items="${assignedTasks}">
                            <tr class="hover:bg-gray-100 transition duration-75">
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${task.taskId}</td>
                                <td class="px-6 py-4 text-sm text-gray-600">${task.title}</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                                    <fmt:formatDate value="${task.dueDate}" pattern="MMM d, yyyy" />
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full 
                                        <c:choose>
                                            <c:when test="${task.priority eq 'High'}">bg-red-100 text-red-800</c:when>
                                            <c:when test="${task.priority eq 'Medium'}">bg-yellow-100 text-yellow-800</c:when>
                                            <c:otherwise>bg-green-100 text-green-800</c:otherwise>
                                        </c:choose>
                                    ">
                                        ${task.priority}
                                    </span>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full 
                                        <c:choose>
                                            <c:when test="${task.status eq 'Completed'}">bg-teal-100 text-teal-800</c:when>
                                            <c:when test="${task.status eq 'In Progress'}">bg-blue-100 text-blue-800</c:when>
                                            <c:otherwise>bg-gray-200 text-gray-700</c:otherwise>
                                        </c:choose>
                                    ">
                                        ${task.status}
                                    </span>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                    <a href="#" class="text-indigo-600 hover:text-indigo-900 mr-4">View</a>
                                    <a href="#" class="text-green-600 hover:text-green-900">Update</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty assignedTasks}">
                             <tr>
                                <td colspan="6" class="px-6 py-8 text-center text-gray-500">You currently have no tasks assigned.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

            <div class="mt-8 text-center p-6 bg-blue-50 rounded-lg border border-blue-200">
                <p class="text-blue-800 font-medium">
                    This is your personalized Employee View. You can only see tasks assigned to you.
                </p>
            </div>
        </main>
    </div>
</body>
</html>