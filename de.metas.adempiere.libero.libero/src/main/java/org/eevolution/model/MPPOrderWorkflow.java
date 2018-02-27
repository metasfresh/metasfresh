/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.eevolution.model;

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
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderWorkflowDAO;

import de.metas.i18n.IMsgBL;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.pporder.LiberoException;

/**
 *	PP Order WorkFlow Model
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: MPPOrderWorkflow.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 * 
 *  @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPOrderWorkflow extends X_PP_Order_Workflow
{
	private static final long serialVersionUID = 1L;

	/**
	 * 	Get Workflow from Cache
	 *	@param ctx context
	 *	@param AD_Workflow_ID id
	 *	@return workflow
	 */
	public static MPPOrderWorkflow get (Properties ctx, int PP_Order_Workflow_ID)
	{
		if (PP_Order_Workflow_ID <= 0)
			return null;
		MPPOrderWorkflow retValue = s_cache.get(PP_Order_Workflow_ID);
		if (retValue != null)
			return retValue;
		retValue = new MPPOrderWorkflow (ctx, PP_Order_Workflow_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(PP_Order_Workflow_ID, retValue);
		return retValue;
	}
	
	/**	Single Cache					*/
	private static CCache<Integer,MPPOrderWorkflow>	s_cache = new CCache<Integer,MPPOrderWorkflow>(Table_Name, 20);

	/**************************************************************************
	 * 	Create/Load Workflow
	 * 	@param ctx Context
	 * 	@param PP_Order_Workflow_ID ID
	 * 	@param trxName transaction
	 */
	public MPPOrderWorkflow (Properties ctx, int PP_Order_Workflow_ID, String trxName)
	{
		super (ctx, PP_Order_Workflow_ID, trxName);
		if (PP_Order_Workflow_ID == 0)
		{
			//	setPP_Order_Workflow_ID (0);
			//	setValue (null);
			//	setName (null);
			setAccessLevel (ACCESSLEVEL_Organization);
			setAuthor (MClient.get(ctx).getName());
			setDurationUnit(DURATIONUNIT_Day);
			setDuration (1);
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setIsDefault (false);
			setPublishStatus (PUBLISHSTATUS_UnderRevision);	// U
			setVersion (0);
			setCost (Env.ZERO);
			setWaitingTime (0);
			setWorkingTime (0);
		}
	}	//	MPPOrderWorkflow

	/**
	 * 	Load Constructor
	 * 	@param ctx context
	 * 	@param rs result set
	 * 	@param trxName transaction
	 */
	public MPPOrderWorkflow (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	Workflow
	
//	/**
//	 * Peer constructor
//	 * @param workflow
//	 * @param PP_Order_ID
//	 * @param trxName
//	 */
//	public MPPOrderWorkflow (MWorkflow workflow, int PP_Order_ID, String trxName)
//	{
//		this(workflow.getCtx(), 0, trxName);
//		setPP_Order_ID(PP_Order_ID);
//		//
//		setValue(workflow.getValue());
//		setWorkflowType(workflow.getWorkflowType());
//		setQtyBatchSize(workflow.getQtyBatchSize());
//		setName(workflow.getName());
//		setAccessLevel(workflow.getAccessLevel());
//		setAuthor(workflow.getAuthor());
//		setDurationUnit(workflow.getDurationUnit());
//		setDuration(workflow.getDuration());
//		setEntityType(workflow.getEntityType());
//		setIsDefault(workflow.isDefault());
//		setPublishStatus(workflow.getPublishStatus());
//		setVersion(workflow.getVersion());
//		setCost(workflow.getCost());
//		setWaitingTime(workflow.getWaitingTime());
//		setWorkingTime(workflow.getWorkingTime());
//		setAD_WF_Responsible_ID(workflow.getAD_WF_Responsible_ID());
//		setAD_Workflow_ID(workflow.getAD_Workflow_ID());
//		setDurationLimit(workflow.getDurationLimit());
//		setPriority(workflow.getPriority());
////		setValidateWorkflow(workflow.getValidateWorkflow()); // buttons do not need to copy
//		setS_Resource_ID(workflow.getS_Resource_ID());
//		setQueuingTime(workflow.getQueuingTime());
//		setSetupTime(workflow.getSetupTime());
//		setMovingTime(workflow.getMovingTime());
//		setProcessType(workflow.getProcessType());
//		setAD_Table_ID(workflow.getAD_Table_ID());
//		setAD_WF_Node_ID(workflow.getAD_WF_Node_ID());
//		setAD_WorkflowProcessor_ID(workflow.getAD_WorkflowProcessor_ID());
//		setDescription(workflow.getDescription());
//		setValidFrom(workflow.getValidFrom());
//		setValidTo(workflow.getValidTo());
//	}

	/**	WF Nodes				*/
	private List<MPPOrderNode> m_nodes = null;
	/** Manufacturing Order 	*/
	private MPPOrder m_order = null;

	/**
	 * Get All Nodes
	 * @param requery 
	 */
	protected List<MPPOrderNode> getNodes(boolean requery)
	{
		if (m_nodes == null || requery)
		{
			final List<I_PP_Order_Node> nodes = Services.get(IPPOrderWorkflowDAO.class).retrieveNodes(this);
			m_nodes = LegacyAdapters.convertToPOList(nodes);
			log.debug("#" + m_nodes.size());
		}
		return m_nodes;
	}
	
	/**
	 * Get All Nodes (do not requery if already loaded)
	 */
	protected List<MPPOrderNode> getNodes()
	{
		return getNodes(false);
	}

	/**
	 * 	Get Number of Nodes
	 * 	@return number of nodes
	 */
	public int getNodeCount()
	{
		return getNodes().size();
	}	//	getNextNodeCount

	/**
	 * 	Get the nodes
	 *  @param ordered ordered array
	 * 	@param AD_Client_ID for client
	 * 	@return array of nodes
	 */
	public MPPOrderNode[] getNodes(boolean ordered, int AD_Client_ID)
	{
		if (ordered)
		{
			return getNodesInOrder(AD_Client_ID);
		}
		//
		ArrayList<MPPOrderNode> list = new ArrayList<MPPOrderNode>();
		for (MPPOrderNode node : getNodes())
		{
			if (node.getAD_Client_ID() == 0 || node.getAD_Client_ID() == AD_Client_ID)
			{
				list.add(node);
			}
		}
		return list.toArray(new MPPOrderNode [list.size()]);
	}	//	getNodes

	/**
	 * 	Get the first node
	 * 	@return array of next nodes
	 */
	public MPPOrderNode getFirstNode()
	{
		return getNode (getPP_Order_Node_ID());
	}	//	getFirstNode

	/**
	 * Get Node with given ID and valid for given Tenant
	 * @param PP_Order_Node_ID ID
	 * @param AD_Client_ID if >= 0 the node is checked if is valid for this tenant
	 * @return valid node or null
	 */
	private MPPOrderNode getNode (int PP_Order_Node_ID, int AD_Client_ID)
	{
		if (PP_Order_Node_ID <= 0)
		{
			return null;
		}
		for (MPPOrderNode node : getNodes())
		{
			if (node.getPP_Order_Node_ID() == PP_Order_Node_ID)
			{
				// Tenant check:
				if (AD_Client_ID >= 0)
				{
					if (node.getAD_Client_ID() == 0 || node.getAD_Client_ID() == AD_Client_ID)
					{
						// node found and tenant valid
						return node;
					}
					else
					{
						// node found but tenant not valid
						return null;
					}
				}
				else
				{
					// node found and no tenant validation needed
					return node;
				}
			}
		}
		return null;
	}	//	getNode

	/**
	 * Get Node with given ID
	 * @param PP_Order_Node_ID ID
	 * @return node or null
	 */
	public MPPOrderNode getNode (int PP_Order_Node_ID)
	{
		return getNode(PP_Order_Node_ID, -1);
	}
	/**
	 * 	Get the next nodes
	 * 	@param PP_Order_Node_ID ID
	 * 	@param AD_Client_ID for client
	 * 	@return array of next nodes or null
	 */
	public MPPOrderNode[] getNextNodes (int PP_Order_Node_ID, int AD_Client_ID)
	{
		MPPOrderNode node = getNode(PP_Order_Node_ID);
		if (node == null || node.getNextNodeCount() == 0)
		{
			return null;
		}
		//
		ArrayList<MPPOrderNode> list = new ArrayList<MPPOrderNode>();
		for (MPPOrderNodeNext nextTr : node.getTransitions(AD_Client_ID))
		{
			MPPOrderNode next = getNode (nextTr.getPP_Order_Next_ID(), AD_Client_ID);
			if (next != null)
			{
				list.add(next);
			}
		}

		//	Return Nodes
		return list.toArray(new MPPOrderNode [list.size()]);
	}	//	getNextNodes

	/**
	 * 	Get The Nodes in Sequence Order
	 * 	@param AD_Client_ID client
	 * 	@return Nodes in sequence
	 */
	private MPPOrderNode[] getNodesInOrder(int AD_Client_ID)
	{
		ArrayList<MPPOrderNode> list = new ArrayList<MPPOrderNode>();
		addNodesSF (list, getPP_Order_Node_ID(), AD_Client_ID);	//	start with first
		//	Remaining Nodes
		if (getNodeCount() != list.size())
		{
			//	Add Stand alone
			for (MPPOrderNode node : getNodes())
			{
				if (node.getAD_Client_ID() == 0 || node.getAD_Client_ID() == AD_Client_ID)
				{
					boolean found = false;
					for (MPPOrderNode existing : list)
					{
						if (existing.getPP_Order_Node_ID() == node.getPP_Order_Node_ID())
						{
							found = true;
							break;
						}
					}
					if (!found)
					{
						log.warn("Added Node w/o transition: " + node);
						list.add(node);
					}
				}
			}
		}
		//
		MPPOrderNode[] nodeArray = new MPPOrderNode [list.size()];
		list.toArray(nodeArray);
		return nodeArray;
	}	//	getNodesInOrder

	/**
	 * 	Add Nodes recursively (sibling first) to Ordered List
	 *  @param list list to add to
	 * 	@param PP_Order_Node_ID start node id
	 * 	@param AD_Client_ID for client
	 */
	private void addNodesSF (Collection<MPPOrderNode> list, int PP_Order_Node_ID, int AD_Client_ID)
	{
		final MPPOrderNode node = getNode (PP_Order_Node_ID, AD_Client_ID);
		if (node != null)
		{
			if (!list.contains(node))
			{
				list.add(node);
			}
			final List<Integer> nextNodes = new ArrayList<Integer>();
			for (final I_PP_Order_NodeNext next : node.getTransitions(AD_Client_ID))
			{
				final MPPOrderNode child = getNode (next.getPP_Order_Next_ID(), AD_Client_ID);
				if (child != null)
				{
					if (!list.contains(child))
					{
						list.add(child);
						nextNodes.add(next.getPP_Order_Next_ID());
					}
					else
					{
						final LiberoException ex = new LiberoException("Cyclic transition found - " + node + " -> " + child);
						log.warn(ex.getLocalizedMessage(), ex);
					}
				}
			}
			//	Remainder Nodes not connected, not already added
			for (int pp_Order_Next_ID : nextNodes)
			{
				addNodesSF (list, pp_Order_Next_ID, AD_Client_ID);
			}
		}
	}	//	addNodesSF

	/**************************************************************************
	 * 	Get first transition (Next Node) of ID
	 * 	@param PP_Order_Node_ID id
	 * 	@param AD_Client_ID for client
	 * 	@return next PP_Order_Node_ID or 0
	 */
	public int getNext (int PP_Order_Node_ID, int AD_Client_ID)
	{
		MPPOrderNode[] nodes = getNodesInOrder(AD_Client_ID);
		for (int i = 0; i < nodes.length; i++)
		{
			if (nodes[i].getPP_Order_Node_ID() == PP_Order_Node_ID)
			{
				MPPOrderNodeNext[] nexts = nodes[i].getTransitions(AD_Client_ID);
				if (nexts.length > 0)
				{
					return nexts[0].getPP_Order_Next_ID();
				}
				return 0;
			}
		}
		return 0;
	}	//	getNext

	/**
	 * 	Get Transitions (NodeNext) of ID
	 * 	@param PP_Order_Node_ID id
	 * 	@param AD_Client_ID for client
	 * 	@return array of next nodes
	 */
	public MPPOrderNodeNext[] getNodeNexts (int PP_Order_Node_ID, int AD_Client_ID)
	{
		MPPOrderNode[] nodes = getNodesInOrder(AD_Client_ID);
		for (int i = 0; i < nodes.length; i++)
		{
			if (nodes[i].getPP_Order_Node_ID() == PP_Order_Node_ID)
			{
				return nodes[i].getTransitions(AD_Client_ID);
			}
		}
		return new MPPOrderNodeNext[]{};
	}	//	getNext

	/**
	 * 	Get (first) Previous Node of ID
	 * 	@param PP_Order_Node_ID id
	 * 	@param AD_Client_ID for client
	 * 	@return next PP_Order_Node_ID or 0
	 */
	public int getPrevious (int PP_Order_Node_ID, int AD_Client_ID)
	{
		MPPOrderNode[] nodes = getNodesInOrder(AD_Client_ID);
		for (int i = 0; i < nodes.length; i++)
		{
			if (nodes[i].getPP_Order_Node_ID() == PP_Order_Node_ID)
			{
				if (i > 0)
					return nodes[i-1].getPP_Order_Node_ID();
				return 0;
			}
		}
		return 0;
	}	//	getPrevious

	/**
	 * 	Get very Last Node
	 * 	@param AD_Client_ID for client
	 * 	@return next PP_Order_Node_ID or 0
	 */
	public int getNodeLastID (int AD_Client_ID)
	{
		MPPOrderNode[] nodes = getNodesInOrder(AD_Client_ID);
		if (nodes.length > 0)
		{
			return nodes[nodes.length-1].getPP_Order_Node_ID();
		}
		return 0;
	}	//	getLast
	
	/**
	 * 	Get very Last Node
	 * 	@param AD_Client_ID for client
	 * 	@return next PP_Order_Node_ID or 0
	 */
	public MPPOrderNode getLastNode (int AD_Client_ID)
	{
		MPPOrderNode[] nodes = getNodesInOrder(AD_Client_ID);
		if (nodes.length > 0)
		{
			return nodes[nodes.length-1];
		}
		return null;
	}	//	getLast

	/**
	 * 	Is this the first Node
	 * 	@param PP_Order_Node_ID id
	 * 	@param AD_Client_ID for client
	 * 	@return true if first node
	 */
	public boolean isFirst (int PP_Order_Node_ID, int AD_Client_ID)
	{
		return PP_Order_Node_ID == getPP_Order_Node_ID();
	}	//	isFirst

	/**
	 * 	Is this the last Node
	 * 	@param PP_Order_Node_ID id
	 * 	@param AD_Client_ID for client
	 * 	@return true if last node
	 */
	public boolean isLast (int PP_Order_Node_ID, int AD_Client_ID)
	{
		MPPOrderNode[] nodes = getNodesInOrder(AD_Client_ID);
		return PP_Order_Node_ID == nodes[nodes.length-1].getPP_Order_Node_ID();
	}	//	isLast

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MPPOrderWorkflow[");
		sb.append(get_ID()).append("-").append(getName())
		.append ("]");
		return sb.toString ();
	} //	toString

	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		log.debug("Success=" + success);
		if (success && newRecord)
		{
			//	save all nodes -- Creating new Workflow
			MPPOrderNode[] nodes = getNodesInOrder(0);
			for (int i = 0; i < nodes.length; i++)
				nodes[i].saveEx(get_TrxName());
		}

		return success;
	}   //  afterSave

	/**
	 * 	Get Duration Base in Seconds
	 *	@return duration unit in seconds
	 */
	public long getDurationBaseSec ()
	{
		if (getDurationUnit() == null)
			return 0;
		else if (DURATIONUNIT_Second.equals(getDurationUnit()))
			return 1;
		else if (DURATIONUNIT_Minute.equals(getDurationUnit()))
			return 60;
		else if (DURATIONUNIT_Hour.equals(getDurationUnit()))
			return 3600;
		else if (DURATIONUNIT_Day.equals(getDurationUnit()))
			return 86400;
		else if (DURATIONUNIT_Month.equals(getDurationUnit()))
			return 2592000;
		else if (DURATIONUNIT_Year.equals(getDurationUnit()))
			return 31536000;
		return 0;
	}	//	getDurationBaseSec

	/**
	 * 	Get Duration CalendarField
	 *	@return Calendar.MINUTE, etc.
	 */
	public int getDurationCalendarField()
	{
		if (getDurationUnit() == null)
			return Calendar.MINUTE;
		else if (DURATIONUNIT_Second.equals(getDurationUnit()))
			return Calendar.SECOND;
		else if (DURATIONUNIT_Minute.equals(getDurationUnit()))
			return Calendar.MINUTE;
		else if (DURATIONUNIT_Hour.equals(getDurationUnit()))
			return Calendar.HOUR;
		else if (DURATIONUNIT_Day.equals(getDurationUnit()))
			return Calendar.DAY_OF_YEAR;
		else if (DURATIONUNIT_Month.equals(getDurationUnit()))
			return Calendar.MONTH;
		else if (DURATIONUNIT_Year.equals(getDurationUnit()))
			return Calendar.YEAR;
		return Calendar.MINUTE;
	}	//	getDurationCalendarField
	

	/**
	 * Close Activities
	 * @param activity current activity / reporting activity
	 * @param movementDate
	 * @param milestone stop on previous milestone
	 */
	public void closeActivities(MPPOrderNode activity, Timestamp movementDate, boolean milestone)
	{
		if (activity == null)
		{
			final LiberoException ex = new LiberoException("No activity provided for "+getPP_Order()+". Skip closing activities.");
			log.warn(ex.getLocalizedMessage(), ex);
			return;
		}
		
		if (activity.getPP_Order_Workflow_ID() != get_ID())
		{
			throw new LiberoException("Activity and Order Workflow not matching"
										+" ("+activity+", PP_Order_Workflow_ID="+get_ID()+")");		
		}
		MPPOrder order = getMPPOrder();
		
		for (int nodeId = activity.get_ID(); nodeId != 0; nodeId = getPrevious(nodeId, getAD_Client_ID()))
		{
			MPPOrderNode node = getNode(nodeId);
			// Break the cycle 
			if(milestone && node.isMilestone() && node.get_ID() != activity.get_ID())
			{
				break;
			}
			if(MPPOrderNode.DOCSTATUS_Drafted.equals(node.getDocStatus()))
			{
				BigDecimal qtyToDeliver = node.getQtyToDeliver();
				if (qtyToDeliver.signum() <= 0)
				{
					// TODO: should we create a negate CC?
					continue;
				}
				int setupTimeReal = node.getSetupTimeRequiered() - node.getSetupTimeReal();
				RoutingService routingService = RoutingServiceFactory.get().getRoutingService(node.getAD_Client_ID());
				BigDecimal durationReal = routingService.estimateWorkingTime(node, qtyToDeliver);
				// arhipac: cristi_pup: Generate even if nothing was reporting on this activity
				// https://sourceforge.net/projects/adempiere/forums/forum/639403/topic/3407220
//				if (setupTimeReal <= 0 && durationReal.signum() <= 0)
//				{
//					continue;
//				}
				MPPCostCollector.createCollector (
						order, 															//MPPOrder
						order.getM_Product_ID(),										//M_Product_ID
						order.getM_Locator_ID(),										//M_Locator_ID
						order.getM_AttributeSetInstance_ID(),							//M_AttributeSetInstance_ID
						node.getS_Resource_ID(),										//S_Resource_ID
						0,																//PP_Order_BOMLine_ID
						node.get_ID(),													//PP_Order_Node_ID
						MDocType.getDocType(MDocType.DOCBASETYPE_ManufacturingCostCollector), 	//C_DocType_ID,
						MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl, 			//Activity Control
						movementDate,													//MovementDate
						qtyToDeliver, Env.ZERO, Env.ZERO,								//qty,scrap,reject
						setupTimeReal, durationReal);									//durationSetup,duration
				node.load(order.get_TrxName()); // reload because it was modified outside of our context
				node.closeIt();
				node.saveEx();
			}
			else if(MPPOrderNode.DOCSTATUS_Completed.equals(node.getDocStatus()) ||
					MPPOrderNode.DOCSTATUS_InProgress.equals(node.getDocStatus()))
			{
				node.closeIt();
				node.saveEx();
			}
		} // for each node
		m_nodes = null; // reset nodes
	}
	
	/**
	 * void Activities
	 */
	public void voidActivities()
	{
		for(MPPOrderNode node : this.getNodes(true, getAD_Client_ID()))
		{
			BigDecimal old = node.getQtyRequiered();
			if (old.signum() != 0)
			{
				node.addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided") + " (" + old + ")");
				node.voidIt();
				node.saveEx();
			}
		}
	}	
	
	/**
	 * 	Get Parent
	 *	@return MPPOrder
	 */
	public MPPOrder getMPPOrder()
	{
		// TODO: improve caching support
		if (m_order == null)
		{
			m_order = new MPPOrder(getCtx(), getPP_Order_ID(), get_TrxName());
		}

		//
		// 07619: Forcefully always set the TrxName with the workflow's current one
		InterfaceWrapperHelper.setTrxName(m_order, get_TrxName());

		return m_order;
	}
	
	
}	//	MPPOrderWorkflow_ID
