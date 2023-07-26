package org.adempiere.invoice.event;

import javax.annotation.Nullable;

import de.metas.i18n.AdMessageKey;
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

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.event.IEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

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
			.type(Type.REMOTE)
			.build();

	private static final transient Logger logger = LogManager.getLogger(InvoiceUserNotificationsProducer.class);

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
