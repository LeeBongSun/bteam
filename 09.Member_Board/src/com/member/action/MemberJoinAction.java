package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.action.Action;
import com.commons.action.ActionForward;
import com.member.study.MemberDAO;
import com.member.study.MemberDTO;

public class MemberJoinAction implements Action {
	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//크라이언트 요청을 받는다 : 매개변수를 전달받는다.
		request.setCharacterEncoding("utf-8");
/*
		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
		String member_name = request.getParameter("member_name");
		int member_age = Integer.parseInt( request.getParameter("member_age") );
		String member_gender = request.getParameter("member_gender");
		String member_email = request.getParameter("member_email");
*/		
		MemberDTO dto = new MemberDTO();
		dto.setMember_id(request.getParameter("member_id"));
		dto.setMember_pw(request.getParameter("member_pw"));
		dto.setMember_name(request.getParameter("member_name"));
		dto.setMember_age(Integer.parseInt( request.getParameter("member_age") ));
		dto.setMember_gender(request.getParameter("member_gender"));
		dto.setMember_email(request.getParameter("member_email"));
		
		//비지니스 로직 : DB연동해서 회원가입처리
		MemberDAO dao = new MemberDAO();
		int succ = dao.joinMember(dto);
		
		//프리젠테이션 로직 : 결과를 출력하고 화면전환
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		if(succ > 0){
			out.println("<script>alert('회원가입 완료!');");
			out.println("location.href='memberLogin.me';</script>");
		}else{
			out.println("<script>alert('회원가입 실패');");
			out.println("location.href='memberJoin.me';</script>");
		}
		
		return null;
	}

}
