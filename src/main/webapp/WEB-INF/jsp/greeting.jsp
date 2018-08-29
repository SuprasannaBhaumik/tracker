<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib  uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Greeting Page</title>
</head>
<body>
	<a href="?language=en">English</a>
	<a href="?language=es">Spanish</a>
	<h1><spring:message code="hello" /> ${text}</h1>
</body>
</html>