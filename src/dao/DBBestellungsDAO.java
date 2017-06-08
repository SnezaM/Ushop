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

	public static final String alleBestellungenKunde = "SELECT * FROM Bestellung WHERE kundenid = ? AND abgeschlossen=true";
	public static final String ladeBestellungID = "SELECT * FROM Bestellung WHERE bestellungid = ?";
	public static final String ladeWarenkorb = "SELECT * FROM Bestellung WHERE kundenid = ? AND abgeschlossen=false";
	public static final String ladePositionenVonBestellung = "SELECT * FROM Position WHERE bestellungid = ?";
	public static final String ladePositionID = "SELECT * FROM Position WHERE bestellungid = ? AND positionid=?";

	public static final String speichereWarenkorb = "INSERT INTO Bestellung (abgeschlossen, kundenid) VALUES(false,?)";
	public static final String speicherePosition = "INSERT INTO Position (menge, preisposition, "
			+ "bestellungid, produktid) VALUES (?, ?, ?, ?)";

	public static final String updatePriceBestellung = "UPDATE Bestellung SET gesamtpreis=? WHERE bestellungid = ?";
	public static final String updateBestellung = "UPDATE Bestellung SET abgeschlossen=true, lieferart=?, datum=? "
			+ "WHERE bestellungid=?";
	public static final String updateBestellungMitVermerk = "UPDATE Bestellung SET abgeschlossen=true, vermerk=?, "
			+ "lieferart=?, datum=? WHERE bestellungid=?";
	public static final String updatePositionMenge = "UPDATE Position SET menge=?, preisposition=? WHERE positionid=? AND bestellungid=?";

	public static final String entfernePosition = "DELETE FROM Position WHERE positionid=? AND bestellungid=?";
	public static final String entferneBestellung = "DELETE FROM Bestellung WHERE bestellungid=?";

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
			System.out.println(e);
			System.out.println("Verbindungsaufbau zur Datenbank nicht moeglich: (" + e.getMessage() + ")");
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#speichereWarenkorb(modell.Bestellung)
	 */
	public boolean createWarenkorb(int kundenID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(speichereWarenkorb);
			preparedStatement.setInt(1, kundenID);
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#addPositionToBestellung(java.util.int,
	 *      modell.Position)
	 */
	public boolean createPosition(int bestellungsID, Position position) {
		try {
			if (position == null) {
				return false;
			}
			PreparedStatement preparedStatement = c.prepareStatement(speicherePosition);
			preparedStatement.setInt(1, position.getMenge());
			preparedStatement.setDouble(2, position.getGesamtpreis());
			preparedStatement.setInt(3, bestellungsID);
			preparedStatement.setInt(4, position.getArtikel());
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#deleteBestellung(int)
	 */
	public boolean deleteBestellung(int bestellungsID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(entferneBestellung);
			preparedStatement.setInt(1, bestellungsID);
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#removePositionFromBestellung(java.util.int, int)
	 */
	public boolean deletePosition(int bestellungsID, int positionID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(entfernePosition);
			preparedStatement.setInt(1, positionID);
			preparedStatement.setInt(2, bestellungsID);
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
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
				boolean abgeschlossen = resultSet.getBoolean(3);
				String vermerk = resultSet.getString(4);
				String lieferartDB = resultSet.getString(5);
				String datum = resultSet.getString(6);
				Lieferart lieferart = this.entryToEnumeration.entryToLieferart(lieferartDB);
				bestellung = new Bestellung(id, preis, abgeschlossen, datum, vermerk, lieferart);
			}
			preparedStatement.close();
			return bestellung;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
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
			System.out.println(e);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#getWarenkorb(int)
	 */
	public Bestellung getWarenkorb(int kundenID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(ladeWarenkorb);
			preparedStatement.setInt(1, kundenID);
			ResultSet resultSet = preparedStatement.executeQuery();
			Bestellung bestellung = null;
			while (resultSet != null && resultSet.next()) {
				int id = resultSet.getInt(1);
				double preis = resultSet.getDouble(2);
				boolean abgeschlossen = false;
				bestellung = new Bestellung(id, preis, abgeschlossen, null, null, Lieferart.Standardversand);
			}
			preparedStatement.close();
			return bestellung;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#readBestellungenByKundenID(int)
	 */
	public List<Bestellung> readBestellungenByKundenID(int kundenID) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement(alleBestellungenKunde);
			preparedStatement.setInt(1, kundenID);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Bestellung> alleBestellungenKunde = new ArrayList<>();
			Bestellung bestellung = null;

			while (resultSet != null && resultSet.next()) {
				int id = resultSet.getInt(1);
				double preis = resultSet.getDouble(2);
				boolean abgeschlossen = resultSet.getBoolean(3);
				String vermerk = resultSet.getString(4);
				String lieferartDB = resultSet.getString(5);
				String datum = resultSet.getString(6);
				Lieferart lieferart = this.entryToEnumeration.entryToLieferart(lieferartDB);
				bestellung = new Bestellung(id, preis, abgeschlossen, datum, vermerk, lieferart);
				alleBestellungenKunde.add(bestellung);
			}
			preparedStatement.close();
			return alleBestellungenKunde;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#readPositionenByBestellungID(int)
	 */
	public List<Position> readPositionenByBestellungID(int bestellungsID) {
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
			System.out.println(e);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#speichereBestellung(modell.Bestellung)
	 */
	public boolean updateWarenkorbToBestellung(Bestellung bestellung, String date) {
		try {
			if (bestellung == null) {
				return false;
			}
			PreparedStatement preparedStatement;
			if (bestellung.getVermerk() == null) {
				preparedStatement = c.prepareStatement(updateBestellung);
				preparedStatement.setString(1, bestellung.getLieferart().toString());
				preparedStatement.setDate(2, Date.valueOf(date));
				preparedStatement.setInt(3, bestellung.getBestellungID());
			} else {
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
			System.out.println(e);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see dao.BestellungsDAO#updateMengeFromPosition(java.util.int, int, int)
	 */
	public boolean updatePosition(int bestellungsID, int positionID, int menge, double preis) {
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
			System.out.println(e);
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#updatePriceBestellung(int, double)
	 */
	public boolean updatePriceBestellung(int bestellungsID, double wert){
		try {
			PreparedStatement preparedStatement = c.prepareStatement(updatePriceBestellung);
			preparedStatement.setDouble(1, wert);
			preparedStatement.setInt(2, bestellungsID);
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

}
