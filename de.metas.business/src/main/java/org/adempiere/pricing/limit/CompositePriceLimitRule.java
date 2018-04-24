package org.adempiere.pricing.limit;

import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArrayList;

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

		return enforcers.stream()
				.map(enforcer -> enforcer.compute(context))
				.filter(result -> result.isBelowPriceLimit(priceActual))
				.findFirst()
				.orElseGet(() -> computeDefault(context));
	}

	private PriceLimitRuleResult computeDefault(final PriceLimitRuleContext context)
	{
		final BigDecimal priceLimit = context.getPriceLimit();
		if (priceLimit.signum() == 0)
		{
			return PriceLimitRuleResult.notApplicable("default PriceLimit=0 is not eligible");
		}
		return PriceLimitRuleResult.priceLimit(priceLimit, "pricing PriceLimit (default)");
	}
}
