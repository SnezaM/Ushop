<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	if(session.getAttribute("username")==null || session.getAttribute("username").equals("null")  ){
		System.out.println("Hauptseite: nicht eingeloggt -> Login");
		response.sendRedirect("Login.jsp");
	}
%>
	<!-- Snezana Milutinovic a1349326-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta name="description" content=""/>
	<meta name="author" content="Snezana"/>
	
	<title>Registrieren</title>
	
	<!-- To ensure proper rendering and touch zooming for mobile -->
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	
	<!-- Bootstrap core CSS -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
		rel="stylesheet"/>
	
	<!-- Bootstrap theme -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
		rel="stylesheet"/>

</head>
<body>
	<div class="container theme-showcase" role="main">
		<div class="jumbotron">
			<h1>Registrierung:</h1>
			<%
				if (request.getSession().getAttribute("fehler") != null) {
			%>
					<h2><%=request.getSession().getAttribute("fehler")%></h2>
			<%
					request.getSession().setAttribute("fehler", null); //nach ausgabe auf null setzen
				}
			%>
			<form action="RegistrierungsController" method="POST">
				<div align="center">
				<table class="table">
					<tr>
						<th>Parameter</th>
						<th>Wert</th>
					</tr>

					<tr>
						<td>Vorname:</td>
						<td><input type="text" name="vorname" value="" /></td>
					</tr>
					<tr>
						<td>Nachname:</td>
						<td><input type="text" name="nachname" value="" /></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td><input type="text" name="email" value="" /></td>
					</tr>
					<tr>
						<td>PLZ:</td>
						<td><input type="text" name="plz" value="" /></td>
					</tr>
					<tr>
						<td>Strasse:</td>
						<td><input type="text" name="strasse" value="" /></td>
					</tr>
					<tr>
						<td>Hausnummer:</td>
						<td><input type="text" name="nummer" value="" /></td>
					</tr>
				
					<tr>
						<td>Username:</td>
						<td><input type="text" name="username" value="" /></td>
					</tr>
					<tr>
						<td>Passwort:</td>
						<td><input type="password" name="password" /></td>
					</tr>
					<tr>
						<td>Passwort wiederholen:</td>
						<td><input type="password" name="passwordW" /></td>
					</tr>
				</table>
				</div>
				
				<div align="center">
				<table class="table">
					
						<tr>
						<td><input type="submit" value="Absenden" /></td>
						<td></td>
					</tr>
					
					</table>
					</div>
				
				
			</form>
			<form action="Login.jsp" method="GET">
				<table>
					<tr>
						<td><input type="submit" value="Retour" /></td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
	
</body>
</html>