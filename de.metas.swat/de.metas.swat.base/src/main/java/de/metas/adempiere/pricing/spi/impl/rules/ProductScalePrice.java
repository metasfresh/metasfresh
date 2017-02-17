package de.metas.adempiere.pricing.spi.impl.rules;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.api.ProductPriceQuery;
import org.adempiere.pricing.spi.impl.rules.AbstractPriceListBasedRule;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MProduct;

import de.metas.product.IProductPA;

/**
 * Calculate price using {@link I_M_ProductScalePrice}
 * 
 * @author tsa
 * 
 */
public class ProductScalePrice extends AbstractPriceListBasedRule
{
	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!super.applies(pricingCtx, result))
		{
			return false;
		}

		if (pricingCtx.getM_PriceList_Version_ID() <= 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final I_M_PriceList_Version priceListVersion = pricingCtx.getM_PriceList_Version();
		if (priceListVersion == null)
		{
			return;
		}
		
		final I_M_ProductPrice productPrice = ProductPriceQuery.newInstance(priceListVersion)
				.setM_Product_ID(pricingCtx.getM_Product_ID())
				.noAttributePricing()
				.onlyScalePrices()
				.firstMatching();
		if (productPrice == null)
		{
			return;
		}

		if (!productPrice.isUseScalePrice())
		{
			return;
		}

		final I_M_ProductScalePrice scalePrice = Services.get(IProductPA.class)
				.retrieveOrCreateScalePrices(productPrice.getM_ProductPrice_ID()
						, pricingCtx.getQty()
						, false // createNew
						, ITrx.TRXNAME_None);
		if (scalePrice == null)
		{
			return;
		}

		calculateWithScalePrice(pricingCtx, result, scalePrice);

		result.setC_TaxCategory_ID(productPrice.getC_TaxCategory_ID());
	}

	private IPricingResult calculateWithScalePrice(
			final IPricingContext pricingCtx,
			final IPricingResult result,
			final I_M_ProductScalePrice scalePrice)
	{
		Check.assumeNotNull(scalePrice, "scalePrice not null");
		
		final Properties ctx = pricingCtx.getCtx();
		final int m_M_Product_ID = pricingCtx.getM_Product_ID();
		int m_M_PriceList_Version_ID = pricingCtx.getM_PriceList_Version_ID();
		int m_M_PriceList_ID = pricingCtx.getM_PriceList_ID();
		//
		BigDecimal m_PriceStd = null;
		BigDecimal m_PriceList = null;
		BigDecimal m_PriceLimit = null;
		int m_C_UOM_ID = -1;
		int m_C_Currency_ID = -1;
		boolean m_enforcePriceLimit = false;
		boolean m_isTaxIncluded = false;
		//
		//

		int m_pp_C_UOM_ID = scalePrice.getM_ProductPrice().getC_UOM_ID();
		m_PriceStd = scalePrice.getPriceStd();
		m_PriceList = scalePrice.getPriceList();
		m_PriceLimit = scalePrice.getPriceLimit();
		final MProduct prod = MProduct.get(ctx, m_M_Product_ID);
		m_C_UOM_ID = prod.getC_UOM_ID();

		if (m_M_PriceList_ID <= 0)
		{
			final I_M_PriceList_Version priceListVersion = pricingCtx.getM_PriceList_Version();
			m_M_PriceList_ID = priceListVersion.getM_PriceList_ID();
		}

		if (m_M_PriceList_Version_ID <= 0)
		{
			m_M_PriceList_Version_ID = scalePrice.getM_ProductPrice().getM_PriceList_Version_ID();
		}

		// TODO handle bom-prices for products that don't have a price themselves.

		final I_M_PriceList priceList = Services.get(IPriceListDAO.class).retrievePriceList(ctx, m_M_PriceList_ID);
		m_C_Currency_ID = priceList.getC_Currency_ID();
		m_enforcePriceLimit = priceList.isEnforcePriceLimit();
		m_isTaxIncluded = priceList.isTaxIncluded();

		result.setPriceStd(m_PriceStd);
		result.setPriceList(m_PriceList);
		result.setPriceLimit(m_PriceLimit);
		result.setC_Currency_ID(m_C_Currency_ID);
		result.setEnforcePriceLimit(m_enforcePriceLimit);
		result.setTaxIncluded(m_isTaxIncluded);
		result.setCalculated(true);

		// 06942 : use product price uom all the time
		if (m_pp_C_UOM_ID <= 0)
		{
			result.setPrice_UOM_ID(m_C_UOM_ID);
		}
		else
		{
			result.setPrice_UOM_ID(m_pp_C_UOM_ID);
		}
		return result;
	}
}
