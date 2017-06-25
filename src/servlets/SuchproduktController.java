package servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DatenBankProduktDAO;
import modell.Administrator;
import modell.Kunde;
import modell.Produkt;
import dao.ProduktDAO;
import management.Benutzerverwaltung;
import management.Produktverwaltung;
/**
 * 
 * @author Arreze Fetahaj a1146976
 *
 */
@WebServlet("/SuchproduktController")
public class SuchproduktController {
	private static final long serialVersionUID = 1L;

	public SuchproduktController() {
		super();

	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Produktverwaltung b = Produktverwaltung.getInstance();
		String produktname="";

		ProduktDAO dao = new DatenBankProduktDAO();
		Produkt temp = dao.getProduktByName(produktname);

		produktname = request.getParameter("produktname");
		if(produktname!=null && !(produktname.equals(""))){
			Produkt p = b.getProduktByName(produktname);

			int gruppeid = temp.getProduktgruppeID();
			String pName = temp.getProduktname();
			double pPreis = temp.getPreis();
			String pBesch = temp.getBeschreibung();

			request.getSession(true).setAttribute("zeigeGruppeid", gruppeid);
			request.getSession(true).setAttribute("zeigeName", pName);
			request.getSession(true).setAttribute("zeigePreis", pPreis);
			request.getSession(true).setAttribute("zeigeBeschr", pBesch);

			response.sendRedirect(request.getContextPath() + "/KundeProduktSeite.jsp");
			response.setContentType("text/html");
			return;
		}
	}*/


		HttpSession session = request.getSession(true);
		request.getRequestDispatcher("/HauptseiteKunde.jsp").include(request, response);

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*
		doGet(request, response);
	}*/
	HttpSession session = request.getSession(true);

		String name=request.getParameter("produktname");
		name.trim();

		response.setContentType( "text/html" );
		response.sendRedirect( "GesuchteProdukte.jsp?foo="+name ); }


	 



}
