package servlets;
import java.io.*;
	

	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;  
		import javax.servlet.http.HttpServletRequest;  
		import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
	 
	@WebServlet(name="SuchproduktController",urlPatterns={"/SuchproduktController"})
		public class SuchproduktController extends HttpServlet {  
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			
			protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				// TODO Auto-generated method stub
				
				HttpSession session = request.getSession(true);
				request.getRequestDispatcher("/GesuchteProdukte.jsp").include(request, response);
				
				response.getWriter().append("Served at: ").append(request.getContextPath());
			}


			protected void doPost(HttpServletRequest request, HttpServletResponse response)  
	                throws ServletException, IOException {    
	     
				HttpSession session = request.getSession(true);
				
	    String  produktname=request.getParameter("produktname");
	    produktname.trim();
	   
	    response.setContentType( "text/html" );
	    response.sendRedirect( "ListOfSortedProductsJSP.jsp?foo="+ produktname ); 
	    
	   
	       
	    }  
	    
	}  
