package de.metas.websocket.producers;

import de.metas.websocket.WebsocketHeaders;
import de.metas.websocket.WebsocketSubscriptionId;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;

import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Implementations of this interface are responsible for producing websocket events.
 * <p>
 * NOTE: if the implementation is annotated with {@link org.springframework.stereotype.Component} it will be automatically discovered by {@link WebSocketProducersRegistry} on start.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface WebSocketProducer
{
	default void setWebsocketSender(@SuppressWarnings("unused") final WebsocketSender websocketSender) {}

	default void onNewSubscription(@NonNull final WebsocketSubscriptionId subscriptionId) {}

	default void onStart() {}

	default void onStop() {}


	interface ProduceEventsOnPollSupport
	{
		/**
		 * Produce a new event.
		 *
		 * @return events list (JSON friendly)
		 */
		List<?> produceEvents();
	}

	default void onNewSubscription(@NonNull final WebsocketSubscriptionId subscriptionId, @NonNull final WebsocketHeaders headers)
	{
		onNewSubscription(subscriptionId);
	}
}