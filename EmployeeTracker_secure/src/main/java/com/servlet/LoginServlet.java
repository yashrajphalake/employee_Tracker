package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation for handling user login requests.
 * Mapped to the URL pattern /login.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles HTTP POST requests for user authentication.
     * @param request The HttpServletRequest object containing user credentials.
     * @param response The HttpServletResponse object for sending the response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 2. Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simple input validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            sendErrorResponse(response, out, "Username and password are required.");
            return;
        }

        // 3. Authenticate User (Placeholder Logic)
        if (isValidCredentials(username, password)) {
            // Authentication Success: Create a session and redirect
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(30 * 60); // Set session timeout to 30 minutes

            out.println("<html><body>");
            out.println("<h2 style='color: green;'>Login Successful!</h2>");
            out.println("<p>Welcome back, " + username + ".</p>");
            out.println("<p>You are being redirected to the dashboard...</p>");
            // In a real application, you would use response.sendRedirect("/dashboard");
            out.println("<a href='/dashboard'>Go to Dashboard</a>");
            out.println("</body></html>");

        } else {
            // Authentication Failure
            sendErrorResponse(response, out, "Invalid username or password. Please try again.");
        }
    }

    /**
     * Placeholder method for credential validation.
     * NOTE: In a production application, this would involve hashing the
     * password and checking against a database.
     */
    private boolean isValidCredentials(String username, String password) {
        // Dummy credentials for demonstration
        final String VALID_USER = "admin";
        final String VALID_PASS = "password123";

        return VALID_USER.equals(username) && VALID_PASS.equals(password);
    }

    /**
     * Helper method to send a standardized error message.
     */
    private void sendErrorResponse(HttpServletResponse response, PrintWriter out, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        out.println("<html><body>");
        out.println("<h2 style='color: red;'>Login Failed</h2>");
        out.println("<p>" + message + "</p>");
        out.println("<p><a href='/login.html'>Back to Login</a></p>");
        out.println("</body></html>");
    }
}