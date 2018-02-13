package org.eevolution.api.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Cost;
import org.eevolution.model.I_PP_Order_Node;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.product.IProductBL;

public class PPOrderCostBL implements IPPOrderCostBL
{
	@Override
	public void createStandardCosts(final I_PP_Order ppOrder)
	{
		//
		// Before creating the new PP_Order_Cost we need to delete the existing records
		// (i.e. handling the case when a re-activated PP_Order is completed again)
		Services.get(IPPOrderCostDAO.class).deleteOrderCosts(ppOrder);

		//
		extractCostSegments(ppOrder)
				.forEach(costSegment -> createPPOrderCosts(ppOrder, costSegment));
	}

	private Set<CostSegment> extractCostSegments(final I_PP_Order ppOrder)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrder);
		final I_C_AcctSchema as = Services.get(IAcctSchemaDAO.class).retrieveAcctSchema(ctx);

		final Set<CostSegment> costSegments = new LinkedHashSet<>();

		//
		// Create Standard Costs for Order Header (resulting product)
		{
			final int productId = ppOrder.getM_Product_ID();
			costSegments.add(CostSegment.builder()
					.costingLevel(productBL.getCostingLevel(productId, as))
					.acctSchemaId(as.getC_AcctSchema_ID())
					.costTypeId(as.getM_CostType_ID())
					.productId(productId)
					.clientId(ppOrder.getAD_Client_ID())
					.orgId(ppOrder.getAD_Org_ID())
					.attributeSetInstanceId(ppOrder.getM_AttributeSetInstance_ID())
					.build());
		}

		//
		// Create Standard Costs for Order BOM Line
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder);
		for (final I_PP_Order_BOMLine line : ppOrderBOMLines)
		{
			final int productId = line.getM_Product_ID();
			costSegments.add(CostSegment.builder()
					.costingLevel(productBL.getCostingLevel(productId, as))
					.acctSchemaId(as.getC_AcctSchema_ID())
					.costTypeId(as.getM_CostType_ID())
					.productId(productId)
					.clientId(line.getAD_Client_ID())
					.orgId(line.getAD_Org_ID())
					.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
					.build());
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
			final I_M_Product resourceProduct = Services.get(IResourceProductService.class).retrieveProductForResource(node.getS_Resource());
			if (resourceProduct == null)
			{
				// shall not happen, but we can skip it for now
				continue;
			}
			final int resourceProductId = resourceProduct.getM_Product_ID();

			costSegments.add(CostSegment.builder()
					.costingLevel(productBL.getCostingLevel(resourceProduct, as))
					.acctSchemaId(as.getC_AcctSchema_ID())
					.costTypeId(as.getM_CostType_ID())
					.productId(resourceProductId)
					.clientId(node.getAD_Client_ID())
					.orgId(node.getAD_Org_ID())
					.attributeSetInstanceId(0)
					.build());
		}

		return costSegments;
	}

	private void createPPOrderCosts(final I_PP_Order ppOrder, final CostSegment costSegment)
	{
		final CurrentCostsRepository currentCostsRepository = Adempiere.getBean(CurrentCostsRepository.class);
		final CostResult costs = currentCostsRepository.getByCostSegmentAndCostingMethod(costSegment, CostingMethod.StandardCosting);
		for (final CostElement costElement : costs.getCostElements())
		{
			createPPOrderCost(ppOrder, costs.getCostSegment(), costElement, costs.getCostAmountForCostElement(costElement));
		}
	}

	private void createPPOrderCost(final I_PP_Order ppOrder, final CostSegment costSegment, final CostElement costElement, final CostAmount amount)
	{
		final I_PP_Order_Cost ppOrderCost = InterfaceWrapperHelper.newInstance(I_PP_Order_Cost.class, ppOrder);
		ppOrderCost.setPP_Order_ID(ppOrder.getPP_Order_ID());

		ppOrderCost.setAD_Org_ID(costSegment.getOrgId());
		ppOrderCost.setC_AcctSchema_ID(costSegment.getAcctSchemaId());
		ppOrderCost.setM_CostType_ID(costSegment.getCostTypeId());
		ppOrderCost.setM_Product_ID(costSegment.getProductId());
		ppOrderCost.setM_AttributeSetInstance_ID(costSegment.getAttributeSetInstanceId());

		ppOrderCost.setM_CostElement_ID(costElement.getId());

		ppOrderCost.setCurrentCostPrice(amount.getValue());
		// ppOrderCost.setCurrentCostPriceLL(cost.getCurrentCostPriceLL());
		// ppOrderCost.setCumulatedAmt(cost.getCumulatedAmt()); // TODO: delete it
		// ppOrderCost.setCumulatedQty(cost.getCumulatedQty()); // TODO: delete it

		InterfaceWrapperHelper.save(ppOrderCost);
	}

}
