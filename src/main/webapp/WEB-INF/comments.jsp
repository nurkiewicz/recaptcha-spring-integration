<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Comments</title>
</head>
<body>
<h1>Comments</h1>
<form action="comments.htm" method="POST">
	<fieldset>
		<legend>Add comment</legend>
		<label>Name: <input name="name"/></label><br/>
		<label>Comment: <textarea name="contents" rows="4" cols="60"></textarea> </label><br/>
		<input type="submit" value="Add comment"/>
	</fieldset>
	<c:forEach var="comment" items="${comments}">
		<h2>${comment.name} on ${comment.date}</h2>
		${comment.contents}
		<hr/>
	</c:forEach>
</form>
</body>
</html>