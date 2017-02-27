package de.metas.ui.web.pattribute;

import java.util.List;
import java.util.Set;

import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.NamePair;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ASILookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	public static final ASILookupDescriptor of(final I_M_Attribute attribute)
	{
		final IAttributeValuesProvider attributeValuesProvider = Services.get(IAttributesBL.class).createAttributeValuesProvider(attribute);
		return new ASILookupDescriptor(attributeValuesProvider);
	}

	private static final String CONTEXT_LookupTableName = I_M_AttributeValue.Table_Name;

	private final IAttributeValuesProvider attributeValuesProvider;

	private ASILookupDescriptor(final IAttributeValuesProvider attributeValuesProvider)
	{
		super();

		Check.assumeNotNull(attributeValuesProvider, "Parameter attributeValuesProvider is not null");
		this.attributeValuesProvider = attributeValuesProvider;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(attributeValuesProvider)
				.toString();
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public boolean isHighVolume()
	{
		return attributeValuesProvider.isHighVolume();
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.list;
	}

	@Override
	public boolean hasParameters()
	{
		return false;
	}

	@Override
	public boolean isNumericKey()
	{
		return false;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return ImmutableSet.of();
	}

	@Override
	public String getCachePrefix()
	{
		return attributeValuesProvider.getCachePrefix();
	}
	
	@Override
	public boolean isCached()
	{
		return true;
	}
	
	@Override
	public List<CCacheStats> getCacheStats()
	{
		return attributeValuesProvider.getCacheStats();
	}

	public int getM_AttributeValue_ID(final LookupValue lookupValue)
	{
		if (lookupValue == null)
		{
			return -1;
		}

		final String valueKey = lookupValue.getIdAsString();
		return attributeValuesProvider.getM_AttributeValue_ID(valueKey);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName).putFilterById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final Object id = evalCtx.getIdToFilter();
		final String idStr = id == null ? null : id.toString();
		final NamePair valueNP = attributeValuesProvider.getAttributeValueOrNull(evalCtx, idStr);
		return LookupValue.fromNamePair(valueNP, LOOKUPVALUE_NULL);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.requiresFilterAndLimit();
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final LookupValueFilterPredicate filter = evalCtx.getFilterPredicate();
		final int limit = evalCtx.getLimit(Integer.MAX_VALUE);
		final int offset = evalCtx.getOffset(0);
		
		return attributeValuesProvider.getAvailableValues(evalCtx)
				.stream()
				.map(namePair -> StringLookupValue.of(namePair.getID(), namePair.getName()))
				.filter(filter)
				.skip(offset)
				.limit(limit)
				.collect(LookupValuesList.collect());
	}
}
