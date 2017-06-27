/**
 * Das Paket dient zum persistenten speichern und lesen in der 
 * Datenbank um spaeter wieder darauf zugreifen zu koennen.
 * 
 */
package dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.MongoClient;
import modell.Administrator;
import modell.Benutzer;
import modell.Kunde;

/**
 * 
 *  @author Snezana Milutinovic a1349326
 *  Die Klasse MonogDBBenutzerDAO, dient zur Kommunikation mit der Datenbank in Bezug 
 *  auf Benutzer. Hier werden die Befehle zum Speichern und Laden der Daten in noSQL 
 *  angegeben.
 */

public class MongoDBBenutzerDAO implements BenutzerDAO {
	
	
	private MongoClient mongoClient;
	private MongoDatabase db;
	private String collectionName;
	private String dbName;
	private String mongoLocation;
	  
	 /* Im Konstruktor wird eine Verbindung zur Datenbank erzeugt und die NoSQL Statements
	    * definiert damit in den weiteren Methoden leichter darauf zugegriffen werden kann.
	    * 
	    */
	public MongoDBBenutzerDAO() {
		
		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "ISME_Ushop";
		this.db =  mongoClient.getDatabase(dbName);
		this.collectionName = "Benutzer";
		
		
		
	}
	
	public MongoDBBenutzerDAO(String sLocation, String dbName, String collectionName){
		
		this.mongoClient = new MongoClient(sLocation);
		this.dbName = dbName;
		this.collectionName = collectionName;
		this.db = mongoClient.getDatabase(dbName);
	}
	
	/**
	 * 
	 * Diese Methode dient zum speichern von Kunde in die NoSQL.
	 * Der Parameter ist ein Kunde, welcher angelegt werden soll.
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */

	@Override
	public boolean speichereKunde(Kunde k) {
		
		int id = k.getBenutzerid();
		if(id==0){
			Random randomGenerator = new Random();
			id = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		
		int idKunde = k.getKundenID();
		if(idKunde==0){
			Random randomGenerator = new Random();
			idKunde = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		
		String username = k.getUsername();
		String passwort = k.getPasswort();
		String vorname = k.getVorname();
		String nachname = k.getNachname();
		String email = k.getEmail();
		
		String strasse = k.getStrasse();
		int plz = k.getPlz();
		int hausnummer = k.getHausnummer();
		
		if(this.getBenutzerByUname(username)!=null){
			System.out.print("MongoDB: Kunde mit dem Username existiert schon");
			return false;
		}
		
		try{
			
			Document adresse = new Document().append("plz",plz)
										     .append("strasse", strasse)
											 .append("hausnummer", hausnummer);
			Document neuerKunde = new Document().append("_id", id)
												.append("kundeID", idKunde)
												.append("uname", username)
												.append("passwort",passwort)
												.append("vorname", vorname)
												.append("nachname", nachname)
												.append("email", email)
												.append("adresse", adresse);
			db.getCollection(collectionName).insertOne(neuerKunde);
			System.out.println("Kunde wurde bereits gespeichert!");
			return true;
		}catch(Exception e){
			System.out.println("MongoDB:Methode:speichereKunde: Fehler! " );
			return false;
		}
		
	}
	/**
	 * 
	 * Diese Methode dient zum speichern von Administrator in die NoSQL.
	 * Der Parameter ist ein Administrator, welcher angelegt werden soll.
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	

	@Override
	public boolean speichereAdmin(Administrator a) {
		
		int id = a.getBenutzerid();
		if(id==0){
			Random randomGenerator = new Random();
			id = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		int idAdmin = a.getAdminID(); 
		if(idAdmin==0){
			Random randomGenerator = new Random();
			idAdmin = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		String username = a.getUsername();
		String passwort = a.getPasswort();
		String vorname = a.getVorname();
		String nachname = a.getNachname();
		String email = a.getEmail();
		
		double gehalt = a.getGehalt();
		String geburtsdatum = a.getGeburtsdatum();

		if(this.getBenutzerByUname(username)!=null){
			System.out.print("MongoDB: Administrator mit dem Username existiert schon");
			return false;
		}
		
		try{
			
			Document adminDaten = new Document().append("gehalt",gehalt)
												.append("geburtsdatum", geburtsdatum);
												
			Document neuerAdmin = new Document().append("_id", id)
												.append("adminID", idAdmin)
												.append("uname", username)
												.append("passwort",passwort)
												.append("vorname", vorname)
												.append("nachname", nachname)
												.append("email", email)
												.append("adminDaten", adminDaten);
			db.getCollection(collectionName).insertOne(neuerAdmin);
			System.out.println("Administrator wurde bereits gespeichert!");
			return true;
		}catch(Exception e){
			System.out.println("MongoDB:Methode:speichereAdmin: Fehler! " );
			return false;
		}
		
		
	}
	/**
	 * 
	 * Diese Methode dient zum Laden aller Benutzer aus der NoSQL.
	 * Mittels get werden die Daten dann verwendent um Benutzer zu erzeugen und diese in einer Liste zu speichern
	 * Return eine Liste mit aller in der Datenbank gespeicherten Benutzer.
	 * 
	 */

	@Override
	public List<Benutzer> getBenutzerListe() {
		
		List<Benutzer> benutzerListe = new ArrayList<Benutzer>();
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document d : documents){
				if(d.get("adminDaten") != null){
					Administrator admin = new Administrator(	
							d.getString("email"),
							d.getString("vorname"),
							d.getString("nachname"),
							d.getString("uname"),
							d.getString("passwort"),	
							((Document)d.get("adminDaten")).getDouble("gehalt"),
							((Document)d.get("adminDaten")).getString("geburtsdatum"));
					admin.setAdminID(d.getInteger("adminID"));
					admin.setBenutzerid(d.getInteger("_id"));
					benutzerListe.add(admin);
				}else if(d.get("adresse") != null){
					Kunde k = new Kunde(
							d.getString("email"),
							d.getString("vorname"),
							d.getString("nachname"),
							d.getString("uname"),
							d.getString("passwort"),
							((Document)d.get("adresse")).getString("strasse"),
							((Document)d.get("adresse")).getInteger("plz").intValue(),
							((Document)d.get("adresse")).getInteger("hausnummer").intValue());
					k.setKundenID(d.getInteger("kundeID"));
					k.setBenutzerid(d.getInteger("_id"));
					benutzerListe.add(k);
							
			}
		}
			return benutzerListe;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	/**
	 * 
	 * Diese Methode dient zum Laden aller Kunden aus der NoSQL.
	 * Die Variable documents bekommt das Ergebnis der Abfrage
	 * Die Daten werden dann verwendent um Kunde zu erzeugen und diese in einer Liste zu speichern
	 * Return eine Liste mit aller in der Datenbank gespeicherten Kunden.
	 * 
	 */

	@Override
	public List<Kunde> getKundeListe() {
		List<Kunde> kundenListe = new ArrayList<Kunde>();
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
	
		try {
			for (Document d : documents){
				if(d.get("adresse")!=null){	
					
					Kunde k = new Kunde(
							d.getString("email"),
							d.getString("vorname"),
							d.getString("nachname"),
							d.getString("uname"),
							d.getString("passwort"),
							((Document)d.get("adresse")).getString("strasse"),
							((Document)d.get("adresse")).getInteger("plz").intValue(),
							((Document)d.get("adresse")).getInteger("hausnummer").intValue());
					
					k.setBenutzerid(d.getInteger("_id"));
					k.setKundenID(d.getInteger("kundeID"));
					kundenListe.add(k);
					
				}
			}
			
			return kundenListe;
		}catch(Exception e){
			System.out.println("MongoDB:methode:getKundeListe: Fehler!");
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
	/**
	 * 
	 * Diese Methode dient zum Laden aller Administratoren aus der NoSQL.
	 * Die Variable documents bekommt das Ergebnis der Abfrage
	 * Die Daten werden dann verwendent um Administratoren zu erzeugen und diese in einer Liste zu speichern
	 * Return eine Liste mit aller in der Datenbank gespeicherten Administratoren.
	 * 
	 */

	@Override
	public List<Administrator> getAdministratorListe() {
		List<Administrator> adminListe = new ArrayList<Administrator>();
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document d : documents){
				if(d.get("adminDaten") != null){
					Administrator admin = new Administrator(	
							d.getString("email"),
							d.getString("vorname"),
							d.getString("nachname"),
							d.getString("uname"),
							d.getString("passwort"),	
							((Document)d.get("adminDaten")).getDouble("gehalt"),
							((Document)d.get("adminDaten")).getString("geburtsdatum"));
					admin.setAdminID(d.getInteger("adminID"));
					admin.setBenutzerid(d.getInteger("_id"));
					adminListe.add(admin);
				}
			}
		}catch(Exception e){
					System.out.println("MongoDB:methode:getAdminListe: Fehler!");
					e.printStackTrace();
					return null;
				}
		return adminListe;
	}

	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Kundes aus der NoSQL.
	 * Der Parameter ist der Name des gesuchten Kunden
	 * Return einen Kunde 
	 * 
	 */

		
	@Override
	public Kunde getKundeByUName(String kundeName) {
		List<Kunde> kundenListe = getKundeListe();
		Kunde kunde = null;
		
		try {
			for(Kunde k : kundenListe){
				
				if(k.getUsername().equals(kundeName)){
					kunde = k;
				}
			}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println( "MongoDB:Methode:getKundeByUName!" );
				return null;
			}
			
		
		return kunde;
	}
	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Benutzer aus der NoSQL.
	 * Der Parameter ist der Benutzer ID  des gesuchten Benutzer
	 * Return einen Benutzer 
	 * 
	 */

	

	@Override
	public Benutzer getBenutzerByID(int benutzerID) {
		List<Benutzer> benutzerListe = getBenutzerListe();
		Benutzer benutzer = null;
		
		try{
			for (Benutzer b : benutzerListe){
				if(b.getBenutzerid() == benutzerID){
					benutzer = b;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println( "MongoDB:Methode:getBenutzerByID!" );
			return null;
		}
		
		
		
		
		return benutzer;
	}
	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Benutzers aus der NoSQL.
	 * Der Parameter ist der Name des gesuchten Benutzers
	 * Return einen Benutzer 
	 * 
	 */
	

	@Override
	public Benutzer getBenutzerByUname(String benutzername) {
		
		
			try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("uname", benutzername));
			
			
			Benutzer benutzer = null; 
			
			for(Document d : documents){
				if(d.get("adminDaten") != null){
					benutzer = new Administrator(	
							d.getString("email"),
							d.getString("vorname"),
							d.getString("nachname"),
							d.getString("uname"),
							d.getString("passwort"),	
							((Document)d.get("adminDaten")).getDouble("gehalt"),
							((Document)d.get("adminDaten")).getString("geburtsdatum"));
				}else if(d.get("adresse") != null){
					benutzer =  new Kunde(
							d.getString("email"),
							d.getString("vorname"),
							d.getString("nachname"),
							d.getString("uname"),
							d.getString("passwort"),
							((Document)d.get("adresse")).getString("strasse"),
							((Document)d.get("adresse")).getInteger("plz").intValue(),
							((Document)d.get("adresse")).getInteger("hausnummer").intValue());
				}
			}
				return benutzer;	
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("MongoDB:methode:getBenutzerByID: Fehler!");
				return null;
			}
		
	}
	/**
	 * 
	 * Diese Methode dient zum Laden eines bestimmten Administrators aus der NoSQL.
	 * Der Parameter ist der Name des gesuchten Administrators
	 * Return einen Administrator 
	 * 
	 */

	@Override
	public Administrator getAdminByUName(String username) {
		List<Administrator> adminListe = getAdministratorListe();
		Administrator admin = null;
		
		try{
			for(Administrator a : adminListe){
				if(a.getUsername().equals(username)){
					if(a instanceof Administrator){
						admin = a;
					}
				}
					
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("MongoDB:methode:getAdminByUName: Fehler!");
			return null;
		}
		return admin;
	}
	
	/**
	 * 
	 * Diese Methode dient zum Loeschen von Administratoren aus der NoSQL.
	 * Der Parameter ist die ID des Benutzers, welcher geloescht werden soll.
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 */

	@Override
	public boolean loescheAdminByID(int benutzerid) {
		DeleteResult dr = db.getCollection(collectionName).deleteOne(new Document("_id",benutzerid));
		if(dr.getDeletedCount()>0){
			return true;
		}
		return false;
	}
	

	/**
	 * 
	 * Diese Methode dient zum Loeschen von Kunden aus der NoSQL.
	 * Der Parameter ist die ID des Benutzer, welcher geloescht werden soll.
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 */

	@Override
	public boolean loescheKundeByID(int benutzerid) {
		DeleteResult dr = db.getCollection(collectionName).deleteOne(new Document("_id",benutzerid));
		if(dr.getDeletedCount()>0){
			return true;
		}
		return false;
	}
	
	


}
