package de.metas.ui.web.address;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_City;
import org.compiere.model.I_C_Location;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.ILocationDAO;
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

public class AddressCityLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	private static final String CACHE_PREFIX = I_C_City.Table_Name;
	private static final String CONTEXT_LookupTableName = I_C_City.Table_Name;

	private static final String PARAM_C_Country_ID = I_C_Location.COLUMNNAME_C_Country_ID;
	private static final String PARAM_C_Region_ID = I_C_Location.COLUMNNAME_C_Region_ID;
	private static final Set<String> PARAMETERS = ImmutableSet.of(
			LookupDataSourceContext.PARAM_Filter.getName() //
			, LookupDataSourceContext.PARAM_Offset.getName() //
			, LookupDataSourceContext.PARAM_Limit.getName() //
			, PARAM_C_Country_ID //
			, PARAM_C_Region_ID //
	);

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}
	
	@Override
	public boolean isCached()
	{
		return false;
	}
	
	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of();
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
		return true;
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
		final int id = evalCtx.getIdToFilterAsInt(-1);
		if (id <= 0)
		{
			throw new IllegalStateException("No ID provided in " + evalCtx);
		}

		final I_C_City cityRecord = InterfaceWrapperHelper.create(Env.getCtx(), id, I_C_City.class, ITrx.TRXNAME_None);
		if (cityRecord == null)
		{
			return LOOKUPVALUE_NULL;
		}

		return createLookupValue(cityRecord);
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
		final int countryId = evalCtx.get_ValueAsInt(PARAM_C_Country_ID, -1);
		if (countryId <= 0)
		{
			return LookupValuesList.EMPTY;
		}

		//
		// Determine what we will filter
		final String filter = evalCtx.getFilter();
		final boolean matchAll;
		final String filterEffective;
		final int limit;
		final int offset = evalCtx.getOffset(0);
		if (filter == LookupDataSourceContext.FILTER_Any)
		{
			matchAll = true;
			filterEffective = null; // N/A
			limit = evalCtx.getLimit(Integer.MAX_VALUE);
		}
		else if (Check.isEmpty(filter, true))
		{
			return LookupValuesList.EMPTY;
		}
		else
		{
			matchAll = false;
			filterEffective = filter.trim().toUpperCase();
			limit = evalCtx.getLimit(100);
		}

		//
		final int regionId = evalCtx.get_ValueAsInt(PARAM_C_Region_ID, -1);
		final IQueryBuilder<I_C_City> queryBuilder = Services.get(ILocationDAO.class)
				.retrieveCitiesByCountryOrRegionQuery(Env.getCtx(), countryId, regionId);

		if (!matchAll)
		{
			queryBuilder.addSubstringFilter(I_C_City.COLUMNNAME_Name, filterEffective, true);
		}

		return queryBuilder
				.create()
				.setLimit(limit, offset)
				.stream(I_C_City.class)
				.map(cityRecord -> createLookupValue(cityRecord))
				.collect(LookupValuesList.collect());
	}

	private LookupValue createLookupValue(final I_C_City cityRecord)
	{
		return IntegerLookupValue.of(cityRecord.getC_City_ID(), cityRecord.getName());
	}

}
