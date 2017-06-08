package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BestellungsDAO;
import dao.DBBestellungsDAO;
import management.Bestellungsverwaltung;

/**
 * 
 * @author Katrin Rohrmueller (1309572)
 * 
 *         Die Klasse Warenkorbverwaltungscontroller ist zustaendig fuer die
 *         Abwicklung von Warenkorb und Bestellung.
 */

@WebServlet("/Warenkorbcontroller")
public class Warenkorbcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Der Konstruktor der Klasse Produktverwaltungscontroller wird mit Super()
	 * von der Oberklasse aufgerufen
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public Warenkorbcontroller() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Bestellungsverwaltung bvw = Bestellungsverwaltung.getInstance();
		if (request.getParameter("warenkorbAnzeigenKunde") != null) {
			response.sendRedirect(request.getContextPath() + "/Warenkorb.jsp");
			response.setContentType("text/html");
			return;
		}

		if (request.getParameter("posEntfernenID") != null) {
			String deletePosID = request.getParameter("posEntfernenID");
			int positionID = Integer.parseInt(deletePosID);
			String deleteFromID = request.getParameter("bestellungsID");
			int bestellungsID = Integer.parseInt(deleteFromID);
			request.getSession(true).setAttribute("zeigeIDWarenkorb", bestellungsID);
			
			if (bvw.removePositionFromBestellung(bestellungsID, positionID)) {
				response.sendRedirect(request.getContextPath() + "/WarenkorbEditieren.jsp");
				response.setContentType("text/html");
				return;
			} else {
				System.out.println("Loeschen gescheitert!");
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Position konnte nicht geloescht werden! "
						+ "Sollte das Problem weiterhin bestehen, wenden Sie sich an den Admin.');");
				out.println("location='WarenkorbEditieren.jsp';");
				out.println("</script>");
				return;
			}
		}
		
		if(request.getParameter("bearbeitungBeendenID")!= null){
			String bestellID = request.getParameter("bearbeitungBeendenID");
			int bestellungsID = Integer.parseInt(bestellID);
			request.getSession(true).setAttribute("zeigeIDWarenkorb", bestellungsID);
			response.sendRedirect(request.getContextPath() + "/Warenkorb.jsp");
			response.setContentType("text/html");
			return;
		}
		
		if(request.getParameter("minimize")!= null){
			String posID = request.getParameter("minimize");
			int positionID = Integer.parseInt(posID);
			String changeFromID = request.getParameter("bestellungsID");
			int bestellungsID = Integer.parseInt(changeFromID);
			request.getSession(true).setAttribute("zeigeIDWarenkorb", bestellungsID);
			
			if (bvw.aenderePosition(bestellungsID, positionID, 1)) {
				response.sendRedirect(request.getContextPath() + "/WarenkorbEditieren.jsp");
				response.setContentType("text/html");
				return;
			} else {
				System.out.println("Mengenaenderung gescheitert!");
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Position konnte nicht bearbeitet werden! "
						+ "Sollte das Problem weiterhin bestehen, wenden Sie sich an den Admin.');");
				out.println("location='WarenkorbEditieren.jsp';");
				out.println("</script>");
				return;
			}
		}
		
		if(request.getParameter("maximize")!= null){
			
		}

		else {
			response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");
			response.setContentType("text/html");
			return;
		}
	}
}
