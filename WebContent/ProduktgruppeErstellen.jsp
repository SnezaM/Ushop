<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("username")==null || session.getAttribute("username").equals("null")  ){
		System.out.println("Hauptseite: nicht eingeloggt -> Login");
		response.sendRedirect("Login.jsp");
	}
%> 
     <!-- Mirza Talic a1367543-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- To ensure proper rendering and touch zooming for mobile -->
	<meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">

	<title>Produktgruppe erstellen</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">
	<h1>Produktgruppe erstellen:</h1>

	<form action="Produktgruppeerstellencontroller" method="POST">
			<table>
				<tr>
					<th>Parameter</th>
					<th>Wert</th>
				</tr>
				<tr>
					<td>Name der Produktgruppe:</td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td>Beschreibung:</td>
					<td><input type="text" name="beschreibung" /></td>
				</tr>
	
			</table>
			
			<input type="submit" value="Send" />
		</form>
	
		<a href="HauptseiteAdmin.jsp"><input type="submit" value="back" /></a>
</div>
</div>	
</body>
</html>