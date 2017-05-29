package management;

import dao.BestellungsDAO;
import dao.DBBestellungsDAO;

/**
 * <b>Klasse Bestellungsverwaltung</b>
 * <p>
 * Verwaltet alle Methoden, die mit der Bestellung in Verbindung stehen.<br>
 * Verwendet für den Datenbankzugriff das {@link BestellungsDAO}.
 * </p>
 * 
 * @see Bestellung, BestellungsDAO
 * @author Katrin (1309572)
 *
 */
public class Bestellungsverwaltung {

	private static Bestellungsverwaltung BestellungsverwaltungInstance = null;
	private BestellungsDAO dao;

	private Bestellungsverwaltung() {
		dao = new DBBestellungsDAO();
	}

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
}
