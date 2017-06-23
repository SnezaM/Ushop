package modell;

/**
 * <b>Klasse Bestellung:</b>
 * <p>
 * Dient der Erstellung von Bestellungen. Eine Bestellung enthaelt mindestens
 * eine Position.<br>
 * Postion verwendet {@link Produkt}.
 * </p>
 * 
 * @see {@link Position}, {@link Produkt}
 * @author Katrin Rohrmueller (1309572)
 *
 */
public class Bestellung {
	private int bestellungsID;
	private double gesamtpreis;
	private boolean abgeschlossen;
	private String datum;
	private String vermerk;
	private Lieferart lieferart;

	/**
	 * Konstruktor. Default.
	 */
	public Bestellung() {
	}

	/**
	 * Konstruktor.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung.
	 * @param gesamtpreis
	 *            Gesamtpreis der Bestellung.
	 * @param abgeschlossen
	 *            Status der Bestellung. (true = abgeschlossen, else false)
	 * @param datum
	 *            Datum der Bestellung.
	 * @param vermerk
	 *            Vermerk der Bestellung.
	 * @param lieferart
	 *            Lieferart der Bestellung.
	 */
	public Bestellung(int bestellungsID, double gesamtpreis, boolean abgeschlossen, String datum, String vermerk,
			Lieferart lieferart) {
		this.bestellungsID = bestellungsID;
		this.gesamtpreis = gesamtpreis;
		this.abgeschlossen = abgeschlossen;
		this.datum = datum;
		this.vermerk = vermerk;
		this.lieferart = lieferart;
	}

	/**
	 * Konstruktor. (Instanz fuer noch nicht abgeschlossene Bestellungen bzw.
	 * Warenkorb)
	 */
	public Bestellung(boolean abgeschlossen) {
		this.gesamtpreis = 0;
		this.abgeschlossen = abgeschlossen;
		this.datum = null;
		this.vermerk = null;
		this.lieferart = null;
	}

	/**
	 * Retourniert die BestellungsID.
	 * 
	 * @return bestellungsID
	 */
	public int getBestellungID() {
		return bestellungsID;
	}

	/**
	 * Retourniert den Gesamtpreis der Bestellung.
	 * 
	 * @return gesamtpreis
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * Retourniert, ob eine Bestellung abgeschlossen ist (true) oder nicht
	 * (false).
	 * 
	 * @return abgeschlossen
	 */
	public boolean isAbgeschlossen() {
		return abgeschlossen;
	}

	/**
	 * Retourniert das Datum der Bestellung.
	 * 
	 * @return the datum
	 */
	public String getDatum() {
		return datum;
	}

	/**
	 * Retourniert den Vermerk einer Bestellung.
	 * 
	 * @return vermerk
	 */
	public String getVermerk() {
		return vermerk;
	}

	/**
	 * Retourniert die Lieferart einer Bestellung.
	 * 
	 * @return the lieferart
	 */
	public Lieferart getLieferart() {
		return lieferart;
	}

	/**
	 * Setzt den Gesamtpreis der Bestellung.
	 * 
	 * @param gesamtpreis
	 *            Gesamtpreis, der gesetzt werden soll.
	 */
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * Setzt den Status fuer abgeschlossen fuer die Bestellung.
	 * 
	 * @param abgeschlossen
	 *            Status, der fuer abgeschlossen gesetzt werden soll.
	 */
	public void setAbgeschlossen(boolean abgeschlossen) {
		this.abgeschlossen = abgeschlossen;
	}

	/**
	 * Setzt das Datum der Bestellung. Diese ist im Format YYYY-MM-DD anzugeben.
	 * 
	 * @param datum
	 *            Datum, das gesetzt werden soll.
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}

	/**
	 * Setzt den Vermerk der Bestellung.
	 * 
	 * @param vermerk
	 *            Vermerk, der gesetzt werden soll.
	 */
	public void setVermerk(String vermerk) {
		this.vermerk = vermerk;
	}

	/**
	 * Setzt die Lieferart der Bestellung.
	 * 
	 * @param lieferart
	 *            Lieferart, die gesetzt werden soll.
	 */
	public void setLieferart(Lieferart lieferart) {
		this.lieferart = lieferart;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bestellung [BestellungID=" + bestellungsID + ", Gesamtpreis=" + gesamtpreis + ", Abgeschlossen="
				+ abgeschlossen + (datum != null ? ", Datum=" + datum : "")
				+ (lieferart != null ? ", Lieferart=" + lieferart : "")
				+ (vermerk != null ? ", Vermerk=" + vermerk : "") + "]";
	}
}