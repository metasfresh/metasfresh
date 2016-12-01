package de.metas.ui.web.address;

import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Country;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext.Builder;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class AddressCountryLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	private static final String CACHE_PREFIX = I_C_Country.Table_Name;
	private static final String CONTEXT_LookupTableName = I_C_Country.Table_Name;

	private static final Set<String> PARAMETERS = ImmutableSet.of(
			LookupDataSourceContext.PARAM_Filter.getName() //
			, LookupDataSourceContext.PARAM_Offset.getName() //
			, LookupDataSourceContext.PARAM_Limit.getName() //
	);

	private final CCache<Object, LookupValuesList> allCountriesCache = CCache.newLRUCache(I_C_Country.Table_Name + "#All#LookupValues", 1, 0);

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}

	@Override
	public boolean isHighVolume()
	{
		return true;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.lookup;
	}

	@Override
	public boolean hasParameters()
	{
		return !PARAMETERS.isEmpty();
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return PARAMETERS;
	}

	@Override
	public boolean isNumericKey()
	{
		return true;
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName).putFilterById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final Object id = evalCtx.getIdToFilter();
		if (id == null)
		{
			throw new IllegalStateException("No ID provided in " + evalCtx);
		}

		final LookupValue country = getAllCountriesById().getById(id);
		return Util.coalesce(country, LOOKUPVALUE_NULL);
	}

	@Override
	public Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.setRequiredParameters(PARAMETERS);
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		//
		// Determine what we will filter
		final String filter = evalCtx.getFilter();
		final boolean matchAll;
		final String filterUC;
		final int limit;
		final int offset = evalCtx.getOffset(0);
		if(filter == LookupDataSourceContext.FILTER_Any)
		{
			matchAll = true;
			filterUC = null; // N/A
			limit = evalCtx.getLimit(Integer.MAX_VALUE);
		}
		else if (Check.isEmpty(filter, true))
		{
			return LookupValuesList.EMPTY;
		}
		else
		{
			matchAll = false;
			filterUC = filter.trim().toUpperCase();
			limit = evalCtx.getLimit(100);
		}

		//
		// Get, filter, return
		return getAllCountriesById()
				.getValues()
				.stream()
				.filter(country -> matchAll || matchesFilter(country, filterUC))
				.skip(offset)
				.limit(limit)
				.collect(LookupValuesList.collect());
	}

	private LookupValuesList getAllCountriesById()
	{
		final int key = 1; // dummy
		return allCountriesCache.getOrLoad(key, () -> retriveAllCountriesById());
	}

	private LookupValuesList retriveAllCountriesById()
	{
		return Services.get(ICountryDAO.class)
				.getCountries(Env.getCtx())
				.stream()
				.map(countryRecord -> IntegerLookupValue.of(countryRecord.getC_Country_ID(), countryRecord.getName()))
				.collect(LookupValuesList.collect());

	}

	private final boolean matchesFilter(final LookupValue country, final String filterUC)
	{
		final String displayName = country.getDisplayName();
		if (Check.isEmpty(displayName))
		{
			return false;
		}

		final String displayNameUC = displayName.trim().toUpperCase();

		return displayNameUC.contains(filterUC);
	}

}
