/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
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

import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.util.Env;

/**
 * Any exception that occurs when no UOM conversion rate was found
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class NoUOMConversionException extends AdempiereException
{
	public static final String AD_Message = "NoUOMConversion";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4868882017576097089L;

	public NoUOMConversionException(int M_Product_ID, int C_UOM_ID, int C_UOM_To_ID)
	{
		super(buildMessage(M_Product_ID, C_UOM_ID, C_UOM_To_ID));
	}

	private static String buildMessage(int M_Product_ID, int C_UOM_ID, int C_UOM_To_ID)
	{
		final StringBuilder sb = new StringBuilder("@"+AD_Message+"@ - ");
		
		//
		sb.append("@M_Product_ID@:");
		MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
		if (product != null)
		{
			sb.append(product.getValue()).append("_").append(product.getName());
		}
		//
		if (C_UOM_ID > 0 || product == null)
		{
			sb.append("  @C_UOM_ID@:");
			MUOM uom = MUOM.get(Env.getCtx(), C_UOM_ID);
			if (uom != null)
			{
				sb.append(uom.getUOMSymbol());
			}
		}
		//
		sb.append("  @C_UOM_To_ID@:");
		MUOM uomTo = MUOM.get(Env.getCtx(), C_UOM_To_ID);
		if (uomTo != null)
		{
			sb.append(uomTo.getUOMSymbol());
		}
		//
		return sb.toString();
	}

}
