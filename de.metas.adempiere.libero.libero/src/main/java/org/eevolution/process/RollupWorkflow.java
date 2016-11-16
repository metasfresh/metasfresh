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
 *                 Bogdan Ioan, www.arhipac.ro                                *
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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.model.engines.CostDimension;
import org.adempiere.model.engines.CostEngine;
import org.adempiere.model.engines.CostEngineFactory;
import org.adempiere.util.Services;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWorkflow;
import org.eevolution.api.IPPWorkflowDAO;
import org.eevolution.model.MPPProductPlanning;
import org.eevolution.model.RoutingService;
import org.eevolution.model.RoutingServiceFactory;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 *	RollUp of Cost Manufacturing Workflow
 *	This process calculate the Labor, Overhead, Burden Cost
 *  @author Victor Perez, e-Evolution, S.C.
 *  @version $Id: RollupWorkflow.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 *
 *  @author Bogdan Ioan, www.arhipac.ro
 *  		<li>BF [ 2093001 ] Error in Cost Workflow & Process Details
 */
public class RollupWorkflow extends SvrProcess
{

	/* Organization     */
	private int		 		p_AD_Org_ID = 0;
	/* Account Schema   */
	private int             p_C_AcctSchema_ID = 0;
	/* Cost Type 		*/
	private int             p_M_CostType_ID = 0;
	/* Product 			*/
	private int             p_M_Product_ID = 0;
	/* Product Category */
	private int 			p_M_Product_Category_ID = 0;
	/* Costing Method 	*/
	private String 			p_ConstingMethod = MCostElement.COSTINGMETHOD_StandardCosting;

	private MAcctSchema m_as = null;

	private RoutingService m_routingService = null;


	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();

			if (para.getParameter() == null)
				;
			else if (name.equals(MCost.COLUMNNAME_AD_Org_ID))
				p_AD_Org_ID = para.getParameterAsInt();
			else if (name.equals(MCost.COLUMNNAME_C_AcctSchema_ID))
			{
				p_C_AcctSchema_ID = para.getParameterAsInt();
				m_as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
			}
			else if (name.equals(MCost.COLUMNNAME_M_CostType_ID))
				p_M_CostType_ID = para.getParameterAsInt();
			else if (name.equals(MCostElement.COLUMNNAME_CostingMethod))
				p_ConstingMethod=(String)para.getParameter();
			else if (name.equals(MProduct.COLUMNNAME_M_Product_ID))
				p_M_Product_ID = para.getParameterAsInt();
			else if (name.equals(MProduct.COLUMNNAME_M_Product_Category_ID))
				p_M_Product_Category_ID = para.getParameterAsInt();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	@SuppressWarnings("deprecation") // hide those to not polute our Warnings
	@Override
	protected String doIt() throws Exception
	{
		m_routingService = RoutingServiceFactory.get().getRoutingService(getAD_Client_ID());

		for (MProduct product : getProducts())
		{
			log.info("Product: "+product);
			int AD_Workflow_ID = 0;
			MPPProductPlanning pp = null;
			if (AD_Workflow_ID <= 0)
			{
				AD_Workflow_ID = Services.get(IPPWorkflowDAO.class).retrieveWorkflowIdForProduct(product);
			}
			if(AD_Workflow_ID <= 0)
			{
				pp = MPPProductPlanning.find(getCtx(), p_AD_Org_ID, 0, 0, product.get_ID(), get_TrxName());

				if (pp != null)
				{
				AD_Workflow_ID = pp.getAD_Workflow_ID();
				}
				else
				{
				createNotice(product, "@NotFound@ @PP_Product_Planning_ID@");
				}
			}

			if(AD_Workflow_ID <= 0)
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
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer("AD_Client_ID=?");
		params.add(getAD_Client_ID());

		whereClause.append(" AND ").append(MProduct.COLUMNNAME_ProductType).append("=?");
		params.add(MProduct.PRODUCTTYPE_Item);

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

		Collection<MProduct> products = new Query(getCtx(),MProduct.Table_Name, whereClause.toString(), get_TrxName())
											.setOrderBy(MProduct.COLUMNNAME_LowLevel)
											.setParameters(params)
											.list();
		return products;
	}


	public void rollup(MProduct product, MWorkflow workflow)
	{
		log.info("Workflow: "+workflow);
		workflow.setCost(Env.ZERO);
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
			node.setCost(Env.ZERO);
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
		workflow.setCost(Env.ZERO);
		workflow.setYield((int)(Yield * 100));
		workflow.setQueuingTime(QueuingTime);
		workflow.setSetupTime(SetupTime);
		workflow.setDuration(Duration);
		workflow.setWaitingTime(WaitingTime);
		workflow.setMovingTime(MovingTime);
		workflow.setWorkingTime(WorkingTime);

		for (I_M_CostElement element : MCostElement.getByCostingMethod(getCtx(), p_ConstingMethod))
		{
			if (!CostEngine.isActivityControlElement(element))
			{
				continue;
			}
			final CostDimension d = new CostDimension(product, m_as, p_M_CostType_ID, p_AD_Org_ID, 0, element.getM_CostElement_ID());
			final List<MCost> costs = d.toQuery(MCost.class, get_TrxName()).list();
			for (MCost cost : costs)
			{
				final int precision = MAcctSchema.get(Env.getCtx(), cost.getC_AcctSchema_ID()).getCostingPrecision();
				BigDecimal segmentCost = Env.ZERO;
				for (MWFNode node : nodes)
				{
					final CostEngine costEngine = CostEngineFactory.getCostEngine(node.getAD_Client_ID());
					final BigDecimal rate = costEngine.getResourceActualCostRate(null, node.getS_Resource_ID(), d, get_TrxName());
					final BigDecimal baseValue = m_routingService.getResourceBaseValue(node.getS_Resource_ID(), node);
					BigDecimal nodeCost = baseValue.multiply(rate);
					if (nodeCost.scale() > precision)
					{
						nodeCost = nodeCost.setScale(precision, RoundingMode.HALF_UP);
					}
					segmentCost = segmentCost.add(nodeCost);
					log.info("Element : "+element+", Node="+node
							+", BaseValue="+baseValue+", rate="+rate
							+", nodeCost="+nodeCost+" => Cost="+segmentCost);
					// Update AD_WF_Node.Cost:
					node.setCost(node.getCost().add(nodeCost));
				}
				//
				cost.setCurrentCostPrice(segmentCost);
				cost.saveEx();
				// Update Workflow cost
				workflow.setCost(workflow.getCost().add(segmentCost));
			} // MCost
		} // Cost Elements
		//
		// Save Workflow & Nodes
		for (MWFNode node : nodes)
		{
			node.saveEx();
		}
		workflow.saveEx();
		log.info("Product: "+product.getName()+" WFCost: " + workflow.getCost());
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
