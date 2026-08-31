package org.adempiere.invoice.event;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.event.IEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

/**
 * {@link IEventBus} wrapper implementation tailored for sending events about generated invoices.
 *
 * @author tsa
 *
 */
public class InvoiceUserNotificationsProducer
{
	public static InvoiceUserNotificationsProducer newInstance()
	{
		return new InvoiceUserNotificationsProducer();
	}

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.invoicecandidate.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	/**
	 * Topic used to notify about invoice candidates which could NOT be invoiced.
	 * <p>
	 * Deliberately NOT {@link #EVENTBUS_TOPIC}: that one carries "invoice generated" events and every subscriber of it
	 * (e.g. the WebUI notification list, or {@code InvoiceGeneratedNotificationChecker} in the tests) expects each event
	 * to reference a C_Invoice. A failure event references no invoice at all, so it has to travel on its own topic.
	 */
	public static final Topic EVENTBUS_TOPIC_Error = Topic.builder()
			.name("de.metas.invoicecandidate.UserNotifications.InvoicingErrors")
			.type(Type.DISTRIBUTED)
			.build();

	private static final transient Logger logger = LogManager.getLogger(InvoiceUserNotificationsProducer.class);

	private static final AdMessageKey MSG_Event_InvoiceGenerated = AdMessageKey.of("Event_InvoiceGenerated");
	private static final AdMessageKey MSG_Event_InvoicingError = AdMessageKey.of("Event_InvoicingError");

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
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner bpartner = bpartnerDAO.getById(invoice.getC_BPartner_ID());

		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();

		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);

		return newUserNotificationRequest()
				.recipientUserId(recipientUserId != null ? recipientUserId : UserId.ofRepoId(invoice.getCreatedBy()))
				.contentADMessage(MSG_Event_InvoiceGenerated)
				.contentADMessageParam(invoiceRef)
				.contentADMessageParam(bpValue)
				.contentADMessageParam(bpName)
				.targetAction(TargetRecordAction.of(invoiceRef))
				.build();
	}

	/**
	 * Notifies the user who started the "Create Invoices" run that the given candidates could not be invoiced.
	 */
	public InvoiceUserNotificationsProducer notifyInvoicingError(
			@Nullable final List<I_C_Invoice_Candidate> failedCandidates,
			@Nullable final Throwable error,
			@Nullable final UserId recipientUserId)
	{
		// Without this, a forwarded-but-empty call would notify "0 invoice candidate(s) could not be invoiced".
		if (failedCandidates == null || failedCandidates.isEmpty())
		{
			return this;
		}

		try
		{
			// send() and not sendAfterCommit(): the invoicing transaction is rolled back on error
			// (InvoiceCandBLCreateInvoices.DefaultInvoiceGeneratorRunnable.doCatch returns true),
			// so an after-commit notification would never be sent.
			Services.get(INotificationBL.class).send(UserNotificationRequest.builder()
					.topic(EVENTBUS_TOPIC_Error)
					.recipientUserId(recipientUserId != null ? recipientUserId : Env.getLoggedUserId())
					.contentADMessage(MSG_Event_InvoicingError)
					.contentADMessageParam(failedCandidates.size())
					// extractMessage() and not getLocalizedMessage(): the latter is null for e.g. a
					// NullPointerException thrown by an aggregator, which would render the note as "...: null".
					.contentADMessageParam(AdempiereException.extractMessage(error))
					.build());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating invoicing-error event for {} candidate(s). Ignored.", failedCandidates.size(), ex);
		}

		return this;
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
