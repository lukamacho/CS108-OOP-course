<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WELCOME</title>
</head>
<body>
        <h1>Welcome homework 5</h1>
        <p>Please login</p>
        <form action = "LoginServlet" method = "post">
            User Name: <input type = "text" name = "name"/><br>
            Password: <input type ="text" name="password"/>
            <input type = "submit" value = "Login">
        </form>
        <p><a href="createAccount">Create New Account</a></p>
</body>
</html>