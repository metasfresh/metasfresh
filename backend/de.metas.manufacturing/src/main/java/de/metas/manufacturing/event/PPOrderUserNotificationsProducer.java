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

package de.metas.manufacturing.event;

import com.google.common.collect.ImmutableList;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_PP_Order;

import java.util.Collection;
import java.util.List;

public class PPOrderUserNotificationsProducer
{
	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.manufacturing.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();
	private static final AdMessageKey MSG_Event_PPOrderGenerated = AdMessageKey.of("EVENT_PP_Order_Generated");
	private final transient INotificationBL notificationBL = Services.get(INotificationBL.class);

	private PPOrderUserNotificationsProducer()
	{
	}

	public static PPOrderUserNotificationsProducer newInstance()
	{
		return new PPOrderUserNotificationsProducer();
	}

	public PPOrderUserNotificationsProducer notifyGenerated(final Collection<? extends I_PP_Order> ppOrders)
	{
		if (ppOrders == null || ppOrders.isEmpty())
		{
			return this;
		}

		postNotifications(ppOrders.stream()
				.map(this::createUserNotification)
				.collect(ImmutableList.toImmutableList()));

		return this;
	}

	public final PPOrderUserNotificationsProducer notifyGenerated(@NonNull final I_PP_Order ppOrder)
	{
		notifyGenerated(ImmutableList.of(ppOrder));
		return this;
	}

	private UserNotificationRequest createUserNotification(@NonNull final I_PP_Order ppOrder)
	{
		final UserId recipientUserId = getNotificationRecipientUserId(ppOrder);
		final TableRecordReference ppOrderRef = TableRecordReference.of(ppOrder);
		return newUserNotificationRequest()
				.recipientUserId(recipientUserId)
				.contentADMessage(MSG_Event_PPOrderGenerated)
				.contentADMessageParam(ppOrderRef)
				.targetAction(TargetRecordAction.of(ppOrderRef))
				.build();
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC);
	}

	private UserId getNotificationRecipientUserId(final I_PP_Order ppOrder)
	{
		return UserId.ofRepoId(ppOrder.getCreatedBy());
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		notificationBL.sendAfterCommit(notifications);
	}
}
