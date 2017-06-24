package management;
import dao.MongoDBBenutzerDAO;
import modell.Administrator;
import modell.Kunde;
public class Test {

	public static void main(String[] args) {
	//	Benutzerverwaltung b = Benutzerverwaltung.getInstance();
		//b.loescheKunden(1017);
		//b.adminAnlegen("mirza1254@gmail.org","snezta","person", "mirza1254", "mirza1245",7844.25, "1998-09-14");
		//System.out.println("Kunde ist Bereits in die Datenbank eingefügt!");
	//	System.out.println(b.getAdminByUserName("Felix1"));
		//System.out.println(b.getAdministratorListe());
		
		MongoDBBenutzerDAO benutzer = new MongoDBBenutzerDAO();
		Administrator k = new Administrator("mirza1254@gmail.org","snezta","person", "mirza1254", "mirza1245",7844.25, "1998-09-14");
		System.out.println(benutzer.getBenutzerByUname("mikibi"));
		benutzer.speichereAdmin(k);
		
		
		
	

	}
}
