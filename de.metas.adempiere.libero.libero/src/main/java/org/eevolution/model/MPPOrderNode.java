/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderWorkflowBL;

import de.metas.i18n.IMsgBL;

/**
 * PP Order Workflow Node Model
 * 
 * <p>Usage notes:
 * <li>NEVER use {@link #setDocStatus(String)} method directly.
 * 		Use {@link #completeIt()}, {@link #closeIt()}, {@link #setInProgress()} instead.
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPOrderNode extends X_PP_Order_Node
{
	private static final long serialVersionUID = 1L;

	/**
	 * Get WF Node from Cache
	 * @param ctx context
	 * @param PP_Order_Node_ID id
	 * @return MPPOrderNode
	 * @deprecated Use {@link #get(Properties, int, String)}
	 */
	@Deprecated
	public static MPPOrderNode get (Properties ctx, int PP_Order_Node_ID)
	{
		return get(ctx, PP_Order_Node_ID, null); 
	}
	
	/**
	 * Get WF Node. If trxName is null, cache will be used.
	 * @param ctx context
	 * @param PP_Order_Node_ID id
	 * @param trxName
	 * @return MPPOrderNode
	 */
	public static MPPOrderNode get (Properties ctx, int PP_Order_Node_ID, String trxName)
	{
		if (PP_Order_Node_ID <= 0)
			return null;
		MPPOrderNode retValue = null;
		if (trxName == null)
		{
			retValue = s_cache.get (PP_Order_Node_ID);
			if (retValue != null)
				return retValue;
		}
		//
		retValue = new MPPOrderNode (ctx, PP_Order_Node_ID, trxName);
		if (retValue.getPP_Order_Node_ID() <= 0)
		{
			retValue = null;
		}
		if (retValue != null && trxName == null)
		{
			s_cache.put (PP_Order_Node_ID, retValue);
		}
		//
		return retValue;
	}	//	get

	/**
	 * @return true if this is last node
	 */
	public static boolean isLastNode(Properties ctx, int PP_Order_Node_ID, String trxName)
	{
		String whereClause = MPPOrderNodeNext.COLUMNNAME_PP_Order_Node_ID+"=?";
		return false == new Query(ctx, MPPOrderNodeNext.Table_Name, whereClause, trxName)
									.setOnlyActiveRecords(true)
									.setParameters(new Object[]{PP_Order_Node_ID})
									.match();
	}

	/**	Cache						*/
	private static CCache<Integer,MPPOrderNode>	s_cache	= new CCache<> (Table_Name, 50);
	/** MPPOrderWorkflow			*/
	MPPOrderWorkflow m_order_wf = null;
	
	/**************************************************************************
	 * 	Standard Constructor - save to cache
	 *	@param ctx context
	 *	@param PP_Order_Node_ID id
	 *	@param trxName transaction
	 */
	public MPPOrderNode (Properties ctx, int PP_Order_Node_ID, String trxName)
	{
		super (ctx, PP_Order_Node_ID, trxName);
		if (PP_Order_Node_ID == 0)
		{
			setDefault();
		}
		//	Save to Cache
		if (get_ID() != 0 && trxName == null)
		{
			s_cache.put (getPP_Order_Node_ID(), this);
		}
	}	//	MPPOrderNode

	/**
	 * 	Parent Constructor
	 *	@param wf workflow (parent)
	 *	@param Value value
	 *	@param Name name
	 */
	public MPPOrderNode (MPPOrderWorkflow wf, String Value, String Name)
	{
		this (wf.getCtx(), 0, wf.get_TrxName());
		setClientOrg(wf);
		setPP_Order_Workflow_ID (wf.getPP_Order_Workflow_ID());
		setValue (Value);
		setName (Name);
		m_durationBaseMS = wf.getDurationBaseSec() * 1000;
	}	//	MPPOrderNode
	
//	/**
//	 * Peer constructor
//	 * @param wfNode
//	 * @param PP_Order_Workflow
//	 * @param qtyOrdered
//	 * @param trxName
//	 */
//	public MPPOrderNode (MWFNode wfNode, MPPOrderWorkflow PP_Order_Workflow,
//							BigDecimal qtyOrdered,
//							String trxName)
//	{
//		this(wfNode.getCtx(), 0, trxName);
//		setPP_Order_ID(PP_Order_Workflow.getPP_Order_ID());
//		setPP_Order_Workflow_ID(PP_Order_Workflow.getPP_Order_Workflow_ID());
//		//
//		setAction(wfNode.getAction());
//		setAD_WF_Node_ID(wfNode.getAD_WF_Node_ID());
//		setAD_WF_Responsible_ID(wfNode.getAD_WF_Responsible_ID());
//		setAD_Workflow_ID(wfNode.getAD_Workflow_ID());
//		setIsSubcontracting(wfNode.isSubcontracting());
//		setIsMilestone(wfNode.isMilestone());
//		setC_BPartner_ID(wfNode.getC_BPartner_ID());
//		setCost(wfNode.getCost());
//		setDuration(wfNode.getDuration());
//		setUnitsCycles(wfNode.getUnitsCycles().intValueExact());
//		setOverlapUnits(wfNode.getOverlapUnits());
//		setEntityType(wfNode.getEntityType());
//		setIsCentrallyMaintained(wfNode.isCentrallyMaintained());
//		setJoinElement(wfNode.getJoinElement()); // X
//		setDurationLimit(wfNode.getDurationLimit());
//		setName(wfNode.getName());
//		setPriority(wfNode.getPriority());
//		setSplitElement(wfNode.getSplitElement()); // X
//		setSubflowExecution(wfNode.getSubflowExecution());
//		setValue(wfNode.getValue());
//		setS_Resource_ID(wfNode.getS_Resource_ID());
//		setSetupTime(wfNode.getSetupTime());
//		setSetupTimeRequiered(wfNode.getSetupTime());
//		setMovingTime(wfNode.getMovingTime());
//		setWaitingTime(wfNode.getWaitingTime());
//		setWorkingTime(wfNode.getWorkingTime());
//		setQueuingTime(wfNode.getQueuingTime());
//		setXPosition(wfNode.getXPosition());
//		setYPosition(wfNode.getYPosition());
//		setDocAction(wfNode.getDocAction());
//		setAD_Column_ID(wfNode.getAD_Column_ID());
//		setAD_Form_ID(wfNode.getAD_Form_ID());
//		setAD_Image_ID(wfNode.getAD_Image_ID());
//		setAD_Window_ID(wfNode.getAD_Window_ID());
//		setAD_Process_ID(wfNode.getAD_Process_ID());
//		setAttributeName(wfNode.getAttributeName());
//		setAttributeValue(wfNode.getAttributeValue());
//		setC_BPartner_ID(wfNode.getC_BPartner_ID());
//		setStartMode(wfNode.getStartMode());
//		setFinishMode(wfNode.getFinishMode());
//		setValidFrom(wfNode.getValidFrom());
//		setValidTo(wfNode.getValidTo());
//		//
//		setQtyOrdered(qtyOrdered);
//		setDocStatus(MPPOrderNode.DOCSTATUS_Drafted);
//	}
	
	/**
	 * 	Load Constructor - save to cache
	 * 	@param ctx context
	 * 	@param rs result set to load info from
	 *	@param trxName transaction
	 */
	public MPPOrderNode (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		//	Save to Cache
		if (trxName == null)
		{
			s_cache.put (getPP_Order_Node_ID(), this);
		}
	}	//	MPPOrderNode
	
	
	/**	Next Nodes				*/
	private List<MPPOrderNodeNext> m_next = null;
	/** Duration Base MS		*/
	private long			m_durationBaseMS = -1;

	/**
	 * 	Load Next
	 */
	private List<MPPOrderNodeNext> getNodeNexts()
	{
		if (m_next != null)
		{
			return m_next;
		}
		boolean splitAnd = SPLITELEMENT_AND.equals(getSplitElement());
		String whereClause = MPPOrderNodeNext.COLUMNNAME_PP_Order_Node_ID+"=?";
		m_next = new Query(getCtx(), MPPOrderNodeNext.Table_Name, whereClause, get_TrxName())
		.setParameters(new Object[]{get_ID()})
		.setOnlyActiveRecords(true)
		.setOrderBy(MPPOrderNodeNext.COLUMNNAME_SeqNo+","+MPPOrderNodeNext.COLUMNNAME_PP_Order_Node_ID)
		.list();
		for (MPPOrderNodeNext next : m_next)
		{
			next.setFromSplitAnd(splitAnd);
		}
		log.debug("#" + m_next.size());
		return m_next;
	}	//	loadNext
	
	/**
	 * Get Qty To Deliver (Open Qty)
	 * @return open qty
	 * @deprecated please use {@link IPPOrderWorkflowBL#getQtyToDeliver(I_PP_Order_Node)}
	 */
	@Deprecated
	public BigDecimal getQtyToDeliver()
	{
		return Services.get(IPPOrderWorkflowBL.class).getQtyToDeliver(this);
	}
	
	/**
	 * 	Get Number of Next Nodes
	 * 	@return number of next nodes
	 */
	public int getNextNodeCount()
	{
		return getNodeNexts().size();
	}	//	getNextNodeCount

	/**
	 * 	Get the transitions
	 * 	@param AD_Client_ID for client
	 * 	@return array of next nodes
	 */
	public MPPOrderNodeNext[] getTransitions(int AD_Client_ID)
	{
		ArrayList<MPPOrderNodeNext> list = new ArrayList<>();
		for (MPPOrderNodeNext next : getNodeNexts())
		{
			if (next.getAD_Client_ID() == 0 || next.getAD_Client_ID() == AD_Client_ID)
			{
				list.add(next);
			}
		}
		return list.toArray(new MPPOrderNodeNext [list.size()]);
	}	//	getNextNodes
	
	/**
	 * 	Get Duration in ms
	 *	@return duration in ms
	 */
	public long getDurationMS ()
	{
		long duration = super.getDuration ();
		if (duration == 0)
			return 0;
		if (m_durationBaseMS == -1)
			m_durationBaseMS = getMPPOrderWorkflow().getDurationBaseSec() * 1000;
		return duration * m_durationBaseMS;
	}	//	getDurationMS
	
	/**
	 * 	Get Duration Limit in ms
	 *	@return duration limit in ms
	 */
	public long getLimitMS ()
	{
		long limit = super.getDurationLimit ();
		if (limit == 0)
			return 0;
		if (m_durationBaseMS == -1)
			m_durationBaseMS = getMPPOrderWorkflow().getDurationBaseSec() * 1000;
		return limit * m_durationBaseMS;
	}	//	getLimitMS
	
	/**
	 * 	Get Duration CalendarField
	 *	@return Calendar.MINUTE, etc.
	 */
	public int getDurationCalendarField()
	{
		return getMPPOrderWorkflow().getDurationCalendarField();
	}	//	getDurationCalendarField

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MPPOrderNode[");
		sb.append(get_ID())
			.append("-").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Get Parent
	 *	@return MPPOrderWorkflow
	 */
	public MPPOrderWorkflow getMPPOrderWorkflow()
	{
		if (m_order_wf == null)
			m_order_wf = new MPPOrderWorkflow(getCtx(), getPP_Order_Workflow_ID(), get_TrxName());
		return m_order_wf;
	}	//	getParent

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
			addDescription(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@closed@ ( @Duration@ :" + old + ") ( @QtyRequiered@ :"+getQtyRequiered()+")"));
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
			log.warn("Activity already voided - "+this);
			return;
		}
		BigDecimal qtyRequired = getQtyRequiered();
		if (qtyRequired.signum() != 0)
		{
			addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided") + " (" + qtyRequired + ")");
		}
		setDocStatus(MPPOrderNode.DOCSTATUS_Voided);
		setDocAction(MPPOrderNode.DOCACTION_None);
		setQtyRequiered(Env.ZERO);
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
			throw new IllegalStateException("Cannot change status from "+getDocStatus()+" to "+DOCSTATUS_InProgress);
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
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription
	
	private void setDefault()
	{
		setAction (ACTION_WaitSleep);
		setCost (Env.ZERO);
		setDuration (0);
		setEntityType (ENTITYTYPE_UserMaintained);	// U
		setIsCentrallyMaintained (true);	// Y
		setJoinElement (JOINELEMENT_XOR);	// X
		setDurationLimit (0);
		setSplitElement (SPLITELEMENT_XOR);	// X
		setWaitingTime (0);
		setXPosition (0);
		setYPosition (0);
		setDocStatus(MPPOrderNode.DOCSTATUS_Drafted);
	}

	/**
	 * Set DateFinish as last MovementDate from PP_CostCollector
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
			log.debug("DateFinish already set : Date="+getDateFinish()+", Override="+override);
			return;
		}
		//
		final String sql = "SELECT MAX("+MPPCostCollector.COLUMNNAME_MovementDate+")"
					+" FROM "+MPPCostCollector.Table_Name
					+" WHERE "+MPPCostCollector.COLUMNNAME_PP_Order_Node_ID+"=?"
						+" AND "+MPPCostCollector.COLUMNNAME_DocStatus+" IN (?,?,?)"
						+" AND "+MPPCostCollector.COLUMNNAME_CostCollectorType+"=?"
		;
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
	
	public BigDecimal getVariance(String costCollectorType, String columnName)
	{
		final String whereClause = I_PP_Cost_Collector.COLUMNNAME_PP_Order_Node_ID+"=?"
		+" AND "+I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID+"=?"
		+" AND "+I_PP_Cost_Collector.COLUMNNAME_DocStatus+" IN (?,?)"
		+" AND "+I_PP_Cost_Collector.COLUMNNAME_CostCollectorType+"=?"
		;
		BigDecimal variance = new Query(getCtx(), I_PP_Cost_Collector.Table_Name, whereClause, get_TrxName())
		.setParameters(new Object[]{
				getPP_Order_Node_ID(),
				getPP_Order_ID(),
				X_PP_Cost_Collector.DOCSTATUS_Completed, X_PP_Cost_Collector.DOCSTATUS_Closed,
				costCollectorType,
		})
		.sum(columnName);
		//
		return variance;
	}
	
	public BigDecimal getSetupTimeUsageVariance()
	{
		return getVariance(X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance,
				X_PP_Cost_Collector.COLUMNNAME_SetupTimeReal);
	}
	
	public BigDecimal getDurationUsageVariance()
	{
		return getVariance(X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance,
				X_PP_Cost_Collector.COLUMNNAME_DurationReal);
	}
}
