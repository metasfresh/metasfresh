package de.metas.pricing.limit;

import java.math.BigDecimal;

import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.Builder;
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
public class PriceLimitRuleResult
{
	public static PriceLimitRuleResult notApplicable(@NonNull final String reason)
	{
		return notApplicable(TranslatableStrings.anyLanguage(reason));
	}

	public static PriceLimitRuleResult notApplicable(@NonNull final ITranslatableString reason)
	{
		return PriceLimitRuleResult._builder()
				.applicable(false)
				.notApplicableReason(reason)
				.build();
	}

	public static PriceLimitRuleResult priceLimit(@NonNull final BigDecimal priceLimit, final String priceLimitExplanation)
	{
		return priceLimit(priceLimit, TranslatableStrings.anyLanguage(priceLimitExplanation));
	}

	public static PriceLimitRuleResult priceLimit(@NonNull final BigDecimal priceLimit, @NonNull final ITranslatableString priceLimitExplanation)
	{
		return PriceLimitRuleResult._builder()
				.applicable(true)
				.priceLimit(priceLimit)
				.priceLimitExplanation(priceLimitExplanation)
				.build();
	}

	private boolean applicable;
	private ITranslatableString notApplicableReason;

	private BigDecimal priceLimit;
	/** Explanation about how the price limit was calculated, from where it comes etc */
	private ITranslatableString priceLimitExplanation;

	@Builder(builderMethodName = "_builder")
	private PriceLimitRuleResult(
			final boolean applicable,
			final ITranslatableString notApplicableReason,
			final BigDecimal priceLimit,
			final ITranslatableString priceLimitExplanation)
	{
		if (applicable)
		{
			Check.assumeNotNull(priceLimit, "Parameter priceLimit is not null");
			Check.assumeNotNull(priceLimitExplanation, "Parameter priceLimitExplanation is not null");

			this.applicable = true;
			this.priceLimit = priceLimit;
			this.priceLimitExplanation = priceLimitExplanation;

			this.notApplicableReason = null;
		}
		else
		{
			Check.assumeNotNull(notApplicableReason, "Parameter notApplicableReason is not null");

			this.applicable = false;
			this.notApplicableReason = notApplicableReason;

			this.priceLimit = null;
			this.priceLimitExplanation = null;
		}
	}

	public BooleanWithReason checkApplicableAndBelowPriceLimit(@NonNull final BigDecimal priceActual)
	{
		if (!applicable)
		{
			return BooleanWithReason.falseBecause(notApplicableReason);
		}

		if (priceLimit.signum() == 0)
		{
			return BooleanWithReason.falseBecause("limit price is ZERO");
		}

		final boolean belowPriceLimit = priceActual.compareTo(priceLimit) < 0;
		if (belowPriceLimit)
		{
			return BooleanWithReason.trueBecause(priceLimitExplanation);
		}
		else
		{
			return BooleanWithReason.falseBecause("Price " + priceActual + " is above " + priceLimit + "(limit price)");
		}
	}
}
