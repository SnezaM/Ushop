package dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.BsonDocument;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import modell.Produkt;
import modell.Produktgruppe;




public class MongoDBProdukteDAO implements ProduktDAO, ProduktgruppeDAO {

	private String dbName;
	private String collectionName;
	private MongoClient mongoClient;
	private MongoDatabase db;
	private String mongoLocation;

	public MongoDBProdukteDAO(){

		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "ISME_Ushop";
		this.db = mongoClient.getDatabase(dbName);
		System.out.println("Connect to database successfully");
		this.collectionName = "Produkte";
	}




	@Override
	public boolean speichereProdukt(Produkt p) {

		int prodId = p.getProduktID(); 
		if(prodId==0){
			Random randomGenerator = new Random();
			prodId = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		System.out.println(prodId);

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

	@Override
	public boolean loescheProduktByID(int produktID) {

		try {
			System.out.println("Hallo Hallo bin hier");
			Produkt prod = this.getProduktByProduktID(produktID);
			int cat_id = prod.getProduktgruppeID();
			System.out.println("Hallo Hallo bin hier222222");
			System.out.println(cat_id + "Das ist die Produktgruppeid");
			System.out.println(produktID + "Das ist die Produktid");
			MongoCollection<Document> collection = db.getCollection(collectionName);

			System.out.println("Hallo ich bin jetzt da 3333333333333");

			BasicDBObject sq = new BasicDBObject("_id", cat_id);
			System.out.println("Passt 1");
			BasicDBObject idoc=new BasicDBObject("ProduktID",produktID);
			System.out.println("Passt 2");
			BasicDBObject odoc =new BasicDBObject("Produkte",idoc);
			System.out.println("Passt 3");
			BasicDBObject delq=new BasicDBObject("$pull",odoc);
			System.out.println("Passt 4");
			collection.updateOne(sq, delq);
			System.out.println("Passt 5");

			return true;

		} catch (Exception e) {
			System.out.println("MongoProduktDAO: loescheProduktByProdID: Error");
			return false;
		}

	}

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
			System.out.println("MongoProduktDAO: getProduktList - Error");
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Produkt getProduktByProduktID(int produktID) {

		FindIterable<Document> documents = db.getCollection(collectionName).find();

		try{
			for(Document x : documents) {
				int kategorieID = x.getInteger("_id").intValue();

				@SuppressWarnings("unchecked")
				List<Document> produkte = (List<Document>)x.get("Produkte");
				if(produkte != null) {
					for(Document prod : produkte) {
						if (prod.getInteger("ProduktID").intValue() == produktID){
							Produkt produkt = new Produkt(
									prod.getString("Produktname"),
									prod.getDouble("Preis"),
									prod.getString("Beschreibung"),
									prod.getInteger("AdminID").intValue(),
									kategorieID);
							produkt.setProduktID(prod.getInteger("ProduktID").intValue());		

							return produkt;
						}
					}
				}
			}
			return null;
		}catch (Exception e) {
			System.out.println("MongoProduktDAO: getProduktByProduktID: Error");
			e.printStackTrace();
			return null;
		}
	}



	//----------------------------- Ab hierProduktgruppe ----------------------------------------





	@Override
	public boolean speichereProduktgruppe(Produktgruppe p) {
		int kategorieId = p.getProduktgruppeID(); //Das wird benoetigt fuer die Migration
		if(kategorieId==0){
			Random randomGenerator = new Random();
			kategorieId = randomGenerator.nextInt(Integer.MAX_VALUE);
		}
		System.out.println(kategorieId);
		String kategorieName = p.getProduktgruppenname();
		String kategorieBez = p.getBezeichnung();
		int adminID = p.getAdminID(); 
		try {
			Document kategorie = new Document() 
					.append("_id", kategorieId)
					.append("Kategoriename", kategorieName)
					.append("Beschreibung", kategorieBez)
					.append("AdminID", adminID);

			db.getCollection(collectionName).insertOne(kategorie);
			System.out.println("Kategorie ("+kategorieName+") wurde gespeichert");
			return true;

		} catch (Exception e) {
			System.err.println("MongoProduktDAO: speichereProduktgruppe: Fehler beim Einfügen einer neuen Kategorie("+p.getProduktgruppenname()+")!");
			return false;
		}
	}

	@Override
	public boolean loescheProduktgruppetByID(int produktgruppeID) {

		if(produktgruppeID <= 0){
			return false;
		}

		MongoCollection<Document> collection = db.getCollection(collectionName);
		Document query = new Document("_id", produktgruppeID);
		collection.deleteOne(query);
		return true;

	}

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
			System.out.println("MongoProduktDAO: getProduktgruppeList - Error");
			e.printStackTrace();
			return null;
		}
	}

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
			System.out.println("MongoProduktDAO: getCategoryByCategoryID: Error");
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
