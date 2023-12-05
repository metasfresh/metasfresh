package de.metas.customs.event;

import com.google.common.collect.ImmutableList;
import de.metas.customs.CustomsInvoice;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Customs_Invoice;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class CustomsInvoiceUserNotificationsProducer
{
	public static final CustomsInvoiceUserNotificationsProducer newInstance()
	{
		return new CustomsInvoiceUserNotificationsProducer();
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.customs.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	private static final AdWindowId WINDOW_CUSTOMS_INVOICE = AdWindowId.ofRepoId(540643); // FIXME: HARDCODED
	private static final AdMessageKey MSG_Event_CustomsInvoiceGenerated = AdMessageKey.of("Event_CustomsInvoiceGenerated");

	private CustomsInvoiceUserNotificationsProducer()
	{
	}

	public CustomsInvoiceUserNotificationsProducer notifyGenerated(final Collection<CustomsInvoice> customInvoices)
	{
		if (customInvoices == null || customInvoices.isEmpty())
		{
			return this;
		}

		postNotifications(customInvoices.stream()
				.map(this::createUserNotification)
				.collect(ImmutableList.toImmutableList()));

		return this;
	}

	public final CustomsInvoiceUserNotificationsProducer notifyGenerated(@NonNull final CustomsInvoice customsInvoice)
	{
		notifyGenerated(ImmutableList.of(customsInvoice));
		return this;
	}

	private final UserNotificationRequest createUserNotification(@NonNull final CustomsInvoice customsInvoice)
	{
		final TableRecordReference customsInvoiceRef = toTableRecordRef(customsInvoice);

		return newUserNotificationRequest()
				.recipientUserId(getNotificationRecipientUserId(customsInvoice))
				.contentADMessage(MSG_Event_CustomsInvoiceGenerated)
				.contentADMessageParam(customsInvoiceRef)
				.targetAction(TargetRecordAction.ofRecordAndWindow(customsInvoiceRef, WINDOW_CUSTOMS_INVOICE))
				.build();

	}

	private static TableRecordReference toTableRecordRef(final CustomsInvoice customsInvoice)
	{
		return TableRecordReference.of(I_C_Customs_Invoice.Table_Name, customsInvoice.getId());
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private final UserId getNotificationRecipientUserId(final CustomsInvoice customsInvoice)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if (customsInvoice.getDocStatus().isReversedOrVoided())
		{
			return customsInvoice.getLastUpdatedBy();
		}
		//
		// Fallback: notify only the creator
		else
		{
			return customsInvoice.getCreatedBy();
		}
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notifications);
	}

}
