package dao;

import modell.Lieferart;

/**
 * <b>Klasse EntryToEnumeration</b>
 * <p>
 * Diese Klasse ermoeglicht mit Hilfe ihrer Methoden ein umwandeln von String in
 * die entsprechenden Enums.
 * </p>
 * 
 * @author Katrin Rohrmueller (1309572)
 *
 */
public class EntryToEnumeration {
	/**
	 * Strings werden in den entsprechenden Enum aus {@link Lieferart}
	 * konvertiert. Moegliche Eingabestrings sind STANDARDVERSAND, DHL, DPD,
	 * GLS, POST. Wird ein leerer String uebergeben wird STANDARDVERSAND
	 * retourniert.
	 * 
	 * @param lieferartDB
	 *            String der in Enum verwandelt werden soll.
	 * @return Uebergebenen String als Enum (DHL, DPD, GLS, POST,
	 *         STANDARDVERSAND)
	 * @see Lieferart
	 */
	public static Lieferart entryToLieferart(String lieferartDB) {
		Lieferart lieferart;
		if (lieferartDB == null) {
			return Lieferart.Standardversand;
		}
		switch (lieferartDB) {
		case "Standardversand":
			lieferart = Lieferart.Standardversand;
			break;
		case "DHL":
			lieferart = Lieferart.DHL;
			break;
		case "DPD":
			lieferart = Lieferart.DPD;
			break;
		case "GLS":
			lieferart = Lieferart.GLS;
			break;
		case "Post":
			lieferart = Lieferart.Post;
			break;
		default:
			throw new IllegalArgumentException("Lieferart nicht vorhanden!");
		}
		return lieferart;
	}
}
