<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<!doctype html>
<h1 class="logo" style="background:url(${pageContext.request.contextPath}/assets/images/logo.jpg)">JBlog</h1>
<ul class="menu">
<c:choose>
	<c:when test="${empty authUser }">
		<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
		<li><a href="${pageContext.request.contextPath}/user/join">회원가입</a></li>
	</c:when>
	<c:otherwise>
		<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
		<li><a href="${pageContext.request.contextPath}/${authUser.id }">내블로그</a></li>
	</c:otherwise>
</c:choose>
</ul>