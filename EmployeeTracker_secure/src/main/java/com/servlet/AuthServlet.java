package com.itco.pept.filter;

import com.itco.pept.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security Filter to intercept requests and ensure the user is logged in.
 * Prevents unauthorized access to dashboard Servlets.
 */
@WebFilter(urlPatterns = {"/Manager", "/Employee", "/Task", "/Report"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        
        // Exclude the login page and authentication servlet from filtering
        String loginURI = httpRequest.getContextPath() + "/index.jsp";
        String authURI = httpRequest.getContextPath() + "/Auth";
        
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI) || 
                                 httpRequest.getRequestURI().equals(authURI);
        
        if (loggedIn || isLoginRequest) {
            // User is logged in or requesting the login/auth page, so let the request proceed.
            chain.doFilter(request, response);
        } else {
            // User is not logged in and attempting to access a protected resource.
            System.out.println("Unauthorized access attempt. Redirecting to login.");
            httpResponse.sendRedirect(loginURI);
        }
    }
    
    // Required Filter methods
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}