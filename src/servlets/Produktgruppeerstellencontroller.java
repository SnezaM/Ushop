/**
 * Das Pakage Servlet dient zur Kontrolle der Inputdaten und verweist 
 * auf die JSP Seiten des benoetigten Bereichs. 
 */
	
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import management.Produktgruppenverwaltung;



/**
 * 
 * @author Mirza Talic a1367543
 * 
 * Dieses Servlet ist für die gesamten Produktgruppen verantwortlich.
 * Die Klasse Produktgruppeerstellencontroller ist zuständig für die gesamte Abwicklung 
 * der Produktgruppen.  
 * 
 */
	@WebServlet("/Produktgruppeerstellencontroller")
	public class Produktgruppeerstellencontroller extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	    /**
	     * Der Konstruktor der Klasse Produktgruppeerstellencontroller wird mit Super() 
	     * aufgerufen,von der Oberklasse
	     * @see HttpServlet#HttpServlet()
	     */
	    public Produktgruppeerstellencontroller() {
	        super();
	        // TODO Auto-generated constructor stub
	    }

		/**
		 *  Falls eine Get Request kommt, dann wird sie automatisch zum Login verwiesen.
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.getRequestDispatcher("Login.jsp").include(request, response);
			response.setContentType("text/html");
		}

		/**
		 * Diese Mehtode kontrolliert die Eingaben und ruft weitere JSP's auf zusaetzlich prueft
		 * sie den Wert der Parameter.
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			if(request.getParameter("neueProduktGruppe")!=null){
				response.sendRedirect(request.getContextPath() + "/ProduktgruppeErstellen.jsp");
				response.setContentType("text/html");
				return;
			}
					
			try{
				
				String name = request.getParameter("name");
				String beschreibung = request.getParameter("beschreibung");
				int adminID = (int) request.getSession().getAttribute("adminID");
				
				if(name.replaceAll(" ","").length() < 2 ){
					request.getSession().setAttribute("message", "Produktgruppe konnte nicht hinzugefuegt werden");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
			
			
				Produktgruppenverwaltung prodGver = Produktgruppenverwaltung.getInstance();
				
				if(prodGver.produkgruppeAnlegen(name, beschreibung, adminID)){ 
					request.getSession().setAttribute("message", "Produktgruppe erfolgreich hinzugefuegt");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
				
				else{ 
					request.getSession().setAttribute("message", "Produktgruppe nicht erfolgreich hinzugefuegt");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
			}catch(Exception e){//Numberformat oder Nullpointer, aber egal: Produkt kann nicht hinz gefï¿½gt werden
				request.getSession().setAttribute("message", "Produktgruppe konnte nicht hinzugefuegt werden");
				response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
				response.setContentType("text/html");
				return;
			}
			
		}

	}
