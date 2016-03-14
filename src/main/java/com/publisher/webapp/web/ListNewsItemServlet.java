package com.publisher.webapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.publisher.webapp.data.NewsItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Gemaakt door Darryl op 13-4-2014.
 * Versienummer: 1.0
 */
public class ListNewsItemServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Fetching list of news items");
        List newsItems = NewsItemDAO.getInstance().findAll();
        request.setAttribute("newsItems", newsItems);
        dispatcher.forward(request, response);
    }
}
