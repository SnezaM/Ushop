/**
 * 
 */
package dao;

import java.util.List;
import java.util.UUID;

import modell.Bestellung;
import modell.Position;

/**
 * @author Katrin
 *
 */
public class DBBestellungsDAO implements BestellungsDAO {

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#addPositionToBestellung(java.util.UUID, modell.Position)
	 */
	public boolean addPositionToBestellung(UUID bestellungsID, Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#getBestellungByID(java.util.UUID)
	 */
	public Bestellung getBestellungByID(UUID bestellungsID) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#getBestellungListByKundenID(int)
	 */
	public List<Bestellung> getBestellungListByKundenID(int kundenID) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#removePositionFromBestellung(java.util.UUID, int)
	 */
	public boolean removePositionFromBestellung(UUID bestellungsID, int positionID) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#speichereBestellung(modell.Bestellung)
	 */
	public boolean speichereBestellung(Bestellung b) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#updateBestellung(modell.Bestellung)
	 */
	public boolean updateBestellung(Bestellung bestellung) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see dao.BestellungsDAO#updateMengeFromPosition(java.util.UUID, int, int)
	 */
	public boolean updateMengeFromPosition(UUID bestellungsID, int positionsID, int menge) {
		// TODO Auto-generated method stub
		return false;
	}

}
