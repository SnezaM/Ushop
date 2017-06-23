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
	public boolean createBestellungFromWarenkorb(Bestellung bestellung, String date);

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
	public boolean createPosition(int bestellungsID, Position position);

	/**
	 * <b>Speichert die uebergebene Bestellung in der DB.</b>
	 * <p>
	 * Die Bestellung wird nur anhand der fuer einen Warenkorb noetigen Fakten
	 * in der DB gespeichert. Abgeschlossen wird auf false gesetzt. Die
	 * Instanzen vermerk, lieferart und datum, werden nicht gesetzt sondern als
	 * null-Values eingetragen. Diese koennen spaeter mittels
	 * {@link #createBestellungFromWarenkorb(Bestellung, String)} in eine
	 * abgeschlossene Bestellung umgewandelt werden.
	 * </p>
	 * 
	 * @param kundenID
	 *            ID des Kunden, dessen Warenkorb gespeichert wird.
	 * @return true, falls die Bestellung gesepeichert werden konnte, sonst
	 *         false.
	 */
	public boolean createWarenkorb(int kundenID);

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
	 * <b>Liefert den Warenkorb des Kunden anhand der uebergebenen KundenID
	 * retour.</b>
	 * <p>
	 * Mithilfe der uebergebenen KundenID wird die Bestellung in der Datenbank
	 * gesucht, deren Bestellungsstatus nicht abgeschlossen ist. Wo also
	 * abgeschlossen false ist. Wir kein Warenkorb gefunden wird null
	 * retourniert.
	 * </p>
	 * 
	 * @param kundenID
	 *            ID des Kunden, dessen Warenkorb retourniert werden soll.
	 * @return Warenkorb des Kunden
	 */
	public Bestellung getWarenkorb(int kundenID);

	/**
	 * <b>Liefert eine Liste mit allen Bestellungen eines Kunden retour</b>
	 * <p>
	 * Mithilfe der uebergebenen KundenID wird die Datenbank nach Bestellungen
	 * fuer den Kunden durchsucht und eine Liste mit allen entsprechenden
	 * Bestellungen retourniert. Sollten keine Bestellungen vorhanden sein, wird
	 * eine leere Liste retourniert. <br/>
	 * Hinweis: Der Warenkorb - jene Bestellung, wo abgeschlossen false ist -
	 * wird nicht retourniert.
	 * </p>
	 * 
	 * @param kundenID
	 *            KundenID, des Kunden fuer den Bestellungen gesucht werden
	 *            sollen.
	 * @return Liste mit Bestellungen des Kunden mit der entsprechenden ID
	 */
	public List<Bestellung> readBestellungenByKundenID(int kundenID);

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
	public List<Position> readPositionenByBestellungID(int bestellungsID);

	/**
	 * <b>Entfernt die Bestellung mit der entsprechenden ID aus der
	 * Datenbank.</b>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Bestellung, die geloescht werden soll.
	 * @return true bei erfolgreichem Entfernen, sonst false.
	 */
	public boolean removeBestellung(int bestellungsID);

	/**
	 * <b>Entfernt die Position mit der entsprechenden PositionsID aus der
	 * Bestellung mit der uebergeben BestellungsID</b>
	 * 
	 * @param bestellungsID
	 *            BestellungsID, der Bestellung mit zu loeschender Position.
	 * @param positionID
	 *            PositionsID, der zu entfernenden Instanz.
	 * @return true bei erfolgreichem Entfernen, sonst false.
	 */
	public boolean removePosition(int bestellungsID, int positionID);

	/**
	 * <b>Aktualisiert die Menge und den Gesamtpreis der Position mit der
	 * uebergebenen PositionsID in der Bestellung mit der entsprechenden
	 * BestellungsID anhand der uebergebenen Daten.</b>
	 * <p>
	 * Hinweis: Eine Aenderung des Produktes innerhalb einer Position ist nicht
	 * moeglich. Ebenso kann auch die ID nicht geaendert werden.
	 * </p>
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
	public boolean updatePosition(int bestellungsID, int positionsID, int menge, double preis);

	/**
	 * <b>Aktualisiert den Gesamtpreis einer Bestellung mit der uerbergebenen
	 * BestellungsID anhand des uebergebenen Wertes.</b>
	 * 
	 * @param bestellungsID
	 * @param wert
	 * @return
	 */
	public boolean updatePriceBestellung(int bestellungsID, double wert);
}
