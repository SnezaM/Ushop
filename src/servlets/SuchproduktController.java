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

@WebServlet("/SuchproduktController")
public class SuchproduktController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public SuchproduktController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		
		Produktverwaltung p = Produktverwaltung.getInstance();
		String pname = request.getParameter("produktName");
		
		request.getSession(true).setAttribute("fehler", "Kein Produkt gefunden");
		 if(request.getParameter("produktName")!=null){
			 response.sendRedirect(request.getContextPath() +"/GesuchteProdukte.jsp");

			response.setContentType("text/html");
			return;
		}
		Produkt k = p.getProduktByName(pname);
		System.out.println(pname+ " ich habe das jetzt gemacht");
		
		
		
		if( k!=null){
			if(k.getProduktname().equals(pname)){
				
				
				ProduktDAO dao = new DatenBankProduktDAO();
				Produkt temp = dao.getProduktByName(pname);
				
				ProduktgruppeDAO daoGruppe = new DatenBankProduktgruppeDAO();
				Produktgruppe tempgruppe = daoGruppe.getProduktgruppeByID(temp.getProduktgruppeID());
				
				request.getSession().invalidate();
				System.out.println(" Erfolgreiche: Weiterleiten zu den Produkten!");
				
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
				
				response.sendRedirect(request.getContextPath() + "/GesuchteProdukte.jsp");
				response.setContentType("text/html");
				return;
			}
			
			request.getSession(true).setAttribute("fehler", "Ihr Produktname ist Falsch!");
			System.out.println("Dieses Produkt haben wir nicht");
		
		}
		
		System.out.println("SuchproduktController: Weiterleiten zur Produkten!");
		request.getRequestDispatcher("GesuchteProdukte.jsp").include(request, response);
		response.setContentType("text/html");
		
		
	}
}
	
	

