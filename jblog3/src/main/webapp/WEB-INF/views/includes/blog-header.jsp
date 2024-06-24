<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>​
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<div id="header">
	<h1><a href="${pageContext.request.contextPath}/${id }">${blogVo.title }</a></h1>
	<ul>
	<c:choose>
		<c:when test="${empty authUser }">
			<li><a href="${pageContext.request.contextPath}/">메인</a></li>
			<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
		</c:when>
		<c:when test="${authUser.id != id}">
				<li><a href="${pageContext.request.contextPath}/">메인</a></li>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${pageContext.request.contextPath}/">메인</a></li>
			<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
			<li><a href="${pageContext.request.contextPath}/${id }/admin/basic">블로그 관리</a></li>
		</c:otherwise>
	</c:choose>
	</ul>
</div>