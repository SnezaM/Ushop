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
<!-- Arreze Fetahaj a1146976-->
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

	<title>Gefundene Produkte</title>
</head>

<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">
<img src="fotos/ushop247.png" alt="" height="90" width="500"/>
	
	<h1>Alle Produkte:</h1>
	<form action="SuchproduktController" method="Post">
	<table class="table">
	
		<tr><th>Name</th>
		<th>Produktgruppe Name</th>
		<th>Preis</th>
		<th>ProduktID</th>
		<th>Produktgruppe ID</th>

		
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
	</table>
	</form>

<tr><td><a href="HauptseiteKunde.jsp">
<input type="submit" value="back" /></a></td><td></td><td></td></tr>

</div>
</div>
</body>
</html>