/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2009 SC ARHIPAC SERVICE SRL. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.adempiere.exceptions;

import javax.annotation.Nullable;

import org.compiere.Adempiere;
import org.compiere.model.I_C_UOM;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;

/**
 * Any exception that occurs when no UOM conversion rate was found
 *
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class NoUOMConversionException extends AdempiereException
{
	public static final String AD_Message = "NoUOMConversion";

	private static final long serialVersionUID = -4868882017576097089L;

	public NoUOMConversionException(@Nullable final ProductId productId, @Nullable final UomId fromUomId, @Nullable final UomId toUomId)
	{
		super(buildMessage(productId, fromUomId, toUomId));
	}

	private static String buildMessage(@Nullable final ProductId productId, @Nullable final UomId fromUomId, @Nullable final UomId toUomId)
	{
		final StringBuilder sb = new StringBuilder("@" + AD_Message + "@ - ");

		//
		sb.append("@M_Product_ID@:").append(extractProductName(productId));

		//
		if (fromUomId != null)
		{
			sb.append("  @C_UOM_ID@:").append(extractUOMSymbol(fromUomId));
		}

		//
		if (toUomId != null)
		{
			sb.append("  @C_UOM_To_ID@:").append(extractUOMSymbol(toUomId));
		}

		//
		return sb.toString();
	}

	private static String extractProductName(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			return "";
		}

		// avoid DB connection issues
		if (Adempiere.isUnitTestMode())
		{
			return String.valueOf(productId.getRepoId());
		}

		final String productName = Services.get(IProductBL.class).getProductValueAndName(productId);
		return productName;
	}

	private static String extractUOMSymbol(UomId uomId)
	{
		if (uomId == null)
		{
			return "";
		}

		// avoid DB connection issues
		if (Adempiere.isUnitTestMode())
		{
			return String.valueOf(uomId.getRepoId());
		}

		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(uomId);
		if (uom == null)
		{
			return String.valueOf(uomId.getRepoId());
		}

		return uom.getUOMSymbol();
	}
}
