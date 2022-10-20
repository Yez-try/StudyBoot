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
		<div><a href="/file/qna/${file.fileName}">이미지 파일</a></div>
	</c:forEach>

</body>
</html>