<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />

<%@ include file="view/top.inc" %>
<%@ include file="view/middle.inc" %>

<form method="post">
    <table>
        <tr>
            <td>Title</td>
            <td><input type="text" name="title" value="${newsItem.title}" size="50" />
                <%
                    if (errors.containsKey("title")) {
                        out.println("<span class=\"error\">" + errors.get("title") + "</span>");
                    }
                %>
            </td>
        </tr>
        <tr>
            <td>URL</td>
            <td><input type="text" name="url" value="${newsItem.url}" size="50" />
                <%
                    if (errors.containsKey("url")) {
                        out.println("<span class=\"error\">" + errors.get("url") + "</span>");
                    }
                %>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="submit-button" value="Submit" />
                <input type="submit" name="cancel-button" value="Cancel" />
            </td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${newsItem.id}" />

</form>

<%@ include file="view/bottom.inc" %>