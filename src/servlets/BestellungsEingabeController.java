/**
 * 
 */
package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>Nimmt Bestellungen des Kunden auf.</b>
 * <p>
 * Ist fuer die Aufnahme von Bestellungen zustaendig und somit der Controller
 * zur Verwaltung von Bestellungen. Hier wird auch geprueft, ob die Eingaben
 * korrekt sind.
 * </p>
 * 
 * @author Katrin Rohrmueller (1309572)
 *
 */
public class BestellungsEingabeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Ruft den Konstruktor der Oberklasse {@link HttpServlet} auf.
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public BestellungsEingabeController() {
		super();
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
