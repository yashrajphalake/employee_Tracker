package com.itco.pept.servlet;

import com.itco.pept.dao.TaskDAO;
import com.itco.pept.model.Task;
import com.itco.pept.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles the Employee Dashboard logic.
 * Mapped to /Employee.
 */
@WebServlet("/Employee")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null || 
            !"employee".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            // Should be caught by AuthFilter, but double-check role
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            User employee = (User) session.getAttribute("user");
            
            // 1. Fetch tasks assigned to this employee
            List<Task> tasks = taskDAO.getTasksByEmployee(employee.getUserId());
            
            // Set attributes for the JSP
            request.setAttribute("tasks", tasks);
            
            // Forward to the Employee Dashboard JSP
            request.getRequestDispatcher("/WEB-INF/jsp/employeeDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error loading Employee Dashboard: " + e.getMessage());
            request.setAttribute("errorMessage", "Could not load dashboard data.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
    
    // Handles POST requests, primarily for updating task status
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // The task update logic should ideally be handled by a dedicated TaskServlet, 
        // but for simplicity, we'll include the status update here.
        String action = request.getParameter("action");
        if ("updateStatus".equals(action)) {
            handleStatusUpdate(request, response);
        } else {
            // If no action or invalid action, just redirect to refresh the view
            response.sendRedirect("Employee");
        }
    }

    private void handleStatusUpdate(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String taskIdStr = request.getParameter("taskId");
        String newStatus = request.getParameter("status");
        
        try {
            int taskId = Integer.parseInt(taskIdStr);
            
            // Update task status in DB
            boolean success = taskDAO.updateTaskStatus(taskId, newStatus);
            
            if (success) {
                session.setAttribute("successMessage", "Task status updated to: " + newStatus);
            } else {
                session.setAttribute("errorMessage", "Failed to update task status.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid Task ID provided.");
        } catch (Exception e) {
            System.err.println("Status update error: " + e.getMessage());
            session.setAttribute("errorMessage", "Server error during status update.");
        }
        
        // Redirect back to the GET method to refresh the dashboard data
        response.sendRedirect("Employee");
    }
}