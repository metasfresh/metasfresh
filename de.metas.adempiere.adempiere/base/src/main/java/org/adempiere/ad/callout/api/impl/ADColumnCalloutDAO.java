package org.adempiere.ad.callout.api.impl;

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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.callout.api.IADColumnCalloutDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_ColumnCallout;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;

public class ADColumnCalloutDAO implements IADColumnCalloutDAO
{
	// private static final transient CLogger logger = CLogger.getCLogger(ADColumnCalloutDAO.class);

	@Override
	public List<I_AD_ColumnCallout> retrieveActiveColumnCallouts(final Properties ctx, final int adColumnId)
	{
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);

		final List<I_AD_ColumnCallout> calloutsAll = retrieveAllColumnCallouts(ctx, adColumnId);
		final List<I_AD_ColumnCallout> calloutsActive = new ArrayList<I_AD_ColumnCallout>(calloutsAll.size());

		for (final I_AD_ColumnCallout callout : calloutsAll)
		{
			if (!callout.isActive())
			{
				continue;
			}

			final int calloutClientId = callout.getAD_Client_ID();
			if (calloutClientId != Env.CTXVALUE_AD_Client_ID_System && calloutClientId != AD_Client_ID)
			{
				continue;
			}

			final int calloutOrgId = callout.getAD_Org_ID();
			if (calloutOrgId != Env.CTXVALUE_AD_Org_ID_System && calloutOrgId != AD_Org_ID)
			{
				continue;
			}

			//
			// If EntityType is not displayed, skip this callout
			if (!EntityTypesCache.instance.isDisplayedInUI(callout.getEntityType()))
			{
				continue;
			}

			calloutsActive.add(callout);
		}

		return calloutsActive;
	}

	@Override
	@Cached(cacheName = I_AD_ColumnCallout.Table_Name + "#By#" + I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID)
	public List<I_AD_ColumnCallout> retrieveAllColumnCallouts(
			@CacheCtx final Properties ctx,
			final int adColumnId)
	{
		final IQueryBuilder<I_AD_ColumnCallout> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ColumnCallout.class)
				.setContext(ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID, adColumnId);

		queryBuilder.orderBy()
				.addColumn(I_AD_ColumnCallout.COLUMNNAME_SeqNo)
				.addColumn(I_AD_ColumnCallout.COLUMNNAME_AD_ColumnCallout_ID);

		return queryBuilder.create()
				.list(I_AD_ColumnCallout.class);
	}

	@Override
	public int retrieveColumnCalloutLastSeqNo(final Properties ctx, final int adColumnId)
	{
		final IQueryBuilder<I_AD_ColumnCallout> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ColumnCallout.class)
				.setContext(ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID, adColumnId);

		final Integer lastSeqNo = queryBuilder.create()
				.setOnlyActiveRecords(true)
				.aggregate(I_AD_ColumnCallout.COLUMNNAME_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);

		if (lastSeqNo == null || lastSeqNo < 0)
		{
			return 0;
		}

		return lastSeqNo;
	}
}
