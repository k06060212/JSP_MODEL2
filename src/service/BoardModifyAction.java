package service;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import model.BoardBean;

public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("BoardModifyAction");
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page");
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardBean board = dao.getDetail(board_num);	// 상세 정보 구하기
		
		request.setAttribute("board", board);
		request.setAttribute("page", board);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./board/modify.jsp");
		
		return forward;
	}

}
