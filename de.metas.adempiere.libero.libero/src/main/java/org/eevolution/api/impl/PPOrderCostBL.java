package org.eevolution.api.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Cost;
import org.eevolution.model.I_PP_Order_Node;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;

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
		final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrder);
		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getByCliendAndOrg(ctx);
		final AcctSchemaId acctSchemaId = as.getId();
		final CostTypeId costTypeId = as.getCosting().getCostTypeId();

		final Set<CostSegment> costSegments = new LinkedHashSet<>();

		//
		// Create Standard Costs for Order Header (resulting product)
		{
			final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
			costSegments.add(CostSegment.builder()
					.costingLevel(productCostingBL.getCostingLevel(productId, as))
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.productId(productId)
					.clientId(ClientId.ofRepoId(ppOrder.getAD_Client_ID()))
					.orgId(OrgId.ofRepoId(ppOrder.getAD_Org_ID()))
					.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getM_AttributeSetInstance_ID()))
					.build());
		}

		//
		// Create Standard Costs for Order BOM Line
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder);
		for (final I_PP_Order_BOMLine line : ppOrderBOMLines)
		{
			final ProductId productId = ProductId.ofRepoId(line.getM_Product_ID());
			costSegments.add(CostSegment.builder()
					.costingLevel(productCostingBL.getCostingLevel(productId, as))
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.productId(productId)
					.clientId(ClientId.ofRepoId(line.getAD_Client_ID()))
					.orgId(OrgId.ofRepoId(line.getAD_Org_ID()))
					.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(line.getM_AttributeSetInstance_ID()))
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
			final ResourceId resourceId = ResourceId.ofRepoId(node.getS_Resource_ID());
			final I_M_Product resourceProduct = Services.get(IResourceProductService.class).getProductByResourceId(resourceId);
			if (resourceProduct == null)
			{
				// shall not happen, but we can skip it for now
				continue;
			}
			final ProductId resourceProductId = ProductId.ofRepoId(resourceProduct.getM_Product_ID());

			costSegments.add(CostSegment.builder()
					.costingLevel(productCostingBL.getCostingLevel(resourceProduct, as))
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.productId(resourceProductId)
					.clientId(ClientId.ofRepoId(node.getAD_Client_ID()))
					.orgId(OrgId.ofRepoId(node.getAD_Org_ID()))
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.build());
		}

		return costSegments;
	}

	private void createPPOrderCosts(final I_PP_Order ppOrder, final CostSegment costSegment)
	{
		final ICurrentCostsRepository currentCostsRepository = Adempiere.getBean(ICurrentCostsRepository.class);
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

		ppOrderCost.setAD_Org_ID(costSegment.getOrgId().getRepoId());
		ppOrderCost.setC_AcctSchema_ID(costSegment.getAcctSchemaId().getRepoId());
		ppOrderCost.setM_CostType_ID(costSegment.getCostTypeId().getRepoId());
		ppOrderCost.setM_Product_ID(costSegment.getProductId().getRepoId());
		ppOrderCost.setM_AttributeSetInstance_ID(costSegment.getAttributeSetInstanceId().getRepoId());

		ppOrderCost.setM_CostElement_ID(costElement.getId().getRepoId());

		ppOrderCost.setCurrentCostPrice(amount.getValue());
		// ppOrderCost.setCurrentCostPriceLL(cost.getCurrentCostPriceLL());
		// ppOrderCost.setCumulatedAmt(cost.getCumulatedAmt()); // TODO: delete it
		// ppOrderCost.setCumulatedQty(cost.getCumulatedQty()); // TODO: delete it

		InterfaceWrapperHelper.save(ppOrderCost);
	}

}
