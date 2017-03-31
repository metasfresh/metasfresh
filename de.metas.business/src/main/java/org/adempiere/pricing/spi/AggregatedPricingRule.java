/**
 *
 */
package org.adempiere.pricing.spi;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Helper class which aggregates multiple {@link IPricingRule}s.
 *
 * @author tsa
 *
 */
public final class AggregatedPricingRule implements IPricingRule
{
	private static final transient Logger logger = LogManager.getLogger(AggregatedPricingRule.class);

	private final List<IPricingRule> rules = new ArrayList<IPricingRule>();

	/**
	 * Add a {@link IPricingRule} child.
	 *
	 * Please note that the <code>rule</code> won't be added if
	 * <ul>
	 * <li>rule was already added
	 * <li>an instance of the same class as given rule was already added
	 * </ul>
	 *
	 * @param rule
	 */
	public void addPricingRule(final IPricingRule rule)
	{
		if (rule == null)
		{
			return;
		}
		if (rules.contains(rule))
		{
			return;
		}

		for (final IPricingRule r : rules)
		{
			if (rule.equals(r))
			{
				logger.debug("PricingRule already registered: " + rule + " [SKIP]");
				return;
			}
			if (rule.getClass().equals(r.getClass()))
			{
				logger.debug("PricingRule with same class already registered: " + rule + " (class=" + rule.getClass() + ") [SKIP]");
				return;
			}
		}

		logger.trace("PricingRule registered: {}", rule);
		rules.add(rule);
	}

	/**
	 * For optimization reasons, this method always returns true.
	 *
	 * In {@link #calculate(IPricingContext, IPricingResult)}, each child {@link IPricingRule} is evaluated and executed if applies.
	 *
	 * @return always returns true
	 * @see org.adempiere.pricing.spi.IPricingRule#applies(org.adempiere.pricing.api.IPricingContext, org.adempiere.pricing.api.IPricingResult)
	 */
	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		return true;
	}

	/**
	 * Executes all rules that can be applied.
	 *
	 * Please note that calculation won't stop after first rule that matched.
	 */
	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		logger.debug("Evaluating prcing rules with pricingContext: {}", pricingCtx);

		for (final IPricingRule rule : rules)
		{
			// NOTE: we are NOT checking if the pricing result was already calculated, on purpose, because:
			// * we want to give flexiblity to pricing rules to override the pricing
			// * we want to support the case of Discount rules which apply on already calculated pricing result

			//
			// Preliminary check if there is a chance this pricing rule to be applied
			if (!rule.applies(pricingCtx, result))
			{
				logger.debug("Skipped rule {}, result: {}", rule, result);
				continue;
			}

			//
			// Try applying it
			rule.calculate(pricingCtx, result);

			//
			// Add it to applied pricing rules list
			// FIXME: make sure the rule was really applied (i.e. calculated). Consider asking the calculate() to return a boolean if it really did some changes.
			// At the moment, there is no way to figure out that a pricing rule which was preliminary considered as appliable
			// was not actually applied because when "calculate()" method was invoked while retrieving data,
			// it found out that it cannot be applied.
			// As a side effect on some pricing results you will get a list of applied rules like: ProductScalePrice, PriceListVersionVB, PriceListVersion, Discount,
			// which means that ProductScalePrice and PriceListVersionVB were not actually applied because they found out that while doing the "calculate()".
			result.getRulesApplied().add(rule);
			logger.debug("Applied rule {}, result: {}", rule, result);
		}
	}

	@Override
	public String toString()
	{
		return "AggregatedPricingRule[" + rules + "]";
	}
}
