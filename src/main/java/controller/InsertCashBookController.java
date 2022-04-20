package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;
import vo.CashBook;

@WebServlet("/InsertCashBookController")
public class InsertCashBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String d = request.getParameter("d");
		String cashDate = y + "-" + m + "-" + d;
		request.setAttribute("cashDate", cashDate);
		request.getRequestDispatcher("/WEB-INF/view/insertCashBookForm.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		System.out.println(cashDate + "<-- cashDate InsertCashBookController.doPost()");
		System.out.println(kind + "<-- kind InsertCashBookController.doPost()");
		System.out.println(cash + "<-- cash InsertCashBookController.doPost()");
		System.out.println(memo + "<-- memo InsertCashBookController.doPost()");
		
		CashBook cashBook = new CashBook();
		cashBook.setCashDate(cashDate);
		cashBook.setKind(kind);
		cashBook.setCash(cash);
		cashBook.setMemo(memo);
		
		List<String> hashtag = new ArrayList<>();
		//자바의 문자(String)는 수정불가,불변 (수정하면 새로 생기는거임)
		String memo2 = memo.replace("#", " #"); // #을 붙여서 수정하는게 아니라 새로운 문자가 생기는 거임.
		String[] arr = memo2.split(" "); // 바뀐 메모를 공백문자 기준으로 자른다
		for(String s : arr) {
			if(s.startsWith("#")) { // #으로 시작하는 문자는
				String temp = s.replace("#", ""); //replace (#을 ""공백으로 치환)
				if(temp.length()>0) {
					hashtag.add(temp);
				}
			}
		}
		System.out.println(hashtag.size() +"<--hashtag.size InsertCashBookController.doPost()");
		for(String h : hashtag) {
			System.out.println(h + "<-- hashtag InsertCashBookController.doPost()");
		}
		
		CashBookDao cashBookDao = new CashBookDao();
		cashBookDao.insertCashBook(cashBook, hashtag);
		
		response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController");
	}

}
