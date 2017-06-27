package management;

import java.util.LinkedList;
import java.util.List;

import dao.*;
import dao.DBBestellungsDAO;
import modell.Administrator;
import modell.Benutzer;
import modell.Bestellung;
import modell.Kunde;
import modell.Position;
import modell.Produkt;
import modell.Produktgruppe;

public class Datenmigration {

	public static void main(String[] args) {
		BenutzerDAO postgreSQL = new DatenBankBenutzerDAO();
		BenutzerDAO mongoDB = new MongoDBBenutzerDAO();
		ProduktDAO postgreSQL1 = new DatenBankProduktDAO();
		MongoDBProdukteDAO mongoDB1 = new MongoDBProdukteDAO();
		ProduktgruppeDAO postgreSQL2 = new DatenBankProduktgruppeDAO();
		BestellungsDAO postgreSQL3 = new DBBestellungsDAO();
		BestellungsDAO mongoDB3 = new MongoDBBestellungsDAO();
		
		//------------------Benutzer-Kunde-Administrator-----------------------
		@SuppressWarnings("unused")
		List<Kunde> kundenListe = postgreSQL.getKundeListe();
		List<Administrator> adminListe = postgreSQL.getAdministratorListe();
		
		/*
		for(Benutzer k : kundenListe)
			if(k instanceof Kunde)
			mongoDB.speichereKunde((Kunde)k);
		System.out.println("DONE ---> Kunden gespeichert!");
		*/
		
		/*
		for(Benutzer m : adminListe){ 
			if(m instanceof Administrator){
				mongoDB.speichereAdmin((Administrator)m); 
			}
		}
		*/
		
		// ----------------------------------------Produkte-----------------------------------------
		/*
		List<Produktgruppe> produktgruppeList = postgreSQL2.getProduktgruppeList();
		List<Produkt> produktList = postgreSQL1.getProduktList();
		*/
		
		/*
		for (Produktgruppe p : produktgruppeList) { if
		(mongoDB1.speichereProduktgruppe(p))
		System.out.println("Produktgruppe " + p.getProduktgruppeID() +
		" erfolgreich migriert!"); else
		System.out.println("Fehler bei Migration von Produktgruppe " +
		p.getProduktgruppenname()); }
		*/
		 
		
		/*
		for (Produkt p : produktList) {
			if (mongoDB1.speichereProdukt(p))
				System.out.println("Produkt " + p.getProduktID() + " erfolgreich migriert!");
			else
				System.out.println("Fehler bei Migration von Produkt " + p.getProduktname());
		}
		*/

		// -----------------------Bestellungen und Positionen--------------------------------------------
		
		/*
		List<Bestellung> bestellungKundeList = null;
				
		for(Kunde k : kundenListe){
			System.out.println("Migrationen der Bestellungen von Kunde " + k.getKundenID());
			bestellungKundeList = postgreSQL3.readBestellungenByKundenID(k.getKundenID());
			for (Bestellung b : bestellungKundeList){
				int id = b.getBestellungID();
				if(mongoDB3.createBestellung(b, k.getKundenID())){
					System.out.println("Bestellung " + id + " erfolgreich migriert!");
					System.out.println("Migration der Positionen zur zuvor erstellten Bestellung");
					List<Position> positionBestellungList = null;
					positionBestellungList = postgreSQL3.readPositionenByBestellungID(id);
					for (Position p : positionBestellungList){
						if(mongoDB3.createPosition(id, p)){
							System.out.println("Position " + p.getPostionID() + " erfolgreich migriert!");
						}
						else{
							System.out.println("Fehler bei Migration von Position " + p.getPostionID());
						}
						
					}
				}
				else{
					System.out.println("Fehler bei Migration von Bestellung " + id);
				}
			}
		}
		*/
		
		//--------------------------Warenkorb und Postionen ------------------------------------------------
		
		
		Bestellung warenkorb = null;
		
		for(Kunde k : kundenListe){
			int kundenID = k.getKundenID();
			warenkorb = postgreSQL3.getWarenkorb(kundenID);
			if(warenkorb != null){
				int warenkorbID = warenkorb.getBestellungID();
				if(mongoDB3.createWarenkorb(kundenID, warenkorb)){
					System.out.println("Warenkorb (" + warenkorbID + ") fuer Kunde " + kundenID + " erfolgreich migriert!");
					System.out.println("Migration der Positionen zum erstellten Warenkorb");
					List<Position> positionBestellungList = null;
					positionBestellungList = postgreSQL3.readPositionenByBestellungID(warenkorbID);
					for (Position p : positionBestellungList){
						if(mongoDB3.createPosition(warenkorbID, p)){
							System.out.println("Position " + p.getPostionID() + " erfolgreich migriert!");
						}
						else{
							System.out.println("Fehler bei Migration von Position " + p.getPostionID());
						}
						
					}
				}
				else{
					System.out.println("Fehler bei Migration von Warenkorb (" + warenkorbID + ") fuer Kunden " + kundenID);
				}
			}
		}
		
	}

}
