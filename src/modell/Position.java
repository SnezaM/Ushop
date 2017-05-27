/**
 * 
 */
package modell;

/**
 * 
 * <b>Klasse Position:</b>
 * <p>Dient der Erstellung von Positionen. Eine Positionen ist Bestandteile einer Bestellung.<br>
 * Postion verwendet {@link Produkt}.</p>
 * 
 * @see Bestellung, Produkt
 * @author Katrin Rohrmüller (1309572)
 */
public class Position {
	private int positionID;
	private int produktID;
	private int menge;
	private double gesamtpreis;
	
	/**
	 * Konstruktor.
	 * @param positionID Postionsnummer der Postion.
	 * @param produktID ProduktID des Produkts der Positon.
	 * @param gesamtpreis Gesamtwert der Position.
	 * @param menge Menge der Position.
	 */
	public Position(int produktID, int menge, double gesamtpreis) {
		this.positionID = 0;
		this.produktID = produktID;	
		this.menge = menge;
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * Retourniert die Positionsnummer der Position.
	 * @return positionID
	 */
	public int getPostionID() {
		return positionID;
	}

	/**
	 * Retourniert die Menge der Position.
	 * @return menge
	 */
	public int getMenge() {
		return menge;
	}

	/**
	 * Retourniert den Gesamtpreis der Position.
	 * @return gesamtpreis
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * Retourniert die ProduktID der Position.
	 * @return produktID
	 */
	public int getArtikel() {
		return produktID;
	}

	/**
	 * Setzt die Positionsnummer der Position.
	 * @param positionID postionID, die gesetzt werden soll
	 */
	public void setPostionID(int positionID){
		this.positionID = positionID;
	}

	/**
	 * Setzt die Menge der Position.
	 * @param menge menge, die gesetzt werden soll
	 */
	public void setMenge(int menge) {
		this.menge = menge;
	}

	/**
	 * Setzt den Gesamtpreis der Position.
	 * @param gesamtpreis gesamtpreis, der gesetzt werden soll
	 */
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * Setzt die ProduktID der Position.
	 * @param produktID produktID, der gesetzt werden soll
	 */
	public void setArtikel(int produktID) {
		this.produktID = produktID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [positionID=" + positionID + ", menge=" + menge + ", produktID=" + produktID + ", gesamtpreis="
				+ gesamtpreis + "]";
	}	
}
