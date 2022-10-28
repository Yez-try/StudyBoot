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
<script defer src="/js/boardWrite.js"></script>
</head>
<body>
	<h1>Board Update Page</h1>

	<div>
		<form action="./update" method="post" enctype="multipart/form-data">
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" placeholder="title을 입력하세요" value="${qnaVO.title}">
				<label for="writer">작성자</label>
				<input type="text" name="writer" id="writer" placeholder="작성자 입력하세요" value="${qnaVO.writer}">
				<textarea id="contents" name="contents"></textarea>
			</div>	
			
			<c:forEach items="${qnaVO.fileVOs}" var="fls">
				<div id="showfiles">
					<img alt="" src="/file/qna/${fls.fileName}"style="height:300px;">
					<button type="button" class="fileDel" data-fNum="${fls.fileNum}">삭제하기</button>
				</div>
			</c:forEach>
            
			<div id="divFiles" data-file-size="${qnaVO.fileVOs.size()}">
			</div>	
			<div>
				<button type="button" id="fileAddBtn" >FileAdd</button>
			</div>
			
			<button type="submit">작성하기</button>
		</form>
	</div>
	<script>
      $('#contents').summernote('code','${qnaVO.contents}');
      
      
      $('.fileDel').click(function(){
    	  alert("삭제하기")
    	  $.post("/qna/filedelete",
    			  {fileNum:$(this).attr("data-fNum")
    		  }, function(result){
    				  console.log(result)
    			  })
    	  
      })
      
    </script>
    <script>

    setCount(${qnaVO.fileVOs.size()});
    </script>
</body>
</html>