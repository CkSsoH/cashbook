package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;

@WebServlet("/DeleteCashBookController")
public class DeleteCashBookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("cashBookNo") == null) {
			response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController?error=null");
		}
		
		int cashBookNo = Integer.parseInt(request.getParameter("cashBookNo"));
		System.out.println(cashBookNo + "<--cashBookNo DeleteCashBookController");
		
		CashBookDao cashBookDao = new CashBookDao();
		cashBookDao.deleteCashBook(cashBookNo);
		
		response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController");
	}

}
