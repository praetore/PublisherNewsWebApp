<%@ page import="com.publisher.webapp.data.NewsItem" %>
<jsp:useBean id="newsItems" scope="request" type="java.util.List" />
<%@ include file="view/top.inc" %>
<a href="create-news-item">Create News Item</a>
<%@ include file="view/middle.inc" %>
    <ul>
        <%
            for (Object newsItem1 : newsItems) {
                NewsItem newsItem = (NewsItem) newsItem1;
        %>
        <li>
            <a href="view-news-item?id=<%=newsItem.getId()%>"><%=newsItem.getTitle()%>
            </a>
            - <a href="<%=newsItem.getUrl()%>"><%=newsItem.getUrl()%>
        </a>
        </li>
        <% } %>
    </ul>
<%@ include file="view/bottom.inc" %>