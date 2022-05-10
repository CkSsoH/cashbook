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
import javax.servlet.http.HttpSession;

import dao.MemberDao;

@WebServlet("/SelectMemberOneController")
public class SelectMemberOneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//같은 새션을 가져와서 
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
						
		//로그인이 하지 않은 상태라면
		if(sessionMemberId == null) {
				response.sendRedirect(request.getContextPath()+"/LoginController");
				return;
		}
		//String id = request.getParameter("id");
		MemberDao memberDao = new MemberDao();
		List<Map<String,Object>> list = memberDao.selectMemberOne(sessionMemberId);
		
		for(Map m:list) {
			System.out.println("정보->"+ m);
		}
		// 뒤로가기 년도와 월 정보 전달
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH)+1;
				
				
		//리스트값 넘겨주기
		request.setAttribute("y", y);
		request.setAttribute("m", m);
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/WEB-INF/view/MemberOne.jsp").forward(request, response);
		
	}
}
