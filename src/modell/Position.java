/**
 * 
 */
package modell;

/**
 * @author Katrin
 *
 */
public class Position {
	private int positionID;
	private int menge;
	private double gesamtpreis;
	private Produkt artikel;
	
	/**
	 * @param positionID
	 * @param menge
	 * @param gesamtpreis
	 * @param artikel
	 */
	public Position(int positionID, int menge, double gesamtpreis, Produkt artikel) {
		super();
		this.positionID = positionID;
		this.menge = menge;
		this.gesamtpreis = gesamtpreis;
		this.artikel = artikel;
	}

	/**
	 * @return the positionID
	 */
	public int getPostionID() {
		return positionID;
	}

	/**
	 * @return the menge
	 */
	public int getMenge() {
		return menge;
	}

	/**
	 * @return the gesamtpreis
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * @return the artikel
	 */
	public Produkt getArtikel() {
		return artikel;
	}

	/**
	 * @param positionID the postionID to set
	 */
	public void setPostionID(int positionID){
		this.positionID = positionID;
	}

	/**
	 * @param menge the menge to set
	 */
	public void setMenge(int menge) {
		this.menge = menge;
	}

	/**
	 * @param gesamtpreis the gesamtpreis to set
	 */
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}

	/**
	 * @param artikel the artikel to set
	 */
	public void setArtikel(Produkt artikel) {
		this.artikel = artikel;
	}
}
