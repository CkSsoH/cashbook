package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vo.CashBook;

public class CashBookDao {
	public void insertCashBook(CashBook cashBook, List<String> hashtag) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			conn.setAutoCommit(false); // 자동커밋 막는것 (자동커밋 해제) (앞으로 커밋해줘야하고, 롤백도 해줘야함)
			
			String sql = "INSERT INTO cashbook(cash_date,kind,cash,memo,update_date,create_date)"
					+ " VALUES(?,?,?,?,NOW(),NOW())";
			
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // 입력직후 기본키값을 select 하는 jdbc API
			//PreparedStatement.RETURN_GENERATED_KEYS 매개변수 입력하면 (입력하자마자 insert + select 까지 실행함)
			// insert테이블에 키값을 select 하는거 (방금 생성된 행의 키값을 select)
			//ex) select 방금입력한 cashbook_no from cashbook;
			
			stmt.setString(1, cashBook.getCashDate());
			stmt.setString(2, cashBook.getKind());
			stmt.setInt(3, cashBook.getCash());
			stmt.setString(4, cashBook.getMemo());
			stmt.executeUpdate(); // insert 실행하는거 ( select 도 실행하려면)
			rs = stmt.getGeneratedKeys(); // select 실행하고 rs 에 담는다
			//방금입력된 키는
			int cashBookNo = 0; 
			if(rs.next()) {
				cashBookNo = rs.getInt(1); //방금입력한 cashbook_no가 담김
			}
			
			//hashtag를 저장하는 코드
			PreparedStatement stmt2 = null;
			for(String h : hashtag) { //hashtag 가 있을때만

				String sql2 = "INSERT INTO hashtag(cashbook_no, tag, create_date) VALUES(?, ?, NOW())";
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, cashBookNo);
				stmt2.setString(2, h);
				stmt2.executeUpdate();
			}
			
			conn.commit();//자동커밋 해제 했기 때문에 커밋 해줘야함
		} catch (Exception e) {
			try {
				conn.rollback(); // 자동커밋 해제 했기 때문에 예외발생시 롤백도 설정
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//selectCashBookOne() 메소드
	public CashBook selectCashBookOne(int cashBookNo) {
		CashBook c = new CashBook(); 

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT "
				+ "			cashbook_no cashBookNo"
				+ "			,cash_date cashDate"
				+ "			,kind"
				+ "			,cash"
				+ "			,memo"
				+ "			,create_date createDate"
				+ "			,update_date updateDate "
				+ "		FROM cashbook"
				+ "		WHERE cashbook_no = ? ";

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashBookNo);
			rs = stmt.executeQuery();
			if(rs.next()) {
				c.setCashBookNo(rs.getInt("cashBookNo"));
				c.setCashDate(rs.getString("cashDate"));
				c.setKind(rs.getString("kind"));
				c.setCash(rs.getInt("cash"));
				c.setMemo(rs.getString("memo"));
				c.setCreateDate(rs.getString("createDate"));
				c.setUpdateDate(rs.getString("updateDate"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
	
				rs.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
			e.printStackTrace();
			}
		}		
		return c;
	}
	
	//deleteCashBook() 메서드
	public void deleteCashBook(int cashBookNo) {
		//DB 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		//쿼리 작성
		//hashtag 테이블의 cashbook_no는 cashbook 테이블에 외래키를 가지고 있으므로, hashtag 테이블 -> cashbook 테이블 순으로 지워야 한다.
		//hashtag 테이블에서 삭제 쿼리
		String sql1 = "DELETE FROM hashtag WHERE cashbook_no = ?"	;
		//cashbook 테이블에서 삭제 쿼리
		String sql2 = "DELETE FROM cashbook WHERE cashbook_no = ?";
		//DB에 값 요청
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			conn.setAutoCommit(false); // 오토커밋해제
			stmt = conn.prepareStatement(sql1);
			stmt.setInt(1, cashBookNo);
			stmt.executeUpdate();// hashtag 테이블에서 delect
			stmt.close(); // stmt를 닫아준 후 다음 요청 실행
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, cashBookNo);
			stmt.executeUpdate();// cashbook 테이블에서 delect
			conn.commit(); //최종 커밋
			
		} catch (Exception e) {
			try {
				conn.rollback();//실패시 롤백
			} catch(SQLException e1) {
				e1.printStackTrace();
				}
				e.printStackTrace();
		}finally {
			try {
				//DB자원 반납
				stmt.close();
				conn.close();
			}catch(SQLException e) {
			e.printStackTrace();
			}
		}
	
	}
	
	
	// cashbook 리스트 불러오기
	public List<Map<String, Object>> selectCashBookListByMonth(int y, int m) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		/*
		 SELECT 
		 	cashbook_no cashbookNo
		 	,DAY(cash_date) day
		 	,kind
		 	,cash
		 	,LEFT(memo, 5) memo
		 FROM cashbook
		 WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?
		 ORDER BY DAY(cash_date) ASC
		 */
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT"
				+ "		 	cashbook_no cashBookNo"
				+ "		 	,DAY(cash_date) day"
				+ "		 	,kind"
				+ "		 	,cash"
				+ " 		,LEFT(memo, 5) memo"
				+ "		 FROM cashbook"
				+ "		 WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?"
				+ "		 ORDER BY DAY(cash_date) ASC, kind ASC";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, y);
			stmt.setInt(2, m);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashBookNo", rs.getInt("cashBookNo"));
				map.put("day", rs.getInt("day"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}