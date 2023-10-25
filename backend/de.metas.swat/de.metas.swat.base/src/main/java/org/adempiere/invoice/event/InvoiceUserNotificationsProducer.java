package org.adempiere.invoice.event;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.event.IEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.Language;
import de.metas.letter.BoilerPlate;
import de.metas.letter.BoilerPlateId;
import de.metas.letter.BoilerPlateRepository;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * {@link IEventBus} wrapper implementation tailored for sending events about generated invoices.
 *
 * @author tsa
 */
public class InvoiceUserNotificationsProducer
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final BoilerPlateRepository boilerPlateRepository = SpringContextHolder.instance.getBean(BoilerPlateRepository.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public static InvoiceUserNotificationsProducer newInstance()
	{
		return new InvoiceUserNotificationsProducer();
	}

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.invoicecandidate.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	private static final Logger logger = LogManager.getLogger(InvoiceUserNotificationsProducer.class);

	private static final AdMessageKey MSG_Event_InvoiceGenerated = AdMessageKey.of("Event_InvoiceGenerated");

	private InvoiceUserNotificationsProducer()
	{
	}

	/**
	 * Post events about given invoice that was generated.
	 */
	public InvoiceUserNotificationsProducer notifyGenerated(
			@Nullable final I_C_Invoice invoice,
			@Nullable final UserId recipientUserId)
	{
		if (invoice == null)
		{
			return this;
		}

		try
		{
			postNotification(createInvoiceGeneratedEvent(invoice, recipientUserId));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating event for invoice {}. Ignored.", invoice, ex);
		}

		return this;
	}

	private UserNotificationRequest createInvoiceGeneratedEvent(
			@NonNull final I_C_Invoice invoice,
			@Nullable final UserId recipientUserId)
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(invoice.getC_BPartner_ID());

		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();

		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);

		final UserId actualRecipientUserId = recipientUserId != null ? recipientUserId : UserId.ofRepoId(invoice.getCreatedBy());
		final UserNotificationRequest.UserNotificationRequestBuilder userNotificationRequestBuilder = newUserNotificationRequest()
				.recipientUserId(actualRecipientUserId)
				.targetAction(TargetRecordAction.of(invoiceRef))
				.contentADMessage(MSG_Event_InvoiceGenerated)
				.contentADMessageParam(invoiceRef)
				.contentADMessageParam(bpValue)
				.contentADMessageParam(bpName);

		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(invoice.getC_DocType_ID());
		if (docTypeId != null)
		{
			final BoilerPlateId boilerPlateId = BoilerPlateId.ofRepoIdOrNull(docTypeDAO.getById(docTypeId).getCompletedNotification_BoilerPlate_ID());

			if (boilerPlateId != null)
			{
				final Language language = Language.asLanguage(userDAO.getById(actualRecipientUserId).getAD_Language());
				final BoilerPlate boilerPlate = boilerPlateRepository.getByBoilerPlateId(boilerPlateId, language);
				final String orgName = orgDAO.retrieveOrgName(OrgId.ofRepoId(invoice.getAD_Org_ID()));
				final Evaluatee evaluationContext = Evaluatees.compose(
						InterfaceWrapperHelper.getEvaluatee(invoice),
						Evaluatees.mapBuilder()
								.put("AD_Org_Name", orgName)
								.build()
				);
				userNotificationRequestBuilder.subjectPlain(boilerPlate.evaluateSubject(evaluationContext))
						.contentPlain(boilerPlate.evaluateTextSnippet(evaluationContext));
			}
		}
		return userNotificationRequestBuilder.build();
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private void postNotification(final UserNotificationRequest notification)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notification);
	}
}
