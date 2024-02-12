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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyRepository;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

/**
 *  Computes the price for "call order lines".
 *
 *  A call order line is a sales order line {@link I_C_OrderLine} attached to a "Call order contract" via {@link I_C_OrderLine#COLUMNNAME_C_Flatrate_Term_ID}
 */
public class CallOrderPricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(CallOrderPricingRule.class);

	private final CallOrderContractService callOrderContractService = SpringContextHolder.instance.getBean(CallOrderContractService.class);
	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IUOMConversionBL conversionBL = Services.get(IUOMConversionBL.class);

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

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoIdOrNull(orderLine.getC_Flatrate_Term_ID());

		if (flatrateTermId == null || !callOrderContractService.isCallOrderContract(flatrateTermId))
		{
			loggable.addLog("Not applying because there is no call order contract referenced!;");
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final I_C_OrderLine orderLine = (I_C_OrderLine)pricingCtx.getReferencedObject();

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(orderLine.getC_Flatrate_Term_ID());

		final I_C_Flatrate_Term callOrderContract = flatrateDAO.getById(flatrateTermId);

		if (callOrderContract.getPriceActual() == null
				|| callOrderContract.getC_UOM_ID() <= 0
				|| callOrderContract.getM_Product_ID() <= 0
				|| callOrderContract.getC_Currency_ID() <= 0)
		{
			loggable.addLog("Not applying because the contract doesn't hold any price information!;");
			return;
		}

		if (!callOrderContractService.doesContractMatchOrderLine(orderLine, callOrderContract))
		{
			loggable.addLog("Not applying because the contract doesn't match the given order line!;");
			return;
		}

		final ProductPrice productPrice = calculatePriceActual(orderLine, callOrderContract);

		result.setPriceStd(productPrice.toBigDecimal());
		result.setPriceList(productPrice.toBigDecimal());
		result.setPriceLimit(productPrice.toBigDecimal());

		result.setPriceUomId(productPrice.getUomId());
		result.setCurrencyId(productPrice.getCurrencyId());
		result.setProductId(productPrice.getProductId());

		result.setTaxIncluded(callOrderContract.isTaxIncluded());
		result.setTaxCategoryId(TaxCategoryId.ofRepoId(callOrderContract.getC_TaxCategory_ID()));
		result.setCalculated(true);
	}

	@NonNull
	private ProductPrice calculatePriceActual(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final UomId orderLinePriceUOM = orderLine.getPrice_UOM_ID() > 0
				? UomId.ofRepoId(orderLine.getPrice_UOM_ID())
				: UomId.ofRepoId(orderLine.getC_UOM_ID());

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final Currency currency = currencyRepository.getById(CurrencyId.ofRepoId(flatrateTerm.getC_Currency_ID()));

		final ProductPrice contractProductPrice = ProductPrice.builder()
				.productId(productId)
				.uomId(UomId.ofRepoId(flatrateTerm.getC_UOM_ID()))
				.money(Money.of(flatrateTerm.getPriceActual(), currency.getId()))
				.build();

		return conversionBL.convertProductPriceToUom(contractProductPrice, orderLinePriceUOM, currency.getPrecision());
	}
}
