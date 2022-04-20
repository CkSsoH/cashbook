<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="vo.CashBook" %>
<%
	CashBook cashBook = (CashBook)request.getAttribute("cashBook");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashBookOne</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>cashBookOne</title>
</head>
<body>
<body class="container">
	<h1>cashBook 상세보기</h1>
	<table class="table table-bordered">
		<tr>
			<td>cashDate</td>
			<td><%= cashBook.getCashDate()%></td>
		</tr>
		<tr>
			<td>kind</td>
			<td><%=cashBook.getKind()%></td>
		</tr>
		<tr>
			<td>cash</td>
			<td><%= cashBook.getCash() %></td>
		</tr>
		<tr>
			<td>memo</td>
			<td><%= cashBook.getMemo() %></td>
		</tr>
		<tr>
			<td>createDate</td>
			<td><%= cashBook.getCreateDate() %></td>
		</tr>
		<tr>
			<td>updateDate</td>
			<td><%= cashBook.getUpdateDate() %></td>
		</tr>
	</table>
	<a href="<%=request.getContextPath()%>/UpdateCashBookController?cashBookNo=<%=cashBook.getCashBookNo()%>" type ="button" class="btn btn-primary ">수정</a>
	<a href="<%=request.getContextPath()%>/DeleteCashBookController?cashBookNo=<%=cashBook.getCashBookNo()%>" type ="button" class="btn btn-danger">삭제</a>
	<a href="<%=request.getContextPath()%>/CashBookListByMonthController" type ="button" class="btn btn-secondary">리스트</a>
</body>
</html>
</body>
</html>