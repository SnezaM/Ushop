/**
 * Das Package Modell dient fuer alle Akteure, Produkte und Produktgrupen die für den Ushop benoetigt werden.
 */
package modell;

/**
 * 
 * @author Snezana Milutinovic a1349326
 * Die Klasse Administrator dient zur Verwaltung aller Kunde und Produkte die registriert sind.
 */

public class Administrator extends Benutzer{
	
	
	
	@Override
	public String toString() {
		return "Administrator [benutzerID=" + benutzerID + ", vorname=" + getVorname() + ", nachname=" + getNachname() + ", email=" + getEmail()
	+ ", username=" + getUsername() + ", passwort=" + getPasswort() + ", adminID= "+ adminID+" , gehalt =" +  gehalt + ", geburtsdatum=" + geburtsdatum.toString() + "]";
	}
	
	
	private int adminID;
	private int benutzerID;
	private double gehalt;
	private String geburtsdatum;
	
	
	/**
	 * 
	 * @param email Die email des jeweiligen Administrators.
	 * @param vorname Der Vorname des des jeweiligen Administrators.
	 * @param nachname Der Nachname des jeweiligen Administrators.
	 * @param username Der eindeutige Username des jeweiligen Administrators.(min 2 Zeichen)
	 * @param passwort Das Passwort des jeweiligen Administrators.(min 2 Zeichen) 
	 * @param gehalt Das Gehalt des jeweiligen Administrators.
	 * @param geburtsdatum Das Geburtsdatum des jeweiligen Administrators.
	 */
	

	public Administrator(String email, String vorname, String nachname, String username, String passwort, double gehalt, String geburtsdatum) {
		super(email,vorname,nachname,username,passwort);
		setGehalt(gehalt);
		setGeburtsdatum(geburtsdatum);
	}
	
	

	/**
	 * 
	 * @return Das Geburtsdatum des Administrators
	 */
	public String getGeburtsdatum() {
		return geburtsdatum;
	}
	
	 public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	 /**
	  * 
	  * @return Das Gehalt des Administrators.
	  */
	public double getGehalt() {
		return gehalt;
	}

	/**
	 * 
	 * @param gehalt Das Gehalt des Administrators.
	 */
	public void setGehalt(double gehalt) {
		this.gehalt = gehalt;
	}
	
	public void setBenutzerid(int id){
		this.benutzerID=id;
	}
	public int getBenutzerid(){
		return benutzerID;
	}
	public int getAdminID() {
		return adminID;
	}

	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}


	

	
	

}
