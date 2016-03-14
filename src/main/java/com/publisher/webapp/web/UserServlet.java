package com.publisher.webapp.web;

import com.publisher.webapp.data.SecureDigester;
import com.publisher.webapp.data.User;
import com.publisher.webapp.data.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gemaakt door darryl op 15-4-14.
 * Versienummer: 1.0
 */
public class UserServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String userLevel = (String) session.getAttribute("userLevel");
        ServletContext context = request.getServletContext();
        String redirect = null;

        if (userLevel.equals("admin")) {
            if (userPath.equals("/deleteUser.do")) {
                String idString = request.getParameter("id");

                String cancelButton = request.getParameter("cancel-button");
                if (cancelButton != null) {
                    logger.debug("User cancelled removal");
                    redirect = "displayUser.do?id=" + idString;
                    response.sendRedirect(redirect);
                    return;
                }

                Long id = new Long(idString);
                User user = UserDAO.getInstance().find(id);
                UserDAO.getInstance().delete(user);
                redirect = "listUsers.do";
            } else if (userPath.equals("/addUser.do")) {
                String cancelButton = request.getParameter("cancel-button");
                if (cancelButton != null) {
                    logger.debug("Creating new entry cancelled by user");
                    response.sendRedirect("listUsers.do");
                    return;
                }
                Map<String, String> errors = validate(request);
                if (!errors.isEmpty()) {
                    logger.debug("Error while validating new entry");
                    dispatcher.forward(request, response);
                    return;
                }

                User user = (User) request.getAttribute("user");
                UserDAO.getInstance().create(user);
                redirect = "displayUser.do?id=" + user.getId();
            } else if (userPath.equals("/editUser.do")) {
                String id = request.getParameter("id");

                String cancelButton = request.getParameter("cancel-button");
                if (cancelButton != null) {
                    logger.debug("User cancelled edit");
                    response.sendRedirect("displayUser.do?id=" + id);
                    return;
                }
                Map<String, String> errors = validate(request);
                if (!errors.isEmpty()) {
                    logger.debug("Edit errors! Can't post edits");
                    dispatcher.forward(request, response);
                    return;
                }

                User user = (User) request.getAttribute("user");
                UserDAO.getInstance().update(user);
                redirect = "listUsers.do";
            }

            response.sendRedirect(redirect);
        } else {
            dispatcher = context.getRequestDispatcher("/WEB-INF/list-users.jsp");
            logger.debug("User not logged in as admin");
            String message = "Must be admin to perform action";
            request.setAttribute("message", message);
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String userLevel = (String) session.getAttribute("userLevel");
        ServletContext context = request.getServletContext();

        if (userLevel.equals("admin")) {
            if (userPath.equals("/listUsers.do")) {
                dispatcher = context.getRequestDispatcher("/WEB-INF/list-users.jsp");
                logger.debug("Fetching list of users");
                List users = UserDAO.getInstance().findAll();
                request.setAttribute("users", users);
            } else if (userPath.equals("/displayUser.do")) {
                dispatcher = context.getRequestDispatcher("/WEB-INF/view-user.jsp");
                String idString = request.getParameter("id");
                Long id = new Long(idString);
                User user = UserDAO.getInstance().find(id);
                if (user == null) {
                    response.sendRedirect("listUsers.do");
                    return;
                }
                request.setAttribute("user", user);
            } else if (userPath.equals("/editUser.do")) {
                dispatcher = context.getRequestDispatcher("/WEB-INF/add-user.jsp");
                logger.debug("Editing user");
                String idString = request.getParameter("id");
                Long id = new Long(idString);
                User user = UserDAO.getInstance().find(id);
                request.setAttribute("user", user);
            } else if (userPath.equals("/addUser.do")) {
                dispatcher = context.getRequestDispatcher("/WEB-INF/add-user.jsp");
                logger.debug("Adding new user");
            } else if (userPath.equals("/deleteUser.do")) {
                dispatcher = context.getRequestDispatcher("/WEB-INF/delete-news-item.jsp");
                logger.debug("Removing user");
            }
        } else {
            dispatcher = context.getRequestDispatcher("/WEB-INF/list-users.jsp");
            logger.debug("User not logged in as admin");
            String message = "Must be admin to perform action";
            request.setAttribute("message", message);
        }

        dispatcher.forward(request, response);
    }

    protected static Map<String, String> validate(HttpServletRequest request) {
        User user = new User();
        HashMap<String, String> errors = new HashMap<String, String>();

        String idString = request.getParameter("id");
        if (idString != null && idString.length() > 0)
        {
            Long id = new Long(idString);
            user.setId(id);
        }

        // username
        String username = request.getParameter("username");
        if (username == null || username.trim().length() == 0)
        {
            errors.put("username", "Username required.");
        }
        user.setUsername(username);

        // password
        String password = request.getParameter("password");
        if (password == null || password.trim().length() == 0)
        {
            errors.put("password", "Password required.");
        }
        user.setPassword(SecureDigester.digest(password));

        // userlevel
        String userlevel = request.getParameter("userlevel");
        if (userlevel == null || userlevel.trim().length() == 0)
        {
            errors.put("userlevel", "Permission level required.");
        }
        user.setUserLevel(userlevel);

        request.setAttribute("errors", errors);
        request.setAttribute("user", user);
        return errors;
    }
}
