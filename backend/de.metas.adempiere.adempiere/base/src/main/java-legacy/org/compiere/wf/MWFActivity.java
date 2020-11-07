package org.compiere.wf;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.email.EMailAddress;
import de.metas.email.MailService;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.event.Topic;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.reflist.ReferenceId;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.time.SystemTime;
import de.metas.workflow.WFAction;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import de.metas.workflow.WFNodeEmailRecipient;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeParameter;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.service.IADWorkflowDAO;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_WF_Activity;
import org.compiere.model.I_AD_WF_EventAudit;
import org.compiere.model.MClient;
import org.compiere.model.MNote;
import org.compiere.model.PO;
import org.compiere.model.X_AD_WF_Activity;
import org.compiere.print.ReportEngine;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * Workflow Activity Model.
 * Controlled by WF Process:
 * set Node - startWork
 *
 * @author Jorg Janke
 * @version $Id: MWFActivity.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFActivity extends X_AD_WF_Activity
{
	private static final long serialVersionUID = 2987002047442429221L;

	private static final Logger log = LogManager.getLogger(MWFActivity.class);
	private static final Topic USER_NOTIFICATIONS_TOPIC = Topic.remote("de.metas.document.UserNotifications");
	private static final AdMessageKey MSG_NotApproved = AdMessageKey.of("NotApproved");

	private WFNode _node = null; // lazy
	private I_AD_WF_EventAudit _audit = null;
	private PO _po = null; // lazy
	private String m_newValue = null;
	private MWFProcess _wfProcess = null; // lazy
	private ArrayList<EMailAddress> m_emails = new ArrayList<>();

	public MWFActivity(final Properties ctx, final int AD_WF_Activity_ID)
	{
		this(ctx, AD_WF_Activity_ID, ITrx.TRXNAME_None);
	}

	@Deprecated
	public MWFActivity(final Properties ctx, final int AD_WF_Activity_ID, final String trxName)
	{
		super(ctx, AD_WF_Activity_ID, trxName);
		if (is_new())
		{
			throw new AdempiereException("Cannot create new WF Activity directly");
		}
	}

	public MWFActivity(final Properties ctx, final ResultSet rs)
	{
		this(ctx, rs, ITrx.TRXNAME_None);
	}

	@Deprecated
	public MWFActivity(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MWFActivity(
			@NonNull final MWFProcess process,
			@NonNull final WFNodeId nodeId)
	{
		super(process.getCtx(), 0, ITrx.TRXNAME_None); // always out-of-trx
		setAD_WF_Process_ID(process.getAD_WF_Process_ID());
		setPriority(process.getPriority());
		// Document Link
		setAD_Table_ID(process.getAD_Table_ID());
		setRecord_ID(process.getRecord_ID());
		// modified by Rob Klein
		setAD_Client_ID(process.getAD_Client_ID());
		setAD_Org_ID(process.getAD_Org_ID());
		// Status
		super.setWFState(WFState.NotStarted.getCode());
		setProcessed(false);
		// Set Workflow Node
		setAD_Workflow_ID(process.getAD_Workflow_ID());
		setWorkflowNodeAndResetToNotStarted(nodeId);
		// Node Priority & End Duration
		final WFNode node = getNode();
		final int priority = node.getPriority();
		if (priority != 0 && priority != getPriority())
		{
			setPriority(priority);
		}
		final Duration durationLimit = node.getDurationLimit();
		if (!durationLimit.isZero())
		{
			final Instant endWaitTime = SystemTime.asInstant().plus(durationLimit);
			setEndWaitTime(TimeUtil.asTimestamp(endWaitTime));
		}
		// Responsible
		setResponsible(process);
		saveEx();

		//
		newEventAudit();

		//
		this._wfProcess = process;
	}

	/**
	 * @param lastPO PO from the previously executed node
	 */
	public MWFActivity(
			@NonNull final MWFProcess process,
			@NonNull final WFNodeId nodeId,
			@Nullable final PO lastPO)
	{
		this(process, nodeId);
		if (lastPO != null)
		{
			// Compare if the last PO is the same type and record needed here, if yes, use it
			if (lastPO.get_Table_ID() == getAD_Table_ID() && lastPO.get_ID() == getRecord_ID())
			{
				this._po = lastPO;
				this._po.set_TrxName(ITrx.TRXNAME_ThreadInherited);
			}
		}
	}

	/**
	 * @return activity summary
	 */
	@Nullable
	public static String getActiveInfo(final int AD_Table_ID, final int Record_ID)
	{
		final ImmutableList<MWFActivity> acts = getActiveActivities(AD_Table_ID, Record_ID);
		if (acts.isEmpty())
		{
			return null;
		}
		//
		final StringBuilder sb = new StringBuilder();
		for (final MWFActivity activity : acts)
		{
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append(activity.toStringX());
		}
		return sb.toString();
	}

	private static ImmutableList<MWFActivity> getActiveActivities(final int AD_Table_ID, final int Record_ID)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilderOutOfTrx(I_AD_WF_Activity.class)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_AD_Table_ID, AD_Table_ID)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_AD_Table_ID, Record_ID)
				.addEqualsFilter(I_AD_WF_Activity.COLUMNNAME_Processed, false)
				.orderBy(I_AD_WF_Activity.COLUMNNAME_AD_WF_Activity_ID)
				.create()
				.listImmutable(MWFActivity.class);
	}

	@NonNull
	private MWFProcess getWorkflowProcess()
	{
		MWFProcess wfProcess = this._wfProcess;
		if (wfProcess == null)
		{
			wfProcess = this._wfProcess = new MWFProcess(getCtx(), getAD_WF_Process_ID());
		}
		return wfProcess;
	}

	WFState getState()
	{
		return WFState.ofCode(getWFState());
	}    // getState

	@Override
	@Deprecated
	public void setWFState(final String wfState)
	{
		changeWFStateTo(WFState.ofCode(wfState));
	}

	/**
	 * Set Activity State.
	 * It also validates the new state and if is valid,
	 * then create event audit and call {@link MWFProcess#checkActivities(PO)}
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
			final String oldState = getWFState();
			log.debug(oldState + "->" + wfState + ", Msg=" + getTextMsg());
			super.setWFState(wfState.getCode());
			saveEx();            // closed in MWFProcess.checkActivities()
			updateEventAudit();

			// Inform Process
			final MWFProcess wfProcess = getWorkflowProcess();
			wfProcess.checkActivities(getPONoLoad());
		}
		else
		{
			final String msg = "Set WFState - Ignored Invalid Transformation - New=" + wfState + ", Current=" + getWFState();
			log.error(msg);
			addTextMsg(msg);
			saveEx();
			// TODO: teo_sarca: throw exception ? please analyze the call hierarchy first
		}
	}    // setWFState

	/**
	 * @return true if closed
	 */
	public boolean isClosed()
	{
		return getState().isClosed();
	}

	private void updateEventAudit()
	{
		final I_AD_WF_EventAudit audit = getEventAudit();
		audit.setTextMsg(getTextMsg());
		audit.setWFState(getWFState());
		if (m_newValue != null)
		{
			audit.setNewValue(m_newValue);
		}
		if (getState().isClosed())
		{
			audit.setEventType(MWFEventAudit.EVENTTYPE_ProcessCompleted);
			final long ms = System.currentTimeMillis() - audit.getCreated().getTime();
			audit.setElapsedTimeMS(new BigDecimal(ms));
		}
		else
		{
			audit.setEventType(MWFEventAudit.EVENTTYPE_StateChanged);
		}

		InterfaceWrapperHelper.save(audit);
	}    // updateEventAudit

	/**
	 * Get/Create Event Audit
	 */
	private I_AD_WF_EventAudit getEventAudit()
	{
		I_AD_WF_EventAudit audit = this._audit;
		if (audit == null)
		{
			final List<I_AD_WF_EventAudit> events = MWFEventAudit.getByWFNode(getAD_WF_Process_ID(), getAD_WF_Node_ID());
			if (events.isEmpty())
			{
				audit = new MWFEventAudit(this);
			}
			else
			{
				audit = events.get(events.size() - 1);        // last event
			}

			this._audit = audit;
		}

		return audit;
	}

	private void newEventAudit()
	{
		final MWFEventAudit audit = new MWFEventAudit(this);
		audit.saveEx();

		this._audit = audit;
	}

	private PO getPONoLoad()
	{
		return this._po;
	}

	/**
	 * Get Persistent Object
	 */
	public PO getPO()
	{
		PO po = this._po;
		if (po != null)
		{
			po.set_TrxName(ITrx.TRXNAME_ThreadInherited);
			return po;
		}
		else
		{
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(getAD_Table_ID());
			po = this._po = TableModelLoader.instance.getPO(getCtx(), tableName, getRecord_ID(), ITrx.TRXNAME_ThreadInherited);
			return po;
		}
	}    // getPO

	private IDocument getDocument()
	{
		final PO po = getPO();
		if (po == null)
		{
			throw new AdempiereException("Persistent Object not found - AD_Table_ID=" + getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
		}

		return Services.get(IDocumentBL.class).getDocument(po);
	}

	public IDocument getDocumentOrNull()
	{
		final PO po = getPO();
		if (po == null)
		{
			throw new AdempiereException("Persistent Object not found - AD_Table_ID=" + getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
		}

		return Services.get(IDocumentBL.class).getDocumentOrNull(po);
	}

	Optional<ClientId> getPO_AD_Client_ID()
	{
		final PO po = getPO();
		return po != null
				? ClientId.optionalOfRepoId(po.getAD_Client_ID())
				: Optional.empty();
	}

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
		final PO po = getPO();
		if (po.get_ID() <= 0)
		{
			return null;
		}
		return po.get_ValueOfColumn(AD_Column_ID);
	}    // getAttributeValue

	/**
	 * @return SO Trx
	 */
	public boolean isSOTrx()
	{
		final PO po = getPO();
		if (po == null || po.get_ID() <= 0)
		{
			return true;
		}

		// Is there a Column?
		final int index = po.get_ColumnIndex("IsSOTrx");
		if (index < 0)
		{
			if (po.get_TableName().startsWith("M_"))
			{
				return false;
			}
			return true;
		}
		else
		{
			return po.get_ValueAsBoolean(index);
		}
	}    // isSOTrx

	private void setWorkflowNodeAndResetToNotStarted(@NonNull final WFNodeId nodeId)
	{
		super.setAD_WF_Node_ID(nodeId.getRepoId());

		if (!WFState.NotStarted.equals(getState()))
		{
			super.setWFState(WFState.NotStarted.getCode());
		}
		if (isProcessed())
		{
			setProcessed(false);
		}
	}

	public WFNode getNode()
	{
		WFNode node = this._node;
		if (node == null)
		{
			final WorkflowId workflowId = WorkflowId.ofRepoId(getAD_Workflow_ID());
			final WFNodeId nodeId = WFNodeId.ofRepoId(getAD_WF_Node_ID());

			final Workflow workflow = Services.get(IADWorkflowDAO.class).getById(workflowId);
			node = this._node = workflow.getNodeById(nodeId);
		}
		return node;
	}

	void addTextMsg(@Nullable final String textMsg)
	{
		if (textMsg == null || Check.isBlank(textMsg))
		{
			return;
		}

		final String oldText = StringUtils.trimBlankToNull(getTextMsg());
		if (oldText == null || Check.isBlank(oldText))
		{
			super.setTextMsg(StringUtils.trunc(textMsg.trim(), 1000));
		}
		else
		{
			super.setTextMsg(StringUtils.trunc(oldText.trim() + "\n" + textMsg.trim(), 1000));
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
	 * Get WF State text
	 *
	 * @return state text
	 */
	private String getWFStateText()
	{
		return Services.get(IADReferenceDAO.class).retrieveListNameTrl(getCtx(), WFSTATE_AD_Reference_ID, getWFState());
	}    // getWFStateText

	/**
	 * Set Responsible and User from Process / Node
	 */
	private void setResponsible(@NonNull final MWFProcess process)
	{
		// Responsible
		WFResponsibleId responsibleId = getNode().getResponsibleId();
		if (responsibleId == null)
		{
			responsibleId = WFResponsibleId.ofRepoId(process.getAD_WF_Responsible_ID());
		}
		setAD_WF_Responsible_ID(responsibleId.getRepoId());
		final WFResponsible resp = getResponsible();

		// User - Directly responsible
		UserId userId = resp.getUserId();
		// Invoker - get Sales Rep or last updater of document
		if (userId == null || resp.isInvoker())
		{
			userId = UserId.ofRepoId(process.getAD_User_ID());
		}

		setAD_User_ID(userId.getRepoId());
	}

	private WFResponsible getResponsible()
	{
		final WFResponsibleId responsibleId = WFResponsibleId.ofRepoId(getAD_WF_Responsible_ID());
		return Services.get(IADWorkflowDAO.class).getWFResponsibleById(responsibleId);
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

	private DocumentToApproveRequest toDocumentToApproveRequest(final IDocument doc)
	{
		final UserId documentOwnerId = UserId.ofRepoId(doc.getDoc_User_ID());
		final UserId invokerId = CoalesceUtil.coalesce(
				UserId.ofRepoIdOrNullIfSystem(getAD_User_ID()),
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
		final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

		final List<IUserRolePermissions> roles = userRolePermissionsDAO.retrieveUserRolesPermissionsForUserWithOrgAccess(
				document.getClientAndOrgId().getClientId(),
				document.getClientAndOrgId().getOrgId(),
				userId,
				Env.getLocalDate());
		for (final IUserRolePermissions role : roles)
		{
			if (isRoleAllowedToApproveDocument(role, userId, document))
			{
				return true;
			}
		}

		return false;
	}

	private static boolean isRoleAllowedToApproveDocument(
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

		//
		if (!CurrencyId.equals(amountToApprove.getCurrencyId(), maxAllowedAmount.getCurrencyId()))
		{
			final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
			final BigDecimal maxAllowedAmountConvBD = currencyBL.convert(// today & default rate
					maxAllowedAmount.toBigDecimal(),
					maxAllowedAmount.getCurrencyId(),
					amountToApprove.getCurrencyId(),
					document.getClientAndOrgId().getClientId(),
					document.getClientAndOrgId().getOrgId());
			if (maxAllowedAmountConvBD == null || maxAllowedAmountConvBD.signum() == 0)
			{
				// TODO: fail
				return false;
			}

			maxAllowedAmount = Money.of(maxAllowedAmountConvBD, amountToApprove.getCurrencyId());
		}

		return amountToApprove.isLessThanOrEqualTo(maxAllowedAmount);
	}

	private Optional<UserId> getSupervisorOfUserId(@NonNull final UserId userId)
	{
		final IUserDAO userDAO = Services.get(IUserDAO.class);

		final I_AD_User user = userDAO.getById(userId);
		final UserId supervisorId = UserId.ofRepoIdOrNullIfSystem(user.getSupervisor_ID());
		return Optional.ofNullable(supervisorId);
	}

	private Optional<UserId> getSupervisorOfOrgId(@NonNull final OrgId orgId)
	{
		final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

		OrgId currentOrgId = orgId;
		final HashSet<OrgId> alreadyCheckedOrgIds = new HashSet<>();
		while (currentOrgId != null)
		{
			if (!alreadyCheckedOrgIds.add(currentOrgId))
			{
				log.debug("Org look detected, returning empty: {}", alreadyCheckedOrgIds);
				return Optional.empty();
			}

			final OrgInfo orgInfo = orgsRepo.getOrgInfoById(currentOrgId);
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

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			private DocStatus newDocStatus = null;

			@Override
			public void run(final String localTrxName)
			{
				if (!getState().isValidAction(WFAction.Start))
				{
					addTextMsg(new AdempiereException("State=" + getWFState() + " - cannot start"));
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
				final PO po = getPONoLoad();
				final IDocument doc = po != null ? Services.get(IDocumentBL.class).getDocumentOrNull(po) : null;
				if (doc != null && newDocStatus != null)
				{
					InterfaceWrapperHelper.refresh(po);
					doc.setDocStatus(newDocStatus.getCode());
					InterfaceWrapperHelper.save(po);
				}

				return ROLLBACK;
			}

			@Override
			public void doFinally()
			{
				InterfaceWrapperHelper.save(MWFActivity.this);
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
			final PO po = getPO();

			final MailTemplateId mailTemplateId = getNode().getMailTemplateId();

			final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);
			final MailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId)
					.recordAndUpdateBPartnerAndContact(po);

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

			final MClient client = MClient.get(getCtx(), getAD_Client_ID());
			client.sendEMail(to, subject.toString(), message.toString(), null);
		}
	}

	private void performWork_AppsProcess()
	{
		final WFNode wfNode = getNode();
		log.debug("Process: AD_Process_ID={}", wfNode.getProcessId());

		ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Client_ID(getAD_Client_ID())
				.setAD_User_ID(getAD_User_ID())
				.setAD_Process_ID(wfNode.getProcessId())
				.setTitle(wfNode.getName().getDefaultValue())
				.setRecord(getAD_Table_ID(), getRecord_ID())
				.addParameters(createProcessInfoParameters(wfNode, getPO()))
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
				.setCtx(getCtx())
				.setAD_Client_ID(getAD_Client_ID())
				.setAD_User_ID(getAD_User_ID())
				.setAD_Process_ID(wfNode.getProcessId())
				.setTitle(wfNode.getName().getDefaultValue())
				.setRecord(getAD_Table_ID(), getRecord_ID())
				.addParameters(createProcessInfoParameters(wfNode, getPO()))
				.build();
		if (!pi.isReportingProcess())
		{
			throw new IllegalStateException("Not a Report AD_Process_ID=" + pi);
		}

		// Report
		final ReportEngine re = ReportEngine.get(getCtx(), pi);
		if (re == null)
		{
			throw new AdempiereException("Cannot create Report AD_Process_ID=" + wfNode.getProcessId());
		}
		final File report = re.getPDF();
		// Notice
		final int AD_Message_ID = 753;        // HARDCODED WorkflowResult
		final MNote note = new MNote(getCtx(), AD_Message_ID, getAD_User_ID(), ITrx.TRXNAME_ThreadInherited);
		note.setTextMsg(wfNode.getName().getDefaultValue());
		note.setDescription(wfNode.getDescription().getDefaultValue());
		note.setRecord(getAD_Table_ID(), getRecord_ID());
		note.saveEx();
		// Attachment

		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
		attachmentEntryService.createNewAttachment(note, report);
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
			setEndWaitTime(TimeUtil.asTimestamp(endWaitTime));
			return PerformWorkResult.SUSPENDED;
		}
	}

	private PerformWorkResult performWork_DocumentAction()
	{
		final WFNode wfNode = getNode();
		final String docAction = wfNode.getDocAction();
		final MWFProcess wfProcess = this.getWorkflowProcess();
		log.debug("DocumentAction={}", docAction);

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		final PO po = getPO();
		if (po == null)
		{
			throw new AdempiereException("Persistent Object not found - AD_Table_ID=" + getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
		}

		try
		{
			final IDocument doc = documentBL.getDocument(po);
			documentBL.processEx(doc, docAction);

			addTextMsg(doc.getSummary());
			wfProcess.setProcessMsg(doc.getProcessMsg());

			InterfaceWrapperHelper.save(po);

			return PerformWorkResult.builder()
					.resolution(PerformWorkResolution.COMPLETED)
					.newDocStatus(DocStatus.ofCode(doc.getDocStatus()))
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
		final IDocument doc = getDocumentOrNull();
		if (doc == null)
		{
			return PerformWorkResult.SUSPENDED;
		}

		final boolean autoApproval;

		// Approval Hierarchy
		final WFResponsible resp = getResponsible();
		if (resp.isInvoker())
		{
			final DocumentToApproveRequest documentToApprove = toDocumentToApproveRequest(doc);
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
				setAD_User_ID(approverId.getRepoId());
				autoApproval = false;
			}
		}
		else if (resp.isHuman())
		{
			final MWFProcess wfProcess = getWorkflowProcess();
			autoApproval = resp.getUserId() != null && resp.getUserId().getRepoId() == wfProcess.getAD_User_ID();
			if (!autoApproval && resp.getUserId() != null)
			{
				setAD_User_ID(resp.getUserId().getRepoId());
			}
		}
		else if (resp.isRole())
		{
			final MWFProcess wfProcess = getWorkflowProcess();

			final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

			final RoleId roleId = resp.getRoleId();
			final Set<UserId> allRoleUserIds = roleDAO.retrieveUserIdsForRoleId(roleId);
			if (allRoleUserIds.contains(UserId.ofRepoId(wfProcess.getAD_User_ID())))
			{
				autoApproval = true;
			}
			else
			{
				autoApproval = false;
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

		if (autoApproval)
		{
			final IDocumentBL documentBL = Services.get(IDocumentBL.class);
			documentBL.processEx(doc, IDocument.ACTION_Approve);

			return PerformWorkResult.COMPLETED;
		}
		else
		{
			return PerformWorkResult.SUSPENDED;
		}
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
		final PO po = getPO();
		if (po == null)
		{
			throw new AdempiereException("Persistent Object not found - AD_Table_ID=" + getAD_Table_ID() + ", Record_ID=" + getRecord_ID());
		}

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
		po.set_ValueOfColumn(nodeColumnName, dbValue);

		po.saveEx();
		if (dbValue != null && !dbValue.equals(po.get_ValueOfColumn(getNode().getDocumentColumnId())))
		{
			throw new AdempiereException("Persistent Object not updated - AD_Table_ID="
					+ getAD_Table_ID() + ", Record_ID=" + getRecord_ID()
					+ " - Should=" + valueStr + ", Is=" + po.get_ValueOfColumn(getNode().getDocumentColumnId()));
		}
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
		setAD_User_ID(userId.getRepoId());
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

		final IDocument doc = getDocumentOrNull();
		if (doc == null)
		{
			return newState;
		}

		try
		{
			// Not approved
			if (!approved)
			{
				newState = WFState.Aborted;
				if (!(doc.processIt(IDocument.ACTION_Reject)))
				{
					addTextMsg("Cannot Reject - Document Status: " + doc.getDocStatus());
				}
			}
			else
			{
				if (isInvoker())
				{
					final DocumentToApproveRequest documentToApprove = toDocumentToApproveRequest(doc);
					final UserId approverId = getApprovalUser(documentToApprove).orElse(null);

					// No Approver
					if (approverId == null)
					{
						newState = WFState.Aborted;
						addTextMsg("Cannot Approve - No Approver");
						doc.processIt(IDocument.ACTION_Reject);
					}
					// Another user is allowed to approve it => forward to that user
					else if (!UserId.equals(documentToApprove.getWorkflowInvokerId(), approverId))
					{
						forwardTo(approverId, "Next Approver");
						newState = WFState.Suspended;
					}
					else
					// Approve
					{
						if (!doc.processIt(IDocument.ACTION_Approve))
						{
							newState = WFState.Aborted;
							addTextMsg("Cannot Approve - Document Status: " + doc.getDocStatus());
						}
					}
				}
				// No Invoker - Approve
				else if (!doc.processIt(IDocument.ACTION_Approve))
				{
					newState = WFState.Aborted;
					addTextMsg("Cannot Approve - Document Status: " + doc.getDocStatus());
				}
			}

			doc.save();
		}
		catch (final Exception ex)
		{
			newState = WFState.Terminated;
			addTextMsg(ex);
			log.warn("", ex);
		}

		//
		// Send Not Approved Notification
		final UserId userId = UserId.ofRepoIdOrNullIfSystem(doc.getDoc_User_ID());
		if (newState.equals(WFState.Aborted) && userId != null)
		{
			final String docInfo = (doc.getSummary() != null ? doc.getSummary() + "\n" : "")
					+ (doc.getProcessMsg() != null ? doc.getProcessMsg() + "\n" : "")
					+ (getTextMsg() != null ? getTextMsg() : "");

			final INotificationBL notificationBL = Services.get(INotificationBL.class);
			notificationBL.sendAfterCommit(UserNotificationRequest.builder()
					.topic(USER_NOTIFICATIONS_TOPIC)
					.recipientUserId(userId)
					.contentADMessage(MSG_NotApproved)
					.contentADMessageParam(doc.toTableRecordReference())
					.contentADMessageParam(docInfo)
					.targetAction(TargetRecordAction.of(doc.toTableRecordReference()))
					.build());
		}

		return newState;
	}

	/**
	 * Forward To
	 */
	public void forwardTo(@NonNull final UserId userId, @Nullable final String textMsg)
	{
		if (userId.getRepoId() == getAD_User_ID())
		{
			log.warn("Asked to forward to same user `{}`. Do nothing.", userId);
			return;
		}
		//
		final IUserDAO userDAO = Services.get(IUserDAO.class);
		final UserId oldUserId = UserId.ofRepoIdOrSystem(getAD_User_ID());
		final I_AD_User oldUser = userDAO.getById(oldUserId);
		final I_AD_User user = userDAO.getById(userId);

		// Update
		setAD_User_ID(user.getAD_User_ID());
		addTextMsg(textMsg);
		saveEx();

		// Close up Old Event
		{
			final I_AD_WF_EventAudit audit = getEventAudit();
			audit.setAD_User_ID(oldUser.getAD_User_ID());
			audit.setTextMsg(getTextMsg());
			audit.setAttributeName("AD_User_ID");
			audit.setOldValue(oldUser.getName() + "(" + oldUser.getAD_User_ID() + ")");
			audit.setNewValue(user.getName() + "(" + user.getAD_User_ID() + ")");
			//
			audit.setWFState(getWFState());
			audit.setEventType(MWFEventAudit.EVENTTYPE_StateChanged);
			final long ms = System.currentTimeMillis() - audit.getCreated().getTime();
			audit.setElapsedTimeMS(new BigDecimal(ms));
			InterfaceWrapperHelper.save(audit);
		}

		// Create new one
		newEventAudit();
	}

	public void setUserConfirmation(@NonNull final UserId userId, @Nullable final String textMsg)
	{
		changeWFStateTo(WFState.Running);
		setAD_User_ID(userId.getRepoId());
		if (textMsg != null)
		{
			addTextMsg(textMsg);
		}
		changeWFStateTo(WFState.Completed);
	}    // setUserConfirmation

	private static List<ProcessInfoParameter> createProcessInfoParameters(@NonNull final WFNode wfNode, @NonNull final PO po)
	{
		return wfNode.getProcessParameters()
				.stream()
				.map(wfNodePara -> createProcessInfoParameter(wfNodePara, po))
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableList());
	}

	@Nullable
	private static ProcessInfoParameter createProcessInfoParameter(@NonNull final WFNodeParameter nPara, @NonNull final PO po)
	{
		final String attributeName = nPara.getAttributeName();
		final String attributeValue = nPara.getAttributeValue();
		log.debug("{} = {}", attributeName, attributeValue);

		// Value - Constant/Variable
		Object value = attributeValue;
		if (attributeValue == null || (attributeValue != null && attributeValue.length() == 0))
		{
			value = null;
		}
		else if (attributeValue.indexOf('@') != -1 && po != null)    // we have a variable
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
			index = po.get_ColumnIndex(columnName);
			if (index != -1)
			{
				value = po.get_Value(index);
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
		final IDocument doc = getDocument();

		final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);
		final WFNode wfNode = getNode();
		final MailTemplateId mailTemplateId = wfNode.getMailTemplateId();
		final MailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId)
				.recordAndUpdateBPartnerAndContact(getPONoLoad());
		//
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
			final PO po = getPONoLoad();
			final int index = po.get_ColumnIndex("AD_User_ID");
			if (index > 0)
			{
				final Object oo = po.get_Value(index);
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
				final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
				final RoleId roleId = resp.getRoleId();
				for (final UserId adUserId : roleDAO.retrieveUserIdsForRoleId(roleId))
				{
					sendEMail(client, adUserId, null, subject, message, pdf, mailTextBuilder.isHtml());
				}
			}
			else if (resp.isOrganization())
			{
				final PO po = getPONoLoad();
				final OrgId orgId = OrgId.ofRepoIdOrAny(po.getAD_Org_ID());
				final OrgInfo org = Services.get(IOrgDAO.class).getOrgInfoById(orgId);
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
			final I_AD_User user = Services.get(IUserDAO.class).getById(userId);
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

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MWFActivity[");
		sb.append(get_ID());

		sb.append(",Node=");
		final WFNode wfNode = this._node;
		if (wfNode == null)
		{
			sb.append(getAD_WF_Node_ID());
		}
		else
		{
			sb.append(wfNode.getName().getDefaultValue());
		}

		sb.append(",State=").append(getWFState())
				.append(",AD_User_ID=").append(getAD_User_ID())
				.append(",").append(getCreated())
				.append("]");
		return sb.toString();
	}

	/**
	 * User String Representation.
	 * Suspended: Approve it (Joe)
	 */
	private String toStringX()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getWFStateText())
				.append(": ").append(getNode().getName());
		if (getAD_User_ID() > 0)
		{
			final String userFullname = Services.get(IUserDAO.class).retrieveUserFullname(getAD_User_ID());
			sb.append(" (").append(userFullname).append(")");
		}
		return sb.toString();
	}
}
