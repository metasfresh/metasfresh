/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.dashboard.websocket;

import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.ui.web.dashboard.DashboardWidgetType;
import de.metas.ui.web.dashboard.UserDashboard;
import de.metas.ui.web.dashboard.UserDashboardId;
import de.metas.ui.web.dashboard.UserDashboardItemChangeResult;
import de.metas.ui.web.dashboard.websocket.json.JSONDashboardChangedEventsList;
import de.metas.ui.web.dashboard.websocket.json.JSONDashboardItemChangedEvent;
import de.metas.ui.web.dashboard.websocket.json.JSONDashboardOrderChangedEvent;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducersRegistry;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.slf4j.Logger;

import java.util.Set;

public class UserDashboardWebsocketSender
{
	private static final Logger logger = LogManager.getLogger(UserDashboardWebsocketSender.class);
	private final WebsocketSender websocketSender;
	private final WebSocketProducersRegistry websocketProducersRegistry;

	public UserDashboardWebsocketSender(
			@NonNull final WebsocketSender websocketSender,
			@NonNull final WebSocketProducersRegistry websocketProducersRegistry)
	{
		this.websocketSender = websocketSender;
		this.websocketProducersRegistry = websocketProducersRegistry;
	}

	public void sendDashboardItemsOrderChangedEvent(
			@NonNull final UserDashboard dashboard,
			@NonNull final DashboardWidgetType widgetType)
	{
		sendEvents(
				getWebsocketTopicNamesByDashboardId(dashboard.getId()),
				JSONDashboardChangedEventsList.of(
						JSONDashboardOrderChangedEvent.of(
								dashboard.getId(),
								widgetType,
								dashboard.getItemIds(widgetType))));
	}

	public void sendDashboardItemChangedEvent(
			@NonNull final UserDashboard dashboard,
			@NonNull final UserDashboardItemChangeResult changeResult)
	{
		sendEvents(
				getWebsocketTopicNamesByDashboardId(dashboard.getId()),
				toJSONDashboardChangedEventsList(changeResult));
	}

	private static JSONDashboardChangedEventsList toJSONDashboardChangedEventsList(final @NonNull UserDashboardItemChangeResult changeResult)
	{
		final JSONDashboardChangedEventsList.JSONDashboardChangedEventsListBuilder eventBuilder = JSONDashboardChangedEventsList.builder()
				.event(JSONDashboardItemChangedEvent.of(
						changeResult.getDashboardId(),
						changeResult.getDashboardWidgetType(),
						changeResult.getItemId()));

		if (changeResult.isPositionChanged())
		{
			eventBuilder.event(JSONDashboardOrderChangedEvent.of(
					changeResult.getDashboardId(),
					changeResult.getDashboardWidgetType(),
					changeResult.getDashboardOrderedItemIds()));
		}

		return eventBuilder.build();
	}

	private void sendEvents(
			@NonNull final Set<WebsocketTopicName> websocketEndpoints,
			@NonNull final JSONDashboardChangedEventsList events)
	{
		if (websocketEndpoints.isEmpty() || events.isEmpty())
		{
			return;
		}

		for (final WebsocketTopicName websocketEndpoint : websocketEndpoints)
		{
			websocketSender.convertAndSend(websocketEndpoint, events);
			logger.trace("Notified WS {}: {}", websocketEndpoint, events);
		}
	}

	private ImmutableSet<WebsocketTopicName> getWebsocketTopicNamesByDashboardId(@NonNull final UserDashboardId dashboardId)
	{
		return websocketProducersRegistry.streamActiveProducersOfType(UserDashboardWebsocketProducer.class)
				.filter(producer -> producer.isMatchingDashboardId(dashboardId))
				.map(UserDashboardWebsocketProducer::getWebsocketTopicName)
				.collect(ImmutableSet.toImmutableSet());
	}
}
