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
 *                 Teo Sarca, http://www.arhipac.ro                           *
 *****************************************************************************/
//package org.compiere.mfg.model;
package org.eevolution.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.MProduct;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;

/**
 * PP Product BOM Model.
 * 
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPProductBOM extends X_PP_Product_BOM
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5770988975738210823L;
//	/** Cache */
//	private static CCache<Integer, MPPProductBOM> s_cache = new CCache<Integer, MPPProductBOM>(Table_Name, 40, 5);

	// /** BOM Lines */
	// private List<MPPProductBOMLine> m_lines = null;

	/**
	 * Get Product BOM by ID (cached)
	 * 
	 * @param ctx
	 * @param PP_Product_BOM_ID
	 * @return product bom
	 */
	@Deprecated
	public static MPPProductBOM get(Properties ctx, int PP_Product_BOM_ID)
	{
		final I_PP_Product_BOM bom = Services.get(IProductBOMDAO.class).retrieveBOMById(ctx, PP_Product_BOM_ID);
		final MPPProductBOM bomPO = LegacyAdapters.convertToPO(bom);
		return bomPO;
	}

	/**
	 * Get BOM with Default Logic (Product = BOM Product and BOM Value = Product Value)
	 * 
	 * @param product
	 * @param trxName
	 * @return product BOM
	 */
	@Deprecated
	public static MPPProductBOM getDefault(I_M_Product product, String trxName)
	{
		final I_PP_Product_BOM bom = Services.get(IProductBOMDAO.class).retrieveDefaultBOM(product);
		final MPPProductBOM bomPO = LegacyAdapters.convertToPO(bom);
		return bomPO;
	}

	/**
	 * Get BOM for Product
	 * 
	 * @param product product
	 * @param ad_org_id Organization ID
	 * @param trxName Transaction Name
	 * @return BOM
	 */
	public static MPPProductBOM get(MProduct product, int ad_org_id, String trxName)
	{
		MPPProductBOM bom = null;
		Properties ctx = product.getCtx();
		// find Default BOM in Product Data Planning
		if (ad_org_id > 0)
		{
			MPPProductPlanning pp = MPPProductPlanning.get(ctx, product.getAD_Client_ID(), ad_org_id, product.getM_Product_ID(), trxName);
			if (pp != null && pp.getPP_Product_BOM_ID() > 0)
			{
				bom = new MPPProductBOM(ctx, pp.getPP_Product_BOM_ID(), trxName);
			}
		}
		if (bom == null)
		{
			// Find BOM with Default Logic where product = bom product and bom value = value
			bom = getDefault(product, trxName);
		}

		return bom;
	}

	/**
	 * Get BOM with valid dates for Product
	 * 
	 * @param product product
	 * @param ad_org_id Organization ID
	 * @param valid Date to Validate
	 * @param trxName Transaction Name
	 * @return BOM
	 */
	public static MPPProductBOM get(MProduct product, int ad_org_id, Timestamp valid, String trxName)
	{
		MPPProductBOM bom = get(product, ad_org_id, trxName);
		if (bom != null && bom.isValidFromTo(valid))
		{
			return bom;
		}
		return null;
	}

	public MPPProductBOM(Properties ctx, int PP_Product_BOM_ID, String trxName)
	{
		super(ctx, PP_Product_BOM_ID, trxName);
	}

	public MPPProductBOM(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Get BOM Lines valid date for Product BOM
	 * 
	 * @param valid Date to Validate
	 * @return BOM Lines
	 */
	@Deprecated
	public MPPProductBOMLine[] getLines(Timestamp valid)
	{
		List<I_PP_Product_BOMLine> lines = Services.get(IProductBOMDAO.class).retrieveLines(this, valid);
		final MPPProductBOMLine[] linesArr = LegacyAdapters.convertToPOArray(lines, MPPProductBOMLine.class);
		return linesArr;
	}	// getLines

	/**
	 * Get BOM Lines for Product BOM from cache
	 * 
	 * @return BOM Lines
	 */
	@Deprecated
	public MPPProductBOMLine[] getLines()
	{
		return getLines(false);
	}

	/**
	 * Get BOM Lines for Product BOM
	 * 
	 * @return BOM Lines
	 */
	@Deprecated
	public MPPProductBOMLine[] getLines(boolean reload)
	{
		List<I_PP_Product_BOMLine> lines = Services.get(IProductBOMDAO.class).retrieveLines(this);
		final MPPProductBOMLine[] linesArr = LegacyAdapters.convertToPOArray(lines, MPPProductBOMLine.class);
		return linesArr;
	}	// getLines

	@Deprecated
	public boolean isValidFromTo(Timestamp date)
	{
		return Services.get(IProductBOMBL.class).isValidFromTo(this, date);
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MPPProductBOM[")
				.append(get_ID()).append("-").append(getDocumentNo())
				.append(", Value=").append(getValue())
				.append("]");
		return sb.toString();
	}
}	// MPPProductBOM
