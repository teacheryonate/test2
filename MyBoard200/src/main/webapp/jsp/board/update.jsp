<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
<h1>게시글 수정</h1>
<form action="/board/update.do" method="post">
	<input type="hidden" name="curPage" value="${curPage}">
	<input type="hidden" name="criteria" value="${criteria}">
	<input type="hidden" name="keyword" value="${keyword}">
	
	num:<input name="num" value="${dto.num}" readonly><br>
	author: <input name="author" required value="${dto.author}"><br>
	pw : <input name="pw" required><br>
	title: <input name="title" required value="${dto.title}"><br>
	
	content<br>
	<textarea rows="10" name="content" required>${dto.content}</textarea>
	<br>
	
	<input type="submit" value="수정">
	


</form>



</body>
</html>