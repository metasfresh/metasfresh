package de.metas.device.websocket;

import com.google.common.base.MoreObjects;
import de.metas.device.adempiere.AttributeDeviceAccessor;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.producers.WebSocketProducerFactory;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

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
 * Creates {@link WebSocketProducer} instances which are reading {@link AttributeDeviceAccessor#acquireValue()} and creates {@link JSONDeviceValueChangedEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
public class DeviceWebSocketProducerFactory implements WebSocketProducerFactory
{
	public static final String TOPIC = "/devices";
	private static final String TOPIC_AND_SLASH = TOPIC + "/";

	private final IDevicesHubFactory devicesHubFactory = Services.get(IDevicesHubFactory.class);

	public static String buildDeviceTopicName(@NonNull final String deviceId)
	{
		Check.assumeNotEmpty(deviceId, "deviceId is not empty");
		return TOPIC_AND_SLASH + deviceId;
	}

	@Nullable
	private static String extractDeviceIdFromTopicName(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();

		if (topicNameString.startsWith(TOPIC_AND_SLASH))
		{
			return topicNameString.substring(TOPIC_AND_SLASH.length());
		}
		else
		{
			return null;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(TOPIC_AND_SLASH).toString();
	}

	@Override
	public String getTopicNamePrefix()
	{
		return TOPIC_AND_SLASH;
	}

	@Override
	public WebSocketProducer createProducer(final WebsocketTopicName topicName)
	{
		final String deviceId = extractDeviceIdFromTopicName(topicName);
		if (deviceId == null)
		{
			throw new AdempiereException("Cannot extract deviceId from topic name `" + topicName + "`");
		}
		return new DeviceWebSocketProducer(devicesHubFactory, deviceId);
	}

}
