package com.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/Manager", "/Employee", "/Task", "/Report"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        
        String loginURI = httpRequest.getContextPath() + "/index.jsp";
        String authURI = httpRequest.getContextPath() + "/Auth";
        
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI) || 
                                 httpRequest.getRequestURI().equals(authURI);
        
        if (loggedIn || isLoginRequest) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(loginURI);
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
public void destroy() {}
}