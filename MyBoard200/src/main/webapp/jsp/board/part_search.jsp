<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style type="text/css">
	.search{
		border: 1px solid gray;
		margin: 5px;
		padding: 5px;
	}
</style>

<div class="search">
	<form action="/board/list.do" method="get">
		<select name="criteria">
			<option value="title">제목</option>
			<option value="author">작성자</option>
			<option value="content">내용</option>
		</select>
		<input name="keyword" placeholder="검색어를 입력하세요"> 
		<input type="submit" value="검색">

	</form>
</div>
