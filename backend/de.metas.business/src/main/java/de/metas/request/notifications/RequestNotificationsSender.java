package de.metas.request.notifications;

import com.google.common.collect.ImmutableSet;
import de.metas.event.Topic;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.notification.UserNotificationsConfig;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_R_Request;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

public class RequestNotificationsSender
{
	public static RequestNotificationsSender newInstance()
	{
		return new RequestNotificationsSender();
	}

	// services
	private final INotificationBL notificationsService = Services.get(INotificationBL.class);
	private final IUserDAO usersRepo = Services.get(IUserDAO.class);

	private static final Topic TOPIC_Requests = Topic.distributed("de.metas.requests");
	private static final AdMessageKey MSG_RequestActionTransfer = AdMessageKey.of("RequestActionTransfer");

	public void notifySalesRepChanged(@NonNull final RequestSalesRepChanged event)
	{
		final Set<UserId> userIdsToNotify = extractUserIdsToNotify(event);
		if (userIdsToNotify.isEmpty())
		{
			return;
		}

		final List<UserNotificationRequest> notifications = new ArrayList<>();
		for (final UserId userIdToNotify : userIdsToNotify)
		{
			notifications.add(createNotification(event, userIdToNotify));
		}

		notificationsService.sendAfterCommit(notifications);
	}

	private UserNotificationRequest createNotification(final RequestSalesRepChanged event, final UserId recipientId)
	{
		final UserNotificationsConfig notificationsConfig = notificationsService.getUserNotificationsConfig(recipientId);
		return UserNotificationRequest.builder()
				.notificationsConfig(notificationsConfig)
				.topic(TOPIC_Requests)
				//
				// RequestActionTransfer - Request {} was transfered by {} from {} to {}
				.subjectADMessage(MSG_RequestActionTransfer)
				.subjectADMessageParam(event.getRequestDocumentNo())
				.subjectADMessageParam(getUserFullname(event.getChangedById()))
				.subjectADMessageParam(getUserFullname(event.getFromSalesRepId()))
				.subjectADMessageParam(getUserFullname(event.getToSalesRepId()))
				//
				.contentADMessage(MSG_RequestActionTransfer)
				.contentADMessageParam(event.getRequestDocumentNo())
				.contentADMessageParam(getUserFullname(event.getChangedById()))
				.contentADMessageParam(getUserFullname(event.getFromSalesRepId()))
				.contentADMessageParam(getUserFullname(event.getToSalesRepId()))
				//
				.targetAction(TargetRecordAction.of(I_R_Request.Table_Name, event.getRequestId().getRepoId()))
				.build();
	}

	private static Set<UserId> extractUserIdsToNotify(@NonNull final RequestSalesRepChanged event)
	{
		return event.getToSalesRepId() != null
				? ImmutableSet.of(event.getToSalesRepId())
				: ImmutableSet.of();
	}

	private String getUserFullname(@Nullable final UserId userId)
	{
		return userId != null
				? usersRepo.retrieveUserFullName(userId)
				: "-";
	}
}
