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

import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostType;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.MPPProductPlanning;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.eevolution.mrp.api.IMRPDAO;

import com.google.common.collect.ImmutableList;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrenctCostsRepository;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.IProductBL;

/**
 * Roll-UP Bill of Material
 *
 * @author victor.perez@e-evolution.com, e-Evolution, S.C.
 * @version $Id: RollupBillOfMaterial.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 */
@SuppressWarnings("deprecation") // hide those to not polute our Warnings
public class RollupBillOfMaterial extends JavaProcess
{
	private final ICostElementRepository costElementsRepo = Services.get(ICostElementRepository.class);
	private final ICurrenctCostsRepository currenctCostsRepository = Services.get(ICurrenctCostsRepository.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	/* Organization */
	private int p_AD_Org_ID = 0;
	/* Account Schema */
	private int p_C_AcctSchema_ID = 0;
	/* Cost Type */
	private int p_M_CostType_ID = 0;
	/* Costing Method */
	private String p_ConstingMethod = CostingMethod.StandardCosting.getCode();
	/* Product */
	private int p_M_Product_ID = 0;
	/* Product Category */
	private int p_M_Product_Category_ID = 0;
	/* Product Type */
	private String p_ProductType = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();

			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals(I_M_CostElement.COLUMNNAME_AD_Org_ID))
			{
				p_AD_Org_ID = para.getParameterAsInt();
			}
			else if (name.equals(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID))
			{
				p_C_AcctSchema_ID = para.getParameterAsInt();
			}
			else if (name.equals(I_M_CostType.COLUMNNAME_M_CostType_ID))
			{
				p_M_CostType_ID = para.getParameterAsInt();
			}
			else if (name.equals(I_M_CostElement.COLUMNNAME_CostingMethod))
			{
				p_ConstingMethod = (String)para.getParameter();
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_ID))
			{
				p_M_Product_ID = para.getParameterAsInt();
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_Category_ID))
			{
				p_M_Product_Category_ID = para.getParameterAsInt();
			}
			else if (name.equals(I_M_Product.COLUMNNAME_ProductType))
			{
				p_ProductType = para.getParameter() == null ? null : para.getParameter().toString();
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	}	// prepare

	/**
	 * Generate Calculate Cost
	 * 
	 * @return info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		resetCostsLLForLLC0();
		//
		final int maxLowLevel = Services.get(IMRPDAO.class).getMaxLowLevel(this);
		// Cost Roll-up for all levels
		for (int lowLevel = maxLowLevel; lowLevel >= 0; lowLevel--)
		{
			for (final MProduct product : getProducts(lowLevel))
			{
				final I_PP_Product_Planning pp = MPPProductPlanning.find(getCtx(), p_AD_Org_ID,
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
				final MPPProductBOM bom = MPPProductBOM.get(getCtx(), PP_Product_BOM_ID);
				if (bom == null)
				{
					createNotice(product, "@NotFound@ @PP_Product_BOM_ID@");
				}
				rollup(product, bom);
			} // for each Products
		} // for each LLC
		return "@OK@";
	}

	protected void rollup(final MProduct product, final MPPProductBOM bom)
	{
		for (final CostElement element : getCostElements())
		{
			for (final CurrentCost cost : getCosts(product, element.getId()))
			{
				log.info("Calculate Lower Cost for: {}", bom);
				final CostAmount price = getCurrentCostPriceLL(bom, element);
				log.info("{} Cost Low Level: {}", element, price);
				cost.setCurrentCostPriceLL(price);
				updateCoProductCosts(bom, cost, element);
				currenctCostsRepository.save(cost);
			} // for each Costs
		} // for Elements
	}

	/**
	 * Update costs for co-products on BOM Lines from given BOM
	 *
	 * @param bom product's BOM
	 * @param costElement cost element
	 * @param baseCost base product cost (BOM Cost)
	 * @param costElement
	 */
	private void updateCoProductCosts(final MPPProductBOM bom, final CurrentCost baseCost, final CostElement costElement)
	{
		// Skip if not BOM found
		if (bom == null)
		{
			return;
		}

		CostAmount costPriceTotal = zeroCosts();
		for (final MPPProductBOMLine bomline : bom.getLines())
		{
			if (!bomline.isCoProduct())
			{
				continue;
			}
			final CostAmount costPrice = baseCost.getCurrentCostPriceLL().multiply(bomline.getCostAllocationPerc());
			//
			// Get/Create Cost

			final CostSegment costSegment = baseCost.getCostSegment()
					.toBuilder()
					.productId(bomline.getM_Product_ID())
					.build();
			final CurrentCost cost = currenctCostsRepository.getOrCreate(costSegment, costElement.getId());
			cost.setCurrentCostPriceLL(costPrice);
			currenctCostsRepository.save(cost);

			costPriceTotal = costPriceTotal.add(costPrice);
		}
		// Update Base Cost:
		if (costPriceTotal.signum() != 0)
		{
			baseCost.setCurrentCostPriceLL(costPriceTotal);
		}
	}

	/**
	 * @return Cost Price Lower Level
	 */
	private CostAmount getCurrentCostPriceLL(final MPPProductBOM bom, final CostElement element)
	{
		log.info("Element: {}", element);
		CostAmount costPriceLL = zeroCosts();
		if (bom == null)
		{
			return costPriceLL;
		}

		for (final MPPProductBOMLine bomline : bom.getLines())
		{
			// Skip co-product
			if (bomline.isCoProduct())
			{
				continue;
			}
			// 06005
			if (X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(bomline.getComponentType()))
			{
				continue;
			}

			//
			final MProduct component = MProduct.get(getCtx(), bomline.getM_Product_ID());
			// get the rate for this resource
			for (final CurrentCost cost : getCosts(component, element.getId()))
			{
				final BigDecimal qty = bomline.getQty(true);

				// ByProducts
				if (bomline.isByProduct())
				{
					cost.setCurrentCostPriceLL(zeroCosts());
				}

				final CostAmount costPrice = cost.getCurrentCostPriceTotal();
				final CostAmount componentCost = costPrice.multiply(qty);
				costPriceLL = costPriceLL.add(componentCost);
				log.info("CostElement: " + element.getName()
						+ ", Component: " + component.getValue()
						+ ", CostPrice: " + costPrice
						+ ", Qty: " + qty
						+ ", Cost: " + componentCost
						+ " => Total Cost Element: " + costPriceLL);
			} // for each cost
		} // for each BOM line
		return costPriceLL;
	}

	private Collection<CurrentCost> getCosts(final MProduct product, final int costElementId)
	{
		final CostSegment costSegment = createCostSegment(product);
		final CurrentCost cost = currenctCostsRepository.getOrNull(costSegment, costElementId);
		return cost != null ? ImmutableList.of(cost) : ImmutableList.of();
	}

	private CostSegment createCostSegment(final I_M_Product product)
	{
		final MAcctSchema as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);

		final int productId = product.getM_Product_ID();
		final CostingLevel costingLevel = productBL.getCostingLevel(productId, as);

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(as.getC_AcctSchema_ID())
				.costTypeId(p_M_CostType_ID)
				.productId(productId)
				.clientId(product.getAD_Client_ID())
				.orgId(p_AD_Org_ID)
				.attributeSetInstanceId(0)
				.build();

	}

	private int getCurrencyId()
	{
		final MAcctSchema as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
		return as.getC_Currency_ID();
	}

	private CostAmount zeroCosts()
	{
		return CostAmount.zero(getCurrencyId());
	}

	private Collection<MProduct> getProducts(final int lowLevel)
	{
		final List<Object> params = new ArrayList<>();
		final StringBuffer whereClause = new StringBuffer("AD_Client_ID=?")
				.append(" AND ").append(I_M_Product.COLUMNNAME_LowLevel).append("=?");
		params.add(getAD_Client_ID());
		params.add(lowLevel);

		whereClause.append(" AND ").append(I_M_Product.COLUMNNAME_IsBOM).append("=?");
		params.add(true);

		if (p_M_Product_ID > 0)
		{
			whereClause.append(" AND ").append(I_M_Product.COLUMNNAME_M_Product_ID).append("=?");
			params.add(p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID > 0)
		{
			whereClause.append(" AND ").append(I_M_Product.COLUMNNAME_M_Product_Category_ID).append("=?");
			params.add(p_M_Product_Category_ID);
		}
		if (p_M_Product_ID <= 0 && p_ProductType != null)
		{
			whereClause.append(" AND ").append(I_M_Product.COLUMNNAME_ProductType).append("=?");
			params.add(p_ProductType);
		}

		return new Query(getCtx(), I_M_Product.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.list();
	}

	/**
	 * Reset LowLevel Costs for products with LowLevel=0 (items)
	 */
	private void resetCostsLLForLLC0()
	{
		final List<Object> params = new ArrayList<>();
		final StringBuffer productWhereClause = new StringBuffer();
		productWhereClause.append("AD_Client_ID=? AND " + I_M_Product.COLUMNNAME_LowLevel + "=?");
		params.add(getAD_Client_ID());
		params.add(0);
		if (p_M_Product_ID > 0)
		{
			productWhereClause.append(" AND ").append(I_M_Product.COLUMNNAME_M_Product_ID).append("=?");
			params.add(p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID > 0)
		{
			productWhereClause.append(" AND ").append(I_M_Product.COLUMNNAME_M_Product_Category_ID).append("=?");
			params.add(p_M_Product_Category_ID);
		}
		//
		final String sql = "UPDATE M_Cost c SET " + I_M_Cost.COLUMNNAME_CurrentCostPriceLL + "=0"
				+ " WHERE EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID=c.M_Product_ID"
				+ " AND " + productWhereClause + ")";
		final int no = DB.executeUpdateEx(sql, params.toArray(), get_TrxName());
		log.info("Updated #" + no);
	}

	private List<CostElement> m_costElements = null;

	private List<CostElement> getCostElements()
	{
		if (m_costElements == null)
		{
			m_costElements = costElementsRepo.getByCostingMethod(CostingMethod.ofCode(p_ConstingMethod));
		}
		return m_costElements;
	}

	/**
	 * Create Cost Rollup Notice
	 * 
	 * @param product
	 * @param msg
	 */
	private void createNotice(final MProduct product, final String msg)
	{
		final String productValue = product != null ? product.getValue() : "-";
		addLog("WARNING: Product " + productValue + ": " + msg);
	}
}
