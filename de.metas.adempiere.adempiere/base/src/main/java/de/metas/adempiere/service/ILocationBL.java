package de.metas.adempiere.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;

import de.metas.adempiere.model.I_C_Postal;

public interface ILocationBL extends ISingletonService
{

	/**
	 * Validate given Address.
	 * 
	 * @param location
	 * @throws AdempiereException
	 * @see {@link #validatePostal(Properties, com.akunagroup.uk.postcode.AddressInterface)}
	 */
	void validatePostal(I_C_Location location) throws AdempiereException;

	/**
	 * Validate given Address.
	 * 
	 * Please note that if {@link I_C_Country#isPostcodeLookup()} return false, validation is skipped.
	 * 
	 * @param ctx
	 * @param address
	 */
	void validatePostal(Properties ctx, com.akunagroup.uk.postcode.AddressInterface address);

	List<com.akunagroup.uk.postcode.AddressInterface> lookupPostcode(Properties ctx, String countryCode, String city, String postal);

	I_C_Country getCountryByCode(Properties ctx, String countryCode);

	/**
	 * Check if given country/postal code exists in DPD_Route table
	 * 
	 * @param countryCode
	 * @param postal
	 * @return
	 */
	boolean checkOnDPDRoute(String countryCode, String postal);

	void registerLocal(Properties ctx, String countryCode, String city, String postal);

	/**
	 * Build address string based on given locationId and bpartner and user blocks
	 * 
	 * task FRESH-119: transaction no longer needed in this method. Provide the location as object, not by its ID. This way we avoid unnecessary extra loading of the object based on id.
	 * 
	 * @param location
	 * @param bPartnerBlock
	 * @param userBlock
	 * @return address string
	 */
	String mkAddress(I_C_Location location, String bPartnerBlock, String userBlock);

	/** Same as {@link #mkAddress(I_C_Location, String, String)} but bpartner and user blocks are empty */
	String mkAddress(I_C_Location location);

	I_C_Postal getC_Postal(com.akunagroup.uk.postcode.AddressInterface address);

	/**
	 * location to string
	 * 
	 * @param location
	 * @return
	 */
	String toString(I_C_Location location);

	/**
	 * Fetch an exact match for city. If there are multiple matches, the method will return <code>null</code>.
	 * 
	 * @param ctx
	 * @param countryCode optional iso code of the country. If emtpy, null or no country with the given iso code is found, the method returns <code>null</code>.
	 * @param city optional name of the city
	 * @param postal optional ZIP code of the postal to be retrieved
	 * @param trxName
	 * @return
	 */
	I_C_Postal fetchPostalIfUnique(Properties ctx, String countryCode, String city, String postal, String trxName);

	/**
	 * Duplicate given location.
	 * 
	 * @param location
	 * @return new duplicated location
	 */
	I_C_Location duplicate(org.compiere.model.I_C_Location location);
}
