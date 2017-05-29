package generator;

import java.util.List;
import java.util.Random;

import management.Benutzerverwaltung;
import management.Bestellungsverwaltung;
import management.Produktverwaltung;
import modell.Benutzer;
import modell.Bestellung;
import modell.Position;
import modell.Produkt;

public class BestellungsGenerator {

	// Dem Programm soll als erster Parameter die Anzahl der zu erstellenden
	// Bestellungen uebergeben werden, als zweiter Parameter soll eine Anzahl
	// fuer die mindestens zu erstellenden Positionen eingegeben werden.
	// Als drittes Parameter soll die maximale Menge eines Produktes uebergeben
	// werden.
	public static void main(String[] args) {
		Bestellungsverwaltung bestellungsverwaltung = Bestellungsverwaltung.getInstance();
		Produktverwaltung produktverwaltung = Produktverwaltung.getInstance();
		Benutzerverwaltung benutzerverwaltung = Benutzerverwaltung.getInstance();
		
		List<Benutzer> benutzer = benutzerverwaltung.getBenutzerListe();
		List<Produkt> produkt = produktverwaltung.getAlleProdukt();
		
		Random random = new Random();
		int randomInt;
		int positionsCounter = 0;
		int bestellungsCounter = 0;
		
		try {
			//Loop zur Bestellungserstellung
			for(int i=0; i < Integer.parseInt(args[0]); i++){
				randomInt = random.nextInt(benutzer.size());
				Benutzer user = benutzer.get(randomInt);
				Bestellung warenkorb = bestellungsverwaltung.getWarenkorb(user.getBenutzerid());
				// Eine Variable Anzahl von Positionen im vorgegeben Rahmen wird festgelegt
				int anzahlPositionen = random.nextInt(Integer.parseInt(args[1])+1); 
				//Loop zur Positionserstellung
				for(int j=1; j <= anzahlPositionen; j++){
					randomInt = random.nextInt(produkt.size());
					Produkt p = produkt.get(randomInt);
					int menge = random.nextInt(Integer.parseInt(args[2])+1);
					Position position = new Position(j, p.getProduktID(), menge, (menge*p.getPreis()));
					if(bestellungsverwaltung.addPositionToBestellung(warenkorb.getBestellungID(), position))
						positionsCounter++;
				}
				int tag = random.nextInt(28)+1;
				int monat = random.nextInt(12)+1;
				int jahr = 2016-random.nextInt(4);
				String date = jahr + "-" + monat + "-" + tag;
				if(bestellungsverwaltung.addBestellung(warenkorb, date))
					bestellungsCounter++;
			}
			System.out.println("Hinzugefuegt: Bestellungen: " + bestellungsCounter + ", Positionen: " + positionsCounter);
		} catch (Exception e) {
			System.out.println(e);
		}		
	}

}
