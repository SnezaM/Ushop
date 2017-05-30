<%@page import="java.util.List"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	if(session.getAttribute("username")==null || session.getAttribute("username").equals("null")  ){
		System.out.println("Hauptseite: nicht eingeloggt -> Login");
		response.sendRedirect("Login.jsp");
	}
%>
<!-- Arreze Fetahaj a1146976-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="description" content="" />
	<meta name="author" content="Arreze" />
		<style>

     		 #box1 { margin-left: 55em;  }

  	  </style>

<title>Gefundene Produkte</title>


	<!-- To ensure proper rendering and touch zooming for mobile -->
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<!-- Bootstrap core CSS -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
		rel="stylesheet" />
	
	<!-- Bootstrap theme -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
		rel="stylesheet" />
		
	<title>Gefundene Produkte</title>
	
	</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

	<h1>Alle Produkte:</h1>
	<form action="SuchproduktController" method="Post">
	<table class="table">
	
		<tr><th>Name</th>
		<th>Produktgruppe Name</th>
		<th>Preis</th>
		<th>ProduktID</th>
		<th>Produktgruppe ID</th>
		<th>Produkt loeschen</th>
		<th>Details</th></tr> 
		
<%
ProduktDAO dao = new DatenBankProduktDAO();
for(Produkt i:dao.getProduktList()){%>
<%
ProduktgruppeDAO pdao = new DatenBankProduktgruppeDAO();
for(Produktgruppe j:pdao.getProduktgruppeList()){%>


		<tr>
			<td><%=i.getProduktname() %></td>
			<td><%=j.getProduktgruppenname() %></td>
			<td><%=i.getPreis() %></td>
			<td><%=i.getProduktID()%></td>
			<td><%=i.getProduktgruppeID() %></td>
			
			<td><button name="Warenkorb" value="<%=i.getProduktID()%>" 
			type="submit">Warenkorb</button></td>
		</tr>
		
<%} %>
<%} %>


<tr><td><a href="HauptseiteKunde.jsp">
<input type="submit" value="back" /></a></td><td></td><td></td></tr>
	</table>
	</form>
</div>
</div>
</body>
</html>