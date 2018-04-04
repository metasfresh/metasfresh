package de.metas.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.adempiere.user.api.NotificationGroupName;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.event.EventBusConstants;
import de.metas.event.Topic;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation,
 * either version 2 of the
 * License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not,
 * see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class UserNotificationRequest
{
	int recipientUserId;
	UserNotificationsConfig notificationsConfig;

	Topic topic;

	String subjectPlain;
	String subjectADMessage;
	List<Object> subjectADMessageParams;

	String contentPlain;
	String contentADMessage;
	List<Object> contentADMessageParams;

	String targetRecordDisplayText;
	ITableRecordReference targetRecord;
	int targetADWindowId;

	@Builder(toBuilder = true)
	private UserNotificationRequest(
			final int recipientUserId,
			final UserNotificationsConfig notificationsConfig,
			//
			final Topic topic,
			//
			final String subjectPlain,
			final String subjectADMessage,
			@Singular final List<Object> subjectADMessageParams,
			//
			final String contentPlain,
			final String contentADMessage,
			@Singular final List<Object> contentADMessageParams,
			//
			String targetRecordDisplayText,
			final ITableRecordReference targetRecord,
			final int targetADWindowId)
	{
		this.notificationsConfig = notificationsConfig;
		if (notificationsConfig != null)
		{
			this.recipientUserId = notificationsConfig.getUserId();
		}
		else
		{
			this.recipientUserId = recipientUserId;
		}

		this.topic = topic != null ? topic : EventBusConstants.TOPIC_GeneralNotifications;

		this.subjectPlain = subjectPlain;
		this.subjectADMessage = subjectADMessage;
		this.subjectADMessageParams = copyADMessageParams(subjectADMessageParams);

		this.contentPlain = contentPlain;
		this.contentADMessage = contentADMessage;
		this.contentADMessageParams = copyADMessageParams(contentADMessageParams);

		this.targetRecordDisplayText = targetRecordDisplayText;
		this.targetRecord = targetRecord;
		this.targetADWindowId = targetADWindowId;
	}

	private static List<Object> copyADMessageParams(final List<Object> params)
	{
		return params != null && !params.isEmpty() ? Collections.unmodifiableList(new ArrayList<>(params)) : ImmutableList.of();
	}

	public String getSubjectADMessageOr(final String defaultValue)
	{
		return !Check.isEmpty(subjectADMessage) ? subjectADMessage : defaultValue;
	}

	public NotificationGroupName getNotificationGroupName()
	{
		return NotificationGroupName.of(getTopic().getName());
	}

	public UserNotificationRequest deriveByNotificationsConfig(@NonNull final UserNotificationsConfig notificationsConfig)
	{
		if (Objects.equals(this.notificationsConfig, notificationsConfig))
		{
			return this;
		}

		return toBuilder().notificationsConfig(notificationsConfig).build();
	}
}
