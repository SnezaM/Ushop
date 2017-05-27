/**
* Das Pakage Servlet dient zur Kontrolle der Inputdaten und verweist 
* auf die JSP Seiten des benoetigten Bereichs. Dieses Package beinhaltet alle Servlets
*/

package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import management.Produktverwaltung;

/**
 * 
 * @author Mirza Talic a1367543
 * 
 * Dieses Servlet ist für die gesamte Produktaufnahme verantwortlich.
 * Diese Klasse ist der Controller zum Verwalten der ganzen Produkte. 
 * Sie prueft ob alle Produkteingaben in Ordnung sind.
 * Servlet implementation class ProdukteingabeController
 */
	
	
	@WebServlet("/Produkteingebencontroller")
	public class Produkteingebencontroller extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	    /**
	     * Der Konstruktort der Klasse Produkteingebencontroller wird mit Super() aufgerufen,
	     * von der Oberklasse
	     * @see HttpServlet#HttpServlet()
	     */
	    public Produkteingebencontroller() {
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
		 * Diese Methode kontrolliert die Eingaben und verweist auf weitere JSP's zusaetzlich prueft
		 * sie den Zustand der Parameter und leitet weitere Schritte ein. 
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			if(request.getParameter("produktEingeben")!=null){
				request.getRequestDispatcher("ProduktEingeben.jsp").include(request, response);
				response.setContentType("text/html");
				return;
				
			}
			
			try{
				
				int adminid = (int) request.getSession().getAttribute("adminID");
				String produktname = request.getParameter("name");
				String produktpreis = request.getParameter("preis");
				String produktkategorie = request.getParameter("kategorie");
				String beschreibung = request.getParameter("beschreibung");
				double ppreis = Double.parseDouble(produktpreis);
				int pkategorie = Integer.parseInt(produktkategorie);
				
				if(produktname.replaceAll(" ","").length() < 2){
					request.getSession().setAttribute("message", "Produkt konnte nicht hinzugefuegt werden1");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
				
				if(ppreis < 0.5){
					request.getSession().setAttribute("message", "Produkt konnte nicht hinzugefuegt werden2");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
				if(beschreibung.replaceAll(" ","").length() < 2){
					request.getSession().setAttribute("message", "Produkt konnte nicht hinzugefuegt werden3");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
				System.out.println("Wird jetzt hergestellt");
				Produktverwaltung prodver = Produktverwaltung.getInstance();
				if(prodver.produktAnlegen(produktname, ppreis, beschreibung, adminid, pkategorie)){
					request.getSession().setAttribute("message", "Produkt erfolgreich hinzugefuegt");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
			}catch(Exception e){//Numberformat oder Nullpointer, aber egal: Produkt kann nicht hinz gefï¿½gt werden
				request.getSession().setAttribute("message", "Produkt konnte nicht hinzugefuegt werden");
				response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
				response.setContentType("text/html");
				return;
			}
		}

	}

