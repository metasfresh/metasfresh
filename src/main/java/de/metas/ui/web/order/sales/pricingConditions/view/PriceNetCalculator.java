package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

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
	// params
	private final BasePriceFromBasePricingSystemCalculator basePriceFromBasePricingSystemCalculator;

	@Builder
	private PriceNetCalculator(@NonNull final BasePriceFromBasePricingSystemCalculator basePriceFromBasePricingSystemCalculator)
	{
		this.basePriceFromBasePricingSystemCalculator = basePriceFromBasePricingSystemCalculator;
	}

	public BigDecimal calculate(@NonNull final PricingConditionsBreak pricingConditionsBreak)
	{
		BigDecimal basePrice = calculateBasePrice(pricingConditionsBreak);
		if (basePrice == null)
		{
			return null;
		}

		final BigDecimal price = subtractDiscount(basePrice, pricingConditionsBreak);

		return price;
	}

	private BigDecimal calculateBasePrice(@NonNull final PricingConditionsBreak pricingConditionsBreak)
	{
		final PriceOverride price = pricingConditionsBreak.getPriceOverride();
		final PriceOverrideType type = price.getType();
		if (type == PriceOverrideType.NONE)
		{
			return null;
		}
		else if (type == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final BigDecimal basePrice = basePriceFromBasePricingSystemCalculator.calculate(pricingConditionsBreak);
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
		if (basePrice.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final int precision = 2; // TODO: hardcoded
		final BigDecimal priceAfterDiscount = pricingConditionsBreak.getDiscount().subtractFromBase(basePrice, precision);
		return priceAfterDiscount;

	}

	@FunctionalInterface
	public static interface BasePriceFromBasePricingSystemCalculator
	{
		BigDecimal calculate(PricingConditionsBreak pricingConditionsBreak);
	}
}
