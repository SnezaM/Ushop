package dao;

import java.util.ArrayList;
import java.util.List;

import modell.Bestellung;
import modell.Lieferart;
import modell.Position;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import javafx.geometry.Pos;

import com.mongodb.MongoClient;

public class MongoDBBestellungsDAO implements BestellungsDAO {

	private MongoClient mongoClient;
	private MongoDatabase db;
	private String collectionName;
	private String dbName;
	private String mongoLocation;
	private Bestellung bestellung;
	private Position position;

	public MongoDBBestellungsDAO() {
		this.mongoLocation = "localhost";
		this.mongoClient = new MongoClient(mongoLocation);
		this.dbName = "mydb";
		this.db = mongoClient.getDatabase(dbName);
		this.collectionName = "Bestellung";
	}

	public MongoDBBestellungsDAO(String sLocation, String dbName, String collectionName) {

		this.mongoClient = new MongoClient(sLocation);
		this.dbName = dbName;
		this.collectionName = collectionName;
		this.db = mongoClient.getDatabase(dbName);
	}

	@Override
	public boolean createBestellungFromWarenkorb(Bestellung bestellung, String date) {
		int id = bestellung.getBestellungID();
		double gesamtpreis = bestellung.getGesamtpreis();
		boolean abgeschlossen = false;
		String liferart = bestellung.getLieferart().toString();
		String vermerkt = bestellung.getVermerk();
		String datum = bestellung.getDatum();

		if (this.getBestellungByID(id) != null) {
			System.out.print("MongoDB: Bestellung mit der ID " + id + " existiert bereits");
			return false;
		}
		try {

			Document neueBestellung = new Document().append("_id", id).append("gesamtpreis", gesamtpreis)
					.append("abgeschlossen", abgeschlossen).append("lieferant", liferart).append("vermerk", vermerkt)
					.append("datum", datum);

			db.getCollection(collectionName).insertOne(neueBestellung);
			System.out.println("Bestellung wurde bereits erstellt!");
			return true;
		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createBestellungFromWarenkorb: Fehler! ");
			return false;
		}

	}

	@Override
	public boolean createPosition(int bestellungsID, Position position) {
		int id = bestellungsID;
		int pid = position.getPostionID();
		int menge = position.getMenge();
		double preis = position.getGesamtpreis();
		int artikel = position.getArtikel();

		if (this.getPositionByID(bestellungsID, pid) != null) {
			System.out.print("MongoDB: Position mit ID "+ pid + " in der Bestellung mit ID " + bestellungsID + " existiert bereits.");
			return false;
		}
		try {
			Document createPosition = new Document().append("_id", id).append("positionId", pid).append("menge", menge)
					.append("preis", preis).append("artikel", artikel);

			db.getCollection(collectionName).insertOne(createPosition);
			System.out.println("Position wurde bereits erstellt!");
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
			Document createWarenkorb = new Document().append("positionId", kid);

			db.getCollection(collectionName).insertOne(createWarenkorb);
			System.out.println("Warenkorb wurde bereits erstellt!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createWarenkorb: Fehler! ");
			return false;
		}

	}

	@Override
	public Bestellung getBestellungByID(int bestellungsID) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName).find(new Document("id", bestellungsID));

			Bestellung bestellung = null;

			for (Document d1 : documents) {
				if (d1.get("adminDaten") != null) {
					int bestellungsID1 = d1.getInteger("id");
					double gesamtpreis = d1.getDouble("gesamtpreis");
					boolean abgeschlossen = d1.getBoolean("abgeschlossen");
					String vermerk = d1.getString("vermerkt");
					String lieferartDB1 = d1.getString("liferart");
					String datum = d1.getString("datum");
					Lieferart lieferart1 = EntryToEnumeration.entryToLieferart(lieferartDB1);
					bestellung = new Bestellung(bestellungsID1, gesamtpreis, abgeschlossen, datum, vermerk, lieferart1);

				}
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
			FindIterable<Document> documents = db.getCollection(collectionName).find(new Document("id", bestellungsID));
			FindIterable<Document> documents1 = db.getCollection(collectionName).find(new Document("pid", positionID));

			Position position = null;
			Bestellung bestellung = null;

			for (Document d1 : documents) {
				if (d1.get("adminDaten") != null) {
					int bestellungsID1 = d1.getInteger("id");
					double gesamtpreis = d1.getDouble("gesamtpreis");
					boolean abgeschlossen = d1.getBoolean("abgeschlossen");
					String vermerk = d1.getString("vermerkt");
					String lieferartDB1 = d1.getString("liferart");
					String datum = d1.getString("datum");
					Lieferart lieferart1 = EntryToEnumeration.entryToLieferart(lieferartDB1);
					bestellung = new Bestellung(bestellungsID1, gesamtpreis, abgeschlossen, datum, vermerk, lieferart1);

				}
			}
			for (Document d : documents1) {
				if (d.get("adminDaten") != null) {
					position = new Position(d.getInteger("positionID"), d.getInteger("produktID"),
							d.getInteger("menge"), d.getDouble("gesamtpreis"));
				}
			}
			return position;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MongoDB:methode:getPositionByID: Fehler!");
			return null;
		}

	}

	@Override
	public Bestellung getWarenkorb(int kundenID) {
		try {
			FindIterable<Document> documents = db.getCollection(collectionName).find(new Document("id", kundenID));

			Bestellung bestellung = null;

			for (Document d1 : documents) {
				if (d1.get("adminDaten") != null) {
					int bestellungsID1 = d1.getInteger("id");
					double gesamtpreis = d1.getDouble("gesamtpreis");
					boolean abgeschlossen = d1.getBoolean("abgeschlossen");
					String vermerk = d1.getString("vermerkt");
					String lieferartDB1 = d1.getString("liferart");
					String datum = d1.getString("datum");
					Lieferart lieferart1 = EntryToEnumeration.entryToLieferart(lieferartDB1);
					bestellung = new Bestellung(bestellungsID1, gesamtpreis, abgeschlossen, datum, vermerk, lieferart1);

				}
			}
			return bestellung;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MongoDB:methode:getWarenkorb: Fehler!");
			return null;
		}

	}

	@Override
	public List<Bestellung> readBestellungenByKundenID(int kundenID) {

		List<Bestellung> bestellungsListe = new ArrayList<Bestellung>();
		FindIterable<Document> documents = db.getCollection(collectionName).find();
		int kid = kundenID;
		try {
			Bestellung bestellung = null;

			for (Document d1 : documents) {
				if (d1.get("adminDaten") != null) {
					int bestellungsID1 = d1.getInteger("id");
					double gesamtpreis = d1.getDouble("gesamtpreis");
					boolean abgeschlossen = d1.getBoolean("abgeschlossen");
					String vermerk = d1.getString("vermerkt");
					String lieferartDB1 = d1.getString("liferart");
					String datum = d1.getString("datum");
					Lieferart lieferart1 = EntryToEnumeration.entryToLieferart(lieferartDB1);
					bestellung = new Bestellung(bestellungsID1, gesamtpreis, abgeschlossen, datum, vermerk, lieferart1);

				}
			}
		} catch (Exception e) {
			System.out.println("MongoDB:readBestellungenByKundenID: Fehler!");
			e.printStackTrace();
			return null;
		}

		return bestellungsListe;
	}

	@Override
	public List<Position> readPositionenByBestellungID(int bestellungsID) {
		List<Position> positionListe = new ArrayList<Position>();
		FindIterable<Document> documents = db.getCollection(collectionName).find();

		try {
			for (Document d : documents) {
				if (d.get("neuePosition") != null) {
					positionListe.add(new Position(d.getInteger("positionID"), d.getInteger("produktID"),
							d.getInteger("menge"), d.getDouble("gesamtpreis")));
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
		bestellung = null;
		bestellungsID = bestellung.getBestellungID();

		try {

			Document neueBestellung = new Document().append("_id", bestellungsID);

			db.getCollection(collectionName).deleteOne(neueBestellung);
			System.out.println("Bestellung wurde bereits gelöscht!");
			return true;
		} catch (Exception e) {
			System.out.println("MongoDB:Methode:removeBestellung: Fehler! ");
			return false;
		}

	}

	@Override
	public boolean removePosition(int bestellungsID, int positionID) {
		bestellung = null;
		position = null;
		bestellungsID = bestellung.getBestellungID();
		positionID = position.getPostionID();
		try {
			Document createPosition = new Document().append("_id", bestellungsID).append("positionId", positionID);

			db.getCollection(collectionName).deleteOne(createPosition);
			System.out.println("Position wurde bereits erstellt!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:createPosition: Fehler! ");
			return false;
		}

	}

	@Override
	public boolean updatePosition(int bestellungsID, int positionsID, int menge, double preis) {
		bestellungsID = bestellung.getBestellungID();
		positionsID = position.getPostionID();
		preis = position.getGesamtpreis();

		try {
			Document createPosition = new Document().append("_id", bestellungsID).append("positionId", positionsID)
					.append("preis", preis);

			db.getCollection(collectionName).updateOne(createPosition, null);
			System.out.println("Position ist bereits upgedated!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:updatePriceBestellung: Fehler! ");
			return false;
		}

	}

	@Override
	public boolean updatePriceBestellung(int bestellungsID, double wert) {
		bestellungsID = bestellung.getBestellungID();
		wert = position.getGesamtpreis();

		try {
			Document createPosition = new Document().append("_id", bestellungsID).append("preis", wert);

			db.getCollection(collectionName).updateOne(createPosition, null);
			System.out.println("Preis ist bereits upgedated!");
			return true;

		} catch (Exception e) {
			System.out.println("MongoDB:Methode:updatePriceBestellung: Fehler! ");
			return false;
		}

	}
}
