/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.price;

import ch.qos.logback.classic.Level;
import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.pricing.ContractPricingUtil;
import de.metas.contracts.pricing.PricingUtil;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.service.IPricingBL;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_PriceList;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * Computes the price for sales order/purchase order lines meant to initiate "call order contracts".
 */
public class CallOrderContractLinePricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(CallOrderContractLinePricingRule.class);

	private final CallOrderContractService callOrderContractService = SpringContextHolder.instance.getBean(CallOrderContractService.class);

	private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
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
		if (!(referencedObject instanceof I_C_OrderLine))
		{
			loggable.addLog("Not applying because referenced object is not I_C_OrderLine;");
			return false;
		}

		final I_C_OrderLine orderLine = (I_C_OrderLine)pricingCtx.getReferencedObject();

		if (!callOrderContractService.isCallOrderContractLine(orderLine))
		{
			loggable.addLog("Not applying because there are no call order term conditions referenced!;");
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final I_C_Flatrate_Conditions conditions = ContractPricingUtil.getC_Flatrate_Conditions(pricingCtx.getReferencedObject());

		if (conditions == null || conditions.getM_PricingSystem_ID() <= 0)
		{
			loggable.addLog("Not applying because the referenced term conditions don't have any price information!;");
			return;
		}

		final PricingSystemId callOrderPricingSystemId = PricingSystemId.ofRepoId(conditions.getM_PricingSystem_ID());

		final IPricingResult callOrderPricingResult = calculatePrice(pricingCtx, callOrderPricingSystemId, pricingCtx.getCountryId(), loggable)
				.orElse(null);

		if (callOrderPricingResult == null)
		{
			return;
		}

		result.setCurrencyId(callOrderPricingResult.getCurrencyId());
		result.setPrecision(callOrderPricingResult.getPrecision());

		result.setPriceUomId(callOrderPricingResult.getPriceUomId());
		result.setPricingSystemId(callOrderPricingResult.getPricingSystemId());
		result.setPriceListVersionId(callOrderPricingResult.getPriceListVersionId());
		result.setCalculated(callOrderPricingResult.isCalculated());

		result.setUsesDiscountSchema(callOrderPricingResult.isUsesDiscountSchema());
		result.setPricingConditions(callOrderPricingResult.getPricingConditions());

		result.setProductCategoryId(callOrderPricingResult.getProductCategoryId());

		result.setEnforcePriceLimit(callOrderPricingResult.getEnforcePriceLimit());
		result.setPriceLimit(callOrderPricingResult.getPriceLimit());
		result.setPriceList(callOrderPricingResult.getPriceList());
		result.setPriceStd(callOrderPricingResult.getPriceStd());

		result.setTaxIncluded(callOrderPricingResult.isTaxIncluded());
		result.setTaxCategoryId(callOrderPricingResult.getTaxCategoryId());

		result.setPriceEditable(callOrderPricingResult.isPriceEditable());
		result.setDiscountEditable(callOrderPricingResult.isDiscountEditable());
		result.setDisallowDiscount(callOrderPricingResult.isDisallowDiscount());
	}

	@NonNull
	private Optional<IPricingResult> calculatePrice(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final CountryId countryId,
			@NonNull final ILoggable loggable)
	{
		loggable.addLog("CallOrderContractLinePricingRule - START Invoking pricing engine with M_PricingSystem_ID={}", pricingSystemId);

		try
		{
			final I_M_PriceList callOrderPriceList = PricingUtil.retrievePriceListForConditionsAndCountry(countryId, pricingSystemId, pricingCtx.getSoTrx());

			if (callOrderPriceList == null)
			{
				loggable.addLog("calculatePriceActual - no price list found for pricing system: {}, and countryId: {}", pricingSystemId, countryId);
				return Optional.empty();
			}

			final IEditablePricingContext callOrderPricingCtx = PricingUtil.copyCtxOverridePriceListAndRefObject(pricingCtx, callOrderPriceList);

			return Optional.of(pricingBL.calculatePrice(callOrderPricingCtx.setFailIfNotCalculated()));
		}
		finally
		{
			loggable.addLog("CallOrderContractLinePricingRule - END Invoking pricing engine with M_PricingSystem_ID={}", callOrderContractService);
		}
	}
}
