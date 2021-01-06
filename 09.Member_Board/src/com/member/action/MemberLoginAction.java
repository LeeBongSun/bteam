package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.commons.action.Action;
import com.commons.action.ActionForward;
import com.member.study.MemberDAO;
import com.member.study.MemberDTO;

public class MemberLoginAction implements Action {
	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//크라이언트 요청을 받는다 : 매개변수를 전달받는다.
		request.setCharacterEncoding("utf-8");
/*
		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
*/		
		MemberDTO dto = new MemberDTO();
		dto.setMember_id(request.getParameter("member_id"));
		dto.setMember_pw(request.getParameter("member_pw"));
		
		//비지니스 로직 : DB연동해서 회원가입처리
		MemberDAO dao = new MemberDAO();
		int result = dao.isMember(dto);
		
		//프리젠테이션 로직 : 결과를 출력하고 화면전환
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		if(result == -1) {
			out.println("<script>alert('아이디가 존재하지 않습니다!');");
			out.println("location.href='memberLogin.me';</script>");
			return null;
		} else if(result == 0) {
			out.println("<script>alert('비밀번호가 일치하지 않습니다!');");
			out.println("location.href='memberLogin.me';</script>");
			return null;
		} else {	//로그인 성공 : session 객체를 생성하고, 게시판 목록으로 이동
			HttpSession session = request.getSession();
			session.setAttribute( "id", dto.getMember_id() );
			
			ActionForward forward = new ActionForward();
			forward.setPath("boardList.bo");
			forward.setRedirect(true);
			
			return forward;
		}//if(result)
		
		//return null;
	}//excute()

}//class MemberLoginAction
