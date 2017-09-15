package de.metas.ui.web.address;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Country;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext.Builder;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.ui.web.window.model.lookup.LookupValueFilterPredicates.LookupValueFilterPredicate;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AddressCountryLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	public static final AddressCountryLookupDescriptor newInstance()
	{
		return new AddressCountryLookupDescriptor();
	}

	private static final Optional<String> LookupTableName = Optional.of(I_C_Country.Table_Name);
	private static final String CACHE_PREFIX = I_C_Country.Table_Name;
	private static final String CONTEXT_LookupTableName = I_C_Country.Table_Name;

	private final CCache<Object, LookupValuesList> allCountriesCache = CCache.newLRUCache(CACHE_PREFIX + "#All#LookupValues", 1, 0);

	private AddressCountryLookupDescriptor()
	{
	}

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return LookupTableName;
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(allCountriesCache.stats());
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
		return true; // yes, we will need some params like AD_Language, Filter, Limit etc
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		// there are no other fields on which we depend
		return ImmutableSet.of();
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
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.requiresAD_Language()
				.putFilterById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final Object id = evalCtx.getIdToFilter();
		if (id == null)
		{
			throw new IllegalStateException("No ID provided in " + evalCtx);
		}
		
		return getLookupValueById(id);
	}
	
	public LookupValue getLookupValueById(final Object idObj)
	{
		final LookupValue country = getAllCountriesById().getById(idObj);
		return Util.coalesce(country, LOOKUPVALUE_NULL);
	}

	@Override
	public Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.requiresFilterAndLimit()
				.requiresAD_Language();
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		//
		// Determine what we will filter
		final LookupValueFilterPredicate filter = evalCtx.getFilterPredicate();
		final int offset = evalCtx.getOffset(0);
		final int limit = evalCtx.getLimit(filter.isMatchAll() ? Integer.MAX_VALUE : 100);

		//
		// Get, filter, return
		return getAllCountriesById()
				.getValues()
				.stream()
				.filter(filter)
				.skip(offset)
				.limit(limit)
				.collect(LookupValuesList.collect());
	}

	private LookupValuesList getAllCountriesById()
	{
		final Object cacheKey = "ALL";
		return allCountriesCache.getOrLoad(cacheKey, () -> retriveAllCountriesById());
	}

	private LookupValuesList retriveAllCountriesById()
	{
		return Services.get(ICountryDAO.class)
				.getCountries(Env.getCtx())
				.stream()
				.map(countryRecord -> createLookupValue(countryRecord))
				.collect(LookupValuesList.collect());

	}

	private IntegerLookupValue createLookupValue(final I_C_Country countryRecord)
	{
		final int countryId = countryRecord.getC_Country_ID();
		final ITranslatableString countryName = InterfaceWrapperHelper.getModelTranslationMap(countryRecord)
				.getColumnTrl(I_C_Country.COLUMNNAME_Name, countryRecord.getName());
		return IntegerLookupValue.of(countryId, countryName);
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}
}
