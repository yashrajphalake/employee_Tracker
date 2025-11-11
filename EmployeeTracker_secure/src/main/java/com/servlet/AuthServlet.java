package com.servlet;

import com.dao.UserDAO;
import com.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Auth")
public class AuthServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userDAO.authenticate(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if ("manager".equals(user.getRole())) {
                    response.sendRedirect("manager.jsp");
                } else {
                    response.sendRedirect("employee.jsp");
                }
            } else {
                response.sendRedirect("index.jsp?error=true");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during authentication", e);
        }
    }
}
