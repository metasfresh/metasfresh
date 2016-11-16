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

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.permissions.DocumentApprovalConstraint;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_User;
import org.compiere.model.MAttachment;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MNote;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.model.MUserRoles;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_WF_Activity;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.StateEngine;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Trace;
import org.compiere.util.Trx;
import org.compiere.util.Util;

import de.metas.currency.ICurrencyBL;
import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;

/**
 * Workflow Activity Model.
 * Controlled by WF Process:
 * set Node - startWork
 *
 * @author Jorg Janke
 * @version $Id: MWFActivity.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFActivity extends X_AD_WF_Activity implements Runnable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1584816335412184476L;

	/**
	 * Get Activities for table/record
	 *
	 * @param ctx context
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param activeOnly if true only not processed records are returned
	 * @return activity
	 */
	public static MWFActivity[] get(Properties ctx, int AD_Table_ID, int Record_ID, boolean activeOnly)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer("AD_Table_ID=? AND Record_ID=?");
		params.add(AD_Table_ID);
		params.add(Record_ID);
		if (activeOnly)
		{
			whereClause.append(" AND Processed<>?");
			params.add(true);
		}
		List<MWFActivity> list = new Query(ctx, Table_Name, whereClause.toString(), null)
				.setParameters(params)
				.setOrderBy(COLUMNNAME_AD_WF_Activity_ID)
				.list();

		MWFActivity[] retValue = new MWFActivity[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// get

	/**
	 * Get Active Info
	 *
	 * @param ctx context
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @return activity summary
	 */
	public static String getActiveInfo(Properties ctx, int AD_Table_ID, int Record_ID)
	{
		MWFActivity[] acts = get(ctx, AD_Table_ID, Record_ID, true);
		if (acts == null || acts.length == 0)
			return null;
		//
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < acts.length; i++)
		{
			if (i > 0)
				sb.append("\n");
			MWFActivity activity = acts[i];
			sb.append(activity.toStringX());
		}
		return sb.toString();
	}	// getActivityInfo

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_WF_Activity_ID id
	 * @param trxName transaction
	 */
	public MWFActivity(Properties ctx, int AD_WF_Activity_ID, String trxName)
	{
		super(ctx, AD_WF_Activity_ID, trxName);
		if (AD_WF_Activity_ID == 0)
			throw new IllegalArgumentException("Cannot create new WF Activity directly");
		m_state = new StateEngine(getWFState());
	}	// MWFActivity

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MWFActivity(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		m_state = new StateEngine(getWFState());
	}	// MWFActivity

	/**
	 * Parent Contructor
	 *
	 * @param process process
	 * @param AD_WF_Node_ID start node
	 */
	public MWFActivity(MWFProcess process, int AD_WF_Node_ID)
	{
		super(process.getCtx(), 0, process.get_TrxName());
		setAD_WF_Process_ID(process.getAD_WF_Process_ID());
		setPriority(process.getPriority());
		// Document Link
		setAD_Table_ID(process.getAD_Table_ID());
		setRecord_ID(process.getRecord_ID());
		// modified by Rob Klein
		setAD_Client_ID(process.getAD_Client_ID());
		setAD_Org_ID(process.getAD_Org_ID());
		// Status
		super.setWFState(WFSTATE_NotStarted);
		m_state = new StateEngine(getWFState());
		setProcessed(false);
		// Set Workflow Node
		setAD_Workflow_ID(process.getAD_Workflow_ID());
		setAD_WF_Node_ID(AD_WF_Node_ID);
		// Node Priority & End Duration
		MWFNode node = MWFNode.get(getCtx(), AD_WF_Node_ID);
		int priority = node.getPriority();
		if (priority != 0 && priority != getPriority())
			setPriority(priority);
		long limitMS = node.getLimitMS();
		if (limitMS != 0)
			setEndWaitTime(new Timestamp(limitMS + System.currentTimeMillis()));
		// Responsible
		setResponsible(process);
		save();
		//
		m_audit = new MWFEventAudit(this);
		m_audit.save();
		//
		m_process = process;
	}	// MWFActivity

	/**
	 * Parent Contructor
	 *
	 * @param process process
	 * @param AD_WF_Node_ID start node
	 * @param lastPO PO from the previously executed node
	 */
	public MWFActivity(MWFProcess process, int next_ID, PO lastPO)
	{
		this(process, next_ID);
		if (lastPO != null)
		{
			// Compare if the last PO is the same type and record needed here, if yes, use it
			if (lastPO.get_Table_ID() == getAD_Table_ID() && lastPO.get_ID() == getRecord_ID())
			{
				m_po = lastPO;
			}
		}
	}

	/** State Machine */
	private StateEngine m_state = null;
	/** Workflow Node */
	private MWFNode m_node = null;
	/** Transaction */
	// private Trx m_trx = null;
	/** Audit */
	private MWFEventAudit m_audit = null;
	/** Persistent Object */
	private PO m_po = null;
	/** Document Status */
	private String m_docStatus = null;
	/** New Value to save in audit */
	private String m_newValue = null;
	/** Process */
	private MWFProcess m_process = null;
	/** List of email recipients */
	private ArrayList<String> m_emails = new ArrayList<String>();

	/**************************************************************************
	 * Get State
	 *
	 * @return state
	 */
	public StateEngine getState()
	{
		return m_state;
	}	// getState

	/**
	 * Set Activity State.
	 * It also validates the new state and if is valid,
	 * then create event audit and call {@link MWFProcess#checkActivities(String, PO)}
	 *
	 * @param WFState
	 */
	@Override
	public void setWFState(String WFState)
	{
		if (m_state == null)
			m_state = new StateEngine(getWFState());
		if (m_state.isClosed())
			return;
		if (getWFState().equals(WFState))
			return;
		//
		if (m_state.isValidNewState(WFState))
		{
			String oldState = getWFState();
			log.debug(oldState + "->" + WFState + ", Msg=" + getTextMsg());
			super.setWFState(WFState);
			m_state = new StateEngine(getWFState());
			save();			// closed in MWFProcess.checkActivities()
			updateEventAudit();

			// Inform Process
			if (m_process == null)
				m_process = new MWFProcess(getCtx(), getAD_WF_Process_ID(),
						this.get_TrxName());
			m_process.checkActivities(this.get_TrxName(), m_po);
		}
		else
		{
			String msg = "Set WFState - Ignored Invalid Transformation - New="
					+ WFState + ", Current=" + getWFState();
			log.error(msg);
			Trace.printStack();
			setTextMsg(msg);
			save();
			// TODO: teo_sarca: throw exception ? please analyze the call hierarchy first
		}
	}	// setWFState

	/**
	 * Is Activity closed
	 *
	 * @return true if closed
	 */
	public boolean isClosed()
	{
		return m_state.isClosed();
	}	// isClosed

	/**************************************************************************
	 * Update Event Audit
	 */
	private void updateEventAudit()
	{
		// log.debug("");
		getEventAudit();
		m_audit.setTextMsg(getTextMsg());
		m_audit.setWFState(getWFState());
		if (m_newValue != null)
			m_audit.setNewValue(m_newValue);
		if (m_state.isClosed())
		{
			m_audit.setEventType(MWFEventAudit.EVENTTYPE_ProcessCompleted);
			long ms = System.currentTimeMillis() - m_audit.getCreated().getTime();
			m_audit.setElapsedTimeMS(new BigDecimal(ms));
		}
		else
			m_audit.setEventType(MWFEventAudit.EVENTTYPE_StateChanged);
		m_audit.save();
	}	// updateEventAudit

	/**
	 * Get/Create Event Audit
	 *
	 * @return event
	 */
	public MWFEventAudit getEventAudit()
	{
		if (m_audit != null)
			return m_audit;
		MWFEventAudit[] events = MWFEventAudit.get(getCtx(), getAD_WF_Process_ID(), getAD_WF_Node_ID(), get_TrxName());
		if (events == null || events.length == 0)
			m_audit = new MWFEventAudit(this);
		else
			m_audit = events[events.length - 1];		// last event
		return m_audit;
	}	// getEventAudit

	/**************************************************************************
	 * Get Persistent Object in Transaction
	 *
	 * @param trx transaction
	 * @return po
	 */
	public PO getPO(Trx trx)
	{
		if (m_po != null)
		{
			if (trx != null)
				m_po.set_TrxName(trx.getTrxName());
			return m_po;
		}

		MTable table = MTable.get(getCtx(), getAD_Table_ID());
		if (trx != null)
			m_po = table.getPO(getRecord_ID(), trx.getTrxName());
		else
			m_po = table.getPO(getRecord_ID(), null);
		return m_po;
	}	// getPO

	/**
	 * Get Persistent Object
	 *
	 * @return po
	 */
	public PO getPO()
	{
		return getPO(get_TrxName() != null ? Trx.get(get_TrxName(), false) : null);
	}	// getPO

	/**
	 * Get PO AD_Client_ID
	 *
	 * @return client of PO
	 */
	public int getPO_AD_Client_ID()
	{
		if (m_po == null)
			getPO(get_TrxName() != null ? Trx.get(get_TrxName(), false) : null);
		if (m_po != null)
			return m_po.getAD_Client_ID();
		return 0;
	}	// getPO_AD_Client_ID

	/**
	 * Get Attribute Value (based on Node) of PO
	 *
	 * @return Attribute Value or null
	 */
	public Object getAttributeValue()
	{
		MWFNode node = getNode();
		if (node == null)
			return null;
		int AD_Column_ID = node.getAD_Column_ID();
		if (AD_Column_ID == 0)
			return null;
		PO po = getPO();
		if (po.get_ID() == 0)
			return null;
		return po.get_ValueOfColumn(AD_Column_ID);
	}	// getAttributeValue

	/**
	 * Is SO Trx
	 *
	 * @return SO Trx or of not found true
	 */
	public boolean isSOTrx()
	{
		PO po = getPO();
		if (po.get_ID() == 0)
			return true;
		// Is there a Column?
		int index = po.get_ColumnIndex("IsSOTrx");
		if (index < 0)
		{
			if (po.get_TableName().startsWith("M_"))
				return false;
			return true;
		}
		// we have a column
		try
		{
			Boolean IsSOTrx = (Boolean)po.get_Value(index);
			return IsSOTrx.booleanValue();
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		return true;
	}	// isSOTrx

	/**************************************************************************
	 * Set AD_WF_Node_ID.
	 * (Re)Set to Not Started
	 *
	 * @param AD_WF_Node_ID now node
	 */
	@Override
	public void setAD_WF_Node_ID(int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID == 0)
			throw new IllegalArgumentException("Workflow Node is not defined");
		super.setAD_WF_Node_ID(AD_WF_Node_ID);
		//
		if (!WFSTATE_NotStarted.equals(getWFState()))
		{
			super.setWFState(WFSTATE_NotStarted);
			m_state = new StateEngine(getWFState());
		}
		if (isProcessed())
			setProcessed(false);
	}	// setAD_WF_Node_ID

	/**
	 * Get WF Node
	 *
	 * @return node
	 */
	public MWFNode getNode()
	{
		if (m_node == null)
			m_node = MWFNode.get(getCtx(), getAD_WF_Node_ID());
		return m_node;
	}	// getNode

	/**
	 * Get WF Node Name
	 *
	 * @return translated node name
	 */
	public String getNodeName()
	{
		return getNode().getName(true);
	}	// getNodeName

	/**
	 * Get Node Description
	 *
	 * @return translated node description
	 */
	public String getNodeDescription()
	{
		return getNode().getDescription(true);
	}	// getNodeDescription

	/**
	 * Get Node Help
	 *
	 * @return translated node help
	 */
	public String getNodeHelp()
	{
		return getNode().getHelp(true);
	}	// getNodeHelp

	/**
	 * Is this an user Approval step?
	 *
	 * @return true if User Approval
	 */
	public boolean isUserApproval()
	{
		return getNode().isUserApproval();
	}	// isNodeApproval

	/**
	 * Is this a Manual user step?
	 *
	 * @return true if Window/Form/..
	 */
	public boolean isUserManual()
	{
		return getNode().isUserManual();
	}	// isUserManual

	/**
	 * Is this a user choice step?
	 *
	 * @return true if User Choice
	 */
	public boolean isUserChoice()
	{
		return getNode().isUserChoice();
	}	// isUserChoice

	/**
	 * Set Text Msg (add to existing)
	 *
	 * @param TextMsg
	 */
	@Override
	public void setTextMsg(String TextMsg)
	{
		if (TextMsg == null || TextMsg.length() == 0)
			return;
		String oldText = getTextMsg();
		if (oldText == null || oldText.length() == 0)
			super.setTextMsg(Util.trimSize(TextMsg, 1000));
		else if (TextMsg != null && TextMsg.length() > 0)
			super.setTextMsg(Util.trimSize(oldText + "\n - " + TextMsg, 1000));
	}	// setTextMsg

	/**
	 * Add to Text Msg
	 *
	 * @param obj some object
	 */
	public void addTextMsg(Object obj)
	{
		if (obj == null)
			return;
		//
		StringBuffer TextMsg = new StringBuffer();
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
			super.setTextMsg(Util.trimSize(TextMsg.toString(), 1000));
		else if (TextMsg != null && TextMsg.length() > 0)
			super.setTextMsg(Util.trimSize(oldText + "\n - " + TextMsg.toString(), 1000));
	}	// setTextMsg

	/**
	 * Get WF State text
	 *
	 * @return state text
	 */
	public String getWFStateText()
	{
		return Services.get(IADReferenceDAO.class).retrieveListNameTrl(getCtx(), WFSTATE_AD_Reference_ID, getWFState());
	}	// getWFStateText

	/**
	 * Set Responsible and User from Process / Node
	 *
	 * @param process process
	 */
	private void setResponsible(MWFProcess process)
	{
		// Responsible
		int AD_WF_Responsible_ID = getNode().getAD_WF_Responsible_ID();
		if (AD_WF_Responsible_ID == 0)	// not defined on Node Level
			AD_WF_Responsible_ID = process.getAD_WF_Responsible_ID();
		setAD_WF_Responsible_ID(AD_WF_Responsible_ID);
		MWFResponsible resp = getResponsible();

		// User - Directly responsible
		int AD_User_ID = resp.getAD_User_ID();
		// Invoker - get Sales Rep or last updater of document
		if (AD_User_ID == 0 && resp.isInvoker())
			AD_User_ID = process.getAD_User_ID();
		//
		setAD_User_ID(AD_User_ID);
	}	// setResponsible

	/**
	 * Get Responsible
	 *
	 * @return responsible
	 */
	public MWFResponsible getResponsible()
	{
		MWFResponsible resp = MWFResponsible.get(getCtx(), getAD_WF_Responsible_ID());
		return resp;
	}	// isInvoker

	/**
	 * Is Invoker (no user & no role)
	 *
	 * @return true if invoker
	 */
	public boolean isInvoker()
	{
		return getResponsible().isInvoker();
	}	// isInvoker

	/**
	 * Get Approval User.
	 * If the returned user is the same, the document is approved.
	 *
	 * @param AD_User_ID starting User
	 * @param C_Currency_ID currency
	 * @param amount amount
	 * @param AD_Org_ID document organization
	 * @param ownDocument the document is owned by AD_User_ID
	 * @return AD_User_ID - if -1 no Approver
	 */
	public int getApprovalUser(final int AD_User_ID,
			final int C_Currency_ID, final BigDecimal amount,
			final int AD_Org_ID,
			boolean ownDocument)
	{
		// Nothing to approve
		if (amount == null
				|| amount.signum() == 0)
			return AD_User_ID;

		// services
		final IUserDAO userDAO = Services.get(IUserDAO.class);
		final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

		// Starting user
		I_AD_User user = userDAO.retrieveUser(getCtx(), AD_User_ID);
		log.debug("For User=" + user
				+ ", Amt=" + amount
				+ ", Own=" + ownDocument);

		I_AD_User oldUser = null;
		while (user != null)
		{
			if (user.equals(oldUser))
			{
				log.debug("Loop - {}", user.getName());
				return -1;
			}
			oldUser = user;
			log.debug("User=" + user.getName());
			// Get Roles of User
			final List<IUserRolePermissions> roles = userRolePermissionsDAO.retrieveUserRolesPermissionsForUserWithOrgAccess(getCtx(), AD_User_ID, AD_Org_ID);
			for (final IUserRolePermissions role : roles)
			{
				final DocumentApprovalConstraint docApprovalConstraints = role.getConstraint(DocumentApprovalConstraint.class)
						.or(DocumentApprovalConstraint.DEFAULT);
				if (ownDocument && !docApprovalConstraints.canApproveOwnDoc())
				{
					continue;	// find a role with allows them to approve own
				}

				BigDecimal amtApproval = docApprovalConstraints.getAmtApproval();
				if (amtApproval == null || amtApproval.signum() == 0)
				{
					continue;
				}

				//
				final int amtApprovalCurrencyId = docApprovalConstraints.getC_Currency_ID();
				if (C_Currency_ID != amtApprovalCurrencyId
						&& amtApprovalCurrencyId > 0)			// No currency = amt only
				{
					amtApproval = Services.get(ICurrencyBL.class).convert(getCtx(),// today & default rate
							amtApproval, amtApprovalCurrencyId,
							C_Currency_ID, getAD_Client_ID(), AD_Org_ID);
					if (amtApproval == null || amtApproval.signum() == 0)
						continue;
				}
				boolean approved = amount.compareTo(amtApproval) <= 0;
				log.debug("Approved=" + approved
						+ " - User=" + user.getName() + ", Role=" + role.getName()
						+ ", ApprovalAmt=" + amtApproval);
				if (approved)
					return user.getAD_User_ID();
			}

			// **** Find next User
			// Get Supervisor
			if (user.getSupervisor_ID() != 0)
			{
				user = userDAO.retrieveUser(getCtx(), user.getSupervisor_ID());
				log.debug("Supervisor: " + user.getName());
			}
			else
			{
				log.debug("No Supervisor");
				MOrg org = MOrg.get(getCtx(), AD_Org_ID);
				MOrgInfo orgInfo = org.getInfo();
				// Get Org Supervisor
				if (orgInfo.getSupervisor_ID() != 0)
				{
					user = userDAO.retrieveUser(getCtx(), orgInfo.getSupervisor_ID());
					log.debug("Org=" + org.getName() + ",Supervisor: " + user.getName());
				}
				else
				{
					log.debug("No Org Supervisor");
					// Get Parent Org Supervisor
					if (orgInfo.getParent_Org_ID() != 0)
					{
						org = MOrg.get(getCtx(), orgInfo.getParent_Org_ID());
						orgInfo = org.getInfo();
						if (orgInfo.getSupervisor_ID() != 0)
						{
							user = userDAO.retrieveUser(getCtx(), orgInfo.getSupervisor_ID());
							log.debug("Parent Org Supervisor: " + user.getName());
						}
					}
				}
			}	// No Supervisor
			// ownDocument should always be false for the next user
			ownDocument = false;
		}	// while there is a user to approve

		log.debug("No user found");
		return -1;
	}	// getApproval

	/**************************************************************************
	 * Execute Work.
	 * Called from MWFProcess.startNext
	 * Feedback to Process via setWFState -> checkActivities
	 */
	@Override
	public void run()
	{
		log.debug("Node={}", getNode());
		m_newValue = null;

		// m_trx = Trx.get(, true);
		Trx trx = null;
		boolean localTrx = false;
		if (get_TrxName() == null)
		{
			this.set_TrxName(Trx.createTrxName("WFA"));
			localTrx = true;
		}

		trx = Trx.get(get_TrxName(), true);

		ITrxSavepoint savepoint = null;

		//
		try
		{
			if (!localTrx)
				savepoint = trx.createTrxSavepoint(null);

			if (!m_state.isValidAction(StateEngine.ACTION_Start))
			{
				setTextMsg("State=" + getWFState() + " - cannot start");
				addTextMsg(new Exception(""));
				setWFState(StateEngine.STATE_Terminated);
				return;
			}
			//
			setWFState(StateEngine.STATE_Running);

			if (getNode().get_ID() == 0)
			{
				setTextMsg("Node not found - AD_WF_Node_ID=" + getAD_WF_Node_ID());
				setWFState(StateEngine.STATE_Aborted);
				return;
			}
			// Do Work
			/**** Trx Start ****/
			boolean done = performWork(Trx.get(get_TrxName(), false));

			/**** Trx End ****/
			// teo_sarca [ 1708835 ]
			// Reason: if the commit fails the document should be put in Invalid state
			if (localTrx)
			{
				try
				{
					trx.commit(true);
				}
				catch (Exception e)
				{
					// If we have a DocStatus, change it to Invalid, and throw the exception to the next level
					if (m_docStatus != null)
						m_docStatus = DocAction.STATUS_Invalid;
					throw e;
				}
			}

			setWFState(done ? StateEngine.STATE_Completed : StateEngine.STATE_Suspended);

			// NOTE: there is no need to postImmediate because DocumentEngine is handling this case (old code was removed from here)
		}
		catch (Exception e)
		{
			log.warn("" + getNode(), e);
			/**** Trx Rollback ****/
			if (localTrx)
			{
				trx.rollback();
			}
			else if (savepoint != null)
			{
				try
				{
					trx.rollback(savepoint);
					savepoint = null;
				}
				catch (Exception e1)
				{
					log.warn("Failed while rolling back to savepoint " + savepoint + ". Going forward...", e1);
				}
			}

			//
			if (e.getCause() != null)
				log.warn("Cause", e.getCause());

			String processMsg = e.getLocalizedMessage();
			if (processMsg == null || processMsg.length() == 0)
				processMsg = e.getMessage();
			setTextMsg(processMsg);
			addTextMsg(e);
			setWFState(StateEngine.STATE_Terminated);	// unlocks
			// Set Document Status
			if (m_po != null && m_po instanceof DocAction && m_docStatus != null)
			{
				m_po.load(get_TrxName());
				DocAction doc = (DocAction)m_po;
				doc.setDocStatus(m_docStatus);
				m_po.save();
			}
		}
		finally
		{
			if (localTrx && trx != null)
			{
				trx.close();
			}
		}
	}	// run

	/**
	 * Perform Work.
	 * Set Text Msg.
	 *
	 * @param trx transaction
	 * @return true if completed, false otherwise
	 * @throws Exception if error
	 */
	private boolean performWork(Trx trx) throws Exception
	{
		log.debug("Performing work for {} [{}]", m_node, trx);
		m_docStatus = null;
		if (m_node.getPriority() != 0)		// overwrite priority if defined
			setPriority(m_node.getPriority());
		String action = m_node.getAction();

		/****** Sleep (Start/End) ******/
		if (MWFNode.ACTION_WaitSleep.equals(action))
		{
			log.debug("Sleep:WaitTime=" + m_node.getWaitTime());
			if (m_node.getWaitingTime() == 0)
				return true;	// done
			Calendar cal = Calendar.getInstance();
			cal.add(m_node.getDurationCalendarField(), m_node.getWaitTime());
			setEndWaitTime(new Timestamp(cal.getTimeInMillis()));
			return false;		// not done
		}

		/****** Document Action ******/
		else if (MWFNode.ACTION_DocumentAction.equals(action))
		{
			log.debug("DocumentAction=" + m_node.getDocAction());
			getPO(trx);
			if (m_po == null)
				throw new Exception("Persistent Object not found - AD_Table_ID="
						+ getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
			boolean success = false;
			String processMsg = null;
			DocAction doc = null;
			if (m_po instanceof DocAction)
			{
				doc = (DocAction)m_po;
				//
				try
				{
					success = doc.processIt(m_node.getDocAction());	// ** Do the work
					setTextMsg(doc.getSummary());
					processMsg = doc.getProcessMsg();
					m_docStatus = doc.getDocStatus();
				}
				catch (Exception e)
				{
					if (m_process != null)
						m_process.setProcessMsg(e.getLocalizedMessage());
					throw e;
				}

				// NOTE: there is no need to postImmediate because DocumentEngine is handling this case (old code was removed from here)

				//
				if (m_process != null)
					m_process.setProcessMsg(processMsg);
			}
			else
				throw new IllegalStateException("Persistent Object not DocAction - "
						+ m_po.getClass().getName()
						+ " - AD_Table_ID=" + getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
			//
			if (!m_po.save())
			{
				success = false;
				processMsg = "SaveError";
			}
			if (!success)
			{
				if (processMsg == null || processMsg.length() == 0)
				{
					processMsg = "PerformWork Error - " + m_node.toStringX();
					if (doc != null)	// problem: status will be rolled back
						processMsg += " - DocStatus=" + doc.getDocStatus();
				}
				throw new Exception(processMsg);
			}
			return success;
		}	// DocumentAction

		/****** Report ******/
		else if (MWFNode.ACTION_AppsReport.equals(action))
		{
			log.debug("Report:AD_Process_ID={}", m_node.getAD_Process_ID());
			// Process
			final I_AD_Process process = m_node.getAD_Process();
			if (!process.isReport())
				throw new IllegalStateException("Not a Report AD_Process_ID=" + m_node.getAD_Process_ID());
			//
			final I_AD_PInstance pInstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(getCtx(), process.getAD_Process_ID(), getAD_Table_ID(), getRecord_ID());
			createPInstanceParameters(pInstance, process, getPO(trx));
			//
			final ProcessInfo pi = ProcessInfo.builder()
					.setCtx(getCtx())
					.setAD_Client_ID(getAD_Client_ID())
					.setAD_User_ID(getAD_User_ID())
					.setAD_PInstance(pInstance)
					.setAD_Process(process)
					.setTitle(m_node.getName(true))
					.setRecord(getAD_Table_ID(), getRecord_ID())
					.build();
			// Report
			ReportEngine re = ReportEngine.get(getCtx(), pi);
			if (re == null)
				throw new IllegalStateException("Cannot create Report AD_Process_ID=" + m_node.getAD_Process_ID());
			File report = re.getPDF();
			// Notice
			int AD_Message_ID = 753;		// HARDCODED WorkflowResult
			MNote note = new MNote(getCtx(), AD_Message_ID, getAD_User_ID(), trx.getTrxName());
			note.setTextMsg(m_node.getName(true));
			note.setDescription(m_node.getDescription(true));
			note.setRecord(getAD_Table_ID(), getRecord_ID());
			note.save();
			// Attachment
			MAttachment attachment = new MAttachment(getCtx(), MNote.Table_ID, note.getAD_Note_ID(), get_TrxName());
			attachment.addEntry(report);
			attachment.setTextMsg(m_node.getName(true));
			attachment.save();
			return true;
		}

		/****** Process ******/
		else if (MWFNode.ACTION_AppsProcess.equals(action))
		{
			log.debug("Process:AD_Process_ID=" + m_node.getAD_Process_ID());
			// Process
			final I_AD_Process process = m_node.getAD_Process();
			//
			final I_AD_PInstance pInstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(getCtx(), process.getAD_Process_ID(), getAD_Table_ID(), getRecord_ID());
			createPInstanceParameters(pInstance, process, getPO(trx));
			//
			final ProcessInfo pi = ProcessInfo.builder()
					.setCtx(getCtx())
					.setAD_Client_ID(getAD_Client_ID())
					.setAD_User_ID(getAD_User_ID())
					.setAD_PInstance(pInstance)
					.setAD_Process_ID(m_node.getAD_Process_ID())
					.setTitle(m_node.getName(true))
					.setRecord(getAD_Table_ID(), getRecord_ID())
					.build();
			
			ProcessExecutor.builder()
					.setProcessInfo(pi)
					.executeSync();
			return true;
		}

		/******
		 * Start Task (Probably redundant;
		 * same can be achieved by attaching a Workflow node sequentially)
		 ******/
		/*
		 * else if (MWFNode.ACTION_AppsTask.equals(action))
		 * {
		 * log.warn("Task:AD_Task_ID=" + m_node.getAD_Task_ID());
		 * log.warn("Start Task is not implemented yet");
		 * }
		 */

		/****** EMail ******/
		else if (MWFNode.ACTION_EMail.equals(action))
		{
			log.debug("EMail:EMailRecipient=" + m_node.getEMailRecipient());
			getPO(trx);
			if (m_po == null)
				throw new Exception("Persistent Object not found - AD_Table_ID="
						+ getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
			if (m_po instanceof DocAction)
			{
				m_emails = new ArrayList<String>();
				sendEMail();
				setTextMsg(m_emails.toString());
			}
			else
			{
				final IMailBL mailBL = Services.get(IMailBL.class);
				final IMailTextBuilder mailTextBuilder = mailBL.newMailTextBuilder(getNode().getR_MailText());
				mailTextBuilder.setRecord(m_po, true); // metas: tsa

				// metas: tsa: check for null strings
				StringBuffer subject = new StringBuffer();
				if (!Check.isEmpty(getNode().getDescription(), true))
					subject.append(getNode().getDescription());
				if (!Check.isEmpty(mailTextBuilder.getMailHeader(), true))
				{
					if (subject.length() > 0)
						subject.append(": ");
					subject.append(mailTextBuilder.getMailHeader());
				}

				// metas: tsa: check for null strings
				StringBuffer message = new StringBuffer(mailTextBuilder.getFullMailText());
				if (!Check.isEmpty(getNodeHelp(), true))
					message.append("\n-----\n").append(getNodeHelp());
				String to = getNode().getEMail();

				final MClient client = MClient.get(getCtx(), getAD_Client_ID());
				client.sendEMail(to, subject.toString(), message.toString(), null);
			}
			return true;	// done
		}	// EMail

		/****** Set Variable ******/
		else if (MWFNode.ACTION_SetVariable.equals(action))
		{
			String value = m_node.getAttributeValue();
			log.debug("SetVariable:AD_Column_ID=" + m_node.getAD_Column_ID()
					+ " to " + value);
			MColumn column = m_node.getColumn();
			int dt = column.getAD_Reference_ID();
			return setVariable(value, dt, null, trx);
		}	// SetVariable

		/****** TODO Start WF Instance ******/
		else if (MWFNode.ACTION_SubWorkflow.equals(action))
		{
			log.warn("Workflow:AD_Workflow_ID=" + m_node.getAD_Workflow_ID());
			log.warn("Start WF Instance is not implemented yet");
		}

		/****** User Choice ******/
		else if (MWFNode.ACTION_UserChoice.equals(action))
		{
			log.debug("UserChoice:AD_Column_ID=" + m_node.getAD_Column_ID());
			// Approval
			if (m_node.isUserApproval()
					&& getPO(trx) instanceof DocAction)
			{
				DocAction doc = (DocAction)m_po;
				boolean autoApproval = false;
				// Approval Hierarchy
				if (isInvoker())
				{
					// Set Approver
					int startAD_User_ID = getAD_User_ID();
					if (startAD_User_ID == 0)
						startAD_User_ID = doc.getDoc_User_ID();
					int nextAD_User_ID = getApprovalUser(startAD_User_ID,
							doc.getC_Currency_ID(), doc.getApprovalAmt(),
							doc.getAD_Org_ID(),
							startAD_User_ID == doc.getDoc_User_ID());	// own doc
					// same user = approved
					autoApproval = startAD_User_ID == nextAD_User_ID;
					if (!autoApproval)
						setAD_User_ID(nextAD_User_ID);
				}
				else
				// fixed Approver
				{
					MWFResponsible resp = getResponsible();
					// MZ Goodwill
					// [ 1742751 ] Workflow: User Choice is not working
					if (resp.isHuman())
					{
						autoApproval = resp.getAD_User_ID() == m_process.getAD_User_ID();
						if (!autoApproval && resp.getAD_User_ID() != 0)
							setAD_User_ID(resp.getAD_User_ID());
					}
					else if (resp.isRole())
					{
						MUserRoles[] urs = MUserRoles.getOfRole(getCtx(), resp.getAD_Role_ID());
						for (int i = 0; i < urs.length; i++)
						{
							if (urs[i].getAD_User_ID() == m_process.getAD_User_ID())
							{
								autoApproval = true;
								break;
							}
						}
					}
					else if (resp.isOrganization())
					{
						throw new AdempiereException("Support not implemented for " + resp);
					}
					else
					{
						throw new AdempiereException("@NotSupported@ " + resp);
					}
					// end MZ
				}
				if (autoApproval
						&& doc.processIt(DocAction.ACTION_Approve)
						&& doc.save())
					return true;	// done
			}	// approval
			return false;	// wait for user
		}
		/****** User Form ******/
		else if (MWFNode.ACTION_UserForm.equals(action))
		{
			log.debug("Form:AD_Form_ID=" + m_node.getAD_Form_ID());
			return false;
		}
		/****** User Window ******/
		else if (MWFNode.ACTION_UserWindow.equals(action))
		{
			log.debug("Window:AD_Window_ID=" + m_node.getAD_Window_ID());
			return false;
		}
		//
		throw new IllegalArgumentException("Invalid Action (Not Implemented) =" + action);
	}	// performWork

	/**
	 * Set Variable
	 *
	 * @param value new Value
	 * @param displayType display type
	 * @param textMsg optional Message
	 * @return true if set
	 * @throws Exception if error
	 */
	private boolean setVariable(String value, int displayType, String textMsg, Trx trx) throws Exception
	{
		m_newValue = null;
		getPO(trx);
		if (m_po == null)
			throw new Exception("Persistent Object not found - AD_Table_ID="
					+ getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
		// Set Value
		Object dbValue = null;
		if (value == null)
			;
		else if (displayType == DisplayType.YesNo)
			dbValue = new Boolean("Y".equals(value));
		else if (DisplayType.isNumeric(displayType))
			dbValue = new BigDecimal(value);
		else
			dbValue = value;
		m_po.set_ValueOfAD_Column_ID(getNode().getAD_Column_ID(), dbValue);
		m_po.save();
		if (dbValue != null && !dbValue.equals(m_po.get_ValueOfColumn(getNode().getAD_Column_ID())))
			throw new Exception("Persistent Object not updated - AD_Table_ID="
					+ getAD_Table_ID() + ", Record_ID=" + getRecord_ID()
					+ " - Should=" + value + ", Is=" + m_po.get_ValueOfColumn(m_node.getAD_Column_ID()));
		// Info
		String msg = getNode().getAttributeName() + "=" + value;
		if (textMsg != null && textMsg.length() > 0)
			msg += " - " + textMsg;
		setTextMsg(msg);
		m_newValue = value;
		return true;
	}	// setVariable

	/**
	 * Set User Choice
	 *
	 * @param AD_User_ID user
	 * @param value new Value
	 * @param displayType display type
	 * @param textMsg optional Message
	 * @return true if set
	 * @throws Exception if error
	 */
	public boolean setUserChoice(int AD_User_ID, String value, int displayType,
			String textMsg) throws Exception
	{
		// Check if user approves own document when a role is reponsible
		/*
		 * 2007-06-08, matthiasO.
		 * The following sequence makes sure that only users in roles which
		 * have the 'Approve own document flag' set can set the user choice
		 * of 'Y' (approve) or 'N' (reject).
		 * IMHO this is against the meaning of 'Approve own document': Why
		 * should a user who is faced with the task of approving documents
		 * generally be required to have the ability to approve his OWN
		 * documents? If the document to approve really IS his own document
		 * this will be respected when trying to find an approval user in
		 * the call to getApprovalUser(...) below.
		 */
		/*
		 * if (getNode().isUserApproval() && getPO() instanceof DocAction)
		 * {
		 * DocAction doc = (DocAction)m_po;
		 * MUser user = new MUser (getCtx(), AD_User_ID, null);
		 * MRole[] roles = user.getRoles(m_po.getAD_Org_ID());
		 * boolean canApproveOwnDoc = false;
		 * for (int r = 0; r < roles.length; r++)
		 * {
		 * if (roles[r].isCanApproveOwnDoc())
		 * {
		 * canApproveOwnDoc = true;
		 * break;
		 * } // found a role which allows to approve own document
		 * }
		 * if (!canApproveOwnDoc)
		 * {
		 * String info = user.getName() + " cannot approve own document " + doc;
		 * addTextMsg(info);
		 * log.debug(info);
		 * return false; // ignore
		 * }
		 * }
		 */

		setWFState(StateEngine.STATE_Running);
		setAD_User_ID(AD_User_ID);
		Trx trx = (get_TrxName() != null) ? Trx.get(get_TrxName(), false) : null;
		boolean ok = setVariable(value, displayType, textMsg, trx);
		if (!ok)
			return false;

		String newState = StateEngine.STATE_Completed;
		// Approval
		if (getNode().isUserApproval() && getPO(trx) instanceof DocAction)
		{
			DocAction doc = (DocAction)m_po;
			try
			{
				// Not approved
				if (!"Y".equals(value))
				{
					newState = StateEngine.STATE_Aborted;
					if (!(doc.processIt(DocAction.ACTION_Reject)))
						setTextMsg("Cannot Reject - Document Status: " + doc.getDocStatus());
				}
				else
				{
					if (isInvoker())
					{
						int startAD_User_ID = getAD_User_ID();
						if (startAD_User_ID == 0)
							startAD_User_ID = doc.getDoc_User_ID();
						int nextAD_User_ID = getApprovalUser(startAD_User_ID,
								doc.getC_Currency_ID(), doc.getApprovalAmt(),
								doc.getAD_Org_ID(),
								startAD_User_ID == doc.getDoc_User_ID());	// own doc
						// No Approver
						if (nextAD_User_ID <= 0)
						{
							newState = StateEngine.STATE_Aborted;
							setTextMsg("Cannot Approve - No Approver");
							doc.processIt(DocAction.ACTION_Reject);
						}
						else if (startAD_User_ID != nextAD_User_ID)
						{
							forwardTo(nextAD_User_ID, "Next Approver");
							newState = StateEngine.STATE_Suspended;
						}
						else
						// Approve
						{
							if (!(doc.processIt(DocAction.ACTION_Approve)))
							{
								newState = StateEngine.STATE_Aborted;
								setTextMsg("Cannot Approve - Document Status: " + doc.getDocStatus());
							}
						}
					}
					// No Invoker - Approve
					else if (!(doc.processIt(DocAction.ACTION_Approve)))
					{
						newState = StateEngine.STATE_Aborted;
						setTextMsg("Cannot Approve - Document Status: " + doc.getDocStatus());
					}
				}
				doc.save();
			}
			catch (Exception e)
			{
				newState = StateEngine.STATE_Terminated;
				setTextMsg("User Choice: " + e.toString());
				addTextMsg(e);
				log.warn("", e);
			}
			// Send Approval Notification
			if (newState.equals(StateEngine.STATE_Aborted))
			{
				MUser to = new MUser(getCtx(), doc.getDoc_User_ID(), null);
				final IUserBL userBL = Services.get(IUserBL.class);
				// send email
				if (userBL.isNotificationEMail(to))
				{
					MClient client = MClient.get(getCtx(), doc.getAD_Client_ID());
					client.sendEMail(doc.getDoc_User_ID(), Services.get(IMsgBL.class).getMsg(getCtx(), "NotApproved")
							+ ": " + doc.getDocumentNo(),
							(doc.getSummary() != null ? doc.getSummary() + "\n" : "")
									+ (doc.getProcessMsg() != null ? doc.getProcessMsg() + "\n" : "")
									+ (getTextMsg() != null ? getTextMsg() : ""), null);
				}

				// Send Note
				if (userBL.isNotificationNote(to))
				{
					MNote note = new MNote(getCtx(), "NotApproved", doc.getDoc_User_ID(), null);
					note.setTextMsg((doc.getSummary() != null ? doc.getSummary() + "\n" : "")
							+ (doc.getProcessMsg() != null ? doc.getProcessMsg() + "\n" : "")
							+ (getTextMsg() != null ? getTextMsg() : ""));
					// 2007-06-08, matthiasO.
					// Add record information to the note, so that the user receiving the
					// note can jump to the doc easily
					note.setRecord(m_po.get_Table_ID(), m_po.get_ID());
					note.save();
				}
			}
		}
		setWFState(newState);
		return ok;
	}	// setUserChoice

	/**
	 * Forward To
	 *
	 * @param AD_User_ID user
	 * @param textMsg text message
	 * @return true if forwarded
	 */
	public boolean forwardTo(int AD_User_ID, String textMsg)
	{
		if (AD_User_ID == getAD_User_ID())
		{
			log.warn("Same User - AD_User_ID=" + AD_User_ID);
			return false;
		}
		//
		MUser oldUser = MUser.get(getCtx(), getAD_User_ID());
		MUser user = MUser.get(getCtx(), AD_User_ID);
		if (user == null || user.get_ID() == 0)
		{
			log.warn("Does not exist - AD_User_ID=" + AD_User_ID);
			return false;
		}
		// Update
		setAD_User_ID(user.getAD_User_ID());
		setTextMsg(textMsg);
		save();
		// Close up Old Event
		getEventAudit();
		m_audit.setAD_User_ID(oldUser.getAD_User_ID());
		m_audit.setTextMsg(getTextMsg());
		m_audit.setAttributeName("AD_User_ID");
		m_audit.setOldValue(oldUser.getName() + "(" + oldUser.getAD_User_ID() + ")");
		m_audit.setNewValue(user.getName() + "(" + user.getAD_User_ID() + ")");
		//
		m_audit.setWFState(getWFState());
		m_audit.setEventType(MWFEventAudit.EVENTTYPE_StateChanged);
		long ms = System.currentTimeMillis() - m_audit.getCreated().getTime();
		m_audit.setElapsedTimeMS(new BigDecimal(ms));
		m_audit.save();
		// Create new one
		m_audit = new MWFEventAudit(this);
		m_audit.save();
		return true;
	}	// forwardTo

	/**
	 * Set User Confirmation
	 *
	 * @param AD_User_ID user
	 * @param textMsg optional message
	 */
	public void setUserConfirmation(int AD_User_ID, String textMsg)
	{
		log.debug(textMsg);
		setWFState(StateEngine.STATE_Running);
		setAD_User_ID(AD_User_ID);
		if (textMsg != null)
			setTextMsg(textMsg);
		setWFState(StateEngine.STATE_Completed);
	}	// setUserConfirmation

	
	private void createPInstanceParameters(final I_AD_PInstance adPInstance, final I_AD_Process adProcess, final PO po)
	{
		final Map<String, MWFNodePara> attributeName2wfNodePara = Stream.of(m_node.getParameters())
				.collect(GuavaCollectors.toImmutableMapByKey(MWFNodePara::getAttributeName));
		
		int seqNo = 10; // metas
		for (final I_AD_Process_Para para : Services.get(IADProcessDAO.class).retrieveProcessParameters(adProcess))
		{
			I_AD_PInstance_Para pip = new MPInstancePara(adPInstance, seqNo);
			pip.setParameterName(para.getColumnName());
			pip.setInfo(para.getName());
			InterfaceWrapperHelper.save(pip);
			seqNo += 10; // metas

			final MWFNodePara wfNodePara = attributeName2wfNodePara.get(pip.getParameterName());
			if(wfNodePara != null)
			{
				fillPInstancePara(pip, wfNodePara, po);
			}
		}
		
	}
	
	private final void fillPInstancePara(final I_AD_PInstance_Para iPara, final MWFNodePara nPara, final PO po)
	{
		final String attributeName = nPara.getAttributeName();
		
		final String variableName = nPara.getAttributeValue();
		log.debug(attributeName + " = " + variableName);
		// Value - Constant/Variable
		Object value = variableName;
		if (variableName == null || (variableName != null && variableName.length() == 0))
			value = null;
		else if (variableName.indexOf('@') != -1 && po != null)	// we have a variable
		{
			// Strip
			int index = variableName.indexOf('@');
			String columnName = variableName.substring(index + 1);
			index = columnName.indexOf('@');
			if (index == -1)
			{
				log.warn(attributeName + " - cannot evaluate=" + variableName);
				return;
			}
			columnName = columnName.substring(0, index);
			index = po.get_ColumnIndex(columnName);
			if (index != -1)
			{
				value = po.get_Value(index);
			}
			else
			// not a column
			{
				// try Env
				String env = Env.getContext(getCtx(), columnName);
				if (env.length() == 0)
				{
					log.warn(attributeName + " - not column nor environment =" + columnName + "(" + variableName + ")");
					return;
				}
				else
					value = env;
			}
		}	// @variable@

		// No Value
		if (value == null)
		{
			if (nPara.isMandatory())
				log.warn(attributeName + " - empty - mandatory!");
			else
				log.debug(attributeName + " - empty");
			return;
		}

		// Convert to Type
		try
		{
			if (DisplayType.isNumeric(nPara.getDisplayType())
					|| DisplayType.isID(nPara.getDisplayType()))
			{
				BigDecimal bd = null;
				if (value instanceof BigDecimal)
					bd = (BigDecimal)value;
				else if (value instanceof Integer)
					bd = new BigDecimal(((Integer)value).intValue());
				else
					bd = new BigDecimal(value.toString());
				iPara.setP_Number(bd);
				log.debug(attributeName + " = " + variableName + " (=" + bd + "=)");
			}
			else if (DisplayType.isDate(nPara.getDisplayType()))
			{
				Timestamp ts = null;
				if (value instanceof Timestamp)
					ts = (Timestamp)value;
				else
					ts = Timestamp.valueOf(value.toString());
				iPara.setP_Date(ts);
				log.debug(attributeName + " = " + variableName + " (=" + ts + "=)");
			}
			else
			{
				iPara.setP_String(value.toString());
				log.debug(attributeName + " = " + variableName + " (=" + value + "=) " + value.getClass().getName());
			}

			InterfaceWrapperHelper.save(iPara);
		}
		catch (Exception e)
		{
			log.warn(attributeName + " = " + variableName + " (" + value + ") " + value.getClass().getName() + " - " + e.getLocalizedMessage());
		}
	}

	/*********************************
	 * Send EMail
	 */
	private void sendEMail()
	{
		DocAction doc = (DocAction)m_po;
		
		final IMailBL mailBL = Services.get(IMailBL.class);
		final IMailTextBuilder mailTextBuilder = mailBL.newMailTextBuilder(m_node.getR_MailText());
		mailTextBuilder.setRecord(m_po, true);
		//
		String subject = doc.getDocumentInfo()
				+ ": " + mailTextBuilder.getMailHeader();
		String message = mailTextBuilder.getFullMailText()
				+ "\n-----\n" + doc.getDocumentInfo()
				+ "\n" + doc.getSummary();
		File pdf = doc.createPDF();
		//
		MClient client = MClient.get(doc.getCtx(), doc.getAD_Client_ID());

		// Explicit EMail
		sendEMail(client, 0, m_node.getEMail(), subject, message, pdf, mailTextBuilder.isHtml());
		// Recipient Type
		String recipient = m_node.getEMailRecipient();
		// email to document user
		if (recipient == null || recipient.length() == 0)
			sendEMail(client, doc.getDoc_User_ID(), null, subject, message, pdf, mailTextBuilder.isHtml());
		else if (recipient.equals(MWFNode.EMAILRECIPIENT_DocumentBusinessPartner))
		{
			int index = m_po.get_ColumnIndex("AD_User_ID");
			if (index > 0)
			{
				Object oo = m_po.get_Value(index);
				if (oo instanceof Integer)
				{
					int AD_User_ID = ((Integer)oo).intValue();
					if (AD_User_ID != 0)
						sendEMail(client, AD_User_ID, null, subject, message, pdf, mailTextBuilder.isHtml());
					else
						log.debug("No User in Document");
				}
				else
					log.debug("Empty User in Document");
			}
			else
				log.debug("No User Field in Document");
		}
		else if (recipient.equals(MWFNode.EMAILRECIPIENT_DocumentOwner))
			sendEMail(client, doc.getDoc_User_ID(), null, subject, message, pdf, mailTextBuilder.isHtml());
		else if (recipient.equals(MWFNode.EMAILRECIPIENT_WFResponsible))
		{
			MWFResponsible resp = getResponsible();
			if (resp.isInvoker())
				sendEMail(client, doc.getDoc_User_ID(), null, subject, message, pdf, mailTextBuilder.isHtml());
			else if (resp.isHuman())
				sendEMail(client, resp.getAD_User_ID(), null, subject, message, pdf, mailTextBuilder.isHtml());
			else if (resp.isRole())
			{
				final I_AD_Role role = resp.getRole();
				if (role != null)
				{
					for (final I_AD_User user : MUser.getWithRole(role))
					{
						sendEMail(client, user.getAD_User_ID(), null, subject, message, pdf, mailTextBuilder.isHtml());
					}
				}
			}
			else if (resp.isOrganization())
			{
				MOrgInfo org = MOrgInfo.get(getCtx(), m_po.getAD_Org_ID());
				if (org.getSupervisor_ID() <= 0)
					log.debug("No Supervisor for AD_Org_ID=" + m_po.getAD_Org_ID());
				else
					sendEMail(client, org.getSupervisor_ID(), null, subject, message, pdf, mailTextBuilder.isHtml());
			}
		}
	}	// sendEMail

	/**
	 * Send actual EMail
	 *
	 * @param client client
	 * @param AD_User_ID user
	 * @param email email string
	 * @param subject subject
	 * @param message message
	 * @param pdf attachment
	 * @param isHtml isHtml
	 */
	private void sendEMail(MClient client, int AD_User_ID, String email,
			String subject, String message, File pdf, boolean isHtml)
	{
		if (AD_User_ID != 0)
		{
			MUser user = MUser.get(getCtx(), AD_User_ID);
			email = user.getEMail();
			if (email != null && email.length() > 0)
			{
				email = email.trim();
				if (!m_emails.contains(email))
				{
					client.sendEMail(null, user, subject, message, pdf, isHtml);
					m_emails.add(email);
				}
			}
			else
			{
				log.debug("No EMail for User {}", user.getName());
			}
		}
		else if (email != null && email.length() > 0)
		{
			// Just one
			if (email.indexOf(';') == -1)
			{
				email = email.trim();
				if (!m_emails.contains(email))
				{
					client.sendEMail(email, subject, message, pdf, isHtml);
					m_emails.add(email);
				}
				return;
			}
			// Multiple EMail
			StringTokenizer st = new StringTokenizer(email, ";");
			while (st.hasMoreTokens())
			{
				String email1 = st.nextToken().trim();
				if (email1.length() == 0)
					continue;
				if (!m_emails.contains(email1))
				{
					client.sendEMail(email1, subject, message, pdf, isHtml);
					m_emails.add(email1);
				}
			}
		}
	}	// sendEMail

	/**************************************************************************
	 * Get Process Activity (Event) History
	 *
	 * @return history
	 */
	public String getHistoryHTML()
	{
		SimpleDateFormat format = DisplayType.getDateFormat(DisplayType.DateTime);
		StringBuffer sb = new StringBuffer();
		MWFEventAudit[] events = MWFEventAudit.get(getCtx(), getAD_WF_Process_ID(), get_TrxName());
		for (int i = 0; i < events.length; i++)
		{
			MWFEventAudit audit = events[i];
			// sb.append("<p style=\"width:400\">");
			sb.append("<p>");
			sb.append(format.format(audit.getCreated()))
					.append(" ")
					.append(getHTMLpart("b", audit.getNodeName()))
					.append(": ")
					.append(getHTMLpart(null, audit.getDescription()))
					.append(getHTMLpart("i", audit.getTextMsg()));
			sb.append("</p>");
		}
		return sb.toString();
	}	// getHistory

	/**
	 * Get HTML part
	 *
	 * @param tag HTML tag
	 * @param content content
	 * @return <tag>content</tag>
	 */
	private StringBuffer getHTMLpart(String tag, String content)
	{
		StringBuffer sb = new StringBuffer();
		if (content == null || content.length() == 0)
			return sb;
		if (tag != null && tag.length() > 0)
			sb.append("<").append(tag).append(">");
		sb.append(content);
		if (tag != null && tag.length() > 0)
			sb.append("</").append(tag).append(">");
		return sb;
	}	// getHTMLpart

	/**************************************************************************
	 * Does the underlying PO (!) object have a PDF Attachment
	 *
	 * @return true if there is a pdf attachment
	 */
	@Override
	public boolean isPdfAttachment()
	{
		if (getPO() == null)
			return false;
		return m_po.isPdfAttachment();
	}	// isPDFAttachment

	/**
	 * Get PDF Attachment of underlying PO (!) object
	 *
	 * @return pdf data or null
	 */
	@Override
	public byte[] getPdfAttachment()
	{
		if (getPO() == null)
			return null;
		return m_po.getPdfAttachment();
	}	// getPdfAttachment

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MWFActivity[");
		sb.append(get_ID()).append(",Node=");
		if (m_node == null)
			sb.append(getAD_WF_Node_ID());
		else
			sb.append(m_node.getName());
		sb.append(",State=").append(getWFState())
				.append(",AD_User_ID=").append(getAD_User_ID())
				.append(",").append(getCreated())
				.append("]");
		return sb.toString();
	} 	// toString

	/**
	 * User String Representation.
	 * Suspended: Approve it (Joe)
	 *
	 * @return info
	 */
	public String toStringX()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getWFStateText())
				.append(": ").append(getNode().getName());
		if (getAD_User_ID() > 0)
		{
			MUser user = MUser.get(getCtx(), getAD_User_ID());
			sb.append(" (").append(user.getName()).append(")");
		}
		return sb.toString();
	}	// toStringX

	/**
	 * Get Document Summary
	 *
	 * @return PO Summary
	 */
	public String getSummary()
	{
		PO po = getPO();
		if (po == null)
			return null;
		StringBuffer sb = new StringBuffer();
		String[] keyColumns = po.get_KeyColumns();
		if ((keyColumns != null) && (keyColumns.length > 0))
			sb.append(Services.get(IMsgBL.class).translate(getCtx(), keyColumns[0])).append(" ");
		int index = po.get_ColumnIndex("DocumentNo");
		if (index != -1)
			sb.append(po.get_Value(index)).append(": ");
		index = po.get_ColumnIndex("SalesRep_ID");
		Integer sr = null;
		if (index != -1)
			sr = (Integer)po.get_Value(index);
		else
		{
			index = po.get_ColumnIndex("AD_User_ID");
			if (index != -1)
				sr = (Integer)po.get_Value(index);
		}
		if (sr != null)
		{
			MUser user = MUser.get(getCtx(), sr.intValue());
			if (user != null)
				sb.append(user.getName()).append(" ");
		}
		//
		index = po.get_ColumnIndex("C_BPartner_ID");
		if (index != -1)
		{
			Integer bp = (Integer)po.get_Value(index);
			if (bp != null)
			{
				MBPartner partner = MBPartner.get(getCtx(), bp.intValue());
				if (partner != null)
					sb.append(partner.getName()).append(" ");
			}
		}
		return sb.toString();
	}	// getSummary

}	// MWFActivity
