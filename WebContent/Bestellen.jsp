<%@page import="java.util.List"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
<%@page import="management.*"%>
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
	<title>Bestellen</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

	<h1>Bestellen:</h1>
	<h2>Warenkorbinhalt</h2>
	<table class="table">	
		<%
		int kundenID = (int) session.getAttribute("kundenid");
		Bestellungsverwaltung bestellver = Bestellungsverwaltung.getInstance();
		Produktverwaltung prodver = Produktverwaltung.getInstance();
		DecimalFormat formator = new DecimalFormat("####,####,###.00");
		Bestellung warenkorb = bestellver.getWarenkorb(kundenID);
		int bestellungsID = warenkorb.getBestellungID();
		if(warenkorb!=null){
			%>
				<tr><th>PositionsID</th><th>Menge</th><th>Produkt</th><th>Preis/Stk</th><th>Gesamtpreis</th></tr> 
			<%
			for(Position p:bestellver.getAllPositionenByBestellungsID(warenkorb.getBestellungID()) ){
			Produkt prod = prodver.getProduktByProduktID(p.getArtikel());
			double preis = p.getGesamtpreis();
			int menge = p.getMenge();
			%>
				<tr>
					<td><%=p.getPostionID()%></td>
					<td align="center"><%=menge%></td>
					<td><%=prod.getProduktname() %></td>
					<td align="right"><%=formator.format(preis/menge) %> &euro;</td>
					<td align="right"><%=formator.format(preis) %> &euro;</td>
				</tr>	
			<%
			}
			%>			
			<tr>
				<td>	
					<form action="Warenkorbcontroller" method="post">
						<input class="btn btn-primary" name="warenkorbAnzeigenKunde" type="submit" value="Warenkorb bearbeiten"/><br/>
					</form>	
				</td>
				<td><td/><td><b>Bestellwert</b></td><td align="right"><%=formator.format(warenkorb.getGesamtpreis()) %> &euro;</td></tr>
		</table>
		
		<form action="BestellenController" method="Post">
			<table class=table>
				<tr>
				<th>Lieferart:</th>
				<td><select name="lieferart">
					<%for(Lieferart lieferart: Lieferart.values()) {%>
						<option value="<%=lieferart.name() %>"> <%=lieferart.name() %>   </option>
					<%} %>	
				</select></td> 
				<th>Vermerk:</th>
				<td><input type="text" name="vermerk" maxlength="50"/></td>
				</tr>
			</table>
			<table>
				<tr>
					<td><input type="submit" value="Bestellung absenden" /></td>
					<td/><td/><td/><td/>
				</tr>
			</table>
			<input  name="bestellungsID" value="<%=bestellungsID %>" type="hidden">
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