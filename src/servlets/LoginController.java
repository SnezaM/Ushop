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
import modell.Kunde;
import modell.Administrator;


/**
 * 
 * @author Snezana Milutinovic a1349326
 * 
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;
       
    
    public LoginController() {
        super();
        
    }

	/**
	 * Falls ein Get request kommt, soll auf die Loginseite verwiesen werden.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		System.out.println("LoginController: Weiterleiten zum Login!");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * Hier wird gepr�ft ob die eingegebenen Daten korrekt sind, falls ja, war der Login erfolgreich und es wird zur Hauptseite verwiesen,
	 * falls nein, dann wird die jeweilige Fehlermeldung ausgegeben.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Benutzerverwaltung b = Benutzerverwaltung.getInstance();
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		System.out.println("LoginController: Pruefe Login: '"+uname+"' mit PWD:'"+pwd+".");
		
		request.getSession(true).setAttribute("fehler", "Kein Benutzer mit solch einem Usernamen registriert!");

		
		//da der logout-button auf der Hauptseite den LoginController aufruft, wird hier gepr�ft, ob er 
		//gedr�ckt wurde
		if(request.getParameter("logout")!=null){
			System.out.println("Logout von : "+request.getSession().getAttribute("username"));
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + "/Login.jsp");
			response.setContentType("text/html");
			return;
		}
		
		
		Kunde k = b.getKundeByUName(uname);
		System.out.println(uname+ " ich habe das jetzt gemacht");
		
		Administrator a = b.getAdminByUserName(uname);
		System.out.println(uname+ " ich habe das jetzt gemacht");
		if( k!=null){
			if(k.getPasswort().equals(pwd)){
				request.getSession().invalidate();
				System.out.println("LoginController: Erfolgreiche Pruefung(istKunde): Weiterleiten zur Hauptseite des Kunden!");
				HttpSession session = request.getSession(true);
				session.setAttribute("username", uname);
				session.setAttribute("benutzerid",k.getBenutzerid());
				session.setAttribute("kundenid", k.getKundenID());
				session.setAttribute("vorname", k.getVorname());
				session.setAttribute("nachname", k.getNachname());
				session.setAttribute("fehler", null);
				response.sendRedirect(request.getContextPath() + "/HauptseiteKunde.jsp");
				response.setContentType("text/html");
				return;
			}
			request.getSession(true).setAttribute("fehler", "Ihr Passwort ist nicht korrekt!");
			System.out.println("Logincontroller: Falsches Passwort eingegeben von '"+uname+"'");
		}
		
		
		if(a != null){
			if(a.getPasswort().equals(pwd)){
				request.getSession().invalidate();
				System.out.println("LoginController: Erfolgreiche Pruefung(istMitarbeiter): Weiterleiten zur Hauptseite des Mitarbeiters!");
				HttpSession session = request.getSession(true);
				session.setAttribute("username", uname);
				session.setAttribute("username", uname);
				session.setAttribute("benutzerid",a.getBenutzerid());
				session.setAttribute("adminID", a.getAdminID());
				session.setAttribute("vorname", a.getVorname());
				session.setAttribute("nachname", a.getNachname());
				session.setAttribute("fehler", null);
				response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
				response.setContentType("text/html");
				return;
			}
			System.out.println("Logincontroller: Falsches Passwort eingegeben von '"+uname+"'");
			request.getSession(true).setAttribute("fehler", "Ihr Passwort ist nicht korrekt!");
		}


		System.out.println("LoginController: Weiterleiten zum Login!");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

}
