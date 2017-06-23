import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DatenBankProduktDAO;
import modell.Administrator;
import modell.Kunde;
import modell.Produkt;
import dao.ProduktDAO;
import management.Benutzerverwaltung;
import management.Produktverwaltung;

@WebServlet("/SuchproduktController")
public class SuchproduktController {
	private static final long serialVersionUID = 1L;

	public SuchproduktController() {
		super();

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*request.getSession().invalidate();
		request.getRequestDispatcher("HauptseiteKunde.jsp").include(request, response);
		response.setContentType("text/html");
	}
		 */
		Produktverwaltung b = Produktverwaltung.getInstance();
		String pname="";

		ProduktDAO dao = new DatenBankProduktDAO();
		Produkt temp = dao.getProduktByName(pname);

		pname = request.getParameter("pname");
		if(pname!=null && !(pname.equals(""))){
			Produkt p = b.getProduktByName(pname);

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
	}




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}






}
