package de.metas.location;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;

import de.metas.util.ISingletonService;

public interface ILocationBL extends ISingletonService
{

	/**
	 * Validate given Address.
	 * 
	 * @param location
	 * @throws AdempiereException
	 */
	void validatePostal(I_C_Location location) throws AdempiereException;

	/**
	 * Build address string based on given locationId and bpartner and user blocks
	 * 
	 * task FRESH-119: transaction no longer needed in this method. Provide the location as object, not by its ID. This way we avoid unnecessary extra loading of the object based on id.
	 * 
	 * @param location
	 * @param bpartner - optional parameter; we need this for language
	 * @param bPartnerBlock
	 * @param userBlock
	 * 
	 * @return address string
	 */
	String mkAddress(I_C_Location location, I_C_BPartner bpartner, String bPartnerBlock, String userBlock);

	/** Same as {@link #mkAddress(I_C_Location, String, String)} but bpartner and user blocks are empty */
	String mkAddress(I_C_Location location);

	/**
	 * location to string
	 * 
	 * @param location
	 * @return
	 */
	String toString(I_C_Location location);

	/**
	 * Duplicate given location.
	 * 
	 * @param location
	 * @return new duplicated location
	 */
	I_C_Location duplicate(I_C_Location location);
}
