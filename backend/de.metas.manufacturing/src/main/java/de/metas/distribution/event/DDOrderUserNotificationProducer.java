/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.distribution.event;

import com.google.common.collect.ImmutableList;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_DD_Order;

import java.util.Collection;
import java.util.List;

public class DDOrderUserNotificationProducer
{
	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.distribution.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();
	private static final AdMessageKey MSG_Event_DDOrderGenerated = AdMessageKey.of("EVENT_DD_Order_Generated");
	private final transient INotificationBL notificationBL = Services.get(INotificationBL.class);

	private DDOrderUserNotificationProducer()
	{
	}

	public static DDOrderUserNotificationProducer newInstance()
	{
		return new DDOrderUserNotificationProducer();
	}

	public DDOrderUserNotificationProducer notifyGenerated(final Collection<? extends I_DD_Order> orders)
	{
		if (orders == null || orders.isEmpty())
		{
			return this;
		}

		postNotifications(orders.stream()
				.map(this::createUserNotification)
				.collect(ImmutableList.toImmutableList()));

		return this;
	}

	public final DDOrderUserNotificationProducer notifyGenerated(@NonNull final I_DD_Order order)
	{
		notifyGenerated(ImmutableList.of(order));
		return this;
	}

	private UserNotificationRequest createUserNotification(@NonNull final I_DD_Order order)
	{
		final UserId recipientUserId = getNotificationRecipientUserId(order);
		final TableRecordReference orderRef = TableRecordReference.of(order);
		return newUserNotificationRequest()
				.recipientUserId(recipientUserId)
				.contentADMessage(MSG_Event_DDOrderGenerated)
				.contentADMessageParam(orderRef)
				.targetAction(UserNotificationRequest.TargetRecordAction.of(orderRef))
				.build();
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC);
	}

	private UserId getNotificationRecipientUserId(final I_DD_Order order)
	{
		return UserId.ofRepoId(order.getCreatedBy());
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		notificationBL.sendAfterCommit(notifications);
	}
}
