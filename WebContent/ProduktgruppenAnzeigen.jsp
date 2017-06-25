<%@page import="java.util.List"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
<%@page import="management.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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

	<title>Produktgruppe Anzeigen</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

	<h1>Alle Produktgruppen:</h1>
	<form action="Produktgruppeverwaltungscontroller" method="Post">
	<table class="table">
		<tr><th>Name</th><th>ProduktgruppeID</th><th>Beschreibung</th><th>Produktgruppe Loeschen</th></tr>
		
<%
Produktgruppenverwaltung prodverGruppe = Produktgruppenverwaltung.getInstance();
for(Produktgruppe i:prodverGruppe.getAlleProduktgruppen()){%>

		<tr>
			<td><%=i.getProduktgruppenname()%></td>
			<td><%=i.getProduktgruppeID()%></td>
			<td><%=i.getBezeichnung()%></td>
			<td><button name="loescheProduktgrupemitID" value="<%=i.getProduktgruppeID() %>" type="submit">Loeschen</button></td>

		</tr>		
<%} %>

<tr><td><a href="HauptseiteAdmin.jsp"><input type="submit" value="Retour" /></a></td><td></td><td></td></tr>
	</table>
	</form>
</div>
</div>
</body>
</html>