package com.project.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// IMPORTANT: Replace these placeholder imports with your actual model and DAO classes
// import com.project.model.Task;
// import com.project.dao.TaskDAO;

/**
 * Servlet implementation class EmployeeDashboardServlet
 * Handles requests for the employee dashboard, fetching tasks assigned to the logged-in employee.
 */
@WebServlet("/EmployeeDashboardServlet")
public class EmployeeDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Placeholder class for Task (replace with your actual Task model)
    public static class Task {
        private int id;
        private String title;
        private String priority;
        private String status;
        private String dueDate;
        private String assignedTo; // Assuming this holds the employee identifier

        public Task(int id, String title, String priority, String status, String dueDate, String assignedTo) {
            this.id = id;
            this.title = title;
            this.priority = priority;
            this.status = status;
            this.dueDate = dueDate;
            this.assignedTo = assignedTo;
        }

        // Getters for JSP
        public int getId() { return id; }
        public String getTitle() { return title; }
        public String getPriority() { return priority; }
        public String getStatus() { return status; }
        public String getDueDate() { return dueDate; }
        public String getAssignedTo() { return assignedTo; }
    }

    // Placeholder method to simulate fetching tasks from a DAO
    private List<Task> getTasksByEmployeeId(String employeeId) {
        // In a real application, you would call: return new TaskDAO().getTasksByEmployeeId(employeeId);
        List<Task> tasks = new ArrayList<>();
        if (employeeId != null && employeeId.equals("employee123")) {
            tasks.add(new Task(1, "Develop Login Feature", "HIGH", "IN_PROGRESS", "2025-12-01", "employee123"));
            tasks.add(new Task(2, "Update Documentation", "LOW", "PENDING", "2025-11-20", "employee123"));
            tasks.add(new Task(3, "Refactor CSS Styling", "MEDIUM", "COMPLETED", "2025-11-10", "employee123"));
        } else {
             // Mock data for any other employee
             tasks.add(new Task(4, "Test Deployment Scripts", "HIGH", "PENDING", "2025-12-15", "employee999"));
        }
        return tasks;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 1. Authentication Check
        if (session == null || session.getAttribute("userId") == null || session.getAttribute("role") == null) {
            response.sendRedirect("index.jsp"); // Redirect to login page if not authenticated
            return;
        }

        String userId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        // 2. Authorization Check (Ensure only employees access this)
        if (!"employee".equalsIgnoreCase(role)) {
            // If they are logged in but not an employee, redirect to their correct dashboard
            response.sendRedirect("ManagerDashboardServlet");
            return;
        }

        // 3. Fetch Tasks for the specific employee
        // Replace this call with your actual DAO logic once implemented
        List<Task> assignedTasks = getTasksByEmployeeId(userId);

        // 4. Set tasks in the request scope
        request.setAttribute("tasks", assignedTasks);

        // 5. Forward to the employee dashboard JSP
        request.getRequestDispatcher("/employeeDashboard.jsp").forward(request, response);
    }
}