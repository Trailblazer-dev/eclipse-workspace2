<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<%
	String name = request.getParameter("name");
	String age = request.getParameter("age");
	%>
	<thead>
		<tr>
			<td>Is these the information</td>
			<td>you provided</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>You name</td>
			<td><%= name %></td>
		</tr>
		<tr>
			<td>You age</td>
			<td><%= age %></td>
		</tr>

	</tbody>
	<form action="Welcome.html" method="post">
	<input type="submit" value="Go back" > 
	</form>
</body>
</html>