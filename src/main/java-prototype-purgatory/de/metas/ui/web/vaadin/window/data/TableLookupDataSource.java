package de.metas.ui.web.vaadin.window.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.INamePairIterator;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.ad.validationRule.impl.SQLValidationRule;
import org.adempiere.util.Services;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.NamePair;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.SimpleStringFilter;

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

class TableLookupDataSource implements ILookupDataSource
{
	// services
	private final transient ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

	private final MLookupInfo lookupInfo;
	private final boolean highVolume;
	private final boolean numericKey;


	public TableLookupDataSource(final LookupDataSourceRequest request)
	{
		super();
		final Properties ctx = Env.getCtx();
		final int windowNo = request.getWindowNo();
		final String columnName = request.getColumnName();
		final int Column_ID = 0;
		final int displayType = request.getDisplayType();
		final int AD_Reference_Value_ID = request.getAD_Reference_Value_ID();
		final Language language = Env.getLanguage(ctx);
		final boolean IsParent = false;
		final String ValidationCode = null;
		lookupInfo = MLookupFactory.getLookupInfo(ctx, windowNo, Column_ID, displayType, language, columnName, AD_Reference_Value_ID, IsParent, ValidationCode);

		highVolume = DisplayType.Search == displayType;
		numericKey = MLookupInfo.isNumericKey(columnName);
	}
	
	@Override
	public boolean isHighVolume()
	{
		return highVolume;
	}

	@Override
	public boolean isNumericKey()
	{
		return numericKey;
	}

	@Override
	public List<NamePair> retrieveValues(final Set<Filter> filters)
	{
		final List<NamePair> result = new LinkedList<>();
		final IValidationRule filteringValidationRule = createFilteringValidationRule(filters);
		try (final INamePairIterator data = lookupDAO.retrieveLookupValues(IValidationContext.NULL, lookupInfo, filteringValidationRule))
		{
			if (!data.isValid())
			{
				return result;
			}

			for (NamePair itemModel = data.next(); itemModel != null; itemModel = data.next())
			{
				result.add(itemModel);
			}
		}

		return result;
	}

	@Override
	public NamePair getNamePair(final Object key)
	{
		return lookupDAO.retrieveLookupValue(IValidationContext.NULL, lookupInfo, key);
	}

	private IValidationRule createFilteringValidationRule(final Set<Filter> filters)
	{
		if (filters == null || filters.isEmpty())
		{
			return NullValidationRule.instance;
		}
		if (filters.size() > 1)
		{
			throw new IllegalStateException("We can handle only one filter but we got " + filters);
		}

		final Filter filter = filters.iterator().next();

		if (filter instanceof SimpleStringFilter)
		{
			final SimpleStringFilter stringFilter = (SimpleStringFilter)filter;
			final String filterString = stringFilter.getFilterString();

			final String displayColumnSql = lookupInfo.getDisplayColumnSQL();

			return new SQLValidationRule("(" + displayColumnSql + ") ILIKE " + DB.TO_STRING("%" + filterString + "%"));
		}
		else
		{
			throw new IllegalStateException("Filter type not supported: " + filter);
		}
	}
}
