<%@page import="modell.*"%>
<%@page import="management.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- Snezana Milutinovic a1349326-->

 <%
	if(session.getAttribute("username")==null || session.getAttribute("username").equals("null")  ){
		System.out.println("Hauptseite: nicht eingeloggt -> Login");
		response.sendRedirect("Login.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<meta name="description" content="" />
	<meta name="author" content="Snezana" />
		<style>

     		 #box1 { margin-left: 55em;  }

  	  </style>

<title>Hauptseiteadmin</title>


	<!-- To ensure proper rendering and touch zooming for mobile -->
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<!-- Bootstrap core CSS -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
		rel="stylesheet" />
	
	<!-- Bootstrap theme -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
		rel="stylesheet" />
	<title>Hauptseite</title>
	
	</head>
<body>
	<div class="container theme-showcase">
		<div class="jumbotron">
			<img src="fotos/ushop247.png" alt="" height="90" width="500"/>
			<h2> UShop 24/7 </h2>
				<h3>Herzlich Willkommen auf der Administrator Ansicht <%=session.getAttribute("username")%>!</h3> 
			
<%String fehler="";
String fehler1="";
session.setAttribute("profilbearbeiten", "");
if(session.getAttribute("message")!=null){fehler=(String)session.getAttribute("message"); session.setAttribute("message", "");%><h2><%=fehler %></h2><% } 
if(request.getSession().getAttribute("fehler")!=null){fehler1=(String)session.getAttribute("fehler"); session.setAttribute("fehler", "");%> <h2><%=fehler1%></h2><% } 
%>
	
		<div id="box1">
			<form action="LoginController" method="post">
			<input class="btn btn-primary" name="logout" type="submit" value="logout"/>
			</form>
		</div>
			
	
			
	<% if(session.getAttribute("username")!= null) if(Benutzerverwaltung.getInstance().getAdminByUserName((String)session.getAttribute("username")).getClass() == Administrator.class){ %>
		
	<form action="BenVerController" method="post">
		<input class="btn btn-primary" type="submit" name="alleKundenAnzeigen" value="Alle Kunden anzeigen"/>
		</form>
	<form action="BenVerController" method="post">
		<input class="btn btn-primary" type="submit" name="alleAdminsAnzeigen" value="Alle Admins anzeigen"/>
		</form>
		
				
	<form action="AdminRegistrierungsController" method="post">
		<input class="btn btn-primary" name="regBest" type="submit" value="Admin Registrieren"/>
		</form>	
		
		

	<form action="Produkteingebencontroller" method="post">
		<input class="btn btn-primary" name="produktEingeben" type="submit" value="Produkt hinzufuegen"/>
		</form>


	<form action="Produktverwaltungscontroller" method="post">
		<input class="btn btn-primary" name="produkteAnzeigen" type="submit" value="Produkte anzeigen"/><br/>
		</form>


	<form action="Produktgruppeerstellencontroller" method="post">
		<input class="btn btn-primary" name="neueProduktGruppe" type="submit" value="Produktgruppe hinzufuegen"/>
		</form>
	
	
	<form action="Produktgruppeverwaltungscontroller" method="post">
		<input class="btn btn-primary" name="produktgruppeAnzeigen" type="submit" value="Produktgruppen anzeigen"/>
		</form>
		
		
			
	<%} %>		
			
			
			
			
			
			
			
</html>