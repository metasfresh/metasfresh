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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADTableScriptValidatorDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Table_ScriptValidator;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Services;

public class ADTableScriptValidatorDAO implements IADTableScriptValidatorDAO
{
	@Cached(cacheName = I_AD_Table_ScriptValidator.Table_Name + "#All"
			, expireMinutes = Cached.EXPIREMINUTES_Never)
	/* package */Map<ArrayKey, List<I_AD_Table_ScriptValidator>> retrieveAllTableScriptValidators(final @CacheCtx Properties ctx)
	{
		//
		// Retrieve all table script validators
		final IQueryBuilder<I_AD_Table_ScriptValidator> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table_ScriptValidator.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter();
		queryBuilder.orderBy()
				.addColumn(I_AD_Table_ScriptValidator.COLUMNNAME_AD_Table_ID)
				.addColumn(I_AD_Table_ScriptValidator.COLUMNNAME_EventModelValidator)
				.addColumn(I_AD_Table_ScriptValidator.COLUMNNAME_SeqNo);
		final List<I_AD_Table_ScriptValidator> tableModelValidatorsList = queryBuilder
				.create()
				.list();

		//
		// Iterate all of them and group them by AD_Table_ID/EventModelValidator
		final Map<ArrayKey, List<I_AD_Table_ScriptValidator>> tableModelValidatorsMap = new HashMap<>();
		ArrayKey currentGroupKey = null;
		List<I_AD_Table_ScriptValidator> currentGroupValidators = null;
		for (final I_AD_Table_ScriptValidator tableModelValidator : tableModelValidatorsList)
		{
			final ArrayKey key = mkKey(tableModelValidator);
			if (currentGroupKey == null || !currentGroupKey.equals(key))
			{
				if (currentGroupKey != null)
				{
					tableModelValidatorsMap.put(currentGroupKey, Collections.unmodifiableList(currentGroupValidators));
					currentGroupKey = null;
					currentGroupValidators = null;
				}
			}

			if (currentGroupKey == null)
			{
				currentGroupKey = key;
				currentGroupValidators = new ArrayList<>();
			}

			currentGroupValidators.add(tableModelValidator);
		}
		// Add the last group
		if (currentGroupKey != null)
		{
			tableModelValidatorsMap.put(currentGroupKey, Collections.unmodifiableList(currentGroupValidators));
			currentGroupKey = null;
			currentGroupValidators = null;
		}

		return tableModelValidatorsMap;
	}

	private final ArrayKey mkKey(I_AD_Table_ScriptValidator tableModelValidator)
	{
		final int adTableId = tableModelValidator.getAD_Table_ID();
		final String eventModelValidator = tableModelValidator.getEventModelValidator();
		return mkKey(adTableId, eventModelValidator);
	}

	private final ArrayKey mkKey(int adTableId, String eventModelValidator)
	{
		return Util.mkKey(adTableId, eventModelValidator);
	}

	@Override
	public List<I_AD_Table_ScriptValidator> retrieveTableScriptValidators(final Properties ctx, final int adTableId, final String eventModelValidator)
	{
		final ArrayKey key = mkKey(adTableId, eventModelValidator);

		final Map<ArrayKey, List<I_AD_Table_ScriptValidator>> tableScriptValidatorsMap = retrieveAllTableScriptValidators(ctx);
		final List<I_AD_Table_ScriptValidator> tableScriptValidators = tableScriptValidatorsMap.get(key);
		if (tableScriptValidators == null)
		{
			return Collections.emptyList();
		}

		// NOTE: we assume this is a unmodifiable list
		return tableScriptValidators;
	}
}
