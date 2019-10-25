package de.metas.pricing.service.impl;

import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.currency.ICurrencyBL;
import de.metas.impexp.processing.product.ProductPriceCreateRequest;
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
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.PriceListsCollection;
import de.metas.pricing.service.UpdateProductPriceRequest;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;

public class PriceListDAO implements IPriceListDAO
{
	private static final transient Logger logger = LogManager.getLogger(PriceListDAO.class);

	@Override
	public I_M_PricingSystem getPricingSystemById(final PricingSystemId pricingSystemId)
	{
		if (pricingSystemId == null)
		{
			return null;
		}

		return loadOutOfTrx(pricingSystemId, I_M_PricingSystem.class);
	}

	@Override
	public PricingSystemId getPricingSystemIdByValue(@NonNull final String value)
	{
		final int pricingSystemId = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_PricingSystem.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_PricingSystem.COLUMNNAME_Value, value)
				.create()
				.firstIdOnly();

		if (pricingSystemId <= 0)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@ (@Value@=" + value + ")");
		}

		return PricingSystemId.ofRepoId(pricingSystemId);
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
			queryBuilder.addNotInArrayFilter(I_M_ProductPrice.COLUMN_M_Product_ID, productIdsToExclude);
		}

		return queryBuilder
				.create()
				.iterateAndStream();
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
			final BPartnerLocationId bpartnerLocationId,
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
		final I_C_BPartner_Location bpartnerLocation = loadOutOfTrx(bpartnerLocationId, I_C_BPartner_Location.class);
		final CountryId countryId = CountryId.ofRepoId(bpartnerLocation.getC_Location().getC_Country_ID());

		assumeNotNull(bpartnerLocationId, "If the given pricingSystemId={} is not null and not-none, then soTrx may not be null", pricingSystemId);
		final List<I_M_PriceList> priceLists = retrievePriceLists(pricingSystemId, countryId, soTrx);

		return !priceLists.isEmpty() ? PriceListId.ofRepoId(priceLists.get(0).getM_PriceList_ID()) : null;
	}

	@Override
	public List<I_M_PriceList> retrievePriceLists(final PricingSystemId pricingSystemId, final CountryId countryId, final SOTrx soTrx)
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
		final IQueryBL queryBL = Services.get(IQueryBL.class);
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
			@NonNull final LocalDate date,
			final Boolean processed)
	{
		final Properties ctx = getCtx(priceList);
		final PriceListId priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());
		final I_M_PriceList_Version plv = retrievePriceListVersionOrNull(ctx, priceListId, date, processed);
		return plv;
	}

	@Override
	public I_M_PriceList_Version retrievePriceListVersionOrNull(
			final PriceListId priceListId,
			final LocalDate date,
			final Boolean processed)
	{
		return retrievePriceListVersionOrNull(Env.getCtx(), priceListId, date, processed);
	}

	@Override
	public PriceListVersionId retrievePriceListVersionIdOrNull(
			@NonNull final PriceListId priceListId,
			@NonNull final LocalDate date,
			@Nullable final Boolean processed)
	{
		final I_M_PriceList_Version plv = retrievePriceListVersionOrNull(Env.getCtx(), priceListId, date, processed);
		return plv != null ? PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID()) : null;
	}

	@Cached(cacheName = I_M_PriceList_Version.Table_Name + "#By#M_PriceList_ID#Date#Processed")
	public I_M_PriceList_Version retrievePriceListVersionOrNull(
			@CacheCtx @NonNull final Properties ctx,
			@NonNull final PriceListId priceListId,
			@NonNull final LocalDate date,
			@Nullable final Boolean processed)
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

		final int nextMatchSeqNo = lastMatchSeqNo / 10 * 10 + 10;
		return nextMatchSeqNo;
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

	private final ICompositeQueryFilter<I_M_ProductPrice> createPriceProductQueryFilter(@NonNull final LocalDate date)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_ProductPrice> filters = queryBL.createCompositeQueryFilter(I_M_ProductPrice.class);

		filters.addOnlyActiveRecordsFilter();

		final CurrencyId currencyId = Services.get(ICurrencyBL.class).getBaseCurrency(Env.getCtx()).getId();

		final IQuery<I_M_PriceList> currencyPriceListQuery = queryBL.createQueryBuilder(I_M_PriceList.class)
				.addEqualsFilter(I_M_PriceList.COLUMN_C_Currency_ID, currencyId)
				.addOnlyActiveRecordsFilter()
				.create();

		final IQuery<I_M_PriceList_Version> currencyPriceLisVersiontQuery = queryBL.createQueryBuilder(I_M_PriceList_Version.class)
				.addInSubQueryFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, I_M_PriceList.COLUMNNAME_M_PriceList_ID, currencyPriceListQuery)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						CompareQueryFilter.Operator.LESS_OR_EQUAL,
						date,
						DateTruncQueryFilterModifier.DAY)
				.create();

		filters.addInSubQueryFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, currencyPriceLisVersiontQuery);

		return filters;
	}

	@Override
	public List<PriceListVersionId> getPriceListVersionIdsUpToBase(@NonNull final PriceListVersionId startPriceListVersionId)
	{
		final Object[] arr = DB.getSQLValueArrayEx(ITrx.TRXNAME_None,
				"SELECT getPriceListVersionsUpToBase(?)",
				startPriceListVersionId);
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
	public I_M_PriceList_Version getBasePriceListVersionForPricingCalculationOrNull(@NonNull final PriceListVersionId priceListVersionId)
	{
		final I_M_PriceList_Version priceListVersion = getPriceListVersionById(priceListVersionId);
		return getBasePriceListVersionForPricingCalculationOrNull(priceListVersion);
	}

	@Override
	public I_M_PriceList_Version getBasePriceListVersionForPricingCalculationOrNull(@NonNull final I_M_PriceList_Version priceListVersion)
	{
		final PriceListVersionId basePriceListVersionId = getBasePriceListVersionIdForPricingCalculationOrNull(priceListVersion);
		return basePriceListVersionId != null
				? getPriceListVersionById(basePriceListVersionId)
				: null;
	}

	@Override
	public /* static */PriceListVersionId getBasePriceListVersionIdForPricingCalculationOrNull(@NonNull final I_M_PriceList_Version priceListVersion)
	{
		return priceListVersion.isFallbackToBasePriceListPrices()
				? PriceListVersionId.ofRepoIdOrNull(priceListVersion.getM_Pricelist_Version_Base_ID())
				: null;
	}

	@Override
	public PriceListVersionId getBasePriceListVersionIdForPricingCalculationOrNull(@NonNull final PriceListVersionId priceListVersionId)
	{
		final I_M_PriceList_Version priceListVersion = getPriceListVersionById(priceListVersionId);
		return getBasePriceListVersionIdForPricingCalculationOrNull(priceListVersion);
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

		final PriceListVersionId basePriceListVersionId = PriceListVersionId.ofRepoIdOrNull(newBasePLV.getM_Pricelist_Version_Base_ID());
		if (basePriceListVersionId == null)
		{
			// nothing to do
			return;
		}

		final I_M_PriceList_Version oldPLV = getPriceListVersionById(basePriceListVersionId);

		final List<I_M_PriceList_Version> versionsForOldBase = retrieveCustomPLVsToMutate(oldPLV);

		for (final I_M_PriceList_Version oldCustPLV : versionsForOldBase)
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
		saveRecord(newCustomerPLV);

		final PriceListVersionId newCustomerPLVId = PriceListVersionId.ofRepoId(newCustomerPLV.getM_PriceList_Version_ID());

		createProductPricesForPLV(userId, newCustomerPLVId);

		cloneASIs(newCustomerPLVId);

		newCustomerPLV.setM_Pricelist_Version_Base_ID(newBasePLV.getM_Pricelist_Version_Base_ID());
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
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, newPLVId)
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
	protected List<I_M_PriceList_Version> retrieveCustomPLVsToMutate(@NonNull final I_M_PriceList_Version basePLV)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final StringBuilder maxValidFromTypedFilter = new StringBuilder()
				.append(I_M_PriceList_Version.Table_Name)
				.append(".")
				.append(I_M_PriceList_Version.COLUMNNAME_ValidFrom)
				.append(" = ( SELECT max (v.")
				.append(I_M_PriceList_Version.COLUMNNAME_ValidFrom)
				.append(") FROM ")
				.append(I_M_PriceList_Version.Table_Name).append(" v ")
				.append(" WHERE ")
				.append(I_M_PriceList_Version.Table_Name)
				.append(".")
				.append(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID)
				.append(" = v.")
				.append(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID)
				.append(") ");

		final IQueryFilter<I_M_PriceList_Version> maxValidFromFilter = TypedSqlQueryFilter.of(maxValidFromTypedFilter.toString());

		final IQuery<I_C_BPartner> customerQuery = queryBL.createQueryBuilder(I_C_BPartner.class)

				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsCustomer, true)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsAllowPriceMutation, true)
				.create();

		final I_M_PriceList basePriceList = getById(basePLV.getM_PriceList_ID());

		final List<I_M_PriceList_Version> newestVersions = queryBL.createQueryBuilder(I_M_PriceList.class)

				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Country_ID, basePriceList.getC_Country_ID())
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Currency_ID, basePriceList.getC_Currency_ID())

				.addInSubQueryFilter()
				.matchingColumnNames(I_M_PriceList.COLUMNNAME_M_PricingSystem_ID, I_C_BPartner.COLUMNNAME_M_PricingSystem_ID)
				.subQuery(customerQuery)
				.end()
				.andCollectChildren(I_M_PriceList_Version.COLUMN_M_PriceList_ID)
				.addOnlyActiveRecordsFilter()

				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_Pricelist_Version_Base_ID, basePLV.getM_PriceList_Version_ID())
				.addNotEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, basePLV.getM_PriceList_ID())
				.addNotNull(I_M_PriceList_Version.COLUMNNAME_M_DiscountSchema_ID)

				.filter(maxValidFromFilter)
				.create()

				.list(I_M_PriceList_Version.class);

		return newestVersions;
	}

	@Override
	public Optional<TaxCategoryId> getDefaultTaxCategoryByPriceListVersionId(@NonNull final PriceListVersionId priceListVersionId)
	{
		final I_M_PriceList priceList = getPriceListByPriceListVersionId(priceListVersionId);
		return TaxCategoryId.optionalOfRepoId(priceList.getDefault_TaxCategory_ID());
	}
}
