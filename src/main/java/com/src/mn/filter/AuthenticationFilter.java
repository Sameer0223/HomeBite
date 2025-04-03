package com.src.mn.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*") // Applies filter to all URLs
public class AuthenticationFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String loginURI = httpRequest.getContextPath() + "/login.jsp";
        String registerURI = httpRequest.getContextPath() + "/register.jsp";
        String indexURI = httpRequest.getContextPath() + "/index.jsp";
        
        boolean loggedIn = (session != null && session.getAttribute("username") != null);
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI); 
        boolean registerRequest = httpRequest.getRequestURI().equals(registerURI);
        boolean indexRequest = httpRequest.getRequestURI().equals(indexURI);
        boolean loginServletRequest = httpRequest.getRequestURI().endsWith("/login");
        boolean registerServletRequest = httpRequest.getRequestURI().endsWith("/register");

        // Allow access if the user is logged in, or if the request is for public pages
        if (loggedIn || loginRequest || registerRequest || indexRequest || loginServletRequest || registerServletRequest) {
        	httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setDateHeader("Expires", 0);

            chain.doFilter(request, response); // Continue to the requested resource
        } else {
            httpResponse.sendRedirect(indexURI); // Redirect to login page if not logged in
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}
