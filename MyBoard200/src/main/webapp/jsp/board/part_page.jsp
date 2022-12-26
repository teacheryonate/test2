<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
    
<c:if test="${pt.curPage > 1}">
	<a href="/board/list.do?curPage=${pt.curPage-1}&criteria=${pt.criteria}&keyword=${pt.keyword}">&lt;</a>
</c:if>    
<c:forEach begin="${pt.beginPageNum}" end="${pt.finishPageNum}" var="i">
	<c:if test="${i != pt.curPage}">
		<a href="/board/list.do?curPage=${i}&criteria=${pt.criteria}&keyword=${pt.keyword}">${i}</a>
	</c:if>
	<c:if test="${i == pt.curPage}">
		<a style="color:red;" href="/board/list.do?curPage=${i}&criteria=${pt.criteria}&keyword=${pt.keyword}">${i}</a>
	</c:if>
</c:forEach>

<c:if test="${pt.curPage < pt.totalPage}">
	<a href="/board/list.do?curPage=${pt.curPage+1}&criteria=${pt.criteria}&keyword=${pt.keyword}">&gt;</a>
</c:if>