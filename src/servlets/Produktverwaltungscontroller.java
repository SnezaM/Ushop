/**
* Das Pakage servlet dient zur Kontrolle der Inputdaten und verweist 
* auf die JSP Seiten des benoetigten Bereichs. 
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
import management.Produktverwaltung;
import modell.Produkt;
import modell.Produktgruppe;


/**
 * 
 * @author Mirza Talic a1367543
 * 
* Die Klasse Produktverwaltungscontroller ist fuer die Abwicklung 
* der Produkte zustaendig.   
*/

@WebServlet("/Produktverwaltungscontroller")
public class Produktverwaltungscontroller extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	    /**
	     * Der Konstruktor der Klasse Produktverwaltungscontroller wird mit Super() 
	     * aufgerufen,von der Oberklasse
	     * @see HttpServlet#HttpServlet()
	     */
	    public Produktverwaltungscontroller() {
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
	
			
			if(request.getParameter("produkteAnzeigen")!=null){			
				response.sendRedirect(request.getContextPath() + "/AlleProdukteAnzeigen.jsp");
				response.setContentType("text/html");	
				return;
			}
			
			if(request.getParameter("produkteAnzeigenkunde")!=null){			
				response.sendRedirect(request.getContextPath() + "/KundeAlleProdukte.jsp");
				response.setContentType("text/html");	
				return;
			}else
				
				
				if(request.getParameter("loescheProduktmitID")!=null){
					
					String produktID = request.getParameter("loescheProduktmitID");
					int produktid = Integer.parseInt(produktID);
					Produktverwaltung benver = Produktverwaltung.getInstance();
					benver.loescheProdukt(produktid);
					
					response.sendRedirect(request.getContextPath() + "/AlleProdukteAnzeigen.jsp");
					response.setContentType("text/html");
					return;
				}
				else
					
					if(request.getParameter("gotoProduktseite")!=null){
						String pID = request.getParameter("gotoProduktseite");
						
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
						int adminid = temp.getAdminID();
						

						request.getSession(true).setAttribute("zeigeKategorie", pKat);
						request.getSession(true).setAttribute("zeigeGruppeid", gruppeid);
						request.getSession(true).setAttribute("zeigeName", pName);
						request.getSession(true).setAttribute("zeigePreis", pPreis);
						request.getSession(true).setAttribute("zeigeBeschr", pBesch);
						request.getSession(true).setAttribute("adminID", adminid);

						response.sendRedirect(request.getContextPath() + "/Produktseite.jsp");
						response.setContentType("text/html");
						return;
					}
			
			response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
			response.setContentType("text/html");
			return;
			}
		}