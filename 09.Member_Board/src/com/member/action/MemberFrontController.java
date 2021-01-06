package com.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.action.Action;
import com.commons.action.ActionForward;

@WebServlet("/MemberFrontController.me")
public class MemberFrontController extends HttpServlet {
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
		//	실제 처리할 Action Class(비지니스 로직)를 연결: memberXXXAction Class
		Action action = null;
		ActionForward forward = null;


		if( command.equals("/memberLogin.me") ) {
			//로그인 화면으로 이동(DB연동X)
			forward = new ActionForward();
			forward.setPath("member/loginForm.jsp");
			forward.setRedirect(false);
			
		} else if ( command.equals("/memberLoginAction.me") ) {
			action = new MemberLoginAction();
			forward = action.excute(request, response);
			
		} else if ( command.equals("/memberJoin.me") ) {
			//회원가입 화면으로 이동(DB연동X)
			forward = new ActionForward();
			forward.setPath("member/joinForm.jsp");
			forward.setRedirect(false);
			
		} else if ( command.equals("/memberJoinAction.me") ) {
			action = new MemberJoinAction();
			forward = action.excute(request, response);
			
		} else if(command.equals("/memberLogout.me")) {
			action = new MemberLogoutAction();
			forward = action.excute(request, response);

		} else if(command.equals("/memberListAction.me")) {
			action = new MemberListAction();
			forward = action.excute(request, response);
			
		} else if(command.equals("/memberDeleteAction.me")) {
			action = new MemberDeleteAction();
			forward = action.excute(request, response);
			
		}/* else if(command.equals("/memberInsertForm.do")) {
			//글을 작성하는 페이지로 화면 전환만 필요
			forward = new ActionForward();
			forward.setPath("member/memberInsertForm.jsp");
			forward.setRedirect(false);

		} else if(command.equals("/memberInsert.do")) {
			//글을 작성하는 페이지로 화면 전환만 필요
			action = new memberInsertAction();
			forward = action.excute(request, response);

		} else if(command.equals("/memberDetail.do")) {
			action = new memberDetailAction();
			forward = action.excute(request, response);

		} else if(command.equals("/memberDelete.do")) {
			action = new memberDeleteAction();
			forward = action.excute(request, response);

		} else if(command.equals("/memberUpdateForm.do")) {
			action = new memberUpdateFormAction();
			forward = action.excute(request, response);

		} else if(command.equals("/memberUpdate.do")) {
			action = new memberUpdateAction();
			forward = action.excute(request, response);

		} else if(command.equals("/memberSearch.do")) {
			action = new memberSearchAction();
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

}//class MemberFrontController

/*
Client : request --> Controller(insert)
					Controller(delete)
					Controller(update)
					Controller(select)

= FrontController.java => command: /~~~.do 
	=> 실제처리 할 Action Class 연결 : memberXXXAction.java ▶ Servlet
		=> 매개변수 전달 받고 DAO연동(DB)후 결과를 리턴받은 후 출력  

*/ 
