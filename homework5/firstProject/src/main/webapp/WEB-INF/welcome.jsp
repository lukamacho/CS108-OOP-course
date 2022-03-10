<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Welcome <%= request.getParameter("name")%> .</title>
</head>
<body>
	<h1>Welcome <%= request.getParameter("name")%> .</h1>
</body>
</html>