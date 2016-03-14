package com.publisher.webapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.publisher.webapp.data.NewsItem;
import com.publisher.webapp.data.NewsItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Darryl on 13-4-2014.
 */
public class AddNewsItemServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cancelButton = request.getParameter("cancel-button");
        if (cancelButton != null) {
            logger.debug("Creating new entry cancelled by user");
            response.sendRedirect("list-news-items");
            return;
        }
        Map<String, String> errors = EditNewsItemServlet.validate(request);
        if (!errors.isEmpty()) {
            logger.debug("Error while validating new entry");
            dispatcher.forward(request, response);
            return;
        }

        NewsItem newsItem = (NewsItem) request.getAttribute("newsItem");
        NewsItemDAO.getInstance().create(newsItem);
        response.sendRedirect("view-news-item?id=" + newsItem.getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Adding new news entry");
        dispatcher.forward(request, response);
    }
}
