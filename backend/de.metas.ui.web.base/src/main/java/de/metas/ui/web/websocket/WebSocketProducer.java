package de.metas.ui.web.websocket;

import de.metas.ui.web.window.datatypes.json.JSONOptions;
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
	/**
	 * Produce a new event.
	 *
	 * @return events list (JSON friendly)
	 */
	List<?> produceEvents(JSONOptions jsonOpts);

	default void onNewSubscription(@NonNull final WebsocketSubscriptionId subscriptionId)
	{
	}
}