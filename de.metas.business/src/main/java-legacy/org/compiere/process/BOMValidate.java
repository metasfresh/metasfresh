/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MBOM;
import org.compiere.model.MBOMProduct;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	Validate BOM
 *	
 *  @author Jorg Janke
 *  @version $Id: BOMValidate.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class BOMValidate extends SvrProcess
{
	/**	The Product			*/
	private int		p_M_Product_ID = 0;
	/** Product Category	*/
	private int		p_M_Product_Category_ID = 0;
	/** Re-Validate			*/
	private boolean	p_IsReValidate = false;
	
	/**	Product				*/
	private MProduct			m_product = null;
	/**	List of Products	*/
	private ArrayList<MProduct>	m_products = null;
	
	/**
	 * 	Prepare
	 */
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_Product_Category_ID"))
				p_M_Product_Category_ID = para[i].getParameterAsInt();
			else if (name.equals("IsReValidate"))
				p_IsReValidate = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		p_M_Product_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return Info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		if (p_M_Product_ID != 0)
		{
			log.info("M_Product_ID=" + p_M_Product_ID);
			return validateProduct(new MProduct(getCtx(), p_M_Product_ID, get_TrxName()));
		}
		log.info("M_Product_Category_ID=" + p_M_Product_Category_ID
			+ ", IsReValidate=" + p_IsReValidate);
		//
		int counter = 0;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM M_Product "
			+ "WHERE IsBOM='Y' AND ";
		if (p_M_Product_Category_ID == 0)
			sql += "AD_Client_ID=? ";
		else
			sql += "M_Product_Category_ID=? ";
		if (!p_IsReValidate)
			sql += "AND IsVerified<>'Y' ";
		sql += "ORDER BY Name";
		int AD_Client_ID = Env.getAD_Client_ID(getCtx());
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			if (p_M_Product_Category_ID == 0)
				pstmt.setInt (1, AD_Client_ID);
			else
				pstmt.setInt(1, p_M_Product_Category_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				String info = validateProduct(new MProduct(getCtx(), rs.getInt(1), get_TrxName()));
				addLog(0, null, null, info);
				counter++;
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
		  DB.close(rs, pstmt);
		  rs = null; pstmt = null;
		}
		return "#" + counter;
	}	//	doIt

	
	
	/**
	 * 	Validate Product
	 *	@param product product
	 *	@return Info
	 */
	private String validateProduct (MProduct product)
	{
		if (!product.isBOM())
			return product.getName() + " @NotValid@ @M_BOM_ID@";
		m_product = product;
	
		//	Check Old Product BOM Structure
		log.info(m_product.getName());
		m_products = new ArrayList<MProduct>();
		if (!validateOldProduct (m_product))
		{
			m_product.setIsVerified(false);
			m_product.save();
			return m_product.getName() + " @NotValid@";
		}
		
		//	New Structure
		MBOM[] boms = MBOM.getOfProduct(getCtx(), p_M_Product_ID, get_TrxName(), null);
		for (int i = 0; i < boms.length; i++)
		{
			m_products = new ArrayList<MProduct>();
			if (!validateBOM(boms[i]))
			{
				m_product.setIsVerified(false);
				m_product.save();
				return m_product.getName() + " " + boms[i].getName() + " @NotValid@";
			}
		}
		
		//	OK
		m_product.setIsVerified(true);
		m_product.save();
		return m_product.getName() + " @IsValid@";
	}	//	validateProduct


	/**
	 * 	Validate Old BOM Product structure
	 *	@param product product
	 *	@return true if valid
	 */
	private boolean validateOldProduct (MProduct product)
	{
		if (!product.isBOM())
			return true;
		//
		if (m_products.contains(product))
		{
			log.warn(m_product.getName() + " recursively includes " + product.getName());
			return false;
		}
		m_products.add(product);
		log.debug(product.getName());
		//
		MProductBOM[] productsBOMs = MProductBOM.getBOMLines(product);
		for (int i = 0; i < productsBOMs.length; i++)
		{
			MProductBOM productsBOM = productsBOMs[i];
			MProduct pp = new MProduct(getCtx(), productsBOM.getM_ProductBOM_ID(), get_TrxName());
			if (!pp.isBOM())
				log.trace(pp.getName());
			else if (!validateOldProduct(pp))
				return false;
		}
		return true;
	}	//	validateOldProduct

	/**
	 * 	Validate BOM
	 *	@param bom bom
	 *	@return true if valid
	 */
	private boolean validateBOM (MBOM bom)
	{
		MBOMProduct[] BOMproducts = MBOMProduct.getOfBOM(bom);
		for (int i = 0; i < BOMproducts.length; i++)
		{
			MBOMProduct BOMproduct = BOMproducts[i];
			MProduct pp = new MProduct(getCtx(), BOMproduct.getM_BOMProduct_ID(), get_TrxName());
			if (pp.isBOM())
				return validateProduct(pp, bom.getBOMType(), bom.getBOMUse());
		}
		return true;
	}	//	validateBOM

	/**
	 * 	Validate Product
	 *	@param product product
	 *	@param BOMType type
	 *	@param BOMUse use
	 *	@return true if valid
	 */
	private boolean validateProduct (MProduct product, String BOMType, String BOMUse)
	{
		if (!product.isBOM())
			return true;
		//
		String restriction = "BOMType='" + BOMType + "' AND BOMUse='" + BOMUse + "'";
		MBOM[] boms = MBOM.getOfProduct(getCtx(), p_M_Product_ID, get_TrxName(),
			restriction);
		if (boms.length != 1)
		{
			log.warn(restriction + " - Length=" + boms.length);
			return false;
		}
		if (m_products.contains(product))
		{
			log.warn(m_product.getName() + " recursively includes " + product.getName());
			return false;
		}
		m_products.add(product);
		log.debug(product.getName());
		//
		MBOM bom = boms[0];
		MBOMProduct[] BOMproducts = MBOMProduct.getOfBOM(bom);
		for (int i = 0; i < BOMproducts.length; i++)
		{
			MBOMProduct BOMproduct = BOMproducts[i];
			MProduct pp = new MProduct(getCtx(), BOMproduct.getM_BOMProduct_ID(), get_TrxName());
			if (pp.isBOM())
				return validateProduct(pp, bom.getBOMType(), bom.getBOMUse());
		}
		return true;
	}	//	validateProduct
	
}	//	BOMValidate
