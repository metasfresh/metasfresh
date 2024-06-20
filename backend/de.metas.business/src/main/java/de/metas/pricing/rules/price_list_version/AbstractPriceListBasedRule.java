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
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.util.Loggables;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import javax.annotation.OverridingMethodsMustInvokeSuper;

abstract class AbstractPriceListBasedRule implements IPricingRule
{
	private static final Logger log = LogManager.getLogger(AbstractPriceListBasedRule.class);

	@Override
	@OverridingMethodsMustInvokeSuper
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (result.isCalculated())
		{
			log.debug("Not applying because already calculated");
			return false;
		}

		if (pricingCtx.getProductId() == null)
		{
			log.debug("Not applying because there is no M_Product_ID specified in context");
			return false;
		}

		if (pricingCtx.getPriceListId() == null)
		{
			final String msg = "pricingCtx {} contains no priceList";
			Loggables.addLog(msg, pricingCtx);
			log.error(msg, pricingCtx);
			Trace.printStack();
			return false; // false;
		}

		if (pricingCtx.getPriceListId().isNone())
		{
			log.info("Not applying because PriceList is NoPriceList ({})", pricingCtx);
			return false;
		}

		return true;
	}
}
