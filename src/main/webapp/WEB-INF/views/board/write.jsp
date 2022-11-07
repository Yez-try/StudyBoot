<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- include libraries(jQuery, bootstrap) -->
<!-- <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->

<!-- include summernote css/js -->
<!-- <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script> -->

<c:import url="../temp/boot.jsp"></c:import>
<c:import url="../temp/summer.jsp"></c:import>
<script defer src="/js/boardWrite.js"></script>
</head>
<body>
	<h1>Board Write Page</h1>

	<div>
		<form action="./write" method="post" enctype="multipart/form-data">
			<sec:csrfInput/>
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" placeholder="title을 입력하세요">
				<label for="writer">작성자</label>
				<input type="text" name="writer" id="writer" placeholder="작성자 입력하세요">
				<textarea id="contents" name="contents"></textarea>
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