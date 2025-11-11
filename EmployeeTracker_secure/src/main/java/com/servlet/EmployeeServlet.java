package com.servlet;

import com.dao.TaskDAO;
import com.model.Task;
import com.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Employee")
public class EmployeeServlet extends HttpServlet {
    private TaskDAO taskDAO;

    @Override
    public void init() {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                try {
                    List<Task> tasks = taskDAO.getTasksByUserId(Integer.parseInt(user.getId()));
                    request.setAttribute("tasks", tasks);
                    request.getRequestDispatcher("/employee.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new ServletException("Database error in EmployeeServlet", e);
                }
                return;
            }
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String status = request.getParameter("status");

        try {
            taskDAO.updateTaskStatus(taskId, status);
            response.sendRedirect("Employee");
        } catch (SQLException e) {
            throw new ServletException("Database error while updating task status", e);
        }
    }
}
