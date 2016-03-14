<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />

<%@ include file="view/top.inc" %>
<%@ include file="view/middle.inc" %>

<form method="post">
    <table>
        <tr>
            <td>Username</td>
            <td><input type="text" name="username" value="${user.username}" size="50" />
                <%
                    if (errors.containsKey("username")) {
                        out.println("<span class=\"error\">" + errors.get("username") + "</span>");
                    }
                %>
            </td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" size="50" />
                <%
                    if (errors.containsKey("password")) {
                        out.println("<span class=\"error\">" + errors.get("password") + "</span>");
                    }
                %>
            </td>
        </tr>
        <tr>
            <td>Permission level</td>
            <td><input type="radio" name="userlevel" value="publisher">publisher<br>
                <input type="radio" name="userlevel" value="admin">admin
                <%
                    if (errors.containsKey("userlevel")) {
                        out.println("<span class=\"error\">" + errors.get("userlevel") + "</span>");
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
    <input type="hidden" name="id" value="${user.id}" />

</form>

<%@ include file="view/bottom.inc" %>