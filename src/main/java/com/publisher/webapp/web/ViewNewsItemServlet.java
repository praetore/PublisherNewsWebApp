package com.publisher.webapp.web;

import com.publisher.webapp.data.NewsItem;
import com.publisher.webapp.data.NewsItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Darryl on 13-4-2014.
 */
public class ViewNewsItemServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Displaying news item admin page");
        String idString = request.getParameter("id");
        Long id = new Long(idString);
        NewsItem newsItem = NewsItemDAO.getInstance().find(id);
        if (newsItem == null) {
            response.sendRedirect("list-news-items");
            return;
        }
        request.setAttribute("newsItem", newsItem);
        dispatcher.forward(request, response);
    }
}
