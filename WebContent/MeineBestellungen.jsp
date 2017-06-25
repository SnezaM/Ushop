<%@page import="java.util.List"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
<%@page import="management.*"%>
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

	<title>Meine Bestellungen</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	<div class="jumbotron">	
		<h1>Meine Bestellungen:</h1>
		<form action="BestellungsController" method="Post">
		<table class="table">			
			<%
			int kundenID = (int) session.getAttribute("kundenid");
			Bestellungsverwaltung bestellver = Bestellungsverwaltung.getInstance();
			DecimalFormat formator = new DecimalFormat("####,####,###.00");
			List<Bestellung> bestellungen = bestellver.getAllBestellungenByKunde(kundenID);
			
			if(bestellungen.size()==0){
				%>
				<tr><th colspan="6"><h3>Es sind noch keine Bestellungen vorhanden.</h3></th></tr>
				<%
			}
			else {
				%>
				<tr><th>BestellungsID</th><th>Datum</th><th>Vermerk</th><th>Lieferart</th><th>Bestellungswert</th><th></th></tr> 
				<%
				for(Bestellung b : bestellungen) { 
				%>
					<tr>
						<td><%=b.getBestellungID()%></td>
						<td><%=b.getDatum()%></td>
						<td><%=b.getVermerk() %></td>
						<td><%=b.getLieferart() %></td>
						<td align="right"><%=formator.format(b.getGesamtpreis()) %> &euro;</td>
						<td align="right"><button name="BestellungsDetailsAnzeigen" value="<%=b.getBestellungID()%>" type="submit">Details anzeigen</button></td>
					</tr>
				<%
				}
			}
			%>
	
				<tr><td><a href="HauptseiteKunde.jsp"><input type="submit" value="Retour" /></a></td><td/><td/><td/><td/><td/></tr>
		</table>
		</form>
	</div>
	</div>
</body>
</html>