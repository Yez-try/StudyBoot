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
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrfToken">
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
        tabsize: 2,
        height: 500,
		callbacks:{
			onImageUpload: function(files) {
			console.log("ImageUpload")
			console.log("file", files)
			const formData = new FormData();
			//<input type="file"
			formData.append('files', files[0])

			let tokenName = $('#csrfToken').attr('name');
			let tokenVal = $('#csrfToken').val();
			formData.append(tokenName,tokenVal)
			console.log("1111111",tokenName,":",tokenVal)

			$.ajax({
				type:"POST",
				url:"summerFile",
				data:formData,
				cache:false,
				processData:false,
				contentType:false,
				enctype:'multipart/form-data',
				success:function(img){
					let imgnode = '<img src="'+img+'">'
					console.log(files[0].name, imgnode)
					$("#contents").summernote('pasteHTML', imgnode, files[0].name)
				}
			})
			},
			onMediaDelete:function(file){
					console.log("Delete Media")
					console.log("DeleteFile =>", file)
					deleteFile(file)
			}
		}
      });

	  function deleteFile(file){
		console.log(file.attr("src"))
		let tokenName = $('#csrfToken').attr('name');
		let tokenVal = $('#csrfToken').val();
		console.log(tokenName,":",tokenVal)
		const formData = new FormData();
		formData.append(tokenName,tokenVal)
		formData.append("fileName",file.attr("src"))
		//파일 삭제 요청 (Get? Post? url주소만 알면 Get은 어디서든 지울수 있으니까 이번에는 Post로 가자)
		$.ajax({
				type:"POST",
				url:"summerFileDelete",
				data:formData,
				cache:false,
				processData:false,
				contentType:false,
				enctype:'multipart/form-data',
				success:function(result){
					console.log("result => ", result);
				}
			})
		// $.post("./summerFileDelete", {
		// 	tokenName:tokenVal,
		// 	fileName:file.attr("src")
		// }, function(result){
		// 	console.log("result => ", result);
		// })

	  }


    </script>
</body>
</html>