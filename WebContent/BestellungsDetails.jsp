<%@page import="java.util.List"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
	if(session.getAttribute("username")==null || session.getAttribute("username").equals("null")  ){
		System.out.println("Hauptseite: nicht eingeloggt -> Login");
		response.sendRedirect("Login.jsp");
	}
%>
	<!-- Katrin Rohrmueller, 1309572-->
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

	<title>BestellungsDetails</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	<div class="jumbotron">	
		<%
			int bestellungsID = Integer.valueOf((String) session.getAttribute("zeigeID"));
		%>
		<h1>Ihre Bestellung:</h1>
		<table class="table">
			<tr><th>Bestellnummer:</th><th>Bestelldatum:</th><th>Versandart:</th><th>Vermerk:</th></tr>
			<tr><td><%=bestellungsID%></td><td></td><td></td><td></td></tr>
			
		</table>
		<br>
		<form action="BestellungsController" method="Post">
		<table class="table">
			<tr><th>PositionsID</th><th>Menge</th><th>ProduktID</th><th>Gesamtpreis</th><th/></tr> 
			
			<%
			BestellungsDAO dao = new DBBestellungsDAO();
			DecimalFormat formator = new DecimalFormat("####,####,###.00");
			List<Position> pos = dao.readPositionenByBestellungID(bestellungsID);
			for(Position p : pos) { 
			%>
				<tr>
					<td><%=p.getPostionID()%></td>
					<td><%=p.getMenge()%></td>
					<td><%=p.getArtikel() %></td>
					<td align="right"><%=formator.format(p.getGesamtpreis()) %> &euro;</td>
					<td align="right"><button name="ProduktDetailsAnzeigen" value="<%=p.getArtikel()%>" type="submit">Produktdetails anzeigen</button></td>
				</tr>
			<%
			} 
			%>
	
			<tr><td><button name="bestellungenAnzeigenKunde" type="submit">Retour</button></td><td/><td/><td/><td/></tr>
		</table>
		</form>
	</div>
	</div>
</body>
</html>