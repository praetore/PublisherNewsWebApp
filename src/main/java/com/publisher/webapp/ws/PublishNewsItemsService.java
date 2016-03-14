package com.publisher.webapp.ws;

import com.publisher.webapp.data.NewsItem;
import com.publisher.webapp.data.NewsItemDAO;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * Created by Darryl on 20-4-2014.
 */
public class PublishNewsItemsService extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(PublishNewsItemsService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read the XML request document.
        BufferedReader bufferedReader = request.getReader();
        Document document;
        try {
            document = new SAXBuilder().build(bufferedReader);
        } catch (JDOMException e) {
            log.error("Can't read XML file", e);
            throw new RuntimeException(e);
        }

        // Extract title and link from request.
        Element element = document.getRootElement();
        Element titleElement = element.getChild("title");
        Element linkElement = element.getChild("link");
        String title = titleElement.getText();
        String link = linkElement.getText();

        // Create a news item from submitted data.
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(title);
        newsItem.setUrl(link);
        NewsItemDAO.getInstance().create(newsItem);

        // Create response document with id of newly created news item.
        Element idElement = new Element("id");
        idElement.addContent(String.valueOf(newsItem.getId()));
        Document responseDocument = new Document(idElement);
        StringWriter sw = new StringWriter();
        XMLOutputter outputter = new XMLOutputter();
        outputter.output(responseDocument, sw);
        String responseDocumentString = sw.toString();

        // Return response document.
        byte[] responseBytes = responseDocumentString.getBytes("UTF-8");
        response.setContentLength(responseBytes.length);
        response.setContentType("text/xml");
        OutputStream os = response.getOutputStream();
        os.write(responseBytes);
        os.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
