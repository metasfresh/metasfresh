package org.adempiere.pricing.spi;

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


import java.util.Iterator;

import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.adempiere.model.I_M_ProductPrice;

/**
 * Pluggable Pricing Rule interface.
 * 
 * NOTE to developer: instead of extending this interface, consider extending {@link PricingRuleAdapter}.
 * 
 * @author tsa
 * 
 */
public interface IPricingRule
{
	/**
	 * 
	 * @param pricingCtx
	 * @param result
	 * @return true if this pricing rule shall be applied
	 */
	boolean applies(final IPricingContext pricingCtx, final IPricingResult result);

	/**
	 * Calculate the pricing values based on pricingCtx and saves the result in <code>result</code>
	 * 
	 * @param pricingCtx
	 * @param result
	 */
	void calculate(IPricingContext pricingCtx, IPricingResult result);

	/**
	 * This method is called when a new price list version is created.
	 * It can modify the given <code>productPrices</code> according to the given discount schema line.
	 * 
	 * @param plv
	 * @param productPrices
	 * @param dsl
	 */
	void updateFromDiscounLine(final I_M_PriceList_Version plv, final Iterator<I_M_ProductPrice> productPrices, final I_M_DiscountSchemaLine dsl);
}
