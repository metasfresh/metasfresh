package de.metas.pricing.rules;

import org.adempiere.util.Loggables;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;

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

public class BOMPriceListVersion implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(BOMPriceListVersion.class);

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (result.isCalculated())
		{
			logger.debug("Not applying because already calculated");
			return false;
		}

		if (pricingCtx.getProductId() == null)
		{
			logger.debug("Not applying because there is no M_Product_ID specified in context");
			return false;
		}

		if (pricingCtx.getPriceListId() == null)
		{
			final String msg = "pricingCtx {} contains no priceList";
			Loggables.get().addLog(msg, pricingCtx);
			logger.error(msg, pricingCtx);
			Trace.printStack();
			return false;
		}

		if (pricingCtx.getPriceListId().isNone())
		{
			logger.info("Not applying because PriceList is NoPriceList ({})", pricingCtx);
			return false;
		}
		
		// TODO: check the Product BOM Type

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
//		Services.get(IProductBOMDAO.class).retrieveLines(productBOM, date);
		// TODO Auto-generated method stub

	}

}
