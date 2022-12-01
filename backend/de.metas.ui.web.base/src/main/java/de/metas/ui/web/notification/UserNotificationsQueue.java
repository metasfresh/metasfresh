package de.metas.ui.web.notification;

import com.google.common.base.MoreObjects;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationRepository;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotificationsList;
import de.metas.ui.web.notification.json.JSONNotification;
import de.metas.ui.web.notification.json.JSONNotificationEvent;
import de.metas.ui.web.session.json.WebuiSessionId;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.sender.WebsocketSender;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class UserNotificationsQueue
{
	private static final Logger logger = LogManager.getLogger(UserNotificationsQueue.class);

	private final UserId userId;
	private JSONOptions jsonOptions;

	private final Set<WebuiSessionId> activeSessions = ConcurrentHashMap.newKeySet();

	private final INotificationRepository notificationsRepo;

	private final WebsocketSender websocketSender;
	private final WebsocketTopicName websocketEndpoint;

	@Builder
	private UserNotificationsQueue(
			@NonNull final UserId userId,
			@NonNull final JSONOptions jsonOptions,
			@NonNull final INotificationRepository notificationsRepo,
			@NonNull final WebsocketSender websocketSender)
	{
		this.userId = userId;
		this.jsonOptions = jsonOptions;
		this.notificationsRepo = notificationsRepo;

		this.websocketSender = websocketSender;
		websocketEndpoint = WebsocketTopicNames.buildNotificationsTopicName(userId);

		logger.trace("Created notifications queue: {}", this); // keep it last
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("websocketEndpoint", websocketEndpoint)
				.toString();
	}

	public UserId getUserId()
	{
		return userId;
	}

	public WebsocketTopicName getWebsocketEndpoint()
	{
		return websocketEndpoint;
	}

	private void fireEventOnWebsocket(final JSONNotificationEvent event)
	{
		websocketSender.convertAndSend(websocketEndpoint, event);
		logger.trace("Fired notification to WS {}: {}", websocketEndpoint, event);
	}

	public UserNotificationsList getNotificationsAsList(@NonNull final QueryLimit limit)
	{
		final List<UserNotification> notifications = notificationsRepo.getByUserId(userId, limit);
		final boolean fullyLoaded = limit.isNoLimit() || notifications.size() <= limit.toInt();

		final int totalCount;
		final int unreadCount;
		if (fullyLoaded)
		{
			totalCount = notifications.size();
			unreadCount = (int)notifications.stream().filter(UserNotification::isNotRead).count();
		}
		else
		{
			totalCount = notificationsRepo.getTotalCountByUserId(userId);
			unreadCount = notificationsRepo.getUnreadCountByUserId(userId);
		}

		return UserNotificationsList.of(notifications, totalCount, unreadCount);
	}

	public void addActiveSessionId(final WebuiSessionId sessionId)
	{
		Check.assumeNotNull(sessionId, "Parameter sessionId is not null");
		activeSessions.add(sessionId);
		logger.debug("Added sessionId '{}' to {}", sessionId, this);
	}

	public void removeActiveSessionId(final WebuiSessionId sessionId)
	{
		activeSessions.remove(sessionId);
		logger.debug("Removed sessionId '{}' to {}", sessionId, this);
	}

	public boolean hasActiveSessions()
	{
		return !activeSessions.isEmpty();
	}

	/* package */void addNotification(@NonNull final UserNotification notification)
	{
		final UserId adUserId = getUserId();
		Check.assume(notification.getRecipientUserId() == adUserId.getRepoId(), "notification's recipient user ID shall be {}: {}", adUserId, notification);

		final JSONNotification jsonNotification = JSONNotification.of(notification, jsonOptions);
		fireEventOnWebsocket(JSONNotificationEvent.eventNew(jsonNotification, getUnreadCount()));
	}

	public void markAsRead(final String notificationId)
	{
		notificationsRepo.markAsReadById(Integer.parseInt(notificationId));
		fireEventOnWebsocket(JSONNotificationEvent.eventRead(notificationId, getUnreadCount()));
	}

	public void markAllAsRead()
	{
		logger.trace("Marking all notifications as read (if any) for {}...", this);
		notificationsRepo.markAllAsReadByUserId(getUserId());
		fireEventOnWebsocket(JSONNotificationEvent.eventReadAll());
	}

	public int getUnreadCount()
	{
		return notificationsRepo.getUnreadCountByUserId(getUserId());
	}

	public void setLanguage(@NonNull final String adLanguage)
	{
		this.jsonOptions = jsonOptions.withAdLanguage(adLanguage);
	}

	public void delete(final String notificationId)
	{
		notificationsRepo.deleteById(Integer.parseInt(notificationId));
		fireEventOnWebsocket(JSONNotificationEvent.eventDeleted(notificationId, getUnreadCount()));
	}

	public void deleteAll()
	{
		notificationsRepo.deleteAllByUserId(getUserId());
		fireEventOnWebsocket(JSONNotificationEvent.eventDeletedAll());
	}

}
