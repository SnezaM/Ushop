package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modell.Bestellung;
import modell.Lieferart;
import modell.Position;
import dao.EntryToEnumeration;

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

	public static final String alleBestellungenKunde = "SELECT * FROM Bestellung WHERE kundenid = ?";
	public static final String ladeBestellungID = "SELECT * FROM Bestellung WHERE bestellungid = ?";
	public static final String ladePositionenVonBestellung = "SELECT * FROM Position WHERE bestellungid = ?";
	public static final String ladePositionID = "SELECT * FROM Position WHERE bestellungid = ? AND positionid=?";
	
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
	
	// Variable zum Verbindungsaufbau zur Datenbank
	private Connection c = null;
	// Variable fuer Enums
	private EntryToEnumeration entryToEnumeration;

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
			entryToEnumeration = new EntryToEnumeration();
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
			preparedStatement.close();
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
		try {
			PreparedStatement preparedStatement = c.prepareStatement(ladeBestellungID);
			preparedStatement.setInt(1, bestellungsID);
			ResultSet resultSet = preparedStatement.executeQuery();
			Bestellung bestellung = null;
			while (resultSet != null && resultSet.next()) {
				int id = resultSet.getInt(1);
				double preis = resultSet.getDouble(2);
				int anzahl = resultSet.getInt(3);
				boolean abgeschlossen = resultSet.getBoolean(4);
				String vermerk = resultSet.getString(5);
				String lieferartDB = resultSet.getString(6);
				String datum = resultSet.getString(7);
				int kundenid = resultSet.getInt(8);
				Lieferart lieferart = this.entryToEnumeration.entryToLieferart(lieferartDB);
				bestellung = new Bestellung(id, preis, anzahl, abgeschlossen, datum, vermerk, lieferart, kundenid);
			}
			preparedStatement.close();
			return bestellung;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#getBestellungListByKundenID(int)
	 */
	public List<Bestellung> getBestellungListByKundenID(int kundenID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(alleBestellungenKunde);
			preparedStatement.setInt(1, kundenID);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Bestellung> alleBestellungenKunde = new ArrayList<>();
			Bestellung bestellung = null;
			
			while (resultSet != null && resultSet.next()) {
				int id = resultSet.getInt(1);
				double preis = resultSet.getDouble(2);
				int anzahl = resultSet.getInt(3);
				boolean abgeschlossen = resultSet.getBoolean(4);
				String vermerk = resultSet.getString(5);
				String lieferartDB = resultSet.getString(6);
				String datum = resultSet.getString(7);
				int kundenid = resultSet.getInt(8);
				Lieferart lieferart = this.entryToEnumeration.entryToLieferart(lieferartDB);
				bestellung = new Bestellung(id, preis, anzahl, abgeschlossen, datum, vermerk, lieferart, kundenid);
				alleBestellungenKunde.add(bestellung);
			}
			preparedStatement.close();
			return alleBestellungenKunde;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#getPositionByID(int, int)
	 */
	public Position getPositionByID(int bestellungsID, int positionID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(ladePositionID);
			preparedStatement.setInt(1, bestellungsID);
			preparedStatement.setInt(2, positionID);
			ResultSet resultSet = preparedStatement.executeQuery();
			Position position = null;
			while (resultSet != null && resultSet.next()) {
				int id = resultSet.getInt(1);
				int menge = resultSet.getInt(2);
				int produktid = resultSet.getInt(5);
				double preis = resultSet.getDouble(3);
				position = new Position(id, produktid, menge, preis);
			}
			preparedStatement.close();
			return position;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#getPositionListByBestellungID(int)
	 */
	public List<Position> getPositionListByBestellungID(int bestellungsID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(ladePositionenVonBestellung);
			preparedStatement.setInt(1, bestellungsID);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Position> allePositionenBestellung = new ArrayList<>();
			Position position = null;
			
			while (resultSet != null && resultSet.next()) {
				int id = resultSet.getInt(1);
				int menge = resultSet.getInt(2);
				int produktid = resultSet.getInt(5);
				double preis = resultSet.getDouble(3);
				position = new Position(id, produktid, menge, preis);
				allePositionenBestellung.add(position);
			}
			preparedStatement.close();
			return allePositionenBestellung;
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#speichereWarenkorb(modell.Bestellung)
	 */
	public boolean speichereWarenkorb(Bestellung b) {
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
			preparedStatement.close();
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
	public boolean speichereBestellung(Bestellung bestellung, String date) {
		try {
			if(bestellung == null){
				return false;
			}
			PreparedStatement preparedStatement;
			if(bestellung.getVermerk() == null){
				preparedStatement = c.prepareStatement(updateBestellung);
				preparedStatement.setString(1, bestellung.getLieferart().toString());
				preparedStatement.setDate(2, Date.valueOf(date));
				preparedStatement.setInt(3, bestellung.getBestellungID());
			}
			else {
				preparedStatement = c.prepareStatement(updateBestellungMitVermerk);
				preparedStatement.setString(1, bestellung.getVermerk());
				preparedStatement.setString(2, bestellung.getLieferart().toString());
				preparedStatement.setDate(3, Date.valueOf(date));
				preparedStatement.setInt(4, bestellung.getBestellungID());
			}
			preparedStatement.execute();
			preparedStatement.close();
			return true;
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
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
