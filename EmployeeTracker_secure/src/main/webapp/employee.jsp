<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <div class="container mx-auto p-4">
        <h1 class="text-2xl font-bold mb-4">Welcome, ${sessionScope.user.name}!</h1>

        <h2 class="text-xl font-bold mb-2">Your Tasks</h2>
        <table class="table-auto w-full">
            <thead>
                <tr>
                    <th class="px-4 py-2">ID</th>
                    <th class="px-4 py-2">Title</th>
                    <th class="px-4 py-2">Description</th>
                    <th class="px-4 py-2">Status</th>
                    <th class="px-4 py-2">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="task" items="${tasks}">
                    <tr>
                        <td class="border px-4 py-2">${task.id}</td>
                        <td class="border px-4 py-2">${task.title}</td>
                        <td class="border px-4 py-2">${task.description}</td>
                        <td class="border px-4 py-2">${task.status}</td>
                        <td class="border px-4 py-2">
                            <form action="Employee" method="post">
                                <input type="hidden" name="taskId" value="${task.id}">
                                <select name="status">
                                    <option value="Pending" ${task.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                    <option value="In Progress" ${task.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                                    <option value="Completed" ${task.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                </select>
                                <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
                                    Update
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>