package de.metas.ui.web.notification;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.notification.INotificationRepository;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotificationsList;
import de.metas.ui.web.notification.json.JSONNotification;
import de.metas.ui.web.notification.json.JSONNotificationEvent;
import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebsocketSender;
import lombok.Builder;
import lombok.NonNull;

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

	private final int adUserId;
	private String adLanguage;

	private final Set<String> activeSessions = ConcurrentHashMap.newKeySet();

	private final INotificationRepository notificationsRepo;

	private final WebsocketSender websocketSender;
	private final String websocketEndpoint;

	@Builder
	private UserNotificationsQueue(
			final int adUserId,
			@NonNull final String adLanguage,
			@NonNull final INotificationRepository notificationsRepo,
			@NonNull final WebsocketSender websocketSender)
	{
		Check.assumeGreaterOrEqualToZero(adUserId, "adUserId");

		this.adUserId = adUserId;
		this.adLanguage = adLanguage;
		this.notificationsRepo = notificationsRepo;

		this.websocketSender = websocketSender;
		websocketEndpoint = WebSocketConfig.buildNotificationsTopicName(adUserId);

		logger.trace("Created notifications queue: {}", this); // keep it last
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("websocketEndpoint", websocketEndpoint)
				.toString();
	}

	public int getAD_User_ID()
	{
		return adUserId;
	}

	public String getWebsocketEndpoint()
	{
		return websocketEndpoint;
	}

	private final void fireEventOnWebsocket(final JSONNotificationEvent event)
	{
		websocketSender.convertAndSend(websocketEndpoint, event);
		logger.trace("Fired notification to WS {}: {}", websocketEndpoint, event);
	}

	public UserNotificationsList getNotificationsAsList(final int limit)
	{
		final List<UserNotification> notifications = notificationsRepo.getByUserId(adUserId, limit);
		final boolean fullyLoaded = limit <= 0 || notifications.size() <= limit;

		final int totalCount;
		final int unreadCount;
		if (fullyLoaded)
		{
			totalCount = notifications.size();
			unreadCount = (int)notifications.stream().filter(UserNotification::isNotRead).count();
		}
		else
		{
			totalCount = notificationsRepo.getTotalCountByUserId(adUserId);
			unreadCount = notificationsRepo.getUnreadCountByUserId(adUserId);
		}

		return UserNotificationsList.of(notifications, totalCount, unreadCount);
	}

	public void addActiveSessionId(final String sessionId)
	{
		Check.assumeNotNull(sessionId, "Parameter sessionId is not null");
		activeSessions.add(sessionId);
		logger.debug("Added sessionId '{}' to {}", sessionId, this);
	}

	public void removeActiveSessionId(final String sessionId)
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
		final int adUserId = getAD_User_ID();
		Check.assume(notification.getRecipientUserId() == adUserId, "notification's recipient user ID shall be {}: {}", adUserId, notification);

		final JSONNotification jsonNotification = JSONNotification.of(notification, adLanguage);
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
		notificationsRepo.markAllAsReadByUserId(getAD_User_ID());
		fireEventOnWebsocket(JSONNotificationEvent.eventReadAll());
	}

	public int getUnreadCount()
	{
		return notificationsRepo.getUnreadCountByUserId(getAD_User_ID());
	}

	public void setLanguage(@NonNull final String adLanguage)
	{
		this.adLanguage = adLanguage;
	}

	public void delete(final String notificationId)
	{
		notificationsRepo.deleteById(Integer.parseInt(notificationId));
		fireEventOnWebsocket(JSONNotificationEvent.eventDeleted(notificationId, getUnreadCount()));
	}
}
