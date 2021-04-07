package de.metas.contracts.pricing;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.service.IPricingBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_PriceList;
import org.slf4j.Logger;

/**
 * This pricing rule applies if the given {@link IPricingContext}'s referenced object references a {@link I_C_Flatrate_Conditions} record.
 * <p>
 * If that is given, then the rule creates a pricing context of it's own and calls the pricing engine with that "alternative" pricing context.
 * The rule's own pricing context contains the {@link I_C_Flatrate_Conditions}'s pricing system.
 *
 *
 */
public class SubscriptionPricingRule implements IPricingRule
{

	private static final Logger logger = LogManager.getLogger(SubscriptionPricingRule.class);

	@Override
	public boolean applies(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result)
	{
		if (result.isCalculated())
		{
			logger.debug("Not applying because already calculated");
			return false;
		}

		if (pricingCtx.getCountryId() == null)
		{
			logger.debug("Not applying because pricingCtx has no C_Country_ID; pricingCtx={}", pricingCtx);
			return false;
		}

		final Object referencedObject = pricingCtx.getReferencedObject();
		final I_C_Flatrate_Conditions flatrateConditions = ContractPricingUtil.getC_Flatrate_Conditions(referencedObject);
		if (flatrateConditions == null)
		{
			logger.debug("Not applying because referencedObject has no C_Flatrate_Conditions; referencedObject={}", referencedObject);
			return false;
		}

		if (flatrateConditions.getM_PricingSystem_ID() <= 0)
		{
			logger.debug("Not applying because the flatrateConditions of the referencedObject has no pricing system; referencedObject={}; flatrateConditions={}", referencedObject, flatrateConditions);
			return false;
		}

		return true;
	}

	@Override
	public void calculate(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result)
	{
		final Object referencedObject = pricingCtx.getReferencedObject();

		final I_C_Flatrate_Conditions conditions = ContractPricingUtil.getC_Flatrate_Conditions(referencedObject);
		final I_M_PriceList subscriptionPriceList = retrievePriceListForConditionsAndCountry(pricingCtx.getCountryId(), conditions);

		final IEditablePricingContext subscriptionPricingCtx = copyPricingCtxButInsertPriceList(pricingCtx, subscriptionPriceList);

		final IPricingResult subscriptionPricingResult = invokePricingEngine(subscriptionPricingCtx.setFailIfNotCalculated());

		copySubscriptionResultIntoResult(subscriptionPricingResult, result);

		copyDiscountIntoResultIfAllowedByPricingContext(subscriptionPricingResult, result, pricingCtx);
	}

	private static I_M_PriceList retrievePriceListForConditionsAndCountry(
			final CountryId countryId,
			@NonNull final I_C_Flatrate_Conditions conditions)
	{
		final I_M_PriceList subscriptionPriceList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_PriceList.COLUMN_C_Country_ID, countryId, null)
				.addEqualsFilter(I_M_PriceList.COLUMN_M_PricingSystem_ID, conditions.getM_PricingSystem_ID())
				.addEqualsFilter(I_M_PriceList.COLUMN_IsSOPriceList, true)
				.orderBy().addColumnDescending(I_M_PriceList.COLUMNNAME_C_Country_ID).endOrderBy()
				.create()
				.first();
		return subscriptionPriceList;
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
		final IPricingResult subscriptionPricingResult = pricingBL.calculatePrice(subscriptionPricingCtx);

		return subscriptionPricingResult;
	}

	/**
	 * Copy the results of our internal call into 'result'
	 */
	private static void copySubscriptionResultIntoResult(
			@NonNull final IPricingResult subscriptionPricingResult,
			@NonNull final IPricingResult result)
	{
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
		result.setDiscountEditable(subscriptionPricingResult.isDiscountEditable());
	}

	private static void copyDiscountIntoResultIfAllowedByPricingContext(
			@NonNull final IPricingResult subscriptionPricingResult,
			@NonNull final IPricingResult result,
			@NonNull final IPricingContext pricingCtx)
	{
		if (!pricingCtx.isDisallowDiscount())
		{
			result.setDiscount(subscriptionPricingResult.getDiscount());
		}
	}

}
