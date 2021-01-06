package com.board.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.action.Action;
import com.commons.action.ActionForward;
//import com.board.action.boardListAction;

@WebServlet("/BoardFrontController.bo")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1. 클라이언트가 어떤 요청을 하였는지를 파악
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();	//uri-pattern 값 : /mbb/XXX.do
		String ctx = request.getContextPath();	//Context root 값 : /mbb
		String command = uri.substring(ctx.length());	//실제요청한 페이지 : /XXX.do
		
//		System.out.println("uri : " + uri);
//		System.out.println("ctx : " + ctx);
//		System.out.println("command : " + command);
		
		//2. 클라이언트의 요청(*.me ▶ command)과 
		//	실제 처리할 Action Class(비지니스 로직)를 연결: boardXXXAction Class
		Action action = null;
		ActionForward forward = null;


		if(command.equals("/boardList.bo")) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>alert('boardFrontController.if(/boardList.bo).start');</script>");
			System.out.println("boardFrontController.if(/boardList.bo).start");

			action = new BoardListAction();
			forward = action.excute(request, response);

			out.println("<script>alert('boardFrontController.if(/boardList.bo).end');</script>");
			System.out.println("boardFrontController.if(/boardList.bo).end");

		}/* else if(command.equals("/boardInsertForm.do")) {
			//글을 작성하는 페이지로 화면 전환만 필요
			forward = new ActionForward();
			forward.setPath("board/boardInsertForm.jsp");
			forward.setRedirect(false);

		} else if(command.equals("/boardInsert.do")) {
			//글을 작성하는 페이지로 화면 전환만 필요
			action = new boardInsertAction();
			forward = action.excute(request, response);

		} else if(command.equals("/boardDetail.do")) {
			action = new boardDetailAction();
			forward = action.excute(request, response);

		} else if(command.equals("/boardDelete.do")) {
			action = new boardDeleteAction();
			forward = action.excute(request, response);

		} else if(command.equals("/boardUpdateForm.do")) {
			action = new boardUpdateFormAction();
			forward = action.excute(request, response);

		} else if(command.equals("/boardUpdate.do")) {
			action = new boardUpdateAction();
			forward = action.excute(request, response);

		} else if(command.equals("/boardSearch.do")) {
			action = new boardSearchAction();
			forward = action.excute(request, response);
			
		}
*/		
		
		//3. 페이지 전환(프리젠테이션 로직) : sendRedirect(), forward()
		if(forward != null) {
			if(forward.isRedirect()) {	//true : sendRedirect()
				response.sendRedirect( forward.getPath() );
			} else {	//false : forward()
				RequestDispatcher rd = request.getRequestDispatcher( forward.getPath() );
				rd.forward( request, response );
				
			}
		}
		
		
		
	}//service()

}//class boardFrontController

/*
Client : request --> Controller(insert)
					Controller(delete)
					Controller(update)
					Controller(select)

= FrontController.java => command: /~~~.do 
	=> 실제처리 할 Action Class 연결 : boardXXXAction.java ▶ Servlet
		=> 매개변수 전달 받고 DAO연동(DB)후 결과를 리턴받은 후 출력  

*/ 
