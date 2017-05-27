/**
 * Das Paket dient zum persistenten speichern und lesen in der 
 * Datenbank um spaeter wieder darauf zugreifen zu koennen.
 * 
 */
package dao;

import java.util.List;
import modell.Administrator;
import modell.Benutzer;
import modell.Kunde;
/**
 * 
 * @author Snezana Milutinovic a1349326
 *Dieses Interface definiert Methoden des zugriffs auf die Datenbank in Bezug auf Benutzer. 
 */


public interface BenutzerDAO  {
	
	public boolean speichereKunde(Kunde k);
	
	public boolean speichereAdmin(Administrator a);
	
	public List<Benutzer> getBenutzerListe();
	
	public List<Kunde> getKundeListe();
	
	public List<Administrator> getAdministratorListe();
	
	public Kunde getKundeByUName(String kundeName );
	
	public Benutzer getBenutzerByID(int benutzerID);
	
	public Benutzer getBenutzerByUname(String benutzername);
	
	public Administrator getAdminByUName(String username);
	
	public boolean loescheAdminByID (int benutzerid);
	
	public boolean loescheKundeByID (int benutzerid);

	

	

}
