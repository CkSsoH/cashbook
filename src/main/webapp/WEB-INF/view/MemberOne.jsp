<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MemberOne</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	int y = (Integer)request.getAttribute("y");
	int m = (Integer)request.getAttribute("m");
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
	<h1>MemberOne</h1>
	<div>
		<a href="<%=request.getContextPath()%>/SelectMemberOneController?id=<%=session.getAttribute("sessionMemberId")%>"><%=session.getAttribute("sessionMemberId")%></a>님 반갑습니다.
	</div>
	<div>	
		<a href="<%=request.getContextPath()%>/LogoutController">로그아웃</a>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController">뒤로가기</a>
	</div>
	<table class="table table-bordered">
		<%
			for(Map<String, Object> map : list) {
		%>
		<tr>
			<td>memberID</td>
			<td><%=map.get("memberId") %></td>
		</tr>
		<tr>
			<td>createDate</td>
			<td><%=map.get("createDate") %></td>
		</tr>
		<%
			}
		%>
	</table>
	<div>
		<a href="<%=request.getContextPath()%>/UpdateMemberPwController">PW수정</a>
		<a href="<%=request.getContextPath()%>/DeleteMemberController">회원탈퇴</a>
	</div>
</body>
</html>