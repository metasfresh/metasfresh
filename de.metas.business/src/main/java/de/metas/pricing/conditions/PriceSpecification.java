package de.metas.pricing.conditions;

import static de.metas.util.Check.fail;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.money.CurrencyId;
import de.metas.pricing.PricingSystemId;
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

/**
 * Within a {@link PricingConditionsBreak} this class specifies how to compute the base price (without discounts).
 * Note: The actual price is <b>not</b> always included.
 */
@Value
public class PriceSpecification
{
	public static PriceSpecification none()
	{
		return NONE;
	}

	public static PriceSpecification basePricingSystem(
			@NonNull final PricingSystemId pricingSystemId,
			@Nullable final BigDecimal pricingSystemSurchargeAmt,
			@Nullable final CurrencyId currencyId)
	{
		return new PriceSpecification(
				PriceSpecificationType.BASE_PRICING_SYSTEM,
				pricingSystemId/* pricingSystemId */,
				pricingSystemSurchargeAmt,
				null/* fixedPriceAmt */,
				currencyId);
	}

	public static PriceSpecification fixedPrice(
			@Nullable final BigDecimal fixedPriceAmt,
			@Nullable final CurrencyId currencyId)
	{
		return new PriceSpecification(
				PriceSpecificationType.FIXED_PRICE,
				null/* pricingSystemId */,
				null/* basePriceAddAmt */,
				fixedPriceAmt,
				currencyId);
	}

	// public static PriceSpecification fixedPriceOrNone(
	// @Nullable final BigDecimal fixedPriceAmt,
	// @Nullable final CurrencyId curencyId)
	// {
	// if (fixedPriceAmt == null |)
	// {
	// return NONE;
	// }
	// return fixedPrice(fixedPriceAmt, curencyId);
	// }

	public static PriceSpecification fixedZeroPrice(@NonNull final CurrencyId fixedPriceCurrencyId)
	{
		return new PriceSpecification(
				PriceSpecificationType.FIXED_PRICE,
				null/* pricingSystemId */,
				null/* basePriceAddAmt */,
				ZERO/* fixedPrice */,
				fixedPriceCurrencyId);
	}

	private static final PriceSpecification NONE = new PriceSpecification(
			PriceSpecificationType.NONE,
			null/* basePricingSystemId */,
			null/* pricingSystemSurchargeAmt */,
			null/* fixedPriceAmt */,
			null/* currencyId */);

	PriceSpecificationType type;

	PricingSystemId basePricingSystemId;

	/** Optional if type={@link PriceSpecificationType#BASE_PRICING_SYSTEM}. */
	BigDecimal pricingSystemSurchargeAmt;

	/** Mandatory if type= {@link PriceSpecificationType#FIXED_PRICE}. */
	BigDecimal fixedPriceAmt;

	CurrencyId currencyId;

	boolean valid;

	private PriceSpecification(
			@NonNull final PriceSpecificationType type,

			@Nullable final PricingSystemId basePricingSystemId,
			@Nullable final BigDecimal pricingSystemSurchargeAmt,

			@Nullable final BigDecimal fixedPriceAmt,

			@Nullable final CurrencyId currencyId)
	{
		this.type = type;
		this.basePricingSystemId = basePricingSystemId;
		this.pricingSystemSurchargeAmt = pricingSystemSurchargeAmt;

		this.fixedPriceAmt = fixedPriceAmt;

		this.currencyId = currencyId;

		final boolean currencyIdGiven = currencyId != null;
		switch (type)
		{
			case NONE:

				this.valid = true;
				break;

			case BASE_PRICING_SYSTEM:

				final boolean surchargeAmtGiven = pricingSystemSurchargeAmt != null;
				this.valid = basePricingSystemId != null && (surchargeAmtGiven == currencyIdGiven);
				break;

			case FIXED_PRICE:

				final boolean fixedPriceAmtGiven = fixedPriceAmt != null;
				this.valid = fixedPriceAmtGiven && currencyIdGiven;
				break;

			default:
				fail("Unexpected type={}", type);
				valid = false;
				break;
		}

		// TODO: add invalidReason
	}

	public boolean isNoPrice()
	{
		return type == PriceSpecificationType.NONE;
	}
}
