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
import javax.servlet.http.HttpSession;
import management.Benutzerverwaltung;
import modell.Kunde;



/**
 * 
 * @author Snezana Milutinovic a1349326
 * 
 */
	@WebServlet("/RegistrierungsController")
	public class RegistrierungsController extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
	  
	    public RegistrierungsController() {
	        super();
	       
	    }

		/**
		 * Falls ein Get request kommt, soll auf die Registrierseite verwiesen werden.
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.getSession().invalidate();
			request.getRequestDispatcher("KundeRegistrieren.jsp").include(request, response);
			response.setContentType("text/html");
		}

		/**
		 * Hier wird gepr�ft ob die eingegebenen Daten korrekt sind, falls ja, war die Registrieren erfolgreich und es wird zur Hauptseite verwiesen,
		 * falls nein, dann wird die jeweilige Fehlermeldung ausgegeben.
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Benutzerverwaltung b = Benutzerverwaltung.getInstance();
			
			
			
			if(request.getParameter("username")==null){//registrierbutton gedr�ckt
				System.out.println("RegistrierController: keinUsername:Weiterleiten zum Registrieren!");
				request.getRequestDispatcher("KundeRegistrieren.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			else{
			
				String email=request.getParameter("email");
				String vorname=request.getParameter("vorname");
				String nachname=request.getParameter("nachname");
				String plzString=request.getParameter("plz");
				String strasse=request.getParameter("strasse");
				String hausnum=request.getParameter("nummer");
				String username=request.getParameter("username");
				String password=request.getParameter("password");
				String passwordW=request.getParameter("passwordW");
				
				
				
				int hausNr = 0;
				int plz = 0;
				try{
					hausNr = Integer.parseInt(hausnum);
					plz = Integer.parseInt(plzString);
				}catch(Exception e){
					request.getSession(true).setAttribute("fehler", "Hausnummer oder PLZ ist keine Nummer oder zu lang!");
					System.out.println("RegistrierungsController: Hausnummer oder PLZ ist keine Nummer!");
					request.getRequestDispatcher("KundeRegistrieren.jsp").include(request, response);
					response.setContentType("text/html");
					return;
				}
				
				if(username.length()<2  || password.length()<2 ){
					request.getSession(true).setAttribute("fehler", "Username od. Passwort zu kurz(mindestens 5 Zeichen ben�tigt)!");
					System.out.println("RegistrierungsController: Pwd od. Username  <  2 Zeichen!");
					request.getRequestDispatcher("KundeRegistrieren.jsp").include(request, response);
					response.setContentType("text/html");
					return;
				}
				
				//Wiederholtes Passwort nicht korrekt
				if(!password.equals(passwordW) ){
					request.getSession(true).setAttribute("fehler", "Passwortwiederholung nicht korrekt!");
					System.out.println("RegistrierungsController: Passwortwiederholung nicht korrekt!!");
					request.getRequestDispatcher("KundeRegistrieren.jsp").include(request, response);
					response.setContentType("text/html");
					return;
				}
				
				//Username enth�lt Abst�nde
				if(username.length()!=username.replaceAll(" ","").length()){
					request.getSession(true).setAttribute("fehler", "Fehler: Username darf keine Leerzeichen enthalten!");
					System.out.println("RegistrierungsController: Leerzeichen im Username!");
					response.sendRedirect("KundeRegistrieren.jsp");
					return;
				}
				
				
				//Nachdem Benutzer angelegt wurde, wird er automatisch(nicht �ber Login) zur Hauptseite.jsp weitergeleitet.
				if(b.kundeAnlegen(email, vorname, nachname, username, passwordW, strasse, plz, hausNr)){
					Kunde k = b.getKundeByUName(username);
					HttpSession session = request.getSession(true);
					session.setAttribute("username", username);
					session.setAttribute("benutzerid",k.getBenutzerid());
					session.setAttribute("kundenid", k.getKundenID());
					session.setAttribute("vorname", vorname);
					session.setAttribute("nachname", nachname);
					
				//	session.setAttribute("message", "Wilkommen "+username+" bei UShop 24/7!");
					System.out.println("RegistrierungsController: Kunde angelegt: "+vorname+" "+nachname+" "+email+" "+strasse+" "+username+" "+password);
				//	session.setAttribute("fehler", null);
					response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");//Damit Produktliste in session gleich aktualisiert wird
					response.setContentType("text/html");
					return;
				}
				//eingabe nicht erfolgreich:
				else{
					System.out.println("RegistrierungsController: Person konnte nicht angelegt werden: "+vorname+" "+nachname+" "+email+" "+strasse+" "+username+" "+password);
					request.getSession(true).setAttribute("fehler", "Fehler: Der Username ist leider schon vergeben!");
					response.sendRedirect("KundeRegistrieren.jsp");
					
					request.getSession(true).setAttribute("fehler", "Kein Produkt mit diesen Namen gefunden");
				}
			}
			
			
		
		}

	}
