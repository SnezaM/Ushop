/**
 * Das Package Modell dient fuer alle Akteure, Produkte und Produktgrupen die f�r den Ushop benoetigt werden.
 */
package modell;

/**
 * 
 * @author Snezana Milutinovic a1349326
 * Die Klasse Kunde dient zur Verwaltung des Kundendaten
 */

public class Kunde extends Benutzer {
	
	
	
	  @Override
	  public String toString() {
	  	return "Kunde [benutzerid=" + getBenutzerid() + ", vorname=" + getVorname() + ", nachname=" + getNachname() + ", email=" + getEmail()
	  			+ ", username=" + getUsername() + ", passwort=" + getPasswort() + ", kundenID="+ kundenID +", strasse=" + strasse + ", plz=" + plz + ", hausnummer=" + hausnummer + "]";
	  }

	private String strasse;
	private int plz;
	private int hausnummer;
	private int kundenID;

	
	/**
	 * 
	 * @param vorname Der Vorname des des jeweiligen Kundes.
	 * @param nachname Der Nachname des jeweiligen Kundes.
	 * @param email Die email des jeweiligen Kundes.
	 * @param username Der eindeutige Username des jeweiligen Kundes.
	 * @param password Das Passwort des jeweiligen Kundes.
	 * @param strasse Die Strasse des jeweiligen Kundes
	 * @param plz Die PLZ des jeweiligen Kundes
	 * @param hausnummer Die hausnummer des jeweiligen Kundes
	 */
	public Kunde(String email, String vorname, String nachname, String username, 
			String passwort, String strasse, int plz , int hausnummer) {
		
		super(email, vorname, nachname, username, passwort);
		setStrasse(strasse);
		setPlz(plz);
		setHausnummer(hausnummer);
		
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}
	public int getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}
	
	public int getKundenID() {
		return kundenID;
	}

	public void setKundenID(int kundenID) {
		this.kundenID = kundenID;
	}

}
