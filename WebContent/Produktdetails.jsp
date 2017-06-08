<%@page import="java.util.List" %>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
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

	<title>Produkseite</title>
</head>

<%

	String id = request.getSession().getAttribute("zeigeIDProd").toString();
	String name = request.getSession().getAttribute("zeigeName").toString();
	String gruppeid = request.getSession().getAttribute("zeigeGruppeid").toString();
	String preis = request.getSession().getAttribute("zeigePreis").toString();
	String beschreibung = request.getSession().getAttribute("zeigeBeschr").toString();
	String kategorie = request.getSession().getAttribute("zeigeKategorie").toString();
	
%>

<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

<h2> Produkt:</h2><h1><%=name %> </h1>
	<form action="BestellungsController" method="Post">
	<table class="table">
	
			<tr><td> ID </td><td><%=id %></td>
			<tr><td> Name </td><td><%=name %></td> 
			<tr><td> Preis </td><td>&euro; <%=preis %></td> 
			<tr><td> Beschreibung </td><td><%=beschreibung %></td> 
			<tr><td> Produktgruppe </td><td><%=kategorie %></td> 
			<tr><td> ProduktgruppenID </td><td><%=gruppeid %></td> 
	
		
	</table>
		
	</form>

	
	<td><a href="BestellungsDetails.jsp"><input type="submit" value="Retour" /></a></td>
</div>
</div>
</body>
</html>