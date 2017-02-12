package org.adempiere.pricing.exceptions;

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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.MProduct;
import org.compiere.util.Env;

public class ProductNotOnPriceListException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4934046895442638053L;

	/**
	 * 
	 * @param pricingCtx
	 * @param documentLineNo ignored if <=0
	 */
	public ProductNotOnPriceListException(IPricingContext pricingCtx, int documentLineNo)
	{
		super(buildMessage(pricingCtx, documentLineNo));
	}

	public ProductNotOnPriceListException(final I_M_PriceList_Version plv, final int productId)
	{
		super(buildMessage(plv, productId));
	}

	private static String buildMessage(IPricingContext pricingCtx, int documentLineNo)
	{
		return org.adempiere.exceptions.ProductNotOnPriceListException.buildMessage(documentLineNo,
				pricingCtx.getM_Product_ID(),
				pricingCtx.getM_PriceList_ID(),
				pricingCtx.getPriceDate());
	}

	private static String buildMessage(final I_M_PriceList_Version plv, final int productId)
	{
		final StringBuilder sb = new StringBuilder("@NotFound@ @M_ProductPrice_ID@");

		//
		// Product
		final Properties ctx = plv == null ? Env.getCtx() : InterfaceWrapperHelper.getCtx(plv);
		final I_M_Product product = MProduct.get(ctx, productId);
		sb.append("\n@M_Product_ID@: ").append(product == null ? "<" + productId + ">" : product.getName());

		//
		// Price List Version
		sb.append("\n@M_PriceList_Version_ID@: ").append(plv == null ? "-" : plv.getName());

		//
		// Price List
		final I_M_PriceList priceList = plv == null ? null : plv.getM_PriceList();
		sb.append("\n@M_PriceList_ID@: ").append(priceList == null ? "-" : priceList.getName());

		//
		// Pricing System
		final I_M_PricingSystem pricingSystem = priceList == null ? null : priceList.getM_PricingSystem();
		sb.append("\n@M_PricingSystem_ID@: ").append(pricingSystem == null ? "-" : pricingSystem.getName());

		return sb.toString();
	}
}
