package org.adempiere.pricing.exceptions;

import java.sql.Timestamp;
import java.text.DateFormat;

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

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

@SuppressWarnings("serial")
public class ProductNotOnPriceListException extends AdempiereException
{
	public static final String AD_Message = "ProductNotOnPriceList";

	/**
	 *
	 * @param pricingCtx
	 * @param documentLineNo ignored if <=0
	 */
	public ProductNotOnPriceListException(final IPricingContext pricingCtx, final int documentLineNo)
	{
		super(buildMessage(pricingCtx, documentLineNo));
	}

	public ProductNotOnPriceListException(final I_M_PriceList_Version plv, final int productId)
	{
		super(buildMessage(plv, productId));
	}

	@Deprecated
	public ProductNotOnPriceListException(final MProductPricing productPricing, final int documentLineNo)
	{
		super(buildMessage(productPricing, documentLineNo));
	}

	private static String buildMessage(final IPricingContext pricingCtx, final int documentLineNo)
	{
		return buildMessage(documentLineNo,
				pricingCtx.getM_Product_ID(),
				pricingCtx.getM_PriceList_ID(),
				pricingCtx.getPriceDate());
	}

	private static final String buildMessage(final MProductPricing pp, final int documentLineNo)
	{
		final int m_Product_ID = pp.getM_Product_ID();
		final int m_PriceList_ID = pp.getM_PriceList_ID();
		final Timestamp priceDate = pp.getPriceDate();

		return buildMessage(documentLineNo, m_Product_ID, m_PriceList_ID, priceDate);
	}

	protected static String buildMessage(final int documentLineNo, final int m_Product_ID, final int m_PriceList_ID, final Timestamp priceDate)
	{
		final StringBuilder sb = new StringBuilder();
		if (documentLineNo > 0)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@Line@:").append(documentLineNo);
		}
		if (m_Product_ID > 0)
		{
			final MProduct p = MProduct.get(Env.getCtx(), m_Product_ID);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@M_Product_ID@:").append(p == null ? "?" : p.getValue());
		}
		if (m_PriceList_ID > 0)
		{
			final MPriceList pl = MPriceList.get(Env.getCtx(), m_PriceList_ID, null);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@M_PriceList_ID@:").append(pl == null ? "?" : pl.getName());
		}
		if (priceDate != null)
		{
			final DateFormat df = DisplayType.getDateFormat(DisplayType.Date);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@Date@:").append(df.format(priceDate));
		}
		//
		sb.insert(0, "@" + AD_Message + "@ - ");
		return sb.toString();
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
