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

public class MemberDeleteAction implements Action {
	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//크라이언트 요청을 받는다 : 매개변수를 전달받는다.
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("memberLogin.me");
			forward.setRedirect(true);
			return forward;
		} else if (!id.equals("admin")) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('관리자가 아닙니다!');");
			out.println("location.href='boardList.do';</script>");
			return null;
		} else {
			String member_id = request.getParameter("id");
			
			MemberDAO dao = new MemberDAO();
			int succ = dao.deleteMember(member_id);

			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();

			if(succ > 0){
				out.println("<script>alert('회원정보 삭제성공');");
				out.println("location.href='list.jsp';</script>");
			}else{
				out.println("<script>alert('회원정보 삭제실패');");
				out.println("location.href='list.jsp';</script>");
			}
			return null;
		}
		
		//return null;
	}//excute()

}//class memberListAction
