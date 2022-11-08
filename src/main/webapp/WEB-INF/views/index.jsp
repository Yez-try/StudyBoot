<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!-- 다국어 설정 taglib jsp 에서 spring message를 사용할 수 있도록 함 -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- SPRING_SECURITY_CONTEXT를 쉽게 사용할 수 있도록 도와주는 taglib -->
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<c:import url="./temp/boot.jsp"></c:import>
	<link href="/css/test.css" rel="stylesheet"/>
	<script defer src="/js/test.js"></script>
</head>
<body>
	
	<h1>index</h1>
	<h1><spring:message code="hi" var="h"></spring:message></h1><!-- hi라는 코드의 message를 m 에 저장한다. 그리고 출력 안함-->
	<h1><spring:message code="test" text="code가 없을 때 기본 메세지"></spring:message></h1>
	<h2>${h}</h2>
	
	
	<!-- 로그인 성공 -->
	<!-- is는 보통 truefalse를 리턴 isAuthenticated()는 인증이 되었습니까? -->
	<sec:authentication property="Principal" var="member"/>
	<sec:authorize access="isAuthenticated()">
		<spring:message code="welcome" arguments="${member.name}"></spring:message>
		<spring:message code="welcome2" arguments="${member.id},${member.name}" argumentSeparator=","></spring:message>
		<!-- 카카오 로그아웃을 로그아웃successHandler에서 실행하도록 바꿔보자 
		<a href="https://kauth.kakao.com/oauth/logout?client_id=8916825993163e8fc4e9bc892c9224ce&logout_redirect_uri=http://localhost:81/member/logoutResult" >카카오로그아웃</a>
		-->
		<a href="#" id="logout">Logout</a>
		<form action="/out" method="post" id="outForm">
			<sec:csrfInput/>		
		</form>	
	</sec:authorize>
	<!-- 로그인 전 -->
	<sec:authorize access="!isAuthenticated()">
		<a href="/member/join">회원가입</a>
		<a href="/member/login">로그인</a>
		<a href="/oauth2/authorization/kakao">카카오로그인</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ADMIN')">
		<a href="/admin">어드민</a>
		<a href="/member/myPage">마이페이지</a>
	</sec:authorize>
	<hr>
	<!-- url경로가 Security Config설정에서 특정권한을 가진 URL로 등록되어 있는 경우 -->
	<sec:authorize url="/admin" var="ad">
		<a href="/admin">config에 등록되어있넹</a>
	</sec:authorize>
	
	<sec:authorize access="hasAnyRole('ADMIN','MANAGER')">
		<a href="/manager">매니저 GO</a>
	</sec:authorize>
	<%-- 앞으로 c 태그 대신 sec 태그를 사용할 것이다.	
	<c:choose>
		<c:when test="${not empty member}">	
			<spring:message code="welcome" arguments="${member.name}"></spring:message>
			<!-- 변수가 여러개 들어가는 경우 separator를 사용해 변수를 구분한다	 -->
			<spring:message code="welcome2" arguments="${member.id},${member.name}" argumentSeparator=","></spring:message>
			<a href="/member/logout">로그아웃</a>
		</c:when>
		<c:otherwise>	
			<a href="/member/login">로그인</a>
		</c:otherwise>
	</c:choose> --%>

	
	<br>
	
	<img src="/images/style.jpg">
	<a href="./qna/list?page=1&search=2&perPage=10">에스파는 나야 둘이 될 수 없어~</a>
	<a href="/fileDown/qna?fileNum=2">다운로드</a>
	<a href="/fileDown/notice?fileNum=2">notice download</a>
	
	<button type="button" id="btn">클릭</button>
	<button type="button" class="btn btns">버튼즈</button>
	<button type="button" class="btn btns">버튼즈</button>
	<button type="button" class="btn btns">버튼즈</button>
	
	<div id="test">
	
	</div>

	<script type="text/javascript">
		let result ='${param.result}';
		if(result!= ""){
			if(result=='1'){
				alert('회원가입 성공!');
			}else{
				alert('회원가입 실패');
			}
		}
		
		$("#logout").click(function() {
			$("#outForm").submit()
		})
	</script>

</body>
</html>