package com.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.commons.action.Action;
import com.commons.action.ActionForward;

public class MemberLogoutAction implements Action {
	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("id");	//id
		//session.invalidate(); //모든 session 삭제
		
		ActionForward forward = new ActionForward();
		forward.setPath("memberLogin.me");
		forward.setRedirect(true);
		
		return forward;
	}//excute()

}//class MemberLoginAction
