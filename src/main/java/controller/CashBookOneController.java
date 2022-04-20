package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;
import vo.CashBook;


@WebServlet("/CashBookOneController")
public class CashBookOneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// CashBookOneController --> CashBookDao.selectCashBookOne()메소드로 갓다와야함
		//다시 와서 -->forward --> CashBookOne.jsp --> 삭제/ 수정 버튼 생성
		
		if(request.getParameter("cashBookNo") == null) { // 받아온 값이 null이면
			response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController?error=null");
			// CashBookListByMonthController 로 돌아감
		}
		
		int cashBookNo = Integer.parseInt(request.getParameter("cashBookNo"));
		
		CashBookDao cashBookDao = new CashBookDao();
		CashBook cashBook = new CashBook();
		cashBook = cashBookDao.selectCashBookOne(cashBookNo);
		request.setAttribute("cashBook", cashBook);
		request.getRequestDispatcher("/WEB-INF/view/cashBookOne.jsp").forward(request, response);
		
		
		//삭제 -> DeleteCashBookController.doGet() --> CashBookDao.deleteCashBook() 갓다와서
		//->다시 DeleteCashBookController -->/Redirect/ --> CashBookListByMonthController
		
		//수정폼 -> UpdateCashBookController.doGet() -> CashBookDao.selectCashBookOne() 갓다와서
		//-> /forward/ -> UpdateCashBook.jsp -> 다시UpdateCashBookController.doPost() 여기로와서 -> cashBookDao.updateCashBook()
		//->수정끝나면 /Redirect/ --> CashBookOneController
	}

}
