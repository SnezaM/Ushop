/**
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
	private PreparedStatement speichereBenutzerStmt;
	private PreparedStatement speichereKundeStmt;
	private PreparedStatement speichereAdminStmt;
	
	
	private PreparedStatement ladenBenutzerStmtUname;
	private PreparedStatement ladenBenutzerStmtID;
	
	private PreparedStatement loescheKundeStmt;
	private PreparedStatement loescheAdminStmt;
	private PreparedStatement loescheBenutzerStmt;
	
	
	private PreparedStatement ladenAlleBenutzerStmt;
	private PreparedStatement ladenAlleKundenStmt;
	private PreparedStatement ladenAlleAdminStmt;
	
	
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
	         connect = DriverManager.getConnection("jdbc:postgresql://gertsch21.ddns.net:5432/ISME_Ushop", "ise_user", "schikuta");
	         connect.setAutoCommit(true);
	         System.out.println("Database success --> Verbindung ist da! ");
	         stmt = connect.createStatement();
	         
			//einfuegen des Benutzers, Kundes, Admins in die DatenBank
			speichereBenutzerStmt = connect
					.prepareStatement("INSERT INTO BENUTZER(EMAIL,VORNAME,NACHNAME,UNAME,PASSWORT)"
									+ " VALUES(?,?,?,?,?)");
			
			speichereKundeStmt = connect
					.prepareStatement("INSERT INTO KUNDE(STRASSE,PLZ,HAUSNUMMER,BENUTZERID) VALUES (?, ?, ?, ?)");
		
			speichereAdminStmt= connect.
					prepareStatement("INSERT INTO ADMINISTRATOR(gehalt,geburtsdatum, benutzerid) VALUES (?,?,?)");
			
			
			ladenBenutzerStmtID = connect.prepareStatement("SELECT * FROM BENUTZER WHERE BENUTZERID=?");
			ladenBenutzerStmtUname = connect.prepareStatement("SELECT * FROM BENUTZER WHERE UNAME=?");
			
			//statements um die vorhandene Benutzer aus der DatenBank zu loeschen
			loescheBenutzerStmt = connect.prepareStatement("DELETE FROM Benutzer WHERE BENUTZERID=?");
			loescheKundeStmt = connect.prepareStatement("DELETE FROM Kunde WHERE BENUTZERID=?");
			loescheAdminStmt = connect.prepareStatement("DELETE FROM Administrator WHERE BENUTZERID=?");
			
			//Liste verwalten --> Daten aus der DatenBank holen
			ladenAlleBenutzerStmt = connect.prepareStatement("SELECT * FROM Benutzer");
			ladenAlleKundenStmt = connect.prepareStatement("SELECT * FROM Kunde NATURAL JOIN Benutzer");
			ladenAlleAdminStmt = connect.prepareStatement("SELECT * FROM Administrator NATURAL JOIN Benutzer");
			
			
			
			
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
			
			speichereBenutzerStmt.setString(1, b.getEmail());
			speichereBenutzerStmt.setString(2, b.getVorname());
			speichereBenutzerStmt.setString(3, b.getNachname());
			speichereBenutzerStmt.setString(4, b.getUsername());
			speichereBenutzerStmt.setString(5, b.getPasswort());
		
			speichereBenutzerStmt.executeUpdate();
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
			ladenBenutzerStmtUname.setString(1,k.getUsername());
			ResultSet rs = ladenBenutzerStmtUname.executeQuery();
			if (!rs.next()){
				System.out.println("Kein Benutzer gefunden!");
				return false;
			}
			int benutzerid = rs.getInt("benutzerid");
			speichereKundeStmt.setString(1, k.getStrasse());
			speichereKundeStmt.setInt(2, k.getPlz());
			speichereKundeStmt.setInt(3, k.getHausnummer());
			speichereKundeStmt.setInt(4, benutzerid);
			speichereKundeStmt.executeUpdate();
		
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
			
			ladenBenutzerStmtUname.setString(1,a.getUsername());
			ResultSet rs = ladenBenutzerStmtUname.executeQuery();
			if (!rs.next()){
				System.out.println("DatenBankBenutzerDAO:methode:SpeichereAdmin:Kein Benutzer gefunden!");
				return false;
			}
			int benutzerid = rs.getInt("benutzerid");
			speichereAdminStmt.setDouble(1, a.getGehalt() );
			speichereAdminStmt.setDate(2, Date.valueOf(a.getGeburtsdatum()));
			speichereAdminStmt.setInt(3, benutzerid);
			
			speichereAdminStmt.executeUpdate();
		
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
			ResultSet rs = ladenAlleBenutzerStmt.executeQuery();
		
		
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
			ResultSet rs = ladenAlleKundenStmt.executeQuery();
		
		
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
			ResultSet rs = ladenAlleAdminStmt.executeQuery();
		
		
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
			
			loescheBenutzerStmt.setInt(1, userId);
			loescheBenutzerStmt.executeUpdate();
			
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
			loescheKundeStmt.setInt(1, userId);
			loescheKundeStmt.executeUpdate();
			
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
			loescheAdminStmt.setInt(1, userId);
			loescheAdminStmt.executeUpdate();
			
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
			
			ladenBenutzerStmtID.setString(1, idToString);
			ResultSet rs = ladenBenutzerStmtID.executeQuery();
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


