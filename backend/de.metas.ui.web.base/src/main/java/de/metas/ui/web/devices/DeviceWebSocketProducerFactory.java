package de.metas.ui.web.devices;

import com.google.common.collect.ImmutableList;
import de.metas.device.adempiere.AttributeDeviceAccessor;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.ui.web.websocket.WebSocketProducer;
import de.metas.ui.web.websocket.WebSocketProducerFactory;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
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
 *
 */
@Component
public class DeviceWebSocketProducerFactory implements WebSocketProducerFactory
{
	private static final String TOPICNAME_Prefix = WebsocketTopicNames.TOPIC_Devices + "/";

	public static String buildDeviceTopicName(final String deviceId)
	{
		Check.assumeNotEmpty(deviceId, "deviceId is not empty");
		return TOPICNAME_Prefix + deviceId;
	}

	@Nullable
	public static String extractDeviceIdFromTopicName(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();

		if (topicNameString.startsWith(TOPICNAME_Prefix))
		{
			return topicNameString.substring(TOPICNAME_Prefix.length());
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getTopicNamePrefix()
	{
		return TOPICNAME_Prefix;
	}

	@Override
	public WebSocketProducer createProducer(final WebsocketTopicName topicName)
	{
		final String deviceId = extractDeviceIdFromTopicName(topicName);
		if(deviceId == null)
		{
			throw new AdempiereException("Cannot extract deviceId from topic name `"+topicName+"`");
		}
		return new DeviceWebSocketProducer(deviceId);
	}

	@ToString
	private static final class DeviceWebSocketProducer implements WebSocketProducer
	{
		private final String deviceId;

		public DeviceWebSocketProducer(final String deviceId)
		{
			Check.assumeNotEmpty(deviceId, "deviceId is not empty");
			this.deviceId = deviceId;
		}

		@Override
		public List<JSONDeviceValueChangedEvent> produceEvents(@NonNull final JSONOptions jsonOpts)
		{
			final AttributeDeviceAccessor deviceAccessor = Services.get(IDevicesHubFactory.class)
					.getDefaultAttributesDevicesHub()
					.getAttributeDeviceAccessorById(deviceId);
			if (deviceAccessor == null)
			{
				throw new RuntimeException("Device accessor no longer exists for: " + deviceId);
			}

			final Object valueObj = deviceAccessor.acquireValue();
			final Object valueJson = Values.valueToJsonObject(valueObj, jsonOpts);

			return ImmutableList.of(JSONDeviceValueChangedEvent.of(deviceId, valueJson));
		}
	}
}
