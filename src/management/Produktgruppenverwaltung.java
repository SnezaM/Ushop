/**
 * 
 * Das Package Management beinhaltet Klassen die zu Verwaltung der Produkte und Akteure benoetigt werden
 */
package management;

import java.util.List;
import dao.DatenBankProduktgruppeDAO;
import dao.MongoDBProdukteDAO;
import dao.ProduktgruppeDAO;
import modell.Produktgruppe;

/**
 * 
 * @author Mirza Talic a1367543
 * Klasse Produktgruppenverwaltung verwaltet alle Methoden die mit dem Produktgruppen in Verbindung stehen.
 *
 */
public class Produktgruppenverwaltung {
	
	private static Produktgruppenverwaltung ProduktgruppenverwaltungInstance=null;
	private ProduktgruppeDAO dao;
	
	private Produktgruppenverwaltung() {
		//dao = new DatenBankProduktgruppeDAO();
		dao = new MongoDBProdukteDAO();
	}

	
	
	/**
	 * 
	 * @return gibt das Objekt produktverwaltungInstance (Produktverwaltung),
	 *  wenn noch keines vorhanden wird eines erzeugt.
	 */
    public static Produktgruppenverwaltung getInstance(){
		if(ProduktgruppenverwaltungInstance == null) ProduktgruppenverwaltungInstance = new Produktgruppenverwaltung();
		return ProduktgruppenverwaltungInstance;
	}
    
    /**
     * 
     * @param produktgruppenname ist der Name der Produktgruppe
     * @param bezeichnung beschreibt die Produktgruppe
     * @param adminID gibt die ID des Admins an der die Produktgruppe angegeben hat
     * @return true falls die Aktion erfolgreich abgeschlossen wurde
     */
    public boolean produkgruppeAnlegen(String produktgruppenname, String bezeichnung, int adminID) {
		return dao.speichereProduktgruppe(new Produktgruppe(produktgruppenname, bezeichnung, adminID));
    }
    
    /**
     * 
     * @return Liste aller Produktgruppen in der Datenbank
     */
        public List<Produktgruppe> getAlleProduktgruppen(){
        	return dao.getProduktgruppeList();
    		
    	}
        
        
    /**
     * 
     * @param produktgruppeID ist die ID der Produktgruppe die gesucht wird
     * @return die gesuchte Produktgruppe falls sie in der Datenbank vorhanden ist
     */
        public Produktgruppe getProdukgruppeByProduktID(int produktgruppeID){
     		return dao.getProduktgruppeByID(produktgruppeID);
     	}
        
        
    /**
     * 
     * @param produktgruppeID  ist die ID der Produktgruppe die geloescht werden soll 
     * @return true oder false
     */
        public boolean loescheProduktgruppe(int produktgruppeID){
    		return dao.loescheProduktgruppetByID(produktgruppeID);
    	}
            
    
}