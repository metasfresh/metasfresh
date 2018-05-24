package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.pricing.conditions.PriceOverride;
import de.metas.pricing.conditions.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditionsBreak;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

public class PriceNetCalculator
{
	private static final Logger logger = LogManager.getLogger(PriceNetCalculator.class);

	// params
	private final BasePricingSystemPriceCalculator basePriceCalculator;

	@Builder
	private PriceNetCalculator(@NonNull final BasePricingSystemPriceCalculator basePriceCalculator)
	{
		this.basePriceCalculator = basePriceCalculator;
	}

	public BigDecimal calculate(@NonNull final PriceNetCalculateRequest request)
	{
		try
		{
			final BigDecimal basePrice = calculateBasePrice(request);
			if (basePrice == null)
			{
				return null;
			}

			final BigDecimal price = subtractDiscount(basePrice, request.getPricingConditionsBreak());

			return price;
		}
		catch (Exception ex)
		{
			logger.warn("Failed calculating net price for {}. Returning null.", request, ex);
			return null;
		}
	}

	private BigDecimal calculateBasePrice(@NonNull final PriceNetCalculateRequest request)
	{
		final PricingConditionsBreak pricingConditionsBreak = request.getPricingConditionsBreak();
		final PriceOverride price = pricingConditionsBreak.getPriceOverride();
		final PriceOverrideType type = price.getType();
		if (type == PriceOverrideType.NONE)
		{
			return null;
		}
		else if (type == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final BigDecimal basePrice = basePriceCalculator.calculate(request);
			// NOTE: assume BasePriceAddAmt was added
			// basePrice = basePrice.add(price.getBasePriceAddAmt());

			return basePrice;
		}
		else if (type == PriceOverrideType.FIXED_PRICE)
		{
			return price.getFixedPrice();
		}
		else
		{
			throw new AdempiereException("Unknown " + PriceOverrideType.class + ": " + type);
		}
	}

	private BigDecimal subtractDiscount(final BigDecimal basePrice, final PricingConditionsBreak pricingConditionsBreak)
	{
		final int precision = 2; // TODO: hardcoded
		final BigDecimal priceAfterDiscount = pricingConditionsBreak.getDiscount().subtractFromBase(basePrice, precision);
		return priceAfterDiscount;

	}

	@lombok.Value
	@lombok.Builder
	public static class PriceNetCalculateRequest
	{
		@lombok.NonNull
		PricingConditionsBreak pricingConditionsBreak;
		@lombok.NonNull
		BPartnerId bpartnerId;
		boolean isSOTrx;
	}

	@FunctionalInterface
	public static interface BasePricingSystemPriceCalculator
	{
		BigDecimal calculate(PriceNetCalculateRequest request);
	}
}
