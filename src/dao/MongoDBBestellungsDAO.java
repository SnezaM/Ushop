package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modell.Bestellung;
import modell.Lieferart;
import modell.Position;
import modell.Produkt;
import modell.Produktgruppe;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import javafx.geometry.Pos;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

public class MongoDBBestellungsDAO implements BestellungsDAO {

	private MongoClient mongoClient;
	private MongoDatabase db;
	private String collectionName;
	private String collectionNamePos;
	private String dbName;
	private String mongoLocation;
	private Bestellung bestellung;
	private Position position;

	public MongoDBBestellungsDAO() {
		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "ISME_Ushop";
		this.db = mongoClient.getDatabase(dbName);
		this.collectionName = "Bestellung";
	}

	public MongoDBBestellungsDAO(String sLocation, String dbName, String collectionName, String collectionName2) {

		this.mongoClient = new MongoClient(sLocation);
		this.dbName = dbName;
		this.collectionName = collectionName;
		this.collectionNamePos = collectionName2;
		this.db = mongoClient.getDatabase(dbName);
	}

	// Sollte ueberarbeitet werden
	@Override
	public boolean createBestellungFromWarenkorb(Bestellung bestellung, String date) {
		int id = bestellung.getBestellungID();
		double gesamtpreis = bestellung.getGesamtpreis();
		String liferart = bestellung.getLieferart().toString();
		String vermerk = bestellung.getVermerk();
		String datum = bestellung.getDatum();

		if (this.getBestellungByID(id) != null) {
			System.out.print("MongoDB: Bestellung mit der ID " + id + " existiert bereits");
			return false;
		}
		try {

			Document neueBestellung = new Document().append("gesamtpreis", gesamtpreis).append("abgeschlossen", true)
					.append("lieferart", liferart).append("vermerk", vermerk).append("datum", datum);

			db.getCollection(collectionName).insertOne(neueBestellung);
			System.out.println("Bestellung wurde erstellt!");
			return true;
		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createBestellungFromWarenkorb: Fehler! ");
			return false;
		}

	}

	/**
	 * Notwendig fuer Datenmigration.
	 * 
	 * @param bestellung
	 * @param kundenid
	 * @return
	 */
	public boolean createBestellung(Bestellung bestellung, int kundenid) {
		int id = bestellung.getBestellungID();
		double gesamtpreis = bestellung.getGesamtpreis();
		boolean abgeschlossen = true;
		String lieferart = bestellung.getLieferart().toString();
		String vermerkt = bestellung.getVermerk();
		String datum = bestellung.getDatum();

		if (this.getBestellungByID(id) != null) {
			System.out.print("MongoDB: Bestellung mit der ID " + id + " existiert bereits");
			return false;
		}
		try {

			Document neueBestellung = new Document().append("_id", id).append("gesamtpreis", gesamtpreis)
					.append("abgeschlossen", abgeschlossen).append("lieferant", lieferart).append("vermerk", vermerkt)
					.append("datum", datum).append("kundenid", kundenid);

			db.getCollection(collectionName).insertOne(neueBestellung);
			System.out.println("Bestellung wurde erstellt!");
			return true;
		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createBestellungFromWarenkorb: Fehler! ");
			return false;
		}

	}

	@Override
	public boolean createPosition(int bestellungsID, Position position) {
		int pid = position.getPostionID();
		int menge = position.getMenge();
		double preis = position.getGesamtpreis();
		int artikel = position.getArtikel();

		if (this.getPositionByID(bestellungsID, pid) != null) {
			System.out.print("MongoDB: Position mit ID " + pid + " in der Bestellung mit ID " + bestellungsID
					+ " existiert bereits.");
			return false;
		}
		try {
			db.getCollection(collectionName).updateOne(new Document("_id", bestellungsID),
					new Document("$push", new Document("Positionen", new Document().append("positionID", pid)
							.append("menge", menge).append("preis", preis).append("produktID", artikel))));

			System.out.println("Position wurde erstellt!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createPosition: Fehler! ");
			return false;
		}

	}

	@Override
	public boolean createWarenkorb(int kundenID) {
		int kid = kundenID;

		try {
			Document createWarenkorb = new Document().append("gesamtpreis", 0).append("abgeschlossen", false)
					.append("lieferant", null).append("vermerk", null).append("datum", null).append("kundenid", kid);

			db.getCollection(collectionName).insertOne(createWarenkorb);
			System.out.println("Warenkorb wurde erstellt!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createWarenkorb: Fehler! ");
			return false;
		}

	}

	/**
	 * Notwendig fuer Datenmigration. Liest einen bereits vorhanden Warenkorb in
	 * die DB ein.
	 * 
	 * @param kundenID
	 * @param b
	 * @return
	 */
	public boolean createWarenkorb(int kundenID, Bestellung b) {
		int kid = kundenID;
		int id = b.getBestellungID();
		double preis = b.getGesamtpreis();

		try {
			Document createWarenkorb = new Document().append("_id", id).append("gesamtpreis", preis)
					.append("abgeschlossen", false).append("lieferart", null).append("vermerk", null)
					.append("datum", null).append("kundenid", kid);

			db.getCollection(collectionName).insertOne(createWarenkorb);
			System.out.println("Warenkorb wurde erstellt!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createWarenkorb: Fehler! ");
			return false;
		}

	}

	@Override
	public Bestellung getBestellungByID(int bestellungsID) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("_id", bestellungsID));

			Bestellung bestellung = null;

			for (Document d1 : documents) {
				int bestellungsID1 = d1.getInteger("_id");
				double gesamtpreis = d1.getDouble("gesamtpreis");
				boolean abgeschlossen = d1.getBoolean("abgeschlossen");
				String vermerk = d1.getString("vermerkt");
				String lieferartDB1 = d1.getString("lieferart");
				String datum = d1.getString("datum");
				Lieferart lieferart1 = EntryToEnumeration.entryToLieferart(lieferartDB1);
				bestellung = new Bestellung(bestellungsID1, gesamtpreis, abgeschlossen, datum, vermerk, lieferart1);
			}
			return bestellung;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MongoDB:methode:getBestellungByID: Fehler!");
			return null;
		}

	}

	@Override
	public Position getPositionByID(int bestellungsID, int positionID) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("_id", bestellungsID));

			for (Document doc : documents) {
				List<Document> positionen = (List<Document>) doc.get("Positionen");
				if (positionen != null) {
					for (Document x : positionen) {
						if (x.getInteger("positionID").intValue() == positionID) {
							Position pos = new Position(positionID, x.getInteger("produktID"), x.getInteger("menge"),
									x.getDouble("preis"));
							return pos;
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MongoDB:methode:getPositionByID: Fehler!");
			return null;
		}

	}

	@Override
	public Bestellung getWarenkorb(int kundenID) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName)
					.find(new Document("kundenid", kundenID));

			Bestellung bestellung = null;

			for (Document d1 : documents) {
				if (!d1.getBoolean("abgeschlossen")) {
					int bestellungsID1 = d1.getInteger("_id");
					double gesamtpreis = d1.getDouble("gesamtpreis");
					boolean abgeschlossen = d1.getBoolean("abgeschlossen");
					String vermerk = d1.getString("vermerkt");
					String lieferartDB1 = d1.getString("lieferart");
					String datum = d1.getString("datum");
					Lieferart lieferart1 = EntryToEnumeration.entryToLieferart(lieferartDB1);
					bestellung = new Bestellung(bestellungsID1, gesamtpreis, abgeschlossen, datum, vermerk, lieferart1);
					return bestellung;
				}
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MongoDB:methode:getWarenkorb: Fehler!");
			return null;
		}

	}

	@Override
	public List<Bestellung> readBestellungenByKundenID(int kundenID) {
		List<Bestellung> bestellungsListe = new ArrayList<Bestellung>();
		FindIterable<Document> documents = db.getCollection(collectionName).find(new Document("kundenid", kundenID));
		try {
			Bestellung bestellung = null;
			for (Document d1 : documents) {
				System.out.println(bestellungsListe.size());
				System.out.println(d1.getBoolean("abgeschlossen"));
				if (d1.getBoolean("abgeschlossen")) {
					int bestellungsID1 = d1.getInteger("_id");
					double gesamtpreis = d1.getDouble("gesamtpreis");
					boolean abgeschlossen = true;
					String vermerk = d1.getString("vermerkt");
					String lieferartDB1 = d1.getString("liferart");
					String datum = d1.getString("datum");
					Lieferart lieferart1 = EntryToEnumeration.entryToLieferart(lieferartDB1);
					bestellung = new Bestellung(bestellungsID1, gesamtpreis, abgeschlossen, datum, vermerk, lieferart1);
					bestellungsListe.add(bestellung);
					System.out.println(bestellungsListe.size());
				}
			}
			return bestellungsListe;
		} catch (Exception e) {
			System.out.println("MongoDB:readBestellungenByKundenID: Fehler!");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Position> readPositionenByBestellungID(int bestellungsID) {
		List<Position> positionListe = new ArrayList<Position>();
		FindIterable<Document> documents = db.getCollection(collectionName).find(new Document("_id", bestellungsID));

		try {
			for (Document doc : documents) {
				List<Document> positionen = (List<Document>) doc.get("Positionen");
				if (positionen != null) {
					for (Document x : positionen) {
						Position pos = new Position(x.getInteger("positionID"), x.getInteger("produktID"),
								x.getInteger("menge"), x.getDouble("preis"));
						positionListe.add(pos);
					}
				}
			}
			return positionListe;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean removeBestellung(int bestellungsID) {
		DeleteResult dr = db.getCollection(collectionName).deleteOne(new Document("_id", bestellungsID));
		if (dr.getDeletedCount() > 0) {
			return true;
		}
		return false;

	}

	@Override
	public boolean removePosition(int bestellungsID, int positionID) {
		try {
			MongoCollection<Document> collection = db.getCollection(collectionName);

			BasicDBObject sq = new BasicDBObject("_id", bestellungsID);
			BasicDBObject idoc = new BasicDBObject("positionID", positionID);
			BasicDBObject odoc = new BasicDBObject("Positionen", idoc);
			BasicDBObject delq = new BasicDBObject("$pull", odoc);
			collection.updateOne(sq, delq);
			return true;
		} catch (Exception e) {
			System.out.println("MongoProduktDAO: loescheProduktByProdID: Error");
			return false;
		}
	}

	// Sollte ueberarbeitet werden
	@Override
	public boolean updatePosition(int bestellungsID, int positionsID, int menge, double preis) {
		try {
			MongoCollection<Document> collection = db.getCollection(collectionName);

			BasicDBObject abfq = new BasicDBObject().append("_id", bestellungsID).append("positionID", positionsID);
			BasicDBObject sq = new BasicDBObject("$and", abfq);
			BasicDBObject idoc = new BasicDBObject().append("menge", menge).append("preis", preis);
			BasicDBObject indoc = new BasicDBObject("Positionen", idoc);
			BasicDBObject updateq = new BasicDBObject("$set", indoc);
			collection.updateOne(sq, updateq);
			System.out.println("Preis wurde upgedated!");
			return true;
		} catch (Exception e) {
			System.out.println("MongoDB:Methode:updatePriceBestellung: Fehler! ");
			return false;
		}
	}

	// Sollte ueberarbeitet werden
	@Override
	public boolean updatePriceBestellung(int bestellungsID, double wert) {
		try {
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("gesamtpreis", wert));
			BasicDBObject searchQuery = new BasicDBObject().append("_id", bestellungsID);
			db.getCollection(collectionName).updateOne(searchQuery, newDocument);

			System.out.println("Preis wurde upgedated!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:updatePriceBestellung: Fehler! ");
			return false;
		}
	}
}