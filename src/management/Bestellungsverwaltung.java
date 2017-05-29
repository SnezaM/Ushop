package management;

import java.util.List;

import dao.BestellungsDAO;
import dao.DBBestellungsDAO;
import modell.Bestellung;
import modell.Position;

/**
 * <b>Klasse Bestellungsverwaltung</b>
 * <p>
 * Verwaltet alle Methoden, die mit der Bestellung in Verbindung stehen.<br>
 * Verwendet f�r den Datenbankzugriff das {@link BestellungsDAO}.
 * </p>
 * 
 * @see Bestellung, BestellungsDAO
 * @author Katrin (1309572)
 *
 */
public class Bestellungsverwaltung {

	private static Bestellungsverwaltung BestellungsverwaltungInstance = null;
	private BestellungsDAO dao;

	/**
	 * Ruft den Konstruktor zur Bestellungsverwaltung auf.
	 * 
	 * @return BestellungsverwaltungInstance (Bestellverwaltung), sollte noch
	 *         keine Instanz vorhanden sein wird eine neue erzeugt.
	 */
	public static Bestellungsverwaltung getInstance() {
		if (BestellungsverwaltungInstance == null)
			BestellungsverwaltungInstance = new Bestellungsverwaltung();
		return BestellungsverwaltungInstance;
	}

	private Bestellungsverwaltung() {
		dao = new DBBestellungsDAO();
	}

	/**
	 * Fuegt die uebergebene Bestellung/Warenkorb in die Datenbank hinzu. Dabei
	 * werden die noetigen Instanzen gesetzt um den Warenkorb als Bestellung zu
	 * sehen. Sollte das hinzufuegen scheitern wird false retourniert sonst
	 * true.
	 * 
	 * @param bestellung
	 *            Bestellung, die hinzugefuegt werden soll
	 * @param date
	 *            Datum, das als Bestellungsdatum eingetragen wird (Format:
	 *            "YYYY-MM-DD")
	 * @return true falls die Position erfolgreich hinzugefuegt wurde, sonst
	 *         false.
	 */
	public boolean addBestellung(Bestellung bestellung, String date) {
		return dao.updateBestellung(bestellung, date);
	}

	/**
	 * Fuegt die uebergebene Position der Bestellung mit der ensprechenden ID
	 * hinzu. Sollte keine Bestellung mit der ID vorhanden sein oder das
	 * hinzufuegen scheitern wird false retourniert sonst true. Auch der
	 * Gesamtpreis der entsprechenden Bestellung wird angepasst.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung, der die Position hinzugefuegt werden soll.
	 * @param position
	 *            Position, die hinzugefuegt werden soll.
	 * @return true falls die Position erfolgreich hinzugefuegt wurde, sonst
	 *         false.
	 */
	public boolean addPositionToBestellung(int bestellungsID, Position position) {
		Bestellung bestellung = dao.getBestellungByID(bestellungsID);
		if (bestellung == null) {
			return false;
		}
		if (dao.createPosition(bestellungsID, position)) {
			Double neuerPreis = bestellung.getGesamtpreis() + position.getGesamtpreis();
			if (dao.updatePriceBestellung(bestellungsID, neuerPreis)) {
				return true;
			}
			dao.deletePosition(bestellungsID, position.getPostionID());
		}
		return false;
	}

	/**
	 * Fuegt den uebergebene Warenkorb mit der entsprechenden KundenID in die
	 * Datenbank hinzu. Sollte das hinzufuegen scheitern wird false retourniert
	 * sonst true.
	 * 
	 * @param bestellung
	 *            Warenkorb der hinzugefuegt werden soll.
	 * @param kundenID
	 *            ID des Kunden, dessen Warenkorb hinzugefuegt werden soll.
	 * @return true falls der Warenkorb erfolgreich hinzugefuegt wurde, sonst
	 *         false.
	 */
	public boolean addWarenkorb(Bestellung bestellung, int kundenID) {
		return dao.createBestellung(bestellung, kundenID);
	}

	/**
	 * Aendert die Menge einer Position auf den uebergebenen Wert fuer Menge und
	 * passt auch automatisch den Preis an. Sollte die Position nicht vorhanden
	 * sein wird null retourniert.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung in der die Position enthalten sein soll.
	 * @param positionID
	 *            ID der Position, die geaendert werden soll.
	 * @param menge
	 *            Menge, auf die geaendert werden soll.
	 * @return true falls die Aenderung erfolgreich durchgefuehrt wurde, sonst
	 *         false.
	 */
	public boolean aenderePosition(int bestellungsID, int positionID, int menge) {
		Position position = dao.getPositionByID(bestellungsID, positionID);
		if (position == null)
			return false;
		double preis = position.getGesamtpreis() / position.getMenge() * menge;
		return dao.updatePosition(bestellungsID, positionID, menge, preis);
	}

	/**
	 * Retourniert alle Bestellungen eines Kunden. Sollten keine Bestellungen
	 * vorhanden sein wird null retourniert.
	 * 
	 * @param kundenID
	 *            ID des Kunden dessen Bestellungen retourniert werden sollen.
	 * @return Liste mit allen Bestellungen des Kunden oder NULL.
	 */
	public List<Bestellung> getAllBestellungenByKunde(int kundenID) {
		return dao.readBestellungenByKundenID(kundenID);
	}

	/**
	 * Retourniert alle Positonen einer Bestellung. Diese wird anhand der
	 * uebergebenen BestellungsID identifiziert.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung deren Positionen gesucht werden sollen.
	 * @return Liste mit den Positionen einer Bestellung.
	 */
	public List<Position> getAllPositionenByBestellungsID(int bestellungsID) {
		return dao.readPositionenByBestellungID(bestellungsID);
	}

	/**
	 * Retourniert die Bestellung mit der uebergenen ID. Sollte keine Bestellung
	 * mit der ID vorhanden sein wird null retourniert.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung, die retourniert werden soll.
	 * @return Bestellung mit der entsprechenden ID oder NULL.
	 */
	public Bestellung getBestellungByID(int bestellungsID) {
		return dao.getBestellungByID(bestellungsID);
	}

	/**
	 * Retourniert die Position mit der uebergenen ID aus der Bestellung mit der
	 * uebergebenen ID. Sollte keine Bestellung oder Position mit der ID
	 * vorhanden sein wird null retourniert.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung in der die Position enthalten sein soll.
	 * @param positionID
	 *            ID der Position, die reotourniert werden soll.
	 * @return Position der entsprechenden Bestellung mit entsprechender ID oder
	 *         NULL.
	 */
	public Position getPositionByID(int bestellungsID, int positionID) {
		return dao.getPositionByID(bestellungsID, positionID);
	}

	/**
	 * Retourniert den Warenkorb des Kunden, dessen ID mit der uebergebenen ID
	 * uebereinstimmt. Sollte kein Warenkorb vorhanden sein, wird ein neuer
	 * angelegt.
	 * 
	 * @param kundenID
	 *            ID des Kunden, dessen Warenkorb gesucht werden soll.
	 * @return Warenkorb des Kunden.
	 * @throws Exception 
	 */
	public Bestellung getWarenkorb(int kundenID) throws Exception {
		Bestellung warenkorb = dao.getWarenkorb(kundenID);
		if (warenkorb == null) {
			Bestellung neuerWarenkorb = new Bestellung();
			if(!dao.createBestellung(neuerWarenkorb, kundenID)){
				throw new Exception("Unerwarteter Fehler! Warenkorb kann nicht generiert werden.");
			}
			return neuerWarenkorb;
		}
		return warenkorb;
	}

	/**
	 * Entfernt die Bestellung mit der entsprechenden ID aus der Datenbank.
	 * Zuvor werden noch alle Positionen der Bestellung aus der Datenbank
	 * geloescht. Sollte keine Bestellung mit der ID vorhanden sein wird false
	 * retourniert.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung, die geloescht werden soll.
	 * @return true falls die Bestellung erfolgreich entfernt wurde, sonst
	 *         false.
	 */
	public boolean removeBestellung(int bestellungsID) {
		List<Position> positionen = dao.readPositionenByBestellungID(bestellungsID);
		for (Position pos : positionen) {
			dao.deletePosition(bestellungsID, pos.getPostionID());
		}
		return dao.deleteBestellung(bestellungsID);
	}

	/**
	 * Entfernt die Positionen mit der entsprechenden ID aus der Bestellung mit
	 * der entsprechenden ID. Sollte keine Bestellung oder Position mit der ID
	 * vorhanden sein wird false retourniert.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung in der die Position enthalten sein soll.
	 * @param positionID
	 *            ID der Position, die geloescht werden soll.
	 * @return true falls die Position erfolgreich entfernt wurde, sonst false.
	 */
	public boolean removePositionFromBestellung(int bestellungsID, int positionID) {
		return dao.deletePosition(bestellungsID, positionID);
	}
}
