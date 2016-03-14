package com.publisher.webapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by darryl on 14-4-14.
 */
public class SecurityFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String servletPath = req.getServletPath();
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        String userLevel = (String) session.getAttribute("userLevel");

        // Allow access to web service.
        if (servletPath.equals("/publish"))
        {
            chain.doFilter(req, resp);
            return;
        }
        // Allow access to login functionality.
        if (servletPath.equals("/login"))
        {
            chain.doFilter(req, resp);
            return;
        }
        // Allow access to news feed.
        if (servletPath.equals("/feed")) {
            chain.doFilter(req, resp);
            return;
        }
        // All other functionality requires authentication.
        if (userId != null) {
            // User is logged in.
            chain.doFilter(req, resp);
            return;
        }

        // Request is not authorized.
        resp.sendRedirect("login");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
