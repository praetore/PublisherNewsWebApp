<%@ include file="view/top.inc" %>
<%@ include file="view/middle.inc" %>

<p>
    Are you sure you want to delete this?
</p>

<form method="post">
    <input type="submit" name="delete-button" value="Delete" />
    <input type="submit" name="cancel-button" value="Cancel" />
    <input type="hidden" name="id" value="${params['id']}" />

</form>

<%@ include file="view/bottom.inc" %>