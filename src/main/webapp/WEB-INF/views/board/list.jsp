<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
	<c:import url="../temp/boot.jsp"></c:import>
	<h1>List Page</h1>
	<hr>

	<a class="btn btn-danger" href="./write" >Write</a> 

	<table border=1 class="table table-hover">
		<thead>
			<tr>
				<th>num</th>
				<th>title</th>
				<th>hit</th>
				<th>date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${lst}" var="lst">
				<tr>
					<td><a href="./detail?num=${lst.num}">클릭</a>${lst.num}</td>
					<td>${lst.title}</td>
					<td>${lst.hit}</td>
					<td>${lst.regDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/javascript">
		let result ='${param.result}';
		if(result!= ""){
			if(result=='1'){
				alert('등록성공');
			}else{
				alert('등록실패');
			}
		}
	</script>
</body>
</html>