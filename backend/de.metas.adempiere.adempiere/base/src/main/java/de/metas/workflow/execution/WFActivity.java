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

import de.metas.ad_reference.ReferenceId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.email.EMailAddress;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.error.AdIssueId;
import de.metas.i18n.ADMessageAndParams;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.ReportResultData;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import de.metas.workflow.WFAction;
import de.metas.workflow.WFEventAudit;
import de.metas.workflow.WFEventAuditType;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import de.metas.workflow.WFNodeEmailRecipient;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeParameter;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.execution.approval.strategy.WFApprovalStrategy;
import de.metas.workflow.execution.approval.strategy.impl.RequestorHierarcyProjectManagerPlusCTO_ApprovalStrategy;
import lombok.Getter;
import lombok.NonNull;
import lombok.With;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.model.MNote;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class WFActivity
{
	private static final Logger log = LogManager.getLogger(WFActivity.class);

	private static final AdMessageKey MSG_DocumentApprovalRequest = AdMessageKey.of("DocumentApprovalRequest");
	private static final AdMessageKey MSG_DocumentSentToApproval = AdMessageKey.of("DocumentSentToApproval");
	//private static final AdMessageKey MSG_NotApproved = AdMessageKey.of("NotApproved");

	@NonNull private final WorkflowExecutionContext context;
	@NonNull private final WFProcess wfProcess;
	@NonNull private final WFNode wfNode;
	@NonNull @Getter private final TableRecordReference documentRef;

	@Nullable private Optional<String> m_newValue;
	private ArrayList<EMailAddress> m_emails = new ArrayList<>();

	private final Instant startTime;

	@NonNull
	private final WFActivityState state;

	public WFActivity(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFNodeId nodeId)
	{
		this.context = wfProcess.getContext();
		this.wfProcess = wfProcess;
		this.wfNode = wfProcess.getWorkflow().getNodeById(nodeId);
		this.documentRef = wfProcess.getDocumentRef();
		this.startTime = SystemTime.asInstant();

		// Node Priority & End Duration
		int priority = wfProcess.getPriority();
		if (wfNode.getPriority() > 0)
		{
			priority = wfNode.getPriority();
		}

		// Responsible
		final WFResponsibleId wfResponsibleId = wfNode.getResponsibleId() != null
				? wfNode.getResponsibleId()
				: wfProcess.getWfResponsibleId();
		final WFResponsible resp = context.getResponsibleById(wfResponsibleId);

		// User - Directly responsible
		UserId userId = resp.getUserId();
		// Invoker - get Sales Rep or last updater of document
		if (userId == null || resp.isInvoker())
		{
			userId = wfProcess.getUserId();
		}

		final Duration durationLimit = wfNode.getDurationLimit();
		final Instant endWaitTime = !durationLimit.isZero()
				? startTime.plus(durationLimit)
				: null;

		this.state = WFActivityState.builder()
				.id(null)
				.wfNodeId(wfNode.getId())
				.priority(priority)
				.wfState(WFState.NotStarted)
				.processed(false)
				.wfResponsibleId(wfResponsibleId)
				.userId(userId)
				.endWaitTime(endWaitTime)
				.build();
	}

	WFActivity(
			@NonNull final WFProcess wfProcess,
			@NonNull final WFActivityState state)
	{
		this.context = wfProcess.getContext();
		this.wfProcess = wfProcess;
		this.wfNode = wfProcess.getWorkflow().getNodeById(state.getWfNodeId());
		this.documentRef = wfProcess.getDocumentRef();
		this.startTime = SystemTime.asInstant();
		this.state = state;
	}

	@NonNull
	private WFProcess getWorkflowProcess() {return wfProcess;}

	@NonNull
	public WFProcessId getWfProcessId() {return getWorkflowProcess().getWfProcessId();}

	public WorkflowId getWorkflowId() {return getWorkflowProcess().getWorkflowId();}

	@Nullable
	public WFActivityId getId() {return state.getId();}

	void setId(@NonNull final WFActivityId id) {state.setId(id);}

	public int getPriority() {return state.getPriority();}

	public WFState getState()
	{
		return state.getWfState();
	}

	/**
	 * Set Activity State.
	 * It also validates the new state and if is valid,
	 * then create event audit and call {@link WFProcess#checkActivities()}
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

		if (getState().isValidNewState(wfState))
		{
			log.debug("{} -> {}, Msg={}", getState(), wfState, getTextMsg());
			state.setWfState(wfState);
			//context.save(this);

			logAuditStateChanged();

			// Inform Process
			final WFProcess wfProcess = getWorkflowProcess();
			wfProcess.checkActivities();
		}
		else
		{
			final String msg = "Set WFState - Ignored Invalid Transformation - New=" + wfState + ", Current=" + getState();
			log.error(msg);
			addTextMsg(msg);
			//context.save(this);
			// TODO: teo_sarca: throw exception ? please analyze the call hierarchy first
		}
	}    // setWFState

	@Nullable
	public UserId getUserId() {return state.getUserId();}

	public void setUserId(@NonNull final UserId userId) {state.setUserId(userId);}

	private void setPriority(final int priority) {state.setPriority(priority);}

	private void setEndWaitTime(@NonNull final Instant endWaitTime) {state.setEndWaitTime(endWaitTime);}

	@Nullable
	public Instant getEndWaitTime() {return state.getEndWaitTime();}

	public Duration getElapsedTime() {return Duration.between(startTime, SystemTime.asInstant());}

	public boolean isProcessed() {return state.isProcessed();}

	void setProcessed() {state.setProcessed(true);}

	/**
	 * @return true if closed
	 */
	public boolean isClosed()
	{
		return getState().isClosed();
	}

	private WFEventAudit.WFEventAuditBuilder prepareEventAudit(@NonNull final WFEventAuditType eventType)
	{
		final WFNode node = getNode();

		final WFEventAudit.WFEventAuditBuilder builder = WFEventAudit.builder()
				.eventType(eventType)
				//
				.orgId(OrgId.ANY)
				.wfProcessId(getWorkflowProcess().getWfProcessId())
				.wfNodeId(node.getId())
				.documentRef(getDocumentRef())
				//
				.wfResponsibleId(getWFResponsibleId())
				.userId(getUserId())
				//
				.wfState(getState())
				.elapsedTime(getElapsedTime())
				//
				.textMsg(getTextMsg());

		final WFNodeAction action = node.getAction();
		if (WFNodeAction.SetVariable.equals(action))
		{
			builder.attributeName(node.getDocumentColumnName());
			builder.attributeValueOld(String.valueOf(getAttributeValue()));
			builder.attributeValueNew(node.getAttributeValue());
		}
		else if (WFNodeAction.UserChoice.equals(action))
		{
			builder.attributeName(node.getDocumentColumnName());
			builder.attributeValueOld(String.valueOf(getAttributeValue()));
		}

		return builder;
	}

	private void logAuditActivityStarted()
	{
		context.addEventAudit(prepareEventAudit(WFEventAuditType.ProcessCreated).build());
	}

	private void logAuditStateChanged()
	{
		final WFEventAuditType eventType = getState().isClosed()
				? WFEventAuditType.ProcessCompleted
				: WFEventAuditType.StateChanged;

		final WFEventAudit audit = prepareEventAudit(eventType).build();
		//noinspection OptionalAssignedToNull
		if (m_newValue != null)
		{
			audit.setAttributeValueNew(m_newValue.orElse(null));
		}

		context.addEventAudit(audit);
	}

	private IDocument getDocument() {return context.getDocument(documentRef);}

	public IDocument getDocumentOrNull() {return context.getDocumentOrNull(documentRef);}

	public Object getDocumentColumnValueByColumnId(final AdColumnId adColumnId) {return context.getDocumentColumnValueByColumnId(getDocumentRef(), adColumnId);}

	public Object getDocumentColumnValueByColumnName(final String columnName) {return context.getDocumentColumnValueByColumnName(getDocumentRef(), columnName);}

	/**
	 * @return Attribute Value (based on Node) of PO
	 */
	@Nullable
	Object getAttributeValue()
	{
		final WFNode node = getNode();
		final AdColumnId AD_Column_ID = AdColumnId.ofRepoIdOrNull(node.getDocumentColumnId());
		if (AD_Column_ID == null)
		{
			return null;
		}

		return context.getDocumentColumnValueByColumnId(getDocumentRef(), AD_Column_ID);
	}

	@NonNull
	public WFNode getNode() {return wfNode;}

	@Nullable
	public String getTextMsg() {return state.getTextMsg();}

	private void setTextMsg(@Nullable final String textMsg) {state.setTextMsg(textMsg);}

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

	private void setIssueId(@NonNull final AdIssueId issueId) {state.setIssueId(issueId);}

	@Nullable
	public AdIssueId getIssueId() {return state.getIssueId();}

	/**
	 * Set Responsible and User from Process / Node
	 */
	WFResponsibleId getWFResponsibleId() {return state.getWfResponsibleId();}

	private WFResponsible getResponsible() {return context.getResponsibleById(getWFResponsibleId());}

	public void start()
	{
		run(WFAction.Start);
	}

	public void resume()
	{
		run(WFAction.Resume);
	}

	private void run(@NonNull WFAction action)
	{
		logAuditActivityStarted();

		//noinspection OptionalAssignedToNull
		m_newValue = null;

		context.getTrxManager().runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			private boolean docStatusChanged = false;
			private Throwable exception = null;

			@Override
			public void run(final String localTrxName)
			{
				if (!getState().isValidAction(action))
				{
					addTextMsg(new AdempiereException("Cannot " + action + " activity from state " + getState()));
					changeWFStateTo(WFState.Terminated);
					return;
				}
				//
				changeWFStateTo(WFState.Running);

				// Do Work
				final PerformWorkResult result = performWork();
				docStatusChanged = result.newDocStatus() != null;
				changeWFStateTo(result.newWFState());
			}

			@Override
			public boolean doCatch(final Throwable ex)
			{
				this.exception = ex;
				wfProcess.setProcessingResultMessage(ex);

				addTextMsg(ex);
				changeWFStateTo(WFState.Terminated);

				return ROLLBACK;
			}

			@Override
			public void doFinally()
			{
				// context.save(WFActivity.this);

				// If document status was changed in on this node then we have to set it to Invalid.
				// If this node didn't touch the document status then let's preserve it.
				if (exception != null && docStatusChanged)
				{
					try
					{
						context.setDocStatus(getDocumentRef(), DocStatus.Invalid);
					}
					catch (final Exception ex)
					{
						log.warn("Failed setting document's DocStatus to invalid but IGNORED: {}", getDocumentRef());
					}
				}
			}
		});
	}

	private record PerformWorkResult(@NonNull WFState newWFState, @Nullable @With DocStatus newDocStatus)
	{
		public static final PerformWorkResult COMPLETED = new PerformWorkResult(WFState.Completed, null);
		public static final PerformWorkResult SUSPENDED = new PerformWorkResult(WFState.Suspended, null);
		public static final PerformWorkResult ABORTED = new PerformWorkResult(WFState.Aborted, null);
	}

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
			throw new AdempiereException("Action not handled: " + action);
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
				if (!subject.isEmpty())
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
		final UserId userId = Check.assumeNotNull(getUserId(), "User is set: {}", state);

		log.debug("Report: AD_Process_ID={}", wfNode.getProcessId());
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setClientId(context.getClientId())
				.setUserId(userId)
				.setAD_Process_ID(wfNode.getProcessId())
				.setTitle(wfNode.getName().getDefaultValue())
				.setRecord(getDocumentRef())
				.addParameters(createProcessInfoParameters())
				.build();
		if (!pi.isReportingProcess())
		{
			throw new IllegalStateException("Not a Report AD_Process_ID=" + pi);
		}

		final ReportResultData reportData = pi.getResult().getReportData();
		if (reportData == null)
		{
			throw new AdempiereException("Cannot create Report AD_Process_ID=" + wfNode.getProcessId());
		}

		final File report = reportData.writeToTemporaryFile("report_");
		// Notice
		final int AD_Message_ID = 753;        // HARDCODED WorkflowResult
		final MNote note = new MNote(Env.getCtx(), AD_Message_ID, userId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
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

		final IDocument document = context.processDocument(getDocumentRef(), docAction);

		addTextMsg(document.getSummary());
		wfProcess.setProcessingResultMessage(document.getProcessMsg());

		return PerformWorkResult.COMPLETED.withNewDocStatus(DocStatus.ofCode(document.getDocStatus()));
	}

	private void performWork_SetVariable()
	{
		final WFNode wfNode = getNode();
		final String value = wfNode.getAttributeValue();
		log.debug("SetVariable:AD_Column_ID={} to {}", wfNode.getDocumentColumnId(), value);
		final ReferenceId dt = wfNode.getDocumentColumnValueType();
		setVariable(value, dt.getRepoId());
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

		final WFApprovalStrategy approvalStrategy = getApprovalStrategy();
		final WFApprovalStrategy.Request request = newWFApprovalRequest();
		final TableRecordReference documentRef = getDocumentRef();

		final WFApprovalStrategy.Response response = approvalStrategy.approve(request);
		return response.map(new WFApprovalStrategy.Response.CaseMapper<>()
		{
			@Override
			public PerformWorkResult approved()
			{
				context.processDocument(documentRef, IDocument.ACTION_Approve);
				return PerformWorkResult.COMPLETED;
			}

			@Override
			public PerformWorkResult rejected()
			{
				return PerformWorkResult.ABORTED;
			}

			public PerformWorkResult pending() {return PerformWorkResult.SUSPENDED;}

			@Override
			public PerformWorkResult forwardTo(@NonNull final UserId forwardToUserId, @Nullable TableRecordReference documentToOpen)
			{
				WFActivity.this.forwardTo(forwardToUserId, msgApprovalRequest(), documentToOpen);

				context.sendNotification(WFUserNotification.builder()
						.userId(request.getWorkflowInvokerId())
						.content(MSG_DocumentSentToApproval, documentRef, context.getUserFullnameById(forwardToUserId))
						.documentToOpen(documentToOpen)
						.build());

				return PerformWorkResult.SUSPENDED;
			}
		});
	}

	private WFApprovalStrategy.Request newWFApprovalRequest()
	{
		final TableRecordReference documentRef = getDocumentRef();
		final IDocument document = context.getDocument(documentRef);
		final UserId documentOwnerId = UserId.ofRepoId(document.getDoc_User_ID());
		final UserId invokerId = CoalesceUtil.coalesceNotNull(getUserId(), documentOwnerId);

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(document.getC_Currency_ID());
		final Money amountToApprove = currencyId != null
				? Money.of(document.getApprovalAmt(), currencyId)
				: null;

		return WFApprovalStrategy.Request.builder()
				.context(context)
				.documentRef(documentRef)
				.documentOwnerId(documentOwnerId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(document.getAD_Client_ID(), document.getAD_Org_ID()))
				.documentNo(document.getDocumentNo())
				.docBaseType(context.getDocBaseType(document).orElse(null))
				//
				.amountToApprove(amountToApprove)
				//
				.workflowInvokerId(invokerId)
				.responsible(getResponsible())
				.wfProcessId(getWfProcessId())
				.wfActivityId(getId())
				.build();
	}

	private WFApprovalStrategy getApprovalStrategy()
	{
		// TODO
		//return SpringContextHolder.instance.getBean(StandardApprovalStrategy.class);
		return SpringContextHolder.instance.getBean(RequestorHierarcyProjectManagerPlusCTO_ApprovalStrategy.class);
	}

	private ADMessageAndParams msgApprovalRequest()
	{
		final String invokerName = context.getUserFullname();
		return ADMessageAndParams.of(MSG_DocumentApprovalRequest, invokerName, getDocumentRef());
	}

	private void setVariable(@Nullable final String valueStr, final int displayType)
	{
		//noinspection OptionalAssignedToNull
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
		addTextMsg(nodeColumnName + "=" + valueStr);
		m_newValue = Optional.ofNullable(valueStr);
	}    // setVariable

	private void forwardTo(
			@NonNull final UserId newUserId,
			@NonNull final ADMessageAndParams subject,
			@Nullable TableRecordReference documentToOpen)
	{
		final UserId oldUserId = getUserId();
		if (UserId.equals(newUserId, oldUserId))
		{
			log.debug("Asked to forward to same user `{}`. Do nothing.", newUserId);
			return;
		}

		// Update
		setUserId(newUserId);
		//addTextMsg();
		//context.save(this);

		//
		// Notify user
		context.sendNotification(WFUserNotification.builder()
				.userId(newUserId)
				.content(subject)
				.documentToOpen(CoalesceUtil.coalesce(documentToOpen, getDocumentRef()))
				.build());

		context.addEventAudit(prepareEventAudit(WFEventAuditType.StateChanged)
				.userId(oldUserId)
				.attributeName("AD_User_ID")
				.attributeValueOld(buildUserSummary(oldUserId))
				.attributeValueNew(buildUserSummary(newUserId))
				.build());
	}

	@Nullable
	private String buildUserSummary(@Nullable final UserId userId)
	{
		if (userId == null)
		{
			return null;
		}

		final String name = context.getUserFullnameById(userId);
		return name + " (" + userId.getRepoId() + ")";
	}

	private List<ProcessInfoParameter> createProcessInfoParameters()
	{
		return getNode()
				.getProcessParameters()
				.stream()
				.map(this::createProcessInfoParameter)
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
				null,
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
				for (final UserId adUserId : context.getUserIdsByRoleId(resp.getRoleIdNotNull()))
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
	}
}
