<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
    <meta charset="UTF-8">
    <title>Create New Account</title>
</head>
    <h1>Create New Account</h1>
    <p>Please enter the proposed name and password.</p>
    <form action="createAccount" method="post">
        User Name: <input type = "text" name = "name"/><br>
        Password: <input type ="text" name="password"/>
        <input type = "submit" value = "Login">
    </form>
</html>