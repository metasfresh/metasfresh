package de.metas.invoicecandidate.async.spi.impl;

import org.adempiere.invoice.event.InvoiceUserNotificationsProducer;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.impl.ForwardingInvoiceGenerateResult;
import de.metas.user.UserId;

/**
 * {@link ForwardingInvoiceGenerateResult} implementation which is also creating and sending user notifications via {@link InvoiceUserNotificationsProducer}.
 * 
 * @author tsa
 *
 */
public class UserNotificationsInvoiceGenerateResult extends ForwardingInvoiceGenerateResult
{
	private final InvoiceUserNotificationsProducer invoiceGeneratedEventBus = InvoiceUserNotificationsProducer.newInstance();

	private UserId recipientUserId;

	public UserNotificationsInvoiceGenerateResult(final IInvoiceGenerateResult delegate)
	{
		super(delegate);
	}

	/**
	 * Sets the user which shall be notified
	 */
	public UserNotificationsInvoiceGenerateResult setNotificationRecipientUserId(final UserId recipientUserId)
	{
		this.recipientUserId = recipientUserId;
		return this;
	}

	@Override
	public void addInvoice(final I_C_Invoice invoice)
	{
		super.addInvoice(invoice);

		invoiceGeneratedEventBus.notifyGenerated(invoice, recipientUserId);
	}
}
