package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import modell.Bestellung;
import modell.Position;

/**
 * <b>Klasse DBBestellungsDAO:</b>
 * <p>
 * Dient zur Kommunikation mit der Datenbank in Bezug auf Bestellungen. Hier
 * werden Befehle zum Speichern und Laden der Daten aus der Datenbank in SQL
 * angegeben. Die Klasse implementiert das Interface {@link BestellungsDAO}.
 * </p>
 * 
 * @author Katrin Rohrmueller (1309572)
 * @see {@link Bestellung}, {@link BestellungsDAO}
 *
 */
public class DBBestellungsDAO implements BestellungsDAO {

	// Variable zum Verbindungsaufbau zur Datenbank
	Connection c = null;

	public static final String alleBestellungenKunde = "SELECT * FROM Bestellung WHERE kundenid = ?";
	public static final String ladeBestellungID = "SELECT * FROM Bestellung WHERE bestellungid = ?";
	public static final String ladePositionenVonBestellung = "SELECT * FROM Position WHERE bestellungid = ?";

	public static final String speichereWarenkorb = "INSERT INTO Bestellung (bestellungid,gesamtpreis,anzahl,"
			+ "abgeschlossen,kundenid) VALUES(?,?,?,false,?)";
	public static final String speicherePosition = "INSERT INTO Position (positionid, menge, preisposition, "
			+ "bestellungid, produktid) VALUES (?, ?, ?, ?, ?)";

	//ToDo String mit '' und Datum als 'YYYY-MM-DD'
	public static final String updateBestellung = "UPDATE Bestellung SET abgeschlossen=true, lieferart=?, datum=? "
			+ "WHERE bestellungid=?";
	public static final String updateBestellungMitVermerk = "UPDATE Bestellung SET abgeschlossen=true, vermerk=?, "
			+ "lieferart=?, datum=? WHERE bestellungid=?";
	public static final String updatePositionID = "UPDATE Position SET positionid=? WHERE positionid=? AND bestellungid=?";
	public static final String updatePositionMenge = "UPDATE Position SET menge=?, preisposition=? WHERE positionid=? AND bestellungid=?";
	
	public static final String entfernePosition = "DELETE FROM Position WHERE positionid=? AND bestellungid=?";

	/**
	 * Im Konstruktor wird eine Verbindung zur Datenbank erzeugt. Mittels
	 * setAutoCommit(true) werden Uebertragungen automatisch an das DBS
	 * gesendet.
	 * 
	 */
	public DBBestellungsDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://gertsch22.ddns.net:5432/ISME_Ushop", "postgres",
					"hallodu");
			c.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verbindungsaufbau zur Datenbank nicht moeglich: (" + e.getMessage() + ")");
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#addPositionToBestellung(java.util.int,
	 *      modell.Position)
	 */
	public boolean addPositionToBestellung(int bestellungsID, Position position) {
		try {
			if(position == null){
				return false;
			}
			PreparedStatement preparedStatement = c.prepareStatement(speicherePosition);
			preparedStatement.setInt(1, position.getPostionID());
			preparedStatement.setInt(2, position.getMenge());
			preparedStatement.setDouble(3, position.getGesamtpreis());
			preparedStatement.setInt(4, bestellungsID);
			preparedStatement.setInt(5, position.getArtikel());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#getBestellungByID(java.util.int)
	 */
	public Bestellung getBestellungByID(int bestellungsID) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#getBestellungListByKundenID(int)
	 */
	public List<Bestellung> getBestellungListByKundenID(int kundenID) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#removePositionFromBestellung(java.util.int, int)
	 */
	public boolean removePositionFromBestellung(int bestellungsID, int positionID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(entfernePosition);
			preparedStatement.setInt(1, positionID);
			preparedStatement.setInt(2, bestellungsID);
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#speichereBestellung(modell.Bestellung)
	 */
	public boolean speichereBestellung(Bestellung b) {
		try {
			if(b == null){
				return false;
			}
			PreparedStatement preparedStatement = c.prepareStatement(speichereWarenkorb);
			preparedStatement.setInt(1, b.getBestellungID());
			preparedStatement.setDouble(2, b.getGesamtpreis());
			preparedStatement.setInt(3, b.getAnzahl());
			preparedStatement.setInt(4, b.getKundenID());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#updateBestellung(modell.Bestellung)
	 */
	public boolean updateBestellung(Bestellung bestellung, String date) {
		try {
			if(bestellung == null){
				return false;
			}
			if(bestellung.getVermerk() == null){
				PreparedStatement preparedStatement = c.prepareStatement(updateBestellung);
				preparedStatement.setString(1, bestellung.getLieferart().toString());
				preparedStatement.setDate(2, Date.valueOf(date));
				preparedStatement.setInt(3, bestellung.getBestellungID());
				preparedStatement.execute();
				return true;
			}
			else {
				PreparedStatement preparedStatement = c.prepareStatement(updateBestellungMitVermerk);
				preparedStatement.setString(1, bestellung.getVermerk());
				preparedStatement.setString(2, bestellung.getLieferart().toString());
				preparedStatement.setDate(3, Date.valueOf(date));
				preparedStatement.setInt(4, bestellung.getBestellungID());
				preparedStatement.execute();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#updateMengeFromPosition(java.util.int, int, int)
	 */
	public boolean updateMengeFromPosition(int bestellungsID, int positionID, int menge, double preis) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(updatePositionMenge);
			preparedStatement.setInt(1, menge);
			preparedStatement.setDouble(2, preis);
			preparedStatement.setInt(3, positionID);
			preparedStatement.setInt(4, bestellungsID);
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
