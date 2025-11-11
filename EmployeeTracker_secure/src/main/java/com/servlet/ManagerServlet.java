package com.servlet;

import com.dao.TaskDAO;
import com.dao.UserDAO;
import com.model.Task;
import com.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Manager")
public class ManagerServlet extends HttpServlet {
    private TaskDAO taskDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        taskDAO = new TaskDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            List<User> employees = userDAO.getAllEmployees();
            request.setAttribute("tasks", tasks);
            request.setAttribute("employees", employees);
            request.getRequestDispatcher("/manager.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error in ManagerServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int assignedTo = Integer.parseInt(request.getParameter("assignedTo"));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setAssignedTo(assignedTo);

        try {
            taskDAO.createTask(task);
            response.sendRedirect("Manager");
        } catch (SQLException e) {
            throw new ServletException("Database error while creating a task", e);
        }
    }
}
