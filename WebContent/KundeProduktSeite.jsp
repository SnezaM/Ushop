<%@page import="java.util.List" %>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
<%@page import="management.Bestellungsverwaltung"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
    
    <!-- Font Awesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

	<title>Produkseite</title>
</head>

<%
	//Benotigt fuer Warenkorb adden
	int kundenID = (int) session.getAttribute("kundenid");
	Bestellungsverwaltung bwver = Bestellungsverwaltung.getInstance();
	int bestellungsID = bwver.getWarenkorb(kundenID).getBestellungID();
	
	String id = request.getSession().getAttribute("zeigeID").toString();
	String name = request.getSession().getAttribute("zeigeName").toString();
	String gruppeid = request.getSession().getAttribute("zeigeGruppeid").toString();
	String preis = request.getSession().getAttribute("zeigePreis").toString();
	String beschreibung = request.getSession().getAttribute("zeigeBeschr").toString();
	String kategorie = request.getSession().getAttribute("zeigeKategorie").toString();
	
%>

<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

<h1> Produkt: <%=name %> </h1>
	<form action="Produktkundencontroller" method="Post">
	<table class="table">
	
			<tr><td> ID </td><td><%=id %></td></tr>
			<tr><td> Name </td><td><%=name %></td></tr> 
			<tr><td> Preis </td><td><%=preis %></td></tr>
			<tr><td> Beschreibung </td><td><%=beschreibung %></td></tr>
			<tr><td> Produktgruppe </td><td><%=kategorie %></td></tr>
			<tr><td> ProduktgruppenID </td><td><%=gruppeid %></td></tr>
			<tr>
				<td>
					<input  name="bestellungsID" value="<%=bestellungsID %>" type="hidden">
					<button name="ZumWarenkorbGeben" value="<%=id%>" type="submit">
						<i class="fa fa-shopping-basket" aria-hidden="true"> In den Warenkorb</i>
					</button>
				</td>
				<td/>
			</tr>
	</table>		
	</form>
	<table><tr><td><a href="KundeAlleProdukte.jsp"><input type="submit" value="Retour" /></a></td></tr></table>
</div>
</div>
</body>
</html>