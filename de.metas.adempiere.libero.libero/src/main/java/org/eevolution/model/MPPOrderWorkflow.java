/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderWorkflowDAO;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.pporder.LiberoException;

/**
 * PP Order WorkFlow Model
 *
 * @author Jorg Janke
 * @version $Id: MPPOrderWorkflow.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 *
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPOrderWorkflow extends X_PP_Order_Workflow
{
	private static final long serialVersionUID = 1L;

	/** WF Nodes */
	private List<MPPOrderNode> _nodes = null;

	public MPPOrderWorkflow(final Properties ctx, final int PP_Order_Workflow_ID, final String trxName)
	{
		super(ctx, PP_Order_Workflow_ID, trxName);
		if (is_new())
		{
			// setPP_Order_Workflow_ID (0);
			// setValue (null);
			// setName (null);
			setAccessLevel(ACCESSLEVEL_Organization);
			setAuthor(".");
			setDurationUnit(DURATIONUNIT_Day);
			setDuration(1);
			setEntityType(ENTITYTYPE_UserMaintained);	// U
			setIsDefault(false);
			setPublishStatus(PUBLISHSTATUS_UnderRevision);	// U
			setVersion(0);
			setCost(BigDecimal.ZERO);
			setWaitingTime(0);
			setWorkingTime(0);
		}
	}	// MPPOrderWorkflow

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MPPOrderWorkflow(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// Workflow

	/**
	 * Get All Nodes
	 *
	 * @param requery
	 */
	protected List<MPPOrderNode> getNodes(final boolean requery)
	{
		List<MPPOrderNode> nodes = _nodes;
		if (nodes == null || requery)
		{
			final List<I_PP_Order_Node> nodesList = Services.get(IPPOrderWorkflowDAO.class).retrieveNodes(this);
			_nodes = nodes = LegacyAdapters.convertToPOList(nodesList);
		}
		return nodes;
	}

	/**
	 * Get All Nodes (do not requery if already loaded)
	 */
	protected List<MPPOrderNode> getNodes()
	{
		return getNodes(false);
	}

	/**
	 * Get the first node
	 *
	 * @return array of next nodes
	 */
	public MPPOrderNode getFirstNode()
	{
		return getNodeById(getPP_Order_Node_ID());
	}

	/**
	 * Get Node with given ID and valid for given Tenant
	 *
	 * @param ppOrderNodeId ID
	 * @param adClientId if >= 0 the node is checked if is valid for this tenant
	 * @return valid node or null
	 */
	private MPPOrderNode getNode(final int ppOrderNodeId, final int adClientId)
	{
		final MPPOrderNode node = getNodeById(ppOrderNodeId);
		if (node == null)
		{
			return null;
		}

		// Tenant check:
		if (adClientId >= 0)
		{
			if (node.getAD_Client_ID() == 0 || node.getAD_Client_ID() == adClientId)
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
	}	// getNode

	/**
	 * Get Node with given ID
	 *
	 * @param ppOrderNodeId ID
	 * @return node or null
	 */
	public MPPOrderNode getNodeById(final int ppOrderNodeId)
	{
		if (ppOrderNodeId <= 0)
		{
			return null;
		}

		return getNodes()
				.stream()
				.filter(node -> node.getPP_Order_Node_ID() == ppOrderNodeId)
				.findFirst()
				.orElse(null);
	}

	/**
	 * Get The Nodes in Sequence Order
	 *
	 * @param adClientId client
	 * @return Nodes in sequence
	 */
	private List<MPPOrderNode> getNodesInOrder(final int adClientId)
	{
		final ArrayList<MPPOrderNode> nodesSF = new ArrayList<>();
		addNodesSF(nodesSF, getPP_Order_Node_ID(), adClientId);	// start with first

		// Remaining Nodes
		final List<MPPOrderNode> allNodes = getNodes();
		if (allNodes.size() != nodesSF.size())
		{
			final List<MPPOrderNode> missingNodes = allNodes.stream()
					.filter(node -> node.getAD_Client_ID() == 0 || node.getAD_Client_ID() == adClientId)
					.filter(node -> !nodesSF
							.stream()
							.anyMatch(existing -> existing.getPP_Order_Node_ID() == node.getPP_Order_Node_ID()))
					.collect(ImmutableList.toImmutableList());
			if (!missingNodes.isEmpty())
			{
				log.warn("Added Nodes w/o transition: {}", missingNodes);
				nodesSF.addAll(missingNodes);
			}
		}

		//
		return nodesSF;
	}

	/**
	 * Add Nodes recursively (sibling first) to Ordered List
	 *
	 * @param list list to add to
	 * @param ppOrderNodeId start node id
	 * @param adClientId for client
	 */
	private void addNodesSF(final Collection<MPPOrderNode> list, final int ppOrderNodeId, final int adClientId)
	{
		final MPPOrderNode node = getNode(ppOrderNodeId, adClientId);
		if (node != null)
		{
			if (!list.contains(node))
			{
				list.add(node);
			}
			final List<Integer> nextNodes = new ArrayList<>();
			for (final I_PP_Order_NodeNext next : node.getTransitions(adClientId))
			{
				final MPPOrderNode child = getNode(next.getPP_Order_Next_ID(), adClientId);
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
			// Remainder Nodes not connected, not already added
			for (final int pp_Order_Next_ID : nextNodes)
			{
				addNodesSF(list, pp_Order_Next_ID, adClientId);
			}
		}
	}	// addNodesSF

	/**************************************************************************
	 * Get first transition (Next Node) of ID
	 *
	 * @param ppOrderNodeId id
	 * @param adClientId for client
	 * @return next PP_Order_Node_ID or 0
	 */
	public int getNext(final int ppOrderNodeId, final int adClientId)
	{
		return getNodesInOrder(adClientId)
				.stream()
				.flatMap(node -> node.getTransitions(adClientId).stream())
				.findFirst()
				.map(nodeNext -> nodeNext.getPP_Order_Next_ID())
				.orElse(0);
	}

	/**
	 * Get (first) Previous Node of ID
	 *
	 * @param PP_Order_Node_ID id
	 * @param adClientId for client
	 * @return next PP_Order_Node_ID or 0
	 */
	public int getPrevious(final int PP_Order_Node_ID, final int adClientId)
	{
		final List<MPPOrderNode> nodes = getNodesInOrder(adClientId);
		for (int i = 0, size = nodes.size(); i < size; i++)
		{
			if (nodes.get(i).getPP_Order_Node_ID() == PP_Order_Node_ID)
			{
				if (i > 0)
				{
					return nodes.get(i - 1).getPP_Order_Node_ID();
				}
				return 0;
			}
		}
		return 0;
	}	// getPrevious

	/**
	 * Get very Last Node
	 *
	 * @param adClientId for client
	 * @return next PP_Order_Node_ID or 0
	 */
	public int getNodeLastID(final int adClientId)
	{
		final List<MPPOrderNode> nodes = getNodesInOrder(adClientId);
		if (!nodes.isEmpty())
		{
			return nodes.get(nodes.size() - 1).getPP_Order_Node_ID();
		}
		return 0;
	}	// getLast

	/**
	 * Get very Last Node
	 *
	 * @param adClientId for client
	 * @return next PP_Order_Node_ID or 0
	 */
	public MPPOrderNode getLastNode(final int adClientId)
	{
		final List<MPPOrderNode> nodes = getNodesInOrder(adClientId);
		if (!nodes.isEmpty())
		{
			return nodes.get(nodes.size() - 1);
		}
		return null;
	}	// getLast

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (success && newRecord)
		{
			// save all nodes -- Creating new Workflow
			getNodesInOrder(0).forEach(node -> node.saveEx(get_TrxName()));
		}

		return success;
	}   // afterSave

	/**
	 * Get Duration Base in Seconds
	 *
	 * @return duration unit in seconds
	 */
	public long getDurationBaseSec()
	{
		if (getDurationUnit() == null)
		{
			return 0;
		}
		else if (DURATIONUNIT_Second.equals(getDurationUnit()))
		{
			return 1;
		}
		else if (DURATIONUNIT_Minute.equals(getDurationUnit()))
		{
			return 60;
		}
		else if (DURATIONUNIT_Hour.equals(getDurationUnit()))
		{
			return 3600;
		}
		else if (DURATIONUNIT_Day.equals(getDurationUnit()))
		{
			return 86400;
		}
		else if (DURATIONUNIT_Month.equals(getDurationUnit()))
		{
			return 2592000;
		}
		else if (DURATIONUNIT_Year.equals(getDurationUnit()))
		{
			return 31536000;
		}
		return 0;
	}	// getDurationBaseSec

	/**
	 * Get Duration CalendarField
	 *
	 * @return Calendar.MINUTE, etc.
	 */
	public int getDurationCalendarField()
	{
		if (getDurationUnit() == null)
		{
			return Calendar.MINUTE;
		}
		else if (DURATIONUNIT_Second.equals(getDurationUnit()))
		{
			return Calendar.SECOND;
		}
		else if (DURATIONUNIT_Minute.equals(getDurationUnit()))
		{
			return Calendar.MINUTE;
		}
		else if (DURATIONUNIT_Hour.equals(getDurationUnit()))
		{
			return Calendar.HOUR;
		}
		else if (DURATIONUNIT_Day.equals(getDurationUnit()))
		{
			return Calendar.DAY_OF_YEAR;
		}
		else if (DURATIONUNIT_Month.equals(getDurationUnit()))
		{
			return Calendar.MONTH;
		}
		else if (DURATIONUNIT_Year.equals(getDurationUnit()))
		{
			return Calendar.YEAR;
		}
		return Calendar.MINUTE;
	}	// getDurationCalendarField

	/**
	 * Close Activities
	 *
	 * @param activity current activity / reporting activity
	 * @param movementDate
	 * @param milestone stop on previous milestone
	 */
	public void closeActivities(final MPPOrderNode activity, final Timestamp movementDate, final boolean milestone)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		if (activity == null)
		{
			final LiberoException ex = new LiberoException("No activity provided for " + getPP_Order() + ". Skip closing activities.");
			log.warn("", ex);
			return;
		}

		if (activity.getPP_Order_Workflow_ID() != get_ID())
		{
			throw new LiberoException("Activity and Order Workflow not matching"
					+ " (" + activity + ", PP_Order_Workflow_ID=" + get_ID() + ")");
		}

		for (int nodeId = activity.get_ID(); nodeId > 0; nodeId = getPrevious(nodeId, getAD_Client_ID()))
		{
			final MPPOrderNode node = getNodeById(nodeId);
			// Break the cycle
			if (milestone && node.isMilestone() && node.get_ID() != activity.get_ID())
			{
				break;
			}
			if (X_PP_Order_Node.DOCSTATUS_Drafted.equals(node.getDocStatus()))
			{
				final BigDecimal qtyToDeliver = node.getQtyToDeliver();
				if (qtyToDeliver.signum() <= 0)
				{
					// TODO: should we create a negate CC?
					continue;
				}
				final int setupTimeReal = node.getSetupTimeRequiered() - node.getSetupTimeReal();
				final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(node.getAD_Client_ID());
				final BigDecimal durationReal = routingService.estimateWorkingTime(node, qtyToDeliver);
				// arhipac: cristi_pup: Generate even if nothing was reporting on this activity
				// https://sourceforge.net/projects/adempiere/forums/forum/639403/topic/3407220
				// if (setupTimeReal <= 0 && durationReal.signum() <= 0)
				// {
				// continue;
				// }
				ppCostCollectorBL.createActivityControl(ActivityControlCreateRequest.builder()
						.node(node)
						.movementDate(movementDate)
						.qtyMoved(qtyToDeliver)
						.durationSetup(setupTimeReal)
						.duration(durationReal)
						.build());
				node.load(ITrx.TRXNAME_ThreadInherited); // reload because it was modified outside of our context
				node.closeIt();
				node.saveEx();
			}
			else if (X_PP_Order_Node.DOCSTATUS_Completed.equals(node.getDocStatus()) ||
					X_PP_Order_Node.DOCSTATUS_InProgress.equals(node.getDocStatus()))
			{
				node.closeIt();
				node.saveEx();
			}
		} // for each node
		_nodes = null; // reset nodes
	}

	/**
	 * void Activities
	 */
	public void voidActivities()
	{
		for (final MPPOrderNode node : getNodesInOrder(getAD_Client_ID()))
		{
			final BigDecimal old = node.getQtyRequiered();
			if (old.signum() != 0)
			{
				node.addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided") + " (" + old + ")");
				node.voidIt();
				node.saveEx();
			}
		}
	}
}
