/**
 * Das Paket dient zum persistenten speichern und lesen in der 
 * Datenbank um spaeter wieder darauf zugreifen zu koennen.
 * 
 */
package dao;

/**
 * @author Mirza Talic a1367543
 *Dieses Interface definiert Methoden des zugriffs auf die Datenbank in Bezug auf Produktgruppen. 
 *
 */

import java.util.List;
import modell.Produktgruppe;

public interface ProduktgruppeDAO {
	
	public boolean speichereProduktgruppe(Produktgruppe p);
	
	public boolean loescheProduktgruppetByID(int produktgruppeID);
	
	public List<Produktgruppe> getProduktgruppeList();
	
	public Produktgruppe getProduktgruppeByID(int produktgruppeID);

}
