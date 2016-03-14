package com.publisher.webapp.web;

import com.publisher.webapp.data.NewsItem;
import com.publisher.webapp.data.NewsItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Darryl on 13-4-2014.
 */
public class EditNewsItemServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        String cancelButton = request.getParameter("cancel-button");
        if (cancelButton != null) {
            logger.info("User cancelled edit");
            response.sendRedirect("view-news-item?id=" + id);
            return;
        }
        Map<String, String> errors = validate(request);
        if (!errors.isEmpty()) {
            logger.info("Edit errors! Can't post edits");
            dispatcher.forward(request, response);
            return;
        }

        NewsItem newsItem = (NewsItem) request.getAttribute("newsItem");
        NewsItemDAO.getInstance().update(newsItem);
        response.sendRedirect("view-news-item?id=" + id);
    }

    protected static Map<String, String> validate(HttpServletRequest request) {
        NewsItem newsItem = new NewsItem();
        HashMap<String, String> errors = new HashMap<String, String>();

        String idString = request.getParameter("id");
        if (idString != null && idString.length() > 0)
        {
            Long id = new Long(idString);
            newsItem.setId(id);
        }

        // title
        String title = request.getParameter("title");
        if (title == null || title.trim().length() == 0)
        {
            errors.put("title", "Title required.");
        }
        newsItem.setTitle(title);

        // url
        String url = request.getParameter("url");
        if (url == null || url.trim().length() == 0)
        {
            errors.put("url", "URL required.");
        }
        newsItem.setUrl(url);

        request.setAttribute("errors", errors);
        request.setAttribute("newsItem", newsItem);
        return errors;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Editing news item");
        String idString = request.getParameter("id");
        Long id = new Long(idString);
        NewsItem newsItem = NewsItemDAO.getInstance().find(id);
        request.setAttribute("newsItem", newsItem);
        dispatcher.forward(request, response);
    }
}
