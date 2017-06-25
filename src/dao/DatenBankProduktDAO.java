/**
 * Das Paket dient zum persistenten speichern und lesen in der 
 * Datenbank um spaeter wieder darauf zugreifen zu koennen.
 * 
 */
package dao;


/**
 * @author Mirza Talic a1367543
 *  Die Klasse DatenBankProduktDAO, dient zur Kommunikation mit der Datenbank in Bezug 
 *  auf Artikel. Hier werden die Befehle zum Speichern und Laden der Daten in SQL 
 *  angegeben.
 */

import java.util.ArrayList;
import java.util.List;

import modell.Benutzer;
import modell.Produkt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatenBankProduktDAO implements ProduktDAO{

	//Variable zum Verbindungsaufbau zur Datenbank
	Connection c = null;

	//Variablen die zum Vordefinieren der SQL Statements benoetigt werden
	private PreparedStatement speicherProduktStmt;
	private PreparedStatement alleProdukteStmt;
	private PreparedStatement ladeProduktIdStmt;
	private PreparedStatement loescheProduktStmt;



	/**
	 * Im Konstruktor wird eine Verbindung zur Datenbank erzeugt und die SQL Statements
	 * definiert damit in den weiteren Methoden leichter darauf zugegriffen werden kann.
	 * Mittels setAutoCommit(true) werden Uebertragungen automatisch an das DBS gesendet
	 * 
	 */
	public DatenBankProduktDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://gertsch21.ddns.net:5432/ISME_Ushop", "ise_user", "schikuta");
			c.setAutoCommit(true);
			System.out.println("Datenbankverbindung Erfolgreich");

			speicherProduktStmt = c.prepareStatement("INSERT INTO PRODUKT (PRODUKTNAME,PREIS,BESCHREIBUNG,ADMINID,PRODUKTGRUPPEID) VALUES(?,?,?,?,?)");

			alleProdukteStmt = c.prepareStatement("SELECT * FROM PRODUKT");

			ladeProduktIdStmt = c.prepareStatement("SELECT * FROM PRODUKT WHERE PRODUKTID=?");

			loescheProduktStmt = c.prepareStatement("DELETE FROM PRODUKT WHERE PRODUKTID=?");


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verbindungsaufbau zur Datenbank nicht möglich auf Grund des Fehlers: ("+e.getMessage()+")");
		}

	}


	/**
	 * 
	 * Diese Methode dient zum speichern von Produkten in die Datenbank.
	 * Der Parameter ist ein Produkt, welches angelegt werden soll.
	 * Mittels set werden die Parameter des Produktes dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	@Override
	public boolean speichereProdukt(Produkt p) {
		try {

			speicherProduktStmt.setString(1, p.getProduktname());
			speicherProduktStmt.setDouble(2, p.getPreis());
			speicherProduktStmt.setString(3, p.getBeschreibung());
			speicherProduktStmt.setInt(4, p.getAdminID());
			speicherProduktStmt.setInt(5, p.getProduktgruppeID());
			speicherProduktStmt.executeUpdate();

			return true;
		}catch(NullPointerException e){
			System.out.println("DatenBankProduktDAO:speichereProdukt: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return false;
		}catch (Exception e) {
			System.out.println("DatenBankProduktDAO:speichereProdukt: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return false;
		}
	}




	/**
	 * 
	 * Diese Methode dient zum Loeschen von Produkten aus der Datenbank.
	 * Der Parameter ist die ID des Produktes, welches geloescht werden soll.
	 * Mittels set wird der Parameter des Produktes dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	@Override
	public boolean loescheProduktByID(int produktID) {
		if(produktID < 1)
			return false; // Keine Produkte gefunden

		try{
			loescheProduktStmt.setInt(1, produktID);
			loescheProduktStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("DatenBankProduktDAO:loescheProduktByID: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return false;
		}
		return true;
	}



	/**
	 * 
	 * Diese Methode dient zum Laden aller Produkte aus der Datenbank.
	 * Die Variable result bekommt das Ergebnis der Abfrage
	 * Mittels get werden die Daten dann verwendent um Produkte zu erzeugen und diese in ein Liste zu speichern
	 * Return eine Liste mit alles Produkten.
	 * 
	 */
	@Override
	public List<Produkt> getProduktList() {

		List<Produkt> produkte = new ArrayList<Produkt>();
		try {
			ResultSet result = alleProdukteStmt.executeQuery();
			while(result.next()){

				Produkt p = new Produkt(result.getString("Produktname"),result.getDouble("Preis"),result.getString("Beschreibung"),result.getInt("AdminID"),result.getInt("ProduktgruppeID"));
				p.setProduktID(result.getInt("ProduktID"));
				produkte.add(p);
			}
		}catch(Exception e){
			System.out.println("DatenBankProduktDAO:getProduktList: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return null;
		}	

		return produkte;
	}


	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Produktes aus der Datenbank.
	 * Der Parameter ist eine ID des Produktes, welches gesucht werden soll.
	 * Mittels set wird der Parameter des Produktes dem Statement uebergeben
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	@Override
	public Produkt getProduktByProduktID(int produktID) {
		try {
			ladeProduktIdStmt.setInt(1,produktID);
			ResultSet result = ladeProduktIdStmt.executeQuery();

			if (!result.next()){ // Kein Produkt gefunden.
				return null;
			}
			Produkt p = new Produkt(result.getString("Produktname"),result.getDouble("Preis"),result.getString("Beschreibung"),result.getInt("AdminID"),result.getInt("ProduktgruppeID"));
			p.setProduktID(result.getInt("ProduktID"));
			return p;
		}catch(Exception e){
			System.out.println("DatenBankProduktDAO:getProduktByProduktID: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return null;
		}	
	}


	@Override
	public Produkt getProduktByName(String pname) {
		List<Produkt> produktliste = this.getProduktList();
		Produkt pro = null;
		try{


			for( Produkt p : produktliste){
				if(p.getProduktname().equals(pname)){
					pro = p;
				}
			}
		}catch (Exception e) {
			System.out.println("DatenBankBenutzerDAO:methode:getProduktByName: Fehler");
			return null;
		}
		return pro;

	}




	@Override
	public List<Produkt> sucheProdukt(String lis) {
		List<Produkt> produkte = new ArrayList<Produkt>();
		try {
			ResultSet result = alleProdukteStmt.executeQuery();

			while(result.next()){
				String pname = result.getString("Produktname");
				if(pname==lis){
					Produkt p = new Produkt(result.getString("Produktname"),result.getDouble("Preis"),result.getString("Beschreibung"),result.getInt("AdminID"),result.getInt("ProduktgruppeID"));
					p.setProduktID(result.getInt("ProduktID"));
					produkte.add(p);
				}
			}
		}catch(Exception e){
			System.out.println("DatenBankProduktDAO:sucheProdukt: Funktioniert nicht auf Grund des Fehlers:("+e.getMessage()+")");
			return null;
		}	

		return produkte;
	}
}