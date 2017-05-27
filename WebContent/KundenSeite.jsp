<%@page import="java.util.List"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- To ensure proper rendering and touch zooming for mobile -->
	<meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">

	<title>Alle Kunden</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

	<h1>Alle Kunden:</h1>
	<form action="BenVerController" method="Post">
	<table class="table">
		<tr><th>BenutzerID</th><th>Vorname</th><th>Nachname</th><th>KundenD</th><th>Strasse</th><th>Hausnummer</th><th>Username</th></tr> 
		
<%
BenutzerDAO dao = new DatenBankBenutzerDAO();
for(Kunde i:dao.getKundeListe()){%>

		<tr>
			<td><%=i.getBenutzerid()%></td>
			<td><%=i.getVorname()%></td>
			<td><%=i.getNachname()%></td>
			<td><%=i.getKundenID()%></td>
			<td><%=i.getStrasse()%></td>
			<td><%=i.getHausnummer() %></td>
			<td><%=i.getUsername()%></td>
			
			
			<td><button name="loescheKunde" value="<%=i.getBenutzerid() %>" type="submit">Loeschen</button></td>
			
			
		</tr>		
<%} %>


	</table>
	</form>
	<form method="get" action="HauptseiteAdmin.jsp">
			    <button type="submit">Back</button>
			</form>
</div>
</div>
</body>
</html>