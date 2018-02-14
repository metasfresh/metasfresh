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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.model.I_AD_Workflow;
import org.compiere.wf.api.IADWorkflowBL;
import org.compiere.wf.api.IADWorkflowDAO;
import org.eevolution.api.IPPOrderWorkflowBL;
import org.eevolution.api.IPPWFNodeAssetDAO;
import org.eevolution.api.IPPWFNodeProductDAO;
import org.eevolution.exceptions.RoutingExpiredException;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_NodeNext;
import org.eevolution.model.I_PP_Order_Node_Asset;
import org.eevolution.model.I_PP_Order_Node_Product;
import org.eevolution.model.I_PP_Order_Workflow;
import org.eevolution.model.I_PP_WF_Node_Asset;
import org.eevolution.model.I_PP_WF_Node_Product;
import org.eevolution.model.MPPOrderNode;
import org.eevolution.model.X_PP_Order_Workflow;

import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.pporder.LiberoException;

public class PPOrderWorkflowBL implements IPPOrderWorkflowBL
{
	@Override
	public void createOrderWorkflow(final I_PP_Order ppOrder)
	{
		final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);
		final IADWorkflowBL workflowBL = Services.get(IADWorkflowBL.class);

		//
		// Validate AD_Workflow
		final I_AD_Workflow adWorkflow = ppOrder.getAD_Workflow();
		// Workflow should be validated first - teo_sarca [ 2817870 ]
		if (!adWorkflow.isValid())
		{
			throw new LiberoException("Routing is not valid. Please validate it first - " + adWorkflow.getValue()); // TODO: translate
		}
		final Timestamp dateStartSchedule = ppOrder.getDateStartSchedule();
		if (!workflowBL.isValidFromTo(adWorkflow, dateStartSchedule))
		{
			throw new RoutingExpiredException(adWorkflow, dateStartSchedule);
		}

		//
		// Create Order Workflow (header)
		final I_PP_Order_Workflow orderWorkflow = createOrderWorkflowHeader(ppOrder, adWorkflow);

		//
		// Create Order Workflow Nodes
		final List<I_PP_Order_Node> orderNodes = new ArrayList<>();
		final Map<Integer, I_PP_Order_Node> adWFNodeId2orderNode = new HashMap<>();
		for (final I_AD_WF_Node adWFNode : workflowDAO.retrieveNodes(adWorkflow, ppOrder.getAD_Client_ID()))
		{
			//
			// Validate Workflow Node
			if (!workflowBL.isValidFromTo(adWFNode, dateStartSchedule))
			{
				continue;
			}

			//
			// Create Order Node
			final I_PP_Order_Node orderNode = createOrderNode(ppOrder, orderWorkflow, adWFNode);
			orderNodes.add(orderNode);
			final int adWFNodeId = adWFNode.getAD_WF_Node_ID();
			adWFNodeId2orderNode.put(adWFNodeId, orderNode);

			//
			// Create Node Products
			for (final I_PP_WF_Node_Product wfnp : Services.get(IPPWFNodeProductDAO.class).retrieveForWFNode(adWFNode))
			{
				createOrderNodeProduct(orderNode, wfnp);
			}

			//
			// Create Node Assets
			for (I_PP_WF_Node_Asset wfna : Services.get(IPPWFNodeAssetDAO.class).retrieveForWFNode(adWFNode))
			{
				createOrderNodeAsset(orderNode, wfna);
			}
		}

		//
		// Set first node
		{
			final I_PP_Order_Node orderWorkflowFirstNode = adWFNodeId2orderNode.get(orderWorkflow.getAD_WF_Node_ID());
			orderWorkflow.setPP_Order_Node(orderWorkflowFirstNode);
			InterfaceWrapperHelper.save(orderWorkflow);
		}

		//
		// Create Node Transitions
		for (I_PP_Order_Node orderNode : orderNodes)
		{
			final I_AD_WF_Node adWFNode = orderNode.getAD_WF_Node();
			for (final I_AD_WF_NodeNext AD_WF_NodeNext : workflowDAO.retrieveNodeNexts(adWFNode, ppOrder.getAD_Client_ID()))
			{
				final I_PP_Order_NodeNext orderNodeNext = createOrderNodeNext(orderNode, AD_WF_NodeNext);

				final int nextAD_WF_Node_ID = orderNodeNext.getAD_WF_Next_ID();
				final I_PP_Order_Node nextPP_Order_Node = adWFNodeId2orderNode.get(nextAD_WF_Node_ID);
				orderNodeNext.setPP_Order_Next(nextPP_Order_Node);
				InterfaceWrapperHelper.save(orderNodeNext);
			}
		}
	}

	public I_PP_Order_Workflow createOrderWorkflowHeader(final I_PP_Order ppOrder, final I_AD_Workflow workflow)
	{
		final I_PP_Order_Workflow orderWorkflow = InterfaceWrapperHelper.newInstance(I_PP_Order_Workflow.class, ppOrder);
		orderWorkflow.setPP_Order(ppOrder);
		orderWorkflow.setAD_Org_ID(ppOrder.getAD_Org_ID());

		// Defaults
		orderWorkflow.setAccessLevel(X_PP_Order_Workflow.ACCESSLEVEL_Organization);
		// orderWorkflow.setAuthor (MClient.get(ctx).getName());
		orderWorkflow.setDurationUnit(X_PP_Order_Workflow.DURATIONUNIT_Day);
		orderWorkflow.setDuration(1);
		orderWorkflow.setEntityType("U");	// U
		orderWorkflow.setIsDefault(false);
		orderWorkflow.setPublishStatus(X_PP_Order_Workflow.PUBLISHSTATUS_UnderRevision);	// U
		orderWorkflow.setVersion(0);
		orderWorkflow.setCost(BigDecimal.ZERO);
		orderWorkflow.setWaitingTime(0);
		orderWorkflow.setWorkingTime(0);

		//
		orderWorkflow.setValue(workflow.getValue());
		orderWorkflow.setWorkflowType(workflow.getWorkflowType());
		orderWorkflow.setQtyBatchSize(workflow.getQtyBatchSize());
		orderWorkflow.setName(workflow.getName());
		orderWorkflow.setAccessLevel(workflow.getAccessLevel());
		orderWorkflow.setAuthor(workflow.getAuthor());
		orderWorkflow.setDurationUnit(workflow.getDurationUnit());
		orderWorkflow.setDuration(workflow.getDuration());
		orderWorkflow.setEntityType(workflow.getEntityType());
		orderWorkflow.setIsDefault(workflow.isDefault());
		orderWorkflow.setPublishStatus(workflow.getPublishStatus());
		orderWorkflow.setVersion(workflow.getVersion());
		orderWorkflow.setCost(workflow.getCost());
		orderWorkflow.setWaitingTime(workflow.getWaitingTime());
		orderWorkflow.setWorkingTime(workflow.getWorkingTime());
		orderWorkflow.setAD_WF_Responsible_ID(workflow.getAD_WF_Responsible_ID());
		orderWorkflow.setAD_Workflow_ID(workflow.getAD_Workflow_ID());
		orderWorkflow.setDurationLimit(workflow.getDurationLimit());
		orderWorkflow.setPriority(workflow.getPriority());
		// orderWorkflow.setValidateWorkflow(workflow.getValidateWorkflow()); // buttons do not need to copy
		orderWorkflow.setS_Resource_ID(workflow.getS_Resource_ID());
		orderWorkflow.setQueuingTime(workflow.getQueuingTime());
		orderWorkflow.setSetupTime(workflow.getSetupTime());
		orderWorkflow.setMovingTime(workflow.getMovingTime());
		orderWorkflow.setProcessType(workflow.getProcessType());
		orderWorkflow.setAD_Table_ID(workflow.getAD_Table_ID());
		orderWorkflow.setAD_WF_Node_ID(workflow.getAD_WF_Node_ID());
		orderWorkflow.setAD_WorkflowProcessor_ID(workflow.getAD_WorkflowProcessor_ID());
		orderWorkflow.setDescription(workflow.getDescription());
		orderWorkflow.setValidFrom(workflow.getValidFrom());
		orderWorkflow.setValidTo(workflow.getValidTo());

		InterfaceWrapperHelper.save(orderWorkflow);
		return orderWorkflow;
	}

	public I_PP_Order_Node createOrderNode(final I_PP_Order order, final I_PP_Order_Workflow orderWorkflow, final I_AD_WF_Node wfNode)
	{
		final I_PP_Order_Node orderNode = InterfaceWrapperHelper.newInstance(I_PP_Order_Node.class, orderWorkflow);
		orderNode.setAD_Org_ID(order.getAD_Org_ID());
		orderNode.setPP_Order(order);
		orderNode.setPP_Order_Workflow(orderWorkflow);
		//
		orderNode.setAction(wfNode.getAction());
		orderNode.setAD_WF_Node(wfNode);
		orderNode.setAD_WF_Responsible_ID(wfNode.getAD_WF_Responsible_ID());
		orderNode.setAD_Workflow_ID(wfNode.getAD_Workflow_ID());
		orderNode.setIsSubcontracting(wfNode.isSubcontracting());
		orderNode.setIsMilestone(wfNode.isMilestone());
		orderNode.setC_BPartner_ID(wfNode.getC_BPartner_ID());
		orderNode.setCost(wfNode.getCost());
		orderNode.setDuration(wfNode.getDuration());
		orderNode.setUnitsCycles(wfNode.getUnitsCycles().intValueExact());
		orderNode.setOverlapUnits(wfNode.getOverlapUnits());
		orderNode.setEntityType(wfNode.getEntityType());
		orderNode.setIsCentrallyMaintained(wfNode.isCentrallyMaintained());
		orderNode.setJoinElement(wfNode.getJoinElement()); // X
		orderNode.setDurationLimit(wfNode.getDurationLimit());
		orderNode.setName(wfNode.getName());
		orderNode.setPriority(wfNode.getPriority());
		orderNode.setSplitElement(wfNode.getSplitElement()); // X
		orderNode.setSubflowExecution(wfNode.getSubflowExecution());
		orderNode.setValue(wfNode.getValue());
		orderNode.setS_Resource_ID(wfNode.getS_Resource_ID());
		orderNode.setSetupTime(wfNode.getSetupTime());
		orderNode.setSetupTimeRequiered(wfNode.getSetupTime());
		orderNode.setMovingTime(wfNode.getMovingTime());
		orderNode.setWaitingTime(wfNode.getWaitingTime());
		orderNode.setWorkingTime(wfNode.getWorkingTime());
		orderNode.setQueuingTime(wfNode.getQueuingTime());
		orderNode.setXPosition(wfNode.getXPosition());
		orderNode.setYPosition(wfNode.getYPosition());
		orderNode.setDocAction(wfNode.getDocAction());
		orderNode.setAD_Column_ID(wfNode.getAD_Column_ID());
		orderNode.setAD_Form_ID(wfNode.getAD_Form_ID());
		orderNode.setAD_Image_ID(wfNode.getAD_Image_ID());
		orderNode.setAD_Window_ID(wfNode.getAD_Window_ID());
		orderNode.setAD_Process_ID(wfNode.getAD_Process_ID());
		orderNode.setAttributeName(wfNode.getAttributeName());
		orderNode.setAttributeValue(wfNode.getAttributeValue());
		orderNode.setC_BPartner_ID(wfNode.getC_BPartner_ID());
		orderNode.setStartMode(wfNode.getStartMode());
		orderNode.setFinishMode(wfNode.getFinishMode());
		orderNode.setValidFrom(wfNode.getValidFrom());
		orderNode.setValidTo(wfNode.getValidTo());
		//
		setQtyOrdered(orderNode, order.getQtyOrdered());
		orderNode.setDocStatus(MPPOrderNode.DOCSTATUS_Drafted);

		InterfaceWrapperHelper.save(orderNode);
		return orderNode;
	}

	@Override
	public void setQtyOrdered(final I_PP_Order_Node orderNode, BigDecimal qtyOrdered)
	{
		orderNode.setQtyRequiered(qtyOrdered);
		RoutingService routingService = RoutingServiceFactory.get().getRoutingService(orderNode.getAD_Client_ID());
		BigDecimal workingTime = routingService.estimateWorkingTime(orderNode, qtyOrdered);
		orderNode.setDurationRequiered(workingTime.intValueExact());
	}

	/**
	 * Creates {@link I_PP_Order_NodeNext}.
	 * 
	 * NOTE: does not save
	 * 
	 * @param orderNode
	 * @param wfNodeNext
	 * @return
	 */
	private I_PP_Order_NodeNext createOrderNodeNext(I_PP_Order_Node orderNode, I_AD_WF_NodeNext wfNodeNext)
	{
		final I_PP_Order_NodeNext orderNodeNext = InterfaceWrapperHelper.newInstance(I_PP_Order_NodeNext.class, orderNode);
		orderNodeNext.setAD_Org_ID(orderNode.getAD_Org_ID());
		orderNodeNext.setPP_Order_ID(orderNode.getPP_Order_ID());

		orderNodeNext.setPP_Order_Node(orderNode);
		orderNodeNext.setAD_WF_Node_ID(wfNodeNext.getAD_WF_Node_ID());

		orderNodeNext.setAD_WF_Next_ID(wfNodeNext.getAD_WF_Next_ID());
		// orderNodeNext.setPP_Order_Next(null); // to be set later

		orderNodeNext.setDescription(wfNodeNext.getDescription());
		orderNodeNext.setEntityType(wfNodeNext.getEntityType());
		orderNodeNext.setIsStdUserWorkflow(wfNodeNext.isStdUserWorkflow());
		orderNodeNext.setSeqNo(wfNodeNext.getSeqNo());
		orderNodeNext.setTransitionCode(wfNodeNext.getTransitionCode());

		// InterfaceWrapperHelper.save(orderNodeNext); // don't save, we are not ready
		return orderNodeNext;
	}

	private I_PP_Order_Node_Product createOrderNodeProduct(I_PP_Order_Node PP_Order_Node, I_PP_WF_Node_Product nodeProduct)
	{
		final I_PP_Order_Node_Product orderNodeProduct = InterfaceWrapperHelper.newInstance(I_PP_Order_Node_Product.class, PP_Order_Node);
		orderNodeProduct.setAD_Org_ID(PP_Order_Node.getAD_Org_ID());
		orderNodeProduct.setSeqNo(nodeProduct.getSeqNo());
		orderNodeProduct.setIsActive(nodeProduct.isActive());
		orderNodeProduct.setM_Product_ID(nodeProduct.getM_Product_ID());
		orderNodeProduct.setQty(nodeProduct.getQty());
		orderNodeProduct.setIsSubcontracting(nodeProduct.isSubcontracting());
		// setYield(np.getYield());
		//
		orderNodeProduct.setPP_Order_ID(PP_Order_Node.getPP_Order_ID());
		orderNodeProduct.setPP_Order_Workflow_ID(PP_Order_Node.getPP_Order_Workflow_ID());
		orderNodeProduct.setPP_Order_Node_ID(PP_Order_Node.getPP_Order_Node_ID());

		InterfaceWrapperHelper.save(orderNodeProduct);
		return orderNodeProduct;
	}

	private I_PP_Order_Node_Asset createOrderNodeAsset(I_PP_Order_Node PP_Order_Node, I_PP_WF_Node_Asset na)
	{
		final I_PP_Order_Node_Asset orderNodeAsset = InterfaceWrapperHelper.newInstance(I_PP_Order_Node_Asset.class, PP_Order_Node);
		orderNodeAsset.setAD_Org_ID(PP_Order_Node.getAD_Org_ID());
		// setSeqNo(na.getSeqNo());
		orderNodeAsset.setA_Asset_ID(na.getA_Asset_ID());
		//
		orderNodeAsset.setPP_Order_ID(PP_Order_Node.getPP_Order_ID());
		orderNodeAsset.setPP_Order_Workflow_ID(PP_Order_Node.getPP_Order_Workflow_ID());
		orderNodeAsset.setPP_Order_Node_ID(PP_Order_Node.getPP_Order_Node_ID());

		InterfaceWrapperHelper.save(orderNodeAsset);
		return orderNodeAsset;
	}

}
