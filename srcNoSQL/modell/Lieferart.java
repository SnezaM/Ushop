package modell;

import java.util.Random;

/**
 * Enum, der die diversen moeglichen Lieferarten darstellt.
 * 
 * @author Katrin Rohrmueller (1309572)
 */
public enum Lieferart {
	Standardversand, DHL, DPD, Post, GLS;

	private static final Lieferart[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();

	/**
	 * Retourniert einen zufaelligen Enum aus Lieferart.
	 * 
	 * @return Lieferart [DHL, DPD, Post, GLS, Standardversand]
	 */
	public static Lieferart getRandomLieferart() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}
