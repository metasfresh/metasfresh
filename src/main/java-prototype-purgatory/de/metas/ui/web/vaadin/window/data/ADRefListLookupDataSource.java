package de.metas.ui.web.vaadin.window.data;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Container.Filter;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class ADRefListLookupDataSource implements ILookupDataSource
{
	private final Map<String, NamePair> itemsMap;

	public ADRefListLookupDataSource(final LookupDataSourceRequest request)
	{
		super();

		final Properties ctx = Env.getCtx();
		final int adReferenceId = request.getAD_Reference_Value_ID();
		final Collection<I_AD_Ref_List> adRefLists = Services.get(IADReferenceDAO.class).retrieveListItems(ctx, adReferenceId);

		final ImmutableMap.Builder<String, NamePair> itemsMap = ImmutableMap.builder();

		for (final I_AD_Ref_List adRefList : adRefLists)
		{
			final String value = adRefList.getValue();

			final I_AD_Ref_List adRefListTrl = InterfaceWrapperHelper.translate(adRefList, I_AD_Ref_List.class);
			final String name = adRefListTrl.getName();

			final ValueNamePair item = new ValueNamePair(value, name);
			itemsMap.put(value, item);
		}

		this.itemsMap = itemsMap.build();
	}

	@Override
	public boolean isHighVolume()
	{
		return false;
	}

	@Override
	public Collection<NamePair> retrieveValues(Set<Filter> filters)
	{
		if (filters != null && !filters.isEmpty())
		{
			throw new IllegalStateException("Filters are not supported");
		}

		return itemsMap.values();
	}

	@Override
	public boolean isNumericKey()
	{
		return false;
	}

	@Override
	public NamePair getNamePair(Object key)
	{
		return itemsMap.get(key);
	}
}
