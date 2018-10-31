package de.metas.pricing.conditions;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.PricingSystemId;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class PriceOverride
{
	public static PriceOverride none()
	{
		return NONE;
	}

	public static PriceOverride basePricingSystem(@NonNull final PricingSystemId pricingSystemId, @NonNull final BigDecimal basePriceAddAmt)
	{
		return new PriceOverride(PriceOverrideType.BASE_PRICING_SYSTEM, pricingSystemId, basePriceAddAmt, /* fixedPrice */null);
	}

	public static PriceOverride fixedPrice(@NonNull final Money fixedPrice)
	{
		return new PriceOverride(PriceOverrideType.FIXED_PRICE, /* pricingSystemId */null, /* basePriceAddAmt */null, fixedPrice);
	}

	public static PriceOverride fixedPriceOrNone(@Nullable final Money fixedPrice)
	{
		if (fixedPrice == null)
		{
			return NONE;
		}
		return fixedPrice(fixedPrice);
	}

	public static PriceOverride fixedZeroPrice(@NonNull final CurrencyId fixedPriceCurrencyId)
	{
		return new PriceOverride(PriceOverrideType.FIXED_PRICE, /* pricingSystemId */null, /* basePriceAddAmt */null, /* fixedPrice */Money.zero(fixedPriceCurrencyId));
	}

	private static final PriceOverride NONE = new PriceOverride();

	@NonNull
	PriceOverrideType type;

	PricingSystemId basePricingSystemId;

	/** Optional, if type= {@link PriceOverrideType#BASE_PRICING_SYSTEM}. */
	BigDecimal basePriceAddAmt;

	Money fixedPrice;

	/** creates an "empty" instance */
	private PriceOverride()
	{
		type = PriceOverrideType.NONE;

		fixedPrice = null;

		basePricingSystemId = null;
		basePriceAddAmt = null;
	}

	private PriceOverride(
			@NonNull final PriceOverrideType type,

			final PricingSystemId basePricingSystemId,
			final BigDecimal basePriceAddAmt,

			final Money fixedPrice)
	{
		this.type = type;

		this.basePricingSystemId = basePricingSystemId;
		this.basePriceAddAmt = NumberUtils.stripTrailingDecimalZeros(basePriceAddAmt);

		this.fixedPrice = fixedPrice;
	}

	public boolean isNoPrice()
	{
		return type == PriceOverrideType.NONE;
	}

}
