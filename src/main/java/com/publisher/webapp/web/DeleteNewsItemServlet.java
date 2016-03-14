package com.publisher.webapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.publisher.webapp.data.NewsItem;
import com.publisher.webapp.data.NewsItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Darryl on 13-4-2014.
 */
public class DeleteNewsItemServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        String cancelButton = request.getParameter("cancel-button");
        if (cancelButton != null) {
            logger.debug("User cancelled edit");
            response.sendRedirect("view-news-item?id=" + idString);
            return;
        }

        Long id = new Long(idString);
        NewsItem newsItem = NewsItemDAO.getInstance().find(id);
        NewsItemDAO.getInstance().delete(newsItem);
        response.sendRedirect("list-news-items");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Removing news entry");
        dispatcher.forward(request, response);
    }
}
