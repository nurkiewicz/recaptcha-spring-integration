<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Comments</title>
	<link href="css/comments.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>Comments</h1>
<form action="comments.htm" method="POST">
	<fieldset>
		<legend>Add comment</legend>
		<label class="label" for="name">Name:</label><input name="name" id="name" class="required"/><br/>
		<label class="label" for="contents">Comment:</label><textarea name="contents" id="contents" rows="4" cols="60" class="required"></textarea><br/>
		<input type="submit" value="Add comment"/>
	</fieldset>
	<c:forEach var="comment" items="${comments}">
		<h2><c:out value="${comment.name}" escapeXml="true"/></h2>
		<h3>at ${comment.date}</h3>
		<c:out value="${comment.contents}" escapeXml="true"/>
		<hr/>
	</c:forEach>
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script src="js/comments.js"></script>
</body>
</html>