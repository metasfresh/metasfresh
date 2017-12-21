package org.eevolution.api.impl;

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


import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Cost;
import org.eevolution.model.I_PP_Order_Node;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;

public class PPOrderCostBL implements IPPOrderCostBL
{
	private final transient Logger log = LogManager.getLogger(getClass());

	@Override
	public void createStandardCosts(final I_PP_Order ppOrder)
	{
		//
		// Before creating the new PP_Order_Cost we need to delete the existing records
		// (i.e. handling the case when a re-activated PP_Order is completed again)
		Services.get(IPPOrderCostDAO.class).deleteOrderCosts(ppOrder);
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrder);

		final I_C_AcctSchema as = Services.get(IAcctSchemaDAO.class).retrieveAcctSchema(ctx);
		log.info("Cost_Group_ID" + as.getM_CostType_ID());

		final Set<Integer> productIdsAdded = new TreeSet<Integer>();

		//
		// Create Standard Costs for Order Header (resulting product)
		{
			final I_M_Product product = ppOrder.getM_Product();
			productIdsAdded.add(product.getM_Product_ID());
			//
			final CostDimension d = new CostDimension(product, as, as.getM_CostType_ID(),
					ppOrder.getAD_Org_ID(),
					ppOrder.getM_AttributeSetInstance_ID(),
					CostDimension.ANY);
			createPPOrderCosts(ppOrder, d);
		}

		//
		// Create Standard Costs for Order BOM Line
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder);
		for (I_PP_Order_BOMLine line : ppOrderBOMLines)
		{
			final I_M_Product product = line.getM_Product();
			//
			// Check if we already added this product
			if (productIdsAdded.contains(product.getM_Product_ID()))
			{
				continue;
			}
			productIdsAdded.add(product.getM_Product_ID());
			//
			final CostDimension d = new CostDimension(line.getM_Product(), as, as.getM_CostType_ID(),
					line.getAD_Org_ID(), line.getM_AttributeSetInstance_ID(),
					CostDimension.ANY);
			createPPOrderCosts(ppOrder, d);
		}

		//
		// Create Standard Costs from Activity Resources
		final List<I_PP_Order_Node> ppOrderNodes = Services.get(IPPOrderWorkflowDAO.class).retrieveNodes(ppOrder);
		for (final I_PP_Order_Node node : ppOrderNodes)
		{
			final int S_Resource_ID = node.getS_Resource_ID();
			if (S_Resource_ID <= 0)
			{
				continue;
			}

			//
			final I_M_Product resourceProduct = Services.get(IResourceProductService.class).retrieveProductForResource(node.getS_Resource());
			if (resourceProduct == null)
			{
				// shall not happen, but we can skip it for now
				continue;
			}

			//
			// Check if we already added this product
			final int resourceProductId = resourceProduct.getM_Product_ID();
			if (productIdsAdded.contains(resourceProductId))
			{
				continue;
			}
			productIdsAdded.add(resourceProductId);
			//
			final CostDimension d = new CostDimension(resourceProduct, as, as.getM_CostType_ID(),
					node.getAD_Org_ID(),
					0, // ASI
					CostDimension.ANY);
			createPPOrderCosts(ppOrder, d);
		}
	}

	private void createPPOrderCosts(final I_PP_Order ppOrder, final CostDimension costDimension)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(ppOrder);
		final Collection<I_M_Cost> costs = costDimension.toQuery(I_M_Cost.class, trxName).list();
		for (final I_M_Cost cost : costs)
		{
			createPPOrderCost(ppOrder, cost);
		}
	}

	private void createPPOrderCost(final I_PP_Order ppOrder, I_M_Cost cost)
	{
		final I_PP_Order_Cost ppOrderCost = InterfaceWrapperHelper.newInstance(I_PP_Order_Cost.class, ppOrder);
		ppOrderCost.setAD_Org_ID(ppOrder.getAD_Org_ID());
		ppOrderCost.setPP_Order_ID(ppOrder.getPP_Order_ID());
		ppOrderCost.setC_AcctSchema_ID(cost.getC_AcctSchema_ID());
		ppOrderCost.setM_CostType_ID(cost.getM_CostType_ID());
		ppOrderCost.setCumulatedAmt(cost.getCumulatedAmt());
		ppOrderCost.setCumulatedQty(cost.getCumulatedQty());
		ppOrderCost.setCurrentCostPrice(cost.getCurrentCostPrice());
		ppOrderCost.setCurrentCostPriceLL(cost.getCurrentCostPriceLL());
		ppOrderCost.setM_Product_ID(cost.getM_Product_ID());
		ppOrderCost.setM_AttributeSetInstance_ID(cost.getM_AttributeSetInstance_ID());
		ppOrderCost.setM_CostElement_ID(cost.getM_CostElement_ID());

		InterfaceWrapperHelper.save(ppOrderCost);
	}

}
