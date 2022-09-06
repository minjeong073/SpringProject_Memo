<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- jstl core library -->    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메모 - 입력</title>

<!-- bootstrap jquery -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

	<link rel="stylesheet" href="/static/css/style.css" type="text/css" >

</head>
<body>

	<div class="container">
		<c:import url="/WEB-INF/jsp/include/header.jsp" />
		
		<section class="d-flex justify-content-center">
			<div class="col-9 my-5">
				<h2 class="text-center">메모 입력</h2>
				
				<div class="d-flex mt-3">
					<label class="col-2">제목 : </label>
					<input type="text" class="form-control col-10" id="titleInput">
				</div>
				
				<textarea class="form-control mt-2" rows="7" id="contentInput"></textarea>
				<input type="file" class="mt-2" id="fileInput">
				
				<div class="d-flex justify-content-between mt-3">
					<!-- button 으로 페이지 이동하기 번거롭기 떄문에 a tag 로 페이지 이동 -->
					<a href="/post/list/view" class="btn btn-primary">목록으로</a>
					<button type="button" class="btn btn-primary" id="saveBtn">저장</button>				
				</div>
			</div>
		</section>
		
		<c:import url="/WEB-INF/jsp/include/footer.jsp" />
	
	</div>
	
	<script>
	
		$(document).ready(function() {
		
			$("#saveBtn").on("click", function() {
				
				let title = $("#titleInput").val();
				let content = $("#contentInput").val();
				
				// validation
				
				if (title == "") {
					alert("제목을 입력하세요");
					return;
				}
				
				if (content == "") {
					alert("메모 내용을 입력하세요");
					return ;
				}
				
				// file 은 있을 수도 없을 수도 있기 때문에 validation X
				
				// file 은 formData 객체 형태로 전달
				// -> formData 객체에 전달할 내용 담아서 data 에 전송
				
				var formData = new FormData();
				formData.append("title", title);
				formData.append("content", content);
				// $("#fileInput")[0].files : 선택된 파일들이 보여짐 -> ~.files[0] : 하나의 파일만 추가
				formData.append("file", $("#fileInput")[0].files[0]);
				
				
				$.ajax({
					type:"post"
					, url:"/post/create"
					, data:formData
					// 파일 업로드 필수 옵션 (enctype, processData, contentType)
					, enctype:"multipart/form-data" // file encoding 형식
					, processData:false	// false -> multipart file 로 변경해서 가장 기본인 param 으로 전달되지 않도록
					, contentType:false 
					, success:function(data) {
						if (data.result == "success") {
							location.href = "/post/list/view";
						} else {
							alert("입력 실패");
						}
					}
					, error:function() {
						alert("입력 에러");
					}
				});
				
			});
		});
		
		
	</script>
</body>
</html>