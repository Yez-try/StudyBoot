<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<c:import url="../temp/boot.jsp"></c:import>
</head>
<body>
	<div class="container mt-5">
		<div class="row">
			<h1>마이페이지</h1>
		</div>
		<sec:authentication property="Principal" var="user"/>
		<div class="row">
			<h3>ID : ${user.id} </h3>
			<h3>EMAIL : <sec:authentication property="Principal.email"/> </h3>
			<h3>놀랍게도 이게 name이 아닌 id임<sec:authentication property="name"/></h3>
		</div>
	</div>

</body>
</html>