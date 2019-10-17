package de.metas.pricing.rules;

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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.currency.CurrencyPrecision;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.ToString;

/**
 * Mocked {@link IPricingRule} implementation to be used in testing.
 *
 * Make sure you have called {@link #reset()} before starting to use it.
 *
 * @author tsa
 *
 */
@ToString
public class MockedPricingRule implements IPricingRule
{
	public static final MockedPricingRule INSTANCE = new MockedPricingRule();

	public final BigDecimal priceToReturnInitial = Env.ONEHUNDRED;

	/** Default price to return */
	public BigDecimal priceToReturn = priceToReturnInitial;

	private CurrencyPrecision precision;

	private final Map<ProductId, I_C_UOM> productId2priceUOM = new HashMap<>();

	/** M_Product_ID to "price" to return" */
	private final Map<ProductId, BigDecimal> productId2price = new HashMap<>();

	/**
	 * Reset it to inital state.
	 */
	public void reset()
	{
		priceToReturn = priceToReturnInitial;
		productId2price.clear();
	}

	public void setC_UOM(final I_M_Product product, final I_C_UOM uom)
	{
		productId2priceUOM.put(ProductId.ofRepoId(product.getM_Product_ID()), uom);
	}

	public void setPrecision(CurrencyPrecision precision)
	{
		this.precision = precision;
	}

	public void setProductPrice(final I_M_Product product, final BigDecimal price)
	{
		productId2price.put(ProductId.ofRepoId(product.getM_Product_ID()), price);
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
		final ProductId productId = pricingCtx.getProductId();
		BigDecimal price = productId2price.get(productId);
		if (price == null)
		{
			price = priceToReturn;
		}

		result.setPriceLimit(price);
		result.setPriceList(price);
		result.setPriceStd(price);

		result.setPrecision(precision);

		result.setTaxCategoryId(TaxCategoryId.ofRepoId(100));

		final I_C_UOM priceUOM = productId2priceUOM.get(productId);
		if (priceUOM != null)
		{
			result.setPriceUomId(UomId.ofRepoId(priceUOM.getC_UOM_ID()));
		}

		result.setCalculated(true);
	}
}
