<%@ include file="view/top.inc" %>

<a href="edit-news-item?id=${newsItem.id}">Edit</a>
<a href="delete-news-item?id=${newsItem.id}">Delete</a>

<%@ include file="view/middle.inc" %>

<table>
    <tr>
        <td>Title:</td>
        <td>${newsItem.title}</td>

    </tr>
    <tr>
        <td>Url:</td>
        <td>
            <a href="${newsItem.url}">${newsItem.url}</a>
        </td>
    </tr>
</table>

<%@ include file="view/bottom.inc" %>