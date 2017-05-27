/**
 * 
 */
package modell;

/**
 * @author Katrin, 1309572
 * 
 * Klasse zur Erstellung von Positionen. Eine Positionen ist Bestandteile einer Bestellung.
 * <p>Postion verwendet {@link Produkt}.</p>
 * 
 * @see Bestellung
 *
 */
public class Position {
	private int positionID;
	private Produkt artikel;
	private int menge;
	private double gesamtpreis;
	
	/**
	 * Konstruktor.
	 * @param positionID Postionsnummer der Postion.
	 * @param artikel Produkt der Positon.
	 * @param gesamtpreis Gesamtwert der Position.
	 * @param menge Menge der Position.
	 */
	public Position(int positionID, Produkt artikel, int menge, double gesamtpreis) {
		this.positionID = positionID;
		this.artikel = artikel;	
		this.menge = menge;
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * Retourniert die Positionsnummer.
	 * @return positionID
	 */
	public int getPostionID() {
		return positionID;
	}

	/**
	 * Retourniert die Menge.
	 * @return menge
	 */
	public int getMenge() {
		return menge;
	}

	/**
	 * Retourniert den Gesamtpreis.
	 * @return gesamtpreis
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * Retourniert den Artikel.
	 * @return artikel
	 */
	public Produkt getArtikel() {
		return artikel;
	}

	/**
	 * Setzt die Positionsnummer.
	 * @param positionID postionID, die gesetzt werden soll
	 */
	public void setPostionID(int positionID){
		this.positionID = positionID;
	}

	/**
	 * Setzt die Menge.
	 * @param menge menge, die gesetzt werden soll
	 */
	public void setMenge(int menge) {
		this.menge = menge;
	}

	/**
	 * Setzt den Gesamtpreis.
	 * @param gesamtpreis gesamtpreis, der gesetzt werden soll
	 */
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * Setzt den Artikel.
	 * @param artikel artikel, der gesetzt werden soll
	 */
	public void setArtikel(Produkt artikel) {
		this.artikel = artikel;
	}
}
