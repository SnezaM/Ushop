/**
 * 
 */
package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

	}
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		doGet(request, response);
	}

}
