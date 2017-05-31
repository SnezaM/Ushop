/**
 * Das Paket dient zum persistenten speichern und lesen in der 
 * Datenbank um spaeter wieder darauf zugreifen zu koennen.
 * 
 */
package dao;

/**
 * @author Mirza Talic a1367543
 *Dieses Interface definiert Methoden des zugriffs auf die Datenbank in Bezug auf Produkte. 
 *
 */
import java.util.List;

import modell.Produkt;

public interface ProduktDAO {
	
	public boolean speichereProdukt(Produkt p);
	
	public boolean loescheProduktByID(int produktID);
	
	public List<Produkt> getProduktList();
	
	public Produkt getProduktByProduktID(int produktID);
	
	public Produkt getProduktByName(String pname);
	
	public List<Produkt> sucheProdukt(String lis);

}
