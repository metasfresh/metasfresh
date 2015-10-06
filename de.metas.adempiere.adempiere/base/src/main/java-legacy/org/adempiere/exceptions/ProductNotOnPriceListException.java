/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 Teo Sarca. All Rights Reserved.                         *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.exceptions;

import java.sql.Timestamp;
import java.text.DateFormat;

import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 * Throw when product price is not found in price list
 * @author teo.sarca@gmail.com
 *			<li>FR [ 2872255 ] Introduce ProductNotOnPriceListException
 *				https://sourceforge.net/tracker/?func=detail&aid=2872255&group_id=176962&atid=879335
 */
public class ProductNotOnPriceListException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3505579126676698444L;
	
	public static final String AD_Message = "ProductNotOnPriceList";
	
	/**
	 * 
	 * @param productPricing
	 * @param documentLineNo ignored if <= 0
	 */
	public ProductNotOnPriceListException(MProductPricing productPricing, int documentLineNo)
	{
		super(buildMessage(productPricing, documentLineNo));
	}

	private static final String buildMessage (MProductPricing pp, int documentLineNo)
	{
		final int m_Product_ID = pp.getM_Product_ID();
		final int m_PriceList_ID = pp.getM_PriceList_ID();
		final Timestamp priceDate = pp.getPriceDate();
		
		return buildMessage(documentLineNo, m_Product_ID, m_PriceList_ID, priceDate);
	}

	public static String buildMessage(int documentLineNo, final int m_Product_ID, final int m_PriceList_ID, final Timestamp priceDate)
	{
		final StringBuilder sb = new StringBuilder();
		if (documentLineNo > 0)
		{
			if (sb.length() > 0)
				sb.append(", ");
			sb.append("@Line@:").append(documentLineNo);
		}
		if (m_Product_ID > 0)
		{
			MProduct p = MProduct.get(Env.getCtx(), m_Product_ID);
			if (sb.length() > 0)
				sb.append(", ");
			sb.append("@M_Product_ID@:").append(p == null ? "?" : p.get_Translation(MProduct.COLUMNNAME_Name));
		}
		if (m_PriceList_ID > 0)
		{
			MPriceList pl = MPriceList.get(Env.getCtx(), m_PriceList_ID, null);
			if (sb.length() > 0)
				sb.append(", ");
			sb.append("@M_PriceList_ID@:").append(pl == null ? "?" : pl.get_Translation(MPriceList.COLUMNNAME_Name));
		}
		if (priceDate != null)
		{
			DateFormat df = DisplayType.getDateFormat(DisplayType.Date);
			if (sb.length() > 0)
				sb.append(", ");
			sb.append("@Date@:").append(df.format(priceDate));
		}
		//
		sb.insert(0, "@"+AD_Message+"@ - ");
		return sb.toString();
	}
}
