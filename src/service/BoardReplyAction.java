package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import model.BoardBean;

public class BoardReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("BoardReplyAction");

		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page");

		BoardDAO dao = BoardDAO.getInstance();

		// 부모글에 대한 상세정보 구하기
		BoardBean board = dao.getDetail(board_num);

		request.setAttribute("board", board);
		request.setAttribute("page", page);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);				 // dispatcher 방식으로 포워딩
		forward.setPath("./board/reply.jsp"); 	 // 댓글 폼파일

		return forward;
	}

}
