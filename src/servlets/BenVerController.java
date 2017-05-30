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
import management.Benutzerverwaltung;
import modell.Administrator;


/**
 * 
 * @author Snezana Milutinovic a1349326
 * 
 */
@WebServlet("/BenVerController")
public class BenVerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BenVerController() {
        super();
    }

	
    
    
	/**
	 * Falls ein Get request kommt, soll auf die Loginseite verwiesen werden.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BenVerController: Weiterleiten zu Login.jsp");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Benutzerverwaltung b = Benutzerverwaltung.getInstance();
		
		if(request.getParameter("alleKundenAnzeigen")!=null){
			response.sendRedirect(request.getContextPath() + "/KundenSeite.jsp");
			response.setContentType("text/html");
			return;
		}
		
		if(request.getParameter("loescheKunde")!=null){
			String benutzerid = request.getParameter("loescheKunde");
			int benID = Integer.parseInt(benutzerid);
			b.loescheKunden(benID);
			response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp"); 
			response.setContentType("text/html");
			return;
		}
		
		if(request.getParameter("alleAdminsAnzeigen")!=null){
			List<Administrator> adminliste = b.getAdministratorListe();
			request.getSession().setAttribute("adminliste", adminliste); 
			request.getRequestDispatcher("AdminSeite.jsp").include(request, response);
			response.setContentType("text/html");
			return;
		}
		
		if(request.getParameter("loescheAdmin")!=null){
			String benutzerid = request.getParameter("loescheAdmin");
			int benID = Integer.parseInt(benutzerid);
			b.loescheAdmin(benID);
			response.sendRedirect(request.getContextPath() + "/HauptseiteAdmin.jsp");
			response.setContentType("text/html");
			return;
		}
		
		
		
		
		
		
		
		
		
		
		
	}

}
