package com.commons.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.action.ActionForward;

public interface Action {
	// 부모의 역할만하는 인터페이스 -> excute() 메소드 강제함.
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException;
}
