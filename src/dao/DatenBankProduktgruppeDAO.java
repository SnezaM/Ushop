/**
 * Das Paket dient zum persistenten speichern und lesen in der 
 * Datenbank um spaeter wieder darauf zugreifen zu koennen.
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modell.Produktgruppe;

/**
 * @author Mirza Talic a1367543
 *  Die Klasse DatenBankProduktgruppeDAO, dient zur Kommunikation mit der Datenbank in Bezug 
 *  auf Produktgruppen. Hier werden die Befehle zum Speichern und Laden der Daten in SQL 
 *  angegeben.
 */


public class DatenBankProduktgruppeDAO implements ProduktgruppeDAO{
	

	//Variable zum Verbindungsaufbau zur Datenbank
	      Connection c = null;

	//Variablen die zum Vordefinieren der SQL Statements benoetigt werden
		private PreparedStatement speicherProduktgruppeStmt;
		private PreparedStatement alleProduktgruppenStmt;
		private PreparedStatement ladeProduktgruppeIdStmt;
		private PreparedStatement loescheProduktgruppeStmt;
	
	
	public DatenBankProduktgruppeDAO () {
		try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://gertsch22.ddns.net:5432/ISME_Ushop","postgres", "hallodu");
	         c.setAutoCommit(true);
	         System.out.println("Datenbankverbindung Erfolgreich");
			
			speicherProduktgruppeStmt = c.prepareStatement("INSERT INTO PRODUKTGRUPPE (PRODUKTGRUPPENNAME,BEZEICHNUNG,ADMINID) VALUES(?,?,?)");
			
			alleProduktgruppenStmt = c.prepareStatement("SELECT * FROM PRODUKTGRUPPE");
			
			ladeProduktgruppeIdStmt = c.prepareStatement("SELECT * FROM PRODUKTGRUPPE WHERE PRODUKTGRUPPEID=?");
			
			loescheProduktgruppeStmt = c.prepareStatement("DELETE FROM PRODUKTGRUPPE WHERE PRODUKTGRUPPEID=?");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verbindungsaufbau zur Datenbank nicht möglich auf Grund des Fehlers: ("+e.getMessage()+")");
		}
	}
	
	
	/**
	 * 
	 * Diese Methode dient zum speichern von Produktgruppen in die Datenbank.
	 * Der Parameter ist eine Produktgruppe, welche angelegt werden soll.
	 * Mittels set werden die Parameter der Produktgruppe dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	@Override
	public boolean speichereProduktgruppe(Produktgruppe p) {
		try {

				speicherProduktgruppeStmt.setString(1, p.getProduktgruppenname());
				speicherProduktgruppeStmt.setString(2, p.getBezeichnung());
				speicherProduktgruppeStmt.setInt(3, p.getAdminID());
				
				speicherProduktgruppeStmt.executeUpdate();
				
				return true;
		}catch(NullPointerException e){
			System.out.println("DatenBankProduktgruppeDAO:speichereProduktgruppe: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return false;
		}catch (Exception e) {
			System.out.println("DatenBankProduktgruppeDAO:speichereProduktgruppe: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return false;
		}
	}
	
	
	/**
	 * 
	 * Diese Methode dient zum Loeschen von Produktgruppen aus der Datenbank.
	 * Der Parameter ist die ID der Produktgruppe, welche geloescht werden soll.
	 * Mittels set wird der Parameter der Produktgruppe dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	@Override
	public boolean loescheProduktgruppetByID(int produktgruppeID) {
		if(produktgruppeID < 1)
			return false; //Keine Produktgruppe gefunden
		
		try{
			loescheProduktgruppeStmt.setInt(1, produktgruppeID);
			loescheProduktgruppeStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DatenBankProduktgruppeDAO:loescheProduktgruppe: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * Diese Methode dient zum Laden aller Produktgruppen aus der Datenbank.
	 * Die Variable result bekommt das Ergebnis der Abfrage
	 * Mittels get werden die Daten dann verwendent um Produktgruppen zu erzeugen und diese in ein Liste zu speichern
	 * Return eine Liste mit alles Produktgruppen.
	 * 
	 */
	@Override
	public List<Produktgruppe> getProduktgruppeList() {
		
		List<Produktgruppe> produktegruppeList = new ArrayList<Produktgruppe>();
		try {
					ResultSet result = alleProduktgruppenStmt.executeQuery();
					while(result.next()){
					
						Produktgruppe p = new Produktgruppe(result.getString("Produktgruppenname"),result.getString("Bezeichnung"),result.getInt("AdminID"));
						p.setProduktgruppeID(result.getInt("ProduktgruppeID"));
						produktegruppeList.add(p);
					}
			}catch(Exception e){
					System.out.println("DatenBankProduktgruppeDAO:getProduktgruppeList: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
					return null;
			}	
			return produktegruppeList;
	}
	
	
	/**
	 * 
	 * Diese Methode dient zum Laden einer bestimmten Produktgruppe aus der Datenbank.
	 * Der Parameter ist eine ID der Produktgruppe, welche gesucht werden soll.
	 * Mittels set wird der Parameter der Produktgruppe dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	@Override
	public Produktgruppe getProduktgruppeByID(int produktgruppeID) {
		try {
			ladeProduktgruppeIdStmt.setInt(1,produktgruppeID);
			ResultSet result = ladeProduktgruppeIdStmt.executeQuery();
		
			if (!result.next()){ // Kein Produkt gefunden.
				return null;
			}
			Produktgruppe p = new Produktgruppe(result.getString("Produktgruppenname"),result.getString("Bezeichnung"),result.getInt("AdminID"));
			p.setProduktgruppeID(result.getInt("ProduktgruppeID"));
			return p;
		}catch(Exception e){
			System.out.println("DatenBankProduktDAO:getProduktByProduktID: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return null;
		}	
	}

}
