/**
 * 
 * Das Package Management beinhaltet Klassen die zu Verwaltung der Produkte und Akteure benoetigt werden
 */
package management;

import java.util.List;
import dao.DatenBankProduktDAO;
import dao.ProduktDAO;
import modell.Produkt;

/**
 * 
 * @author Mirza Talic a1367543
 * Klasse Produktverwaltung verwaltet alle Methoden die mit dem Produkt in Verbindung stehen.
 *
 */

public class Produktverwaltung {
	
	private static Produktverwaltung produktverwaltungInstance=null;
	private ProduktDAO dao;
	
	private Produktverwaltung() {
		dao = new DatenBankProduktDAO();
	}
	
	
/**
 * 
 * @return gibt das Objekt produktverwaltungInstance (Produktverwaltung),
 *  wenn noch keines vorhanden wird eines erzeugt.
 */
    public static Produktverwaltung getInstance(){
		if(produktverwaltungInstance == null) produktverwaltungInstance = new Produktverwaltung();
		return produktverwaltungInstance;
	}
    
	
    
/**
 * 
 * @param produktname ist der Name des Produkt
 * @param preis ist der Preis des Produkts
 * @param beschreibung ist die Beschreibung des Produktes
 * @param adminID ist die ID des Admins der das Produkt anlegen will
 * @param  produktgruppeID ist die ID der Produktgruppe
 * @return true oder false 
 */
    public boolean produktAnlegen(String produktname, double preis, String beschreibung, int adminID, int produktgruppeID ) {
		return dao.speichereProdukt(new Produkt(produktname, preis, beschreibung, adminID, produktgruppeID));
    }
    
    
    
/**
 * 
 * @return Liste aller Produkte in der Datenbank
 */
    public List<Produkt> getAlleProdukt(){
		return dao.getProduktList();
		
	}
   
    
    
/**
 * 
 * @param prodID ist die ID des Produktes die gesucht wird
 * @return das gesuchte Produkt falls es in der Datenbank vorhanden ist
 */
    public Produkt getProduktByProduktID(int produktID){
 		return dao.getProduktByProduktID(produktID);
 	}
    
    

/**
 * 
 * @param produktID  ist die ID des Produktes die geloescht werden soll 
 * @return true oder false
 */
    public boolean loescheProdukt(int produktID){
		return dao.loescheProduktByID(produktID);
	}
    
    public Produkt getProduktByName(String pname){
 		return dao.getProduktByName(pname);
 	}
    
    
}