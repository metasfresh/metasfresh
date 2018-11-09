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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product;
import org.compiere.model.Query;
import org.compiere.model.X_M_CostElement;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.X_T_BOMLine;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;

/**
 * Cost Multi-Level BOM & Formula Review
 *
 * @author victor.perez@e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 *
 */
public class CostBillOfMaterial extends JavaProcess
{
	private final ICostElementRepository costElementRepo = Adempiere.getBean(ICostElementRepository.class);

	private static final String LEVELS = "....................";
	//
	private OrgId p_AD_Org_ID = OrgId.ANY;
	private AcctSchemaId p_C_AcctSchema_ID;
	private ProductId p_M_Product_ID;
	private CostTypeId p_M_CostType_ID;
	private CostingMethod p_ConstingMethod = CostingMethod.StandardCosting;
	private boolean p_implosion = false;
	//
	private int m_LevelNo = 0;
	private int m_SeqNo = 0;
	private AcctSchema m_as = null;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals(I_M_Cost.COLUMNNAME_AD_Org_ID))
				p_AD_Org_ID = OrgId.ofRepoIdOrAny(para.getParameterAsInt());
			else if (name.equals(I_M_Cost.COLUMNNAME_C_AcctSchema_ID))
			{
				p_C_AcctSchema_ID = AcctSchemaId.ofRepoId(para.getParameterAsInt());
				m_as = Services.get(IAcctSchemaDAO.class).getById(p_C_AcctSchema_ID);
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_CostType_ID))
				p_M_CostType_ID = CostTypeId.ofRepoIdOrNull(para.getParameterAsInt());
			else if (name.equals(X_M_CostElement.COLUMNNAME_CostingMethod))
				p_ConstingMethod = CostingMethod.ofCode(para.getParameterAsString());
			else if (name.equals(I_M_Cost.COLUMNNAME_M_Product_ID))
				p_M_Product_ID = ProductId.ofRepoIdOrNull(para.getParameterAsInt());
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
		if (p_M_Product_ID == null)
		{
			throw new FillMandatoryException("M_Product_ID");
		}
		explodeProduct(p_M_Product_ID, false);
		//
		return "";
	} // doIt

	/**
	 * Generate an Explosion for this product
	 * 
	 * @param product
	 * @param isComponent component / header
	 */
	@SuppressWarnings("deprecation") // hide those to not polute our Warnings
	private void explodeProduct(ProductId productId, boolean isComponent)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
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
				explodeProduct(ProductId.ofRepoId(bomLine.getM_Product_ID()), true);
			}
			m_LevelNo--;
		}
	}

	/**
	 * Get BOMs for given product
	 * 
	 * @param product
	 * @param isComponent
	 * @return list of MPPProductBOM
	 */
	private List<MPPProductBOM> getBOMs(I_M_Product product, boolean includeAlternativeBOMs)
	{
		ArrayList<Object> params = new ArrayList<>();
		StringBuffer whereClause = new StringBuffer();
		whereClause.append(MPPProductBOM.COLUMNNAME_M_Product_ID).append("=?");
		params.add(product.getM_Product_ID());
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
				.list(MPPProductBOM.class);
		return list;
	}

	/**
	 * Create T_BOMLine
	 * 
	 * @param product
	 * @param costElement
	 * @param qty
	 * @param bomLine
	 * @return
	 */
	private void createLines(MPPProductBOM bom, MPPProductBOMLine bomLine)
	{
		final I_M_Product product;
		BigDecimal qty;
		if (bomLine != null)
		{
			product = Services.get(IProductDAO.class).getById(bomLine.getM_Product_ID());
			qty = bomLine.getQty();
		}
		else if (bom != null)
		{
			product = Services.get(IProductDAO.class).getById(bom.getM_Product_ID());
			qty = BigDecimal.ONE;
		}
		else
		{
			throw new LiberoException("@NotFound@ @PP_Product_BOM_ID@");
		}
		for (CostElement costElement : getCostElements())
		{
			X_T_BOMLine tboml = new X_T_BOMLine(getCtx(), 0, get_TrxName());
			tboml.setAD_Org_ID(p_AD_Org_ID.getRepoId());
			tboml.setSel_Product_ID(ProductId.toRepoId(p_M_Product_ID));
			tboml.setImplosion(p_implosion);
			tboml.setC_AcctSchema_ID(p_C_AcctSchema_ID.getRepoId());
			tboml.setM_CostType_ID(CostTypeId.toRepoId(p_M_CostType_ID));
			tboml.setCostingMethod(p_ConstingMethod.getCode());
			tboml.setAD_PInstance_ID(getPinstanceId().getRepoId());
			tboml.setM_CostElement_ID(costElement.getId().getRepoId());
			tboml.setM_Product_ID(product.getM_Product_ID());
			tboml.setQtyBOM(qty);
			//
			tboml.setSeqNo(m_SeqNo);
			tboml.setLevelNo(m_LevelNo);
			tboml.setLevels(LEVELS.substring(0, m_LevelNo) + m_LevelNo);
			//
			// Set Costs:
			Collection<I_M_Cost> costs = getCostsByElement(
					product,
					m_as,
					p_M_CostType_ID,
					p_AD_Org_ID,
					AttributeSetInstanceId.NONE, // ASI
					costElement.getId());
			BigDecimal currentCostPrice = BigDecimal.ZERO;
			BigDecimal currentCostPriceLL = BigDecimal.ZERO;
			BigDecimal futureCostPrice = BigDecimal.ZERO;
			BigDecimal futureCostPriceLL = BigDecimal.ZERO;
			boolean isCostFrozen = false;
			for (I_M_Cost cost : costs)
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

	private List<CostElement> getCostElements()
	{
		if (m_costElements == null)
		{
			m_costElements = costElementRepo.getByCostingMethod(p_ConstingMethod);
		}
		return m_costElements;
	}

	private List<CostElement> m_costElements = null;

	private static Collection<I_M_Cost> getCostsByElement(
			final I_M_Product product,
			final AcctSchema as,
			final CostTypeId M_CostType_ID,
			final OrgId AD_Org_ID,
			final AttributeSetInstanceId M_AttributeSetInstance_ID,
			final CostElementId costElementId)
	{
		final CostDimension cd = new CostDimension(product,
				as,
				M_CostType_ID,
				AD_Org_ID,
				M_AttributeSetInstance_ID,
				costElementId);
		return cd.toQuery(I_M_Cost.class, ITrx.TRXNAME_ThreadInherited)
				.setOnlyActiveRecords(true)
				.list();
	}

}
