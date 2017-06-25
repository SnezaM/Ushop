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



public class MongoDBBenutzerDAO implements BenutzerDAO {
	
	
	private MongoClient mongoClient;
	private MongoDatabase db;
	private String collectionName;
	private String dbName;
	private String mongoLocation;
	  

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

	@Override
	public boolean speichereKunde(Kunde k) {
		
		int id = k.getBenutzerid();
		if(id==0){
			Random randomGenerator = new Random();
			id = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		System.out.println(id);
		int idKunde = k.getKundenID();
		if(idKunde==0){
			Random randomGenerator = new Random();
			idKunde = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		System.out.println(id);
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

	@Override
	public boolean loescheAdminByID(int benutzerid) {
		DeleteResult dr = db.getCollection(collectionName).deleteOne(new Document("_id",benutzerid));
		if(dr.getDeletedCount()>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean loescheKundeByID(int benutzerid) {
		DeleteResult dr = db.getCollection(collectionName).deleteOne(new Document("_id",benutzerid));
		if(dr.getDeletedCount()>0){
			return true;
		}
		return false;
	}
	
	


}
