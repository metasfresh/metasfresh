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
package org.compiere.wf;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_WF_Process;
import org.compiere.process.DocAction;
import org.compiere.process.StateEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import de.metas.process.ProcessInfo;


/**
 *	Workflow Process
 *	
 *  @author Jorg Janke
 *  @version $Id: MWFProcess.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFProcess extends X_AD_WF_Process
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8992222567597358696L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_WF_Process_ID process
	 *	@param trxName transaction
	 */
	public MWFProcess (Properties ctx, int AD_WF_Process_ID, String trxName)
	{
		super (ctx, AD_WF_Process_ID, trxName);
		if (AD_WF_Process_ID == 0)
			throw new IllegalArgumentException ("Cannot create new WF Process directly");
		m_state = new StateEngine (getWFState());
	}	//	MWFProcess

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWFProcess (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		m_state = new StateEngine (getWFState());
	}	//	MWFProcess
	
	/**
	 * 	New Constructor
	 *	@param wf workflow
	 *	@param pi Process Info (Record_ID)
	 *  @deprecated
	 *	@throws Exception
	 */
	@Deprecated
	public MWFProcess (MWorkflow wf, ProcessInfo pi) throws Exception
	{
		this(wf, pi, wf.get_TrxName());
	}
	
	/**
	 * 	New Constructor
	 *	@param wf workflow
	 *	@param pi Process Info (Record_ID)
	 *  @param trxName 
	 *	@throws Exception
	 */
	public MWFProcess (MWorkflow wf, ProcessInfo pi, String trxName) throws Exception
	{
		super (wf.getCtx(), 0, trxName);
		// metas: tsa: begin: WF Process should be started using process's AD_Client_ID
		if (pi.getAD_Client_ID() >= 0 && pi.getAD_Client_ID() != getAD_Client_ID())
			setClientOrg(pi.getAD_Client_ID(), 0);
		// metas: tsa: end
		if (!TimeUtil.isValid(wf.getValidFrom(), wf.getValidTo()))
			throw new IllegalStateException("Workflow not valid");
		m_wf = wf;
//TODO  m_pi = pi; red1 - never used  -check later	
		setAD_Workflow_ID (wf.getAD_Workflow_ID());
		setPriority(wf.getPriority());
		super.setWFState (WFSTATE_NotStarted);
		
		//	Document
		setAD_Table_ID(wf.getAD_Table_ID());
		setRecord_ID(pi.getRecord_ID());
		if (getPO() == null)
		{
			setTextMsg("No PO with ID=" + pi.getRecord_ID());
			addTextMsg(new Exception(""));
			super.setWFState (WFSTATE_Terminated);
		}
		else
			setTextMsg(getPO());
		//	Responsible/User
		if (wf.getAD_WF_Responsible_ID() == 0)
			setAD_WF_Responsible_ID();
		else
			setAD_WF_Responsible_ID(wf.getAD_WF_Responsible_ID());
		setUser_ID(pi.getAD_User_ID());		//	user starting
		//
		m_state = new StateEngine (getWFState());
		setProcessed (false);
		//	Lock Entity
		getPO();
		//hengsin: remove lock/unlock which is causing deadlock
		//if (m_po != null)
			//m_po.lock();
	}	//	MWFProcess

	/**	State Machine				*/
	private StateEngine			m_state = null;
	/**	Activities					*/
	private MWFActivity[] 		m_activities = null;
	/**	Workflow					*/
	private MWorkflow			m_wf = null;
	/**	Process Info				*/
/*TODO red1 - never used
 * 
	private ProcessInfo			m_pi = null;
 */
	/**	Persistent Object			*/
	private PO					m_po = null;
	/** Message from Activity		*/
	private String				m_processMsg = null;
	
	/**
	 * 	Get active Activities of Process
	 *	@param requery if true requery
	 *	@param onlyActive only active activities
	 *	@return array of activities
	 */
	public MWFActivity[] getActivities (boolean requery, boolean onlyActive)
	{
		return getActivities(requery, onlyActive, get_TrxName());
	}
	
	/**
	 * 	Get active Activities of Process
	 *	@param requery if true requery
	 *	@param onlyActive only active activities
	 *	@return array of activities
	 */
	public MWFActivity[] getActivities (boolean requery, boolean onlyActive, String trxName)
	{
		if (!requery && m_activities != null)
			return m_activities;
		//
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer("AD_WF_Process_ID=?");
		params.add(getAD_WF_Process_ID());
		if (onlyActive)
		{
			whereClause.append(" AND Processed=?");
			params.add(false);
		}
		List<MWFActivity> list = new Query(getCtx(), MWFActivity.Table_Name, whereClause.toString(), trxName)
								.setParameters(params)
								.list();
		m_activities = new MWFActivity[list.size ()];
		list.toArray (m_activities);
		return m_activities;
	}	//	getActivities
	
	/**
	 * 	Get State
	 *	@return state
	 */
	public StateEngine getState()
	{
		return m_state;
	}	//	getState
	
	/**
	 * 	Get Action Options
	 *	@return array of valid actions
	 */
	public String[] getActionOptions()
	{
		return m_state.getActionOptions();
	}	//	getActionOptions
	
	/**
	 * 	Set Process State and update Actions
	 *	@param WFState
	 */
	@Override
	public void setWFState (String WFState)
	{
		if (m_state == null)
			m_state = new StateEngine (getWFState());
		if (m_state.isClosed())
			return;
		if (getWFState().equals(WFState))
			return;
		//
		if (m_state.isValidNewState(WFState))
		{
			log.debug(WFState); 
			super.setWFState (WFState);
			m_state = new StateEngine (getWFState());
			if (m_state.isClosed())
				setProcessed(true);
			save();
			//	Force close to all Activities
			if (m_state.isClosed())
			{
				MWFActivity[] activities = getActivities(true, true);	//	requery only active
				for (int i = 0; i < activities.length; i++)
				{
					if (!activities[i].isClosed())
					{
						activities[i].setTextMsg("Process:" + WFState);
						activities[i].setWFState(WFState);
					}
					if (!activities[i].isProcessed())
						activities[i].setProcessed(true);
					activities[i].save();
				}
			}	//	closed
		}
		else	
			log.error("Ignored Invalid Transformation - New=" + WFState 
				+ ", Current=" + getWFState());
	}	//	setWFState

	
	/**************************************************************************
	 * 	Check Status of Activities.
	 * 	- update Process if required
	 * 	- start new activity
	 * 	@param trxName transaction
	 */
	public void checkActivities(String trxName, PO lastPO)
	{
		log.info("(" + getAD_Workflow_ID() + ") - " + getWFState() 
			+ (trxName == null ? "" : "[" + trxName + "]"));
		if (m_state.isClosed())
			return;
		
		if (lastPO != null && lastPO.get_ID() == this.getRecord_ID())
			m_po = lastPO;
		
		//
		MWFActivity[] activities = getActivities (true, true, trxName);	//	requery active
		String closedState = null;
		boolean suspended = false;
		boolean running = false;
		for (int i = 0; i < activities.length; i++)
		{
			MWFActivity activity = activities[i];
			StateEngine activityState = activity.getState(); 
			
			//	Completed - Start Next
			if (activityState.isCompleted())
			{
				if (startNext (activity, activities, lastPO, trxName))
					continue;		
			}
			//
			String activityWFState = activity.getWFState();
			if (activityState.isClosed())
			{
				//	eliminate from active processed
				activity.setProcessed(true);
				activity.save();
				//
				if (closedState == null)
					closedState = activityWFState;
				else if (!closedState.equals(activityState))
				{
					//	Overwrite if terminated
					if (WFSTATE_Terminated.equals(activityState))
						closedState = activityWFState;
					//	Overwrite if activity aborted and no other terminated
					else if (WFSTATE_Aborted.equals(activityState) && !WFSTATE_Terminated.equals(closedState))
						closedState = activityWFState;
				}
			}
			else	//	not closed
			{
				closedState = null;		//	all need to be closed
				if (activityState.isSuspended())
					suspended = true;
				if (activityState.isRunning())
					running = true;
			}
		}	//	for all activities
		if (activities.length == 0)
		{
			setTextMsg("No Active Processed found");
			addTextMsg(new Exception(""));
			closedState = WFSTATE_Terminated;
		}
		if (closedState != null)
		{
			setWFState(closedState);
			getPO();
			//hengsin: remmove lock/unlock in workflow which is causing deadlock in many place
			//if (m_po != null)
				//m_po.unlock(null);
		}
		else if (suspended)
			setWFState(WFSTATE_Suspended);
		else if (running)
			setWFState(WFSTATE_Running);
	}	//	checkActivities


	/**
	 * 	Start Next Activity
	 *	@param last last activity
	 *	@param activities all activities
	 *	@return true if there is a next activity
	 */
	private boolean startNext (MWFActivity last, MWFActivity[] activities, PO lastPO, String trxName)
	{
		log.debug("Last=" + last);
		//	transitions from the last processed node
		MWFNodeNext[] transitions = getWorkflow().getNodeNexts(
			last.getAD_WF_Node_ID(), last.getPO_AD_Client_ID());
		if (transitions == null || transitions.length == 0)
			return false;	//	done
		
		//	We need to wait for last activity
		if (MWFNode.JOINELEMENT_AND.equals(last.getNode().getJoinElement()))
		{
			//	get previous nodes
			//	check if all have closed activities
			//	return false for all but the last
		}
		//	eliminate from active processed
		last.setProcessed(true);
		last.save();

		//	Start next activity
		String split = last.getNode().getSplitElement();
		for (int i = 0; i < transitions.length; i++)
		{
			//	Is this a valid transition?
			if (!transitions[i].isValidFor(last))
				continue;
			
			//	Start new Activity...
			MWFActivity activity = new MWFActivity (this, transitions[i].getAD_WF_Next_ID(), lastPO);
			activity.set_TrxName(trxName);
			activity.run();
			
			//	only the first valid if XOR
			if (MWFNode.SPLITELEMENT_XOR.equals(split))
				return true;
		}	//	for all transitions
		return true;
	}	//	startNext

	
	/**************************************************************************
	 * 	Set Workflow Responsible.
	 * 	Searches for a Invoker.
	 */
	public void setAD_WF_Responsible_ID ()
	{
		int AD_WF_Responsible_ID = DB.getSQLValueEx(ITrx.TRXNAME_None,
				Env.getUserRolePermissions(getCtx()).addAccessSQL(
			"SELECT AD_WF_Responsible_ID FROM AD_WF_Responsible "
			+ "WHERE ResponsibleType='H' AND COALESCE(AD_User_ID,0)=0 "
			+ "ORDER BY AD_Client_ID DESC", 
			"AD_WF_Responsible", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO));
		setAD_WF_Responsible_ID (AD_WF_Responsible_ID);
	}	//	setAD_WF_Responsible_ID

	/**
	 * 	Set User from 
	 * 	- (1) Responsible
	 *  - (2) Document Sales Rep
	 *  - (3) Document UpdatedBy
	 * 	- (4) Process invoker
	 * 	@param User_ID process invoker
	 */
	private void setUser_ID (Integer User_ID)
	{
		//	Responsible
		MWFResponsible resp = MWFResponsible.get(getCtx(), getAD_WF_Responsible_ID());
		//	(1) User - Directly responsible
		int AD_User_ID = resp.getAD_User_ID();
		
		//	Invoker - get Sales Rep or last updater of Document
		if (AD_User_ID == 0 && resp.isInvoker())
		{
			getPO();
			//	(2) Doc Owner
			if (m_po != null && m_po instanceof DocAction)
			{
				DocAction da = (DocAction)m_po;
				AD_User_ID = da.getDoc_User_ID();
			}
			//	(2) Sales Rep
			if (AD_User_ID == 0 && m_po != null && m_po.get_ColumnIndex("SalesRep_ID") != -1)
			{
				Object sr = m_po.get_Value("SalesRep_ID");
				if (sr != null && sr instanceof Integer)
					AD_User_ID = ((Integer)sr).intValue();
			}
			//	(3) UpdatedBy
			if (AD_User_ID == 0 && m_po != null)
				AD_User_ID = m_po.getUpdatedBy();
		}
		
		//	(4) Process Owner
		if (AD_User_ID == 0 && User_ID != null)
			AD_User_ID = User_ID.intValue();
		//	Fallback 
		if (AD_User_ID == 0)
			AD_User_ID = Env.getAD_User_ID(getCtx());
		//
		setAD_User_ID(AD_User_ID);
	}	//	setUser_ID
	
	/**
	 * 	Get Workflow
	 *	@return workflow
	 */
	private MWorkflow getWorkflow()
	{
		if (m_wf == null)
			m_wf = MWorkflow.get (getCtx(), getAD_Workflow_ID());
		if (m_wf.get_ID() == 0)
			throw new IllegalStateException("Not found - AD_Workflow_ID=" + getAD_Workflow_ID());
		return m_wf;
	}	//	getWorkflow
	

	/**************************************************************************
	 * 	Perform Action
	 *	@param action StateEngine.ACTION_*
	 *	@return true if valid
	 */
	public boolean perform (String action)
	{
		if (!m_state.isValidAction(action))
		{
			log.error("Ignored Invalid Transformation - Action=" + action 
				+ ", CurrentState=" + getWFState());
			return false;
		}
		log.debug(action); 
		//	Action is Valid
		if (StateEngine.ACTION_Start.equals(action))
			return startWork();
		//	Set new State
		setWFState (m_state.getNewStateIfAction(action));
		return true;
	}	//	perform
	
	/**
	 * 	Start WF Execution
	 *	@return true if success
	 */
	public boolean startWork()
	{
		if (!m_state.isValidAction(StateEngine.ACTION_Start))
		{
			log.warn("State=" + getWFState() + " - cannot start");
			return false;
		}
		int AD_WF_Node_ID = getWorkflow().getAD_WF_Node_ID();
		log.debug("AD_WF_Node_ID=" + AD_WF_Node_ID);
		setWFState(WFSTATE_Running);
		try
		{
			//	Start first Activity with first Node
			MWFActivity activity = new MWFActivity (this, AD_WF_Node_ID);
			//
			// Thread workerWF = new Thread(activity);
			// workerWF.setName(activity.getAD_Workflow().getName() + " "
			//		+ activity.getAD_Table().getName() + " "
			//		+ activity.getRecord_ID());
			// workerWF.start();
			activity.run();

		}
		catch (Throwable e)
		{
			log.error("AD_WF_Node_ID=" + AD_WF_Node_ID, e);
			setTextMsg(e.toString());
			addTextMsg(e);
			setWFState(StateEngine.STATE_Terminated);
			return false;
		}
		return true;
	}	//	performStart
	

	/**************************************************************************
	 * 	Get Persistent Object
	 *	@return po
	 */
	public PO getPO()
	{
		if (m_po != null)
			return m_po;
		if (getRecord_ID() == 0)
			return null;
		
		MTable table = MTable.get (getCtx(), getAD_Table_ID());
		m_po = table.getPO(getRecord_ID(), get_TrxName());
		return m_po;
	}	//	getPO

	/**
	 * 	Set Text Msg (add to existing)
	 *	@param po base object
	 */
	public void setTextMsg (PO po)
	{
		if (po != null && po instanceof DocAction)
			setTextMsg(((DocAction)po).getSummary());
	}	//	setTextMsg	

	/**
	 * 	Set Text Msg (add to existing)
	 *	@param TextMsg msg
	 */
	@Override
	public void setTextMsg (String TextMsg)
	{
		String oldText = getTextMsg();
		if (oldText == null || oldText.length() == 0)
			super.setTextMsg (TextMsg);
		else if (TextMsg != null && TextMsg.length() > 0)
			super.setTextMsg (oldText + "\n - " + TextMsg);
	}	//	setTextMsg	

	/**
	 * 	Add to Text Msg
	 *	@param obj some object
	 */
	public void addTextMsg (Object obj)
	{
		if (obj == null)
			return;
		//
		StringBuffer TextMsg = new StringBuffer ();
		if (obj instanceof Exception)
		{
			Exception ex = (Exception)obj;
			if (ex.getMessage() != null && ex.getMessage().trim().length() > 0)
			{
				TextMsg.append(ex.toString());
			}
			else if (ex instanceof NullPointerException)
			{
				TextMsg.append(ex.getClass().getName());
			}
			while (ex != null)
			{
				StackTraceElement[] st = ex.getStackTrace();
				for (int i = 0; i < st.length; i++)
				{
					StackTraceElement ste = st[i];
					if (i == 0 || ste.getClassName().startsWith("org.compiere") || ste.getClassName().startsWith("org.adempiere"))
						TextMsg.append(" (").append(i).append("): ")
							.append(ste.toString())
							.append("\n");
				}
				if (ex.getCause() instanceof Exception)
					ex = (Exception)ex.getCause();
				else
					ex = null;
			}
		}
		else
		{
			TextMsg.append(obj.toString());
		}
		//
		String oldText = getTextMsg();
		if (oldText == null || oldText.length() == 0)
			super.setTextMsg(Util.trimSize(TextMsg.toString(),1000));
		else if (TextMsg != null && TextMsg.length() > 0)
			super.setTextMsg(Util.trimSize(oldText + "\n - " + TextMsg.toString(),1000));
	}	//	addTextMsg
	
	/**
	 * 	Set Runtime (Error) Message
	 *	@param msg message
	 */
	public void setProcessMsg (String msg)
	{
		m_processMsg = msg;
		if (msg != null && msg.length() > 0)
			setTextMsg(msg);
	}	//	setProcessMsg
	
	/**
	 * 	Get Runtime (Error) Message
	 *	@return msg
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
}	//	MWFProcess
