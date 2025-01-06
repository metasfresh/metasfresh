package de.metas.contracts.definitive.notification;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.event.IEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * {@link IEventBus} wrapper implementation tailored for sending events about generated invoices.
 *
 * @author tsa
 *
 */
public class DefinitiveInvoiceUserNotificationsProducer
{
	public static DefinitiveInvoiceUserNotificationsProducer newInstance()
	{
		return new DefinitiveInvoiceUserNotificationsProducer();
	}

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.modCntr.DefinitiveInvoice.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	private static final Logger logger = LogManager.getLogger(DefinitiveInvoiceUserNotificationsProducer.class);

	private final static AdMessageKey ERROR_NOT_ALL_QTY_HAS_BEEN_SHIPPED = AdMessageKey.of("de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped");

	private DefinitiveInvoiceUserNotificationsProducer()
	{
	}

	/**
	 * Post events about given contract that was generated.
	 */
	public DefinitiveInvoiceUserNotificationsProducer notifyCannotBeGenerated(
			@Nullable final FlatrateTermId contract,
			@Nullable final UserId recipientUserId)
	{
		if (contract == null)
		{
			return this;
		}

		try
		{
			postNotification(createCannotBeGeneratedEvent(contract, recipientUserId));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating event for contract {}. Ignored.", contract, ex);
		}

		return this;
	}

	private UserNotificationRequest createCannotBeGeneratedEvent(
			@NonNull final FlatrateTermId contractId,
			@Nullable final UserId recipientUserId)
	{
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
		final I_C_Flatrate_Term contract = flatrateDAO.getById(contractId);


		final TableRecordReference invoiceRef = TableRecordReference.of(contract);

		return newUserNotificationRequest()
				.recipientUserId(recipientUserId != null ? recipientUserId : UserId.ofRepoId(contract.getCreatedBy()))
				.contentADMessage(ERROR_NOT_ALL_QTY_HAS_BEEN_SHIPPED)
				.contentADMessageParam(contract.getDocumentNo())
				.targetAction(TargetRecordAction.of(invoiceRef))
				.build();
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
