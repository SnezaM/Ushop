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
	 * <p>
	 * Die Datenbank wird mit Hilfe der uebergebenen BestellungsID nach der
	 * Bestellung durchsucht. Sollte keine entsprechende Bestellung vorhanden
	 * sein, wird der Vorgang abgebrochen und false retourniert. Ist die
	 * Bestellung vorhanden wird ueberprueft, ob die Bestellungs bereits
	 * abgeschlossen wurde, falls ja wird false retourniert und der Vorgang
	 * abgebrochen, sonst wird die Position mit der naechstmoechlichen
	 * PositionsID in der Datenbank gespeichert und true retourniert.
	 * </p>
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
	 * <b>Entfernt die Position mit der entsprechenden PositionsID aus der
	 * Bestellung mit der uebergeben BestellungsID</b>
	 * <p>
	 * Die Datenbank wird mit Hilfe der uebergebenen BestellungsID nach der
	 * Bestellung durchsucht. Sollte keine entsprechende Bestellung vorhanden
	 * sein, wird der Vorgang abgebrochen und false retourniert. Ist die
	 * Bestellung vorhanden wird ueberprueft, ob die Bestellungs bereits
	 * abgeschlossen wurde, falls ja wird false retourniert und der Vorgang
	 * abgebrochen, sonst wird nach der Position mit der entsprechenden ID
	 * gesucht und diese wird entfernt. Andernfalls wird der false retourniert
	 * und der Vorgang wird abgebrochen.
	 * </p>
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
	 * Eine uebergebene Bestellung wird in der Datenbank gespeichert. Zuvor wird
	 * ueberprueft, ob es eine Bestellung mit derselben ID gibt, wenn ja kann
	 * die Bestellung nicht gespeichert werden und der Vorgang wird abgebrochen.
	 * In diesem Fall liefert die Methode false als Ergebnis retour.
	 * </p>
	 * 
	 * @param bestellung
	 *            Bestellung, die gespeichert werden soll.
	 * @return true, falls die Bestellung gesepeichert werden konnte, sonst
	 *         false
	 */
	public boolean speichereBestellung(Bestellung bestellung);

	/**
	 * <b>Aktualisiert die uebergebene Bestellungs in der Datenbank</b>
	 * <p>
	 * Die Datenbank wird mit Hilfe der BestellungsID der uebergebenen
	 * Bestellung nach der Bestellung durchsucht. Sollte keine entsprechende
	 * Bestellung vorhanden sein, wird der Vorgang abgebrochen und false
	 * retourniert. Ist die Bestellung vorhanden wird ueberprueft, ob die
	 * Bestellungs bereits abgeschlossen wurde, falls ja wird false retourniert
	 * und der Vorgang abgebrochen, sonst wird die Bestellung in der Datenbank
	 * mit den Daten aus der uebergebenen Bestellung aktualisiert.
	 * 
	 * @param bestellung
	 *            Bestellung, die aktualiesiert werden soll
	 * @param date
	 *            Datum, das als Bestellungsdatum eingetragen wird (Format:
	 *            "YYYY-MM-DD")
	 * @return true bei erfolgreichen Aktualisieren, sonst false
	 */
	public boolean updateBestellung(Bestellung bestellung, String date);

	/**
	 * <b>Aktualisiert die Menge und den Gesamtpreis der Position mit der
	 * uebergebenen PositionsID in der Bestellung mit der entsprechenden
	 * BestellungsID</b>
	 * <p>
	 * Die Datenbank wird mit Hilfe der uebergebenen BestellungsID nach der
	 * Bestellung durchsucht. Sollte keine entsprechende Bestellung vorhanden
	 * sein, wird der Vorgang abgebrochen und false retourniert. Ist die
	 * Bestellung vorhanden wird ueberprueft, ob die Bestellungs bereits
	 * abgeschlossen wurde, falls ja wird false retourniert und der Vorgang
	 * abgebrochen, sonst wird nach der Position mit der entsprechenden ID
	 * gesucht und bei dieser wird die Menge auf den uebergebenen Preis gesetzt
	 * und der Preis entsprechend angepasst.
	 * </p>
	 * 
	 * @param bestellungsID
	 * @param positionsID
	 * @param menge
	 * @param preis
	 * @return
	 */
	public boolean updateMengeFromPosition(int bestellungsID, int positionsID, int menge, double preis);
}
