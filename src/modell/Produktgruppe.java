/**
 * Das Package Modell dient fuer alle Akteure die Akionen und Verwaltungen durchfuehren koennen 
 */
package modell;


/**
 * @author Mirza Talic a1367543
 *  Die Klasse Produktgruppe,dient uns zur Erzeugung von Produktgruppe, welche fuer die
 *  Produkte benoetigt werden
 */


public class Produktgruppe {

	private int produktgruppeID;
	private String produktgruppenname;
	private String bezeichnung;
	private int adminID;
	
	
	/**
	 * 
	 * @param produktgruppenname bezeichnet den Namen der Produktgruppe
	 * @param bezeichnung beschreibt die produktgruppe
	 * @param adminID gibt die ID des Admin an, welcher die Produktgruppe angelegt hat.
	 */
	public Produktgruppe(String produktgruppenname, String bezeichnung, int adminID ){
		this.produktgruppenname = produktgruppenname;
		this.bezeichnung = bezeichnung;
		this.adminID = adminID;
	}
	
	public Produktgruppe(){}
	
	@Override
	public String toString(){
		return "Produktgruppe [produktgruppenname=" + produktgruppenname + ", Bezeichnung="  + bezeichnung + ", AdminID=" + adminID+"]";  
						  
	}

	public int getProduktgruppeID() {
		return produktgruppeID;
	}

	public void setProduktgruppeID(int produktgruppeID) {
		this.produktgruppeID = produktgruppeID;
	}

	public String getProduktgruppenname() {
		return produktgruppenname;
	}

	public void setProduktgruppenname(String produktgruppenname) {
		this.produktgruppenname = produktgruppenname;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getAdminID() {
		return adminID;
	}

	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
	
	
}
