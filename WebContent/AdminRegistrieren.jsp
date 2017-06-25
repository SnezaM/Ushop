<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	if (session.getAttribute("username") == null
			|| session.getAttribute("username").equals("null")) {
		System.out
				.println("HauptseiteMitarbeiter: nicht eingeloggt -> Login");
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

<title>AdminRegistrierung</title>

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
			<h1>Administrator registrieren</h1>


<!-- Eingaben zum Registrieren -->
			<form action="AdminRegistrierungsController" method="POST">
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
						<td>Gehalt:</td>
						<td><input type="text" name="gehalt" /></td>
					</tr>
					<tr>
						<td>Geburtsdatum:(JJJJ-MM-TT)</td>
						<td><input type="text" name="geburtsdatum" value="" /></td>
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
			
			

<!-- Einfaches Retour zur Hauptseite -->
			<form method="get" action="HauptseiteAdmin.jsp">
			    <button type="submit">Retour</button>
			</form>

		</div>
	</div>
</body>
</html>