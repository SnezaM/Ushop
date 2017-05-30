package generator;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import management.Benutzerverwaltung;
import management.Bestellungsverwaltung;
import management.Produktverwaltung;
import modell.Benutzer;
import modell.Bestellung;
import modell.Lieferart;
import modell.Position;
import modell.Produkt;

/**
 * Generiert Bestellungen. Produkte und Kunden muessen dafuer bereits vorhanden
 * sein.
 * 
 * @author Katrin Rohrmueller (1309572)
 *
 */
public class BestellungsGenerator {
	private Bestellungsverwaltung bestellungsverwaltung;
	private Produktverwaltung produktverwaltung;
	private Benutzerverwaltung benutzerverwaltung;
	private Random random;
	private static int positionsCounter = 0;
	private static int bestellungsCounter = 0;
	private String[] vermerk = { "", "", "Beim Nachbarn abgeben", "", "", "Pronto bitte!", "", "", "Ein Geschenk" };

	/**
	 * Konstruktor.
	 */
	public BestellungsGenerator() {
		super();
		this.bestellungsverwaltung = Bestellungsverwaltung.getInstance();
		this.produktverwaltung = Produktverwaltung.getInstance();
		this.benutzerverwaltung = Benutzerverwaltung.getInstance();
		this.random = new Random();
	}

	/**
	 * Retourniert eine Liste mit den Produkten aus der Datenbank.
	 * 
	 * @return List<Produkt>
	 */
	public List<Produkt> getProduktList() {
		return produktverwaltung.getAlleProdukt();
	}

	/**
	 * Retourniert eine Liste mit den Benutzern aus der Datenbank.
	 * 
	 * @return List<Benutzer>
	 */
	public List<Benutzer> getBenutzerList() {
		return benutzerverwaltung.getBenutzerListe();
	}

	/**
	 * Liefert einen zufaelligen Integer zwischen 1 und dem uebergebenen Maximum
	 * retour.
	 * 
	 * @param max
	 *            Maximaler Wert
	 * @return Zufallszahl
	 */
	public int getRandom(int max) {
		return (this.random.nextInt(max) + 1);
	}

	/**
	 * Liefert den Warenkorb des Kunden mit der ID retour.
	 * 
	 * @param kundenID
	 *            ID des Kunden.
	 * @return Warenkorb.
	 */
	public Bestellung getWarenkorb(int kundenID) {
		return bestellungsverwaltung.getWarenkorb(kundenID);
	}

	/**
	 * Erstellt einen Warenkorb fuer den Kunden mit der uebergebenen ID.
	 * 
	 * @param kundenID
	 *            ID des Kunden.
	 * @return true wenn Warenkorb angelegt wurde, sonst false
	 */
	public Boolean createWarenkorb(int kundenID) {
		return bestellungsverwaltung.addWarenkorb(kundenID);
	}

	/**
	 * Macht aus einem Warenkorb - uebergenen als Bestellung - inkl.
	 * zusaetzlicher Daten, wie Vermerk und Lieferart, und dem uebergebenen
	 * Datum eine abgeschlossene Bestellung.
	 * 
	 * @param bestellung
	 *            Warenkorb, der bestellt werden soll.
	 * @param date
	 *            Bestelldatum.
	 * @return true wenn Bestellung angelegt wurde, sonst false
	 */
	public Boolean makeBestellung(Bestellung bestellung, String date) {
		return bestellungsverwaltung.addBestellung(bestellung, date);
	}

	/**
	 * Generiert ein Datum, das maximal die uebergebene Anzahl von Jahren vom
	 * aktuellen Datum retour liegt. [Berechnung: yearsBack*365]
	 * 
	 * @param yearsBack
	 *            Jahre, die vom aktuellen Datum maximal abgezogen werden.
	 * @return Datum als String [Format: YYYY-MM-DD]
	 */
	public String makeDate(int yearsBack) {
		LocalDate aktDate = LocalDate.now();
		LocalDate newDate = aktDate.minusDays(this.random.nextInt((365 * yearsBack)));
		return newDate.toString();
	}

	/**
	 * Fuegt die uebergebene Position zu der Bestllung mit der uebergebenen ID
	 * hinzu.
	 * 
	 * @param bestellungsID
	 *            ID der Bestellung.
	 * @param position
	 *            Position, die hinzugefuegt werden soll.
	 * @return true wenn Position hinzugefuegt werden konnte, sonst false
	 */
	public boolean addPosition(int bestellungsID, Position position) {
		return bestellungsverwaltung.addPositionToBestellung(bestellungsID, position);
	}

	/**
	 * Liefert einen zufaelligen Vermerk aus dem vermerk-Array retour.
	 * 
	 * @return Vermerk als String
	 */
	public String getRandomVermerk() {
		return vermerk[this.random.nextInt(vermerk.length)];
	}

	public static void main(String[] args) {
		BestellungsGenerator generator = new BestellungsGenerator();
		List<Benutzer> benutzer = generator.getBenutzerList();
		List<Produkt> produkt = generator.getProduktList();

		Scanner sc = new Scanner(System.in);
		System.out.println("Bei wie vielen Kunden sollen Bestellungen generiert werden?");
		int maxOrder = Integer.parseInt(sc.nextLine());
		System.out.println("Wie viele Bestellungen sollen maximal pro Kunde generiert werden?");
		int maxOrderKunde = Integer.parseInt(sc.nextLine());
		System.out.println("Wie viele Positionen pro Bestellung sollen maximal generiert werden?");
		int maxPos = Integer.parseInt(sc.nextLine());
		System.out.println("Wie gross soll die Menge eines Artikels maximal in einer Position sein?");
		int maxMengeProd = Integer.parseInt(sc.nextLine());
		System.out.println("Wie viele Jahre soll das Datum maximal in der Vergangenheit liegen?");
		int yearsBack = Integer.parseInt(sc.nextLine());
		System.out.println("Einen Moment - die Daten werden generiert. . . .");
		sc.close();

		/*
		 * int maxOrder = Integer.parseInt(args[0]); int maxOrderKunde =
		 * Integer.parseInt(args[1]); int maxPos = Integer.parseInt(args[2]);
		 * int maxMengeProd = Integer.parseInt(args[3]); int yearsBack =
		 * Integer.parseInt(args[4]);
		 */
		if (benutzer == null || produkt == null) {
			System.out.println("Keine Benutzer oder Produkte gefunden! Generator wird beendet!");
			return;
		}
		if (maxOrder <= 0 || maxOrderKunde <= 0 || maxPos <= 0 || maxMengeProd <= 0 || yearsBack <= 0) {
			System.out.println("Keine negativen Werte oder 0 zulaessig! Generator wird beendet!");
			return;
		}

		// Bestellungserzeugung
		for (int i = 0; i < maxOrder; i++) {
			int random = generator.getRandom(benutzer.size());
			int kundenID = benutzer.get(random).getBenutzerid();
			// Bestellungserzeugung Kunde
			int randomOrder = generator.getRandom(maxOrderKunde);
			for (int j = 0; j < randomOrder; j++) {
				Bestellung warenkorb = generator.getWarenkorb(kundenID);
				if (warenkorb == null) {
					if (generator.createWarenkorb(kundenID)) {
						warenkorb = generator.getWarenkorb(kundenID);
					} else {
						System.out.println("Fehler beim Generieren des Warenkorbs!");
						return;
					}
				}
				int warenkorbID = warenkorb.getBestellungID();
				// Positionserzeugung
				int randomPos = generator.getRandom(maxPos);
				for (int k = 0; k < randomPos; k++) {
					int randomProd = generator.getRandom(produkt.size());
					Produkt prod = produkt.get(randomProd);
					int randomMenge = generator.getRandom(maxMengeProd);
					Position pos = new Position(k + 1, prod.getProduktID(), randomMenge, randomMenge * prod.getPreis());
					if (generator.addPosition(warenkorbID, pos)) {
						positionsCounter++;
					}
				}
				// Bestellung entgueltig erzeugen
				warenkorb.setLieferart(Lieferart.getRandomLieferart());
				warenkorb.setVermerk(generator.getRandomVermerk());
				if (generator.makeBestellung(warenkorb, generator.makeDate(yearsBack))) {
					bestellungsCounter++;
				}
			}
		}
		System.out.println("Hinzugefuegt: Bestellungen: " + bestellungsCounter + ", Positionen: " + positionsCounter);
	}

}
