/**
* Das Pakage servlet dient zur Kontrolle der Inputdaten und verweist 
* auf die JSP Seiten des benoetigten Bereichs. 
*/
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DatenBankProduktDAO;
import dao.DatenBankProduktgruppeDAO;
import dao.ProduktDAO;
import dao.ProduktgruppeDAO;
import management.Bestellungsverwaltung;
import modell.Position;
import modell.Produkt;
import modell.Produktgruppe;


/**
* 
* @author Mirza Talic a1367543
* 
* Die Klasse Produktverwaltungscontroller ist zustaendig fuer die Abwicklung 
* der Produkte.   
*/

@WebServlet("/Produktkundencontroller")
public class Produktkundencontroller extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	/**
     * Der Konstruktor der Klasse Produktverwaltungscontroller wird mit Super() 
     * aufgerufen,von der Oberklasse
     * @see HttpServlet#HttpServlet()
     */
    public Produktkundencontroller() {
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
				if(request.getParameter("produkteAnzeigenkunde")!=null){			
					response.sendRedirect(request.getContextPath() + "/KundeAlleProdukte.jsp");
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
				//Waren zum Warenkorb adden
				if(request.getParameter("ZumWarenkorbGeben")!=null){
					Bestellungsverwaltung bverw = Bestellungsverwaltung.getInstance();
					ProduktDAO dao = new DatenBankProduktDAO();
					String pID = request.getParameter("ZumWarenkorbGeben");
					int produktid = Integer.parseInt(pID);
					double produktPreis = dao.getProduktByProduktID(produktid).getPreis();
					int bestellungsID = Integer.parseInt(request.getParameter("bestellungsID"));
					List<Position> positionen = bverw.getAllPositionenByBestellungsID(bestellungsID);
					boolean alreadyAdded = false;
					boolean noProblems = true;
					for(Position p: positionen){
						if(p.getArtikel()==produktid){
							noProblems = bverw.aenderePosition(bestellungsID, p.getPostionID(), 1);
							alreadyAdded = true;
						}
					}
					if(!alreadyAdded){
						int positionsID = (positionen.size()+1);
						Position position = new Position(positionsID, produktid, 1, produktPreis);
						noProblems = bverw.addPosition(bestellungsID, position);
					}	
					if(noProblems){
						response.sendRedirect(request.getContextPath() + "/Warenkorb.jsp");
						response.setContentType("text/html");
						return;
					}
					else {
						PrintWriter out = response.getWriter();
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Artikel konnte nicht zum Warenkorb hinzugefuegt werden! "
								+ "Sollte das Problem weiterhin bestehen, wenden Sie sich an den Admin.');");
						out.println("location='KundeAlleProdukte.jsp';");
						out.println("</script>");
						return;
					}
				}
				response.sendRedirect(request.getContextPath() + "/KundeAlleProdukte.jsp");
				response.setContentType("text/html");
				return;
				}
			}