/**
 * 
 */
package servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EntryToEnumeration;
import management.Bestellungsverwaltung;
import modell.Bestellung;
import modell.Lieferart;

/**
 * <b>Ermoeglicht das der Kunde anhand seines Warenkorbs eine Bestellung
 * taetigen kann.</b>
 * <p>
 * Ist fuer die Bestellung von Kunden zustaendig und somit der Controller um den
 * Kunden Bestellungen taetigen zu lassen.
 * </p>
 * 
 * @author Katrin Rohrmueller (1309572)
 *
 */
@WebServlet("/BestellenController")
public class BestellenController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Ruft den Konstruktor der Oberklasse {@link HttpServlet} auf.
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public BestellenController() {
		super();
	}

	/**
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Soweit");
		if (request.getParameter("bestellen") != null) {
			response.sendRedirect(request.getContextPath() + "/Bestellen.jsp");
			response.setContentType("text/html");
			System.out.println("Sogut");
			return;
		}

		try {
			Bestellungsverwaltung bwver = Bestellungsverwaltung.getInstance();
			// Warenkorb erhalten und uebermittelte Parameter setzen
			String changeFromID = request.getParameter("bestellungsID");
			int bestellungsID = Integer.parseInt(changeFromID);
			Bestellung bestellung = bwver.getBestellungByID(bestellungsID);
			
			String lieferartString = request.getParameter("lieferart");
			Lieferart lieferart = EntryToEnumeration.entryToLieferart(lieferartString);
			bestellung.setLieferart(lieferart);
			
			String vermerk = request.getParameter("vermerk");
			bestellung.setVermerk(vermerk);
			
			//Aktuelles Datum erheben
			LocalDate aktDate = LocalDate.now();
			if (bwver.addBestellung(bestellung, aktDate.toString())) {
				request.getSession().setAttribute("message", "Bestellung erfolgreich durchgefuehrt!");
				response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");
				response.setContentType("text/html");
				return;
			}
			System.out.println("Oho");
		} catch (Exception e) {// Numberformat oder Nullpointer, aber egal:
								// Produkt kann nicht hinz gef�gt werden
			request.getSession().setAttribute("message",
					"Bestellung konnte nicht durchgefuehrt werden! Bitte versuchen Sie es nocheinmal oder wenden Sie sich an den Systemadmin.");
			response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");
			response.setContentType("text/html");
			return;
		}

		response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");
		response.setContentType("text/html");
		return;
	}
}
