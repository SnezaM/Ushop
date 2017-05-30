package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DatenBankProduktDAO;
import dao.DatenBankProduktgruppeDAO;
import dao.ProduktDAO;
import dao.ProduktgruppeDAO;
import modell.Produkt;
import modell.Produktgruppe;


/**
* 
* @author Katrin Rohrmueller (1309572)
* 
* Die Klasse Warenkorbverwaltungscontroller ist zustaendig fuer die Abwicklung 
* von Warenkorb und Bestellung.   
*/

@WebServlet("/Warenkorbcontroller")
public class Warenkorbcontroller extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	/**
     * Der Konstruktor der Klasse Produktverwaltungscontroller wird mit Super() 
     * von der Oberklasse aufgerufen
     * @see HttpServlet#HttpServlet()
     */
    public Warenkorbcontroller() {
        super();
        
    }

			/**
			 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
			 */
			protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.getSession().invalidate();
				request.getRequestDispatcher("Login.jsp").include(request, response);
				response.setContentType("text/html");
			}

			/**
			 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
			 */
			protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				if(request.getParameter("warenkorbAnzeigenKunde")!=null){
					response.sendRedirect(request.getContextPath() + "/Warenkorb.jsp");
					response.setContentType("text/html");	
					return;
				}
				
				else {		
					response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");
					response.setContentType("text/html");
					return;
				}
			}
}
