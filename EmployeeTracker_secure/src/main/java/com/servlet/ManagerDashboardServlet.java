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
 * Servlet implementation class ManagerDashboardServlet
 * Handles requests for the manager dashboard, fetching ALL tasks for oversight.
 */
@WebServlet("/ManagerDashboardServlet")
public class ManagerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Placeholder class for Task (copied from EmployeeDashboardServlet for consistency)
    // In a real project, this would be in its own model package.
    public static class Task {
        private int id;
        private String title;
        private String priority;
        private String status;
        private String dueDate;
        private String assignedTo; 

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

    // Placeholder method to simulate fetching ALL tasks from a DAO
    private List<Task> getAllTasks() {
        // In a real application, you would call: return new TaskDAO().getAllTasks();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Develop Login Feature", "HIGH", "IN_PROGRESS", "2025-12-01", "employee123"));
        tasks.add(new Task(2, "Update Documentation", "LOW", "PENDING", "2025-11-20", "employee123"));
        tasks.add(new Task(3, "Refactor CSS Styling", "MEDIUM", "COMPLETED", "2025-11-10", "employee123"));
        tasks.add(new Task(4, "Review Q3 Financials", "HIGH", "PENDING", "2025-12-15", "employee456"));
        tasks.add(new Task(5, "Plan Team Offsite", "MEDIUM", "COMPLETED", "2025-11-05", "employee789"));
        return tasks;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 1. Authentication Check
        if (session == null || session.getAttribute("userId") == null || session.getAttribute("role") == null) {
            response.sendRedirect("index.jsp"); // Redirect to login page if not authenticated
            return;
        }

        String role = (String) session.getAttribute("role");

        // 2. Authorization Check (Ensure only managers access this)
        if (!"manager".equalsIgnoreCase(role)) {
            // If they are logged in but not a manager, redirect to their correct dashboard
            response.sendRedirect("EmployeeDashboardServlet");
            return;
        }

        // 3. Fetch ALL Tasks
        // Replace this call with your actual DAO logic once implemented
        List<Task> allTasks = getAllTasks();

        // 4. Set tasks in the request scope
        request.setAttribute("tasks", allTasks);

        // 5. Forward to the manager dashboard JSP
        request.getRequestDispatcher("/managerDashboard.jsp").forward(request, response);
    }
}