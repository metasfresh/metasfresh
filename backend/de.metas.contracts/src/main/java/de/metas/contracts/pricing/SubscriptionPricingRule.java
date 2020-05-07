package de.metas.contracts.pricing;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.pricing.spi.IPricingRule;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;
import org.slf4j.Logger;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

		if (pricingCtx.getC_Country_ID() <= 0)
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
		final I_M_PriceList subscriptionPriceList = retrievePriceListForConditionsAndCountry(pricingCtx.getC_Country_ID(), conditions);

		final IEditablePricingContext subscriptionPricingCtx = copyPricingCtxButInsertPriceList(pricingCtx, subscriptionPriceList);

		final IPricingResult subscriptionPricingResult = invokePricingEngine(subscriptionPricingCtx);

		copySubscriptionResultIntoResult(subscriptionPricingResult, result);

		copyDiscountIntoResultIfAllowedByPricingContext(subscriptionPricingResult, result, pricingCtx);
	}

	private static I_M_PriceList retrievePriceListForConditionsAndCountry(
			final int countryId, 
			@NonNull final I_C_Flatrate_Conditions conditions)
	{
		final I_M_PriceList subscriptionPriceList = Services.get(IQueryBL.class).createQueryBuilder(I_M_PriceList.class).addOnlyActiveRecordsFilter()
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
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final IEditablePricingContext subscriptionPricingCtx = pricingBL.createPricingContext();
		subscriptionPricingCtx.setAD_Table_ID(pricingCtx.getAD_Table_ID());
		subscriptionPricingCtx.setC_BPartner_ID(pricingCtx.getC_BPartner_ID());
		subscriptionPricingCtx.setC_Currency_ID(pricingCtx.getC_Currency_ID());
		subscriptionPricingCtx.setC_UOM_ID(pricingCtx.getC_UOM_ID());
		subscriptionPricingCtx.setM_Product_ID(pricingCtx.getM_Product_ID());
		subscriptionPricingCtx.setPriceDate(pricingCtx.getPriceDate());
		subscriptionPricingCtx.setQty(pricingCtx.getQty());
		subscriptionPricingCtx.setSOTrx(pricingCtx.isSOTrx());
		subscriptionPricingCtx.setAD_Table_ID(pricingCtx.getAD_Table_ID());
		subscriptionPricingCtx.setRecord_ID(pricingCtx.getRecord_ID());
		subscriptionPricingCtx.setDisallowDiscount(pricingCtx.isDisallowDiscount());

		// don't set a ReferencedObject, so that this rule's 'applies()' method will return false
		subscriptionPricingCtx.setReferencedObject(null);

		// set the price list from subscription's M_Pricing_Systen
		subscriptionPricingCtx.setM_PriceList_Version_ID(0);
		subscriptionPricingCtx.setM_PriceList_ID(subscriptionPriceList.getM_PriceList_ID());
		
		return subscriptionPricingCtx;
	}

	private static IPricingResult invokePricingEngine(@NonNull final IPricingContext subscriptionPricingCtx)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IPricingResult subscriptionPricingResult = pricingBL.calculatePrice(subscriptionPricingCtx);

		if (!subscriptionPricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(subscriptionPricingCtx, 0);
		}
		return subscriptionPricingResult;
	}

	/**
	 * copy the results of our internal call into 'result'
	 * 
	 * @param subscriptionPricingResult
	 * @param result
	 */
	private static void copySubscriptionResultIntoResult(
			@NonNull final IPricingResult subscriptionPricingResult,
			@NonNull final IPricingResult result)
	{
		result.setC_Currency_ID(subscriptionPricingResult.getC_Currency_ID());
		result.setPrice_UOM_ID(subscriptionPricingResult.getPrice_UOM_ID());
		result.setCalculated(subscriptionPricingResult.isCalculated());
		result.setDisallowDiscount(subscriptionPricingResult.isDisallowDiscount());

		result.setUsesDiscountSchema(subscriptionPricingResult.isUsesDiscountSchema());
		// 08634: also set the discount schema
		result.setM_DiscountSchema_ID(subscriptionPricingResult.getM_DiscountSchema_ID());
		result.setEnforcePriceLimit(subscriptionPricingResult.isEnforcePriceLimit());
		result.setM_PricingSystem_ID(subscriptionPricingResult.getM_PricingSystem_ID());
		result.setM_PriceList_Version_ID(subscriptionPricingResult.getM_PriceList_Version_ID());
		result.setM_Product_Category_ID(subscriptionPricingResult.getM_Product_Category_ID());
		result.setPrecision(subscriptionPricingResult.getPrecision());
		result.setPriceLimit(subscriptionPricingResult.getPriceLimit());
		result.setPriceList(subscriptionPricingResult.getPriceList());
		result.setPriceStd(subscriptionPricingResult.getPriceStd());
		result.setTaxIncluded(subscriptionPricingResult.isTaxIncluded());
		result.setC_TaxCategory_ID(subscriptionPricingResult.getC_TaxCategory_ID());
		
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
