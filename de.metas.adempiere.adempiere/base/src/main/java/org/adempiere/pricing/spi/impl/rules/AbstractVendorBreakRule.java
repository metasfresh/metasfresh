package org.adempiere.pricing.spi.impl.rules;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.compiere.model.I_M_ProductPriceVendorBreak;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

public abstract class AbstractVendorBreakRule extends AbstractPriceListBasedRule
{
	@Override
	public boolean applies(IPricingContext pricingCtx, IPricingResult result)
	{
		// Check parent if applies
		if (!super.applies(pricingCtx, result))
		{
			return false;
		}
		
		if (pricingCtx.isSOTrx())
		{
			log.fine("Not applying because IsSOTrx=Yes");
			return false;
		}

		if (!hasVendorBreaks(pricingCtx.getM_Product_ID(), pricingCtx.getC_BPartner_ID()))
		{
			log.fine("Not applying there are no vendor breaks defined");
			return false;
		}

		return true;
	}

	public boolean hasVendorBreaks(int productId, int bpartnerId)
	{
		if (productId <= 0 || bpartnerId <= 0)
		{
			return false;
		}
		
		final ArrayKey cacheKey = Util.mkKey(productId, bpartnerId);
		Boolean matched = vendorBreaksCache.get(cacheKey);
		if (matched == null)
		{
			matched = new Query(Env.getCtx(), I_M_ProductPriceVendorBreak.Table_Name,
					I_M_ProductPriceVendorBreak.COLUMNNAME_M_Product_ID + "=? AND " + I_M_ProductPriceVendorBreak.COLUMNNAME_C_BPartner_ID + "=?",
					ITrx.TRXNAME_None)
					.setParameters(productId, bpartnerId)
					.match();
			vendorBreaksCache.put(cacheKey, matched);
		}
		return matched.booleanValue();
	}

	private final CCache<ArrayKey, Boolean> vendorBreaksCache = new CCache<ArrayKey, Boolean>(I_M_ProductPriceVendorBreak.Table_Name + "_Match", 20);
}
