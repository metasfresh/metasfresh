package de.metas.contracts.pricing;

import ch.qos.logback.classic.Level;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.SubscriptionDiscountLine;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.repository.ISubscriptionDiscountRepository;
import de.metas.contracts.repository.SubscriptionDiscountQuery;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.service.IPricingBL;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * This pricing rule applies if the given {@link IPricingContext}'s referenced object references a {@link I_C_Flatrate_Conditions} record.
 * <p>
 * If that is given, then the rule creates a pricing context of it's own and calls the pricing engine with that "alternative" pricing context.
 * The rule's own pricing context contains the {@link I_C_Flatrate_Conditions}'s pricing system.
 */
public class SubscriptionPricingRule implements IPricingRule
{
	private final @NonNull ISubscriptionDiscountRepository subscriptionDiscountRepository = Services.get(ISubscriptionDiscountRepository.class);

	private static final Logger logger = LogManager.getLogger(SubscriptionPricingRule.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public boolean applies(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		if (result.isCalculated())
		{
			loggable.addLog("Not applying because already calculated");
			return false;
		}

		if (pricingCtx.getCountryId() == null)
		{
			loggable.addLog("Not applying because pricingCtx has no C_Country_ID; pricingCtx={}", pricingCtx);
			return false;
		}

		final Object referencedObject = pricingCtx.getReferencedObject();
		final I_C_Flatrate_Conditions flatrateConditions = ContractPricingUtil.getC_Flatrate_Conditions(referencedObject);

		if (flatrateConditions == null)
		{
			loggable.addLog("Not applying because referencedObject has no C_Flatrate_Conditions; referencedObject={}", referencedObject);
			return false;
		}

		if (!TypeConditions.SUBSCRIPTION.getCode().equals(flatrateConditions.getType_Conditions()))
		{
			loggable.addLog("Not applying because referenced C_Flatrate_Conditions.Type_Conditions={} (should be: {})", flatrateConditions.getType_Conditions(), TypeConditions.SUBSCRIPTION.getCode());
			return false;
		}

		return true;
	}

	@Override
	public void calculate(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final Object referencedObject = pricingCtx.getReferencedObject();
		final I_C_Flatrate_Conditions conditions = ContractPricingUtil.getC_Flatrate_Conditions(referencedObject);

		// subscription-discount
		final SubscriptionDiscountLine subscriptionDiscountLine;
		if (!pricingCtx.isDisallowDiscount())
		{
			subscriptionDiscountLine = getSubscriptionDiscountOrNull(pricingCtx, conditions);
		}
		else
		{
			loggable.addLog("pricingCtx does not allow discounts; -> not considering C_Subscr_Discount; pricingCtx={}", pricingCtx);
			subscriptionDiscountLine = null;
		}

		// alternate pricing-system
		final IPricingResult subscriptionPricingResult;
		if (conditions.getM_PricingSystem_ID() > 0)
		{
			loggable.addLog("START Invoking pricing engine with M_PricingSystem_ID={}", conditions.getM_PricingSystem_ID());
			final I_M_PriceList subscriptionPriceList = retrievePriceListForConditionsAndCountry(pricingCtx.getCountryId(), conditions);
			final IEditablePricingContext subscriptionPricingCtx = copyPricingCtxButInsertPriceList(pricingCtx, subscriptionPriceList);
			subscriptionPricingResult = invokePricingEngine(subscriptionPricingCtx.setFailIfNotCalculated());
			loggable.addLog("END Invoking pricing engine with M_PricingSystem_ID={}", conditions.getM_PricingSystem_ID());
		}
		else
		{
			subscriptionPricingResult = result;
		}

		final IPricingResult combinedPricingResult = aggregateSubscriptionDiscount(subscriptionDiscountLine, subscriptionPricingResult);
		copySubscriptionResultIntoResult(combinedPricingResult, result);
		copyDiscountIntoResultIfAllowedByPricingContext(combinedPricingResult, result, pricingCtx);
	}

	private IPricingResult aggregateSubscriptionDiscount(
			@Nullable final SubscriptionDiscountLine subscriptionDiscountLine,
			@NonNull final IPricingResult subscriptionPricingResult)
	{
		if (subscriptionDiscountLine != null
				&& !subscriptionPricingResult.isDisallowDiscount()
				&& (subscriptionDiscountLine.isPrioritiseOwnDiscount() || !subscriptionPricingResult.isDiscountCalculated()))
		{
			subscriptionPricingResult.setDiscount(subscriptionDiscountLine.getDiscount());
			if (subscriptionDiscountLine.isPrioritiseOwnDiscount())
			{
				subscriptionPricingResult.setDontOverrideDiscountAdvice(true);
			}
		}
		return subscriptionPricingResult;
	}

	@Nullable
	private SubscriptionDiscountLine getSubscriptionDiscountOrNull(final @NonNull IPricingContext pricingCtx, final I_C_Flatrate_Conditions conditions)
	{
		final Optional<SubscriptionDiscountLine> discount = subscriptionDiscountRepository.getDiscount(SubscriptionDiscountQuery.builder()
				.productId(pricingCtx.getProductId())
				.flatrateConditionId(ConditionsId.ofRepoId(conditions.getC_Flatrate_Conditions_ID()))
				.onDate(pricingCtx.getPriceDate().atStartOfDay(orgDAO.getTimeZone(pricingCtx.getOrgId())))
				.build());

		if (discount.isPresent() && conditions.getC_Flatrate_Transition() != null)
		{
			final SubscriptionDiscountLine discountLine = discount.get();
			final boolean matchIfTermEndsWithCalendarYear = discountLine.isMatchIfTermEndsWithCalendarYear();
			if (matchIfTermEndsWithCalendarYear == conditions.getC_Flatrate_Transition().isEndsWithCalendarYear())
			{
				return discountLine;
			}
		}
		return null;
	}

	private static I_M_PriceList retrievePriceListForConditionsAndCountry(
			final CountryId countryId,
			@NonNull final I_C_Flatrate_Conditions conditions)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_PriceList.COLUMN_C_Country_ID, countryId, null)
				.addEqualsFilter(I_M_PriceList.COLUMN_M_PricingSystem_ID, conditions.getM_PricingSystem_ID())
				.addEqualsFilter(I_M_PriceList.COLUMN_IsSOPriceList, true)
				.orderBy().addColumnDescending(I_M_PriceList.COLUMNNAME_C_Country_ID).endOrderBy()
				.create()
				.first();
	}

	private static IEditablePricingContext copyPricingCtxButInsertPriceList(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final I_M_PriceList subscriptionPriceList)
	{
		final IEditablePricingContext subscriptionPricingCtx = pricingCtx.copy();

		// don't set a ReferencedObject, so that this rule's 'applies()' method will return false
		subscriptionPricingCtx.setReferencedObject(null);

		// set the price list from subscription's M_Pricing_Systen
		subscriptionPricingCtx.setPriceListId(PriceListId.ofRepoId(subscriptionPriceList.getM_PriceList_ID()));
		subscriptionPricingCtx.setPricingSystemId(PricingSystemId.ofRepoId(subscriptionPriceList.getM_PricingSystem_ID()));
		subscriptionPricingCtx.setPriceListVersionId(null);

		return subscriptionPricingCtx;
	}

	private static IPricingResult invokePricingEngine(@NonNull final IPricingContext subscriptionPricingCtx)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		return pricingBL.calculatePrice(subscriptionPricingCtx);
	}

	/**
	 * Copy the results of our internal call into 'result'
	 */
	private static void copySubscriptionResultIntoResult(
			@NonNull final IPricingResult subscriptionPricingResult,
			@NonNull final IPricingResult result)
	{
		if (Util.same(subscriptionPricingResult, result))
		{
			return; // no need to copy anything
		}

		result.setCurrencyId(subscriptionPricingResult.getCurrencyId());
		result.setPriceUomId(subscriptionPricingResult.getPriceUomId());
		result.setCalculated(subscriptionPricingResult.isCalculated());
		result.setDisallowDiscount(subscriptionPricingResult.isDisallowDiscount());

		result.setUsesDiscountSchema(subscriptionPricingResult.isUsesDiscountSchema());
		result.setPricingConditions(subscriptionPricingResult.getPricingConditions());

		result.setEnforcePriceLimit(subscriptionPricingResult.getEnforcePriceLimit());
		result.setPricingSystemId(subscriptionPricingResult.getPricingSystemId());
		result.setPriceListVersionId(subscriptionPricingResult.getPriceListVersionId());
		result.setProductCategoryId(subscriptionPricingResult.getProductCategoryId());
		result.setPrecision(subscriptionPricingResult.getPrecision());
		result.setPriceLimit(subscriptionPricingResult.getPriceLimit());
		result.setPriceList(subscriptionPricingResult.getPriceList());
		result.setPriceStd(subscriptionPricingResult.getPriceStd());
		result.setTaxIncluded(subscriptionPricingResult.isTaxIncluded());
		result.setTaxCategoryId(subscriptionPricingResult.getTaxCategoryId());

		result.setPriceEditable(subscriptionPricingResult.isPriceEditable());
	}

	private static void copyDiscountIntoResultIfAllowedByPricingContext(
			@NonNull final IPricingResult subscriptionPricingResult,
			@NonNull final IPricingResult result,
			@NonNull final IPricingContext pricingCtx)
	{
		if (!pricingCtx.isDisallowDiscount())
		{
			if (result.isDiscountEditable())
			{
				result.setDiscount(subscriptionPricingResult.getDiscount());
				result.setDiscountEditable(subscriptionPricingResult.isDiscountEditable());
				result.setDontOverrideDiscountAdvice(subscriptionPricingResult.isDontOverrideDiscountAdvice());
			}
		}
	}

}
