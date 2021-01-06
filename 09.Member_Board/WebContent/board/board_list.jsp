<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String id = (String) session.getAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BoardList</title>
</head>
<body>
<div align="center">
<h3>[게시판 화면 입니다]</h3>
<h4>[ <%=id %> 로 로그인 되었습니다 ]</h4>

<table border="1">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>

<!-- 글목록 표시 -->


<!-- 페이징 처리 -->

	<tr align="center">
		<td colspan="5">
			<% if( id != null && id.equals("admin") ) { %>
				<input type="button" value="회원관리"
					onclick="location.href = 'memberListAction.me'" />
			<% } %>
			<input type="button" value="로그아웃"
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