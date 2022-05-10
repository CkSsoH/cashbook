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

import vo.Member;

public class MemberDao {
	//회원가입
	public void insertMember(Member member)	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	
			String sql = "insert INTO member(member_id,member_pw,create_date) VALUE(?,PASSWORD(?),NOW());";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("입력성공");
			} else {
				System.out.println("입력실패");
			}
			
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
	}
	//회원수정
	//회원탈퇴
	//회원정보보기
	public List<Map<String,Object>> selectMemberOne(String id) {
		List<Map<String,Object>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			/*
			SELECT member_id memberId ,member_pw memberPw,create_date createDate
			FROM member
			WHERE member_id = ?
			 */
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			String sql = "SELECT member_id memberId , member_pw memberPw,create_date createDate"
					+ " FROM member"
					+ " WHERE member_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("memberId", rs.getString("memberId"));
				map.put("memberPw", rs.getString("memberPw"));
				map.put("createDate", rs.getString("createDate"));
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
	
	//로그인
  public String selectMemberByIdPw(Member member) {
      String memberId = null;
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
      try {
         Class.forName("org.mariadb.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
         stmt = conn.prepareStatement(sql);
         stmt.setString(1, member.getMemberId());
         stmt.setString(2, member.getMemberPw());
         rs = stmt.executeQuery();
         if(rs.next()) {
            memberId = rs.getString("memberId");
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
      return memberId;
   }
}