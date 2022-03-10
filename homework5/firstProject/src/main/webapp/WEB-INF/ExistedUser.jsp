<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Account</title>
</head>
<body>
	<h1>The Name <%=request.getParameter("name")%> is Already In Use</h1>
	<p>Please enter another name and password.</p>
	<form action="createAccount" method="post">
	User Name: <input type="text" name="name"/><br>
	Password: <input type="text" name="password"/>
	<input type="submit" value="Login">
	</form>
</body>
</html>