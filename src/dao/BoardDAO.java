package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.BoardBean;

public class BoardDAO {

	// 싱글톤
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	// 커넥션 풀에서 커넥션을 구해오는 메소드
	private Connection getConnection() throws Exception{
		Context init = new InitialContext();
  		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/orcl");
  		return ds.getConnection();
	}
	
	// 글작성 : 원문 작성
	public int insert(BoardBean board) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			
			String sql="insert into jspmodel2 values(jspmodel2_seq.nextval,?,?,?,?,?,jspmodel2_seq.nextval,?,?,?,sysdate)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, board.getBoard_name());
			pstmt.setString(2, board.getBoard_pass());
			pstmt.setString(3, board.getBoard_subject());
			pstmt.setString(4, board.getBoard_content());
			pstmt.setString(5, board.getBoard_file());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			
			result = pstmt.executeUpdate(); // sql문 실행
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {try{con.close();}catch(Exception e) {}}
			if(pstmt != null) {try{pstmt.close();}catch(Exception e) {}}
		}
		
		return result;
	}
	
}
