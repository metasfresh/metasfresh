/**
 *
 */
package de.metas.pricing.conditions.service.impl;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsDiscountType;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.PricingConditionsErrorCode;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.conditions.service.PricingConditionsResult.PricingConditionsResultBuilder;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.IPricingBL;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.util.Optional;

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
/* package */ class CalculatePricingConditionsCommand
{
	// services
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);

	private final CalculatePricingConditionsRequest request;
	private PricingConditions _pricingConditions; // lazy

	public CalculatePricingConditionsCommand(@NonNull final CalculatePricingConditionsRequest request)
	{
		this.request = request;
	}

	public Optional<PricingConditionsResult> calculate()
	{
		final PricingConditionsDiscountType discountType = getDiscountType();
		if (discountType == PricingConditionsDiscountType.FLAT_PERCENT)
		{
			return Optional.of(computeFlatDiscount());
		}
		else if (discountType == PricingConditionsDiscountType.FORMULA
				|| discountType == PricingConditionsDiscountType.PRICE_LIST)
		{
			return Optional.of(PricingConditionsResult.ZERO);
		}
		else if (discountType == PricingConditionsDiscountType.BREAKS)
		{
			return computeBreaksDiscount();
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @DiscountType@: " + discountType);
		}
	}

	private PricingConditionsDiscountType getDiscountType()
	{
		if (request.getForcePricingConditionsBreak() != null)
		{
			return PricingConditionsDiscountType.BREAKS;
		}
		else
		{
			return getPricingConditions().getDiscountType();
		}
	}

	private PricingConditions getPricingConditions()
	{
		PricingConditions pricingConditions = _pricingConditions;
		if (pricingConditions == null)
		{
			final PricingConditionsId pricingConditionsId = request.getPricingConditionsId();
			if (pricingConditionsId == null)
			{
				throw new AdempiereException("Cannot fetch the pricing conditions because no ID was provided: " + request);
			}
			pricingConditions = _pricingConditions = pricingConditionsRepo.getPricingConditionsById(pricingConditionsId);
		}
		return pricingConditions;
	}

	private PricingConditionsResult computeFlatDiscount()
	{
		final Percent flatDiscount;
		final PricingConditions pricingConditions = getPricingConditions();
		if (pricingConditions.isBpartnerFlatDiscount())
		{
			flatDiscount = request.getBpartnerFlatDiscount();
		}
		else
		{
			flatDiscount = pricingConditions.getFlatDiscount();
		}

		return PricingConditionsResult.builder()
				.pricingConditionsId(pricingConditions.getId())
				.discount(flatDiscount)
				.build();

	}

	private Optional<PricingConditionsResult> computeBreaksDiscount()
	{
		final PricingConditionsBreak breakToApply = findMatchingPricingConditionBreak();
		if (breakToApply == null)
		{
			return Optional.empty();
		}

		final PricingConditionsResultBuilder result = PricingConditionsResult.builder()
				.pricingConditionsBreak(breakToApply)
				.paymentTermId(breakToApply.getDerivedPaymentTermIdOrNull());

		computePriceForPricingConditionsBreak(result, breakToApply.getPriceSpecification());
		computeDiscountForPricingConditionsBreak(result, breakToApply);

		return Optional.of(result.build());
	}

	private void computePriceForPricingConditionsBreak(
			@NonNull final PricingConditionsResultBuilder result,
			@NonNull final PriceSpecification priceOverride)
	{
		final PriceSpecificationType priceOverrideType = priceOverride.getType();
		if (priceOverrideType == PriceSpecificationType.NONE)
		{
			// nothing
		}
		else if (priceOverrideType == PriceSpecificationType.BASE_PRICING_SYSTEM)
		{
			final PricingSystemId basePricingSystemId = priceOverride.getBasePricingSystemId();

			final IPricingResult productPrices = computePricesForBasePricingSystem(basePricingSystemId);

			final CurrencyId currencyId = productPrices.getCurrencyId();
			final BigDecimal priceStd = productPrices.getPriceStd();
			final BigDecimal priceList = productPrices.getPriceList();
			final BigDecimal priceLimit = productPrices.getPriceLimit();

			result.currencyId(currencyId);
			result.basePricingSystemId(basePricingSystemId);
			result.priceListOverride(priceList);
			result.priceLimitOverride(priceLimit);

			//
			// Add surcharge amount to standard price
			final Money pricingSystemSurcharge = priceOverride.getPricingSystemSurcharge();
			if (pricingSystemSurcharge != null && pricingSystemSurcharge.signum() != 0)
			{
				if (!CurrencyId.equals(pricingSystemSurcharge.getCurrencyId(), currencyId))
				{
					throw new AdempiereException("Surcharge's currency is not matching base price's currency. ")
							.appendParametersToMessage()
							.setParameter(PricingConditionsErrorCode.SurchargeCurrencyNotMatchingPriceListCurrency)
							.setParameter("price", Money.of(priceStd, currencyId))
							.setParameter("surcharge", pricingSystemSurcharge);
				}

				final BigDecimal priceStdOverride = priceStd.add(pricingSystemSurcharge.toBigDecimal());
				result.priceStdOverride(priceStdOverride);
			}
		}
		else if (priceOverrideType == PriceSpecificationType.FIXED_PRICE)
		{
			final Money fixedPrice = priceOverride.getFixedPrice();
			result.currencyId(fixedPrice != null ? fixedPrice.getCurrencyId() : null);
			result.priceStdOverride(fixedPrice != null ? fixedPrice.toBigDecimal() : null);
		}
		else
		{
			throw new AdempiereException("Unknow price override type: " + priceOverrideType)
					.setParameter("priceOverride", priceOverride);
		}
	}

	private IPricingResult computePricesForBasePricingSystem(@NonNull final PricingSystemId basePricingSystemId)
	{
		final IPricingContext pricingCtx = request.getPricingCtx();
		Check.assumeNotNull(pricingCtx, "pricingCtx shall not be null for {}", request);

		final IPricingContext basePricingSystemPricingCtx = createBasePricingSystemPricingCtx(pricingCtx, basePricingSystemId);
		try
		{
			return pricingBL.calculatePrice(basePricingSystemPricingCtx);
		}
		catch (@NonNull final ProductNotOnPriceListException e)
		{
			Loggables.get().addLog(CalculatePricingConditionsCommand.class.getSimpleName() + ".computePricesForBasePricingSystem caught ProductNotOnPriceListException");
			throw e.appendParametersToMessage() // augment and rethrow
					.setParameter("Sub-Calculation", "true")
					.setParameter("basePricingSystemPricingCtx", basePricingSystemPricingCtx);
		}
	}

	private static IPricingContext createBasePricingSystemPricingCtx(@NonNull final IPricingContext pricingCtx, @NonNull final PricingSystemId basePricingSystemId)
	{
		Check.assumeNotNull(pricingCtx.getCountryId(), "Given pricingCtx needs to have a country-ID, so we can later dedic the priceListId! pricingCtx={}", pricingCtx);
		
		final IEditablePricingContext newPricingCtx = pricingCtx.copy();
		newPricingCtx.setPricingSystemId(basePricingSystemId);
		newPricingCtx.setPriceListId(null); // will be recomputed
		newPricingCtx.setPriceListVersionId(null); // will be recomputed
		newPricingCtx.setSkipCheckingPriceListSOTrxFlag(true);
		newPricingCtx.setDisallowDiscount(true);
		newPricingCtx.setFailIfNotCalculated();

		return newPricingCtx;
	}

	private void computeDiscountForPricingConditionsBreak(final PricingConditionsResultBuilder result, final PricingConditionsBreak pricingConditionsBreak)
	{
		final Percent discount;
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

	private PricingConditionsBreak findMatchingPricingConditionBreak()
	{
		if (request.getForcePricingConditionsBreak() != null)
		{
			return request.getForcePricingConditionsBreak();
		}
		else
		{
			final PricingConditions pricingConditions = getPricingConditions();
			return pricingConditions.pickApplyingBreak(request.getPricingConditionsBreakQuery());
		}
	}
}
