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
 * Bogdan Ioan, www.arhipac.ro *
 *****************************************************************************/

package org.eevolution.process;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.engines.CostEngine;
import org.adempiere.model.engines.CostEngineFactory;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWorkflow;
import org.eevolution.api.IPPWorkflowDAO;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

/**
 * RollUp of Cost Manufacturing Workflow
 * This process calculate the Labor, Overhead, Burden Cost
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: RollupWorkflow.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 *
 * @author Bogdan Ioan, www.arhipac.ro
 *         <li>BF [ 2093001 ] Error in Cost Workflow & Process Details
 */
public class RollupWorkflow extends JavaProcess
{
	private final ICostElementRepository costElementsRepo = Adempiere.getBean(ICostElementRepository.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	private final IPPWorkflowDAO workflowsRepo = Services.get(IPPWorkflowDAO.class);

	private final ICurrentCostsRepository currentCostsRepo = Adempiere.getBean(ICurrentCostsRepository.class);

	/* Organization */
	private OrgId p_AD_Org_ID = OrgId.ANY;
	/* Cost Type */
	private CostTypeId p_M_CostType_ID;
	/* Product */
	private ProductId p_M_Product_ID;
	/* Product Category */
	private ProductCategoryId p_M_Product_Category_ID;
	/* Costing Method */
	private CostingMethod p_ConstingMethod = CostingMethod.StandardCosting;

	private AcctSchema acctSchema = null;

	private CostEngine costEngine;
	private RoutingService routingService = null;

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
			else if (name.equals(I_M_Cost.COLUMNNAME_AD_Org_ID))
			{
				p_AD_Org_ID = OrgId.ofRepoIdOrAny(para.getParameterAsInt());
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_C_AcctSchema_ID))
			{
				final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(para.getParameterAsInt());
				acctSchema = acctSchemasRepo.getById(acctSchemaId);
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_CostType_ID))
			{
				p_M_CostType_ID = CostTypeId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_CostElement.COLUMNNAME_CostingMethod))
			{
				p_ConstingMethod = CostingMethod.ofCode(para.getParameterAsString());
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_ID))
			{
				p_M_Product_ID = ProductId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_Category_ID))
			{
				p_M_Product_Category_ID = ProductCategoryId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	}	// prepare

	@Override
	protected String doIt()
	{
		costEngine = CostEngineFactory.newCostEngine();
		routingService = RoutingServiceFactory.get().getRoutingService();

		for (final I_M_Product product : getProducts())
		{
			rollup(product);
		}
		return "@OK@";
	}

	private void rollup(final I_M_Product product)
	{
		log.info("Product: {}", product);

		int workflowId = 0;
		I_PP_Product_Planning pp = null;
		if (workflowId <= 0)
		{
			workflowId = workflowsRepo.retrieveWorkflowIdForProduct(product);
		}
		if (workflowId <= 0)
		{
			pp = Services.get(IProductPlanningDAO.class).find(ProductPlanningQuery.builder()
					.orgId(p_AD_Org_ID.getRepoId())
					.productId(p_M_Product_ID)
					.build());
			if (pp != null)
			{
				workflowId = pp.getAD_Workflow_ID();
			}
			else
			{
				createNotice(product, "@NotFound@ @PP_Product_Planning_ID@");
			}
		}
		if (workflowId <= 0)
		{
			createNotice(product, "@NotFound@ @AD_Workflow_ID@");
			return;
		}

		final MWorkflow workflowRecord = new MWorkflow(getCtx(), workflowId, get_TrxName());
		final List<MWFNode> nodes = Arrays.asList(workflowRecord.getNodes(false, getAD_Client_ID()));
		final WorkflowAndNodes workflow = WorkflowAndNodes.of(product, workflowRecord, nodes);
		rollup(workflow);

		// Update Product Data Planning
		if (pp != null)
		{
			pp.setYield(workflowRecord.getYield());
			saveRecord(pp);
		}
	}

	private List<I_M_Product> getProducts()
	{
		final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMN_AD_Client_ID, getAD_Client_ID())
				.addEqualsFilter(I_M_Product.COLUMN_ProductType, X_M_Product.PRODUCTTYPE_Item)
				.addEqualsFilter(I_M_Product.COLUMN_IsBOM, true);

		if (p_M_Product_ID != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, p_M_Product_Category_ID);
		}

		return queryBuilder
				.orderBy(I_M_Product.COLUMNNAME_LowLevel)
				.create()
				.list();
	}

	public void rollup(final WorkflowAndNodes workflow)
	{
		log.info("Workflow: {}", workflow);

		resetWorkflowAndNodeCosts(workflow);
		rollupDurations(workflow);

		final CostSegment costSegment = createCostSegment(workflow.getProduct());
		for (final CostElement element : costElementsRepo.getByCostingMethod(p_ConstingMethod))
		{
			if (!element.isActivityControlElement())
			{
				continue;
			}

			final CostElementId costElementId = element.getId();

			currentCostsRepo.updateCostRecord(costSegment, costElementId, costRecord -> {
				updateCostRecord(costRecord, costSegment, costElementId, workflow);
			});
		}

		//
		// Save Workflow & Nodes
		saveWorkflowAndNodes(workflow);
	}

	private void resetWorkflowAndNodeCosts(final WorkflowAndNodes workflow)
	{
		final I_AD_Workflow workflowRecord = workflow.getWorkflowRecord();

		workflowRecord.setCost(BigDecimal.ZERO);

		for (final I_AD_WF_Node node : workflow.getNodes())
		{
			node.setCost(BigDecimal.ZERO);
		}

		workflowRecord.setCost(BigDecimal.ZERO);
	}

	private void rollupDurations(final WorkflowAndNodes workflow)
	{
		final RoutingDuration routingDuration = new RoutingDuration();
		for (final I_AD_WF_Node node : workflow.getNodes())
		{
			routingDuration.addNode(node);
		}
		routingDuration.applyToWorkflow(workflow.getWorkflowRecord());
	}

	private void saveWorkflowAndNodes(final WorkflowAndNodes workflow)
	{
		for (final I_AD_WF_Node node : workflow.getNodes())
		{
			saveRecord(node);
		}
		saveRecord(workflow.getWorkflowRecord());
	}

	private CostAmount updateCostRecord(final I_M_Cost costRecord, final CostSegment costSegment, final CostElementId costElementId, final WorkflowAndNodes workflow)
	{
		final CostAmount segmentCost = computeAndUpdateWorkflowCost(costSegment, costElementId, workflow);
		costRecord.setCurrentCostPrice(segmentCost.getValue());
		// NOTE: don't save, will be saved by caller
		return segmentCost;
	}

	private CostAmount computeAndUpdateWorkflowCost(final CostSegment costSegment, final CostElementId costElementId, final WorkflowAndNodes workflow)
	{
		final AcctSchemaId acctSchemaId = costSegment.getAcctSchemaId();
		final AcctSchema acctSchema = acctSchemasRepo.getById(acctSchemaId);
		final int precision = acctSchema.getCosting().getCostingPrecision();

		CostAmount segmentCost = CostAmount.zero(acctSchema.getCurrencyId());
		for (final MWFNode node : workflow.getNodes())
		{
			final CostAmount nodeCost = computeAndUpdateNodeCost(costSegment, costElementId, node, precision);
			segmentCost = segmentCost.add(nodeCost);
		}

		// Update Workflow cost
		final I_AD_Workflow workflowRecord = workflow.getWorkflowRecord();
		workflowRecord.setCost(workflowRecord.getCost().add(segmentCost.getValue()));

		return segmentCost;
	}

	private CostAmount computeAndUpdateNodeCost(final CostSegment costSegment, final CostElementId costElementId, final MWFNode node, final int precision)
	{
		final CostAmount nodeCost = computeNodeCost(costSegment, costElementId, node, precision);

		// Update AD_WF_Node.Cost:
		node.setCost(node.getCost().add(nodeCost.getValue()));

		return nodeCost;
	}

	private CostAmount computeNodeCost(final CostSegment costSegment, final CostElementId costElementId, final MWFNode node, final int precision)
	{
		final ResourceId stdResourceId = ResourceId.ofRepoId(node.getS_Resource_ID());
		final CostSegment resourceCostSegment = createCostSegment(costSegment, stdResourceId);

		final CostAmount rate = costEngine.getProductActualCostPrice(resourceCostSegment, costElementId);
		final Quantity baseValue = routingService.getResourceBaseValue(stdResourceId, node);
		final CostAmount nodeCost = rate.multiply(baseValue)
				.roundToPrecisionIfNeeded(precision);
		return nodeCost;
	}

	private CostSegment createCostSegment(final I_M_Product product)
	{
		final CostTypeId costTypeId = p_M_CostType_ID != null ? p_M_CostType_ID : acctSchema.getCosting().getCostTypeId();
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(product, acctSchema);

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchema.getId())
				.costTypeId(costTypeId)
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.clientId(ClientId.ofRepoId(getAD_Client_ID()))
				.orgId(p_AD_Org_ID)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();
	}

	private CostSegment createCostSegment(final CostSegment costSegment, final ResourceId resourceId)
	{
		final I_M_Product product = resourceProductService.getProductByResourceId(resourceId);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final AcctSchema acctSchema = acctSchemasRepo.getById(costSegment.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(product, acctSchema);

		return costSegment.withProductIdAndCostingLevel(productId, costingLevel);
	}

	/**
	 * Create Cost Rollup Notice
	 *
	 * @param product
	 * @param msg
	 */
	private void createNotice(final I_M_Product product, final String msg)
	{
		final String productValue = product != null ? product.getValue() : "-";
		addLog("WARNING: Product " + productValue + ": " + msg);
	}

	@Value(staticConstructor = "of")
	private static class WorkflowAndNodes
	{
		@NonNull
		I_M_Product product;
		@NonNull
		I_AD_Workflow workflowRecord;
		@NonNull
		List<MWFNode> nodes;
	}

	private static class RoutingDuration
	{
		private double Yield = 1;
		private int QueuingTime = 0;
		private int SetupTime = 0;
		private int Duration = 0;
		private int WaitingTime = 0;
		private int MovingTime = 0;
		private int WorkingTime = 0;

		public void addNode(final I_AD_WF_Node node)
		{
			if (node.getYield() != 0)
			{
				Yield = Yield * ((double)node.getYield() / 100);
			}
			// We use node.getDuration() instead of m_routingService.estimateWorkingTime(node) because
			// this will be the minimum duration of this node. So even if the node have defined units/cycle
			// we consider entire duration of the node.
			final long nodeDuration = node.getDuration();

			QueuingTime += node.getQueuingTime();
			SetupTime += node.getSetupTime();
			Duration += nodeDuration;
			WaitingTime += node.getWaitingTime();
			MovingTime += node.getMovingTime();
			WorkingTime += node.getWorkingTime();
		}

		public void applyToWorkflow(final I_AD_Workflow workflow)
		{
			workflow.setYield((int)(Yield * 100));
			workflow.setQueuingTime(QueuingTime);
			workflow.setSetupTime(SetupTime);
			workflow.setDuration(Duration);
			workflow.setWaitingTime(WaitingTime);
			workflow.setMovingTime(MovingTime);
			workflow.setWorkingTime(WorkingTime);
		}
	}
}
