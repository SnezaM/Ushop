<%@ page import="java.util.List"%>
<%@ page import="modell.Produktgruppe"%>
<%@ page import="dao.*" %>
<%@page import="management.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- To ensure proper rendering and touch zooming for mobile -->
	<meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">

	<title>Produkteingabe</title>
</head>


<%
Produktgruppenverwaltung prodGver = Produktgruppenverwaltung.getInstance();
List <Produktgruppe> liste = prodGver.getAlleProduktgruppen();
%>

<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">
	<h1>Produkt hinzufuegen:</h1>
	<form action="Produkteingebencontroller" method="POST">
		<table>
			<tr>
				<th>Parameter</th>
				<th>Wert</th>
			</tr>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Preis:</td>
				<td><input type="text" name="preis" /></td> 
				
			</tr>	
			<tr>
				<td>Beschreibung:</td>
				<td><input type="text" name="beschreibung" /></td>
			</tr>
			<tr>
				<td>Kategorie:</td>
				
				<td><select name="kategorie">
				<%for(Produktgruppe x: liste) {%>
						<option value="<%=x.getProduktgruppeID() %>"> <%=x.getProduktgruppenname() %>   </option>

					<%} %>	
				</select></td>
				
			</tr>
			<tr>
				<td><input type="submit" value="Absenden" /></td>
				<td></td>
			</tr>
		</table>
	</form>

	<a href="HauptseiteAdmin.jsp"><input type="submit" value="Retour" /></a>
</div>
</div>
</body>
</html>