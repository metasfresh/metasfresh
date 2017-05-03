/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/
package org.eevolution.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.MUOM;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMBL;

/**
 * PP Product BOM Line Model. <code>
 * 			MPPProductBOMLine l = new MPPProductBOMLine(bom);
 * 			l.setM_Product_ID(wbl.getM_Product_ID());
 * 			l.setQty(wbl.getQuantity());
 * 			l.saveEx();
 * 	</code>
 * 
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPProductBOMLine extends X_PP_Product_BOMLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6729103151164195906L;

	/**
	 * Default Constructor
	 * 
	 * @param ctx context
	 * @param PP_Product_BOMLine BOM line to load
	 * @param Transaction Line
	 */
	public MPPProductBOMLine(Properties ctx, int PP_Product_BOMLine, String trxName)
	{
		super(ctx, PP_Product_BOMLine, trxName);
	} // MPPProductBOMLine

	/**
	 * Parent Constructor.
	 * 
	 * @param bom parent BOM
	 */
	public MPPProductBOMLine(MPPProductBOM bom)
	{
		super(bom.getCtx(), 0, bom.get_TableName());
		if (bom.get_ID() <= 0)
			throw new IllegalArgumentException("Header not saved");
		setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID()); // parent
	}

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set record
	 */
	public MPPProductBOMLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	} // MPPProductBOMLine

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//
		// For Co/By Products, Qty should be always negative:
		if (isCoProduct() && getQty(false).signum() >= 0)
		{
			throw new AdempiereException("@Qty@ > 0");
		}
		//
		// Update Line#
		if (getLine() <= 0)
		{
			final String sql = "SELECT COALESCE(MAX(" + COLUMNNAME_Line + "),0) + 10 FROM " + Table_Name
					+ " WHERE " + COLUMNNAME_PP_Product_BOM_ID + "=?";
			int line = DB.getSQLValueEx(get_TrxName(), sql, getPP_Product_BOM_ID());
			setLine(line);
		}

		return true;
	}

	@Deprecated
	public boolean isValidFromTo(Timestamp date)
	{
		return Services.get(IProductBOMBL.class).isValidFromTo(this, date);
	}

	public boolean isByProduct()
	{
		String componentType = getComponentType();
		return COMPONENTTYPE_By_Product.equals(componentType);
	}

	public boolean isCoProduct()
	{
		String componentType = getComponentType();
		return COMPONENTTYPE_Co_Product.equals(componentType);
	}

	/**
	 * Return absolute (unified) quantity value. If IsQtyPercentage then QtyBatch / 100 will be returned. Else QtyBOM will be returned.
	 * 
	 * @param includeScrapQty if true, scrap qty will be used for calculating qty
	 * @return qty
	 */
	public BigDecimal getQty(boolean includeScrapQty)
	{
		int precision = getPrecision();
		BigDecimal qty;
		if (isQtyPercentage())
		{
			precision += 2;
			qty = getQtyBatch().divide(Env.ONEHUNDRED, precision, RoundingMode.HALF_UP);
		}
		else
		{
			qty = getQtyBOM();
		}
		//
		if (includeScrapQty)
		{
			final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
			qty = productBOMBL.calculateQtyWithScrap(qty, getScrap());
		}
		//
		if (qty.scale() > precision)
		{
			qty = qty.setScale(precision, RoundingMode.HALF_UP);
		}
		//
		return qty;
	}

	/**
	 * Like {@link #getQty(boolean)}, includeScrapQty = false
	 */
	public BigDecimal getQty()
	{
		return getQty(false);
	}

	/**
	 * @return UOM precision
	 */
	public int getPrecision()
	{
		return MUOM.getPrecision(getCtx(), getC_UOM_ID());
	}

	/**
	 * @return co-product cost allocation percent (i.e. -1/qty)
	 */
	public BigDecimal getCostAllocationPerc()
	{
		BigDecimal qty = getQty(false).negate();
		BigDecimal allocationPercent = BigDecimal.ZERO;
		if (qty.signum() != 0)
		{
			allocationPercent = BigDecimal.ONE.divide(qty, 4, RoundingMode.HALF_UP);
		}
		return allocationPercent;
	}
}
