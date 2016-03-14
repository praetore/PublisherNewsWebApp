package com.publisher.webapp.web;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.publisher.webapp.data.NewsItem;
import com.publisher.webapp.data.NewsItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darryl on 11-4-2014.
 */
public class NewsFeedServlet extends NewsServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("My Local News Feed");
        feed.setLink("http://localhost:8080/publisher/");
        feed.setDescription("This feed was created using ROME.");
        List<SyndEntry> entries = new ArrayList<SyndEntry>();

        List newsItems = NewsItemDAO.getInstance().findAll();
        for (Object newsItem1 : newsItems) {
            NewsItem newsItem = (NewsItem) newsItem1;
            String title = newsItem.getTitle();
            String url = newsItem.getUrl();
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(title);
            entry.setLink(url);
            entries.add(entry);
        }

        response.setContentType("text/xml");

        feed.setEntries(entries);
        Writer writer = response.getWriter();
        SyndFeedOutput output = new SyndFeedOutput();
        try {
            output.output(feed, writer);
        } catch (FeedException e) {
            logger.error("Failed to write RSS response", e);
        }
    }
}
