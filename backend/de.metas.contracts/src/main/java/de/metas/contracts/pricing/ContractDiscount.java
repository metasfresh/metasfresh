package de.metas.contracts.pricing;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.slf4j.Logger;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.logging.LogManager;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.util.lang.Percent;

/**
 * This pricing discount rule applies if
 * the {@link IPricingContext} instances that has a <code>referencedObject</code> which has a
 * {@link I_C_Flatrate_Conditions} with {@link I_C_Flatrate_Conditions#isFreeOfCharge()}<code>==true</code>
 *
 *
 * In this case, the rule's {@link #calculate(IPricingContext, IPricingResult)} sets the discount to 100%.
 *
 */
public class ContractDiscount implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(ContractDiscount.class);

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!result.isCalculated())
		{
			logger.debug("Cannot apply discount if the price was not calculated - {}", result);
			return false;
		}
		if (result.isDisallowDiscount())
		{
			logger.debug("Discounts are not allowed [SKIP]");
			return false;
		}

		final Object referencedObject = pricingCtx.getReferencedObject();
		if (referencedObject == null)
		{
			logger.debug("Not applying because pricingCtx has no referencedObject");
			return false;
		}

		final I_C_Flatrate_Conditions conditions = ContractPricingUtil.getC_Flatrate_Conditions(referencedObject);
		if (conditions == null)
		{
			logger.debug("Not applying because referencedObject='{}' has no C_Flatrate_Conditions", referencedObject);
			return false;
		}

		return conditions.isFreeOfCharge();
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		result.setDiscount(Percent.ONE_HUNDRED);
	}
}
