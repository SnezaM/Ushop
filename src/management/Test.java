package management;
import dao.MongoDBBenutzerDAO;
import dao.MongoDBProdukteDAO;
import modell.Administrator;
import modell.Kunde;
import modell.Produkt;
import modell.Produktgruppe;
public class Test {

	public static void main(String[] args) {
	//	Benutzerverwaltung b = Benutzerverwaltung.getInstance();
		//b.loescheKunden(1017);
		//b.adminAnlegen("mirza1254@gmail.org","snezta","person", "mirza1254", "mirza1245",7844.25, "1998-09-14");
		//System.out.println("Kunde ist Bereits in die Datenbank eingefügt!");
	//	System.out.println(b.getAdminByUserName("Felix1"));
		//System.out.println(b.getAdministratorListe());
		
		//MongoDBBenutzerDAO benutzer = new MongoDBBenutzerDAO();
		//MongoDBProdukteDAO produkt = new MongoDBProdukteDAO();
		//Administrator k = new Administrator("mir@gmail.org","test","test", "test", "test",7844.25, "1998-09-14");
		//System.out.println(benutzer.getBenutzerByUname("mikibi"));
		//benutzer.speichereAdmin(k);
		//System.out.println(benutzer.getBenutzerListe());
		//System.out.println(produkt.getProduktList());
		//MongoDBProdukteDAO produkt = new MongoDBProdukteDAO();
		//Produkt p = new Produkt("TestProdukt2",1232,"Super2",1,3);
		//produkt.speichereProdukt(p);
		//produkt.loescheProduktByID(1486920330);
		//Produktgruppe p = new Produktgruppe("Testname","Eine Tolle Gruppe",1);
		//System.out.println(produkt.speichereProduktgruppe(p));
		//produkt.loescheProduktgruppetByID(1364950084);
		Produktverwaltung prover = Produktverwaltung.getInstance();
		System.out.println(prover.loescheProdukt(1337680815));
		
		
		
		
		
		
	

	}
}
