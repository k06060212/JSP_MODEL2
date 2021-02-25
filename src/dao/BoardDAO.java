package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

	// 총 데이터 갯수 구하기
	public int getCount() {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql="select count(*) from jspmodel2";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {try{con.close();}catch(Exception e) {}}
			if(pstmt != null) {try{pstmt.close();}catch(Exception e) {}}
			if(rs != null) {try{rs.close();}catch(Exception e) {}}
		}
		
		return result;
	}

	public List<BoardBean> getlist(int start, int end) {
		List<BoardBean> list = new ArrayList<BoardBean>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql = "select * from ";
				   sql += " (select rownum rnum, board.* from ";
				   sql += " (select * from jspmodel2 order by board_re_ref desc, board_re_seq asc) board )";
				   sql += " where rnum >= ? and rnum <= ?";
			
				   pstmt = con.prepareStatement(sql);
				   pstmt.setInt(1, start);
				   pstmt.setInt(2, end);
				   rs = pstmt.executeQuery();
				   
				   while(rs.next()) {
					   BoardBean board = new BoardBean();
					   board.setBoard_num(rs.getInt("board_num"));
					   board.setBoard_name(rs.getString("board_name"));
					   board.setBoard_pass(rs.getString("board_pass"));
					   board.setBoard_subject(rs.getString("board_subject"));
					   board.setBoard_content(rs.getString("board_content"));
					   board.setBoard_file(rs.getString("board_file"));
					   board.setBoard_re_ref(rs.getInt("board_re_ref"));
					   board.setBoard_re_lev(rs.getInt("board_re_lev"));
					   board.setBoard_re_seq(rs.getInt("board_re_seq"));
					   board.setBoard_readcount(rs.getInt("board_readcount"));
					   board.setBoard_date(rs.getTimestamp("board_date"));
					   
					   list.add(board);
				   }
				   
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {try{con.close();}catch(Exception e) {}}
			if(pstmt != null) {try{pstmt.close();}catch(Exception e) {}}
			if(rs != null) {try{rs.close();}catch(Exception e) {}}
		}
		
		
		return list;
	}
	
}
