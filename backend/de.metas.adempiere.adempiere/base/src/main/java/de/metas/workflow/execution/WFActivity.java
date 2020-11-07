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

import de.metas.common.util.CoalesceUtil;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.email.EMailAddress;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.error.AdIssueId;
import de.metas.event.Topic;
import de.metas.i18n.ADMessageAndParams;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.reflist.ReferenceId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import de.metas.util.time.SystemTime;
import de.metas.workflow.WDEventAuditType;
import de.metas.workflow.WFAction;
import de.metas.workflow.WFEventAudit;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import de.metas.workflow.WFNodeEmailRecipient;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeParameter;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.model.MNote;
import org.compiere.print.ReportEngine;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class WFActivity
{
	private static final Logger log = LogManager.getLogger(WFActivity.class);
	private static final Topic USER_NOTIFICATIONS_TOPIC = Topic.remote("de.metas.document.UserNotifications");

	private static final AdMessageKey MSG_ApprovalRequest = AdMessageKey.of("DocumentApprovalRequest");
	private static final AdMessageKey MSG_NotApproved = AdMessageKey.of("NotApproved");

	private final WorkflowExecutionContext context;
	private WFActivityId id;
	private final WFProcess wfProcess;
	private final WFNode wfNode;
	private int priority;
	private WFState wfState;
	private boolean processed;
	private final TableRecordReference documentRef;
	private Instant endWaitTime;
	@Nullable
	private String textMsg;
	@Nullable
	private AdIssueId issueId;

	private WFResponsibleId wfResponsibleId;
	private UserId userId;

	private WFEventAudit _lastAudit = null;
	private String m_newValue = null;
	private ArrayList<EMailAddress> m_emails = new ArrayList<>();

	public WFActivity(
			@NonNull final WFProcess process,
			@NonNull final WFNodeId nodeId)
	{
		this.context = process.getContext();

		this.wfProcess = process;
		this.wfNode = context.getNodeById(nodeId);
		this.wfState = WFState.NotStarted;
		this.documentRef = process.getDocumentRef();
		this.processed = false;

		// Node Priority & End Duration
		this.priority = process.getPriority();
		if (wfNode.getPriority() > 0)
		{
			this.priority = wfNode.getPriority();
		}
		final Duration durationLimit = wfNode.getDurationLimit();
		if (!durationLimit.isZero())
		{
			this.endWaitTime = SystemTime.asInstant().plus(durationLimit);
		}
		else
		{
			this.endWaitTime = null;
		}

		// Responsible
		{
			this.wfResponsibleId = CoalesceUtil.coalesce(wfNode.getResponsibleId(), process.getWfResponsibleId());

			final WFResponsible resp = context.getResponsibleById(this.wfResponsibleId);

			// User - Directly responsible
			this.userId = resp.getUserId();
			// Invoker - get Sales Rep or last updater of document
			if (userId == null || resp.isInvoker())
			{
				userId = process.getUserId();
			}
		}

		context.save(this);

		//
		newEventAudit();
	}

	@NonNull
	WorkflowExecutionContext getContext() { return context; }

	@NonNull
	private WFProcess getWorkflowProcess() { return wfProcess; }

	@NonNull
	public WFProcessId getWfProcessId() { return getWorkflowProcess().getWfProcessId(); }

	public WFActivityId getId() { return id; }

	void setId(@NonNull final WFActivityId id) { this.id = id; }

	public int getPriority() { return priority; }

	public WFState getState()
	{
		return wfState;
	}

	private void setState(@NonNull final WFState wfState)
	{
		this.wfState = wfState;
	}

	/**
	 * Set Activity State.
	 * It also validates the new state and if is valid,
	 * then create event audit and call {@link WFProcess#checkActivities()}
	 */
	public void changeWFStateTo(final WFState wfState)
	{
		if (getState().isClosed())
		{
			return;
		}
		if (getState().equals(wfState))
		{
			return;
		}
		//
		if (getState().isValidNewState(wfState))
		{
			final WFState oldState = getState();
			log.debug(oldState + "->" + wfState + ", Msg=" + getTextMsg());
			setState(wfState);
			context.save(this);            // closed in MWFProcess.checkActivities()
			updateEventAudit();

			// Inform Process
			final WFProcess wfProcess = getWorkflowProcess();
			wfProcess.checkActivities();
		}
		else
		{
			final String msg = "Set WFState - Ignored Invalid Transformation - New=" + wfState + ", Current=" + getState();
			log.error(msg);
			addTextMsg(msg);
			context.save(this);
			// TODO: teo_sarca: throw exception ? please analyze the call hierarchy first
		}
	}    // setWFState

	public UserId getUserId() { return this.userId; }

	public void setUserId(@NonNull final UserId userId) { this.userId = userId; }

	private void setPriority(final int priority)
	{
		this.priority = priority;
	}

	private void setEndWaitTime(@NonNull final Instant endWaitTime) { this.endWaitTime = endWaitTime; }

	public Instant getEndWaitTime() { return endWaitTime; }

	public boolean isProcessed() { return processed; }

	void setProcessed() { this.processed = true; }

	/**
	 * @return true if closed
	 */
	public boolean isClosed()
	{
		return getState().isClosed();
	}

	private void updateEventAudit()
	{
		final WFEventAudit audit = getLastEventAuditOrCreate();
		audit.setTextMsg(getTextMsg());
		audit.setWfState(getState());
		if (m_newValue != null)
		{
			audit.setAttributeValueNew(m_newValue);
		}
		if (getState().isClosed())
		{
			audit.setEventType(WDEventAuditType.ProcessCompleted);
			audit.updateElapsedTime();
		}
		else
		{
			audit.setEventType(WDEventAuditType.StateChanged);
		}

		context.save(audit);
	}

	private WFEventAudit getLastEventAuditOrCreate()
	{
		WFEventAudit lastAudit = this._lastAudit;
		if (lastAudit == null)
		{
			final WFProcessId wfProcessId = getWorkflowProcess().getWfProcessId();
			final WFNodeId wfNodeId = getNode().getId();
			lastAudit = context.getLastEventAuditByWFNodeId(wfProcessId, wfNodeId)
					.orElseGet(() -> newEventAuditDraft(this));

			this._lastAudit = lastAudit;
		}

		return lastAudit;
	}

	private void newEventAudit()
	{
		final WFEventAudit audit = newEventAuditDraft(this);
		context.save(audit);

		this._lastAudit = audit;
	}

	private static WFEventAudit newEventAuditDraft(@NonNull final WFActivity activity)
	{
		final WFEventAudit.WFEventAuditBuilder builder = WFEventAudit.builder()
				.eventType(WDEventAuditType.ProcessCreated)
				//
				.orgId(OrgId.ANY)
				.wfProcessId(activity.getWorkflowProcess().getWfProcessId())
				//.wfNodeId(WFNodeId.ofRepoId(activity.getAD_WF_Node_ID())) // see below
				.documentRef(activity.getDocumentRef())
				//
				.wfResponsibleId(activity.getWFResponsibleId())
				.userId(activity.getUserId())
				//
				.wfState(activity.getState())
				.elapsedTime(Duration.ZERO);

		final WFNode node = activity.getNode();
		if (node != null)
		{
			builder.wfNodeId(node.getId());

			final WFNodeAction action = node.getAction();
			if (WFNodeAction.SetVariable.equals(action)
					|| WFNodeAction.UserChoice.equals(action))
			{
				builder.attributeName(node.getDocumentColumnName());
				builder.attributeValueOld(String.valueOf(activity.getAttributeValue()));
				if (WFNodeAction.SetVariable.equals(action))
				{
					builder.attributeValueNew(node.getAttributeValue());
				}
			}
		}

		return builder.build();
	}

	private IDocument getDocument() { return context.getDocument(documentRef); }

	public IDocument getDocumentOrNull() { return context.getDocumentOrNull(documentRef); }

	public TableRecordReference getDocumentRef() { return documentRef; }

	public Object getDocumentColumnValueByColumnId(final int adColumnId) { return context.getDocumentColumnValueByColumnId(getDocumentRef(), adColumnId); }

	public Object getDocumentColumnValueByColumnName(final String columnName) { return context.getDocumentColumnValueByColumnName(getDocumentRef(), columnName); }

	/**
	 * @return Attribute Value (based on Node) of PO
	 */
	@Nullable
	Object getAttributeValue()
	{
		final WFNode node = getNode();
		if (node == null)
		{
			return null;
		}

		final int AD_Column_ID = node.getDocumentColumnId();
		if (AD_Column_ID <= 0)
		{
			return null;
		}

		return context.getDocumentColumnValueByColumnId(getDocumentRef(), AD_Column_ID);
	}

	public WFNode getNode() { return wfNode; }

	public String getTextMsg() { return textMsg; }

	private void setTextMsg(@Nullable final String textMsg) { this.textMsg = textMsg; }

	public void addTextMsg(@Nullable final String textMsg)
	{
		if (textMsg == null || Check.isBlank(textMsg))
		{
			return;
		}

		final String oldText = StringUtils.trimBlankToNull(getTextMsg());
		if (oldText == null || Check.isBlank(oldText))
		{
			setTextMsg(StringUtils.trunc(textMsg.trim(), 1000));
		}
		else
		{
			setTextMsg(StringUtils.trunc(oldText.trim() + "\n" + textMsg.trim(), 1000));
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

	private void setIssueId(@NonNull final AdIssueId issueId) { this.issueId = issueId; }

	public AdIssueId getIssueId() { return issueId; }

	/**
	 * Set Responsible and User from Process / Node
	 */
	public WFResponsibleId getWFResponsibleId()
	{
		return wfResponsibleId;
	}

	private WFResponsible getResponsible()
	{
		return context.getResponsibleById(getWFResponsibleId());
	}

	public void setWFResponsibleId(@NonNull final WFResponsibleId wfResponsibleId)
	{
		this.wfResponsibleId = wfResponsibleId;
	}

	private boolean isInvoker()
	{
		return getResponsible().isInvoker();
	}    // isInvoker

	@Value
	@Builder
	private static class DocumentToApproveRequest
	{
		@NonNull
		UserId workflowInvokerId;

		@Nullable
		Money amountToApprove;
		@NonNull
		UserId documentOwnerId;
		@NonNull
		ClientAndOrgId clientAndOrgId;
	}

	private DocumentToApproveRequest getDocumentToApproveRequest()
	{
		final IDocument doc = context.getDocument(getDocumentRef());
		final UserId documentOwnerId = UserId.ofRepoId(doc.getDoc_User_ID());
		final UserId invokerId = CoalesceUtil.coalesce(
				getUserId(),
				documentOwnerId);

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(doc.getC_Currency_ID());
		final Money amountToApprove = currencyId != null
				? Money.of(doc.getApprovalAmt(), currencyId)
				: null;

		return DocumentToApproveRequest.builder()
				.workflowInvokerId(invokerId)
				.amountToApprove(amountToApprove)
				.documentOwnerId(documentOwnerId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(doc.getAD_Client_ID(), doc.getAD_Org_ID()))
				.build();
	}

	private Optional<UserId> getApprovalUser(@NonNull final DocumentToApproveRequest document)
	{
		// Nothing to approve
		if (document.getAmountToApprove() == null || document.getAmountToApprove().signum() == 0)
		{
			return Optional.of(document.getWorkflowInvokerId());
		}

		UserId currentUserId = document.getWorkflowInvokerId();
		final HashSet<UserId> alreadyCheckedUserIds = new HashSet<>();
		while (currentUserId != null)
		{
			if (!alreadyCheckedUserIds.add(currentUserId))
			{
				log.debug("Loop - {}", alreadyCheckedUserIds);
				return Optional.empty();
			}

			if (isUserAllowedToApproveDocument(currentUserId, document))
			{
				return Optional.of(currentUserId);
			}

			//
			// Get's user's supervisor
			currentUserId = getSupervisorOfUserId(currentUserId).orElse(null);
			if (currentUserId == null)
			{
				currentUserId = getSupervisorOfOrgId(document.getClientAndOrgId().getOrgId()).orElse(null);
			}
		}

		log.debug("No user found");
		return Optional.empty();
	}    // getApproval

	private boolean isUserAllowedToApproveDocument(
			@NonNull final UserId userId,
			@NonNull final DocumentToApproveRequest document)
	{

		final List<IUserRolePermissions> roles = context.getUserRolesPermissionsForUserWithOrgAccess(
				userId,
				document.getClientAndOrgId());
		for (final IUserRolePermissions role : roles)
		{
			if (isRoleAllowedToApproveDocument(role, userId, document))
			{
				return true;
			}
		}

		return false;
	}

	private boolean isRoleAllowedToApproveDocument(
			@NonNull final IUserRolePermissions role,
			@NonNull final UserId userId,
			@NonNull final DocumentToApproveRequest document)
	{
		final DocumentApprovalConstraint docApprovalConstraints = role.getConstraint(DocumentApprovalConstraint.class)
				.or(DocumentApprovalConstraint.DEFAULT);

		final boolean ownDocument = UserId.equals(document.getDocumentOwnerId(), userId);
		if (ownDocument && !docApprovalConstraints.canApproveOwnDoc())
		{
			return false;
		}

		final Money amountToApprove = document.getAmountToApprove();
		if (amountToApprove == null)
		{
			return true;
		}

		Money maxAllowedAmount = docApprovalConstraints.getAmtApproval(amountToApprove.getCurrencyId());
		if (maxAllowedAmount.signum() <= 0)
		{
			return false;
		}

		maxAllowedAmount = context.convertMoney(maxAllowedAmount, amountToApprove.getCurrencyId(), document.getClientAndOrgId());

		return amountToApprove.isLessThanOrEqualTo(maxAllowedAmount);
	}

	private Optional<UserId> getSupervisorOfUserId(@NonNull final UserId userId)
	{
		final I_AD_User user = context.getUserById(userId);
		final UserId supervisorId = UserId.ofRepoIdOrNullIfSystem(user.getSupervisor_ID());
		return Optional.ofNullable(supervisorId);
	}

	private Optional<UserId> getSupervisorOfOrgId(@NonNull final OrgId orgId)
	{
		OrgId currentOrgId = orgId;
		final HashSet<OrgId> alreadyCheckedOrgIds = new HashSet<>();
		while (currentOrgId != null)
		{
			if (!alreadyCheckedOrgIds.add(currentOrgId))
			{
				log.debug("Org look detected, returning empty: {}", alreadyCheckedOrgIds);
				return Optional.empty();
			}

			final OrgInfo orgInfo = context.getOrgInfoById(currentOrgId);
			if (orgInfo.getSupervisorId() != null)
			{
				return Optional.of(orgInfo.getSupervisorId());
			}

			currentOrgId = orgInfo.getParentOrgId();
		}

		return Optional.empty();
	}

	/**************************************************************************
	 * Execute Work.
	 * Called from MWFProcess.startNext
	 * Feedback to Process via setWFState -> checkActivities
	 */
	public void run()
	{
		log.debug("Node={}", getNode());

		m_newValue = null;

		context.getTrxManager().runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			private DocStatus newDocStatus = null;

			@Override
			public void run(final String localTrxName)
			{
				if (!getState().isValidAction(WFAction.Start))
				{
					addTextMsg(new AdempiereException("Cannot start activity from state " + getState()));
					changeWFStateTo(WFState.Terminated);
					return;
				}
				//
				changeWFStateTo(WFState.Running);

				if (getNode() == null)
				{
					addTextMsg("Node not found");
					changeWFStateTo(WFState.Aborted);
					return;
				}

				// Do Work
				final PerformWorkResult result = performWork();
				final PerformWorkResolution resolution = result.getResolution();
				this.newDocStatus = result.getNewDocStatus();

				if (resolution == PerformWorkResolution.COMPLETED)
				{
					changeWFStateTo(WFState.Completed);
				}
				else if (resolution == PerformWorkResolution.SUSPENDED)
				{
					changeWFStateTo(WFState.Suspended);
				}
				else
				{
					throw new AdempiereException("Unknown resolution: " + result);
				}
			}

			@Override
			public boolean doCatch(final Throwable ex)
			{
				// If we have a DocStatus, change it to Invalid, and throw the exception to the next level
				// If not, don't touch it at all.
				if (newDocStatus != null)
				{
					newDocStatus = DocStatus.Invalid;
				}

				addTextMsg(ex);
				changeWFStateTo(WFState.Terminated);    // unlocks

				// Set Document Status
				if (newDocStatus != null)
				{
					context.setDocStatus(getDocumentRef(), newDocStatus);
				}

				return ROLLBACK;
			}

			@Override
			public void doFinally()
			{
				context.save(WFActivity.this);
			}
		});
	}

	private enum PerformWorkResolution
	{
		COMPLETED,
		SUSPENDED
	}

	@Value
	@Builder
	private static class PerformWorkResult
	{
		public static final PerformWorkResult COMPLETED = builder().resolution(PerformWorkResolution.COMPLETED).build();
		public static final PerformWorkResult SUSPENDED = builder().resolution(PerformWorkResolution.SUSPENDED).build();

		@NonNull
		PerformWorkResolution resolution;

		@Nullable
		@With
		DocStatus newDocStatus;
	}

	/**
	 * Perform Work.
	 * Set Text Msg.
	 */
	private PerformWorkResult performWork()
	{
		final WFNode wfNode = getNode();
		log.debug("Performing work for {}", wfNode);

		if (wfNode.getPriority() != 0)
		{
			setPriority(wfNode.getPriority());
		}

		final WFNodeAction action = wfNode.getAction();

		if (WFNodeAction.WaitSleep.equals(action))
		{
			return performWork_WaitSleep();
		}
		else if (WFNodeAction.DocumentAction.equals(action))
		{
			return performWork_DocumentAction();
		}
		else if (WFNodeAction.AppsReport.equals(action))
		{
			performWork_AppsReport();
			return PerformWorkResult.COMPLETED;
		}
		else if (WFNodeAction.AppsProcess.equals(action))
		{
			performWork_AppsProcess();
			return PerformWorkResult.COMPLETED;
		}
		else if (WFNodeAction.EMail.equals(action))
		{
			performWork_EMail();
			return PerformWorkResult.COMPLETED;
		}
		else if (WFNodeAction.SetVariable.equals(action))
		{
			performWork_SetVariable();
			return PerformWorkResult.COMPLETED;
		}
		else if (WFNodeAction.SubWorkflow.equals(action))
		{
			throw new AdempiereException("Starting sub-workflow is not implemented yet");
		}
		else if (WFNodeAction.UserChoice.equals(action))
		{
			return performWork_UserChoice();
		}
		else if (WFNodeAction.UserForm.equals(action))
		{
			log.debug("Form:AD_Form_ID={}", wfNode.getAdFormId());
			return PerformWorkResult.SUSPENDED;
		}
		else if (WFNodeAction.UserWindow.equals(action))
		{
			log.debug("Window:AD_Window_ID={}", wfNode.getAdWindowId());
			return PerformWorkResult.SUSPENDED;
		}
		else
		{
			throw new AdempiereException("Invalid Action (Not Implemented) =" + action);
		}
	}

	private void performWork_EMail()
	{
		log.debug("EMail:EMailRecipient={}", getNode().getEmailRecipient());

		final IDocument document = getDocumentOrNull();
		if (document != null)
		{
			m_emails = new ArrayList<>();
			sendEMail();
			addTextMsg(m_emails.toString());
		}
		else
		{
			final MailTemplateId mailTemplateId = getNode().getMailTemplateId();

			final MailTextBuilder mailTextBuilder = context.newMailTextBuilder(getDocumentRef(), mailTemplateId);

			final String adLanguage = mailTextBuilder.getAdLanguage();

			// metas: tsa: check for null strings
			final StringBuilder subject = new StringBuilder();
			final String description = getNode().getDescription().translate(adLanguage);
			if (!Check.isBlank(description))
			{
				subject.append(description);
			}
			if (!Check.isBlank(mailTextBuilder.getMailHeader()))
			{
				if (subject.length() > 0)
				{
					subject.append(": ");
				}
				subject.append(mailTextBuilder.getMailHeader());
			}

			// metas: tsa: check for null strings
			final StringBuilder message = new StringBuilder(mailTextBuilder.getFullMailText());
			final String help = getNode().getHelp().translate(adLanguage);
			if (!Check.isBlank(help))
			{
				message.append("\n-----\n").append(help);
			}

			final EMailAddress to = getNode().getEmailTo();

			final MClient client = MClient.get(Env.getCtx(), context.getClientId().getRepoId());
			client.sendEMail(to, subject.toString(), message.toString(), null);
		}
	}

	private void performWork_AppsProcess()
	{
		final WFNode wfNode = getNode();
		log.debug("Process: AD_Process_ID={}", wfNode.getProcessId());

		ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setClientId(context.getClientId())
				.setUserId(getUserId())
				.setAD_Process_ID(wfNode.getProcessId())
				.setTitle(wfNode.getName().getDefaultValue())
				.setRecord(getDocumentRef())
				.addParameters(createProcessInfoParameters())
				//
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.executeSync();
	}

	private void performWork_AppsReport()
	{
		final WFNode wfNode = getNode();
		log.debug("Report: AD_Process_ID={}", wfNode.getProcessId());
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setClientId(context.getClientId())
				.setUserId(getUserId())
				.setAD_Process_ID(wfNode.getProcessId())
				.setTitle(wfNode.getName().getDefaultValue())
				.setRecord(getDocumentRef())
				.addParameters(createProcessInfoParameters())
				.build();
		if (!pi.isReportingProcess())
		{
			throw new IllegalStateException("Not a Report AD_Process_ID=" + pi);
		}

		// Report
		final ReportEngine re = ReportEngine.get(Env.getCtx(), pi);
		if (re == null)
		{
			throw new AdempiereException("Cannot create Report AD_Process_ID=" + wfNode.getProcessId());
		}
		final File report = re.getPDF();
		// Notice
		final int AD_Message_ID = 753;        // HARDCODED WorkflowResult
		final MNote note = new MNote(Env.getCtx(), AD_Message_ID, getUserId().getRepoId(), ITrx.TRXNAME_ThreadInherited);
		note.setTextMsg(wfNode.getName().getDefaultValue());
		note.setDescription(wfNode.getDescription().getDefaultValue());
		note.setRecord(getDocumentRef());
		note.saveEx();
		// Attachment

		context.createNewAttachment(note, report);
	}

	private PerformWorkResult performWork_WaitSleep()
	{
		final WFNode wfNode = getNode();
		final Duration waitTime = wfNode.getWaitTime();
		log.debug("Sleep:WaitTime={}", waitTime);
		if (waitTime.isNegative() || waitTime.isZero())
		{
			return PerformWorkResult.COMPLETED;
		}
		else
		{
			final Instant endWaitTime = SystemTime.asInstant().plus(waitTime);
			setEndWaitTime(endWaitTime);
			return PerformWorkResult.SUSPENDED;
		}
	}

	private PerformWorkResult performWork_DocumentAction()
	{
		final WFNode wfNode = getNode();
		final String docAction = wfNode.getDocAction();
		final WFProcess wfProcess = this.getWorkflowProcess();
		log.debug("DocumentAction={}", docAction);

		try
		{
			final IDocument document = context.processDocument(getDocumentRef(), docAction);

			addTextMsg(document.getSummary());
			wfProcess.setProcessMsg(document.getProcessMsg());

			return PerformWorkResult.builder()
					.resolution(PerformWorkResolution.COMPLETED)
					.newDocStatus(DocStatus.ofCode(document.getDocStatus()))
					.build();
		}
		catch (final Exception ex)
		{
			wfProcess.setProcessMsg(ex);
			throw ex;
		}
	}

	private void performWork_SetVariable()
	{
		final WFNode wfNode = getNode();
		final String value = wfNode.getAttributeValue();
		log.debug("SetVariable:AD_Column_ID={} to {}", wfNode.getDocumentColumnId(), value);
		final ReferenceId dt = wfNode.getDocumentColumnValueType();
		setVariable(value, dt.getRepoId(), null);
	}

	private PerformWorkResult performWork_UserChoice()
	{
		final WFNode wfNode = getNode();
		log.debug("UserChoice:AD_Column_ID={}", wfNode.getDocumentColumnId());

		// Approval:
		if (getNode().isUserApproval())
		{
			return performWork_UserChoice_DocumentApproval();
		}
		// Wait for user's manual input:
		else
		{
			return PerformWorkResult.SUSPENDED;
		}
	}

	private PerformWorkResult performWork_UserChoice_DocumentApproval()
	{
		if (getDocumentOrNull() == null)
		{
			return PerformWorkResult.SUSPENDED;
		}

		final boolean autoApproval;

		// Approval Hierarchy
		final WFResponsible responsible = getResponsible();
		if (responsible.isInvoker())
		{
			final DocumentToApproveRequest documentToApprove = getDocumentToApproveRequest();
			final UserId approverId = getApprovalUser(documentToApprove).orElse(null);
			if (approverId == null)
			{
				throw new AdempiereException("No user to approve found!"); // TODO: trl
			}
			else if (UserId.equals(documentToApprove.getWorkflowInvokerId(), approverId)) // same user as invoker
			{
				autoApproval = true;
			}
			else
			{
				autoApproval = false;

				final String invokerName = context.getUserFullname();
				forwardTo(approverId, msgApprovalRequest(), null);
			}
		}
		else if (responsible.isHuman())
		{
			final WFProcess wfProcess = getWorkflowProcess();
			autoApproval = responsible.getUserId() != null
					&& UserId.equals(responsible.getUserId(), wfProcess.getUserId());
			if (!autoApproval && responsible.getUserId() != null)
			{
				final String invokerName = context.getUserFullname();
				forwardTo(responsible.getUserId(), msgApprovalRequest(), null);
			}
		}
		else if (responsible.isRole())
		{
			final Set<UserId> allRoleUserIds = context.getUserIdsByRoleId(responsible.getRoleId());

			final WFProcess wfProcess = getWorkflowProcess();
			if (allRoleUserIds.contains(wfProcess.getUserId()))
			{
				autoApproval = true;
			}
			else
			{
				autoApproval = false;
			}
		}
		else if (responsible.isOrganization())
		{
			throw new AdempiereException("Support not implemented for " + responsible);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ " + responsible);
		}

		if (autoApproval)
		{
			context.processDocument(getDocumentRef(), IDocument.ACTION_Approve);

			return PerformWorkResult.COMPLETED;
		}
		else
		{
			return PerformWorkResult.SUSPENDED;
		}
	}

	private ADMessageAndParams msgApprovalRequest()
	{
		final String invokerName = context.getUserFullname();
		return ADMessageAndParams.of(MSG_ApprovalRequest, invokerName, getDocumentRef());
	}

	/**
	 * Set Variable
	 *
	 * @param valueStr    new Value
	 * @param displayType display type
	 * @param textMsg     optional Message
	 * @throws AdempiereException if error
	 */
	private void setVariable(
			@Nullable final String valueStr,
			final int displayType,
			@Nullable final String textMsg)
	{
		m_newValue = null;

		// Set Value
		final Object dbValue;
		if (valueStr == null)
		{
			dbValue = null;
		}
		else if (displayType == DisplayType.YesNo)
		{
			dbValue = StringUtils.toBoolean(valueStr);
		}
		else if (DisplayType.isNumeric(displayType))
		{
			dbValue = NumberUtils.asBigDecimal(valueStr);
		}
		else
		{
			dbValue = valueStr;
		}

		final String nodeColumnName = getNode().getDocumentColumnName();
		context.setDocumentColumnValue(getDocumentRef(), nodeColumnName, dbValue);

		// Info
		String msg = nodeColumnName + "=" + valueStr;
		if (textMsg != null && textMsg.length() > 0)
		{
			msg += " - " + textMsg;
		}
		addTextMsg(msg);
		m_newValue = valueStr;
	}    // setVariable

	/**
	 * Set User Choice
	 */
	public void setUserChoice(
			@NonNull final UserId userId,
			final String value,
			final int displayType,
			@Nullable final String textMsg)
	{
		changeWFStateTo(WFState.Running);
		setUserId(userId);
		setVariable(value, displayType, textMsg);

		final WFState newState;

		if (getNode().isUserApproval())
		{
			final boolean approved = StringUtils.toBoolean(value);
			newState = setUserChoice_DocumentApproval(approved);
		}
		else
		{
			newState = WFState.Completed;
		}

		changeWFStateTo(newState);
	}    // setUserChoice

	private WFState setUserChoice_DocumentApproval(final boolean approved)
	{
		WFState newState = WFState.Completed;

		if (getDocumentOrNull() == null)
		{
			return newState;
		}

		try
		{
			// Not approved
			if (!approved)
			{
				context.processDocument(getDocumentRef(), IDocument.ACTION_Reject);
				newState = WFState.Aborted;
			}
			else
			{
				if (isInvoker())
				{
					final DocumentToApproveRequest documentToApprove = getDocumentToApproveRequest();
					final UserId approverId = getApprovalUser(documentToApprove).orElse(null);

					// No Approver
					if (approverId == null)
					{
						newState = WFState.Aborted;
						addTextMsg("Cannot Approve - No Approver");
						context.processDocument(getDocumentRef(), IDocument.ACTION_Reject);
					}
					// Another user is allowed to approve it => forward to that user
					else if (!UserId.equals(documentToApprove.getWorkflowInvokerId(), approverId))
					{
						forwardTo(approverId, msgApprovalRequest(), null);
						newState = WFState.Suspended;
					}
					else
					// Approve
					{
						context.processDocument(getDocumentRef(), IDocument.ACTION_Approve);
					}
				}
				// No Invoker - Approve
				else
				{
					context.processDocument(getDocumentRef(), IDocument.ACTION_Approve);
				}
			}
		}
		catch (final Exception ex)
		{
			newState = WFState.Terminated;
			addTextMsg(ex);
			log.warn("", ex);
		}

		//
		// Send Not Approved Notification
		final IDocument doc = getDocument();
		final UserId userId = UserId.ofRepoIdOrNullIfSystem(doc.getDoc_User_ID());
		if (newState.equals(WFState.Aborted) && userId != null)
		{
			final String docInfo = (doc.getSummary() != null ? doc.getSummary() + "\n" : "")
					+ (doc.getProcessMsg() != null ? doc.getProcessMsg() + "\n" : "")
					+ (getTextMsg() != null ? getTextMsg() : "");

			context.sendNotification(UserNotificationRequest.builder()
					.topic(USER_NOTIFICATIONS_TOPIC)
					.recipientUserId(userId)
					.contentADMessage(MSG_NotApproved)
					.contentADMessageParam(doc.toTableRecordReference())
					.contentADMessageParam(docInfo)
					.targetAction(UserNotificationRequest.TargetRecordAction.of(doc.toTableRecordReference()))
					.build());
		}

		return newState;
	}

	/**
	 * Forward To
	 */
	public void forwardTo(
			@NonNull final UserId userId,
			@NonNull final ADMessageAndParams subject,
			@Nullable final String textMsg)
	{
		final UserId oldUserId = getUserId();
		if (UserId.equals(userId, oldUserId))
		{
			log.warn("Asked to forward to same user `{}`. Do nothing.", userId);
			return;
		}

		// Update
		setUserId(userId);
		addTextMsg(textMsg);
		context.save(this);

		//
		// Notify user
		final TableRecordReference documentRef = getDocumentRef();
		context.sendNotification(UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC)
				.recipientUserId(userId)
				.contentADMessage(subject.getAdMessage())
				.contentADMessageParams(subject.getParams())
				.targetAction(UserNotificationRequest.TargetRecordAction.of(documentRef))
				.build());

		// Close up Old Event
		{
			final WFEventAudit audit = getLastEventAuditOrCreate();
			audit.setUserId(oldUserId);
			audit.setTextMsg(getTextMsg());
			audit.setAttributeName("AD_User_ID");
			audit.setAttributeValueOld(buildUserSummary(oldUserId));
			audit.setAttributeValueNew(buildUserSummary(userId));
			//
			audit.setWfState(getState());
			audit.setEventType(WDEventAuditType.StateChanged);
			audit.updateElapsedTime();

			context.save(audit);
		}

		// Create new one
		newEventAudit();
	}

	@Nullable
	private String buildUserSummary(final UserId userId)
	{
		if (userId == null)
		{
			return null;
		}

		final String name = context.getUserFullnameById(userId);
		return name + " (" + userId.getRepoId() + ")";
	}

	public void setUserConfirmation(@NonNull final UserId userId, @Nullable final String textMsg)
	{
		changeWFStateTo(WFState.Running);
		setUserId(userId);
		if (textMsg != null)
		{
			addTextMsg(textMsg);
		}
		changeWFStateTo(WFState.Completed);
	}    // setUserConfirmation

	private List<ProcessInfoParameter> createProcessInfoParameters()
	{
		return getNode()
				.getProcessParameters()
				.stream()
				.map(wfNodePara -> createProcessInfoParameter(wfNodePara))
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableList());
	}

	@Nullable
	private ProcessInfoParameter createProcessInfoParameter(@NonNull final WFNodeParameter nPara)
	{
		final String attributeName = nPara.getAttributeName();
		final String attributeValue = nPara.getAttributeValue();
		log.debug("{} = {}", attributeName, attributeValue);

		// Value - Constant/Variable
		Object value = attributeValue;
		if (attributeValue == null || attributeValue.isEmpty())
		{
			value = null;
		}
		else if (attributeValue.indexOf('@') != -1)    // we have a variable
		{
			// Strip
			int index = attributeValue.indexOf('@');
			String columnName = attributeValue.substring(index + 1);
			index = columnName.indexOf('@');
			if (index == -1)
			{
				log.warn(attributeName + " - cannot evaluate=" + attributeValue);
				return null;
			}
			columnName = columnName.substring(0, index);
			if (context.hasDocumentColumn(getDocumentRef(), columnName))
			{
				value = context.getDocumentColumnValueByColumnName(getDocumentRef(), columnName);
			}
			else
			// not a column
			{
				// try Env
				final String env = Env.getContext(Env.getCtx(), columnName);
				if (Check.isEmpty(env))
				{
					log.warn("{} - not column nor environment ={}({})", attributeName, columnName, attributeValue);
					return null;
				}
				else
				{
					value = env;
				}
			}
		}    // @variable@

		// No Value
		if (value == null)
		{
			if (nPara.isMandatory())
			{
				log.warn("{} - empty - mandatory!", attributeName);
			}
			else
			{
				log.debug("{} - empty", attributeName);
			}
			return null;
		}

		// Convert to Type
		try
		{
			final int displayType = nPara.getValueType().getRepoId();
			if (DisplayType.isNumeric(displayType)
					|| DisplayType.isID(displayType))
			{
				final BigDecimal bd = NumberUtils.asBigDecimal(value);
				return ProcessInfoParameter.of(attributeName, bd);
			}
			else if (DisplayType.isDate(displayType))
			{
				final Timestamp ts;
				if (value instanceof Timestamp)
				{
					ts = (Timestamp)value;
				}
				else
				{
					ts = Timestamp.valueOf(value.toString());
				}
				return ProcessInfoParameter.of(attributeName, ts);
			}
			else
			{
				return ProcessInfoParameter.of(attributeName, value.toString());
			}
		}
		catch (final Exception e)
		{
			log.warn("Failed on {} = {} ({})", attributeName, attributeValue, value, e);
			return null;
		}
	}

	private void sendEMail()
	{
		final WFNode wfNode = getNode();
		final MailTemplateId mailTemplateId = wfNode.getMailTemplateId();
		final MailTextBuilder mailTextBuilder = context.newMailTextBuilder(getDocumentRef(), mailTemplateId);
		//
		final IDocument doc = getDocument();
		final String subject = doc.getDocumentInfo()
				+ ": " + mailTextBuilder.getMailHeader();
		final String message = mailTextBuilder.getFullMailText()
				+ "\n-----\n" + doc.getDocumentInfo()
				+ "\n" + doc.getSummary();
		final File pdf = doc.createPDF();
		//
		final MClient client = MClient.get(doc.getCtx(), doc.getAD_Client_ID());

		// Explicit EMail
		sendEMail(
				client,
				(UserId)null,
				wfNode.getEmailTo(),
				subject,
				message,
				pdf,
				mailTextBuilder.isHtml());
		// Recipient Type
		final WFNodeEmailRecipient emailRecipient = wfNode.getEmailRecipient();
		// email to document user
		if (emailRecipient == null)
		{
			final UserId docUserId = UserId.ofRepoIdOrNull(doc.getDoc_User_ID());
			sendEMail(client, docUserId, null, subject, message, pdf, mailTextBuilder.isHtml());
		}
		else if (emailRecipient.equals(WFNodeEmailRecipient.DocumentBusinessPartner))
		{
			if (context.hasDocumentColumn(getDocumentRef(), "AD_User_ID"))
			{
				final Object oo = context.getDocumentColumnValueByColumnName(getDocumentRef(), "AD_User_ID");
				if (oo instanceof Integer)
				{
					final UserId userId = UserId.ofRepoIdOrNull((Integer)oo);
					if (userId != null)
					{
						sendEMail(client, userId, null, subject, message, pdf, mailTextBuilder.isHtml());
					}
					else
					{
						log.debug("No User in Document");
					}
				}
				else
				{
					log.debug("Empty User in Document");
				}
			}
			else
			{
				log.debug("No User Field in Document");
			}
		}
		else if (emailRecipient.equals(WFNodeEmailRecipient.DocumentOwner))
		{
			final UserId docUserId = UserId.ofRepoIdOrNull(doc.getDoc_User_ID());
			sendEMail(client, docUserId, null, subject, message, pdf, mailTextBuilder.isHtml());
		}
		else if (emailRecipient.equals(WFNodeEmailRecipient.WorkflowResponsible))
		{
			final WFResponsible resp = getResponsible();
			if (resp.isInvoker())
			{
				final UserId docUserId = UserId.ofRepoIdOrNull(doc.getDoc_User_ID());
				sendEMail(client, docUserId, null, subject, message, pdf, mailTextBuilder.isHtml());
			}
			else if (resp.isHuman())
			{
				final UserId docUserId = UserId.ofRepoIdOrNull(doc.getDoc_User_ID());
				sendEMail(client, docUserId, null, subject, message, pdf, mailTextBuilder.isHtml());
			}
			else if (resp.isRole())
			{
				for (final UserId adUserId : context.getUserIdsByRoleId(resp.getRoleId()))
				{
					sendEMail(client, adUserId, null, subject, message, pdf, mailTextBuilder.isHtml());
				}
			}
			else if (resp.isOrganization())
			{
				final OrgId orgId = context.getDocumentOrgId(getDocumentRef());
				final OrgInfo org = context.getOrgInfoById(orgId);
				if (org.getSupervisorId() == null)
				{
					log.debug("No Supervisor for AD_Org_ID={}", orgId);
				}
				else
				{
					sendEMail(client, org.getSupervisorId(), null, subject, message, pdf, mailTextBuilder.isHtml());
				}
			}
		}
	}    // sendEMail

	/**
	 * Send actual EMail
	 *
	 * @param client  client
	 * @param userId  user
	 * @param email   email string
	 * @param subject subject
	 * @param message message
	 * @param pdf     attachment
	 * @param isHtml  isHtml
	 */
	private void sendEMail(
			final MClient client,
			final UserId userId,
			EMailAddress email,
			final String subject,
			final String message,
			final File pdf,
			final boolean isHtml)
	{
		if (userId != null)
		{
			final I_AD_User user = context.getUserById(userId);
			email = EMailAddress.ofNullableString(user.getEMail());
			if (email != null)
			{
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
		else if (email != null)
		{
			if (!m_emails.contains(email))
			{
				client.sendEMail(email, subject, message, pdf, isHtml);
				m_emails.add(email);
			}
		}
		else
		{
			log.warn("No userId or email provided");
		}
	}    // sendEMail

	/**
	 * User String Representation.
	 * Suspended: Approve it (Joe)
	 */
	@Deprecated
	private String toStringX()
	{
		final StringBuilder sb = new StringBuilder();

		sb.append(getState()).append(": ").append(getNode().getName());

		final UserId userId = getUserId();
		if (userId != null)
		{
			final String userFullname = context.getUserFullnameById(userId);
			sb.append(" (").append(userFullname).append(")");
		}

		return sb.toString();
	}
}
