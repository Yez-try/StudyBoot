<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/boot.jsp"></c:import>
<c:import url="../temp/summer.jsp"></c:import>
</head>
<body>
	<h1>Board Update Page</h1>

	<div>
		<form action="./update" method="post" enctype="multipart/form-data">
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" placeholder="title을 입력하세요" value="${qnaVO.title}">
				<label for="writer">작성자${qnaVO.writer}</label>
				<input type="text" name="writer" id="writer" placeholder="작성자 입력하세요" value="${qnaVO.writer}">
				<textarea id="contents" name="contents">${qnaVO.contents}</textarea>
			</div>	
			<div id="divFiles">
			</div>	
			<div>
				<button type="button" id="fileAddBtn">FileAdd</button>
			</div>
			
			<button type="submit">작성하기</button>
		</form>
	</div>
	<script>
      $('#contents').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 100
      });
    </script>
</body>
</html>