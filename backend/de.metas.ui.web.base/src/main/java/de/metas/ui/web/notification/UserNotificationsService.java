package de.metas.ui.web.notification;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationRepository;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotificationUtils;
import de.metas.notification.UserNotificationsList;
import de.metas.ui.web.session.UserSession.LanguagedChangedEvent;
import de.metas.ui.web.session.json.WebuiSessionId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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

@Service
public class UserNotificationsService
{
	private static final Logger logger = LogManager.getLogger(UserNotificationsService.class);

	@Autowired
	private WebsocketSender websocketSender;

	private final ConcurrentHashMap<UserId, UserNotificationsQueue> adUserId2notifications = new ConcurrentHashMap<>();

	private final AtomicBoolean subscribedToEventBus = new AtomicBoolean(false);

	@EventListener
	public void onUserLanguageChanged(final LanguagedChangedEvent event)
	{
		final UserNotificationsQueue notificationsQueue = adUserId2notifications.get(event.getAdUserId());
		if (notificationsQueue != null)
		{
			notificationsQueue.setLanguage(event.getAdLanguage());
		}
	}

	private void subscribeToEventTopicsIfNeeded()
	{
		if (!subscribedToEventBus.getAndSet(true))
		{
			final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
			eventBusFactory.registerUserNotificationsListener(this::forwardEventToNotificationsQueues);
		}
	}

	public synchronized void enableForSession(
			@NonNull final WebuiSessionId sessionId,
			@NonNull final UserId adUserId,
			@NonNull final JSONOptions jsonOptions)
	{
		logger.trace("Enabling for sessionId={}, adUserId={}, jsonOptions={}", sessionId, adUserId, jsonOptions);

		final UserNotificationsQueue notificationsQueue = adUserId2notifications.computeIfAbsent(adUserId, k -> UserNotificationsQueue.builder()

				.userId(adUserId)
				.jsonOptions(jsonOptions)
				.notificationsRepo(Services.get(INotificationRepository.class))
				.websocketSender(websocketSender)
				.build());

		notificationsQueue.addActiveSessionId(sessionId);

		subscribeToEventTopicsIfNeeded();
	}

	public synchronized void disableForSession(final String sessionId)
	{
		// TODO: implement
	}

	public WebsocketTopicName getWebsocketEndpoint(final UserId adUserId)
	{
		return getNotificationsQueue(adUserId).getWebsocketEndpoint();
	}

	private UserNotificationsQueue getNotificationsQueueOrNull(final UserId adUserId)
	{
		return adUserId2notifications.get(adUserId);
	}

	private UserNotificationsQueue getNotificationsQueue(final UserId adUserId)
	{
		final UserNotificationsQueue notificationsQueue = getNotificationsQueueOrNull(adUserId);
		if (notificationsQueue == null)
		{
			throw new IllegalArgumentException("No notifications queue found for AD_User_ID=" + adUserId);
		}
		return notificationsQueue;
	}

	public UserNotificationsList getNotifications(final UserId adUserId, final QueryLimit limit)
	{
		return getNotificationsQueue(adUserId).getNotificationsAsList(limit);
	}

	private void forwardEventToNotificationsQueues(final IEventBus eventBus, final Event event)
	{
		logger.trace("Got event from {}: {}", eventBus, event);

		final UserNotification notification = UserNotificationUtils.toUserNotification(event);
		final UserId recipientUserId = UserId.ofRepoId(notification.getRecipientUserId());
		final UserNotificationsQueue notificationsQueue = getNotificationsQueueOrNull(recipientUserId);
		if (notificationsQueue == null)
		{
			logger.trace("No notification queue was found for recipientUserId={}", recipientUserId);
			return;
		}

		notificationsQueue.addNotification(notification);
	}

	public void markNotificationAsRead(final UserId adUserId, final String notificationId)
	{
		getNotificationsQueue(adUserId).markAsRead(notificationId);
	}

	public void markAllNotificationsAsRead(final UserId adUserId)
	{
		getNotificationsQueue(adUserId).markAllAsRead();
	}

	public int getNotificationsUnreadCount(final UserId adUserId)
	{
		return getNotificationsQueue(adUserId).getUnreadCount();
	}

	public void deleteNotification(final UserId adUserId, final String notificationId)
	{
		getNotificationsQueue(adUserId).delete(notificationId);
	}

	public void deleteAllNotification(final UserId adUserId)
	{
		getNotificationsQueue(adUserId).deleteAll();
	}

}
