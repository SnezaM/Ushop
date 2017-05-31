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
    <!-- Snezana Milutinovic a1349326-->
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

	<title>Warenkorb</title>
</head>
<body>
<div class="container theme-showcase" role="main">
<div class="jumbotron">

	<h1>Mein Warenkorb:</h1>
	
		<form action="Produktkundencontroller" method="Post">
	<table class="table">
		<tr><th>PositionsID</th><th>Menge</th><th>ProduktID</th><th>Gesamtpreis in EUR</th><th>Details</th></tr> 
		
<%
BestellungsDAO dao = new DBBestellungsDAO();
int kundenID = (int) session.getAttribute("benutzerid");
Bestellung warenkorb = dao.getWarenkorb(kundenID);
if(warenkorb!=null){
	for(Position p:dao.readPositionenByBestellungID(warenkorb.getBestellungID()) ){%>

				<tr>
					<td><%=p.getPostionID()%></td>
					<td><%=p.getMenge()%></td>
					<td><%=p.getArtikel() %></td>
					<td><%=p.getGesamtpreis() %></td>
					<td><button name="KundeProduktseite" value="<%=p.getGesamtpreis()%>" type="submit">Mehr</button></td>
				</tr>	
	<%} 
}%>


<tr><td><a href="HauptseiteKunde.jsp"><input type="submit" value="Zurueck" /></a></td><td></td><td></td></tr>
	</table>
	</form>
</div>
</div>
</body>
</html>