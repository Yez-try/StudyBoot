<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<c:import url="../temp/boot.jsp"></c:import>
</head>
<body>
<div class="container">
	<div class="row my-5">
		<div class="col text-center">
			<h3>회원가입</h3>
		</div>
	</div>
	<form:form action="./join" method="post" enctype="" modelAttribute="memberVO">
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="id">아이디</label>
			</div>
			<div class="col-5">
				<form:input path="id" id="id" cssClass="valid" />
				<form:errors path="id" id="inputIdResult"></form:errors><!-- 백엔드에서 검증에 실패했을때 출력되는 부분 아이디부분에 문제가 발생했을때 여기에 출력할것임 -->
				<button type="button" id="idChkBtn">중복체크</button>
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="password">비밀번호</label>
			</div>
			<div class="col-5">
				<form:password path="password" id="password" cssClass="valid" />
				<form:errors path="password"></form:errors>
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="pwCheck">비밀번호 확인</label>
			</div>
			<div class="col-5">
				<form:password path="pwCheck" id="pwCheck" cssClass="valid" />
				<form:errors path="pwCheck"></form:errors>
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="name">이름</label>
			</div>
			<div class="col-5">
				<form:input path="name" id="name" cssClass="valid" />
				<div id="inputNameResult">
					<form:errors path="name"></form:errors>
				</div>
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="age">나이</label>
			</div>
			<div class="col-5">
				<form:input path="age" id="age" cssClass="valid" />
				<form:errors path="age"></form:errors>
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="birth">생일</label>
			</div>
			<div class="col-5">
				<form:input path="birth" id="birth" cssClass="valid" />
				<form:errors path="birth"></form:errors>
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<label for="email">이메일</label>
			</div>
			<div class="col-5">
				<form:input path="email" id="email" cssClass="valid"/>
				<form:errors path="email"></form:errors>
			</div>
		</div>
		<div class="row justify-content-center mt-2">
			<div class="col-2">
				<button type="submit" id="joinButton" class="btn btn-success">회원가입</button>
			</div>
		</div>
	</form:form>
	<!--약관 test-->
	<div class="row my-5">
		<div class="col text-center">
			ALL <input type="checkbox" id="all">
		</div>
		<div class="col text-center">
			동의1<input type="checkbox" class="check">
			<div>약관1</div>
		</div>
		<div class="col text-center">
			동의2<input type="checkbox" class="check">
			<div>약관2</div>
		</div>
		<div class="col text-center">
			동의3<input type="checkbox" class="check">
			<div>약관3</div>
		</div>
	</div>

</div>

</body>
</html>