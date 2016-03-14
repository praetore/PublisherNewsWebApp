<jsp:useBean id="user" scope="request" type="com.publisher.webapp.data.User"/>
<%@ include file="view/top.inc" %>
<a href="listUsers.do">Back</a>
<a href="editUser.do?id=${user.id}">Edit</a>
<a href="deleteUser.do?id=${user.id}">Delete</a>

<%@ include file="view/middle.inc" %>

<table>
    <tr>
        <td>Username:</td>
        <td>${user.username}</td>

    </tr>
    <tr>
        <td>Permissions level:</td>
        <td>${user.userLevel}</td>
    </tr>
</table>

<%@ include file="view/bottom.inc" %>