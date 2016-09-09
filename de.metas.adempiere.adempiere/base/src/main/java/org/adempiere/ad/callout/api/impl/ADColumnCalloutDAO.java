package org.adempiere.ad.callout.api.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.callout.api.IADColumnCalloutDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.security.permissions.UIDisplayedEntityTypes;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ColumnCallout;
import org.compiere.util.Env;

import com.google.common.collect.ListMultimap;

import de.metas.adempiere.util.CacheCtx;

public class ADColumnCalloutDAO implements IADColumnCalloutDAO
{
	@Override
	@Cached(cacheName = I_AD_ColumnCallout.Table_Name + "#By#" + I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID)
	public ListMultimap<Integer, I_AD_ColumnCallout> retrieveAvailableCalloutsToRun(@CacheCtx final Properties ctx, final int adTableId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Column.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMN_AD_Table_ID, adTableId)
				//
				.andCollectChildren(I_AD_ColumnCallout.COLUMN_AD_Column_ID, I_AD_ColumnCallout.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_ColumnCallout.COLUMN_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System, Env.getAD_Client_ID(ctx))
				.addInArrayFilter(I_AD_ColumnCallout.COLUMN_AD_Org_ID, Env.CTXVALUE_AD_Org_ID_System, Env.getAD_Org_ID(ctx))
				//
				.orderBy()
				.addColumn(I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID)
				.addColumn(I_AD_ColumnCallout.COLUMNNAME_SeqNo)
				.addColumn(I_AD_ColumnCallout.COLUMNNAME_AD_ColumnCallout_ID)
				.endOrderBy()
				//
				.create()
				.stream(I_AD_ColumnCallout.class)
				// If EntityType is not displayed, skip this callout
				.filter(cc -> {
					final String entityType = cc.getEntityType();
					return Check.isEmpty(entityType, true) || UIDisplayedEntityTypes.isEntityTypeDisplayedInUIOrTrueIfNull(entityType);
				})
				// collect to: AD_Column_ID -> List of AD_ColumnCallouts
				.collect(GuavaCollectors.toImmutableListMultimap(cc -> cc.getAD_Column_ID()));
	}

	@Override
	// no cache
	public List<I_AD_ColumnCallout> retrieveAllColumnCallouts(final Properties ctx, final int adColumnId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ColumnCallout.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID, adColumnId)
				//
				.orderBy()
				.addColumn(I_AD_ColumnCallout.COLUMNNAME_SeqNo)
				.addColumn(I_AD_ColumnCallout.COLUMNNAME_AD_ColumnCallout_ID)
				.endOrderBy()
				//
				.create()
				.list(I_AD_ColumnCallout.class);
	}

	@Override
	public int retrieveColumnCalloutLastSeqNo(final Properties ctx, final int adColumnId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ColumnCallout.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID, adColumnId)
				//
				.create()
				.aggregate(I_AD_ColumnCallout.COLUMNNAME_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);

		if (lastSeqNo == null || lastSeqNo < 0)
		{
			return 0;
		}
		return lastSeqNo;
	}
}
