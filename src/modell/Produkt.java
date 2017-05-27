/**
 * Das Package Modell dient fuer alle Akteure, Produkte und Produktgrupen die für den Ushop benoetigt werden.
 */
package modell;

/**
 * @author Mirza Talic a1367543
 *  Die Klasse Produkt,dient uns zur Erzeugung von Artikeln, welche zum Shoppen benoetigt werden
 */

public class Produkt {
	
	private int produktID;
	private String produktname;
	private double preis;
	private String beschreibung;
	private int adminID;
	private int produktgruppeID;
	
	/**
	 * 
	 * @param produktID Die ID des Produktes
	 * @param produktname Der Name des Produktes
	 * @param preis Der Preis des Produktes 
	 * @param adminID Die ID des Admins, welcher das Produkt anlegt
	 * @param produktgruppeID Ist eine ID der Kategorie in welcher das Produkt am besten identifiziert wird
	 */
	
	public Produkt(String produktname, double preis, String beschreibung, int adminID, int produktgruppeID ){
		this.produktname = produktname;
		this.preis = preis;
		this.beschreibung = beschreibung;
		this.adminID = adminID;
		this.produktgruppeID = produktgruppeID;
	}


	public Produkt(){	
		
	}
	
	/**
	 * ueberschreibt die to String Mehtode in den gewuenschten Format, sodass bei einer
	 * Ausgabe des Produktes, es leicht lesbar ist
	 */
	@Override
	public String toString(){
		return "Produkt [ProduktID="+ produktID +", Name=" + produktname + ", Preis="  + preis + ", Beschreibung=" + beschreibung  +
				", AdminID: "+ adminID +", ProduktgruppeID.: "+produktgruppeID+"]";  
						  
	}


	public int getProduktID() {
		return produktID;
	}


	public void setProduktID(int produktID) {
		this.produktID = produktID;
	}


	public String getProduktname() {
		return produktname;
	}


	public void setProduktname(String produktname) {
		this.produktname = produktname;
	}


	public double getPreis() {
		return preis;
	}


	public void setPreis(double preis) {
		this.preis = preis;
	}


	public String getBeschreibung() {
		return beschreibung;
	}


	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}


	public int getAdminID() {
		return adminID;
	}


	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}


	public int getProduktgruppeID() {
		return produktgruppeID;
	}


	public void setProduktgruppeID(int produktgruppeID) {
		this.produktgruppeID = produktgruppeID;
	}
	
	
}	
