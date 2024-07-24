/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.pricing.service.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.common.util.Check;
import de.metas.currency.ICurrencyBL;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.AddProductPriceRequest;
import de.metas.pricing.service.CopyProductPriceRequest;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.PriceListsCollection;
import de.metas.pricing.service.ProductPriceCreateRequest;
import de.metas.pricing.service.UpdateProductPriceRequest;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class PriceListDAO implements IPriceListDAO
{
	private static final Logger logger = LogManager.getLogger(PriceListDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_PricingSystem getPricingSystemById(final PricingSystemId pricingSystemId)
	{
		if (pricingSystemId == null)
		{
			return null;
		}

		return loadOutOfTrx(pricingSystemId, I_M_PricingSystem.class);
	}

	@NonNull
	@Override
	public PricingSystemId getPricingSystemIdByValue(@NonNull final String value)
	{
		final PricingSystemId pricingSystemId = getPricingSystemIdByValueOrNull(value);

		if (pricingSystemId == null)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@ (@Value@=" + value + ")");
		}

		return pricingSystemId;
	}

	@Nullable
	@Override
	public PricingSystemId getPricingSystemIdByValueOrNull(@NonNull final String value)
	{
		final int pricingSystemId = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_PricingSystem.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_PricingSystem.COLUMNNAME_Value, value)
				.orderByDescending(I_M_PricingSystem.COLUMNNAME_AD_Client_ID)
				.create()
				.firstId();

		return PricingSystemId.ofRepoIdOrNull(pricingSystemId);
	}

	@Override
	public I_M_PriceList getById(final int priceListId)
	{
		return getById(PriceListId.ofRepoIdOrNull(priceListId));
	}

	@Override
	public I_M_PriceList getById(final PriceListId priceListId)
	{
		if (priceListId == null)
		{
			return null;
		}

		return loadOutOfTrx(priceListId, I_M_PriceList.class);
	}

	@Override
	public I_M_PriceList_Version getPriceListVersionById(final PriceListVersionId priceListVersionId)
	{
		return loadOutOfTrx(priceListVersionId, I_M_PriceList_Version.class);
	}

	@Override
	public I_M_PriceList_Version getPriceListVersionByIdInTrx(final PriceListVersionId priceListVersionId)
	{
		return load(priceListVersionId, I_M_PriceList_Version.class);
	}

	@Override
	public Stream<I_M_ProductPrice> retrieveProductPrices(
			@NonNull final PriceListVersionId priceListVersionId,
			final Set<ProductId> productIdsToExclude)
	{
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, priceListVersionId);

		if (productIdsToExclude != null && !productIdsToExclude.isEmpty())
		{
			queryBuilder.addNotInArrayFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productIdsToExclude);
		}

		return queryBuilder
				.create()
				.iterateAndStream();
	}

	@Override
	public ImmutableList<I_M_ProductPrice> retrieveProductPrices(
			@NonNull final PriceListVersionId priceListVersionId,
			final ProductId productId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, priceListVersionId)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Iterator<I_M_ProductPrice> retrieveProductPricesOrderedBySeqNoAndProductIdAndMatchSeqNo(@NonNull final PriceListVersionId priceListVersionId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, priceListVersionId)
				.orderBy(org.compiere.model.I_M_ProductPrice.COLUMNNAME_SeqNo)
				.orderBy(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_Product_ID)
				.orderBy(org.compiere.model.I_M_ProductPrice.COLUMNNAME_MatchSeqNo)
				.create()
				.iterate(I_M_ProductPrice.class);
	}

	@Override
	public PriceListId retrievePriceListIdByPricingSyst(
			@Nullable final PricingSystemId pricingSystemId,
			final BPartnerLocationAndCaptureId bpartnerLocationId,
			final SOTrx soTrx)
	{
		if (pricingSystemId == null)
		{
			return null;
		}

		// In case we are dealing with Pricing System None, return the PriceList none
		if (pricingSystemId.isNone())
		{
			return PriceListId.NONE;
		}

		assumeNotNull(bpartnerLocationId, "If the given pricingSystemId={} is not null and not-none, then bpartnerLocationId may not be null", pricingSystemId);
		final CountryId countryId = Services.get(IBPartnerBL.class).getCountryId(bpartnerLocationId);

		final List<I_M_PriceList> priceLists = retrievePriceLists(pricingSystemId, countryId, soTrx);

		return !priceLists.isEmpty() ? PriceListId.ofRepoId(priceLists.get(0).getM_PriceList_ID()) : null;
	}

	@Override
	public boolean isProductPriceExistsInSystem(final PricingSystemId pricingSystemId, final SOTrx soTrx, final ProductId productId)
	{
		final ImmutableSet<PriceListId> priceListIds = retrievePriceListsCollectionByPricingSystemId(pricingSystemId).filterAndListIds(soTrx);
		return queryBL.createQueryBuilder(I_M_PriceList_Version.class)
				.addInArrayFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID,priceListIds)
				.andCollectChildren(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_IsActive, true)
				.create()
				.anyMatch();
	}

	@Override
	public PriceListId retrievePriceListIdByPricingSyst(
			@Nullable final PricingSystemId pricingSystemId,
			final CountryId countryId,
			final SOTrx soTrx)
	{
		if (pricingSystemId == null)
		{
			return null;
		}

		// In case we are dealing with Pricing System None, return the PriceList none
		if (pricingSystemId.isNone())
		{
			return PriceListId.NONE;
		}

		assumeNotNull(countryId, "If the given pricingSystemId={} is not null and not-none, then countryId may not be null", countryId);

		final List<I_M_PriceList> priceLists = retrievePriceLists(pricingSystemId, countryId, soTrx);

		return !priceLists.isEmpty() ? PriceListId.ofRepoId(priceLists.get(0).getM_PriceList_ID()) : null;
	}

	@NonNull
	public I_M_PriceList retrievePriceListbyId(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = load(priceListId, I_M_PriceList.class);
		return Check.assumeNotNull(priceList, "Missing M_PriceList record for ID={}", priceListId.getRepoId());
	}

	@Override
	public List<I_M_PriceList> retrievePriceLists(
			@NonNull final PricingSystemId pricingSystemId, 
			@NonNull final CountryId countryId, 
			@Nullable final SOTrx soTrx)
	{
		return retrievePriceListsCollectionByPricingSystemId(pricingSystemId)
				.filterAndList(countryId, soTrx);
	}

	@Override
	public PriceListsCollection retrievePriceListsCollectionByPricingSystemId(@NonNull final PricingSystemId pricingSystemId)
	{
		return retrievePriceListsCollectionByPricingSystemId(Env.getCtx(), pricingSystemId);
	}

	@Cached(cacheName = I_M_PriceList.Table_Name + "#by#M_PricingSystem_ID")
	public PriceListsCollection retrievePriceListsCollectionByPricingSystemId(
			@NonNull @CacheCtx final Properties ctx,
			@NonNull final PricingSystemId pricingSystemId)
	{
		final ImmutableList<I_M_PriceList> priceLists = queryBL.createQueryBuilder(I_M_PriceList.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_M_PricingSystem_ID, pricingSystemId)
				.orderBy(I_M_PriceList.COLUMNNAME_C_Country_ID)
				.create()
				.listImmutable(I_M_PriceList.class);

		return new PriceListsCollection(pricingSystemId, priceLists);
	}

	@Override
	public I_M_PriceList_Version retrievePriceListVersionOrNull(
			@NonNull final org.compiere.model.I_M_PriceList priceList,
			@NonNull final ZonedDateTime date,
			final Boolean processed)
	{
		final Properties ctx = getCtx(priceList);
		final PriceListId priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());
		return retrievePriceListVersionOrNull(ctx, priceListId, date, processed);
	}

	@Override
	public I_M_PriceList_Version retrievePriceListVersionOrNull(
			final PriceListId priceListId,
			final ZonedDateTime date,
			final @Nullable Boolean processed)
	{
		return retrievePriceListVersionOrNull(Env.getCtx(), priceListId, date, processed);
	}

	@Override
	@Nullable
	public PriceListVersionId retrievePriceListVersionIdOrNull(
			@NonNull final PriceListId priceListId,
			@NonNull final ZonedDateTime date,
			@Nullable final Boolean processed)
	{
		final I_M_PriceList_Version plv = retrievePriceListVersionOrNull(Env.getCtx(), priceListId, date, processed);
		return plv != null ? PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID()) : null;
	}

	@Cached(cacheName = I_M_PriceList_Version.Table_Name + "#By#M_PriceList_ID#Date#Processed")
	@Nullable
	public I_M_PriceList_Version retrievePriceListVersionOrNull(
			@CacheCtx @NonNull final Properties ctx,
			@NonNull final PriceListId priceListId,
			final ZonedDateTime date,
			@Nullable final Boolean processed)
	{
		final IQueryBuilder<I_M_PriceList_Version> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList_Version.class, ctx, ITrx.TRXNAME_None)
				// Same pricelist
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, priceListId)
				// valid from must be before the date we need it
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						CompareQueryFilter.Operator.LESS_OR_EQUAL,
						TimeUtil.asTimestamp(date),
						DateTruncQueryFilterModifier.DAY)

				// active
				.addOnlyActiveRecordsFilter();

		if (processed != null)
		{
			queryBuilder.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_Processed, processed);
		}

		// Order the version by validFrom, descending.
		queryBuilder.orderByDescending(I_M_PriceList_Version.COLUMNNAME_ValidFrom);

		final IQuery<I_M_PriceList_Version> query = queryBuilder.create();
		final I_M_PriceList_Version result = query.first();
		if (result == null)
		{
			logger.warn("None found M_PriceList_ID=" + priceListId + ", date=" + date + ", query=" + query);
		}
		return result;
	}

	@Override
	@Cached(cacheName = I_M_PriceList_Version.Table_Name + "#By#M_PriceList_ID#Date")
	@Nullable
	public I_M_PriceList_Version retrievePriceListVersionWithExactValidDate(final PriceListId priceListId, @NonNull final Date date)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList_Version.class)
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, priceListId)
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						CompareQueryFilter.Operator.EQUAL,
						new Timestamp(date.getTime()),
						DateTruncQueryFilterModifier.DAY)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_PriceList_Version.COLUMNNAME_ValidFrom)
				.create()
				.first();
	}

	@Override
	public I_M_PriceList_Version retrieveNextVersionOrNull(final I_M_PriceList_Version plv, final boolean onlyProcessed)
	{
		// we want the PLV with the lowest ValidFrom that is just greater than plv's
		final Operator validFromOperator = Operator.GREATER;
		final boolean orderAscending = true;

		return retrievePreviousOrNext(plv, validFromOperator, onlyProcessed, orderAscending);
	}

	@Override
	public I_M_PriceList_Version retrievePreviousVersionOrNull(final I_M_PriceList_Version plv, final boolean onlyProcessed)
	{
		// we want the PLV with the highest ValidFrom that is just less than plv's
		final Operator validFromOperator = Operator.LESS;
		final boolean orderAscending = false; // i.e. descending
		return retrievePreviousOrNext(plv, validFromOperator, onlyProcessed, orderAscending);
	}

	@Nullable
	private I_M_PriceList_Version retrievePreviousOrNext(final I_M_PriceList_Version plv,
			final Operator validFromOperator,
			final boolean onlyProcessed,
			final boolean orderAscending)
	{
		final IQueryBuilder<I_M_PriceList_Version> filter = Services.get(IQueryBL.class).createQueryBuilder(I_M_PriceList_Version.class, plv)
				// active
				.addOnlyActiveRecordsFilter()
				// same price list
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, plv.getM_PriceList_ID())

				// valid from must be after the given PLV's validFrom date
				.addCompareFilter(I_M_PriceList_Version.COLUMNNAME_ValidFrom, validFromOperator, plv.getValidFrom());

		if (onlyProcessed)
		{
			filter// same processed value
					.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_Processed, onlyProcessed);
		}

		// by validFrom, ascending.
		return filter.orderBy(I_M_PriceList_Version.COLUMNNAME_ValidFrom)
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
				.aggregate(I_M_ProductPrice.COLUMN_MatchSeqNo, Aggregate.MAX, Integer.class);

		if (lastMatchSeqNo == null)
		{
			lastMatchSeqNo = 0;
		}

		return lastMatchSeqNo / 10 * 10 + 10;
	}

	@Override
	public I_M_PriceList_Version retrieveNewestPriceListVersion(final PriceListId priceListId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList_Version.class)
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, priceListId)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_M_PriceList_Version.COLUMNNAME_ValidFrom)
				.create()
				.first();
	}

	@Override
	@NonNull
	public Optional<PriceListVersionId> retrieveNewestPriceListVersionId(@NonNull final PriceListId priceListId)
	{
		return Optional.ofNullable(retrieveNewestPriceListVersion(priceListId))
				.map(priceListVersion -> PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));
	}

	@Override
	public String getPricingSystemName(@Nullable final PricingSystemId pricingSystemId)
	{
		if (pricingSystemId == null)
		{
			return "-";
		}

		final I_M_PricingSystem pricingSystem = getPricingSystemById(pricingSystemId);
		if (pricingSystem == null)
		{
			return "<" + pricingSystemId + ">";
		}

		return pricingSystem.getName();
	}

	@Override
	public String getPriceListName(final PriceListId priceListId)
	{
		if (priceListId == null)
		{
			return "-";
		}

		final I_M_PriceList priceList = getById(priceListId);
		if (priceList == null)
		{
			return "<" + priceListId + ">";
		}

		return priceList.getName();
	}

	@Override
	public Set<CountryId> retrieveCountryIdsByPricingSystem(@NonNull final PricingSystemId pricingSystemId)
	{
		return retrievePriceListsCollectionByPricingSystemId(pricingSystemId)
				.getCountryIds();
	}

	@Override
	public Set<ProductId> retrieveHighPriceProducts(@NonNull final BigDecimal minimumPrice, @NonNull final LocalDate date)
	{
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_M_ProductPrice.COLUMNNAME_PriceStd, Operator.GREATER_OR_EQUAL, minimumPrice)
				.addFiltersUnboxed(createPriceProductQueryFilter(date));

		queryBuilder.orderBy()
				.addColumn(I_M_ProductPrice.COLUMNNAME_M_Product_ID);

		return queryBuilder.create()
				.stream()
				.map(I_M_ProductPrice::getM_Product_ID)
				.map(ProductId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

	}

	private ICompositeQueryFilter<I_M_ProductPrice> createPriceProductQueryFilter(@NonNull final LocalDate date)
	{
		final ICompositeQueryFilter<I_M_ProductPrice> filters = queryBL.createCompositeQueryFilter(I_M_ProductPrice.class);

		filters.addOnlyActiveRecordsFilter();

		final CurrencyId currencyId = Services.get(ICurrencyBL.class).getBaseCurrency(Env.getCtx()).getId();

		final IQuery<I_M_PriceList> currencyPriceListQuery = queryBL.createQueryBuilder(I_M_PriceList.class)
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Currency_ID, currencyId)
				.addOnlyActiveRecordsFilter()
				.create();

		final IQuery<I_M_PriceList_Version> currencyPriceListVersionQuery = queryBL.createQueryBuilder(I_M_PriceList_Version.class)
				.addInSubQueryFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, I_M_PriceList.COLUMNNAME_M_PriceList_ID, currencyPriceListQuery)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						CompareQueryFilter.Operator.LESS_OR_EQUAL,
						date,
						DateTruncQueryFilterModifier.DAY)
				.create();

		filters.addInSubQueryFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, currencyPriceListVersionQuery);

		return filters;
	}

	@Override
	public List<PriceListVersionId> getPriceListVersionIdsUpToBase(@NonNull final PriceListVersionId startPriceListVersionId, @NonNull final ZonedDateTime date)
	{
		final Object[] arr = DB.getSQLValueArrayEx(ITrx.TRXNAME_None,
												   "SELECT getPriceListVersionsUpToBase_ForPricelistVersion(?, ?)",
												   startPriceListVersionId,
												   date);
		if (arr == null || arr.length == 0)
		{
			logger.warn("Got null/empty price list version array for {}. Returning same price list version.", startPriceListVersionId);
			return ImmutableList.of(startPriceListVersionId);
		}

		return Stream.of(arr)
				.map(NumberUtils::asIntOrZero)
				.map(PriceListVersionId::ofRepoId)
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public I_M_PriceList_Version getCreatePriceListVersion(@NonNull final ProductPriceCreateRequest request)
	{
		final PriceListId priceListId = PriceListId.ofRepoId(request.getPriceListId());
		@NonNull
		final LocalDate validDate = request.getValidDate();
		final I_M_PriceList_Version plv;
		if (request.isUseNewestPriceListversion())
		{
			plv = Services.get(IPriceListDAO.class).retrieveNewestPriceListVersion(priceListId);
		}
		else
		{
			plv = Services.get(IPriceListDAO.class).retrievePriceListVersionWithExactValidDate(priceListId, TimeUtil.asTimestamp(validDate));
		}
		return plv == null ? createPriceListVersion(priceListId, validDate) : plv;
	}

	private I_M_PriceList_Version createPriceListVersion(final PriceListId priceListId, @NonNull final LocalDate validFrom)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class);
		plv.setName(validFrom.toString());
		plv.setValidFrom(TimeUtil.asTimestamp(validFrom));
		plv.setM_PriceList_ID(priceListId.getRepoId());
		plv.setProcessed(true);
		save(plv);

		// now set the previous one as base list
		final I_M_PriceList_Version previousPlv = Services.get(IPriceListDAO.class).retrievePreviousVersionOrNull(plv, true);
		if (previousPlv != null)
		{
			plv.setM_Pricelist_Version_Base(previousPlv);
			save(plv);
		}

		return plv;
	}

	@Override
	public I_M_PriceList getPriceListByPriceListVersionId(@NonNull final PriceListVersionId priceListVersionId)
	{
		final I_M_PriceList_Version plv = getPriceListVersionById(priceListVersionId);
		final PriceListId priceListId = PriceListId.ofRepoId(plv.getM_PriceList_ID());
		return getById(priceListId);
	}

	@Override
	public I_M_PriceList_Version getBasePriceListVersionForPricingCalculationOrNull(@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final ZonedDateTime promisedDate)
	{
		final I_M_PriceList_Version priceListVersion = getPriceListVersionById(priceListVersionId);
		return getBasePriceListVersionForPricingCalculationOrNull(priceListVersion, promisedDate);
	}

	@Override
	public I_M_PriceList_Version getBasePriceListVersionForPricingCalculationOrNull(@NonNull final I_M_PriceList_Version priceListVersion,
			@NonNull final ZonedDateTime date)
	{
		final PriceListVersionId basePriceListVersionId = getBasePriceListVersionIdForPricingCalculationOrNull(priceListVersion, date);
		return basePriceListVersionId != null
				? getPriceListVersionById(basePriceListVersionId)
				: null;
	}

	@Override
	public /* static */PriceListVersionId getBasePriceListVersionIdForPricingCalculationOrNull(@NonNull final I_M_PriceList_Version priceListVersion,
			@NonNull final ZonedDateTime date)
	{

		final I_M_PriceList priceList = getById(priceListVersion.getM_PriceList_ID());

		final int basePriceListRecordId = priceList.getBasePriceList_ID();

		if (basePriceListRecordId <= 0)
		{
			return null;
		}

		return retrievePriceListVersionIdOrNull(PriceListId.ofRepoId(basePriceListRecordId), date);
	}

	@Override
	public PriceListVersionId getBasePriceListVersionIdForPricingCalculationOrNull(@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final ZonedDateTime date)
	{
		final I_M_PriceList_Version priceListVersion = getPriceListVersionById(priceListVersionId);
		return getBasePriceListVersionIdForPricingCalculationOrNull(priceListVersion, date);
	}

	@Override
	public ProductPriceId addProductPrice(@NonNull final AddProductPriceRequest request)
	{
		final I_M_ProductPrice record = newInstance(I_M_ProductPrice.class);

		record.setM_PriceList_Version_ID(request.getPriceListVersionId().getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setC_UOM_ID(request.getUomId().getRepoId());

		record.setPriceStd(request.getPriceStd());
		record.setPriceList(request.getPriceList());
		record.setPriceLimit(request.getPriceLimit());

		record.setC_TaxCategory_ID(request.getTaxCategoryId().getRepoId());

		record.setIsAttributeDependant(false);

		saveRecord(record);

		return ProductPriceId.ofRepoId(record.getM_ProductPrice_ID());
	}

	@Override
	public ProductPriceId copyProductPrice(@NonNull final CopyProductPriceRequest request)
	{
		final I_M_ProductPrice record = load(request.getCopyFromProductPriceId(), I_M_ProductPrice.class);

		final I_M_ProductPrice recordCopy = InterfaceWrapperHelper.copy()
				.setFrom(record)
				.copyToNew(I_M_ProductPrice.class);

		recordCopy.setM_PriceList_Version_ID(request.getCopyToPriceListVersionId().getRepoId());
		recordCopy.setM_ProductPrice_Base_ID(record.getM_ProductPrice_ID());
		recordCopy.setIsInvalidPrice(false);

		if (request.getPriceStd() != null)
		{
			recordCopy.setPriceStd(request.getPriceStd());
		}

		saveRecord(recordCopy);

		return ProductPriceId.ofRepoId(recordCopy.getM_ProductPrice_ID());
	}

	@Override
	public void updateProductPrice(@NonNull final UpdateProductPriceRequest request)
	{
		final I_M_ProductPrice record = load(request.getProductPriceId(), I_M_ProductPrice.class);

		if (request.getPriceStd() != null)
		{
			record.setPriceStd(request.getPriceStd());
		}

		saveRecord(record);
	}

	@Override
	public void deleteProductPricesByIds(@NonNull final Set<ProductPriceId> productPriceIds)
	{
		if (productPriceIds.isEmpty())
		{
			return;
		}

		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class)
				.addInArrayFilter(I_M_ProductPrice.COLUMN_M_ProductPrice_ID, productPriceIds)
				.create()
				.delete();
	}

	@Override
	public void mutateCustomerPrices(final PriceListVersionId newBasePLVId, final UserId userId)
	{
		final I_M_PriceList_Version newBasePLV = getPriceListVersionById(newBasePLVId);

		final PriceListId basePricelistId = PriceListId.ofRepoId(newBasePLV.getM_PriceList_ID());

		final List<I_M_PriceList_Version> versionsForBasePriceList = retrieveCustomPLVsToMutate(basePricelistId);

		for (final I_M_PriceList_Version oldCustPLV : versionsForBasePriceList)
		{
			createNewPLV(oldCustPLV, newBasePLV, userId);
		}

	}

	private void createNewPLV(final I_M_PriceList_Version oldCustomerPLV, final I_M_PriceList_Version newBasePLV, final UserId userId)
	{
		final I_M_PriceList_Version newCustomerPLV = copy()
				.setSkipCalculatedColumns(true)
				.setFrom(oldCustomerPLV)
				.copyToNew(I_M_PriceList_Version.class);

		newCustomerPLV.setValidFrom(newBasePLV.getValidFrom());
		newCustomerPLV.setM_DiscountSchema_ID(newBasePLV.getM_DiscountSchema_ID());
		newCustomerPLV.setM_Pricelist_Version_Base_ID(oldCustomerPLV.getM_PriceList_Version_ID());

		final PriceListId pricelistId = PriceListId.ofRepoId(oldCustomerPLV.getM_PriceList_ID());
		final LocalDate validFrom = TimeUtil.asLocalDate(newBasePLV.getValidFrom());

		if (pricelistId != null && validFrom != null)
		{
			final I_M_PriceList priceList = getById(pricelistId);
			final String plvName = Services.get(IPriceListBL.class).createPLVName(priceList.getName(), validFrom);

			newCustomerPLV.setName(plvName);
		}

		saveRecord(newCustomerPLV);

		final PriceListVersionId newCustomerPLVId = PriceListVersionId.ofRepoId(newCustomerPLV.getM_PriceList_Version_ID());

		createProductPricesForPLV(userId, newCustomerPLVId);

		cloneASIs(newCustomerPLVId);

		save(newCustomerPLV);

	}

	@VisibleForTesting
	protected void createProductPricesForPLV(final UserId userId, final PriceListVersionId newCustomerPLVId)
	{
		final ISessionBL sessionBL = Services.get(ISessionBL.class);

		sessionBL.setDisableChangeLogsForThread(true);
		try
		{
			DB.executeFunctionCallEx( //
									  ITrx.TRXNAME_ThreadInherited //
					, "select M_PriceList_Version_CopyFromBase(p_M_PriceList_Version_ID:=?, p_AD_User_ID:=?)" //
					, new Object[] { newCustomerPLVId, userId.getRepoId() } //
			);

		}
		finally
		{
			sessionBL.setDisableChangeLogsForThread(false);
		}
	}

	private void cloneASIs(final PriceListVersionId newPLVId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, newPLVId)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_IsAttributeDependant, true)
				.create()
				.iterateAndStream()
				.forEach(this::cloneASI);
	}

	private void cloneASI(final I_M_ProductPrice productPrice)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		if (!productPrice.isAttributeDependant())
		{
			return;
		}

		final AttributeSetInstanceId asiSourceId = AttributeSetInstanceId.ofRepoId(productPrice.getM_AttributeSetInstance_ID());

		final AttributeSetInstanceId asiTargetId = attributeDAO.copyASI(asiSourceId);

		productPrice.setM_AttributeSetInstance_ID(asiTargetId.getRepoId());

		save(productPrice);
	}

	@VisibleForTesting
	protected List<I_M_PriceList_Version> retrieveCustomPLVsToMutate(@NonNull final PriceListId basePriceListId)
	{
		final String maxValidFromTypedFilter = I_M_PriceList_Version.Table_Name
				+ "."
				+ I_M_PriceList_Version.COLUMNNAME_ValidFrom
				+ " = ( SELECT max (v."
				+ I_M_PriceList_Version.COLUMNNAME_ValidFrom
				+ ") FROM "
				+ I_M_PriceList_Version.Table_Name + " v "
				+ " WHERE "
				+ I_M_PriceList_Version.Table_Name
				+ "."
				+ I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID
				+ " = v."
				+ I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID
				+ ") ";
		final IQueryFilter<I_M_PriceList_Version> maxValidFromFilter = TypedSqlQueryFilter.of(maxValidFromTypedFilter);

		final IQuery<I_C_BPartner> customerQuery = queryBL.createQueryBuilder(I_C_BPartner.class)

				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsCustomer, true)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsAllowPriceMutation, true)
				.create();

		final I_M_PriceList basePriceList = getById(basePriceListId);

		return queryBL.createQueryBuilder(I_M_PriceList.class)

				.addEqualsFilter(I_M_PriceList.COLUMNNAME_BasePriceList_ID, basePriceListId)
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Country_ID, basePriceList.getC_Country_ID())
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Currency_ID, basePriceList.getC_Currency_ID())

				.addInSubQueryFilter()
				.matchingColumnNames(I_M_PriceList.COLUMNNAME_M_PricingSystem_ID, I_C_BPartner.COLUMNNAME_M_PricingSystem_ID)
				.subQuery(customerQuery)
				.end()
				.andCollectChildren(I_M_PriceList_Version.COLUMN_M_PriceList_ID)
				.addOnlyActiveRecordsFilter()

				.addNotEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, basePriceListId)

				.filter(maxValidFromFilter)
				.create()

				.list(I_M_PriceList_Version.class);
	}

	@Override
	public Optional<TaxCategoryId> getDefaultTaxCategoryByPriceListVersionId(@NonNull final PriceListVersionId priceListVersionId)
	{
		final I_M_PriceList priceList = getPriceListByPriceListVersionId(priceListVersionId);
		return TaxCategoryId.optionalOfRepoId(priceList.getDefault_TaxCategory_ID());
	}

	@Override
	public PricingSystemId getPricingSystemId(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = getById(priceListId);
		return PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID());
	}

	/**
	 * @param productFilter    when running from a process, you can get an instance of this filter with {@code final IQueryFilter<I_M_Product> queryFilterOrElseFalse = getProcessInfo().getQueryFilterOrElseFalse();}
	 * @param dateFrom         the method updates product-prices with a PLV that is valid at or after the given date, if missing all product-prices are updated
	 * @param newIsActiveValue {@code M_ProductPrice.IsActive} is set this the given value for all matching product prices.
	 */
	@Override
	public void updateProductPricesIsActive(
			@NonNull final IQueryFilter<I_M_Product> productFilter,
			@Nullable final LocalDate dateFrom,
			final boolean newIsActiveValue)
	{

		final IQuery<I_M_ProductPrice> productPriceQuery = createProductPriceQueryForDiscontinuedProduct(productFilter, dateFrom);
		final int updatedRecords = productPriceQuery
				.updateDirectly()
				.addSetColumnValue(I_M_ProductPrice.COLUMNNAME_IsActive, newIsActiveValue)
				.execute();
		logger.debug("Updated {} M_ProductPrice records", updatedRecords);

		if (updatedRecords > 0)
		{
			invalidateCacheForProductPrice(updatedRecords, productPriceQuery);
		}
	}

	private IQuery<I_M_PriceList_Version> currentPriceListVersionQuery(@NonNull final LocalDate dateFrom)
	{
		return queryBL.createQueryBuilder(I_M_PriceList_Version.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						Operator.LESS_OR_EQUAL,
						dateFrom,
						DateTruncQueryFilterModifier.DAY)
				.orderByDescending(I_M_PriceList_Version.COLUMN_ValidFrom)
				.setLimit(QueryLimit.ONE)
				.create();
	}

	private IQueryFilter<I_M_PriceList_Version> futurePriceListVersionFilter(@NonNull final LocalDate dateFrom)
	{
		return queryBL.createCompositeQueryFilter(I_M_PriceList_Version.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						Operator.GREATER,
						dateFrom,
						DateTruncQueryFilterModifier.DAY);
	}

	@NonNull
	private IQuery<I_M_ProductPrice> createProductPriceQueryForDiscontinuedProduct(
			@NonNull final IQueryFilter<I_M_Product> productFilter,
			@Nullable final LocalDate dateFrom)
	{
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = queryBL.createQueryBuilder(I_M_Product.class)
				.filter(productFilter)
				.andCollectChildren(I_M_ProductPrice.COLUMNNAME_M_Product_ID, I_M_ProductPrice.class);

		if (dateFrom == null)
		{
			return queryBuilder
					.create();
		}
		
		final IQuery<I_M_PriceList_Version> currentPriceListVersionQuery = currentPriceListVersionQuery(dateFrom);

		final IQueryFilter<I_M_PriceList_Version> futurePriceListVersionFilter = futurePriceListVersionFilter(dateFrom);

		final IQuery<I_M_PriceList_Version> priceListVersionQuery = queryBL.createQueryBuilder(I_M_PriceList_Version.class)
				.setJoinOr()
				.addInSubQueryFilter(I_M_PriceList_Version.COLUMN_M_PriceList_Version_ID, I_M_PriceList_Version.COLUMN_M_PriceList_Version_ID, currentPriceListVersionQuery)
				.filter(futurePriceListVersionFilter)
				.create();

		return queryBuilder
				.addInSubQueryFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, priceListVersionQuery)
				.create();
	}

	private void invalidateCacheForProductPrice(final int updatedRecords, final IQuery<I_M_ProductPrice> productPriceQuery)
	{
		final CacheInvalidateMultiRequest cacheInvalidateMultiRequest;
		if (updatedRecords > 100)
		{
			cacheInvalidateMultiRequest = CacheInvalidateMultiRequest.allRecordsForTable(I_M_ProductPrice.Table_Name);
		}
		else
		{
			cacheInvalidateMultiRequest = CacheInvalidateMultiRequest.fromTableNameAndRecordIds(I_M_ProductPrice.Table_Name, productPriceQuery.listIds());
		}
		ModelCacheInvalidationService.get()
				.invalidate(cacheInvalidateMultiRequest, ModelCacheInvalidationTiming.AFTER_CHANGE);
	}

	@Override
	public CurrencyId getCurrencyId(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList priceList = getById(priceListId);
		if (priceList == null)
		{
			throw new AdempiereException("@NotFound@ @M_PriceList_ID@: " + priceListId);
		}
		return CurrencyId.ofRepoId(priceList.getC_Currency_ID());
	}

	@Override
	public I_M_ProductScalePrice retrieveScalePriceForExactBreak(@NonNull final ProductPriceId productPriceId, @NonNull final BigDecimal scalePriceBreak)
	{
		return queryBL.createQueryBuilder(I_M_ProductScalePrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID, productPriceId)
				.addEqualsFilter(I_M_ProductScalePrice.COLUMNNAME_Qty, scalePriceBreak)
				.create()
				.firstOnly(I_M_ProductScalePrice.class);
	}
}
