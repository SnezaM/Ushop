<%@page import="java.util.List"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
<%@page import="management.*"%>
<%@page import="management.Bestellungsverwaltung"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
    
<%
	if(session.getAttribute("username")==null || session.getAttribute("username").equals("null")  ){
		System.out.println("Hauptseite: nicht eingeloggt -> Login");
		response.sendRedirect("Login.jsp");
	}
%>
    <!-- Katrin Rohrmüller, a1309572-->
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
	<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<title>Warenkorb</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

	<h1>Mein Warenkorb:</h1>
	<%
	int kundenID = (int) session.getAttribute("kundenid");
	Produktverwaltung prodver = Produktverwaltung.getInstance();
	Bestellungsverwaltung bwver = Bestellungsverwaltung.getInstance();
	DecimalFormat formator = new DecimalFormat("####,####,###.00");
	Bestellung warenkorb = bwver.getWarenkorb(kundenID);
	int bestellungsID = warenkorb.getBestellungID();
	List<Position> positionen = bwver.getAllPositionenByBestellungsID(bestellungsID);
	if(positionen.size()>0){
	%>
		<form action="Warenkorbcontroller" method="Post">
			<table class="table">	
				<tr><th>PositionsID</th><th/><th>Menge</th><th/><th>Produkt</th><th>Preis/Stk</th><th>Gesamtpreis</th><th/></tr> 
			<%
			for(Position p: positionen){
			Produkt prod = prodver.getProduktByProduktID(p.getArtikel());
			double preis = p.getGesamtpreis();
			int menge = p.getMenge();
			%>
				<tr>
					<td><%=p.getPostionID()%></td>
					<td>
						<input  name="bestellungsID" value="<%=bestellungsID %>" type="hidden">
						<button name="minimize" value="<%=p.getPostionID() %>" type="submit"><i class="fa fa-minus" aria-hidden="true"></i></button>
					</td>
					<td align="center"><%=menge%></td>
					<td>
						<input  name="bestellungsID" value="<%=bestellungsID %>" type="hidden">
						<button name="maximize" value="<%=p.getPostionID() %>" type="submit"><i class="fa fa-plus" aria-hidden="true"></i></button>
					</td>
					<td><%=prod.getProduktname() %></td>
					<td align="right"><%=formator.format(preis/menge) %> &euro;</td>
					<td align="right"><%=formator.format(preis) %> &euro;</td>
					<td align="right">
						<button name="posEntfernenID" value="<%=p.getPostionID() %>" type="submit"><i class="fa fa-trash" id="remove"></i></button>
					</td>
				</tr>	
			<%
			}
		%>			
			<tr><td></td><td></td><td></td><td><td/><td/><th>Bestellwert</th><td align="right"><%=formator.format(warenkorb.getGesamtpreis()) %> &euro;</td><td/></tr>
		</table>
	</form>
	<form action="BestellenController" method="post">
		<input class="btn btn-primary" name="bestellen" type="submit" value="Produkte bestellen"/><br/>
	</form>	
	<%	
	}
	else {
	%>
		<table class=table>
			<tr><td><h3>Es befinden sich keine Produkte im Warenkorb.</h3></td></tr>
		</table>
	<%
	}
	%>
	<br>
	<hr>
	<table>
			<tr>
				<td><a href="HauptseiteKunde.jsp"><input type="submit" value="Retour zur Hauptseite" /></a></td>
			</tr>	
	</table>
</div>
</div>
</body>
</html>