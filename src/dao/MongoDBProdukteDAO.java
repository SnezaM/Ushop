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
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modell.Produkt;
import modell.Produktgruppe;

/**
 * @author Mirza Talic a1367543
 *  Die Klasse MongoDBProdukteDAO, dient zur Kommunikation mit der Datenbank in Bezug 
 *  auf Artikel. Hier werden die Befehle zum Speichern und Laden der Daten in NoSQL 
 *  angegeben.
 */



public class MongoDBProdukteDAO implements ProduktDAO, ProduktgruppeDAO {

	//Name der Datenbank
	private String dbName;
	//Name der Collection
	private String collectionName;
	private MongoClient mongoClient;
	private MongoDatabase db;
	// Name des Servers
	private String mongoLocation;
	
	/**
	 * Im Defaultkonstruktor wird eine Verbindung zur Datenbank erzeugt und die NoSQL Statements
	 * definiert damit in den weiteren Methoden leichter darauf zugegriffen werden kann.
	 * 
	 * 
	 */
	public MongoDBProdukteDAO(){
		
		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "ISME_Ushop";
		this.db = mongoClient.getDatabase(dbName);
		System.out.println("Connect to database successfully");
		this.collectionName = "Produkte";
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
			
			int prodId = p.getProduktID(); 
			if(prodId==0){
				Random randomGenerator = new Random();
				prodId = randomGenerator.nextInt(Integer.MAX_VALUE);
			}
			
			String prodName = p.getProduktname();
			double prodPreis = p.getPreis();
			String beschreibung = p.getBeschreibung();
			int KategorieId = p.getProduktgruppeID();
			int adminId= p.getAdminID();
			
			try {
				db.getCollection(collectionName)
					.updateOne(new Document("_id", KategorieId),
						new Document("$push", 
							new Document("Produkte", 
								new Document("ProduktID", prodId)
								.append("Produktname", prodName)
								.append("Preis", prodPreis)
								.append("Beschreibung", beschreibung)
								.append("AdminID", adminId)
							)
						)
					);
				System.out.println("Produkt ("+prodId+") wurde gespeichert");
				return true;
				
			} catch (Exception e) {
				System.err.println("MongoProduktDAO: speichereProdukt: Fehler beim Einfügen des Produktes("+p.getProduktID()+")!");
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
		
		try {
		
		  Produkt produkt = this.getProduktByProduktID(produktID);
		  int kategorieID = produkt.getProduktgruppeID();

		  MongoCollection<Document> collection = db.getCollection(collectionName);
	
	      BasicDBObject sq = new BasicDBObject("_id", kategorieID);
	      BasicDBObject idoc=new BasicDBObject("ProduktID",produktID);
	      BasicDBObject odoc =new BasicDBObject("Produkte",idoc);
	      BasicDBObject delq=new BasicDBObject("$pull",odoc);
	      collection.updateOne(sq, delq);
		     
	
			return true;
			
		} catch (Exception e) {
			System.out.println("MongoProduktDAO: loescheProduktByID: Fehler beim löschen des Produktes!");
			return false;
		}
		
	}

	
	/**
	 * 
	 * Diese Methode dient zum Laden aller Produkte aus der Datenbank.
	 * Mittels get werden die Daten dann verwendent um Produkte zu erzeugen und diese in ein Liste zu speichern
	 * Return eine Liste mit alles Produkten.
	 * 
	 */
	@Override
	public List<Produkt> getProduktList() {
		
		List<Produkt> produktList = new ArrayList<Produkt>();
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document x : documents) {
				
				int kategorieID = x.getInteger("_id").intValue();
				
				@SuppressWarnings("unchecked")
				List<Document> produkte = (List<Document>)x.get("Produkte");
				if(produkte != null) {	
					for(Document p : produkte) {
						Produkt produkt = new Produkt(
								p.getString("Produktname"),
								p.getDouble("Preis"),
								p.getString("Beschreibung"),
								p.getInteger("AdminID").intValue(),
								kategorieID);
						produkt.setProduktID(p.getInteger("ProduktID").intValue());
						produktList.add(produkt);
								
					} 
				}
			}
			return produktList;
			
		}catch (Exception e) {
			System.out.println("MongoProduktDAO: getProduktList: Fehler beim holen aller Produkte!");
			e.printStackTrace();
			return null;
		}
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
		
		FindIterable<Document> documents = db.getCollection(collectionName).find();

		try{
			for(Document x : documents) {
				int kategorieID = x.getInteger("_id").intValue();
				
				@SuppressWarnings("unchecked")
				List<Document> produkte = (List<Document>)x.get("Produkte");
				if(produkte != null) {
					for(Document k : produkte) {
						if (k.getInteger("ProduktID").intValue() == produktID){
						Produkt produkt = new Produkt(
								k.getString("Produktname"),
								k.getDouble("Preis"),
								k.getString("Beschreibung"),
								k.getInteger("AdminID").intValue(),
								kategorieID);
						produkt.setProduktID(k.getInteger("ProduktID").intValue());		
						
						return produkt;
					  }
				   }
				}
			 }
			return null;
		}catch (Exception e) {
			System.out.println("MongoProduktDAO: getProduktByProduktID: Fehler beim holen eines Produktes!");
			e.printStackTrace();
			return null;
		}
	}

	
	
	//----------------------------- Ab hierProduktgruppe ----------------------------------------
	
	
	
	/**
	 * 
	 * Diese Methode dient zum speichern von Produktgruppen in die Datenbank.
	 * Der Parameter ist eine Produktgruppe, welche angelegt werden soll.
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	
	@Override
	public boolean speichereProduktgruppe(Produktgruppe p) {

		try {
			
			int kategorieId = p.getProduktgruppeID(); 
			
			if(kategorieId==0){
		        Random randomGenerator = new Random();
				kategorieId = randomGenerator.nextInt(Integer.MAX_VALUE);
			}
			
			
			String kategorieName = p.getProduktgruppenname();
			String kategorieBez = p.getBezeichnung();
			int adminID = p.getAdminID(); 
			
			Document kategorie = new Document() 
				.append("_id", kategorieId)
				.append("Kategoriename", kategorieName)
				.append("Beschreibung", kategorieBez)
				.append("AdminID", adminID);
			
			db.getCollection(collectionName).insertOne(kategorie);
			System.out.println("Kategorie ("+kategorieName+") wurde gespeichert");
			return true;
			
		} catch (Exception e) {
			System.err.println("MongoProduktDAO: speichereProduktgruppe: Fehler beim Einfügen einer neuen Kategorie!");
			return false;
		}
	}

	
	/**
	 * 
	 * Diese Methode dient zum Loeschen von Produktgruppen aus der Datenbank.
	 * Der Parameter ist die ID der Produktgruppe, welche geloescht werden soll.
	 * Return true wenn die Transaktion erfolgreich abgeschlossen ist.
	 * 
	 */
	@Override
	public boolean loescheProduktgruppetByID(int produktgruppeID) {
		try{
			if(produktgruppeID <= 0){
				return false;
			}
			
			MongoCollection<Document> collection = db.getCollection(collectionName);
			Document query = new Document("_id", produktgruppeID);
			collection.deleteOne(query);
			return true;
		
	}catch (Exception e) {
		System.out.println("MongoProduktDAO: loescheProduktgruppetByID: Fehler beim löschen einer Kategorie!");
		e.printStackTrace();
		return false;
	}
		
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
		
		List<Produktgruppe> produktgruppenList = new ArrayList<Produktgruppe>();
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		
		try{
			for(Document x : documents) {
				Produktgruppe produktgr= new Produktgruppe(		
						x.getString("Kategoriename"),
						x.getString("Beschreibung"),
						x.getInteger("AdminID")
						);
				produktgr.setProduktgruppeID(x.getInteger("_id"));
				produktgruppenList.add(produktgr);
			}
			return produktgruppenList;
			
		}catch (Exception e) {
			System.out.println("MongoProduktDAO: getProduktgruppeList: Fehler beim lesen aller Kategorie!");
			e.printStackTrace();
			return null;
		}
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
			FindIterable<Document> documents = db.getCollection(collectionName).find(new Document("_id", produktgruppeID ));
			
			for(Document x : documents){
					Produktgruppe produktg = new Produktgruppe(
										x.getString("Kategoriename"),
										x.getString("Beschreibung"),
										x.getInteger("AdminID")
										);
								produktg.setProduktgruppeID(x.getInteger("_id"));
					return produktg;
			}

			return null;
		
		} catch (Exception e) {
			System.out.println("MongoProduktDAO: getProduktgruppeByID: Fehler beim lesen einer Kategorie!");
			return null;
		}
	}




	@Override
	public Produkt getProduktByName(String pname) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("pname", pname));


			Produkt produkt = null; 


			for(Document p : documents){
				int kategorieID = p.getInteger("_id").intValue();
				produkt = new Produkt(	
						p.getString("Produktname"),
						p.getDouble("Preis"),
						p.getString("Beschreibung"),
						p.getInteger("AdminID").intValue(),
						kategorieID);
			}
			return produkt;	

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("MongoDB:methode:getProduktByName: Fehler!");
			return null;
		}
	}




	@Override
	public List<Produkt> sucheProdukt(String lis) {
		List<Produkt> produktList = new ArrayList<Produkt>();

		FindIterable<Document> documents = db.getCollection(collectionName).find();

		try{
			for(Document x : documents) {
				int kategorieID = x.getInteger("_id").intValue();

				@SuppressWarnings("unchecked")
				List<Document> produkte = (List<Document>)x.get("Produkte");


				if(produkte != null){	
					for(Document p : produkte) {
						String pname=p.getString("Produktname");
						if(lis==pname){
							Produkt produkt = new Produkt(
									p.getString("Produktname"),
									p.getDouble("Preis"),
									p.getString("Beschreibung"),
									p.getInteger("AdminID").intValue(),
									kategorieID);
							produkt.setProduktID(p.getInteger("ProduktID").intValue());
							produktList.add(produkt);

						} 
					}
				}
			}
			return produktList;

		}catch (Exception e) {
			System.out.println("MongoProduktDAO: sucheProdukt - Error");
			e.printStackTrace();
			return null;
		}
	}


}
