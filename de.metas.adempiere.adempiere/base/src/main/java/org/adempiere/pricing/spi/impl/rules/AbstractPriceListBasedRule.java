package org.adempiere.pricing.spi.impl.rules;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.logging.Level;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.compiere.model.MPriceList;
import org.compiere.util.CLogger;
import org.compiere.util.Trace;

public abstract class AbstractPriceListBasedRule extends PricingRuleAdapter
{
	protected final transient CLogger log = CLogger.getCLogger(getClass());
	
	@Override
	@OverridingMethodsMustInvokeSuper
	public boolean applies(IPricingContext pricingCtx, IPricingResult result)
	{
		if (result.isCalculated())
		{
			log.fine("Not applying because already calculated");
			return false;
		}
		
		if (pricingCtx.getM_Product_ID() <= 0)
		{
			log.fine("Not applying because there is no M_Product_ID specified in context");
			return false;
		}
		
		if (pricingCtx.getM_PriceList_ID() <= 0)
		{
			log.log(Level.SEVERE, "No PriceList");
			Trace.printStack();
			return false; //false;
		}
		
		if (pricingCtx.getM_PriceList_ID() == MPriceList.M_PriceList_ID_None)
		{
			log.log(Level.INFO, "Not applying because PriceList is NoPriceList ({0})", pricingCtx);
			return false;
		}

		return true;
	}
}
