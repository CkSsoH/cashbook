package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;

@WebServlet("/CashBookListByMonthController")
public class CashBookListByMonthController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 월별 가계부 리스트 요청 분석
		Calendar now = Calendar.getInstance(); //오늘날짜 구하기 2022.04.19
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH) + 1; // 0 - 1월, 1 - 2월, ... 11 - 12월
		
		
		if(request.getParameter("y") != null) {
			y = Integer.parseInt(request.getParameter("y"));
		}
		if(request.getParameter("m") != null) {
			m = Integer.parseInt(request.getParameter("m"));
		}
		if(m==0) {
			m = 12;
			y = y-1;
		}
		if(m==13) {
			m = 1;
			y = y+1;
		}
		
		System.out.println(y+" <-- y");
		System.out.println(m+" <-- m");
		
		// 일 0, 월 1, 화 2... 토 6 -> 1일의 요일을 이용하여 구한다. (시작시 필요한 앞 공백 <td>)
		// firstDay는 오늘날짜를 먼저구하여 날짜만 1일로 변경해서 구한다.
		Calendar firstDay = Calendar.getInstance(); //2022.04.19
		firstDay.set(Calendar.YEAR, y);
		firstDay.set(Calendar.MONTH, m-1); //위에서 +1해줘서 -1 해야 0 ->1월이 됨
		firstDay.set(Calendar.DATE, 1); // 여기서 일자를 1로 바꿈 /2022.04.01 
		int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK); //1일의 요일 구하기
		//dayOfWeek 는 일요일이 1, 월 2,... 토 7로 나와서 앞공백 (startBlank)은 -1일을 해줘야 공백갯수를 알 수 있음을 찾음
		int startBlank = dayOfWeek - 1;
		//마지막 날짜는 자바달력 API 사용
		int endDay = firstDay.getActualMaximum(Calendar.DATE); //firstDay달의 제일 큰 숫자 (마지막날짜)
		// startBlank와 endDay를 합쳐 결과에 endBlank를 더해서 7의 배수가 되도록
		int endBlank = 0;
		if((startBlank + endDay) % 7 != 0) {
			// 7로나눈 나머지가 0이 아니면, startBlank+ endDay의 7로나눈 나머지값을 빼면 마지막 공백이나옴
			endBlank = 7-((startBlank + endDay) % 7);
		}
		int totalTd = startBlank + endDay + endBlank;
		
		
		// 2) 모델값(월별 가계부 리스트)을 반환하는 비지니스로직(모델) 호출
		CashBookDao cashBookDao = new CashBookDao();
		List<Map<String, Object>> list = cashBookDao.selectCashBookListByMonth(y, m);
		/*
		달력 출력에 필요한 모델값(startBlank,endBlank,totalTd,endDay) + 데이터베이스에서 반환된 모델값(list,y출력연도,m출력월) + 오늘날짜(today)
		*/
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endDay", endDay);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalTd", totalTd);
		
		request.setAttribute("list", list);
		request.setAttribute("y", y);
		request.setAttribute("m", m);
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashBookListByMonth.jsp").forward(request, response);
	}

}