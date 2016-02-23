package org.adempiere.ad.service.impl;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.TypedAccessor;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.util.CacheCtx;

public class ADReferenceDAO implements IADReferenceDAO
{
	@Override
	public Collection<I_AD_Ref_List> retrieveListItems(final Properties ctx, final int adReferenceId)
	{
		final Map<String, I_AD_Ref_List> itemsMap = retrieveListValuesMap(ctx, adReferenceId);
		return itemsMap.values();
	}

	@Override
	public Set<String> retrieveListValues(final Properties ctx, final int adReferenceId)
	{
		final Map<String, I_AD_Ref_List> itemsMap = retrieveListValuesMap(ctx, adReferenceId);
		return itemsMap.keySet();
	}

	/**
	 * 
	 * @param ctx
	 * @param adReferenceId
	 * @return map of "Value" to {@link I_AD_Ref_List}; never return null
	 */
	@Cached(cacheName = I_AD_Ref_List.Table_Name + "#by#" + I_AD_Ref_List.COLUMNNAME_AD_Reference_ID + "#asMap"
			, expireMinutes = Cached.EXPIREMINUTES_Never)
	/* package */Map<String, I_AD_Ref_List> retrieveListValuesMap(@CacheCtx final Properties ctx, final int adReferenceId)
	{

		final IQueryBuilder<I_AD_Ref_List> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Ref_List.class, ctx, ITrx.TRXNAME_None);

		queryBuilder.getFilters()
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_AD_Reference_ID, adReferenceId)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID);

		final List<I_AD_Ref_List> items = queryBuilder.create().list(I_AD_Ref_List.class);
		if (items.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<String, I_AD_Ref_List> itemsMap = new HashMap<String, I_AD_Ref_List>(items.size());
		for (final I_AD_Ref_List item : items)
		{
			final String value = item.getValue();
			itemsMap.put(value, item);
		}

		return ImmutableMap.copyOf(itemsMap);
	}

	@Override
	public I_AD_Ref_List retrieveListItemOrNull(final Properties ctx, final int adReferenceId, final String value)
	{
		final Map<String, I_AD_Ref_List> itemsMap = retrieveListValuesMap(ctx, adReferenceId);
		final I_AD_Ref_List item = itemsMap.get(value);
		return item;
	}

	private I_AD_Ref_List retrieveListItemTrlOrNull(final Properties ctx, final int adReferenceId, final String value)
	{
		final I_AD_Ref_List item = retrieveListItemOrNull(ctx, adReferenceId, value);
		if (item == null)
		{
			return null;
		}

		final String adLanguage = Env.getAD_Language(ctx);
		final I_AD_Ref_List itemTrl = InterfaceWrapperHelper.translate(item, I_AD_Ref_List.class, adLanguage);
		return itemTrl;
	}

	@Override
	public String retriveListName(final Properties ctx, final int adReferenceId, final String value)
	{
		final I_AD_Ref_List item = retrieveListItemOrNull(ctx, adReferenceId, value);
		if (item == null)
		{
			return value;
		}

		return item.getName();
	}

	@Override
	public boolean existListValue(final Properties ctx, final int adReferenceId, final String value)
	{
		final I_AD_Ref_List item = retrieveListItemOrNull(ctx, adReferenceId, value);
		return item != null;
	}

	@Override
	@Cached(cacheName = I_AD_Ref_List.Table_Name
			+ "#" + I_AD_Ref_List.COLUMNNAME_Name
			+ "#by#" + I_AD_Ref_List.COLUMNNAME_AD_Reference_ID
			+ "#" + I_AD_Ref_List.COLUMNNAME_Value
			+ "#AD_Language"
			, expireMinutes = Cached.EXPIREMINUTES_Never)
	public String retrieveListNameTrl(@CacheCtx final Properties ctx, final int adReferenceId, final String value)
	{
		final I_AD_Ref_List itemTrl = retrieveListItemTrlOrNull(ctx, adReferenceId, value);
		if (itemTrl == null)
		{
			return value;
		}

		final String nameTrl = itemTrl.getName();
		if (nameTrl == null)
		{
			return "";
		}
		return nameTrl;
	}

	@Override
	@Cached(cacheName = I_AD_Ref_List.Table_Name
			+ "#" + I_AD_Ref_List.COLUMNNAME_Description
			+ "#by#" + I_AD_Ref_List.COLUMNNAME_AD_Reference_ID
			+ "#" + I_AD_Ref_List.COLUMNNAME_Value
			+ "#AD_Language"
			, expireMinutes = Cached.EXPIREMINUTES_Never)
	public String retrieveListDescriptionTrl(@CacheCtx final Properties ctx, final int adReferenceId, final String value)
	{
		final I_AD_Ref_List itemTrl = retrieveListItemTrlOrNull(ctx, adReferenceId, value);
		if (itemTrl == null)
		{
			return "";
		}

		final String descriptionTrl = itemTrl.getDescription();
		if (descriptionTrl == null)
		{
			return "";
		}
		return descriptionTrl;
	}

	@Override
	public List<I_AD_Ref_List> retrieveListItemsOrderedByName(final Properties ctx, final int i)
	{
		final List<I_AD_Ref_List> docActions = new ArrayList<I_AD_Ref_List>(retrieveListItems(ctx, 135));

		// order them by name
		Collections.sort(docActions,
				new AccessorComparator<I_AD_Ref_List, String>(
						new ComparableComparator<String>(),
						new TypedAccessor<String>()
						{
							@Override
							public String getValue(final Object o)
							{
								return ((I_AD_Ref_List)o).getName();
							}
						}));
		return docActions;
	}
}
