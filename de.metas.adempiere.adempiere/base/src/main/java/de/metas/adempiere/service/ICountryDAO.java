/**
 *
 */
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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.model.I_C_Region;

/**
 * @author cg
 *
 */
public interface ICountryDAO extends ISingletonService
{


	/**
	 * retrieve custom user info
	 *
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	ICountryCustomInfo retriveCountryCustomInfo(Properties ctx, String trxName);

	/**
	 * Get Default Country
	 *
	 * @param ctx
	 *            context
	 * @return Country
	 */
	I_C_Country getDefault(Properties ctx);

	/**
	 * 	Get Country (cached)
	 * 	@param ctx context
	 *	@param C_Country_ID ID
	 *	@return Country
	 */
	public I_C_Country get(Properties ctx, int C_Country_ID);

	/**
	 * Return Countries as Array
	 *
	 * @param ctx
	 *            context
	 * @return countries
	 */
	public List<I_C_Country> getCountries(Properties ctx);

	List<I_C_Region> retrieveRegions(Properties ctx, int countryId);
	
	/**
	 * retrieve country sequence per org and language
	 * @param country
	 * @param org
	 * @param language
	 * @return
	 */
	public List<I_C_Country_Sequence> retrieveCountrySequence(final I_C_Country country, final I_AD_Org org, final String language);
}
