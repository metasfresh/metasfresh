package de.metas.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.user.api.NotificationGroupName;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.springframework.core.io.Resource;

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
	boolean broadcastToAllUsers;
	int recipientUserId;
	UserNotificationsConfig notificationsConfig;

	Topic topic;

	boolean important;

	/** Optional; takes precedence over {@link #subjectADMessage}, if set. */
	String subjectPlain;

	/** Optional */
	String subjectADMessage;
	List<Object> subjectADMessageParams;

	/** Optional; takes precedence over {@link #contentADMessage}, if set. */
	String contentPlain;

	/** Optional */
	String contentADMessage;
	List<Object> contentADMessageParams;

	String targetRecordDisplayText;
	ITableRecordReference targetRecord;
	int targetADWindowId;

	List<Resource> attachments;

	// Options
	boolean noEmail;

	@Builder(toBuilder = true)
	private UserNotificationRequest(
			final boolean broadcastToAllUsers,
			final Integer recipientUserId,
			final UserNotificationsConfig notificationsConfig,
			//
			final Topic topic,
			//
			final boolean important,
			//
			@Nullable final String subjectPlain,
			@Nullable final String subjectADMessage,
			@Singular final List<Object> subjectADMessageParams,
			//
			final String contentPlain,
			final String contentADMessage,
			@Singular final List<Object> contentADMessageParams,
			//
			@Nullable String targetRecordDisplayText,
			@Nullable final ITableRecordReference targetRecord,
			final int targetADWindowId,
			//
			@Singular final List<Resource> attachments,
			// Options:
			final boolean noEmail)
	{
		this.broadcastToAllUsers = broadcastToAllUsers;
		if (broadcastToAllUsers)
		{
			this.notificationsConfig = null;
			this.recipientUserId = -1;
		}
		else
		{
			this.notificationsConfig = notificationsConfig;
			if (notificationsConfig != null)
			{
				this.recipientUserId = notificationsConfig.getUserId();
			}
			else
			{
				Check.assume(recipientUserId != null && recipientUserId >= 0, "recipientUserId >= 0 but it was {}", recipientUserId);
				this.recipientUserId = recipientUserId;
			}
		}

		this.topic = topic != null ? topic : EventBusConstants.TOPIC_GeneralUserNotifications;

		this.important = important;

		this.subjectPlain = subjectPlain;
		this.subjectADMessage = subjectADMessage;
		this.subjectADMessageParams = copyADMessageParams(subjectADMessageParams);

		this.contentPlain = contentPlain;
		this.contentADMessage = contentADMessage;
		this.contentADMessageParams = copyADMessageParams(contentADMessageParams);

		this.targetRecordDisplayText = targetRecordDisplayText;
		this.targetRecord = targetRecord;
		this.targetADWindowId = targetADWindowId;

		this.attachments = ImmutableList.copyOf(attachments);

		this.noEmail = noEmail;
	}

	private static List<Object> copyADMessageParams(final List<Object> params)
	{
		return params != null && !params.isEmpty() ? Collections.unmodifiableList(new ArrayList<>(params)) : ImmutableList.of();
	}

	public int getRecipientUserId()
	{
		if (isBroadcastToAllUsers())
		{
			throw new AdempiereException("recipientUserId not available when broadcastToAllUsers is set: " + this);
		}
		return recipientUserId;
	}
	
	public String getADLanguageOrGet(final Supplier<String> defaultLanguageSupplier)
	{
		final String adLanguage = getNotificationsConfig().getUserADLanguage();
		return adLanguage != null ? adLanguage : defaultLanguageSupplier.get();
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

		return toBuilder().broadcastToAllUsers(false).notificationsConfig(notificationsConfig).build();
	}

	public UserNotificationRequest deriveByRecipientUserId(final int recipientUserId)
	{
		Check.assumeGreaterOrEqualToZero(recipientUserId, "recipientUserId");
		if (!broadcastToAllUsers && this.recipientUserId == recipientUserId)
		{
			return this;
		}

		return toBuilder().broadcastToAllUsers(false).recipientUserId(recipientUserId).notificationsConfig(null).build();
	}
}
