<%@page import="java.util.ArrayList"%>
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

	<title>Gefundene Produkte</title>
</head>

<body>
    

        <h2>Search product by keyword</h2> 
        <br/><br/>

        <form method="post" name="Search" action="ListOfSortedProducts">
            <table cellpadding="15" border="0" width="100%" align="center"   background="images/logo.jpg">
                <tr><td colspan=2 style="font-size:12pt;" align="center">
                <tr> <td> <input type="text"  class="input inputsearch" name=" produktname" placeholder="Search.."required>
                    </td> </tr>      
                <tr><td colspan=2 align="center">
                        <input type="submit"  class="button button1" value="Search" /></td></tr>
            </table>
        </form>
        <br/><br/>
        <ul class="zebra">
           
<%  

ProduktDAO object = new DatenBankProduktDAO();
List<Produkt> list =new ArrayList<Produkt>();
list=object.getProduktList();


for (int i=0;i<list.size();i++)
          {

              out.println("<li>"+ "<p>"+"Name of Product: "+list.get(i).getProduktname()+"<br>"
            		  );}
              
%>
</ul>
</body>

<tr><td><a href="HauptseiteKunde.jsp">
<input type="submit" value="back" /></a></td><td></td><td></td></tr>


</body>
</html>