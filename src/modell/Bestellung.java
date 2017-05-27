package modell;

import modell.Lieferart;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Katrin, 1309572
 *
 */
public class Bestellung {
	private UUID bestellungID;
	private double gesamtpreis;
	private int anzahl;
	private List<Position> positionen;
	private boolean abgeschlossen;
	private String vermerk;
	private Lieferart lieferart;
	private LocalDate datum;
	private int kundenID;
	

	/**
	 * Konstruktor. (Instanz fuer noch nicht abgeschlossene Bestellungen)
	 * @param gesamtpreis Gesamtpreis der Bestellung.
	 * @param positionen Liste mit den Positionen der Bestellung.
	 * @param kundenID KundenID des Kunden der die Bestellung tätigt.
	 */
	public Bestellung(double gesamtpreis, List<Position> positionen, int kundenID) {
		this.bestellungID = UUID.randomUUID();
		this.gesamtpreis = gesamtpreis;
		this.anzahl = positionen.size();
		this.positionen = positionen;
		this.abgeschlossen = false;
		this.vermerk = null;
		this.lieferart = null;
		this.datum = null;
		this.kundenID = kundenID;
	}
	
	/**
	 * Retourniert die BestellungsID.
	 * @return bestellungID
	 */
	public UUID getBestellungID() {
		return bestellungID;
	}

	/**
	 * Retourniert den Gesamtpreis der Bestellung.
	 * @return gesamtpreis
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}


	/**
	 * Retourniert die Anzahl der Positionen der Bestellung.
	 * @return anzahl
	 */
	public int getAnzahl() {
		return anzahl;
	}


	/**
	 * Retourniert eine Liste mit den Positionen
	 * @return positionen
	 */
	public List<Position> getPositionen() {
		return positionen;
	}


	/**
	 * Retourniert, ob eine Bestellung abgeschlossen ist (true) oder nicht (false).
	 * @return abgeschlossen
	 */
	public boolean isAbgeschlossen() {
		return abgeschlossen;
	}


	/**
	 * Retourniert den Vermerk einer Bestellung.
	 * @return vermerk
	 */
	public String getVermerk() {
		return vermerk;
	}


	/**
	 * Retourniert die Lieferart einer Bestellung.
	 * @return the lieferart
	 */
	public Lieferart getLieferart() {
		return lieferart;
	}


	/**
	 * Retourniert das Datum der Bestellung.
	 * @return datum
	 */
	public LocalDate getDatum() {
		return datum;
	}


	/**
	 * Retourniert die KundenID des Bestellers.
	 * @return kundenID
	 */
	public int getKundenID() {
		return kundenID;
	}

	/**
	 * Setzt den Gesamtpreis der Bestellung.
	 * @param gesamtpreis Gesamtpreis, der gesetzt werden soll.
	 */
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}


	/**
	 * Setzt die Anzahl der Positionen einer Bestellung.
	 * @param anzahl the anzahl to set
	 */
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}


	/**
	 * Setzt die Liste der Positionen für die Bestellung.
	 * @param positionen Liste mit Positionen, die gesetzt werden soll.
	 */
	public void setPositionen(List<Position> positionen) {
		this.positionen = positionen;
	}


	/**
	 * Setzt den Status für abgeschlossen für die Bestellung.
	 * @param abgeschlossen Status, der für abgeschlossen gesetzt werden soll.
	 */
	public void setAbgeschlossen(boolean abgeschlossen) {
		this.abgeschlossen = abgeschlossen;
	}


	/**
	 * Setzt den Vermerk der Bestellung.
	 * @param vermerk Vermerk, der gesetzt werden soll.
	 */
	public void setVermerk(String vermerk) {
		this.vermerk = vermerk;
	}


	/**
	 * Setzt die Lieferart der Bestellung.
	 * @param lieferart Lieferart, die gesetzt werden soll.
	 */
	public void setLieferart(Lieferart lieferart) {
		this.lieferart = lieferart;
	}


	/**
	 * Setzt das Datum der Bestellung.
	 * @param datum Datum, das gesetzt werden soll.
	 */
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}


	/**
	 * Setzt die KundenID des Bestellers für die Bestellung.
	 * @param kundenID KundenID, die gestzt werden soll.
	 */
	public void setKundenID(int kundenID) {
		this.kundenID = kundenID;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellung [" + (bestellungID != null ? "BestellungID=" + bestellungID + ", " : "") + "KundenID="
				+ kundenID + ", Anzahl=" + anzahl + ", " + (positionen != null ? "Positionen=" + positionen + ", " : "")
				+ "Gesamtpreis=" + gesamtpreis + ",Abgeschlossen=" + abgeschlossen + ", "
				+ (datum != null ? "Datum=" + datum + ", " : "")
				+ (lieferart != null ? "Lieferart=" + lieferart + ", " : "")
				+ (vermerk != null ? "Vermerk=" + vermerk : "") + "]";
	}
}