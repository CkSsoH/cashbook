<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InsertMember</title>
</head>
<body>
	<form method="post" action="<%=request.getContextPath()%>/InsertMemberController" >
			<table>
				<tr>
					<td>회원가입</td>
				</tr>
				<tr>
					<td>ID 입력<br>
					<input type="text" name="memberId"></td>
				</tr>
				<tr>
					<td>PW 입력<br>
					<input type="password" name="memberPw"></td>
				</tr>
				<tr>
					<td>PW 확인<br>
					<input type="password" name="memberPw2"></td>
				</tr>
			</table>
			<div>
				<a href="<%=request.getContextPath()%>/LoginController">뒤로가기</a>
				<button type="submit">회원가입</button>
			</div>
		</form>
</body>
</html>