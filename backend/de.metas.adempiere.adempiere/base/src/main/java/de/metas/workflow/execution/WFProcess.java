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
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.error.AdIssueId;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import de.metas.workflow.WFAction;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeSplitType;
import de.metas.workflow.WFNodeTransition;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

public class WFProcess
{
	private static final Logger log = LogManager.getLogger(WFProcess.class);

	private final WorkflowExecutionContext context;
	private WFProcessId wfProcessId;
	private final int priority;
	private WFState wfState;
	private boolean processed;
	private final TableRecordReference documentRef;

	private final WFResponsibleId wfResponsibleId;
	private final UserId userId;
	@Nullable
	private String textMsg;
	@Nullable
	private String processMsg;
	@Nullable
	private AdIssueId issueId;

	private final ArrayList<WFActivity> activities;

	WFProcess(@NonNull final WorkflowExecutionContext context)
	{
		this.context = context;

		final Instant now = SystemTime.asInstant();
		final Workflow workflow = context.getWorkflow();
		if (!TimeUtil.isBetween(now, workflow.getValidFrom(), workflow.getValidTo()))
		{
			throw new AdempiereException("Workflow not valid on " + now);
		}

		this.priority = workflow.getPriority();
		this.wfState = WFState.NotStarted;
		this.documentRef = context.getDocumentRef();

		//	Responsible/User
		this.wfResponsibleId = CoalesceUtil.coalesce(workflow.getResponsibleId(), WFResponsibleId.Invoker);
		this.userId = context.getUserId(); // user starting

		this.processed = false;

		activities = new ArrayList<>();
	}

	@NonNull
	WorkflowExecutionContext getContext() { return context; }

	@Nullable
	WFProcessId getWfProcessIdOrNull() { return wfProcessId; }

	WFProcessId getWfProcessId()
	{
		if (wfProcessId == null)
		{
			throw new AdempiereException("WF Process was not already saved");
		}
		return wfProcessId;
	}

	void setWfProcessId(@NonNull final WFProcessId wfProcessId)
	{
		this.wfProcessId = wfProcessId;
	}

	@NonNull
	public WorkflowId getWorkflowId() { return getContext().getWorkflow().getId(); }

	int getPriority()
	{
		return priority;
	}

	WFResponsibleId getWfResponsibleId()
	{
		return wfResponsibleId;
	}

	UserId getUserId()
	{
		return userId;
	}

	TableRecordReference getDocumentRef()
	{
		return documentRef;
	}

	public WFState getState()
	{
		return wfState;
	}

	private void setState(@NonNull final WFState wfState)
	{
		this.wfState = wfState;
	}

	public boolean isProcessed() { return processed; }

	private void setProcessed() { this.processed = true; }

	/**
	 * Set Process State and update Actions
	 */
	public void changeWFStateTo(final WFState wfState)
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

	private WFActivity newActivity(@NonNull final WFNodeId nodeId)
	{
		final WFActivity activity = new WFActivity(this, nodeId);
		activities.add(activity);
		return activity;
	}

	ImmutableList<WFActivity> getAllActivities()
	{
		return ImmutableList.copyOf(activities);
	}

	private ImmutableList<WFActivity> getActiveActivities()
	{
		return activities.stream()
				.filter(activity -> !activity.isProcessed())
				.collect(ImmutableList.toImmutableList());
	}

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
		final ImmutableList<WFNodeTransition> transitions = getWorkflow().getTransitionsFromNode(
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
			if (!transition.isValidFor(lastActivity))
			{
				continue;
			}

			//	Start new Activity...
			final WFActivity activity = newActivity(transition.getNextNodeId());
			activity.run();

			//	only the first valid if XOR
			if (splitType.isXOR())
			{
				return true;
			}
		}    //	for all transitions

		return true;
	}    //	startNext

	private Workflow getWorkflow()
	{
		return context.getWorkflow();
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

		final WFNodeId firstWFNodeId = getWorkflow().getFirstNodeId();
		changeWFStateTo(WFState.Running);
		try
		{
			//	Start first Activity with first Node
			final WFActivity firstActivity = newActivity(firstWFNodeId);
			firstActivity.run();
		}
		catch (final Throwable ex)
		{
			log.error("firstWFNodeId={}", firstWFNodeId, ex);
			setProcessMsg(ex);
			changeWFStateTo(WFState.Terminated);
		}
	}

	@Nullable
	public AdIssueId getIssueId() { return issueId; }

	private void setIssueId(@NonNull final AdIssueId issueId) { this.issueId = issueId; }

	@Nullable
	public String getTextMsg() { return textMsg; }

	private void setTextMsg(@Nullable final String textMsg)
	{
		this.textMsg = textMsg;
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

	/**
	 * Set Runtime (Error) Message
	 */
	void setProcessMsg(@Nullable final String msg)
	{
		processMsg = msg;
		addTextMsg(msg);
	}

	/**
	 * Set Runtime Error Message
	 */
	void setProcessMsg(@NonNull final Throwable ex)
	{
		processMsg = AdempiereException.extractMessage(ex);
		addTextMsg(ex);
	}

	@Nullable
	public String getProcessMsg() { return processMsg; }

}
