package org.adempiere.ad.validationRule.impl;

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
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationRuleDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_AD_Val_Rule_Dep;
import org.compiere.model.I_AD_Val_Rule_Included;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class ValidationRuleDAO implements IValidationRuleDAO
{
	@Override
	@Cached(cacheName = I_AD_Val_Rule.Table_Name)
	public I_AD_Val_Rule retriveValRule(final int adValRuleId)
	{
		if (adValRuleId <= 0)
		{
			return null;
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Val_Rule.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Val_Rule.COLUMNNAME_AD_Val_Rule_ID, adValRuleId)
				.create()
				.firstOnly(I_AD_Val_Rule.class);
	}

	@Override
	public int retrieveValRuleIdByColumnName(final String tableName, final String columnName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if (poInfo == null)
		{
			return -1;
		}

		return poInfo.getColumnValRuleId(columnName);
	}

	@Override
	public List<I_AD_Val_Rule> retrieveChildValRules(final int parentValRuleId)
	{
		if (parentValRuleId <= 0)
		{
			return ImmutableList.of();
		}
		return retrieveChildValRules0(Env.getCtx(), parentValRuleId, ITrx.TRXNAME_None);
	}

	@Cached(cacheName = I_AD_Val_Rule.Table_Name)
	/* package */ List<I_AD_Val_Rule> retrieveChildValRules0(@CacheCtx final Properties ctx, final int adValRuleId, @CacheTrx final String trxName)
	{
		final String whereClause = I_AD_Val_Rule.COLUMNNAME_AD_Val_Rule_ID + " IN (SELECT " + I_AD_Val_Rule_Included.COLUMNNAME_Included_Val_Rule_ID
				+ " FROM " + I_AD_Val_Rule_Included.Table_Name
				+ " WHERE " + I_AD_Val_Rule_Included.COLUMNNAME_AD_Val_Rule_ID
				+ " = ? )";

		final List<I_AD_Val_Rule> includedRules = new TypedSqlQuery<>(ctx, I_AD_Val_Rule.class, whereClause, trxName)
				.setParameters(adValRuleId)
				.setOnlyActiveRecords(true)
				.listImmutable(I_AD_Val_Rule.class);

		return includedRules;
	}

	@Override
	@Cached(cacheName = I_AD_Val_Rule_Dep.Table_Name)
	public Set<String> retrieveValRuleDependsOnTableNames(final int valRuleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Val_Rule_Dep.class)
				.addEqualsFilter(I_AD_Val_Rule_Dep.COLUMN_AD_Val_Rule_ID, valRuleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(valRuleDep -> valRuleDep.getAD_Table_ID())
				.map(Services.get(IADTableDAO.class)::retrieveTableName)
				.collect(ImmutableSet.toImmutableSet());
	}

}
