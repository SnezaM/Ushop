package management;

import java.util.List;

import dao.BenutzerDAO;
import dao.DatenBankBenutzerDAO;
import dao.MongoDBBenutzerDAO;
import modell.Administrator;
import modell.Benutzer;
import modell.Kunde;

public class Datenmigration {

	public static void main(String[] args) {
		BenutzerDAO postgreSQL = new DatenBankBenutzerDAO();
		BenutzerDAO mongoDB = new MongoDBBenutzerDAO();

		List<Kunde> kundenListe = postgreSQL.getKundeListe();
		List<Administrator> adminListe = postgreSQL.getAdministratorListe();

		/* for(Benutzer k : kundenListe)
		 if(k instanceof Kunde)
		 mongoDB.speichereKunde((Kunde)k);
		 System.out.println("DONE ---> Kunden gespeichert!");
*/
	
		 for(Benutzer m : adminListe){
		 if(m instanceof Administrator){
		 mongoDB.speichereAdmin((Administrator)m);
			 }
		

	}

}
}
