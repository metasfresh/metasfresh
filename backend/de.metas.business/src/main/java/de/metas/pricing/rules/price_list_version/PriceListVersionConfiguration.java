/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.rules.price_list_version;

import de.metas.logging.LogManager;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.service.ProductPriceQuery;
import de.metas.pricing.service.ProductPrices;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class PriceListVersionConfiguration
{
	private static final Logger logger = LogManager.getLogger(PriceListVersionConfiguration.class);

	private static Supplier<IPricingRule> huPricingRuleFactory = null;

	public static void setupHUPricing(
			@NonNull final Supplier<IPricingRule> huPricingRuleFactory,
			@NonNull final ProductPriceQuery.IProductPriceQueryMatcher mainProductPriceMatcher)
	{
		PriceListVersionConfiguration.huPricingRuleFactory = huPricingRuleFactory;
		logger.info("Registered HUPricing factory: {}", huPricingRuleFactory);

		ProductPrices.registerMainProductPriceMatcher(mainProductPriceMatcher);
	}

	public static Supplier<IPricingRule> getHUPricingRuleFactory()
	{
		return huPricingRuleFactory;
	}

	public static void reset()
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new AdempiereException("Resetting PriceListVersion configuration is allowed only when running in JUnit mode");
		}

		ProductPrices.clearMainProductPriceMatchers();

		PriceListVersionConfiguration.huPricingRuleFactory = null;
		logger.info("Configuration reset");
	}
}
