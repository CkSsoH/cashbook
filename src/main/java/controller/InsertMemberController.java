package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/InsertMemberController")
public class InsertMemberController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 똑같은 새션을 가져와서 
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
				
		// 로그인이 되어 있는 상태
		if(sessionMemberId != null) {
		response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController");
		return;
		}
		System.out.println("로그인 안됨");
		request.getRequestDispatcher("/WEB-INF/view/InsertMember.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 받아온 값 변수에 저장
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String memberPw2 = request.getParameter("memberPw2");
		
		System.out.println("memberId-->"+ memberId);
		System.out.println("memberPw--> "+ memberPw);
		System.out.println("memberPw2-->"+ memberPw2);
		
		// 비밀번호가 일치하지 않다면
		if(! memberPw.equals(memberPw2)) {
			response.sendRedirect(request.getContextPath()+"/insertmemberController");	
		}
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		
		MemberDao memberDao = new MemberDao();
		memberDao.insertMember(member);
		
		response.sendRedirect(request.getContextPath()+"/LoginController");	
	}

}
