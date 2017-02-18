package org.adempiere.pricing.api.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;

public class PriceListDAO implements IPriceListDAO
{
	private static final transient Logger logger = LogManager.getLogger(PriceListDAO.class);


	@Override
	@Cached(cacheName = I_M_PriceList.Table_Name + "#By#M_PriceList_ID")
	public I_M_PriceList retrievePriceList(@CacheCtx final Properties ctx, final int priceListId)
	{
		if (priceListId <= 0)
		{
			return null;
		}

		final I_M_PriceList priceList = InterfaceWrapperHelper.create(ctx, priceListId, I_M_PriceList.class, ITrx.TRXNAME_None);
		return priceList;
	}

	@Override
	public Iterator<I_M_ProductPrice> retrieveAllProductPricesOrderedBySeqNOandProductName(final I_M_PriceList_Version plv)
	{
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice.class, plv)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, plv.getM_PriceList_Version_ID());

		queryBuilder.orderBy()
				.addColumn(org.compiere.model.I_M_ProductPrice.COLUMNNAME_SeqNo)
				.addColumn(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_Product_ID)
				.addColumn(org.compiere.model.I_M_ProductPrice.COLUMNNAME_MatchSeqNo);
				

		return queryBuilder.create()
				.iterate(I_M_ProductPrice.class);
	}

	@Override
	public Iterator<I_M_PriceList> retrievePriceLists(final I_M_PricingSystem pricingSystem, final I_C_Country country, final boolean isSOTrx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_PriceList> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_PriceList.class, pricingSystem)
				.addOnlyActiveRecordsFilter();

		final ICompositeQueryFilter<I_M_PriceList> filters = queryBL.<I_M_PriceList> createCompositeQueryFilter(I_M_PriceList.class)
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_M_PricingSystem_ID, pricingSystem.getM_PricingSystem_ID())
				.addEqualsFilter(org.compiere.model.I_M_PriceList.COLUMNNAME_IsSOPriceList, isSOTrx);

		final ICompositeQueryFilter<I_M_PriceList> countryFilter = queryBL.<I_M_PriceList> createCompositeQueryFilter(I_M_PriceList.class)
				.setJoinOr()
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Country_ID, country.getC_Country_ID())
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Country_ID, null);
		filters
				.addFilter(countryFilter);

		return queryBuilder
				.filter(filters)
				.orderBy()
				.addColumn(I_M_PriceList.COLUMNNAME_C_Country_ID, Direction.Ascending, Nulls.Last).endOrderBy()
				.create()
				.iterate(I_M_PriceList.class);
	}

	@Override
	public I_M_PriceList_Version retrievePriceListVersionOrNull(
			final org.compiere.model.I_M_PriceList priceList,
			final Date date,
			final Boolean processed)
	{
		Check.assumeNotNull(priceList, "param 'priceList' not null", priceList);
		Check.assumeNotNull(priceList, "param 'date' not null", date);

		final Properties ctx = InterfaceWrapperHelper.getCtx(priceList);

		final int priceListId = priceList.getM_PriceList_ID();
		final I_M_PriceList_Version plv = retrieveLastVersion(ctx, priceListId, date, processed);

		return plv;
	}

	@Cached(cacheName = I_M_PriceList_Version.Table_Name + "#By#M_PriceList_ID#Date#Processed")
	/* package */ I_M_PriceList_Version retrieveLastVersion(@CacheCtx final Properties ctx,
			final int priceListId,
			final Date date,
			final Boolean processed)
	{
		Check.assumeNotNull(date, "Param 'date' is not null; other params: priceListId={}, processed={}, ctx={}", priceListId, processed, ctx);

		final IQueryBuilder<I_M_PriceList_Version> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList_Version.class, ctx, ITrx.TRXNAME_None)
				// Same pricelist
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, priceListId)
				// valid from must be before the date we need it
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						CompareQueryFilter.Operator.LESS_OR_EQUAL,
						new Timestamp(date.getTime()),
						DateTruncQueryFilterModifier.DAY)

				// active
				.addOnlyActiveRecordsFilter();

		if (processed != null)
		{
			queryBuilder.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_Processed, processed);
		}

		// Order the version by validFrom, descending.
		queryBuilder.orderBy()
				.addColumn(I_M_PriceList_Version.COLUMNNAME_ValidFrom, false);

		final IQuery<I_M_PriceList_Version> query = queryBuilder.create();
		final I_M_PriceList_Version result = query.first();
		if (result == null)
		{
			logger.warn("None found M_PriceList_ID=" + priceListId + ", date=" + date + ", query=" + query);
		}
		return result;
	}

	@Override
	public I_M_PriceList_Version retrieveNextVersionOrNull(final I_M_PriceList_Version plv)
	{
		// we want the PLV with the lowest ValidFrom that is just greater than plv's
		final Operator validFromOperator = Operator.GREATER;
		final boolean orderAscending = true;

		return retrievePreviousOrNext(plv, validFromOperator, orderAscending);
	}

	@Override
	public I_M_PriceList_Version retrievePreviousVersionOrNull(final I_M_PriceList_Version plv)
	{
		// we want the PLV with the highest ValidFrom that is just less than plv's
		final Operator validFromOperator = Operator.LESS;
		final boolean orderAscending = false; // i.e. descending
		return retrievePreviousOrNext(plv, validFromOperator, orderAscending);
	}

	private I_M_PriceList_Version retrievePreviousOrNext(final I_M_PriceList_Version plv,
			final Operator validFromOperator,
			final boolean orderAscending)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PriceList_Version.class, plv)
				// active
				.addOnlyActiveRecordsFilter()
				// same price list
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, plv.getM_PriceList_ID())
				// same processed value
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_Processed, true)
				// valid from must be after the given PLV's validFrom date
				.addCompareFilter(I_M_PriceList_Version.COLUMNNAME_ValidFrom, validFromOperator, plv.getValidFrom())
				// by validFrom, ascending.
				.orderBy()
				.addColumn(I_M_PriceList_Version.COLUMNNAME_ValidFrom, orderAscending)
				.endOrderBy()
				.create()
				.first();
	}
	
	@Override
	public int retrieveNextMatchSeqNo(final I_M_ProductPrice productPrice)
	{
		Integer lastMatchSeqNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class, productPrice)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, productPrice.getM_PriceList_Version_ID())
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productPrice.getM_Product_ID())
				.addNotEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID, productPrice.getM_ProductPrice_ID())
				.create()
				.aggregate(I_M_ProductPrice.COLUMN_MatchSeqNo, IQuery.AGGREGATE_MAX, Integer.class);

		if (lastMatchSeqNo == null)
		{
			lastMatchSeqNo = 0;
		}

		final int nextMatchSeqNo = (lastMatchSeqNo / 10) * 10 + 10;
		return nextMatchSeqNo;
	}

}
