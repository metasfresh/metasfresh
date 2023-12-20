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

import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.error.AdIssueId;
import de.metas.event.Topic;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.workflow.WFEventAudit;
import de.metas.workflow.WFEventAuditList;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.PO;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
@ToString
public final class WorkflowExecutionContext
{
	@NonNull @Getter private final WorkflowExecutionSupportingServicesFacade services;

	@NonNull @Getter private final ClientId clientId;
	@NonNull @Getter private final TableRecordReference documentRef;
	@NonNull @Getter private final UserId userId;
	@NonNull @Getter private final LocalDate evaluationTimeAsLocalDate = SystemTime.asLocalDate();

	private final HashMap<TableRecordReference, PO> poByRef = new HashMap<>();
	private final WFEventAuditList auditList = new WFEventAuditList();

	private static final Topic USER_NOTIFICATIONS_TOPIC = Topic.distributed("de.metas.document.UserNotifications");

	@Builder
	private WorkflowExecutionContext(
			@NonNull final WorkflowExecutionSupportingServicesFacade services,
			@Nullable final ClientId clientId,
			@NonNull final TableRecordReference documentRef,
			@NonNull final UserId userId)
	{
		this.services = services;
		this.clientId = clientId != null ? clientId : ClientId.ofRepoId(getPO(documentRef).getAD_Client_ID());
		this.documentRef = documentRef;
		this.userId = userId;
	}

	void save(@NonNull final WFProcess wfProcess)
	{
		services.save(wfProcess);
		services.save(auditList);
	}

	public void save(@NonNull final WFEventAudit audit) {services.save(audit);}

	public IDocument processDocument(
			@NonNull final TableRecordReference documentRef,
			@NonNull final String docAction)
	{
		final IDocument document = getDocument(documentRef);
		services.processDocument(document, docAction);
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
		return services.getDocument(po);
	}

	public IDocument getDocumentOrNull(final TableRecordReference documentRef)
	{
		final PO po = getPO(documentRef);
		return services.getDocumentOrNull(po);
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
			final AdColumnId adColumnId)
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

	public AdIssueId createIssue(@NonNull final Throwable exception) {return services.createIssue(exception);}

	public Set<UserId> getUserIdsByRoleId(@NonNull final RoleId roleId) {return services.getUserIdsByRoleId(roleId);}

	public I_AD_User getUserById(@NonNull final UserId userId) {return services.getUserById(userId);}

	public String getUserFullnameById(@NonNull final UserId userId) {return services.getUserFullnameById(userId);}

	public OrgInfo getOrgInfoById(@NonNull final OrgId orgId) {return services.getOrgInfoById(orgId);}

	void sendNotification(@NonNull final WFUserNotification notification)
	{
		services.sendNotificationAfterCommit(UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC)
				.recipientUserId(notification.getUserId())
				.contentADMessage(notification.getContent().getAdMessage())
				.contentADMessageParams(notification.getContent().getParams())
				.targetAction(notification.getTargetAction())
				.build());
	}

	public MailTextBuilder newMailTextBuilder(
			@NonNull final TableRecordReference documentRef,
			@NonNull final MailTemplateId mailTemplateId)
	{
		return services.newMailTextBuilder(mailTemplateId)
				.recordAndUpdateBPartnerAndContact(getPO(documentRef));
	}

	public void addEventAudit(@NonNull final WFEventAudit audit)
	{
		auditList.add(audit);
	}
}
