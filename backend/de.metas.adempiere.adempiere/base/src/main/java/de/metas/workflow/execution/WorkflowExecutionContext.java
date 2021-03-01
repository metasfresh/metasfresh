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

import de.metas.attachments.AttachmentEntryService;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.email.MailService;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.event.Topic;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.workflow.WFEventAudit;
import de.metas.workflow.WFEventAuditList;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.Workflow;
import de.metas.workflow.service.IADWorkflowDAO;
import de.metas.workflow.service.WFEventAuditRepository;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
@ToString
public final class WorkflowExecutionContext
{
	private final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	@Getter
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	@Getter
	private final WFProcessRepository wfProcessRepository = SpringContextHolder.instance.getBean(WFProcessRepository.class);
	private final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);
	private final WFEventAuditRepository auditRepo = SpringContextHolder.instance.getBean(WFEventAuditRepository.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	private static final Topic USER_NOTIFICATIONS_TOPIC = Topic.remote("de.metas.document.UserNotifications");

	@NonNull
	@Getter
	private final Instant startTime;

	@NonNull
	@Getter
	private final Workflow workflow;

	@NonNull
	@Getter
	private final ClientId clientId;

	@NonNull
	@Getter
	private final String adLanguage;

	@NonNull
	@Getter
	private final TableRecordReference documentRef;

	@NonNull
	@Getter
	private final UserId userId;

	private final HashMap<TableRecordReference, PO> poByRef = new HashMap<>();
	private final WFEventAuditList auditList = new WFEventAuditList();

	@Builder
	private WorkflowExecutionContext(
			@NonNull final Workflow workflow,
			@NonNull final ClientId clientId,
			@NonNull final String adLanguage,
			@NonNull final TableRecordReference documentRef,
			@NonNull final UserId userId)
	{
		this.startTime = SystemTime.asInstant();
		this.workflow = workflow;
		this.clientId = clientId;
		this.adLanguage = adLanguage;
		this.documentRef = documentRef;
		this.userId = userId;

		if (!TimeUtil.isBetween(startTime, workflow.getValidFrom(), workflow.getValidTo()))
		{
			throw new AdempiereException("Workflow not valid on " + startTime);
		}
	}

	@NonNull
	public WFResponsibleId getWFResponsibleId()
	{
		return CoalesceUtil.coalesce(getWorkflow().getResponsibleId(), WFResponsibleId.Invoker);
	}

	@NonNull
	public WFNode getNodeById(@NonNull final WFNodeId nodeId)
	{
		return workflow.getNodeById(nodeId);
	}

	public WFResponsible getResponsibleById(final WFResponsibleId wfResponsibleId)
	{
		return workflowDAO.getWFResponsibleById(wfResponsibleId);
	}

	void save(@NonNull final WFProcess wfProcess)
	{
		wfProcessRepository.save(wfProcess);
		auditRepo.save(auditList);
	}

	public IDocument processDocument(
			@NonNull final TableRecordReference documentRef,
			@NonNull final String docAction)
	{
		final IDocument document = getDocument(documentRef);
		documentBL.processEx(document, docAction);
		return document;
	}

	public void setDocStatus(
			@NonNull final TableRecordReference documentRef,
			@NonNull final DocStatus docStatus)
	{
		final IDocument document = getDocument(documentRef);
		document.setDocStatus(docStatus.getCode());
		InterfaceWrapperHelper.save(document);
	}

	public IDocument getDocument(final TableRecordReference documentRef)
	{
		final PO po = getPO(documentRef);
		return documentBL.getDocument(po);
	}

	public IDocument getDocumentOrNull(final TableRecordReference documentRef)
	{
		final PO po = getPO(documentRef);
		return documentBL.getDocumentOrNull(po);
	}

	public void setDocumentColumnValue(
			@NonNull final TableRecordReference documentRef,
			@NonNull final String columnName,
			@Nullable final Object value)
	{
		final PO po = getPO(documentRef);
		po.set_ValueOfColumn(columnName, value);
		po.saveEx();
	}

	@Nullable
	public Object getDocumentColumnValueByColumnId(
			@NonNull final TableRecordReference documentRef,
			final int adColumnId)
	{
		return getPO(documentRef).get_ValueOfColumn(adColumnId);
	}

	@Nullable
	public Object getDocumentColumnValueByColumnName(
			@NonNull final TableRecordReference documentRef,
			@NonNull final String columnName)
	{
		return getPO(documentRef).get_Value(columnName);
	}

	public boolean hasDocumentColumn(
			@NonNull final TableRecordReference documentRef,
			final String columnName)
	{
		return getPO(documentRef).get_ColumnIndex(columnName) >= 0;
	}

	public OrgId getDocumentOrgId(@NonNull final TableRecordReference documentRef)
	{
		return OrgId.ofRepoId(getPO(documentRef).getAD_Org_ID());
	}

	public Optional<DocStatus> getDocumentStatus(@NonNull final TableRecordReference documentRef)
	{
		return Optional.ofNullable(getDocumentOrNull(documentRef))
				.map(document -> DocStatus.ofNullableCodeOrUnknown(document.getDocStatus()));
	}

	private PO getPO(final TableRecordReference documentRef)
	{
		return poByRef.computeIfAbsent(documentRef, this::retrievePO);
	}

	private PO retrievePO(@NonNull final TableRecordReference documentRef)
	{
		return TableModelLoader.instance.getPO(
				Env.getCtx(),
				documentRef.getAD_Table_ID(),
				documentRef.getRecord_ID(),
				ITrx.TRXNAME_ThreadInherited);
	}

	public AdIssueId createIssue(@NonNull final Throwable exception)
	{
		return errorManager.createIssue(exception);
	}

	public Set<UserId> getUserIdsByRoleId(@NonNull final RoleId roleId)
	{
		return roleDAO.retrieveUserIdsForRoleId(roleId);
	}

	public List<IUserRolePermissions> getUserRolesPermissionsForUserWithOrgAccess(
			@NonNull final UserId userId,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return userRolePermissionsDAO.retrieveUserRolesPermissionsForUserWithOrgAccess(
				clientAndOrgId.getClientId(),
				clientAndOrgId.getOrgId(),
				userId,
				Env.getLocalDate());
	}

	public Money convertMoney(
			@NonNull final Money amount,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		if (CurrencyId.equals(amount.getCurrencyId(), toCurrencyId))
		{
			return amount;
		}

		final CurrencyConversionContext conversionCtx = currencyBL.createCurrencyConversionContext(
				null, // TODAY
				(CurrencyConversionTypeId)null,
				clientAndOrgId.getClientId(),
				clientAndOrgId.getOrgId());

		final CurrencyConversionResult conversionResult = currencyBL.convert(
				conversionCtx,
				amount.toBigDecimal(),
				amount.getCurrencyId(),
				toCurrencyId);
		if (conversionResult == null)
		{
			throw new NoCurrencyRateFoundException(
					currencyBL.getCurrencyCodeById(amount.getCurrencyId()),
					currencyBL.getCurrencyCodeById(toCurrencyId),
					conversionCtx.getConversionDate(),
					null);
		}

		return Money.of(conversionResult.getAmount(), toCurrencyId);
	}

	public I_AD_User getUserById(@NonNull final UserId userId)
	{
		return userDAO.getById(userId);
	}

	public String getUserFullname()
	{
		return getUserFullnameById(getUserId());
	}

	public String getUserFullnameById(@NonNull final UserId userId)
	{
		return userDAO.retrieveUserFullname(userId);
	}

	public OrgInfo getOrgInfoById(@NonNull final OrgId orgId)
	{
		return orgsRepo.getOrgInfoById(orgId);
	}

	public void sendNotification(@NonNull final WFUserNotification notification)
	{
		notificationBL.sendAfterCommit(UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC)
				.recipientUserId(notification.getUserId())
				.contentADMessage(notification.getContent().getAdMessage())
				.contentADMessageParams(notification.getContent().getParams())
				.targetAction(notification.getDocumentToOpen() != null
						? UserNotificationRequest.TargetRecordAction.of(notification.getDocumentToOpen())
						: null)
				.build());
	}

	public MailTextBuilder newMailTextBuilder(
			@NonNull final TableRecordReference documentRef,
			@NonNull final MailTemplateId mailTemplateId)
	{
		return mailService.newMailTextBuilder(mailTemplateId)
				.recordAndUpdateBPartnerAndContact(getPO(documentRef));
	}

	public void save(@NonNull final WFEventAudit audit)
	{
		auditRepo.save(audit);
	}

	public void addEventAudit(@NonNull final WFEventAudit audit)
	{
		auditList.add(audit);
	}

	public void createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final File file)
	{
		attachmentEntryService.createNewAttachment(referencedRecord, file);
	}
}
