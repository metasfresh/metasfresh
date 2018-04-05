package de.metas.ui.web.notification;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.notification.NotificationRepository;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotificationsList;
import de.metas.ui.web.notification.json.JSONNotification;
import de.metas.ui.web.notification.json.JSONNotificationEvent;
import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebsocketSender;
import lombok.Builder;

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

	private final NotificationRepository notificationsRepo;
	private final ConcurrentHashMap<String, UserNotification> id2notification = new ConcurrentHashMap<>();
	private final ConcurrentLinkedDeque<UserNotification> notifications = new ConcurrentLinkedDeque<>();
	private final AtomicInteger unreadCount = new AtomicInteger(0);

	private final WebsocketSender websocketSender;
	private final String websocketEndpoint;

	@Builder
	private UserNotificationsQueue(
			final int adUserId,
			final String adLanguage,
			final NotificationRepository notificationsRepo,
			final WebsocketSender websocketSender)
	{
		this.adUserId = adUserId;
		this.adLanguage = adLanguage;

		//
		// Load notifications from repository
		this.notificationsRepo = notificationsRepo;
		notificationsRepo.getByUser(adUserId)
				.forEach(notification -> {
					notifications.addFirst(notification);
					id2notification.put(notification.getIdAsString(), notification);
					if (!notification.isRead())
					{
						unreadCount.incrementAndGet();
					}
				});

		this.websocketSender = websocketSender;
		websocketEndpoint = WebSocketConfig.buildNotificationsTopicName(adUserId);

		logger.trace("Created notifications queue: {}", this); // keep it last
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("websocketEndpoint", websocketEndpoint)
				.add("unread", unreadCount.get())
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
		if (limit <= 0)
		{
			final List<UserNotification> notifications = ImmutableList.copyOf(this.notifications);
			return UserNotificationsList.of(notifications, notifications.size(), getUnreadCount());
		}
		else
		{
			final List<UserNotification> notifications = this.notifications.stream()
					.limit(limit)
					.collect(GuavaCollectors.toImmutableList());
			return UserNotificationsList.of(notifications, this.notifications.size(), getUnreadCount());
		}
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

	/* package */void addNotification(final UserNotification notification)
	{
		Check.assumeNotNull(notification, "Parameter notification is not null");

		if (notification.getRecipientUserId() != getAD_User_ID())
		{
			logger.warn("Skip adding notification to queue because the recipient user does not match: notification={}, queue={}", notification, this);
			return;
		}

		//
		// Add notification to list and map
		final UserNotification notificationOld = id2notification.put(notification.getIdAsString(), notification);
		if (notificationOld != null)
		{
			// already added, shall not happen
			logger.warn("Skip adding notification {} because it's ID is already present in {}", notification, this);
			return;
		}
		notifications.addFirst(notification);

		//
		// Update unreadCount
		if (!notification.isRead())
		{
			unreadCount.incrementAndGet();
		}

		logger.trace("Added notification to {}: {}", this, notification); // NOTE: log after updating unreadCount

		//
		// Notify on websocket
		final JSONNotification jsonNotification = JSONNotification.of(notification, adLanguage);
		fireEventOnWebsocket(JSONNotificationEvent.eventNew(jsonNotification, unreadCount.get()));
	}

	public void markAsRead(final String notificationId)
	{
		final UserNotification notification = id2notification.get(notificationId);
		if (notification == null)
		{
			throw new IllegalArgumentException("Notification for id=" + notificationId + " not found in " + this);
		}

		markAsRead(notification);
	}

	public void markAllAsRead()
	{
		logger.trace("Marking all notifications as read (if any) for {}...", this);
		id2notification.values().forEach(this::markAsRead);
	}

	private void markAsRead(final UserNotification notification)
	{
		final boolean changed = notificationsRepo.markAsRead(notification);
		if (!changed)
		{
			return;
		}

		//
		// Update unreadCount
		unreadCount.decrementAndGet();

		//
		// Notify on websocket
		final JSONNotification jsonNotification = JSONNotification.of(notification, adLanguage);
		fireEventOnWebsocket(JSONNotificationEvent.eventRead(jsonNotification, unreadCount.get()));
	}

	public int getUnreadCount()
	{
		return unreadCount.get();
	}

	public void setLanguage(final String adLanguage)
	{
		Preconditions.checkNotNull(adLanguage, "language");
		this.adLanguage = adLanguage;
	}

	public void delete(final String notificationId)
	{
		notificationsRepo.deleteById(Integer.parseInt(notificationId));

		final UserNotification notification = id2notification.remove(notificationId);
		if (notification != null)
		{
			notifications.remove(notification);
		}

		// Update unread count
		if (notification != null && !notification.isRead())
		{
			unreadCount.decrementAndGet();
		}

		//
		// Notify on websocket
		fireEventOnWebsocket(JSONNotificationEvent.eventDeleted(notificationId, unreadCount.get()));
	}
}
