package de.metas.pricing.limit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public class CompositePriceLimitRule implements IPriceLimitRule
{
	private final CopyOnWriteArrayList<IPriceLimitRule> enforcers = new CopyOnWriteArrayList<>();

	public void addEnforcer(@NonNull final IPriceLimitRule enforcer)
	{
		enforcers.addIfAbsent(enforcer);
	}

	@Override
	public PriceLimitRuleResult compute(@NonNull final PriceLimitRuleContext context)
	{
		final BigDecimal priceActual = context.getPriceActual();

		//
		// Check each enforcer
		PriceLimitRuleResult applicableResultWithMaxPriceLimit = null;
		final ArrayList<PriceLimitRuleResult> notApplicableResults = new ArrayList<>();
		for (final IPriceLimitRule enforcer : enforcers)
		{
			final PriceLimitRuleResult result = enforcer.compute(context);
			final BooleanWithReason applicable = result.checkApplicableAndBelowPriceLimit(priceActual);
			if (applicable.isTrue())
			{
				if (applicableResultWithMaxPriceLimit == null)
				{
					applicableResultWithMaxPriceLimit = result;
				}
				else if (applicableResultWithMaxPriceLimit.getPriceLimit().compareTo(result.getPriceLimit()) < 0)
				{
					applicableResultWithMaxPriceLimit = result;
				}
				else
				{
					// keep current applicableResultWithMaxPriceLimit
				}
			}
			else
			{
				notApplicableResults.add(result);
			}
		}

		//
		// Case: we have a applicable limit:
		if (applicableResultWithMaxPriceLimit != null)
		{
			return applicableResultWithMaxPriceLimit;
		}
		//
		// Case: we don't have an applicable limit but we have a limit from price list
		else if (context.getPriceLimit().signum() != 0)
		{
			return PriceLimitRuleResult.priceLimit(context.getPriceLimit(), "pricing PriceLimit (context/default)");
		}
		//
		// Case: found nothing applicable
		else
		{
			if (notApplicableResults.isEmpty())
			{
				return PriceLimitRuleResult.notApplicable("default PriceLimit=0 is not eligible");
			}
			else
			{
				return mergeNotApplicableResults(notApplicableResults);
			}

		}
	}

	private static PriceLimitRuleResult mergeNotApplicableResults(final List<PriceLimitRuleResult> notApplicableResults)
	{
		if (notApplicableResults.size() == 1)
		{
			return notApplicableResults.get(0);
		}

		final TranslatableStringBuilder builder = TranslatableStrings.builder();
		for (final PriceLimitRuleResult notApplicableResult : notApplicableResults)
		{
			if (!builder.isEmpty())
			{
				builder.append("; ");
			}
			builder.append(notApplicableResult.getNotApplicableReason());
		}

		return PriceLimitRuleResult.notApplicable(builder.build());
	}

	@Override
	public Set<CountryId> getPriceCountryIds()
	{
		return enforcers.stream()
				.flatMap(enforcer -> enforcer.getPriceCountryIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}
}
