/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow.execution;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.error.AdIssueId;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.workflow.WFAction;
import de.metas.workflow.WFEventAudit;
import de.metas.workflow.WFEventAuditType;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeSplitType;
import de.metas.workflow.WFNodeTransition;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WFProcess
{
	private static final Logger log = LogManager.getLogger(WFProcess.class);

	@NonNull private final WorkflowExecutionContext context;
	@NonNull @Getter(AccessLevel.PACKAGE) private final Workflow workflow;

	@NonNull private final WFProcessState state;

	@NonNull private final ArrayList<WFActivity> activities;

	@Nullable private String processingResultMessage;
	@Nullable private Throwable processingResultException;

	WFProcess(@NonNull final WorkflowExecutionContext context, @NonNull final WorkflowId workflowId)
	{
		this.context = context;
		this.workflow = context.getWorkflowById(workflowId);
		this.state = createState(context, workflow);
		this.activities = new ArrayList<>();
	}

	private static WFProcessState createState(@NonNull final WorkflowExecutionContext context, @NonNull final Workflow workflow)
	{
		return WFProcessState.builder()
				.wfProcessId(null)
				.workflowId(workflow.getId())
				.priority(workflow.getPriority())
				.documentRef(context.getDocumentRef())
				.wfState(WFState.NotStarted)
				.processed(false)
				.wfResponsibleId(workflow.getResponsibleId())
				.initialUserId(context.getUserId())
				.userId(context.getUserId())
				.build();
	}

	WFProcess(
			@NonNull final WorkflowExecutionContext context,
			@NonNull final WFProcessState wfProcessState,
			@NonNull final List<WFActivityState> wfActivityStates)
	{
		this.context = context;
		this.workflow = context.getWorkflowById(wfProcessState.getWorkflowId());
		this.state = wfProcessState;

		activities = new ArrayList<>(wfActivityStates.size());
		for (final WFActivityState wfActivityState : wfActivityStates)
		{
			activities.add(new WFActivity(this, wfActivityState));
		}
	}

	@NonNull
	WorkflowExecutionContext getContext() {return context;}

	@Nullable
	WFProcessId getWfProcessIdOrNull() {return state.getWfProcessId();}

	WFProcessId getWfProcessId()
	{
		final WFProcessId wfProcessId = getWfProcessIdOrNull();
		if (wfProcessId == null)
		{
			throw new AdempiereException("WF Process was not already saved");
		}
		return wfProcessId;
	}

	void setWfProcessId(@NonNull final WFProcessId wfProcessId) {state.setWfProcessId(wfProcessId);}

	@NonNull
	public WorkflowId getWorkflowId() {return workflow.getId();}

	int getPriority() {return state.getPriority();}

	@NonNull WFResponsibleId getWfResponsibleId()
	{
		return state.getWfResponsibleId();
	}

	@NonNull UserId getInitialUserId() {return state.getInitialUserId();}

	@NonNull UserId getUserId() {return state.getUserId();}

	@NonNull Optional<DocStatus> getDocumentStatus() {return context.getDocumentStatus(getDocumentRef());}

	@NonNull TableRecordReference getDocumentRef() {return state.getDocumentRef();}

	@NonNull
	public WFState getState() {return state.getWfState();}

	private void setState(@NonNull final WFState wfState) {state.setWfState(wfState);}

	public boolean isProcessed() {return state.isProcessed();}

	private void setProcessed() {state.setProcessed(true);}

	/**
	 * Set Process State and update Actions
	 */
	public void changeWFStateTo(@NonNull final WFState wfState)
	{
		// No state change
		if (getState().equals(wfState))
		{
			return;
		}

		if (getState().isClosed())
		{
			return;
		}

		//
		if (getState().isValidNewState(wfState))
		{
			log.debug("Set WFState={}", wfState);
			setState(wfState);
			if (getState().isClosed())
			{
				setProcessed();
			}

			//context.save(this);

			//	Force close to all Activities
			if (getState().isClosed())
			{
				for (final WFActivity activity : getActiveActivities())
				{
					if (!activity.isClosed())
					{
						activity.addTextMsg("Process:" + wfState);
						activity.changeWFStateTo(wfState);
					}

					activity.setProcessed();
					//context.save(activity);
				}
			}    //	closed
		}
		else
		{
			log.warn("Ignored invalid state transition {} -> {}", getState(), wfState);
		}
	}

	void checkActivities()
	{
		if (getState().isClosed())
		{
			return;
		}

		//
		WFState closedState = null;
		boolean suspended = false;
		boolean running = false;

		final ImmutableList<WFActivity> activities = getActiveActivities();
		if (activities.isEmpty())
		{
			addTextMsg(new AdempiereException("No Active Processed found"));
			closedState = WFState.Terminated;
		}
		else
		{
			for (final WFActivity activity : activities)
			{
				final WFState activityState = activity.getState();

				//	Completed - Start Next
				if (activityState.isCompleted())
				{
					if (startNextActivity(activity))
					{
						continue;
					}
				}

				//
				// Activity is closed
				if (activityState.isClosed())
				{
					//	eliminate from active processed
					activity.setProcessed();
					//context.save(activity);

					//
					if (closedState == null)
					{
						closedState = activityState;
					}
					else if (!closedState.equals(activityState))
					{
						//	Overwrite if terminated
						if (WFState.Terminated.equals(activityState))
						{
							closedState = activityState;
						}
						//	Overwrite if activity aborted and no other terminated
						else if (WFState.Aborted.equals(activityState) && !WFState.Terminated.equals(closedState))
						{
							closedState = activityState;
						}
					}
				}
				//
				// Activity not closed
				else
				{
					// All activities shall be closed in order to close the process
					closedState = null;

					if (activityState.isSuspended())
					{
						suspended = true;
					}
					if (activityState.isRunning())
					{
						running = true;
					}
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
			changeWFStateTo(WFState.Suspended);
		}
		else if (running)
		{
			changeWFStateTo(WFState.Running);
		}
	}    //	checkActivities

	@NonNull
	private WFActivity newActivity(@NonNull final WFNodeId nodeId)
	{
		final WFActivity activity = new WFActivity(this, nodeId);
		activities.add(activity);
		return activity;
	}

	@NonNull ImmutableList<WFActivity> getAllActivities()
	{
		return ImmutableList.copyOf(activities);
	}

	@NonNull
	private ImmutableList<WFActivity> getActiveActivities()
	{
		return activities.stream()
				.filter(activity -> !activity.isProcessed())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Optional<WFActivity> getFirstActivityByWFState(@NonNull final WFState wfState)
	{
		return activities.stream()
				.filter(activity -> activity.getState().equals(wfState))
				.findFirst();
	}

	/**
	 * Start Next Activity
	 *
	 * @return true if there is a next activity
	 */
	private boolean startNextActivity(@NonNull final WFActivity lastActivity)
	{
		log.debug("Last activity: {}", lastActivity);

		//	transitions from the last processed node
		final ImmutableList<WFNodeTransition> transitions = workflow.getTransitionsFromNode(
				lastActivity.getNode().getId(),
				context.getClientId());
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
		lastActivity.setProcessed();
		//context.save(lastActivity);

		//	Start next activity
		final WFNodeSplitType splitType = lastActivity.getNode().getSplitType();
		for (final WFNodeTransition transition : transitions)
		{
			//	Can we move to next activity?
			final BooleanWithReason allow = transition.checkAllowGoingAwayFrom(lastActivity);
			if (allow.isFalse())
			{
				final WFNode nextNode = workflow.getNodeById(transition.getNextNodeId());
				logAudit(lastActivity.getNode().getId(),
						"Transition to " + nextNode.getName().getDefaultValue()
								+ " not valid because: " + allow.getReason().getDefaultValue());
				continue;
			}

			//	Start new Activity...
			final WFActivity activity = newActivity(transition.getNextNodeId());
			activity.start();

			//	only the first valid if XOR
			if (splitType.isXOR())
			{
				return true;
			}
		}    //	for all transitions

		return true;
	}    //	startNext

	private void logAudit(
			@Nullable final WFNodeId wfNodeId,
			@Nullable final String textMsg)
	{
		if (Check.isBlank(textMsg))
		{
			return;
		}

		context.addEventAudit(WFEventAudit.builder()
				.eventType(WFEventAuditType.Trace)
				.wfProcessId(getWfProcessId())
				.wfNodeId(wfNodeId)
				.documentRef(getDocumentRef())
				.wfResponsibleId(getWfResponsibleId())
				.userId(getUserId())
				.wfState(getState())
				.textMsg(textMsg)
				.build());

	}

	/**
	 * Start WF Execution
	 */
	public void startWork()
	{
		if (!getState().isValidAction(WFAction.Start))
		{
			log.warn("Cannot start from state {}", getState());
			return;
		}

		//
		// Make sure is saved
		context.save(this);

		final WFNodeId firstWFNodeId = workflow.getFirstNodeId();
		changeWFStateTo(WFState.Running);
		try
		{
			//	Start first Activity with first Node
			final WFActivity firstActivity = newActivity(firstWFNodeId);
			firstActivity.start();
		}
		catch (final Throwable ex)
		{
			setProcessingResultMessage(ex);
			changeWFStateTo(WFState.Terminated);

			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	public void resumeWork()
	{
		if (!getState().isValidAction(WFAction.Resume))
		{
			log.warn("Cannot resume from state {}", getState());
			return;
		}

		for (final WFActivity activity : getActiveActivities())
		{
			if (activity.getState().isSuspended())
			{
				activity.resume();
			}
		}

	}

	@Nullable
	public AdIssueId getIssueId() {return state.getIssueId();}

	private void setIssueId(@NonNull final AdIssueId issueId) {state.setIssueId(issueId);}

	@Nullable
	public String getTextMsg() {return state.getTextMsg();}

	private void setTextMsg(@Nullable final String textMsg)
	{
		state.setTextMsg(textMsg);
	}

	void addTextMsg(@Nullable final String textMsg)
	{
		if (textMsg == null || textMsg.isEmpty())
		{
			return;
		}

		final String oldText = StringUtils.trimBlankToNull(getTextMsg());
		if (oldText == null)
		{
			setTextMsg(textMsg.trim());
		}
		else
		{
			setTextMsg(oldText + "\n" + textMsg.trim());
		}
	}

	private void addTextMsg(@Nullable final Throwable ex)
	{
		if (ex == null)
		{
			return;
		}

		addTextMsg(AdempiereException.extractMessage(ex));

		final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
		final AdIssueId adIssueId = context.createIssue(metasfreshException);
		setIssueId(adIssueId);
	}

	void setProcessingResultMessage(@Nullable final String msg)
	{
		this.processingResultMessage = msg;
		this.processingResultException = null;
		addTextMsg(msg);
	}

	void setProcessingResultMessage(@NonNull final Throwable ex)
	{
		this.processingResultMessage = AdempiereException.extractMessage(ex);
		this.processingResultException = ex;
		addTextMsg(ex);
	}

	@Nullable
	public String getProcessingResultMessage() {return processingResultMessage;}

	@Nullable
	public Throwable getProcessingResultException() {return processingResultException;}
}
