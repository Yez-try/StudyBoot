<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="../temp/boot.jsp"></c:import>
	
	<div>${qnaVO.title}</div>
	<div>${qnaVO.regDate}</div>
	<div>
	</div>
	<c:forEach items="${qnaVO.fileVOs}" var="file">
		<div>보여주기</div>
		<div><img alt="" src="/file/qna/${file.fileName}"></div>
		<div>다운로드 하기</div>
		<div><a href="/fileDown/qna?fileNum=${file.fileNum}">${file.oriName}</a></div>
	</c:forEach>

	<a href="/qna/update?num=${qnaVO.num}">쉊ㅇ하지</a>
	<button id="btnUpdate">수정하기</button>

	<script type="text/javascript">
		$("#btnUpdate").click(function(){
			alert("수정")
		})
	</script>
</body>
</html>