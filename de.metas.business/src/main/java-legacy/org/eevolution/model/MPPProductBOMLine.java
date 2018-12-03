/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 * Teo Sarca, www.arhipac.ro *
 *****************************************************************************/
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.eevolution.api.IProductBOMBL;

import de.metas.util.Services;

@SuppressWarnings("serial")
public class MPPProductBOMLine extends X_PP_Product_BOMLine
{
	public MPPProductBOMLine(final Properties ctx, final int PP_Product_BOMLine, final String trxName)
	{
		super(ctx, PP_Product_BOMLine, trxName);
	}

	public MPPProductBOMLine(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		//
		// For Co/By Products, Qty should be always negative:
		if (isCoProduct()
				&& Services.get(IProductBOMBL.class).getQtyExcludingScrap(this).signum() >= 0)
		{
			throw new AdempiereException("@Qty@ > 0");
		}
		//
		// Update Line#
		if (getLine() <= 0)
		{
			final String sql = "SELECT COALESCE(MAX(" + COLUMNNAME_Line + "),0) + 10 FROM " + Table_Name
					+ " WHERE " + COLUMNNAME_PP_Product_BOM_ID + "=?";
			final int line = DB.getSQLValueEx(get_TrxName(), sql, getPP_Product_BOM_ID());
			setLine(line);
		}

		return true;
	}

	private boolean isCoProduct()
	{
		final String componentType = getComponentType();
		return COMPONENTTYPE_Co_Product.equals(componentType);
	}
}
