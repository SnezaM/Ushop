/**
* Das Pakage Servlet dient zur Kontrolle der Inputdaten und verweist 
* auf die JSP Seiten des benoetigten Bereichs. 
*/
package servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import management.Benutzerverwaltung;
import modell.Administrator;

	
/**
 * 
 * @author Snezana Milutinovic a1349326
 * 
 */
@WebServlet("/AdminRegistrierungsController")
public class AdminRegistrierungsController extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public AdminRegistrierungsController() {
	        super();
	        
	    }

		/**
		 * Falls ein Get request kommt, soll auf die Loginseite verwiesen werden.
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.getRequestDispatcher("Login.jsp").include(request, response);
			response.setContentType("text/html");
			
		}
		
		/**
		 * Hier wird geprüft ob die eingegebenen Daten korrekt sind, falls ja, war die Registrierung erfolgreich und es wird ein Mitarbeiter angelegt,
		 * falls nein, dann wird die jeweilige Fehlermeldung ausgegeben.
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Benutzerverwaltung b = Benutzerverwaltung.getInstance();
			
			//Hier wird geprüft, ob einfach nur auf das JSP zugegriffen wird und dies Authorisiert erfolgt
			if(request.getParameter("regBest")!=null){
				System.out.println("AdminRegistrierungsController: Authorisiert zum Mitarbeiter Registrieren --> RegistrierenMitarbeiter.jsp");
				request.getRequestDispatcher("AdminRegistrieren.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			
		//Ab hier wird alles bearbeitet, das vom MitarbeiterRegistrieren.jsp kommt
			if(request.getParameter("username")==null){//registrierbutton gedrückt
				System.out.println("MitarbeiterRegistrierController: keinUsername:Weiterleiten zum Registrieren!");
				request.getRequestDispatcher("AdminRegistrieren.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			else{
				String vorname=request.getParameter("vorname");
				String nachname=request.getParameter("nachname");
				String email=request.getParameter("email");
				String gehaltString=request.getParameter("gehalt");
				String geburtsdatum = request.getParameter("geburtsdatum");
				String username=request.getParameter("username");
				String password=request.getParameter("password");
				String passwordW=request.getParameter("passwordW");
		
				
			
				int gehalt;
				Double gehaltDouble = null;
				
				try{
					gehaltDouble = Double.parseDouble(gehaltString); 
					gehalt = gehaltDouble.intValue();
				}catch(Exception e){
					request.getSession(true).setAttribute("fehler", "Keine Nummer(HausNr oder Plz oder staffNo oder salary) oder zu lang!");
					System.out.println("MitarbeiterRegistrierungsController: Hausnummer oder PLZ oder salary oder staffNo ist keine Nummer!");
					request.getRequestDispatcher("AdminRegistrieren.jsp").include(request, response);
					response.setContentType("text/html");
					return;
				}
				
				
				if(Integer.MAX_VALUE==gehalt){
					request.getSession(true).setAttribute("fehler", "Nummer(Plz oder HausNr oder staffNo oder salary) ist zu lang!");
					System.out.println("MitarbeiterRegistrierungsController: Hausnummer oder PLZ oder salary oder staffNoist keine Nummer!");
					request.getRequestDispatcher("AdminRegistrieren.jsp").include(request, response);
					response.setContentType("text/html");
					return;
				}
				
				if(username.length()<=2  || password.length()<=2 ){
					request.getSession(true).setAttribute("fehler", "Fehler: Username od. Passwort zu kurz!");
					System.out.println("MitarbeiterRegistrierungsController: Pwd od. Username  <  5 Zeichen!");
					request.getRequestDispatcher("AdminRegistrieren.jsp").include(request, response);
					response.setContentType("text/html");
					return;
				}
				
				//Wiederholtes Passwort nicht korrekt
				if(!password.equals(passwordW) ){
					request.getSession(true).setAttribute("fehler", "Fehler: Passwortwiederholung nicht korrekt!");
					System.out.println("MitarbeiterRegistrierungsController: Passwortwiederholung nicht korrekt!!");
					request.getRequestDispatcher("AdminRegistrieren.jsp").include(request, response);
					response.setContentType("text/html");
					return;
				}
				
				//Username darf keine Abstände beinhalten
				if(username.length()!=username.replaceAll(" ","").length()){
					request.getSession(true).setAttribute("fehler", "Fehler: Username darf keine Leerzeichen enthalten!");
					System.out.println("MitarbeiterRegistrierungsController: Leerzeichen im Username!");
					response.sendRedirect("AdminRegistrieren.jsp");
					return;
				}
				
				
				//Nachdem Benutzer angelegt wurde, wird er automatisch(nicht über Login) zur Hauptseite.jsp weitergeleitet.
				if(b.adminAnlegen(email, vorname, nachname, username, password, gehalt, geburtsdatum)){
					
					Administrator a = b.getAdminByUserName(username);
				
					HttpSession session = request.getSession(true);
					session.setAttribute("message",username+" wurde nun als Mitarbeiter gespeichert");
					System.out.println("MitarbeiterRegistrierungsController: Kunde angelegt: "+vorname+" "+nachname+" "+email+" "+username+" "+password);
					session.setAttribute("fehler", null);
					response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
					response.setContentType("text/html");
					return;
				}
				//eingabe nicht erfolgreich:
				else{
					System.out.println("MitarbeiterRegistrierungsController: Person konnte nicht angelegt werden: "+vorname+" "+nachname+" "+email+" "+username+" "+password);
					request.getSession(true).setAttribute("fehler", "Fehler: Prüfen Sie das Datum(Jahr-Monat-Tag) oder der Username ist schon vergeben!");
					response.sendRedirect("AdminRegistrieren.jsp");
				}
			}

		}

	}



