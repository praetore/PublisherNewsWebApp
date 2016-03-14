package com.publisher.webapp.web;

import com.publisher.webapp.data.SecureDigester;
import com.publisher.webapp.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.publisher.webapp.data.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by darryl on 14-4-14.
 */
public class LoginServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        User user = UserDAO.getInstance().findByUserName(username);
        if (user == null)
        {
            request.setAttribute("message", "Authentication failed.");
            dispatcher.forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        if (password == null)
        {
            request.setAttribute("message", "Authentication failed.");
            dispatcher.forward(request, response);
            return;
        }

        String passwordDigest = SecureDigester.digest(password);
        if (!user.getPassword().equals(passwordDigest))
        {
            request.setAttribute("message", "Authentication failed.");
            dispatcher.forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Long userId = user.getId();
        session.setAttribute("userId", userId);
        session.setAttribute("userLevel", user.getUserLevel());
        logger.info("Logged in as a " + user.getUserLevel());
        String url = "/";
        response.sendRedirect(url);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Logging in");
        dispatcher.forward(request, response);
    }
}
