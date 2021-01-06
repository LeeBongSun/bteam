package com.board.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.study.BoardDAO;
import com.board.study.BoardDTO;
import com.commons.action.Action;
import com.commons.action.ActionForward;

public class BoardListAction implements Action {
	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		//DAO 연동하여 게시판의 전체목록을 가져오는 작업
		//▶ 서블릿(Controller)이 해야할 일을 대신처리하는 작업

		//① 클라이언트의 요청을 받는다 : 세션을 받는다.
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		//② 비지니스 로직 : DAO연동 -> 결과를 리턴
		BoardDAO dao = new BoardDAO();
		//List<BoardDTO> list = dao.getAllList();
		//request.setAttribute("list", list);
		
		
		//③ 프리젠테이션 로직 : 페이지 전환 ▶ ActionForward.java
		//View Page(path) : board/boardList.jsp
		//페이지 전환방식(isRedirect) : forward()
		ActionForward forward = new ActionForward();
		if(id == null) {	//로그인이 않된 상태
			forward.setPath("memberLogin.me");
			forward.setRedirect(true);
		} else {
			forward.setPath("board/board_list.jsp");
			forward.setRedirect(false);
		}
		return forward;
	}

}
