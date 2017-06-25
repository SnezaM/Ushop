/**
* Das Pakage Servlet dient zur Kontrolle der Inputdaten und verweist 
* auf die JSP Seiten des benoetigten Bereichs. 
*/
package servlets;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DatenBankProduktDAO;
import dao.ProduktDAO;
import management.Produktgruppenverwaltung;
import management.Produktverwaltung;
import modell.Produkt;

/**
 * 
 * @author Mirza Talic a1367543
 * 
 */
@WebServlet("/Produktgruppeverwaltungscontroller")
public class Produktgruppeverwaltungscontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Produktgruppeverwaltungscontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("produktgruppeAnzeigen")!=null){
			response.sendRedirect(request.getContextPath() + "/ProduktgruppenAnzeigen.jsp");
			response.setContentType("text/html");
			return;
		}else
			
			// der Loeschbutton beim jeweiligen Produktgruppe gedrueckt wurde.
			if(request.getParameter("loescheProduktgrupemitID")!=null){
				
				String produktgruppeID = request.getParameter("loescheProduktgrupemitID");
				
				int produktgrID = Integer.parseInt(produktgruppeID);
				
				Produktgruppenverwaltung prodverGruppe = Produktgruppenverwaltung.getInstance();
				Produktverwaltung pro = Produktverwaltung.getInstance();
				//ProduktDAO dao = new DatenBankProduktDAO();
				//List<Produkt> liste = dao.getProduktList(); 
				List <Produkt> liste = pro.getAlleProdukt();
				
				int count = 0;
				for(Produkt x: liste){
					if(produktgrID == x.getProduktgruppeID()){
						count ++;
					}
				}
				if(count>0){
					request.getSession().setAttribute("message", "FEHLER: Es befinden sich noch Produkte in der Produktgruppe");
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
				
				prodverGruppe.loescheProduktgruppe(produktgrID);
				
			
				response.sendRedirect(request.getContextPath() + "/ProduktgruppenAnzeigen.jsp");
				response.setContentType("text/html");
				return;
			}
		
		response.sendRedirect("HauptseiteAdmin.jsp");
	}

}
