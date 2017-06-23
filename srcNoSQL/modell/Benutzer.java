/**
 * Das Package Modell dient fuer alle Akteure, Produkte und Produktgrupen die für den Ushop benoetigt werden.
 */
package modell;


/**
 * 
 * @author Snezana Milutinovic a1349326
 * Die Klasse Benutzer dient als Vorlage fuer Kunde und Administrator
 *
 */



public class Benutzer{
	
	
	
	
	    @Override
	public String toString() {
		return "Benutzer [id=" + benutzerid + ", vorname=" + vorname + ", nachname=" + nachname + ", email=" + email
				+ ", username=" + username + ", passwort=" + passwort + "]";
	}
	    
	    
	    private int benutzerid;
		private String vorname;
		private String nachname;
		private String passwort;
		private String email;
		private String username;
		
	    /**
	     
	 * 
	 * @param benutzerid Die id des jeweiligen Benutzers.
	 * @param vorname Der Vorname des des jeweiligen Benutzers.
	 * @param nachname Der Nachname des jeweiligen Benutzers.
	 * @param email Die email des jeweiligen Benutzers.
	 * @param username Der eindeutige Username des jeweiligen Benutzers.
	 * @param password Das Passwort des jeweiligen Benutzers.
	     */
		
	   
		
		
		public Benutzer(String email, String vorname, String nachname, String username, String passwort){
				
				setEmail(email);
				setVorname(vorname);
				setNachname(nachname);
				setUsername(username);
				setPasswort(passwort);
			
		}
		/**
		 * 
		 * @return Der Vorname des jeweiligen Benutzers
		 */
		
		public String getVorname() {
			return vorname;
		}
		/**
		 * 
		 * @param vorname Der neue Vorname des jeweiligen Benutzers
		 */
		public void setVorname(String vorname) {
			this.vorname = vorname;
		}
		/**
		 * 
		 * @return Der Nachname des jeweiligen Benutzers
		 */
		public String getNachname() {
			return nachname;
		}
		/**
		 * 
		 * @param nachname Der neue Nachname des jeweiligen Benutzers
		 */
		public void setNachname(String nachname) {
			this.nachname = nachname;
		}
		/**
		 * 
		 * @return Die EmailAdresse des  Benuzuers
		 */
		public String getEmail() {
			return email;
		}
		/**
		 * 
		 * @param email die neue EmailAdresse des Benutzers
		 */
		public void setEmail(String email) {
			this.email = email;
		}
		/**
		 * 
		 * @return username des Benutzers
		 */
		public String getUsername() {
			return username;
		}
		/**
		 * 
		 * @param username Der neue username des jeweiligen Benutzers
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		/**
		 * 
		 * @return Das Passwort des jeweiligen Benutzers
		 */
		public String getPasswort() {
			return passwort;
		}
		/**
		 * 
		 * @param passwort Das neue Passwort des jeweiligen Benutzers
		 */
		public void setPasswort(String passwort) {
			this.passwort = passwort;
		}
		
		/**
		 * 
		 * @return Die id des jeweiligen Benutzers.
		 */ 
		public int getBenutzerid() {
			return benutzerid;
		}
		/**
		 * 
		 * @param benutzerid Die neue id des jeweiligen Benutzers
		 */
		public void setBenutzerid(int benutzerid) {
			this.benutzerid = benutzerid;
		}
		
}
