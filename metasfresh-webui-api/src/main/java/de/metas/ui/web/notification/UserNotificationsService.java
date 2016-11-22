package de.metas.ui.web.notification;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.ui.web.config.WebSocketConfig;
import de.metas.ui.web.notification.json.JSONNotification;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class UserNotificationsService
{
	// private static final Logger logger = LogManager.getLogger(UserNotificationsService.class);

	@Autowired
	private SimpMessagingTemplate websocketMessagingTemplate;

	private final ConcurrentHashMap<String, Event2WebSocketListener> sessionId2listeners = new ConcurrentHashMap<>();

	public void enableForSession(final String sessionId, final String adLanguage)
	{
		sessionId2listeners.computeIfAbsent(sessionId, theSessionId -> Event2WebSocketListener.createAndRegister(websocketMessagingTemplate, sessionId, adLanguage));
	}

	public void disableForSession(final String sessionId)
	{
		// NOTE: we assume the listeners were weakly registered...
		sessionId2listeners.remove(sessionId);
	}

	public String getWebsocketEndpoint(final String sessionId)
	{
		return sessionId2listeners.get(sessionId).getWebsocketEndpoint();
	}

	private static final class Event2WebSocketListener implements IEventListener
	{
		public static final Event2WebSocketListener createAndRegister(final SimpMessagingTemplate websocketMessagingTemplate, final String sessionId, final String adLanguage)
		{
			final Event2WebSocketListener eventBusListener = new Event2WebSocketListener(websocketMessagingTemplate, sessionId, adLanguage);

			final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
			eventBusFactory.getAvailableUserNotificationsTopics()
					.stream()
					.map(topic -> eventBusFactory.getEventBus(topic))
					.forEach(eventBus -> eventBus.subscribeWeak(eventBusListener));

			return eventBusListener;
		}

		private final SimpMessagingTemplate websocketMessagingTemplate;
		private final String sessionId;
		private final String adLanguage;
		private final String websocketEndpoint;
		private final JSONOptions jsonOpts;

		public Event2WebSocketListener(final SimpMessagingTemplate websocketMessagingTemplate, final String sessionId, final String adLanguage)
		{
			super();
			this.websocketMessagingTemplate = websocketMessagingTemplate;
			this.sessionId = sessionId;
			this.adLanguage = adLanguage;
			websocketEndpoint = WebSocketConfig.buildNotificationsTopicName(sessionId);

			jsonOpts = JSONOptions.builder()
					.setAD_Language(adLanguage)
					.build();

		}

		@Override
		public void onEvent(final IEventBus eventBus, final Event event)
		{
			final JSONNotification jsonNotification = JSONNotification.of(event, jsonOpts);
			websocketMessagingTemplate.convertAndSend(websocketEndpoint, jsonNotification);
		}

		public String getWebsocketEndpoint()
		{
			return websocketEndpoint;
		}
	}
}
