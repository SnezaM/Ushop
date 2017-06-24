package management;

import java.util.List;

import dao.BenutzerDAO;
import dao.DatenBankBenutzerDAO;
import dao.DatenBankProduktDAO;
import dao.DatenBankProduktgruppeDAO;
import dao.MongoDBBenutzerDAO;
import dao.MongoDBProdukteDAO;
import dao.ProduktDAO;
import dao.ProduktgruppeDAO;
import modell.Administrator;
import modell.Benutzer;
import modell.Kunde;
import modell.Produkt;
import modell.Produktgruppe;

public class Datenmigration {

	public static void main(String[] args) {
		BenutzerDAO postgreSQL = new DatenBankBenutzerDAO();
		BenutzerDAO mongoDB = new MongoDBBenutzerDAO();
		ProduktDAO postgreSQL1 = new DatenBankProduktDAO();
		MongoDBProdukteDAO mongoDB1 = new MongoDBProdukteDAO();
		ProduktgruppeDAO postgreSQL2 = new DatenBankProduktgruppeDAO();

		@SuppressWarnings("unused")
		List<Kunde> kundenListe = postgreSQL.getKundeListe();
		List<Administrator> adminListe = postgreSQL.getAdministratorListe();

		 for(Benutzer k : kundenListe)
		 if(k instanceof Kunde)
		 mongoDB.speichereKunde((Kunde)k);
		 System.out.println("DONE ---> Kunden gespeichert!");

	/*
		 for(Benutzer m : adminListe){
		 if(m instanceof Administrator){
		 mongoDB.speichereAdmin((Administrator)m);
			 }
		

	}
		/*
		 List<Produktgruppe> produktgruppeList = postgreSQL2.getProduktgruppeList();
		 List<Produkt> produktList = postgreSQL1.getProduktList();

			
			for (Produktgruppe p : produktgruppeList) {
				if (mongoDB1.speichereProduktgruppe(p))
					System.out.println("Produktgruppe " + p.getProduktgruppeID() + " erfolgreich migriert!");
				else
					System.out.println("Fehler bei Migration von Produktgruppe " + p.getProduktgruppenname());
			}

			
			for (Produkt p : produktList) {
				if (mongoDB1.speichereProdukt(p))
					System.out.println("Produkt " + p.getProduktID() + " erfolgreich migriert!");
				else
					System.out.println("Fehler bei Migration von Produkt " + p.getProduktname());
			}
	*/
		}
		 
}

