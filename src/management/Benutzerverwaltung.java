/**
 *  Das Package Management beinhaltet Klassen die zu Verwaltung der Produkte und Akteure benoetigt werden
 */
package management;

import dao.BenutzerDAO;
import dao.DatenBankBenutzerDAO;
import modell.Administrator;
import modell.Kunde;
import modell.Benutzer;
import java.util.List;

/**
 * Klasse Benutzerverwaltung verwaltet alle Methoden die mit dem Benutzer des Ushops24/7 in Verbindung stehen.
 * @author Snezana Milutinovic a1349326
 *
 */
public class Benutzerverwaltung {
	
	
	private static Benutzerverwaltung benutzerverwaltungInstance=null;
	private BenutzerDAO dao;
	
	private Benutzerverwaltung() {
		dao = new DatenBankBenutzerDAO();
	}
	
	/**
	 *  Gibt das Objekt benutzerverwaltungInstance,
 	 *  wenn noch keines vorhanden wird eines erzeugt.
 	 */
	
	public static Benutzerverwaltung getInstance(){
		if(benutzerverwaltungInstance == null) benutzerverwaltungInstance = new Benutzerverwaltung();
		return benutzerverwaltungInstance;
	}
	
	/**
	 * 
	 * @param vorname Der Vorname des des jeweiligen Kundes der gespeichert werden soll.
	 * @param nachname Der Nachname des jeweiligen Kundes der gespeichert werden soll.
	 * @param email Die email des jeweiligen Kundes der gespeichert werden soll.
	 * @param username Der eindeutige Username des jeweiligen Kundes der gespeichert werden soll.
	 * @param password Das Passwort des jeweiligen Kundes der gespeichert werden soll.
	 * @param strasse Die Strasse des jeweiligen Kundes der gespeichert werden soll.
	 * @param plz Die PLZ des jeweiligen Kundes der gespeichert werden soll.
	 * @param hausnummer Die hausnummer des jeweiligen Kundes der gespeichert werden soll.
	 * @return true oder false
	 */
	public boolean kundeAnlegen( String email, String vorname, String nachname, String username, String password, String strasse, int plz, int hausnummer){
		System.out.println("Benutzerverwaltung:kundeAnlegen:  "+vorname+" "+nachname+", "+email+", "+username+" "+password+", anlegen!");
		return dao.speichereKunde(new Kunde(vorname,nachname,email, username,password, strasse,plz, hausnummer));
	}
	
	/**
	 * 
	 * @param email Die email des jeweiligen Administrators der gespeichert werden soll.
	 * @param vorname Der Vorname des des jeweiligen Administrators der gespeichert werden soll.
	 * @param nachname Der Nachname des jeweiligen Administrators der gespeichert werden soll.
	 * @param username Der eindeutige Username des jeweiligen Administrators der gespeichert werden soll.(min 2 Zeichen)
	 * @param passwort Das Passwort des jeweiligen Administrators der gespeichert werden soll.(min 2 Zeichen) 
	 * @param gehalt Das Gehalt des jeweiligen Administrators der gespeichert werden soll.
	 * @param geburtsdatum Das Geburtsdatum des jeweiligen Administrators der gespeichert werden soll.
	 * @return
	 */
	
	
	public boolean adminAnlegen( String email, String vorname, String nachname, String username, String password, double gehalt,String geburtsdatum){
		System.out.println("Benutzerverwaltung:adminAnlegen:  "+gehalt+", "+geburtsdatum+", "+username+" "+password+", anlegen!");
		return dao.speichereAdmin(new Administrator(email, vorname, nachname, username ,password ,gehalt,geburtsdatum));
	}
	
	/**
	 * 
	 * @param benutzerid  ist die ID des Kundes die geloescht werden soll 
	 * @return true oder false
	 */
	
	public boolean loescheKunden(int benutzerid){
		return dao.loescheKundeByID(benutzerid);
	}
	
	/**
	 * 
	 * @param benutzerid  ist die ID des Admins die geloescht werden soll 
	 * @return true oder false
	 */
	
	public boolean loescheAdmin(int benutzerid){
		return dao.loescheAdminByID(benutzerid);
	}
	/**
	 * 
	 * @return eine Liste mit aller in der Datenbank vorhandenen Benutzer
	 */
	
	public List<Benutzer> getBenutzerListe(){
		return dao.getBenutzerListe();
	}
	/**
	 * 
	 * @return eine Liste mit aller in der Datenbank vorhandenen Kunden
	 */
	
	public List<Kunde> getKundenListe(){
		return dao.getKundeListe();
	}
	/**
	 * 
	 * @return eine Liste mit aller in der Datenbank vorhandenen Admins
	 */
	public List<Administrator> getAdministratorListe(){
		return dao.getAdministratorListe();
	}
	/**
	 * 
	 * @param benutzerName ist der Name des Benutzers der gesucht wird
	 * @return den gesuchten Benutzer falls er in der Datenbank vorhanden ist
	 */
	
	public Benutzer getBenutzerByUsername(String benutzerName){
		return dao.getBenutzerByUname(benutzerName);
	}
	/**
	 * 
	 * @param benutzerID ist die ID des Benutzers die gesucht wird
	 * @return den gesuchten Benutzer falls er in der Datenbank vorhanden ist
	 */
	public Benutzer getBenutzerByID(int benutzerID){
		return dao.getBenutzerByID(benutzerID);
	}
	/**
	 * 
	 * @param uname ist der Name des Kundes der gesucht wird
	 * @return den gesuchten Kunde falls er in der Datenbank vorhanden ist
	 */
	
	public Administrator getAdminByUserName(String uname){
		return dao.getAdminByUName(uname);
	}
	/**
	 * 
	 * @param uname ist der Name des Kundes der gesucht wird
	 * @return den gesuchten Kunde falls er in der Datenbank vorhanden ist
	 */
	
	
	
	public Kunde getKundeByUName(String uname){
		return dao.getKundeByUName(uname);
		
	}

	
}