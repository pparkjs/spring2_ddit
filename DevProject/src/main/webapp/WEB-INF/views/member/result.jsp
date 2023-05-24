<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
	<c:set value="한국인" var="foreigner"/>
<c:if test="${member.foreigner}">
	<c:set value="외국인" var="foreigner"/>
</c:if>


<body>

		<table border="1">
			<tr>
				<td>유저 ID</td>
				<td>${member.userId }</td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td>${member.password }</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${member.userName }</td>
			</tr>
			<tr>
				<td>E-Mail</td>
				<td>${member.email }</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td>${member.dateOfBirth }</td>
			</tr>
			<tr>
				<td>성별</td>
				<td>${member.gender }</td>
			</tr>
			<tr>
				<td>개발자 여부</td>
				<td>${member.developer }</td>
			</tr>
			<tr>
				<td>외국인 여부</td>
				<td>${foreigner }</td>
			</tr>
			<tr>
				<td>국적</td>
				<td>${member.nationality[0] }</td>
			</tr>
			<tr>
				<td>소유차량</td>
				<td>
					<c:forEach var="i" begin="0" end="3">
						${member.cars[i] }
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>취미</td>
				<td>
					<c:forEach var="i" begin="0" end="3">
						${member.hobby[i] } 
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>우편번호</td>
				<td>${address.postCode }</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>${address.location }</td>
			</tr>
			<tr>
				<td>카드1 - 번호</td>
				<td>${cardList[0].no }</td>
			</tr>
			<tr>
				<td>카드1 - 유효년월</td>
				<td>${cardList[0].validMonth }</td>
			</tr>
			<tr>
				<td>카드2 - 번호</td>
				<td>${cardList[1].no }</td>
			</tr>
			<tr>
				<td>카드2 - 유효년월</td>
				<td>${cardList[1].validMonth }</td>
			</tr>
			<tr>
				<td>소개</td>
				<td>${member.introduction }</td>
			</tr>
		</table>
</body>
</html>