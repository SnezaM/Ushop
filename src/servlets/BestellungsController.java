/**
 * 
 */
package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DatenBankProduktDAO;
import dao.DatenBankProduktgruppeDAO;
import dao.ProduktDAO;
import dao.ProduktgruppeDAO;
import modell.Produkt;
import modell.Produktgruppe;


/**
 * <b>Gibt die Bestellungen des Kunden aus.</b>
 * <p>
 * Ist fuer die Ausgabe von Bestellungen zustaendig und somit der Controller zur
 * Anzeigeverwaltung von Bestellungen.
 * </p>
 * 
 * @author Katrin Rohrmueller (1309572)
 *
 */
@WebServlet("/BestellungsController")
public class BestellungsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Ruft den Konstruktor der Oberklasse {@link HttpServlet} auf.
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public BestellungsController() {
		super();
	}

	/**
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}
	
	/**
	 * @throws IOException 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("bestellungenAnzeigenKunde")!=null){		
			response.sendRedirect(request.getContextPath() + "/MeineBestellungen.jsp");
			response.setContentType("text/html");	
			return;
		}
		else{
			if(request.getParameter("BestellungsDetails")!=null){
				String bID = request.getParameter("BestellungsDetails");			
				request.getSession(true).setAttribute("zeigeID", bID);
				response.sendRedirect(request.getContextPath() + "/BestellungsDetails.jsp");
				response.setContentType("text/html");
				return;
			}
			if(request.getParameter("KundeProduktseite")!=null){
				String pID = request.getParameter("KundeProduktseite");
				
				request.getSession(true).setAttribute("zeigeID", pID);
				int produktid = Integer.parseInt(pID);
				
				ProduktDAO dao = new DatenBankProduktDAO();
				Produkt temp = dao.getProduktByProduktID(produktid);
				
				ProduktgruppeDAO daoGruppe = new DatenBankProduktgruppeDAO();
				Produktgruppe tempgruppe = daoGruppe.getProduktgruppeByID(temp.getProduktgruppeID());

				String pKat = tempgruppe.getProduktgruppenname();
				int gruppeid = temp.getProduktgruppeID();
				String pName = temp.getProduktname();
				double pPreis = temp.getPreis();
				String pBesch = temp.getBeschreibung();
				
				request.getSession(true).setAttribute("zeigeKategorie", pKat);
				request.getSession(true).setAttribute("zeigeGruppeid", gruppeid);
				request.getSession(true).setAttribute("zeigeName", pName);
				request.getSession(true).setAttribute("zeigePreis", pPreis);
				request.getSession(true).setAttribute("zeigeBeschr", pBesch);

				response.sendRedirect(request.getContextPath() + "/KundeProduktSeite.jsp");
				response.setContentType("text/html");
				return;
			}
			response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");
			response.setContentType("text/html");
			return;
		}
	}
}
