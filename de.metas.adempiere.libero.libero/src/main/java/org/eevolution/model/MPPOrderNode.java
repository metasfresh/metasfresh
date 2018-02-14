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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.eevolution.api.IPPOrderNodeBL;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;

/**
 * PP Order Workflow Node Model
 * 
 * <p>
 * Usage notes:
 * <li>NEVER use {@link #setDocStatus(String)} method directly.
 * Use {@link #completeIt()}, {@link #closeIt()}, {@link #setInProgress()} instead.
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPOrderNode extends X_PP_Order_Node
{
	private static final long serialVersionUID = 1L;

	/** MPPOrderWorkflow */
	private MPPOrderWorkflow m_order_wf = null;
	/** Next Nodes */
	private List<I_PP_Order_NodeNext> _nextNodes = null; // lazy
	/** Duration Base MS */
	private long m_durationBaseMS = -1;

	public MPPOrderNode(Properties ctx, int PP_Order_Node_ID, String trxName)
	{
		super(ctx, PP_Order_Node_ID, trxName);
		if (is_new())
		{
			setDefault();
		}
	}

	public MPPOrderNode(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Load Next
	 */
	private List<I_PP_Order_NodeNext> getNodeNexts()
	{
		if (_nextNodes == null)
		{
			_nextNodes = retrieveNodeNexts();
		}
		return _nextNodes;
	}

	private List<I_PP_Order_NodeNext> retrieveNodeNexts()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_NodeNext.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_NodeNext.COLUMNNAME_PP_Order_Node_ID, getPP_Order_Node_ID())
				.orderBy(I_PP_Order_NodeNext.COLUMNNAME_SeqNo)
				.orderBy(I_PP_Order_NodeNext.COLUMNNAME_PP_Order_NodeNext_ID)
				.create()
				.listImmutable(I_PP_Order_NodeNext.class);
	}

	/**
	 * Get Qty To Deliver (Open Qty)
	 * 
	 * @return open qty
	 * @deprecated please use {@link IPPOrderNodeBL#getQtyToDeliver(I_PP_Order_Node)}
	 */
	@Deprecated
	public BigDecimal getQtyToDeliver()
	{
		return Services.get(IPPOrderNodeBL.class).getQtyToDeliver(this);
	}

	/**
	 * Get the transitions
	 * 
	 * @param adClientId for client
	 * @return next nodes
	 */
	public List<I_PP_Order_NodeNext> getTransitions(final int adClientId)
	{
		return getNodeNexts()
				.stream()
				.filter(next -> next.getAD_Client_ID() == 0 || next.getAD_Client_ID() == adClientId)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Get Duration in ms
	 * 
	 * @return duration in ms
	 */
	public long getDurationMS()
	{
		long duration = super.getDuration();
		if (duration == 0)
			return 0;
		if (m_durationBaseMS == -1)
			m_durationBaseMS = getMPPOrderWorkflow().getDurationBaseSec() * 1000;
		return duration * m_durationBaseMS;
	}	// getDurationMS

	/**
	 * Get Duration Limit in ms
	 * 
	 * @return duration limit in ms
	 */
	public long getLimitMS()
	{
		long limit = super.getDurationLimit();
		if (limit == 0)
			return 0;
		if (m_durationBaseMS == -1)
			m_durationBaseMS = getMPPOrderWorkflow().getDurationBaseSec() * 1000;
		return limit * m_durationBaseMS;
	}	// getLimitMS

	/**
	 * Get Duration CalendarField
	 * 
	 * @return Calendar.MINUTE, etc.
	 */
	public int getDurationCalendarField()
	{
		return getMPPOrderWorkflow().getDurationCalendarField();
	}	// getDurationCalendarField

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MPPOrderNode[");
		sb.append(get_ID())
				.append("-").append(getName())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Get Parent
	 * 
	 * @return MPPOrderWorkflow
	 */
	public MPPOrderWorkflow getMPPOrderWorkflow()
	{
		if (m_order_wf == null)
			m_order_wf = new MPPOrderWorkflow(getCtx(), getPP_Order_Workflow_ID(), get_TrxName());
		return m_order_wf;
	}	// getParent

	@Override
	public I_PP_Order_Workflow getPP_Order_Workflow()
	{
		return getMPPOrderWorkflow();
	}

	/**
	 * Complete Activity (i.e. mark the activity as completed)
	 */
	public void completeIt()
	{
		setDocStatus(MPPOrderNode.DOCSTATUS_Completed);
		setDocAction(MPPOrderNode.DOCACTION_None);
		setDateFinish(true);
	}

	/**
	 * Close the Activity
	 */
	public void closeIt()
	{
		setDocStatus(MPPOrderNode.DOCSTATUS_Closed);
		setDocAction(MPPOrderNode.DOCACTION_None);
		setDateFinish(false);
		int old = getDurationRequiered();
		if (old != getDurationReal())
		{
			addDescription(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@closed@ ( @Duration@ :" + old + ") ( @QtyRequiered@ :" + getQtyRequiered() + ")"));
			setDurationRequiered(getDurationReal());
			setQtyRequiered(getQtyDelivered());
		}
	}

	/**
	 * Void Activity
	 */
	public void voidIt()
	{
		String docStatus = getDocStatus();
		if (DOCSTATUS_Voided.equals(docStatus))
		{
			log.warn("Activity already voided - {}", this);
			return;
		}
		BigDecimal qtyRequired = getQtyRequiered();
		if (qtyRequired.signum() != 0)
		{
			addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided") + " (" + qtyRequired + ")");
		}
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		setQtyRequiered(BigDecimal.ZERO);
		setSetupTimeRequiered(0);
		setDurationRequiered(0);
	}

	/**
	 * Mark activity as InProgress (Started but not finished)
	 */
	public void setInProgress(MPPCostCollector currentActivity)
	{
		if (isProcessed())
		{
			throw new IllegalStateException("Cannot change status from " + getDocStatus() + " to " + DOCSTATUS_InProgress);
		}

		setDocStatus(DOCSTATUS_InProgress);
		setDocAction(DOCACTION_Complete);

		// Mark activity as started
		if (currentActivity != null && getDateStart() == null)
		{
			setDateStart(currentActivity.getDateStart());
		}
	}

	/**
	 * @return true if this activity was already processed (i.e. DocStatus=COmpleted/CLosed)
	 */
	public boolean isProcessed()
	{
		final String status = getDocStatus();
		return DOCSTATUS_Completed.equals(status) || DOCSTATUS_Closed.equals(status);
	}

	/**
	 * Add to Description
	 * 
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

	private void setDefault()
	{
		setAction(ACTION_WaitSleep);
		setCost(BigDecimal.ZERO);
		setDuration(0);
		setEntityType(ENTITYTYPE_UserMaintained);	// U
		setIsCentrallyMaintained(true);	// Y
		setJoinElement(JOINELEMENT_XOR);	// X
		setDurationLimit(0);
		setSplitElement(SPLITELEMENT_XOR);	// X
		setWaitingTime(0);
		setXPosition(0);
		setYPosition(0);
		setDocStatus(MPPOrderNode.DOCSTATUS_Drafted);
	}

	/**
	 * Set DateFinish as last MovementDate from PP_CostCollector
	 * 
	 * @param override Update DateFinish even if is already set
	 */
	private void setDateFinish(boolean override)
	{
		if (!DOCSTATUS_Completed.equals(getDocStatus()) && !DOCSTATUS_Closed.equals(getDocStatus()))
		{
			throw new IllegalStateException("Calling setDateFinish when the activity is not completed/closed is not allowed");
		}
		if (!override && getDateFinish() != null)
		{
			log.debug("DateFinish already set : Date=" + getDateFinish() + ", Override=" + override);
			return;
		}
		//
		final String sql = "SELECT MAX(" + MPPCostCollector.COLUMNNAME_MovementDate + ")"
				+ " FROM " + MPPCostCollector.Table_Name
				+ " WHERE " + MPPCostCollector.COLUMNNAME_PP_Order_Node_ID + "=?"
				+ " AND " + MPPCostCollector.COLUMNNAME_DocStatus + " IN (?,?,?)"
				+ " AND " + MPPCostCollector.COLUMNNAME_CostCollectorType + "=?";
		Timestamp dateFinish = DB.getSQLValueTSEx(get_TrxName(), sql, get_ID(),
				MPPCostCollector.DOCSTATUS_InProgress,
				MPPCostCollector.DOCSTATUS_Completed,
				MPPCostCollector.DOCSTATUS_Closed,
				MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl);
		if (dateFinish == null)
		{
			log.warn("Activity Completed/Closed but no cost collectors found!");
			return;
		}
		setDateFinish(dateFinish);
	}
}
