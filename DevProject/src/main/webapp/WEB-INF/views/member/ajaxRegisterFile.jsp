<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<body>
	<h1>10. 파일업로드 Ajax 방식 요청 처리</h1>
	<hr>
	
	<p>Ajax 방식으로 전달한 파일 요소값을 스프링 MVC가 지원하는 MultipartFile 매개변수로 처리한다.</p>
	<div>
		<input type="file" id="inputFile"><br>
		<hr>
		<img id="preview">
	</div>
</body>
<script type="text/javascript">
$(function(){
	
	$("#inputFile").on("change", function(event){
		console.log("change event...!")
		
		var files = event.target.files;
		var file = files[0];
		
		console.log(file);
		
		// 이미지 미리보기에대한 이 방식은 서버에 이미지를 넣지 않고 이미지 수정할 때마다 브라우저단에만 처리하고 확인을 눌렀을 때만 서버에 넘어간다
		if(isImageFile(file)){ // 이미지 파일일때
			// 비동기 처리시, 파일데이터를 전송할때에는 formData()를 이용하여 데이터를 전송한다.
			var formData = new FormData();
			formData.append("file", file);
			
			// formData는 key/value 형식으로 데이터가 저장된다.
			// dataType : 응답(response) 데이터를 내보낼때 보내줄 데이터 타입
			// processData : 데이터 파라미터를 data라는 속성으로 넣는데, 제이쿼리 내부적으로 쿼리스트링을 구성합니다.
			//				파일 전송의 경우 쿼리스트링을 사용하지 않으므로 해당 설정을 false로 비활성화한다.
			// contentType : Content-Type을 설정 시, 사용하는데 해당 설정의 기본값은 
			//				'application/x-www-form-urlendcoded; charset=utf-8'입니다.
			//				하여, 기본값으로 나가지않고 'multipart/form-data'로 나갈수 있도록 설정을 false합니다.
			//				request 요청해서 Content=Type을 확인해보면 'multipart/form-data; boundar====WebkitFormBoundary[HashKey]'
			//				와 같은 값으로 전송된 것을 확인할 수 있습니다.
			
			$.ajax({
				type : "post",
				url : "/ajax/uploadAjax",
				data : formData,
				dataType : "text", // 요청해서 받을 데이터 타입을 text형식으로 정한다는 의미
				processData : false,
				contentType : false,
				success : function(data){
					alert(data);
					if(data === "UPLOAD SUCCESS"){
						var file = event.target.files[0];
						var reader = new FileReader();
						reader.onload = function(e){
							$('#preview').attr("src", e.target.result);
						}
						reader.readAsDataURL(file);
					}
				}
			});
		}else{				   // 이미지 파일이 아닐때
			alert("이미지를 넣으라구요!!");
		}
	})
})	

function isImageFile(file){
	var ext = file.name.split(".").pop().toLowerCase(); // 파일명에서 확장자를 가져온다.
	// 확장자 중 이미지에 해당하는 확장자가 아닌경우 포함되어 있는 문자가 없으니까 -1(false)가 리턴
	// 확장자 중 이미지 확장자가 포함되어 있다면 0보다 큰 수 일테니 true가 리턴
	return ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) ? false : true; 
}
</script>
</html>