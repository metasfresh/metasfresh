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
package org.eevolution.report;

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

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.engines.CostEngine;
import org.adempiere.model.engines.CostEngineFactory;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.X_T_BOMLine;

/**
 * Cost Multi-Level BOM & Formula Review
 *
 * @author victor.perez@e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 *
 */
public class CostBillOfMaterial extends SvrProcess
{
	private static final String LEVELS = "....................";
	//
	private int p_AD_Org_ID = 0;
	private int p_C_AcctSchema_ID = 0;
	private int p_M_Product_ID = 0;
	private int p_M_CostType_ID = 0;
	private String p_ConstingMethod = MCostElement.COSTINGMETHOD_StandardCosting;
	private boolean p_implosion = false;
	//
	private int m_LevelNo = 0;
	private int m_SeqNo = 0;
	private MAcctSchema m_as = null;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParameter())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals(MCost.COLUMNNAME_AD_Org_ID))
				p_AD_Org_ID = para.getParameterAsInt();
			else if (name.equals(MCost.COLUMNNAME_C_AcctSchema_ID))
			{
				p_C_AcctSchema_ID= para.getParameterAsInt();
				m_as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
			}
			else if (name.equals(MCost.COLUMNNAME_M_CostType_ID))
				p_M_CostType_ID= para.getParameterAsInt();
			else if (name.equals(MCostElement.COLUMNNAME_CostingMethod))
				p_ConstingMethod=(String)para.getParameter();
			else if (name.equals(MCost.COLUMNNAME_M_Product_ID))
				p_M_Product_ID = para.getParameterAsInt();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	} // prepare

	/**
	 * Perform process.
	 *
	 * @return Message (clear text)
	 * @throws Exception
	 *             if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		if (p_M_Product_ID == 0)
		{
			throw new FillMandatoryException("M_Product_ID");
		}
		explodeProduct(p_M_Product_ID, false);
		//
		return "";
	} // doIt

	/**
	 * Generate an Explosion for this product
	 * @param product
	 * @param isComponent component / header
	 */
	@SuppressWarnings("deprecation") // hide those to not polute our Warnings
	private void explodeProduct(int M_Product_ID, boolean isComponent)
	{
		MProduct product = MProduct.get(getCtx(), M_Product_ID);
		List<MPPProductBOM> list = getBOMs(product, isComponent);
		if (!isComponent && list.size() == 0)
		{
			throw new LiberoException("@Error@ Product is not a BOM");
		}
		//
		for (MPPProductBOM bom : list)
		{
			// Create header
			if (!isComponent)
			{
				createLines(bom, null);
			}
			m_LevelNo++;
			// Create Lines:
			for (MPPProductBOMLine bomLine : bom.getLines())
			{
				if (!bomLine.isActive())
				{
					continue;
				}
				createLines(bom, bomLine);
				explodeProduct(bomLine.getM_Product_ID(), true);
			}
			m_LevelNo--;
		}
	}

	/**
	 * Get BOMs for given product
	 * @param product
	 * @param isComponent
	 * @return list of MPPProductBOM
	 */
	private List<MPPProductBOM> getBOMs(MProduct product, boolean includeAlternativeBOMs)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		whereClause.append(MPPProductBOM.COLUMNNAME_M_Product_ID).append("=?");
		params.add(product.get_ID());
		// Allow alternative BOMs
		if (includeAlternativeBOMs)
		{
			whereClause.append(" AND ").append(MPPProductBOM.COLUMNNAME_Value).append("=?");
			params.add(product.getValue());
		}
		List<MPPProductBOM> list = new Query(getCtx(), MPPProductBOM.Table_Name, whereClause.toString(), null)
									.setParameters(params)
									.setOnlyActiveRecords(true)
									.setOrderBy(MPPProductBOM.COLUMNNAME_Value)
									.list();
		return list;
	}

	/**
	 * Create T_BOMLine
	 * @param product
	 * @param costElement
	 * @param qty
	 * @param bomLine
	 * @return
	 */
	private void createLines(MPPProductBOM bom, MPPProductBOMLine bomLine)
	{
		MProduct product;
		BigDecimal qty;
		if (bomLine != null)
		{
			product = MProduct.get(getCtx(), bomLine.getM_Product_ID());
			qty = bomLine.getQty();
		}
		else if (bom != null)
		{
			product = MProduct.get(getCtx(), bom.getM_Product_ID());
			qty = Env.ONE;
		}
		else
		{
			throw new LiberoException("@NotFound@ @PP_Product_BOM_ID@");
		}
		for (I_M_CostElement costElement : getCostElements())
		{
			X_T_BOMLine tboml = new X_T_BOMLine(getCtx(), 0, get_TrxName());
			tboml.setAD_Org_ID(p_AD_Org_ID);
			tboml.setSel_Product_ID(p_M_Product_ID);
			tboml.setImplosion(p_implosion);
			tboml.setC_AcctSchema_ID(p_C_AcctSchema_ID);
			tboml.setM_CostType_ID(p_M_CostType_ID);
			tboml.setCostingMethod(p_ConstingMethod);
			tboml.setAD_PInstance_ID(getAD_PInstance_ID());
			tboml.setM_CostElement_ID(costElement.getM_CostElement_ID());
			tboml.setM_Product_ID(product.get_ID());
			tboml.setQtyBOM(qty);
			//
			tboml.setSeqNo(m_SeqNo);
			tboml.setLevelNo(m_LevelNo);
			tboml.setLevels(LEVELS.substring(0, m_LevelNo) + m_LevelNo);
			//
			// Set Costs:
			final CostEngine engine = CostEngineFactory.getCostEngine(getAD_Client_ID());
			Collection <MCost> costs = engine.getByElement(
					product,
					m_as,
					p_M_CostType_ID,
					p_AD_Org_ID,
					0, // ASI
					costElement.getM_CostElement_ID());
			BigDecimal currentCostPrice = Env.ZERO;
			BigDecimal currentCostPriceLL = Env.ZERO;
			BigDecimal futureCostPrice = Env.ZERO;
			BigDecimal futureCostPriceLL = Env.ZERO;
			boolean isCostFrozen = false;
			for (MCost cost : costs)
			{
				currentCostPrice = currentCostPrice.add(cost.getCurrentCostPrice());
				currentCostPriceLL = currentCostPriceLL.add(cost.getCurrentCostPriceLL());
				futureCostPrice = futureCostPrice.add(cost.getFutureCostPrice());
				futureCostPriceLL = futureCostPriceLL.add(cost.getFutureCostPriceLL());
				isCostFrozen = cost.isCostFrozen();
			}
			tboml.setCurrentCostPrice(currentCostPrice);
			tboml.setCurrentCostPriceLL(currentCostPriceLL);
			tboml.setFutureCostPrice(currentCostPrice);
			tboml.setFutureCostPriceLL(currentCostPriceLL);
			tboml.setIsCostFrozen(isCostFrozen);
			//
			// Reference
			if (bomLine != null)
			{
				tboml.setPP_Product_BOM_ID(bomLine.getPP_Product_BOM_ID());
				tboml.setPP_Product_BOMLine_ID(bomLine.getPP_Product_BOMLine_ID());
			}
			else if (bom != null)
			{
				tboml.setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID());
			}
			//
			tboml.saveEx();
			m_SeqNo++;
		}
	}

	public Collection<I_M_CostElement> getCostElements()
	{
		if (m_costElements == null)
		{
			m_costElements = MCostElement.getByCostingMethod(getCtx(), p_ConstingMethod);
		}
		return m_costElements;
	}
	private Collection <I_M_CostElement> m_costElements = null;
}
