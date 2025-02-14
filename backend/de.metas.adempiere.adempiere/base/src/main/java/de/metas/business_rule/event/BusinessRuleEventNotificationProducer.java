/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.business_rule.event;

import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationsConfig;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

public final class BusinessRuleEventNotificationProducer
{
	/**
	 * Topic used to send notifications about business rules that failed
	 */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.businessRule.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	public static BusinessRuleEventNotificationProducer newInstance()
	{
		return new BusinessRuleEventNotificationProducer();
	}

	private BusinessRuleEventNotificationProducer()
	{
	}

	public void createNotice(@NonNull final UserId userId,
							 @NonNull final TableRecordReference targetRecordRef,
							 @NonNull final AdMessageKey messageKey)
	{
		final UserNotificationsConfig notificationsConfig = createUserNotificationsConfigOrNull(userId);
		if (notificationsConfig == null)
		{
			return;
		}

		final INotificationBL notificationBL = Services.get(INotificationBL.class);
		notificationBL.send(
				UserNotificationRequest.builder()
						.topic(EVENTBUS_TOPIC)
						.notificationsConfig(notificationsConfig)
						.contentADMessage(messageKey)
						.targetAction(UserNotificationRequest.TargetRecordAction.of(targetRecordRef))
						.build());
	}

	private static UserNotificationsConfig createUserNotificationsConfigOrNull(final UserId recipientUserId)
	{
		final INotificationBL notifications = Services.get(INotificationBL.class);
		return notifications.getUserNotificationsConfig(recipientUserId);
	}
}
