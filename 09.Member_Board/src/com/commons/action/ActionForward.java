package com.commons.action;

public class ActionForward {
	//View Page(*.jsp)의 경로와 파일명
	private String path = null;	

	//페이지 전환방식 결정 ▶ true : sendRedirect(), false : forward()
	private boolean isRedirect = false;

	//Getters & Setters
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}	
	

}//class ActionForward
