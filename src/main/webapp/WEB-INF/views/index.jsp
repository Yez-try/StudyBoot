<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
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
	<a href="/member/join">회원가입</a>
	<c:choose>
		<c:when test="${not empty member}">	
			<a href="/member/logout">로그아웃</a>
		</c:when>
		<c:otherwise>	
			<a href="/member/login">로그인</a>
		</c:otherwise>
	</c:choose>
	
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
	</script>

</body>
</html>