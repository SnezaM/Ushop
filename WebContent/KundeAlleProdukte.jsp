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

	<title>Alle Produkte</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

	<h1>Alle Produkte:</h1>
	<form action="Produktkundencontroller" method="Post">
	<table class="table">
		<tr><th>Name</th><th>Preis</th><th>ProduktID</th><th>Produktgruppe ID</th><th>Details</th><th></th></tr> 
		
<%
ProduktDAO dao = new DatenBankProduktDAO();
for(Produkt i:dao.getProduktList()){%>

		<tr>
			<td><%=i.getProduktname() %></td>
			<td><%=i.getPreis() %></td>
			<td><%=i.getProduktID()%></td>
			<td><%=i.getProduktgruppeID() %></td>
			<td>In den Warenkorb MUSS NOCH IMPLEMENTIERT WERDEN</td>
			
			<td><button name="KundeProduktseite" value="<%=i.getProduktID()%>" type="submit">Mehr</button></td>
			
		</tr>		
<%} %>

<tr><td><a href="HauptseiteKunde.jsp"><input type="submit" value="back" /></a></td><td></td><td></td><td></td></tr>
	</table>
	</form>
</div>
</div>
</body>
</html>