package de.metas.pricing.conditions;

import javax.annotation.Nullable;

import de.metas.money.Money;
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

	public static PriceSpecification basePricingSystem(@NonNull final PricingSystemId pricingSystemId)
	{
		final Money pricingSystemSurcharge = null;
		return basePricingSystem(pricingSystemId, pricingSystemSurcharge);
	}

	public static PriceSpecification basePricingSystem(
			@NonNull final PricingSystemId pricingSystemId,
			@Nullable final Money pricingSystemSurcharge)
	{
		return new PriceSpecification(
				PriceSpecificationType.BASE_PRICING_SYSTEM,
				pricingSystemId/* pricingSystemId */,
				pricingSystemSurcharge /* pricingSystemSurcharge */,
				null/* fixedPriceAmt */
		);
	}

	public static PriceSpecification fixedPrice(@NonNull final Money fixedPrice)
	{
		return new PriceSpecification(
				PriceSpecificationType.FIXED_PRICE,
				null/* pricingSystemId */,
				null/* basePriceAddAmt */,
				fixedPrice/* fixedPriceAmt */
		);
	}

	private static final PriceSpecification NONE = new PriceSpecification(
			PriceSpecificationType.NONE,
			null/* basePricingSystemId */,
			null/* pricingSystemSurchargeAmt */,
			null/* fixedPriceAmt */);

	PriceSpecificationType type;

	//
	// Base pricing system related fields
	PricingSystemId basePricingSystemId;
	Money pricingSystemSurcharge;

	//
	// Fixed price related fields
	Money fixedPrice;

	private PriceSpecification(
			@NonNull final PriceSpecificationType type,

			@Nullable final PricingSystemId basePricingSystemId,
			@Nullable final Money pricingSystemSurcharge,
			@Nullable final Money fixedPrice)
	{
		this.type = type;

		this.basePricingSystemId = basePricingSystemId;
		this.pricingSystemSurcharge = pricingSystemSurcharge;

		this.fixedPrice = fixedPrice;
	}

	public boolean isNoPrice()
	{
		return type == PriceSpecificationType.NONE;
	}
}
