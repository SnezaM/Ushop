<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="description" content="" />
	<meta name="author" content="Snezana" />
		<style>

     		 #box1 { margin-left: 55em;  }

  	  </style>

<title>Hauptseitekunde</title>


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
			<h2> UShop 24/7</h2>
			<h3>Herzlich Willkommen <%=session.getAttribute("username")%></h3>
			
		<br />
		
		
		<div id="box1">
			<form action="LoginController" method="post">
			<input class="btn btn-primary" name="logout" type="submit" value="logout"/>
			</form>
		</div>
		
		
	<form action="Produktkundencontroller" method="post">
		<input class="btn btn-primary" name="produkteAnzeigenkunde" type="submit" value="Produkte anzeigen"/><br/>
	</form>
			
</html>