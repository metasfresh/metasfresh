package de.metas.websocket.producers;

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

import de.metas.websocket.WebsocketTopicName;

/**
 * Implementations of this interface are responsible for creating {@link WebSocketProducer}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface WebSocketProducerFactory
{
	/**
	 * @return websocket topic name prefix to be considered when searching to a suitable factory
	 */
	String getTopicNamePrefix();

	default boolean isDestroyIfNoActiveSubscriptions() { return false; }

	/**
	 * Creates {@link WebSocketProducer} for given topic name
	 */
	WebSocketProducer createProducer(final WebsocketTopicName topicName);
}