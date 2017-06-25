<%@page import="java.lang.reflect.Array"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.*"%>
<%@page import="modell.*"%>
<%@page import="management.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("username") == null || session.getAttribute("username").equals("null")) {
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

<title>Produksuche</title>
</head>

<body>


	<form method="post" name="Search" action="ListOfSortedProducts">
		<table>
			<tr>
				<td colspan=2 style="font-size: 12pt;" align="center">
			<tr>
				<td><input type="text" class="input inputsearch" name="keyword"
					placeholder="Search.." required></td>
			</tr>
			<tr>
				<td colspan=2 align="center"><input type="submit"
					class="button button1" value="Search" /></td>
			</tr>
		</table>
	</form>
	<br />
	<br />
	<ul class="zebra">

		<%
		Produktverwaltung prodver = Produktverwaltung.getInstance();
			List<Produkt> produkte = new ArrayList<Produkt>();
			produkte = prodver.getAlleProdukt();

			for (int i=0;i<produkte.size();i++)
	          {

	              out.println("<li>"+ "<p>"+"Name of Product: "+produkte.get(i).getProduktname()+"<br>"+
	            		  "Description: "+produkte.get(i).getBeschreibung()+"<br>"+
	            		  "Price: "+ produkte.get(i).getPreis()+"<br>" +"</li>"+"</p>");}
	              
	%>
	</ul>
</body>

</html>