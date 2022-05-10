package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate(); //invalidate() 는 세션갱신 메서드임 : 기존 세션을 지우고 새로운 세션공간을 부여
		/* 위에꺼 풀어서 적자면 이렇게.. 
		HttpSession session = request.getSession();
		session.invalidate();
		*/
		response.sendRedirect(request.getContextPath()+"/LoginController");
	}


}
