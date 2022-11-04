<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
<c:import url="../temp/boot.jsp"></c:import>
<div class="container">
	<div class="row my-5">
		<div class="col text-center">
			<h3>로그인</h3>
		</div>
	</div>
		<div class="row my-5">
		<div class="col text-center text-danger">
			
			<h3>${param.message}</h3>
			${msg}
		</div>
	</div>
	<form id="joinForm" action="/member/login" method="post">
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="id">아이디</label>
			</div>
			<div class="col-5">
				<input name="id" id="id" type="text" value="${cookie.userId.value}">
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="password">비밀번호</label>
			</div>
			<div class="col-5">
				<input name="password" id="password" type="password">
			</div>
		</div>
				<div class="row justify-content-center mt-2">
			<div class="col-4">
				<label for="remember">아이디 기억하기</label>
			</div>
			<div class="col-3">
				<input name="remember" id="remember" class="form-check-input" type="checkbox">
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<button type="submit" class="btn btn-success">로그인</button>
			</div>
		</div>
	</form>

</div>


</body>
</html>