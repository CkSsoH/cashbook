<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashBookListByMonth</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body class ="container">
	<%
		List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");
		
		int startBlank = (Integer)request.getAttribute("startBlank");
		int endDay = (Integer)request.getAttribute("endDay");
		int endBlank = (Integer)request.getAttribute("endBlank");
		int totalTd = (Integer)request.getAttribute("totalTd");
		
		System.out.println(list.size() + " <- list.size() CashBookListByMonth.jsp");
		System.out.println(y + " <- y CashBookListByMonth.jsp");
		System.out.println(m + " <- m CashBookListByMonth.jsp");
		
		System.out.println(startBlank + " <- startBlank CashBookListByMonth.jsp");
		System.out.println(endDay + " <- endDay CashBookListByMonth.jsp");
		System.out.println(endBlank + " <- endBlank CashBookListByMonth.jsp");
		System.out.println(totalTd + " <- totalTd CashBookListByMonth.jsp");
		
	%>
	<div>
		<a href ="<%=request.getContextPath()%>/SelectMemberOneController"><%=session.getAttribute("sessionMemberId")%></a> 님 반갑습니다.
		<a href ="<%=request.getContextPath()%>/LogoutController">로그아웃</a>
	</div>
	<div>
		<a href="<%=request.getContextPath()%>/TagController">tags</a>
	</div>
	<h2><%=y%>년 <%=m%>월</h2>

	<div>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m-1%>">이전달</a>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m+1%>">다음달</a>
	</div>
	<!-- 
		1) 이번달 1일의 요일 알고리즘 firstDayOfWeek(요일) -> startBlank가 필요 -> 일요일이면 0, 월 1, 화 2,... 토 6칸의 공백이 필요
		3) 이번달 마지막 날짜알면 endDay
		4) endBlank 알고 -> totalBlank 구할수 있다 (앞공백 + 뒷공백)
		5) td(총칸)의 갯수 1 ~ totalBlank
			+
		6) 가계부 list
		7) 오늘날짜 (today)
	 -->
	<table class ="table table-striped">
		<thead>
			<tr>
				<th>일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th>토</th>
			<tr>
		</thead>
		<tbody>
			<tr>
				<%
					for(int i=1; i<=totalTd; i+=1){
						if((i-startBlank) > 0 && (i-startBlank) <= endDay){
							String c = "";
							if(i%7==0){
								c = "text-primary";
							} else if(i%7==1) {
								c = "text-danger";
							} 
				%>
							<td class ="<%=c%>">
								<%=i-startBlank%>
								<a href="<%=request.getContextPath()%>/InsertCashBookController?y=<%=y%>&m=<%=m%>&d=<%=i-startBlank%>" class="btn btn-light">입력</a>
								<div>
									<%
										//해당날짜의 cashBook 목록 출력할것
										for(Map map : list){
											if((Integer)map.get("day") == (i-startBlank)){
									%>
												<div>
													<a href="<%=request.getContextPath()%>/CashBookOneController?cashBookNo=<%=map.get("cashBookNo")%>">
														[<%=map.get("kind")%>] 
														<%=map.get("cash")%>원
														<%=map.get("memo")%>...
													</a>
												</div>
									<%			
											}
										}
									%>
								</div>
							</td>
				<%			
						}else{
				%>
						<td>&nbsp;</td>
				<%		
						}
						if(i<totalTd && i%7==0){
				%>
							</tr><tr> <!-- 새로운 행 추가 -->
				<%			
						}
					}
				%>
			</tr>
		</tbody>
	</table>
	
</body>
</html>