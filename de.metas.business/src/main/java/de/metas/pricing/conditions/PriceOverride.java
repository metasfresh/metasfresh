package de.metas.pricing.conditions;

import java.math.BigDecimal;

import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;

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

	public static PriceOverride basePricingSystem(final int pricingSystemId, @NonNull final BigDecimal basePriceAddAmt)
	{
		Check.assumeGreaterThanZero(pricingSystemId, "pricingSystemId");
		return new PriceOverride(PriceOverrideType.BASE_PRICING_SYSTEM, /* fixedPrice */null, pricingSystemId, basePriceAddAmt);
	}

	public static PriceOverride fixedPrice(@NonNull final BigDecimal fixedPrice)
	{
		if (fixedPrice.signum() == 0)
		{
			return ZERO;
		}
		return new PriceOverride(PriceOverrideType.FIXED_PRICE, fixedPrice, /* pricingSystemId */-1, /* basePriceAddAmt */null);
	}

	public static PriceOverride fixedZeroPrice()
	{
		return ZERO;
	}

	private static final PriceOverride NONE = new PriceOverride();
	private static final PriceOverride ZERO = new PriceOverride(PriceOverrideType.FIXED_PRICE, /* fixedPrice */BigDecimal.ZERO, /* pricingSystemId */-1, /* basePriceAddAmt */null);

	@NonNull
	PriceOverrideType type;
	int basePricingSystemId;
	BigDecimal basePriceAddAmt;
	BigDecimal fixedPrice;

	private PriceOverride()
	{
		type = PriceOverrideType.NONE;
		fixedPrice = null;
		basePricingSystemId = -1;
		basePriceAddAmt = null;
	}

	private PriceOverride(
			@NonNull final PriceOverrideType type,
			final BigDecimal fixedPrice,
			final int basePricingSystemId,
			final BigDecimal basePriceAddAmt)
	{
		this.type = type;
		this.fixedPrice = NumberUtils.stripTrailingDecimalZeros(fixedPrice);
		this.basePricingSystemId = basePricingSystemId > 0 ? basePricingSystemId : -1;
		this.basePriceAddAmt = NumberUtils.stripTrailingDecimalZeros(basePriceAddAmt);
	}

	public boolean isNoPrice()
	{
		return type == PriceOverrideType.NONE;
	}

}
