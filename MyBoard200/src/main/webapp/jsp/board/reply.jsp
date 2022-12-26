<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 작성</title>
</head>
<body>
	<h1>답글 작성</h1>
	
<form action="/board/reply.do" method="post">
	<input type="hidden" name="orgNum" value="${orgNum}">
	<input type="hidden" name="curPage" value="${curPage}">
	<input type="hidden" name="criteria" value="${criteria}">
	<input type="hidden" name="keyword" value="${keyword}">
	
	
	
	author: <input name="author" required><br>
	pw : <input name="pw" required><br>
	title: <input name="title" required><br>
	
	content<br>
	<textarea rows="10" name="content" required></textarea>
	<br>
	
	<input type="submit" value="답글 등록">
	


</form>
	
	
</body>
</html>