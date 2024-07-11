package de.metas.device.websocket;

import com.google.common.base.MoreObjects;
import de.metas.device.accessor.DeviceAccessor;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.device.accessor.DeviceId;
import de.metas.logging.LogManager;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.producers.WebSocketProducerFactory;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Creates {@link WebSocketProducer} instances which are reading {@link DeviceAccessor#acquireValue()} and creates {@link JSONDeviceValueChangedEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
public class DeviceWebSocketProducerFactory implements WebSocketProducerFactory
{
	private static final Logger logger = LogManager.getLogger(DeviceWebSocketProducerFactory.class);

	private final DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy;
	private final DeviceAccessorsHubFactory deviceAccessorsHubFactory;

	public DeviceWebSocketProducerFactory(
			@NonNull final DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy,
			@NonNull final DeviceAccessorsHubFactory deviceAccessorsHubFactory)
	{
		this.deviceWebsocketNamingStrategy = deviceWebsocketNamingStrategy;
		this.deviceAccessorsHubFactory = deviceAccessorsHubFactory;

		logger.info("deviceWebsocketNamingStrategy={}", deviceWebsocketNamingStrategy);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(deviceWebsocketNamingStrategy.getPrefixAndSlash()).toString();
	}

	@Override
	public String getTopicNamePrefix()
	{
		return deviceWebsocketNamingStrategy.getPrefixAndSlash();
	}

	@Override
	public WebSocketProducer createProducer(final WebsocketTopicName topicName)
	{
		final DeviceId deviceId = deviceWebsocketNamingStrategy.extractDeviceId(topicName);
		return new DeviceWebSocketProducer(deviceAccessorsHubFactory, deviceId);
	}

}
