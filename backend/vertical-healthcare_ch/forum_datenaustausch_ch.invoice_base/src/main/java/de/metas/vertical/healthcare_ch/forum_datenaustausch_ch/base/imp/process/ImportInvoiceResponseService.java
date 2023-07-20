package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.email.EMailCustomType;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceRejectionDetailId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice_Rejection_Detail;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// package visibility
@Service
class ImportInvoiceResponseService
{
	private final static transient Logger log = LogManager.getLogger(ImportInvoiceResponseService.class);

	private static final AdMessageKey MSG_NOT_ALL_FILES_IMPORTED_NOTIFICATION = AdMessageKey.of("de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.NotAllFilesImportedNotification");

	private static final AdMessageKey MSG_INVOICE_REJECTED_NOTIFICATION_SUBJECT = AdMessageKey.of("de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Subject");
	private static final AdMessageKey MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_EXISTS = AdMessageKey.of("de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Content_WhenUserExists");
	private static final AdMessageKey MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_DOES_NOT_EXIST = AdMessageKey.of("de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Content_WhenUserDoesNotExist");

	private static final int WINDOW_ID_AD_PInstance_ID = 332; // FIXME Hardcoded

	private static final Topic INVOICE_EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.invoicecandidate.UserNotifications")
			.type(Type.REMOTE)
			.build();

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	private final IBPartnerDAO ibPartnerDAO = Services.get(IBPartnerDAO.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	ImportInvoiceResponseService()
	{
	}

	// package visibility
	// i believe these 2 methods should be merged into 1 since all that's different is a single TRL.
	void sendNotificationDefaultUserDoesNotExist(
			@NonNull final ImportedInvoiceResponse responseWithTags,
			@NonNull final InvoiceRejectionDetailId invoiceRejectionDetailId,
			@NonNull final UserId userId)
	{
		final Recipient recipient = Recipient.user(userId);

		final TableRecordReference invoiceRef = TableRecordReference.of(I_C_Invoice_Rejection_Detail.Table_Name, invoiceRejectionDetailId);
		final String orgName = orgDAO.retrieveOrgName(responseWithTags.getBillerOrg());

		final UserNotificationRequest userNotificationRequest = UserNotificationRequest
				.builder()
				.topic(INVOICE_EVENTBUS_TOPIC)
				.recipient(recipient)
				.subjectADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_SUBJECT)
				.subjectADMessageParam(responseWithTags.getDocumentNumber())
				.contentADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_DOES_NOT_EXIST)
				.contentADMessageParam(orgName)
				.contentADMessageParam(invoiceRef)
				.targetAction(TargetRecordAction.of(invoiceRef))
				.eMailCustomType(EMailCustomType.InvoiceRejection)
				.build();

		notificationBL.send(userNotificationRequest);
		log.info("Send notification to recipient={}", recipient);
	}

	// package visibility
	void sendNotificationDefaultUserExists(
			@NonNull final ImportedInvoiceResponse responseWithTags,
			final InvoiceRejectionDetailId invoiceRejectionDetailId,
			final List<UserId> userIds)
	{
		for (final UserId userId : userIds)
		{
			final Recipient recipient = Recipient.user(userId);

			final TableRecordReference invoiceRef = TableRecordReference.of(I_C_Invoice_Rejection_Detail.Table_Name, invoiceRejectionDetailId);
			final String orgName = orgDAO.retrieveOrgName(responseWithTags.getBillerOrg());
			final UserNotificationRequest userNotificationRequest = UserNotificationRequest
					.builder()
					.topic(INVOICE_EVENTBUS_TOPIC)
					.recipient(recipient)
					.subjectADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_SUBJECT)
					.subjectADMessageParam(responseWithTags.getDocumentNumber())
					.contentADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_EXISTS)
					.contentADMessageParam(orgName)
					.contentADMessageParam(invoiceRef)
					.targetAction(TargetRecordAction.of(invoiceRef))
					.build();

			notificationBL.send(userNotificationRequest);
			log.info("Send notification to recipient={}", recipient);
		}
	}

	// package visibility
	List<UserId> retrieveOrgDefaultContactByGLN(@NonNull final String gln)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<Integer> bpartnerIds = queryBL
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Location.COLUMN_GLN, gln)
				.orderBy(I_C_BPartner.COLUMNNAME_C_BPartner_ID) // just to have an predictable order
				.create()
				.listDistinct(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, Integer.class);

		return queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsSubjectMatterContact, true)
				.orderBy(I_AD_User.COLUMN_AD_User_ID)
				.create()
				.listIds().stream().map(UserId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

	}

	// package visibility
	OrgId retrieveOrgByGLN(final GLN gln)
	{
		final BPartnerQuery query = BPartnerQuery.builder()
				.gln(gln)
				.build();
		final Optional<BPartnerId> partnerIdOptional = ibPartnerDAO.retrieveBPartnerIdBy(query);
		OrgId billerOrg = OrgId.ANY;
		if (partnerIdOptional.isPresent())
		{
			ibPartnerDAO.getById(partnerIdOptional.get()).getAD_OrgBP_ID();
			billerOrg = OrgId.ofRepoIdOrAny(ibPartnerDAO.getById(partnerIdOptional.get()).getAD_Org_ID());
		}
		return billerOrg;
	}

	// package visibility
	void sendNotificationNotAllFilesImported(final UserId userId, final PInstanceId pinstanceId, final int repoId)
	{
		final UserNotificationRequest userNotificationRequest = UserNotificationRequest
				.builder()
				.recipient(Recipient.user(userId))
				.contentADMessage(MSG_NOT_ALL_FILES_IMPORTED_NOTIFICATION)
				.contentADMessageParam(repoId)
				.targetAction(TargetRecordAction.ofRecordAndWindow(TableRecordReference.of(I_AD_PInstance.Table_Name, pinstanceId), WINDOW_ID_AD_PInstance_ID))
				.build();
		notificationBL.send(userNotificationRequest);
	}
}
