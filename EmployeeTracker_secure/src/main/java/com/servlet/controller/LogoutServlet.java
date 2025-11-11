package com.servlet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogoutServlet.java
 * Handles user logout requests by invalidating the session.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles HTTP GET and POST requests for logout.
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // Do not create a new session
        if (session != null) {
            session.invalidate(); // Invalidate the session, removing all user data
        }
        
        // Redirect the user back to the login page after logout
        response.sendRedirect(request.getContextPath() + "/login");
    }
}