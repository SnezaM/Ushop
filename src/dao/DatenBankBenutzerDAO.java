/**
 * Das Paket dient zum persistenten speichern und lesen in der 
 * Datenbank um spaeter wieder darauf zugreifen zu koennen.
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modell.Administrator;
import modell.Benutzer;
import modell.Kunde;



/**
 * 
 * @author Snezana Milutinovic a1349326
 * Die Klasse DatenBankBenutzerDAO, dient zur Kommunikation mit der Datenbank in Bezug 
 *  auf Benutzer. Hier werden die Befehle zum Speichern und Laden der Daten in SQL 
 *  angegeben.
 */
public class DatenBankBenutzerDAO implements BenutzerDAO {
	
	//Variablen die zum Vordefinieren der SQL Statements benoetigt werden
	private PreparedStatement saveUserStmt;
	private PreparedStatement saveKundeStmt;
	private PreparedStatement saveAdminStmt;
	
	
	private PreparedStatement loadUserStmtUname;
	private PreparedStatement loadUserStmtID;
	
	private PreparedStatement deleteKundeStmt;
	private PreparedStatement deleteAdminStmt;
	private PreparedStatement deleteUserStmt;
	
	
	private PreparedStatement loadAllUserStmt;
	private PreparedStatement loadAllKundeStmt;
	private PreparedStatement loadAllAdminStmt;
	
	
	//Variable zum Verbindungsaufbau zur Datenbank
	Connection connect = null;
    Statement stmt = null;
	
   /* Im Konstruktor wird eine Verbindung zur Datenbank erzeugt und die SQL Statements
    * definiert damit in den weiteren Methoden leichter darauf zugegriffen werden kann.
    * Mittels setAutoCommit(true) werden Uebertragungen automatisch an das DBS gesendet
    */
	public DatenBankBenutzerDAO() {
		try {
			
			//Connection with the postgresql
			Class.forName("org.postgresql.Driver");
	         connect = DriverManager.getConnection("jdbc:postgresql://gertsch22.ddns.net:5432/ISME_Ushop", "postgres", "hallodu");
	         connect.setAutoCommit(true);
	         System.out.println("Database success --> Verbindung ist da! ");
	         stmt = connect.createStatement();
	         
			//einfuegen des Benutzers, Kundes, Admins in die DatenBank
			saveUserStmt = connect
					.prepareStatement("INSERT INTO BENUTZER(EMAIL,VORNAME,NACHNAME,UNAME,PASSWORT)"
									+ " VALUES(?,?,?,?,?)");
			
			saveKundeStmt = connect
					.prepareStatement("INSERT INTO KUNDE(STRASSE,PLZ,HAUSNUMMER,BENUTZERID) VALUES (?, ?, ?, ?)");
		
			saveAdminStmt= connect.
					prepareStatement("INSERT INTO ADMINISTRATOR(GEHALT,geburtsdatum, benutzerid) VALUES (?,?,?)");
			
			
			loadUserStmtID = connect.prepareStatement("SELECT * FROM BENUTZER WHERE BENUTZERID=?");
			loadUserStmtUname = connect.prepareStatement("SELECT * FROM BENUTZER WHERE UNAME=?");
			
			//statements um die vorhandene Benutzer aus der DatenBank zu loeschen
			deleteUserStmt = connect.prepareStatement("DELETE FROM Benutzer WHERE BENUTZERID=?");
			deleteKundeStmt = connect.prepareStatement("DELETE FROM Kunde WHERE BENUTZERID=?");
			deleteAdminStmt = connect.prepareStatement("DELETE FROM Administrator WHERE BENUTZERID=?");
			
			//Liste verwalten --> Daten aus der DatenBank holen
			loadAllUserStmt = connect.prepareStatement("SELECT * FROM Benutzer");
			loadAllKundeStmt = connect.prepareStatement("SELECT * FROM Kunde NATURAL JOIN Benutzer");
			loadAllAdminStmt = connect.prepareStatement("SELECT * FROM Administrator NATURAL JOIN Benutzer");
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verbindungsaufbau zur DB nicht möglich!! ("+e.getMessage()+")");
		}

	}
	
	/**
	 * 
	 * Diese Methode dient zum speichern von Benutzer in die Datenbank.
	 * Der Parameter ist ein Benutzer, welcher angelegt werden soll.
	 * Mittels set werden die Parameter des Benutzers dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * Dies ist eine interne Methode und wird nicht mittels Interface angeboten.
	 * 
	 */
	
	private boolean speichereBenutzer(Benutzer b) {
		try {
			
			saveUserStmt.setString(1, b.getEmail());
			saveUserStmt.setString(2, b.getVorname());
			saveUserStmt.setString(3, b.getNachname());
			saveUserStmt.setString(4, b.getUsername());
			saveUserStmt.setString(5, b.getPasswort());
		
			saveUserStmt.executeUpdate();
			return true;
		}catch(NullPointerException e){
			System.out.println("DatenBankBenutzer:methode:SpeichereBenutzer:NullPointerException!!! ("+e.getMessage()+")");
			return false;
		}catch (Exception e) {
			System.out.println("DatenBankBenutzer:methode:SpeichereBenutzer: Benutzer existiert bereits!("+e.getMessage()+")!");
			return false;
		}
	}
	
	/**
	 * 
	 * Diese Methode dient zum speichern von Kunden in die Datenbank.
	 * Der Parameter ist ein Kunde, welcher angelegt werden soll.
	 * Mittels set werden die Parameter des Kundes dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	
	
	@Override
	public boolean speichereKunde(Kunde k) {
		
		
		if(!speichereBenutzer(k))
			return false;
		
		try {
			loadUserStmtUname.setString(1,k.getUsername());
			ResultSet rs = loadUserStmtUname.executeQuery();
			if (!rs.next()){
				System.out.println("Kein Benutzer gefunden!");
				return false;
			}
			int benutzerid = rs.getInt("benutzerid");
			saveKundeStmt.setString(1, k.getStrasse());
			saveKundeStmt.setInt(2, k.getPlz());
			saveKundeStmt.setInt(3, k.getHausnummer());
			saveKundeStmt.setInt(4, benutzerid);
			saveKundeStmt.executeUpdate();
		
			return true;
			
		}catch(NullPointerException e){
			System.out.println("DatenBankBenutzerDAO:methode:SpeichereKunde:NullPointerException");
			return false;
		}catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:SpeichereKunde: Kunde existiert bereits!("+e.getMessage()+")!!!");
			return false;
		}

	}
	
	/**
	 * 
	 * Diese Methode dient zum speichern von Administrator in die Datenbank.
	 * Der Parameter ist ein Administrator, welcher angelegt werden soll.
	 * Mittels set werden die Parameter des Administrators dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	
	@Override
	public boolean speichereAdmin(Administrator a) {
		if(!speichereBenutzer(a))
			return false;
		
		try{
			
			loadUserStmtUname.setString(1,a.getUsername());
			ResultSet rs = loadUserStmtUname.executeQuery();
			if (!rs.next()){
				System.out.println("DatenBankBenutzerDAO:methode:SpeichereAdmin:Kein Benutzer gefunden!");
				return false;
			}
			int benutzerid = rs.getInt("benutzerid");
			saveAdminStmt.setDouble(1, a.getGehalt() );
			saveAdminStmt.setDate(2, Date.valueOf(a.getGeburtsdatum()));
			saveAdminStmt.setInt(3, benutzerid);
			
			saveAdminStmt.executeUpdate();
		
			return true;
			
		}catch(NullPointerException e){
			System.out.println("DatenBankBenutzerDAO:methode:SpeichereAdmin:NullPointerException!");
			return false;
		}catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:SpeichereAdmin: Admin existiert bereits("+e.getMessage()+")!!!");
			return false;
		}
	}
	
	/**
	 * 
	 * Diese Methode dient zum Laden aller Benutzer aus der Datenbank.
	 * Die Variable rs bekommt das Ergebnis der Abfrage
	 * Mittels get werden die Daten dann verwendent um Benutzer zu erzeugen und diese in einer Liste zu speichern
	 * Return eine Liste mit aller in der Datenbank gespeicherten Benutzer.
	 * 
	 */

	@Override
	public List<Benutzer> getBenutzerListe() {
		List<Benutzer> liste = new ArrayList<Benutzer>();
		try {
			ResultSet rs = loadAllUserStmt.executeQuery();
		
		
			while(rs.next()){
				String email = rs.getString("EMAIL");
				String vorname = rs.getString("VORNAME");
				String nachname = rs.getString("NACHNAME");
				String username = rs.getString("UNAME");
				String passwort = rs.getString("PASSWORT");
				
				
				try {
					
					Benutzer b = new Benutzer(email,vorname,nachname,username,passwort);
					b.setBenutzerid(rs.getInt("benutzerID"));
					liste.add(b);			
					
				} catch (NumberFormatException e) {
					System.out.println("DatenBankBenutzerDao:methode:getBenutzerListe:NumberFormatException");
					return null;
				}catch(Exception e){
					System.out.println("DatenBankBenutzerDao:methode:getBenutzerListe "+e.getMessage());
					return null;
				}	
			}
			
			

			return liste;
			
		} catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:getBenutzerListe: Fehler");
			return null;
		}
	}
	
	/**
	 * 
	 * Diese Methode dient zum Laden aller Kunden aus der Datenbank.
	 * Die Variable rs bekommt das Ergebnis der Abfrage
	 * Mittels get werden die Daten dann verwendent um Kunde zu erzeugen und diese in einer Liste zu speichern
	 * Return eine Liste mit aller in der Datenbank gespeicherten Kunden.
	 * 
	 */

	@Override
	public List<Kunde> getKundeListe() {
		List<Kunde> liste = new ArrayList<Kunde>();
		try {
			ResultSet rs = loadAllKundeStmt.executeQuery();
		
		
			while(rs.next()){
				
				Kunde k = new Kunde(rs.getString("email"), rs.getString("vorname") , rs.getString("nachname") ,  rs.getString("uname") , 
						rs.getString("passwort") , rs.getString("strasse") , rs.getInt("plz"),rs.getInt("hausnummer"));
				k.setBenutzerid(rs.getInt("benutzerid"));
				k.setKundenID(rs.getInt("kundenid"));
				liste.add(k);
				
			}
					
				} catch (NumberFormatException e) {
					System.out.println("DatenBankBenutzerDao:methode:getBenutzerListe:NumberFormatException ");
					return null;
				}catch(Exception e){
					System.out.println("DatenBankBenutzerDao:methode:getBenutzerListe "+e.getMessage());
					return null;
				}	

			return liste;
		
		
	}
	
	/**
	 * 
	 * Diese Methode dient zum Laden aller Administratoren aus der Datenbank.
	 * Die Variable rs bekommt das Ergebnis der Abfrage
	 * Mittels get werden die Daten dann verwendent um Administratoren zu erzeugen und diese in einer Liste zu speichern
	 * Return eine Liste mit aller in der Datenbank gespeicherten Administratoren.
	 * 
	 */
	
	
	@Override
	public List<Administrator> getAdministratorListe() {
		List<Administrator> liste = new ArrayList<Administrator>();
		try {
			ResultSet rs = loadAllAdminStmt.executeQuery();
		
		
			while(rs.next()){
				
				Administrator admin = new Administrator(rs.getString("email"), rs.getString("vorname") , rs.getString("nachname") ,  rs.getString("uname") , 
						rs.getString("passwort") , rs.getDouble("gehalt") , rs.getString("geburtsdatum"));
				admin.setBenutzerid(rs.getInt("benutzerid"));
				admin.setAdminID(rs.getInt("adminid"));
				liste.add(admin);
				
			}
				}catch(Exception e){
					System.out.println("DatenBankBenutzerDao:methode:getBenutzerListe "+e.getMessage());
					return null;
				}	

			return liste;
	}
	
	
	
	/**
	 * 
	 * Diese Methode dient zum Loeschen von Benutzer aus der Datenbank.
	 * Der Parameter ist die ID des Benutzeres, welcher geloescht werden soll.
	 * Mittels set wird der Parameter des Benutzeres dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * Dies ist eine interne Methode und wird nicht mittels Interface angeboten.
	 */
	
	//Interne methode
	public boolean loescheBenutzer(int userId) {
		if(userId < 1)
			return false; 
		
		try{
			
			deleteUserStmt.setInt(1, userId);
			deleteUserStmt.executeUpdate();
			
		}catch(SQLException e){
			System.out.println("DatenBankBenutzerDAO: LoescheBenutzer: "+e.getMessage());
			return false;
		}
		return true;		
	}
	
	/**
	 * 
	 * Diese Methode dient zum Loeschen von Kunden aus der Datenbank.
	 * Der Parameter ist die ID des Kunden, welcher geloescht werden soll.
	 * Mittels set wird der Parameter des Kunden dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 */
	
	
	@Override
	public boolean loescheKundeByID(int userId) {
		
		if( userId < 1 )
			return false; 
		
		try{	
			deleteKundeStmt.setInt(1, userId);
			deleteKundeStmt.executeUpdate();
			
		}catch(SQLException e){
			System.out.println("DatenBankBenutzerDAO:methode: loescheKunde" +e.getMessage());
			return false;
		}
		
		if(!loescheBenutzer(userId))
			return false;
		
		return true;
		
	}
	
	/**
	 * 
	 * Diese Methode dient zum Loeschen von Administratoren aus der Datenbank.
	 * Der Parameter ist die ID des Administrators, welcher geloescht werden soll.
	 * Mittels set wird der Parameter des Administrators dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 */
	
	@Override
	public boolean loescheAdminByID(int userId) {
		if( userId < 1 )
			return false; 
		
		try{
			deleteAdminStmt.setInt(1, userId);
			deleteAdminStmt.executeUpdate();
			
		}catch(SQLException e){
			System.out.println("DatenBankBenutzerDAO:methode: loescheAdmin" +e.getMessage());
			return false;
		}
		
		if(!loescheBenutzer(userId))
			return false;
		
		return true;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	
	
/**
 * 
 * Diese Methode dient zum Laden eines bestimmten Administrators aus der Datenbank.
 * Der Parameter ist der Name des gesuchten Administrators
 * Return einen Administrator 
 * 
 */


	@Override
	public Administrator getAdminByUName(String name) {
		List<Administrator> adminliste = this.getAdministratorListe();
		Administrator admin = null;
		try{
			
			for(Administrator a : adminliste){
				if(a.getUsername().equals(name)){
					admin = a;
				}
			}
			
			
		}catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:getAdminByUName: Fehler");
			return null;
		}
		
		return admin;
	}
	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Kundes aus der Datenbank.
	 * Der Parameter ist der Name des gesuchten Kunden
	 * Return einen Kunde 
	 * 
	 */

	
	@Override
	public Kunde getKundeByUName(String kundeName) {
		
		List<Kunde> kundenliste = this.getKundeListe();
		Kunde gesuchterkunde =null;
		System.out.println("ICh bin nun in der Mehtode");
		
		try{
			for (Kunde k : kundenliste){
				if(k.getUsername().equals(kundeName)){
					gesuchterkunde = k;
				}
			}
		}catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:getKundeByUName: Fehler!");
			return null;
		
		}
		return gesuchterkunde;
		
	}
	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Benutzer aus der Datenbank.
	 * Der Parameter ist der Benutzer ID  des gesuchten Benutzer
	 * Return einen Benutzer 
	 * 
	 */

	@Override
	public Benutzer getBenutzerByID(int benutzerID) {
		try{
			String idToString = Integer.toString(benutzerID);
			
			loadUserStmtID.setString(1, idToString);
			ResultSet rs = loadUserStmtID.executeQuery();
			if(!rs.next()){
				System.out.println("Kein Benutzer mit der angegebenen ID gefunden!");
				return null;
			}
			
			String email = rs.getString("EMAIL");
			String vorname = rs.getString("VORNAME");
			String nachname = rs.getString("NACHNAME");
			String username = rs.getString("UNAME");
			String passwort = rs.getString("PASSWORT");
			
			Benutzer ben = new Benutzer(email, vorname, nachname, username, passwort);
			ben.setBenutzerid(benutzerID);
			
			return ben;
			
		}catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:getBenutzerById: Fehler");
			return null;
		}
	}
	
	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Benutzers aus der Datenbank.
	 * Der Parameter ist der Name des gesuchten Benutzers
	 * Return einen Benutzer 
	 * 
	 */
	
	@Override
	public Benutzer getBenutzerByUname(String benutzername){
		List<Benutzer> benutzerliste = this.getBenutzerListe();
		Benutzer ben = null;
		try{
			 
			
			for( Benutzer b : benutzerliste){
				if(b.getUsername().equals(benutzername)){
					ben = b;
				}
			}
		}catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:getBenutzerByBenutzerName: Fehler");
			return null;
		}
		return ben;
		
	}
	

	}



