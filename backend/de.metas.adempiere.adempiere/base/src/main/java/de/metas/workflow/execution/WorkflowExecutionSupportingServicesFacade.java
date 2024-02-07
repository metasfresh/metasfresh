package de.metas.workflow.execution;

import de.metas.attachments.AttachmentEntryService;
import de.metas.document.DocBaseType;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.email.MailService;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.workflow.WFEventAudit;
import de.metas.workflow.WFEventAuditList;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.execution.approval.WFApprovalRequestRepository;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyId;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import de.metas.workflow.service.IADWorkflowDAO;
import de.metas.workflow.service.WFEventAuditRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_DocType;
import org.compiere.model.PO;
import org.compiere.util.TrxRunnable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class WorkflowExecutionSupportingServicesFacade
{
	@NonNull private final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	@NonNull @Getter private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	@NonNull @Getter private final WFProcessRepository wfProcessRepository;
	@NonNull private final MailService mailService;
	@NonNull private final WFEventAuditRepository auditRepo;
	@NonNull private final AttachmentEntryService attachmentEntryService;
	@NonNull @Getter private final DocApprovalStrategyService docApprovalStrategyService;
	@NonNull @Getter private final WFApprovalRequestRepository wfApprovalRequestRepository;

	public Workflow getWorkflowById(final WorkflowId workflowId) {return workflowDAO.getById(workflowId);}

	public WFResponsible getWFResponsibleById(final WFResponsibleId wfResponsibleId) {return workflowDAO.getWFResponsibleById(wfResponsibleId);}

	public void save(@NonNull final WFProcess wfProcess) {wfProcessRepository.save(wfProcess);}

	public void save(@NonNull final WFEventAuditList auditList) {auditRepo.save(auditList);}

	public void save(@NonNull final WFEventAudit audit) {auditRepo.save(audit);}

	public void processDocument(@NonNull final IDocument document, @NonNull final String docAction)
	{
		Services.get(IDocumentBL.class).processEx(document, docAction);
	}

	public IDocument getDocument(final PO po)
	{
		return Services.get(IDocumentBL.class).getDocument(po);
	}

	public IDocument getDocumentOrNull(final PO po)
	{
		return Services.get(IDocumentBL.class).getDocumentOrNull(po);
	}

	public Optional<DocBaseType> getDocBaseType(final IDocument document)
	{
		return Services.get(IDocumentBL.class).getDocBaseType(document);
	}

	public Optional<DocApprovalStrategyId> getDocApprovalStrategyId(final IDocument document)
	{
		final I_C_DocType docType = Services.get(IDocumentBL.class).getDocTypeOrNull(document);
		if (docType == null)
		{
			return Optional.empty();
		}

		return DocApprovalStrategyId.optionalOfRepoId(docType.getC_Doc_Approval_Strategy_ID());
	}

	public AdIssueId createIssue(@NonNull final Throwable exception) {return errorManager.createIssue(exception);}

	public Set<UserId> getUserIdsByRoleId(@NonNull final RoleId roleId) {return roleDAO.retrieveUserIdsForRoleId(roleId);}

	public I_AD_User getUserById(@NonNull final UserId userId) {return userBL.getById(userId);}

	public String getUserFullnameById(@NonNull final UserId userId) {return userBL.getUserFullNameById(userId);}

	public OrgInfo getOrgInfoById(@NonNull final OrgId orgId)
	{
		return orgsRepo.getOrgInfoById(orgId);
	}

	public void sendNotificationAfterCommit(@NonNull final UserNotificationRequest request) {notificationBL.sendAfterCommit(request);}

	public MailTextBuilder newMailTextBuilder(@NonNull final MailTemplateId mailTemplateId) {return mailService.newMailTextBuilder(mailTemplateId);}

	public void createNewAttachment(@NonNull final Object referencedRecord, @NonNull final File file)
	{
		attachmentEntryService.createNewAttachment(referencedRecord, file);
	}

	public void runInThreadInheritedTrx(final TrxRunnable runnable) {trxManager.runInThreadInheritedTrx(runnable);}
}
