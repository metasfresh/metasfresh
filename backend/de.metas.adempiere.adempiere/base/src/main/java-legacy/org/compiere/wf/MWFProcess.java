package org.compiere.wf;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.organization.OrgId;
import de.metas.process.ProcessInfo;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.time.SystemTime;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeSplitType;
import de.metas.workflow.WFNodeTransition;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_WF_Activity;
import org.compiere.model.PO;
import org.compiere.model.X_AD_WF_Process;
import org.compiere.process.StateEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import de.metas.workflow.service.IADWorkflowDAO;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.Optional;
import java.util.Properties;

/**
 * Workflow Process
 *
 * @author Jorg Janke
 * @version $Id: MWFProcess.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFProcess extends X_AD_WF_Process
{
	private Workflow _workflow = null; // lazy
	private PO _po = null; // lazy
	private String _processMsg = null;

	public MWFProcess(final Properties ctx, final int AD_WF_Process_ID)
	{
		this(ctx, AD_WF_Process_ID, ITrx.TRXNAME_None);
	}

	@Deprecated
	public MWFProcess(final Properties ctx, final int AD_WF_Process_ID, final String trxName)
	{
		super(ctx, AD_WF_Process_ID, trxName);
		if (is_new())
		{
			throw new AdempiereException("Cannot create new WF Process directly");
		}
	}

	public MWFProcess(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MWFProcess(final Workflow workflow, final ProcessInfo pi)
	{
		super(Env.getCtx(), 0, ITrx.TRXNAME_None); // always out of trx

		// WF Process should be started using process's AD_Client_ID
		if (pi.getAD_Client_ID() >= 0 && pi.getAD_Client_ID() != getAD_Client_ID())
		{
			setClientOrg(pi.getAD_Client_ID(), OrgId.ANY.getRepoId());
		}

		final Instant now = SystemTime.asInstant();
		if (!TimeUtil.isBetween(now, workflow.getValidFrom(), workflow.getValidTo()))
		{
			throw new AdempiereException("Workflow not valid on " + now);
		}

		this._workflow = workflow;
		setAD_Workflow_ID(workflow.getId().getRepoId());
		setPriority(workflow.getPriority());
		super.setWFState(WFSTATE_NotStarted);

		//	Document
		setAD_Table_ID(pi.getTable_ID());
		setRecord_ID(pi.getRecord_ID());
		if (getPO() == null)
		{
			addTextMsg(new AdempiereException("No PO with ID=" + pi.getRecord_ID()));
			super.setWFState(WFSTATE_Terminated);
		}
		else
		{
			addTextMsg(getPO());
		}

		//	Responsible/User
		if (workflow.getResponsibleId() == null)
		{
			setAD_WF_Responsible_ID();
		}
		else
		{
			setAD_WF_Responsible_ID(workflow.getResponsibleId().getRepoId());
		}

		setAD_User_ID(pi.getUserId().getRepoId()); //	user starting

		//
		setProcessed(false);

		//	Lock Entity
		//getPO();
		//hengsin: remove lock/unlock which is causing deadlock
		//if (m_po != null)
		//m_po.lock();
	}    //	MWFProcess

	private ImmutableList<MWFActivity> getActiveActivities()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Activity.class)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_AD_WF_Process_ID, getAD_WF_Process_ID())
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_Processed, false)
				.orderBy(I_AD_WF_Activity.COLUMNNAME_AD_WF_Activity_ID)
				.create()
				.listImmutable(MWFActivity.class);
	}

	public Optional<I_AD_WF_Activity> getFirstActivityByWFState(@NonNull final StateEngine wfState)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Activity.class)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_AD_WF_Process_ID, getAD_WF_Process_ID())
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_WFState, wfState.getCode())
				.orderBy(I_AD_WF_Activity.COLUMNNAME_AD_WF_Activity_ID)
				.create()
				.firstOptional(I_AD_WF_Activity.class);
	}

	public StateEngine getState()
	{
		return StateEngine.ofCode(getWFState());
	}

	@Override
	@Deprecated
	public void setWFState(final String WFState)
	{
		changeWFStateTo(WFState);
	}

	/**
	 * Set Process State and update Actions
	 */
	public void changeWFStateTo(final String WFState)
	{
		if (getState().isClosed())
		{
			return;
		}

		if (getWFState().equals(WFState))
		{
			return;
		}

		//
		if (getState().isValidNewState(WFState))
		{
			log.debug("Set WFState={}", WFState);
			super.setWFState(WFState);
			if (getState().isClosed())
			{
				setProcessed(true);
			}

			saveEx();

			//	Force close to all Activities
			if (getState().isClosed())
			{
				for (final MWFActivity activity : getActiveActivities())
				{
					if (!activity.isClosed())
					{
						activity.addTextMsg("Process:" + WFState);
						activity.changeWFStateTo(WFState);
					}
					if (!activity.isProcessed())
					{
						activity.setProcessed(true);
					}
					activity.saveEx();
				}
			}    //	closed
		}
		else
		{
			log.warn("Ignored invalid state transition {} -> {}", getWFState(), WFState);
		}
	}

	/**************************************************************************
	 * 	Check Status of Activities.
	 * 	- update Process if required
	 * 	- start new activity
	 */
	void checkActivities(@Nullable final PO lastPO)
	{
		if (getState().isClosed())
		{
			return;
		}

		if (lastPO != null && lastPO.get_ID() == this.getRecord_ID())
		{
			this._po = lastPO;
		}

		//
		String closedState = null;
		boolean suspended = false;
		boolean running = false;

		final ImmutableList<MWFActivity> activities = getActiveActivities();
		if (activities.isEmpty())
		{
			addTextMsg(new AdempiereException("No Active Processed found"));
			closedState = WFSTATE_Terminated;
		}
		else
		{
			for (final MWFActivity activity : activities)
			{
				final StateEngine activityState = activity.getState();

				//	Completed - Start Next
				if (activityState.isCompleted())
				{
					if (startNextActivity(activity, lastPO))
					{
						continue;
					}
				}

				//
				final String activityWFState = activity.getWFState();
				if (activityState.isClosed())
				{
					//	eliminate from active processed
					activity.setProcessed(true);
					activity.saveEx();
					//
					if (closedState == null)
					{
						closedState = activityWFState;
					}
					else if (!closedState.equals(activityWFState))
					{
						//	Overwrite if terminated
						if (WFSTATE_Terminated.equals(activityWFState))
						{
							closedState = activityWFState;
						}
						//	Overwrite if activity aborted and no other terminated
						else if (WFSTATE_Aborted.equals(activityWFState) && !WFSTATE_Terminated.equals(closedState))
						{
							closedState = activityWFState;
						}
					}
				}
				else    //	not closed
				{
					closedState = null;        //	all need to be closed
					if (activityState.isSuspended())
						suspended = true;
					if (activityState.isRunning())
						running = true;
				}
			}    //	for all activities
		}

		//
		// Update workflow process state
		if (closedState != null)
		{
			changeWFStateTo(closedState);
			//getPO();
			//hengsin: remmove lock/unlock in workflow which is causing deadlock in many place
			//if (m_po != null)
			//m_po.unlock(null);
		}
		else if (suspended)
		{
			changeWFStateTo(WFSTATE_Suspended);
		}
		else if (running)
		{
			changeWFStateTo(WFSTATE_Running);
		}
	}    //	checkActivities

	/**
	 * Start Next Activity
	 *
	 * @return true if there is a next activity
	 */
	private boolean startNextActivity(
			@NonNull final MWFActivity lastActivity,
			@Nullable final PO lastPO)
	{
		log.debug("Last activity: {}", lastActivity);

		//	transitions from the last processed node
		final ImmutableList<WFNodeTransition> transitions = getWorkflow().getTransitionsFromNode(
				WFNodeId.ofRepoId(lastActivity.getAD_WF_Node_ID()),
				lastActivity.getPO_AD_Client_ID().orElse(ClientId.SYSTEM));
		if (transitions.isEmpty())
		{
			return false;    //	done
		}

		//	We need to wait for last activity
		//if (lastActivity.getNode().getJoinType().isAnd())
		//{
		//	get previous nodes
		//	check if all have closed activities
		//	return false for all but the last
		//}

		//	eliminate from active processed
		lastActivity.setProcessed(true);
		lastActivity.saveEx();

		//	Start next activity
		final WFNodeSplitType splitType = lastActivity.getNode().getSplitType();
		for (final WFNodeTransition transition : transitions)
		{
			//	Is this a valid transition?
			if (!transition.isValidFor(lastActivity))
				continue;

			//	Start new Activity...
			final MWFActivity activity = new MWFActivity(this, transition.getNextNodeId(), lastPO);
			activity.run();

			//	only the first valid if XOR
			if (splitType.isXOR())
			{
				return true;
			}
		}    //	for all transitions

		return true;
	}    //	startNext

	/**************************************************************************
	 * 	Set Workflow Responsible.
	 * 	Searches for a Invoker.
	 */
	private void setAD_WF_Responsible_ID()
	{
		final int AD_WF_Responsible_ID = DB.getSQLValueEx(ITrx.TRXNAME_None,
				Env.getUserRolePermissions(getCtx()).addAccessSQL(
						"SELECT AD_WF_Responsible_ID FROM AD_WF_Responsible "
								+ "WHERE ResponsibleType='H' AND COALESCE(AD_User_ID,0)=0 "
								+ "ORDER BY AD_Client_ID DESC",
						"AD_WF_Responsible", IUserRolePermissions.SQL_NOTQUALIFIED, Access.READ));
		setAD_WF_Responsible_ID(AD_WF_Responsible_ID);
	}    //	setAD_WF_Responsible_ID

	private Workflow getWorkflow()
	{
		Workflow workflow = this._workflow;
		if (workflow == null)
		{
			final WorkflowId workflowId = WorkflowId.ofRepoId(getAD_Workflow_ID());
			workflow = Services.get(IADWorkflowDAO.class).getById(workflowId);
			this._workflow = workflow;
		}
		return workflow;
	}

	/**
	 * Start WF Execution
	 */
	public void startWork()
	{
		if (!getState().isValidAction(StateEngine.ACTION_Start))
		{
			log.warn("State={} - cannot start", getWFState());
			return;
		}

		final WFNodeId firstWFNodeId = getWorkflow().getFirstNodeId();
		changeWFStateTo(WFSTATE_Running);
		try
		{
			//	Start first Activity with first Node
			final MWFActivity firstActivity = new MWFActivity(this, firstWFNodeId);
			firstActivity.run();
		}
		catch (final Throwable ex)
		{
			log.error("firstWFNodeId={}", firstWFNodeId, ex);
			setProcessMsg(ex);
			changeWFStateTo(StateEngine.STATE_Terminated);
		}
	}

	@Nullable
	public PO getPO()
	{
		PO po = this._po;
		if (po == null)
		{
			final int adTableId = getAD_Table_ID();
			final int recordId = getRecord_ID();
			if (adTableId <= 0 || recordId <= 0)
			{
				return null;
			}
			po = this._po = TableModelLoader.instance.getPO(getCtx(), adTableId, recordId, ITrx.TRXNAME_ThreadInherited);
		}
		return po;
	}

	private void addTextMsg(final PO po)
	{
		final IDocument document = po != null ? Services.get(IDocumentBL.class).getDocumentOrNull(po) : null;
		if (document != null)
		{
			addTextMsg(document.getSummary());
		}
	}

	void addTextMsg(@Nullable final String textMsg)
	{
		if (Check.isBlank(textMsg))
		{
			return;
		}

		final String oldText = StringUtils.trimBlankToNull(getTextMsg());
		if (oldText == null)
		{
			super.setTextMsg(textMsg.trim());
		}
		else
		{
			super.setTextMsg(oldText + "\n" + textMsg.trim());
		}
	}

	private void addTextMsg(@Nullable final Throwable ex)
	{
		if (ex == null)
		{
			return;
		}

		final IErrorManager errorManager = Services.get(IErrorManager.class);

		addTextMsg(AdempiereException.extractMessage(ex));

		final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
		final AdIssueId adIssueId = errorManager.createIssue(metasfreshException);
		setAD_Issue_ID(adIssueId.getRepoId());
	}

	/**
	 * Set Runtime (Error) Message
	 */
	void setProcessMsg(@Nullable final String msg)
	{
		_processMsg = msg;
		addTextMsg(msg);
	}

	/**
	 * Set Runtime Error Message
	 */
	void setProcessMsg(@NonNull final Throwable ex)
	{
		_processMsg = AdempiereException.extractMessage(ex);
		addTextMsg(ex);
	}

	public String getProcessMsg()
	{
		return _processMsg;
	}

}
