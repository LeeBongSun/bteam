<%@page import="com.member.study.MemberDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setCharacterEncoding("utf-8");
List<MemberDTO> list = (List<MemberDTO>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MemberList</title>
<script type="text/javascript">
function fnDelete(id) {
	if ( confirm("회원을 정말 삭제하시겠습니까?") ) {
		location.href = "memberDeleteAction.me?id=${id }"
	}
}
</script>
</head>
<body>
<div align="center">
<h3>[전체회원 관리 화면 입니다]</h3>

<table border="1">
	<tr>
		<th>번호</th>
		<th>아이디</th>
		<th>이름</th>
		<th>나이</th>
		<th>성별</th>
		<th>이메일</th>
		<th>삭제</th>
	</tr>

<!-- 글목록 표시 -->
<%-- 
	<%if(list.size() == 0){ %>
		<tr align="center">
			<td colspan="8">검색된 글이 없습니다.</td>
		</tr>
	<%}else{ %>
		<%for(int i = 0; i < list.size(); i++){ %>
			<tr align="center">
				<td><%= list.size() - i %></td>
				<td><%= list.get(i).getMember_id() %></td>
				<td><a href="memberDetail.do?member_id=<%= list.get(i).getMember_id() %>">
					<%= list.get(i).getMember_name() %></a>
				</td>
				<td><%= list.get(i).getMember_age() %></td>
				<td><%= list.get(i).getMember_gender() %></td>
				<td><%= list.get(i).getMember_email() %></td>
				<td><a href="memberDelete.me?member_id=<%= list.get(i).getMember_id() %>">
					삭제</a>
				</td>
			</tr>
			
		<%}//for %>
	<%}//if %>
 --%>
 
	<%-- JSTL core 문과 EL 표현식을 이용한 출력 --%>
	<c:forEach var="i" items="${list }">
		<tr align="center">
			<td>${i.member_id }</td>
			<td>${i.member_id }</td>
			<td>${i.member_name }</td>
			<td>${i.member_age }</td>
			<td>${i.member_gender }</td>
			<td>${i.member_email }</td>
			<td>
				<c:if test="${i.member_id eq 'admin' }">관리자</c:if>
				<c:if test="${i.member_id ne 'admin' }">
					<input type="button" value="삭제" onclick="fnDelete('${i.member_id}')"/>
				</c:if>
			</td>
		</tr>
	</c:forEach>

<!-- 페이징 처리 -->

	<tr align="center">
		<td colspan="7">
<%-- 			<% if( id != null && id.equals("admin") ) { %>
				<input type="button" value="회원관리"
					onclick="location.href = 'memberListAction.me'" />
			<% } %>
 --%>			<input type="button" value="로그아웃"
					onclick="location.href = 'memberLogout.me'" />
			<input type="button" value="글쓰기"
					onclick="location.href = 'boardWrite.bo'" />
			<input type="button" value="회원정보수정"
					onclick="fnModify();" />
		</td>
	</tr>

</table>

</div>

</body>
</html>