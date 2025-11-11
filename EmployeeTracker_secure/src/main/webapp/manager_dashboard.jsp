<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard - PEPT</title>
    <!-- Assuming Tailwind CSS is configured or loaded via CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .inter-font { font-family: 'Inter', sans-serif; }
        .priority-high { background-color: #fee2e2; color: #b91c1c; }
        .priority-medium { background-color: #fef9c3; color: #a16207; }
        .priority-low { background-color: #d1fae5; color: #047857; }
    </style>
</head>
<body class="bg-gray-50 inter-font min-h-screen">

    <div class="container mx-auto p-4 sm:p-8">
        <!-- Header -->
        <header class="flex justify-between items-center pb-6 border-b border-gray-200 mb-8">
            <h1 class="text-3xl font-bold text-gray-800">Project Executive Portal (PEPT)</h1>
            <div class="flex items-center space-x-4">
                <span class="text-lg text-gray-600 font-medium">Welcome, ${managerName}!</span>
                <a href="${pageContext.request.contextPath}/logout" class="px-4 py-2 bg-red-600 text-white rounded-lg shadow hover:bg-red-700 transition duration-150 ease-in-out">
                    Logout
                </a>
            </div>
        </header>

        <!-- Main Dashboard Content -->
        <main>
            <h2 class="text-2xl font-semibold text-gray-700 mb-6">Manager Dashboard: Active Tasks</h2>

            <!-- Task List Table -->
            <div class="bg-white p-6 rounded-xl shadow-lg overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                        <tr>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Task Title</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Priority</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <c:forEach var="task" items="${taskList}">
                            <tr class="hover:bg-gray-100 transition duration-75">
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${task.id}</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">${task.title}</td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-3 inline-flex text-xs leading-5 font-semibold rounded-full 
                                        <c:choose>
                                            <c:when test="${task.status eq 'Completed'}">bg-green-100 text-green-800</c:when>
                                            <c:when test="${task.status eq 'In Progress'}">bg-blue-100 text-blue-800</c:when>
                                            <c:otherwise>bg-yellow-100 text-yellow-800</c:otherwise>
                                        </c:choose>
                                    ">
                                        ${task.status}
                                    </span>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full 
                                        <c:choose>
                                            <c:when test="${task.priority eq 'High'}">priority-high</c:when>
                                            <c:when test="${task.priority eq 'Medium'}">priority-medium</c:when>
                                            <c:otherwise>priority-low</c:otherwise>
                                        </c:choose>
                                    ">
                                        ${task.priority}
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty taskList}">
                             <tr>
                                <td colspan="4" class="px-6 py-8 text-center text-gray-500">No active tasks found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

            <!-- Future: Charts and Analytics would go here -->
            <div class="mt-8 text-center p-6 bg-yellow-50 rounded-lg border border-yellow-200">
                <p class="text-yellow-800 font-medium">
                    This is the secure Manager View. Next steps could involve fetching real data via **TaskDAO** and implementing the chart rendering logic (using Chart.js, as noted in your roadmap).
                </p>
            </div>
        </main>
    </div>
</body>
</html>