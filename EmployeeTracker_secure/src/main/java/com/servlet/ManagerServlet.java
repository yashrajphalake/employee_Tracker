package com.itco.pept.servlet;

import com.itco.pept.dao.TaskDAO;
import com.itco.pept.dao.UserDAO;
import com.itco.pept.model.Task;
import com.itco.pept.model.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles the Manager Dashboard logic.
 * Mapped to /Manager.
 */
@WebServlet("/Manager")
public class ManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null || 
            !"manager".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            // Should be caught by AuthFilter, but double-check role
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            User manager = (User) session.getAttribute("user");
            
            // 1. Fetch all assigned tasks for the manager's view
            List<Task> tasks = taskDAO.getTasksByManager(manager.getUserId());
            
            // 2. Fetch list of all employees for the task assignment form
            List<User> employees = taskDAO.getAllEmployees();
            
            // Set attributes for the JSP
            request.setAttribute("tasks", tasks);
            request.setAttribute("employees", employees);
            
            // Forward to the Manager Dashboard JSP
            request.getRequestDispatcher("/WEB-INF/jsp/managerDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error loading Manager Dashboard: " + e.getMessage());
            request.setAttribute("errorMessage", "Could not load dashboard data.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    // Handles POST requests, primarily for creating new tasks
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User manager = (User) session.getAttribute("user");
        
        // Get form parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String assignedToId = request.getParameter("assignedTo");
        String deadlineStr = request.getParameter("deadline");
        
        try {
            // Convert deadline string to Date object
            Date deadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadlineStr);
            
            Task newTask = new Task();
            newTask.setTitle(title);
            newTask.setDescription(description);
            newTask.setManagerId(manager.getUserId());
            newTask.setAssignedToId(assignedToId);
            newTask.setDeadline(deadline);
            
            // Save task to DB
            int taskId = taskDAO.createTask(newTask);
            
            if (taskId > 0) {
                // Successful task creation
                session.setAttribute("successMessage", "Task '" + title + "' created and assigned successfully!");
            } else {
                session.setAttribute("errorMessage", "Failed to create task in the database.");
            }
        } catch (Exception e) {
            System.err.println("Task creation error: " + e.getMessage());
            session.setAttribute("errorMessage", "Invalid input or server error: " + e.getMessage());
        }
        
        // Redirect back to the GET method to refresh the dashboard data
        response.sendRedirect("Manager");
    }
}