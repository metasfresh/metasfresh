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

package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.model.engines.CostDimension;
import org.adempiere.util.Services;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MCostType;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.MPPProductPlanning;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.eevolution.mrp.api.IMRPDAO;

/**
 * Roll-UP Bill of Material
 *
 * @author victor.perez@e-evolution.com, e-Evolution, S.C.
 * @version $Id: RollupBillOfMaterial.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 */
@SuppressWarnings("deprecation") // hide those to not polute our Warnings
public class RollupBillOfMaterial extends SvrProcess
{
	/* Organization 		*/
	private int		 		p_AD_Org_ID = 0;
	/* Account Schema 		*/
	private int				p_C_AcctSchema_ID = 0;
	/* Cost Type			*/
	private int				p_M_CostType_ID = 0;
	/* Costing Method 		*/
	private String 			p_ConstingMethod = MCostElement.COSTINGMETHOD_StandardCosting;
	/* Product 				*/
	private int				p_M_Product_ID = 0;
	/* Product Category  	*/
	private int				p_M_Product_Category_ID = 0;
	/* Product Type			*/
	private String			p_ProductType = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParameter())
		{
			String name = para.getParameterName();

			if (para.getParameter() == null)
				;
			else if (name.equals(MCostElement.COLUMNNAME_AD_Org_ID))
				p_AD_Org_ID = para.getParameterAsInt();
			else if (name.equals(MAcctSchema.COLUMNNAME_C_AcctSchema_ID))
				p_C_AcctSchema_ID = para.getParameterAsInt();
			else if (name.equals(MCostType.COLUMNNAME_M_CostType_ID))
				p_M_CostType_ID = para.getParameterAsInt();
			else if (name.equals(MCostElement.COLUMNNAME_CostingMethod))
				p_ConstingMethod=(String)para.getParameter();
			else if (name.equals(MProduct.COLUMNNAME_M_Product_ID))
				p_M_Product_ID = para.getParameterAsInt();
			else if (name.equals(MProduct.COLUMNNAME_M_Product_Category_ID))
				p_M_Product_Category_ID = para.getParameterAsInt();
			else if (name.equals(MProduct.COLUMNNAME_ProductType))
				p_ProductType = para.getParameter() == null ? null : para.getParameter().toString();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 * 	Generate Calculate Cost
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		resetCostsLLForLLC0();
		//
		int maxLowLevel = Services.get(IMRPDAO.class).getMaxLowLevel(this);
		// Cost Roll-up for all levels
		for (int lowLevel = maxLowLevel; lowLevel >= 0; lowLevel--)
		{
			for (MProduct product : getProducts(lowLevel))
			{
				I_PP_Product_Planning pp = MPPProductPlanning.find(getCtx(), p_AD_Org_ID,
						0, // M_Warehouse_ID
						0, // S_Resource_ID
						product.getM_Product_ID(),
						get_TrxName());

				int PP_Product_BOM_ID = 0;
				if (pp != null)
				{
					PP_Product_BOM_ID = pp.getPP_Product_BOM_ID();
				}
				else
				{
					createNotice(product, "@NotFound@ @PP_Product_Planning_ID@");
				}
				if (PP_Product_BOM_ID <= 0)
				{
					PP_Product_BOM_ID = Services.get(IProductBOMDAO.class).retrieveDefaultBOMId(product);
				}
				MPPProductBOM bom = MPPProductBOM.get(getCtx(), PP_Product_BOM_ID);
				if (bom == null)
				{
					createNotice(product, "@NotFound@ @PP_Product_BOM_ID@");
				}
				rollup(product, bom);
			} // for each Products
		} // for each LLC
		return "@OK@";
	}

	protected void rollup(MProduct product, MPPProductBOM bom)
	{
		for (I_M_CostElement element : getCostElements())
		{
			for (MCost cost : getCosts(product, element.getM_CostElement_ID()))
			{
				log.info("Calculate Lower Cost for: "+ bom);
				BigDecimal price = getCurrentCostPriceLL(bom, element);
				log.info(element.getName() + " Cost Low Level:" + price);
				cost.setCurrentCostPriceLL(price);
				updateCoProductCosts(bom, cost);
				cost.saveEx();
			} // for each Costs
		} // for Elements
	}

	/**
	 * Update costs for co-products on BOM Lines from given BOM
	 * @param bom product's BOM
	 * @param element cost element
	 * @param baseCost base product cost (BOM Cost)
	 */
	private void updateCoProductCosts(MPPProductBOM bom, MCost baseCost)
	{
		// Skip if not BOM found
		if (bom == null)
			return;

		BigDecimal costPriceTotal = Env.ZERO;
		for (MPPProductBOMLine bomline : bom.getLines())
		{
			if (!bomline.isCoProduct())
			{
				continue;
			}
			final BigDecimal costPrice = baseCost.getCurrentCostPriceLL().multiply(bomline.getCostAllocationPerc());
			//
			// Get/Create Cost
			MCost cost = MCost.get(baseCost.getCtx(), baseCost.getAD_Client_ID(), baseCost.getAD_Org_ID(),
									bomline.getM_Product_ID(),
									baseCost.getM_CostType_ID(), baseCost.getC_AcctSchema_ID(),
									baseCost.getM_CostElement_ID(),
									0, // ASI
									baseCost.get_TrxName());
			if (cost == null)
			{
				cost = new MCost (baseCost.getCtx(), 0, baseCost.get_TrxName());
				//cost.setAD_Client_ID(baseCost.getAD_Client_ID());
				cost.setAD_Org_ID(baseCost.getAD_Org_ID());
				cost.setM_Product_ID(bomline.getM_Product_ID());
				cost.setM_CostType_ID(baseCost.getM_CostType_ID());
				cost.setC_AcctSchema_ID(baseCost.getC_AcctSchema_ID());
				cost.setM_CostElement_ID(baseCost.getM_CostElement_ID());
				cost.setM_AttributeSetInstance_ID(0);
			}
			cost.setCurrentCostPriceLL(costPrice);
			cost.saveEx();
			costPriceTotal = costPriceTotal.add(costPrice);
		}
		// Update Base Cost:
		if(costPriceTotal.signum() != 0)
		{
			baseCost.setCurrentCostPriceLL(costPriceTotal);
		}
	}

	/**
	 * Get the sum Current Cost Price Level Low for this Cost Element
	 * @param bom MPPProductBOM
	 * @param element MCostElement
	 * @return Cost Price Lower Level
	 */
	private BigDecimal getCurrentCostPriceLL(MPPProductBOM bom, I_M_CostElement element)
	{
		log.info("Element: "+ element);
		BigDecimal costPriceLL = Env.ZERO;
		if(bom == null)
			return costPriceLL;

		for (MPPProductBOMLine bomline : bom.getLines())
		{
			// Skip co-product
			if (bomline.isCoProduct())
			{
				continue;
			}
			//06005
			if (X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(bomline.getComponentType()))
			{
				continue;
			}
			//
			MProduct component = MProduct.get(getCtx(), bomline.getM_Product_ID());
			// get the rate for this resource
			for (MCost cost : getCosts(component, element.getM_CostElement_ID()))
			{
				BigDecimal qty = bomline.getQty(true);

				// ByProducts
				if (bomline.isByProduct())
				{
					cost.setCurrentCostPriceLL(Env.ZERO);
				}

				BigDecimal costPrice = cost.getCurrentCostPrice().add(cost.getCurrentCostPriceLL());
				BigDecimal componentCost = costPrice.multiply(qty);
				costPriceLL = costPriceLL.add(componentCost);
				log.info("CostElement: "+element.getName()
						+ ", Component: "+component.getValue()
						+ ", CostPrice: "+costPrice
						+ ", Qty: " + qty
						+ ", Cost: " + componentCost
						+ " => Total Cost Element: " +  costPriceLL);
			} // for each cost
		} // for each BOM line
		return costPriceLL;
	}

	private Collection<MCost> getCosts(MProduct product, int M_CostElement_ID)
	{
		MAcctSchema as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
		CostDimension d = new CostDimension(product, as, p_M_CostType_ID, p_AD_Org_ID, 0, M_CostElement_ID);
		return d.toQuery(MCost.class, get_TrxName()).list();
	}

	private Collection<MProduct> getProducts(int lowLevel)
	{
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer("AD_Client_ID=?")
						.append(" AND ").append(MProduct.COLUMNNAME_LowLevel).append("=?")
		;
		params.add(getAD_Client_ID());
		params.add(lowLevel);

		whereClause.append(" AND ").append(MProduct.COLUMNNAME_IsBOM).append("=?");
		params.add(true);

		if (p_M_Product_ID > 0)
		{
			whereClause.append(" AND ").append(MProduct.COLUMNNAME_M_Product_ID).append("=?");
			params.add(p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID > 0)
		{
			whereClause.append(" AND ").append(MProduct.COLUMNNAME_M_Product_Category_ID).append("=?");
			params.add(p_M_Product_Category_ID);
		}
		if (p_M_Product_ID <= 0 && p_ProductType != null)
		{
			whereClause.append(" AND ").append(MProduct.COLUMNNAME_ProductType).append("=?");
			params.add(p_ProductType);
		}

		return new Query(getCtx(),MProduct.Table_Name, whereClause.toString(), get_TrxName())
		.setParameters(params)
		.list();
	}

	/**
	 * Reset LowLevel Costs for products with LowLevel=0 (items)
	 */
	private void resetCostsLLForLLC0()
	{
		List<Object> params = new ArrayList<Object>();
		StringBuffer productWhereClause = new StringBuffer();
		productWhereClause.append("AD_Client_ID=? AND "+MProduct.COLUMNNAME_LowLevel+"=?");
		params.add(getAD_Client_ID());
		params.add(0);
		if (p_M_Product_ID > 0)
		{
			productWhereClause.append(" AND ").append(MProduct.COLUMNNAME_M_Product_ID).append("=?");
			params.add(p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID > 0)
		{
			productWhereClause.append(" AND ").append(MProduct.COLUMNNAME_M_Product_Category_ID).append("=?");
			params.add(p_M_Product_Category_ID);
		}
		//
		final String sql = "UPDATE M_Cost c SET "+MCost.COLUMNNAME_CurrentCostPriceLL+"=0"
		+" WHERE EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID=c.M_Product_ID"
						+" AND "+productWhereClause+")";
		int no = DB.executeUpdateEx(sql, params.toArray(), get_TrxName());
		log.info("Updated #"+no);
	}

	private Collection<I_M_CostElement> m_costElements = null;
	private Collection<I_M_CostElement> getCostElements()
	{
		if (m_costElements == null)
		{
			m_costElements = MCostElement.getByCostingMethod(getCtx(),  p_ConstingMethod);
		}
		return m_costElements;
	}

	/**
	 * Create Cost Rollup Notice
	 * @param product
	 * @param msg
	 */
	private void createNotice(MProduct product, String msg)
	{
		String productValue = product != null ? product.getValue() : "-";
		addLog("WARNING: Product "+productValue+": "+msg);
	}
}
