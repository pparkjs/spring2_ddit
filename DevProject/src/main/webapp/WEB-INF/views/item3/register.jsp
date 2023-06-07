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
	<h2>Register</h2>
	<form id="item3" method="post" action="/item3/register" enctype="multipart/form-data">
		<table>
			<tr>
				<td>상품명</td>
				<td>
					<input type="text" name="itemName" id="itemName">
				</td>
			</tr>
			<tr>
				<td>가격</td>
				<td>
					<input type="text" name="price" id="price">
				</td>
			</tr>
			<tr>
				<td>파일</td>
				<td>
					<input type="file" id="inputFile">
					<div class="uploadedList"></div>
				</td>
			</tr>
			<tr>
				<td>개요</td>
				<td>
					<textarea rows="10" cols="20" name="description"></textarea>
				</td>
			</tr>
		</table>
		<div>
			<button type="submit" id="btnRegister">Register</button>
			<button type="button" id="btnList" onclick="javascript:location.href='/item3/list'">List</button>
		</div>
	</form>
</body>
<script type="text/javascript">
$(function(){
	var inputFile = $("#inputFile");
	
	// 업로드 한 이미지 'x' 클릭
	$(".uploadedList").on("click", "span", function(){
		$(this).parent("div").remove();
	})
	
	$("#item3").submit(function(event){
		event.preventDefault();
		
		var that = $(this);	// 현재 눌른 form 태그
		var str = "";
		
		$(".uploadedList a").each(function(index){
			var value = $(this).attr("href");
			value = value.substr(28);  // '?fileName=' 다음에 나오는 값
					
			str += "<input type='hidden' name='files["+index+"]' value='"+value+"'>'";
		});
		
		console.log("str : " + str);
		
		that.append(str);
		that.get(0).submit();	// form의 첫번째를 가져와서 submit() 처리
	})
	
	//Open파일을 변경했을때 발동
	inputFile.on("change", function(event){
		console.log("change event...!");
		var files = event.target.files;
		var file = files[0];
		
		console.log(file); 		// 로그 출력(확인용);
		
		var formData = new FormData();
		formData.append("file", file);
		console.log("formData : " , formData)
		
		$.ajax({
			type : "post",
			url : "/item3/uploadAjax",
			data : formData,
			dataType : "text",
			processData : false,
			contentType : false,
			success : function(data){
				console.log(data); 		//결과 출력(확인용)
				
				var str = "";
				if(checkImageType(data)){ // 이미지면 이미지태그를 이용하여 출력
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>";
					str += "		<img src='/item3/displayFile?fileName="+ getThumbnailName(data) + "'/>";
					str += "	</a>";
					str += "    <span>X</span>";
					str += "</div>";
				}else{			// 이미지가 아닌 일반 파일일때
					str += "<div>"
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>" + getOrignalName(data) + "</a>";
					str += "	<span>X</span>";
					str += "</div>";
				}
				
				$(".uploadedList").append(str) // 추가된 파일(이미지, 파일)들을 div에 추가한다.
			}
		})
	})
		function getThumbnailName(fileName){
			var front = fileName.substr(0, 12); // /2023/06/07 폴더
			var end = fileName.substr(12);		// 뒤 파일명
			
			console.log("front : " + front);
			console.log("end : " + end);
			
			return front + "s_" + end;
			
		}
		
		function getOrignalName(fileName){
			if(checkImageType(fileName)){
				return;
			}
			
			var idx = fileName.indexOf("_") + 1;
			return fileName.substr(idx); // 파일 이름 얻기
		}
		
		// 이미지 파일인지 확인한다.
		function checkImageType(fileName){
			var pattern = /jpg|gif|png|jpeg/;
			
			return fileName.match(pattern); // 패턴과 일치하면 true(이미지구나?)
		}
});
</script>
</html>