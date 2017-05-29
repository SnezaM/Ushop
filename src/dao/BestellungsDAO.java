/**
 * 
 */
package dao;

import java.util.List;

import modell.Bestellung;
import modell.Position;

/**
 * <b>Interface BestellungsDAO:</b>
 * <p>
 * Definiert Methoden fuer den Datenbankzugriff im Bezug auf Bestellungen.
 * </p>
 * 
 * @see {@link Bestellung}
 * @author Katrin Rohrmueller (1309572)
 *
 */
public interface BestellungsDAO {
	/**
	 * <b>Fuegt der Bestellung mit der uebergebenen ID, die uebergebene Position
	 * hinzu</b>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Bestellung, die um die Position ergaenzt
	 *            werden soll.
	 * @param position
	 *            Position, die zur Bestellungs hinzugefuegt werden soll.
	 * @return true bei erfolgreichen Hinzufuegen, sonst false
	 */
	public boolean addPositionToBestellung(int bestellungsID, Position position);

	/**
	 * <b>Liefert die Bestellung mit der uebergebenen ID retour</b>
	 * <p>
	 * Mithilfe der uebergebenen BestellungsID wird die Datenbank nach der
	 * korrekten Bestellung durchsucht und diese wird sodann retourniert. Sollte
	 * keine entsprechende Bestellung vorhanden sein wird null retourniert.
	 * </p>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Bestellung, die gesucht wird.
	 * @return Bestellung mit der uebergebenen ID
	 */
	public Bestellung getBestellungByID(int bestellungsID);

	/**
	 * <b>Liefert eine Liste mit allen Bestellungen eines Kunden retour</b>
	 * <p>
	 * Mithilfe der uebergebenen KundenID wird die Datenbank nach Bestellungen
	 * fuer den Kunden durchsucht und eine Liste mit allen entsprechenden
	 * Bestellungen retourniert. Sollten keine Bestellungen vorhanden sein, wird
	 * eine leere Liste retourniert.
	 * </p>
	 * 
	 * @param kundenID
	 *            KundenID, des Kunden fuer den Bestellungen gesucht werden
	 *            sollen.
	 * @return Liste mit Bestellungen des Kunden mit der entsprechenden ID
	 */
	public List<Bestellung> getBestellungListByKundenID(int kundenID);

	/**
	 * <b>Liefert eine Liste mit allen Positionen einer Bestellung retour</b>
	 * <p>
	 * Mithilfe der uebergebenen BestellungsID wird die Datenbank nach
	 * Positionen zur Bestellung durchsucht und eine Liste mit allen
	 * entsprechenden Positionen retourniert. Sollten keine Position vorhanden
	 * sein, wird eine leere Liste retourniert.
	 * </p>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Positionen, die gesucht werden.
	 * @return Liste mit Positon der Bestellung mit der entsprechenden ID
	 */
	public List<Position> getPositionListByBestellungID(int bestellungsID);

	/**
	 * <b>Liefert die Position mit der uebergebenen ID aus der Bestellung mit
	 * der uebergebenen ID retour</b>
	 * <p>
	 * Mithilfe der uebergebenen BestellungsID und PositionsID wird die
	 * Datenbank nach der korrekten Position durchsucht und diese wird sodann
	 * retourniert. Sollte keine entsprechende Position vorhanden sein wird null
	 * retourniert.
	 * </p>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Bestellung, die gesucht wird.
	 * @param positionID
	 *            PositionsID, der gesuchten Position.
	 * @return Position mit der uebergebenen ID aus Bestellung mit der
	 *         uebergebenen ID
	 */
	public Position getPositionByID(int bestellungsID, int positionID);

	/**
	 * <b>Entfernt die Position mit der entsprechenden PositionsID aus der
	 * Bestellung mit der uebergeben BestellungsID</b>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Bestellung mit zu loeschender Position
	 * @param positionID
	 *            PositionsID, der zu entfernenden Instanz
	 * @return true bei erfolgreichem Entfernen, sonst false
	 */
	public boolean removePositionFromBestellung(int bestellungsID, int positionID);

	/**
	 * <b>Speichert die uebergebene Bestellung in der DB.</b>
	 * <p>
	 * Die Bestellung wird nur anhand der fuer einen Warenkorb noetigen Fakten
	 * in der DB gespeichert. Die Instanzen abgeschlossen, vermerk, lieferart
	 * und datum, werden nicht uebernommen sondern als null-Values eingetragen.
	 * </p>
	 * 
	 * @param bestellung
	 *            Bestellung, die gespeichert werden soll.
	 * @return true, falls die Bestellung gesepeichert werden konnte, sonst
	 *         false
	 */
	public boolean speichereWarenkorb(Bestellung bestellung);

	/**
	 * <b>Aktualisiert die uebergebene Bestellungs in der Datenbank</b>
	 * <p>
	 * Wenn die uebergebene Bestellung in der DB vorhanden ist wird diese in den
	 * Variablen lieferart, datum und vermerk anhand der uebergebenen Bestellung
	 * aktualisiert. Die Variable abgeschlossen wird auf true gesetzt. Wenn Sie
	 * nicht vorhanden ist, wird kein Update und keine Speicherung
	 * durchgefuehrt.
	 * </p>
	 * 
	 * @param bestellung
	 *            Bestellung, die aktualiesiert werden soll
	 * @param date
	 *            Datum, das als Bestellungsdatum eingetragen wird (Format:
	 *            "YYYY-MM-DD")
	 * @return true bei erfolgreichen Aktualisieren, sonst false
	 */
	public boolean speichereBestellung(Bestellung bestellung, String date);

	/**
	 * <b>Aktualisiert die Menge und den Gesamtpreis der Position mit der
	 * uebergebenen PositionsID in der Bestellung mit der entsprechenden
	 * BestellungsID anhand der uebergebenen Daten</b>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Bestellung mit der Position die anzupassen
	 *            ist
	 * @param positionsID
	 *            PositionsID, der Position die anzupassen ist
	 * @param menge
	 *            Menge, die eingesetzt werden soll
	 * @param preis
	 *            Preis, der eingesetzt werden soll
	 * @return true bei erfolgreichen Aendern, sonst false
	 */
	public boolean updateMengeFromPosition(int bestellungsID, int positionsID, int menge, double preis);
}
