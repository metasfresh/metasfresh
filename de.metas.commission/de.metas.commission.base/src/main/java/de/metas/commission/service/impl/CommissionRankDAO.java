package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.service.ICommissionRankDAO;

public class CommissionRankDAO implements ICommissionRankDAO
{
	@Override
	@Cached
	public I_C_AdvCommissionSalaryGroup retrieve(
			final @CacheCtx Properties ctx,
			final I_C_AdvComRankCollection rankcollection,
			final String value,
			final String trxName)
	{
		final String whereClause =
				I_C_AdvCommissionSalaryGroup.COLUMNNAME_C_AdvComRankCollection_ID + "=? AND " + I_C_AdvCommissionSalaryGroup.COLUMNNAME_Value + "=?";

		final I_C_AdvCommissionSalaryGroup result =
				new Query(ctx, I_C_AdvCommissionSalaryGroup.Table_Name, whereClause, trxName)
						.setParameters(rankcollection.getC_AdvComRankCollection_ID(), value)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.firstOnly(I_C_AdvCommissionSalaryGroup.class);

		if (result == null)
		{
			throw CommissionException.inconsistentConfig(
					"Missing salary group '" + value + "'; AD_Client_ID="
							+ Env.getAD_Client_ID(ctx) + "; AD_Org_ID="
							+ Env.getAD_Org_ID(ctx), null);
		}
		return result;
	}

	@Override
	@Cached
	public List<I_C_AdvCommissionSalaryGroup> retrieveForCollection(
			final @CacheCtx Properties ctx,
			final I_C_AdvComRankCollection rankCollection,
			final String trxName)
	{
		final String whereClause = I_C_AdvCommissionSalaryGroup.COLUMNNAME_C_AdvComRankCollection_ID + "=?";

		final List<I_C_AdvCommissionSalaryGroup> result =
				new Query(ctx, I_C_AdvCommissionSalaryGroup.Table_Name, whereClause, trxName)
						.setParameters(rankCollection.getC_AdvComRankCollection_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(I_C_AdvCommissionSalaryGroup.COLUMNNAME_SeqNo)
						.list(I_C_AdvCommissionSalaryGroup.class);

		return result;
	}

	/**
	 * Retrieves the salary group with the given value as well as the groups that are above it (according to their seqno).
	 * 
	 * @param ctx
	 * @param value
	 * @param adOrgId
	 * @param trxName
	 * @return
	 */
	@Override
	@Cached
	public List<I_C_AdvCommissionSalaryGroup> retrieveGroupAndBetter(
			final @CacheCtx Properties ctx,
			final I_C_AdvComRankCollection rankcollection,
			final String value,
			final @CacheIgnore String trxName)
	{
		assert value != null;

		final List<I_C_AdvCommissionSalaryGroup> result = new ArrayList<I_C_AdvCommissionSalaryGroup>();

		final I_C_AdvCommissionSalaryGroup lowestSG = retrieve(ctx, rankcollection, value, trxName);

		if (lowestSG == null)
		{
			return result;
		}

		result.add(lowestSG);

		final String whereClause = I_C_AdvCommissionSalaryGroup.COLUMNNAME_C_AdvComRankCollection_ID + "=? AND " + I_C_AdvCommissionSalaryGroup.COLUMNNAME_SeqNo + ">?";

		final Object[] parameters = { rankcollection.getC_AdvComRankCollection_ID(), lowestSG.getSeqNo() };

		final String orderBy = I_C_AdvCommissionSalaryGroup.COLUMNNAME_SeqNo;

		final List<I_C_AdvCommissionSalaryGroup> higherSGs =
				new Query(ctx, I_C_AdvCommissionSalaryGroup.Table_Name, whereClause, trxName)
						.setParameters(parameters)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(orderBy)
						.list(I_C_AdvCommissionSalaryGroup.class);

		result.addAll(higherSGs);

		return result;
	}

	@Override
	public I_C_AdvCommissionSalaryGroup retrieveLowest(
			final Properties ctx, final I_C_AdvComRankCollection rankCollection, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		
		final IQueryFilter<I_C_AdvCommissionSalaryGroup> filter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionSalaryGroup.class)
				.addEqualsFilter(I_C_AdvCommissionSalaryGroup.COLUMNNAME_C_AdvComRankCollection_ID, rankCollection.getC_AdvComRankCollection_ID())
				.addOnlyActiveRecordsFilter();

		final IQueryBuilder<I_C_AdvCommissionSalaryGroup> queryBuilder = queryBL.createQueryBuilder(I_C_AdvCommissionSalaryGroup.class, ctx, trxName)
				.filter(filter);

		queryBuilder.orderBy()
				.addColumn(I_C_AdvCommissionSalaryGroup.COLUMNNAME_SeqNo);

		return queryBuilder
				.create()
				.first(I_C_AdvCommissionSalaryGroup.class);

		// OLD Version:
		// final String where = I_C_AdvCommissionSalaryGroup.COLUMNNAME_C_AdvComRankCollection_ID + "=?";
		// final String orderBy = I_C_AdvCommissionSalaryGroup.COLUMNNAME_SeqNo;
		// return new Query(ctx, I_C_AdvCommissionSalaryGroup.Table_Name, where, trxName)
		// .setParameters(rankCollection.getC_AdvComRankCollection_ID())
		// .setClient_ID()
		// .setOnlyActiveRecords(true)
		// .setOrderBy(orderBy)
		// .first(I_C_AdvCommissionSalaryGroup.class);
	}
}
