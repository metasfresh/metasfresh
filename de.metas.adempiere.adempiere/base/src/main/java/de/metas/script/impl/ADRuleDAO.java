package de.metas.script.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Rule;
import org.compiere.util.CCache;

import de.metas.script.IADRuleDAO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ADRuleDAO implements IADRuleDAO
{
	/** Cache by AD_Rule_ID */
	private static CCache<Integer, I_AD_Rule> s_cacheById = new CCache<>(I_AD_Rule.Table_Name + "#by#AD_Rule_ID", 20);
	private static CCache<String, I_AD_Rule> s_cacheByValue = new CCache<>(I_AD_Rule.Table_Name + "#by#Value", 20);

	@Override
	public I_AD_Rule retrieveById(final Properties ctx, final int AD_Rule_ID)
	{
		if (AD_Rule_ID <= 0)
		{
			return null;
		}
		return s_cacheById.getOrLoad(AD_Rule_ID, () -> {
			final I_AD_Rule rule = InterfaceWrapperHelper.create(ctx, AD_Rule_ID, I_AD_Rule.class, ITrx.TRXNAME_None);
			s_cacheByValue.put(rule.getValue(), rule);
			return rule;
		});
	}

	@Override
	public I_AD_Rule retrieveByValue(final Properties ctx, final String ruleValue)
	{
		if (ruleValue == null)
		{
			return null;
		}

		return s_cacheByValue.getOrLoad(ruleValue, () -> {
			final I_AD_Rule rule = retrieveByValue_NoCache(ctx, ruleValue);
			if (rule != null)
			{
				s_cacheById.put(rule.getAD_Rule_ID(), rule);
			}
			return rule;
		});
	}

	private final I_AD_Rule retrieveByValue_NoCache(final Properties ctx, final String ruleValue)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Rule.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Rule.COLUMNNAME_Value, ruleValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_AD_Rule.class);
	}

	@Override
	public List<I_AD_Rule> retrieveByEventType(final Properties ctx, final String eventType)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Rule.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Rule.COLUMNNAME_EventType, eventType)
				.create()
				.list(I_AD_Rule.class);
	}

}
