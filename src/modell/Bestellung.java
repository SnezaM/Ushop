/**
 * 
 */
package modell;

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
	//private int kundenID;
	

}