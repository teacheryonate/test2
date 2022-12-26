<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
</head>
<body>
<h1>게시글 등록</h1>
<form action="/board/insert.do" method="post">
	author: <input name="author" required><br>
	pw : <input name="pw" required><br>
	title: <input name="title" required><br>
	
	content<br>
	<textarea rows="10" name="content" required></textarea>
	<br>
	
	<input type="submit" value="등록">
	


</form>



</body>
</html>