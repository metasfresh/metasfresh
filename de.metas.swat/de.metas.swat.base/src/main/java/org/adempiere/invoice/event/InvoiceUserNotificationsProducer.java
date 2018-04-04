package org.adempiere.invoice.event;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.slf4j.Logger;

import de.metas.event.IEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;

/**
 * {@link IEventBus} wrapper implementation tailored for sending events about generated invoices.
 *
 * @author tsa
 *
 */
public class InvoiceUserNotificationsProducer
{
	public static final InvoiceUserNotificationsProducer newInstance()
	{
		return new InvoiceUserNotificationsProducer();
	}

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.invoicecandidate.UserNotifications")
			.type(Type.REMOTE)
			.build();

	private static final transient Logger logger = LogManager.getLogger(InvoiceUserNotificationsProducer.class);

	private static final String MSG_Event_InvoiceGenerated = "Event_InvoiceGenerated";

	private InvoiceUserNotificationsProducer()
	{
	}

	/**
	 * Post events about given invoice that was generated.
	 *
	 * @param inouts
	 */
	public InvoiceUserNotificationsProducer notifyGenerated(final I_C_Invoice invoice, final int recipientUserId)
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

	private final UserNotificationRequest createInvoiceGeneratedEvent(final I_C_Invoice invoice, final int recipientUserId)
	{
		Check.assumeNotNull(invoice, "invoice not null");

		final I_C_BPartner bpartner = invoice.getC_BPartner();
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();

		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);

		return newUserNotificationRequest()
				.recipientUserId(recipientUserId <= 0 ? invoice.getCreatedBy() : recipientUserId)
				.contentADMessage(MSG_Event_InvoiceGenerated)
				.contentADMessageParam(invoiceRef)
				.contentADMessageParam(bpValue)
				.contentADMessageParam(bpName)
				.targetRecord(invoiceRef)
				.build();
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private void postNotification(final UserNotificationRequest notification)
	{
		Services.get(INotificationBL.class).notifyUserAfterCommit(notification);
	}
}
