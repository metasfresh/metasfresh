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


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.IPricingRule;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

/**
 * Mocked {@link IPricingRule} implementation to be used in testing.
 *
 * Make sure you have called {@link #reset()} before starting to use it.
 *
 * @author tsa
 *
 */
public class MockedPricingRule extends PricingRuleAdapter
{
	public static final BigDecimal priceToReturnInitial = Env.ONEHUNDRED;

	/** Default price to return */
	public static BigDecimal priceToReturn = priceToReturnInitial;

	private static int precision;

	/** M_Product_ID to "price" to return" */
	private static final Map<Integer, BigDecimal> productId2price = new HashMap<>();

	/**
	 * Reset it to inital state.
	 */
	public static void reset()
	{
		priceToReturn = priceToReturnInitial;
		productId2price.clear();
	}

	public static void setPrecision(int precision)
	{
		MockedPricingRule.precision = precision;
	}

	public static void setProductPrice(final I_M_Product product, final BigDecimal price)
	{
		productId2price.put(product.getM_Product_ID(), price);
	}

	@Override
	public boolean applies(IPricingContext pricingCtx, IPricingResult result)
	{
		return true;
	}

	@Override
	public void calculate(IPricingContext pricingCtx, IPricingResult result)
	{
		//
		// Check product price
		BigDecimal price = productId2price.get(pricingCtx.getM_Product_ID());

		if (price == null)
		{
			price = priceToReturn;
		}

		result.setPriceLimit(price);
		result.setPriceList(price);
		result.setPriceStd(price);

		result.setPrecision(precision);

		result.setC_TaxCategory_ID(100);

		result.setCalculated(true);

		// final I_C_UOM uom = InterfaceWrapperHelper.create(Env.getCtx(), I_C_UOM.class, ITrx.TRXNAME_None);
		// InterfaceWrapperHelper.save(uom);
		// result.setC_UOM_ID(uom.getC_UOM_ID());
	}
}
