/**
 * 
 */
package de.metas.adempiere.service.impl;

import java.util.Comparator;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_C_Country;
import org.junit.Ignore;

import de.metas.adempiere.service.ICountryCustomInfo;

/**
 * @author cg
 * 
 */

@Ignore
public class PlainCountryDAO extends CountryDAO
{
	@Override
	public ICountryCustomInfo retriveCountryCustomInfo(Properties ctx, String trxName)
	{
		return null;
	}

	@Override
	public List<I_C_Country> getCountries(Properties ctx)
	{
		return POJOLookupMap.get().getRecords(I_C_Country.class, country -> true);
	}

	@Override
	public I_C_Country getDefault(final Properties ctx)
	{
		// NOTE: we need to override the default implementation because that one is assuming that we have AD_Client records... and in some tests we don't have it.
		// So for now, we are just returning first country which was created.
		final Comparator<I_C_Country> orderByComparator = Comparator.comparing(I_C_Country::getC_Country_ID);
		return POJOLookupMap.get().getFirst(I_C_Country.class, country -> true, orderByComparator);
	}
}
