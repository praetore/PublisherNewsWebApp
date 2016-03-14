<%@ page import="com.publisher.webapp.data.User" %>
<%@ page import="java.util.List" %>
<%@ include file="view/top.inc" %>

<%@ include file="view/middle.inc" %>
    <a href="addUser.do">Create new user</a>
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
            out.println("<p>" + message + "</p>");
        } else {
    %>
    <h1>Users</h1>
    <ul>
        <%
            List users = (List) request.getAttribute("users");
            for (Object user : users) {
                User user1 = (User) user;
        %>
        <li>
            <a href="displayUser.do?id=<%=user1.getId()%>"><%=user1.getUsername()%></a>

        </li>
        <% } %>
    </ul>
    <% } %>
<%@ include file="view/bottom.inc" %>