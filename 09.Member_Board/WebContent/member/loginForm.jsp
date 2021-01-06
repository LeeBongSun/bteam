<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

%>
<%!%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
<script type="text/javascript">
function fnJoin() {
	if ( confirm("회원가입하시겠습니까?") ) {
		location.href = "memberJoin.me"
	}
}
</script>
</head>
<body>
<div align="center">
<h3>[로그인 페이지 입니다]</h3>
<form action="memberLoginAction.me" method="post">
<table border="1">
	<tr>
		<th>아이디</th>
		<td>
			<input type="text" name="member_id" required="required" />
		</td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td>
			<input type="password" name="member_pw" required="required" />
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="로그인" />
			<input type="button" value="회원가입" onclick="fnJoin();"/>
		</td>
	</tr>

</table>

</form>
</div>
</body>
</html>