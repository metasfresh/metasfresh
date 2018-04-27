/**
 *
 */
package org.adempiere.pricing.conditions.service.impl;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.conditions.PricingConditions;
import org.adempiere.pricing.conditions.PricingConditionsBreak;
import org.adempiere.pricing.conditions.PricingConditionsBreak.PriceOverrideType;
import org.adempiere.pricing.conditions.PricingConditionsBreakQuery;
import org.adempiere.pricing.conditions.PricingConditionsDiscountType;
import org.adempiere.pricing.conditions.service.CalculateDiscountRequest;
import org.adempiere.pricing.conditions.service.DiscountResult;
import org.adempiere.pricing.conditions.service.DiscountResult.DiscountResultBuilder;
import org.adempiere.pricing.conditions.service.IPricingConditionsRepository;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.product.IProductDAO;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class CalculateDiscountCommand
{
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);

	final CalculateDiscountRequest request;

	public CalculateDiscountCommand(@NonNull final CalculateDiscountRequest request)
	{
		this.request = request;
	}

	public DiscountResult calculateDiscount()
	{
		final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(request.getDiscountSchemaId());
		Check.assumeNotNull(pricingConditions, "pricingConditions shall not be null");

		final PricingConditionsDiscountType discountType = pricingConditions.getDiscountType();

		if (discountType == PricingConditionsDiscountType.FLAT_PERCENT)
		{
			return computeFlatDiscount(pricingConditions);
		}
		else if (discountType == PricingConditionsDiscountType.FORMULA
				|| discountType == PricingConditionsDiscountType.PRICE_LIST)
		{
			return DiscountResult.ZERO;
		}
		else if (discountType == PricingConditionsDiscountType.BREAKS)
		{
			return computeBreaksDiscount(pricingConditions);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @DiscountType@: " + discountType);
		}
	}

	private DiscountResult computeFlatDiscount(final PricingConditions pricingConditions)
	{
		if (pricingConditions.isBpartnerFlatDiscount())
		{
			return DiscountResult.discount(request.getBpartnerFlatDiscount());
		}
		else
		{
			return DiscountResult.discount(pricingConditions.getFlatDiscount());
		}
	}

	private DiscountResult computeBreaksDiscount(final PricingConditions pricingConditions)
	{
		final PricingConditionsBreak breakApplied = fetchDiscountSchemaBreak(pricingConditions);
		if (breakApplied == null)
		{
			return DiscountResult.ZERO;
		}

		final DiscountResultBuilder result = DiscountResult.builder()
				.discountSchemaBreakId(breakApplied.getDiscountSchemaBreakId())
				.C_PaymentTerm_ID(breakApplied.getPaymentTermId());

		computePriceForDiscountSchemaBreak(result, breakApplied);
		computeDiscountForDiscountSchemaBreak(result, breakApplied);

		return result.build();
	}

	private void computePriceForDiscountSchemaBreak(final DiscountResultBuilder result, final PricingConditionsBreak pricingConditionsBreak)
	{
		final PriceOverrideType priceOverride = pricingConditionsBreak.getPriceOverride();
		if (priceOverride == PriceOverrideType.NONE)
		{
			// nothing
		}
		else if (priceOverride == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final int basePricingSystemId = pricingConditionsBreak.getBasePricingSystemId();

			final IPricingResult productPrices = computePricesForPricingSystem(basePricingSystemId);
			final BigDecimal priceStd = productPrices.getPriceStd();
			final BigDecimal priceList = productPrices.getPriceList();
			final BigDecimal priceLimit = productPrices.getPriceLimit();

			final BigDecimal priceStdAddAmt = pricingConditionsBreak.getBasePriceAddAmt();

			result.discountSchemaBreak_BasePricingSystem_Id(basePricingSystemId);
			result.priceListOverride(priceList);
			result.priceLimitOverride(priceLimit);
			result.priceStdOverride(priceStd.add(priceStdAddAmt));
		}
		else if (priceOverride == PriceOverrideType.FIXED_PRICED)
		{
			result.priceStdOverride(pricingConditionsBreak.getFixedPrice());
		}
		else
		{
			throw new AdempiereException("Unknow price override type: " + priceOverride)
					.setParameter("break", pricingConditionsBreak);
		}
	}

	private IPricingResult computePricesForPricingSystem(final int basePricingSystemId)
	{
		Check.assumeGreaterThanZero(basePricingSystemId, "basePricingSystemId");

		final IPricingContext pricingCtx = request.getPricingCtx();
		Check.assumeNotNull(pricingCtx, "pricingCtx shall not be null for {}", request);

		final IPricingContext basePricingSystemPricingCtx = createBasePricingSystemPricingCtx(pricingCtx, basePricingSystemId);
		final IPricingResult pricingResult = pricingBL.calculatePrice(basePricingSystemPricingCtx);

		return pricingResult;
	}

	private IPricingContext createBasePricingSystemPricingCtx(final IPricingContext pricingCtx, final int basePricingSystemId)
	{
		Check.assumeGreaterThanZero(basePricingSystemId, "basePricingSystemId");

		final IEditablePricingContext newPricingCtx = pricingCtx.copy();
		newPricingCtx.setM_PricingSystem_ID(basePricingSystemId);
		newPricingCtx.setM_PriceList_ID(-1); // will be recomputed
		newPricingCtx.setM_PriceList_Version_ID(-1); // will be recomputed
		newPricingCtx.setSkipCheckingPriceListSOTrxFlag(true);
		newPricingCtx.setDisallowDiscount(true);
		newPricingCtx.setFailIfNotCalculated(true);

		return newPricingCtx;
	}

	private void computeDiscountForDiscountSchemaBreak(final DiscountResultBuilder result, final PricingConditionsBreak pricingConditionsBreak)
	{
		final BigDecimal discount;
		if (pricingConditionsBreak.isBpartnerFlatDiscount())
		{
			discount = request.getBpartnerFlatDiscount();
		}
		else
		{
			discount = pricingConditionsBreak.getDiscount();
		}

		result.discount(discount);
	}

	private PricingConditionsBreak fetchDiscountSchemaBreak(final PricingConditions pricingConditions)
	{
		if (request.getForceSchemaBreak() != null)
		{
			return request.getForceSchemaBreak();
		}

		return pricingConditions.pickApplyingBreak(PricingConditionsBreakQuery.builder()
				.attributeInstances(request.getAttributeInstances())
				.productId(request.getProductId())
				.productCategoryId(productsRepo.retrieveProductCategoryByProductId(request.getProductId()))
				.qty(request.getQty())
				.amt(request.getPrice().multiply(request.getQty()))
				.build());
	}
}
