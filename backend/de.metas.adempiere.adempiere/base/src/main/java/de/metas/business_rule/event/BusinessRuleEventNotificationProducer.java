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
import de.metas.record.warning.RecordWarningId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_AD_Record_Warning;

public final class BusinessRuleEventNotificationProducer
{
	public static BusinessRuleEventNotificationProducer newInstance()
	{
		return new BusinessRuleEventNotificationProducer();
	}

	@NonNull private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	private BusinessRuleEventNotificationProducer()
	{
	}

	/**
	 * Topic used to send notifications about business rules that failed
	 */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.businessRule.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	public void createNotice(@NonNull final UserId userId,
							 @NonNull final RecordWarningId recordWarningId,
							 @NonNull final AdMessageKey messageKey)
	{
		notificationBL.send(
				UserNotificationRequest.builder()
						.topic(EVENTBUS_TOPIC)
						.notificationsConfig(notificationBL.getUserNotificationsConfig(userId))
						.contentADMessage(messageKey)
						.targetAction(UserNotificationRequest.TargetRecordAction.of(I_AD_Record_Warning.Table_Name, recordWarningId.getRepoId()))
						.build());
	}

}
