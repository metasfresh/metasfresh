package org.adempiere.pricing.api;

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


import java.math.BigDecimal;

import org.adempiere.pricing.limit.IPriceLimitRule;
import org.adempiere.pricing.limit.PriceLimitRuleContext;
import org.adempiere.pricing.limit.PriceLimitRuleResult;
import org.adempiere.pricing.spi.IPricingRule;
import org.adempiere.util.ISingletonService;

public interface IPricingBL extends ISingletonService
{

	/**
	 * Error message for setting product price to both scale and attribute.
	 */
	public static final String PRODUCTPRICE_FLAG_ERROR = "de.metas.pricing.flagError";

	/**
	 * Creates an editable pricing context
	 * 
	 * @return
	 */
	IEditablePricingContext createPricingContext();

	/**
	 * Creates and editable pricing context, initialized with given values.
	 * 
	 * @param M_Product_ID
	 * @param C_BPartner_ID
	 * @param C_UOM_ID TODO
	 * @param Qty
	 * @param isSOTrx
	 * @return
	 */
	IEditablePricingContext createInitialContext(int M_Product_ID, int C_BPartner_ID, int C_UOM_ID, BigDecimal Qty, boolean isSOTrx);

	/**
	 * Calculate pricing
	 * 
	 * @param pricingCtx
	 * @return
	 */
	IPricingResult calculatePrice(IPricingContext pricingCtx);

	/**
	 * Creates an initial {@link IPricingResult}. Copies some of the given <code>pricingCtx</code>'s properties to the pricing result.
	 * 
	 * This result will be updated later by {@link IPricingRule}s
	 * 
	 * @param pricingCtx
	 * @return
	 */
	IPricingResult createInitialResult(IPricingContext pricingCtx);

	PriceLimitRuleResult computePriceLimit(PriceLimitRuleContext context);

	void registerPriceLimitRule(IPriceLimitRule rule);
}
