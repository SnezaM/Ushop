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
    <!--Mirza Talic a1367543-->
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
    
    <!-- Font Awesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

	<title>Alle Produkte</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">
	<%
	//Benotigt fuer Warenkorb adden
	int kundenID = (int) session.getAttribute("kundenid");
	
	Bestellungsverwaltung bwver = Bestellungsverwaltung.getInstance();
	int bestellungsID = bwver.getWarenkorb(kundenID).getBestellungID();
	%>
	<h1>Alle Produkte:</h1>
	<form action="Produktkundencontroller" method="Post">
	<table class="table">
		<tr><th>Name</th><th>Preis</th><th>ProduktID</th><th>Produktgruppe ID</th><th>Details</th><th></th></tr> 
		<tr><td> <input name = "bestellungsID" value = "<%=bestellungsID %>" type="hidden"  ></td></tr>
<%
Produktverwaltung prodver = Produktverwaltung.getInstance();
for(Produkt i:prodver.getAlleProdukt()){
%>

		<tr>
			<td><%=i.getProduktname() %></td>
			<td><%=i.getPreis() %></td>
			<td><%=i.getProduktID()%></td>
			<td><%=i.getProduktgruppeID() %></td>	
			<td><button name="KundeProduktseite" value="<%=i.getProduktID()%>" type="submit">Mehr</button></td>
			<td>
				<button name="ZumWarenkorbGeben" value="<%=i.getProduktID()%>" type="submit">
					<i class="fa fa-shopping-basket" aria-hidden="true"> In den Warenkorb</i>
				</button>
			</td>
		</tr>		
<%} %>

<tr><td></td><td></td><td></td><td></td></tr>
	</table>
	</form>
		<form method="get" action="HauptseiteKunde.jsp">
			    <button type="submit">Retour</button>
			</form>
</div>
</div>
</body>
</html>