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

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.model.engines.CostEngine;
import org.adempiere.model.engines.CostEngineFactory;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Cost;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.model.X_M_CostElement;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWorkflow;
import org.eevolution.api.IPPWorkflowDAO;
import org.eevolution.model.MPPProductPlanning;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;

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

	/* Organization */
	private OrgId p_AD_Org_ID = OrgId.ANY;
	/* Account Schema */
	private AcctSchemaId p_C_AcctSchema_ID;
	/* Cost Type */
	private CostTypeId p_M_CostType_ID;
	/* Product */
	private ProductId p_M_Product_ID;
	/* Product Category */
	private ProductCategoryId p_M_Product_Category_ID;
	/* Costing Method */
	private CostingMethod p_ConstingMethod = CostingMethod.StandardCosting;

	private AcctSchema acctSchema = null;

	private RoutingService m_routingService = null;

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
				acctSchema = Services.get(IAcctSchemaDAO.class).getById(p_C_AcctSchema_ID);
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_CostType_ID))
				p_M_CostType_ID = CostTypeId.ofRepoIdOrNull(para.getParameterAsInt());
			else if (name.equals(X_M_CostElement.COLUMNNAME_CostingMethod))
				p_ConstingMethod = CostingMethod.ofCode(para.getParameterAsString());
			else if (name.equals(MProduct.COLUMNNAME_M_Product_ID))
				p_M_Product_ID = ProductId.ofRepoIdOrNull(para.getParameterAsInt());
			else if (name.equals(MProduct.COLUMNNAME_M_Product_Category_ID))
				p_M_Product_Category_ID = ProductCategoryId.ofRepoIdOrNull(para.getParameterAsInt());
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	// prepare

	@SuppressWarnings("deprecation") // hide those to not polute our Warnings
	@Override
	protected String doIt() throws Exception
	{
		m_routingService = RoutingServiceFactory.get().getRoutingService();

		for (MProduct product : getProducts())
		{
			log.info("Product: " + product);
			int AD_Workflow_ID = 0;
			MPPProductPlanning pp = null;
			if (AD_Workflow_ID <= 0)
			{
				AD_Workflow_ID = Services.get(IPPWorkflowDAO.class).retrieveWorkflowIdForProduct(product);
			}
			if (AD_Workflow_ID <= 0)
			{
				pp = MPPProductPlanning.find(getCtx(), p_AD_Org_ID.getRepoId(), 0, 0, product.get_ID(), get_TrxName());

				if (pp != null)
				{
					AD_Workflow_ID = pp.getAD_Workflow_ID();
				}
				else
				{
					createNotice(product, "@NotFound@ @PP_Product_Planning_ID@");
				}
			}

			if (AD_Workflow_ID <= 0)
			{
				createNotice(product, "@NotFound@ @AD_Workflow_ID@");
				continue;
			}

			MWorkflow workflow = new MWorkflow(getCtx(), AD_Workflow_ID, get_TrxName());
			rollup(product, workflow);

			// Update Product Data Planning
			if (pp != null)
			{
				pp.setYield(workflow.getYield());
				pp.saveEx();
			}
		}
		return "@OK@";
	}

	private Collection<MProduct> getProducts()
	{
		List<Object> params = new ArrayList<>();
		StringBuffer whereClause = new StringBuffer("AD_Client_ID=?");
		params.add(getAD_Client_ID());

		whereClause.append(" AND ").append(MProduct.COLUMNNAME_ProductType).append("=?");
		params.add(MProduct.PRODUCTTYPE_Item);

		whereClause.append(" AND ").append(MProduct.COLUMNNAME_IsBOM).append("=?");
		params.add(true);

		if (p_M_Product_ID != null)
		{
			whereClause.append(" AND ").append(MProduct.COLUMNNAME_M_Product_ID).append("=?");
			params.add(p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID != null)
		{
			whereClause.append(" AND ").append(MProduct.COLUMNNAME_M_Product_Category_ID).append("=?");
			params.add(p_M_Product_Category_ID);
		}

		Collection<MProduct> products = new Query(getCtx(), MProduct.Table_Name, whereClause.toString(), get_TrxName())
				.setOrderBy(MProduct.COLUMNNAME_LowLevel)
				.setParameters(params)
				.list(MProduct.class);
		return products;
	}

	public void rollup(MProduct product, MWorkflow workflow)
	{
		log.info("Workflow: " + workflow);
		workflow.setCost(BigDecimal.ZERO);
		double Yield = 1;
		int QueuingTime = 0;
		int SetupTime = 0;
		int Duration = 0;
		int WaitingTime = 0;
		int MovingTime = 0;
		int WorkingTime = 0;

		MWFNode[] nodes = workflow.getNodes(false, getAD_Client_ID());
		for (MWFNode node : nodes)
		{
			node.setCost(BigDecimal.ZERO);
			if (node.getYield() != 0)
			{
				Yield = Yield * ((double)node.getYield() / 100);
			}
			// We use node.getDuration() instead of m_routingService.estimateWorkingTime(node) because
			// this will be the minimum duration of this node. So even if the node have defined units/cycle
			// we consider entire duration of the node.
			long nodeDuration = node.getDuration();

			QueuingTime += node.getQueuingTime();
			SetupTime += node.getSetupTime();
			Duration += nodeDuration;
			WaitingTime += node.getWaitingTime();
			MovingTime += node.getMovingTime();
			WorkingTime += node.getWorkingTime();
		}
		workflow.setCost(BigDecimal.ZERO);
		workflow.setYield((int)(Yield * 100));
		workflow.setQueuingTime(QueuingTime);
		workflow.setSetupTime(SetupTime);
		workflow.setDuration(Duration);
		workflow.setWaitingTime(WaitingTime);
		workflow.setMovingTime(MovingTime);
		workflow.setWorkingTime(WorkingTime);

		for (final CostElement element : costElementsRepo.getByCostingMethod(p_ConstingMethod))
		{
			if (!element.isActivityControlElement())
			{
				continue;
			}
			final CostDimension d = new CostDimension(product, acctSchema, p_M_CostType_ID, p_AD_Org_ID, AttributeSetInstanceId.NONE, element.getId());
			final List<I_M_Cost> costs = d.toQuery(I_M_Cost.class, get_TrxName()).list();
			for (I_M_Cost cost : costs)
			{
				final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(cost.getC_AcctSchema_ID());
				final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
				final int precision = acctSchema.getCosting().getCostingPrecision();

				CostAmount segmentCost = CostAmount.zero(acctSchema.getCurrencyId());
				for (final MWFNode node : nodes)
				{
					final ClientId clientId = ClientId.ofRepoId(node.getAD_Client_ID());
					final ResourceId resourceId = ResourceId.ofRepoId(node.getS_Resource_ID());

					final CostEngine costEngine = CostEngineFactory.getCostEngine(clientId);
					final CostAmount rate = costEngine.getResourceActualCostRate(resourceId, d);
					final Quantity baseValue = m_routingService.getResourceBaseValue(resourceId, node);
					CostAmount nodeCost = rate.multiply(baseValue)
							.roundToPrecisionIfNeeded(precision);
					segmentCost = segmentCost.add(nodeCost);

					log.info("Element : " + element + ", Node=" + node
							+ ", BaseValue=" + baseValue + ", rate=" + rate
							+ ", nodeCost=" + nodeCost + " => Cost=" + segmentCost);

					// Update AD_WF_Node.Cost:
					node.setCost(node.getCost().add(nodeCost.getValue()));
				}
				//
				cost.setCurrentCostPrice(segmentCost.getValue());
				InterfaceWrapperHelper.save(cost);

				// Update Workflow cost
				workflow.setCost(workflow.getCost().add(segmentCost.getValue()));
			} // MCost
		} // Cost Elements
			 //
			 // Save Workflow & Nodes
		for (MWFNode node : nodes)
		{
			node.saveEx();
		}
		workflow.saveEx();
		log.info("Product: " + product.getName() + " WFCost: " + workflow.getCost());
	}

	/**
	 * Create Cost Rollup Notice
	 * 
	 * @param product
	 * @param msg
	 */
	private void createNotice(MProduct product, String msg)
	{
		String productValue = product != null ? product.getValue() : "-";
		addLog("WARNING: Product " + productValue + ": " + msg);
	}
}
