package de.metas.ui.web.devices;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;

import de.metas.device.adempiere.AttributesDevicesHub.AttributeDeviceAccessor;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebSocketProducer;
import de.metas.ui.web.websocket.WebSocketProducerFactory;
import de.metas.ui.web.window.datatypes.Values;

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
 * Creates {@link WebSocketProducer} instances which are reading {@link AttributeDeviceAccessor#acquireValue()} and creates {@link JSONDeviceValueChangedEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class DeviceWebSocketProducerFactory implements WebSocketProducerFactory
{
	private static final String TOPICNAME_Prefix = WebSocketConfig.TOPIC_Devices + "/";

	public static final String buildDeviceTopicName(final String deviceId)
	{
		Check.assumeNotEmpty(deviceId, "deviceId is not empty");
		return TOPICNAME_Prefix + deviceId;
	}

	public static final String extractDeviceIdFromTopicName(final String topicName)
	{
		if (topicName == null)
		{
			return null;
		}
		else if (topicName.startsWith(TOPICNAME_Prefix))
		{
			return topicName.substring(TOPICNAME_Prefix.length());
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
	public WebSocketProducer createProducer(final String topicName)
	{
		final String deviceId = extractDeviceIdFromTopicName(topicName);
		return new DeviceWebSocketProducer(deviceId);
	}

	private static final class DeviceWebSocketProducer implements WebSocketProducer
	{
		private final String deviceId;

		public DeviceWebSocketProducer(final String deviceId)
		{
			super();
			Check.assumeNotEmpty(deviceId, "deviceId is not empty");
			this.deviceId = deviceId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("deviceId", deviceId)
					.toString();
		}

		@Override
		public Object produceEvent()
		{
			final AttributeDeviceAccessor deviceAccessor = Services.get(IDevicesHubFactory.class)
					.getDefaultAttributesDevicesHub()
					.getAttributeDeviceAccessorById(deviceId);
			if (deviceAccessor == null)
			{
				throw new RuntimeException("Device accessor no longer exists for: " + deviceId);
			}

			final Object valueObj = deviceAccessor.acquireValue();
			final Object valueJson = Values.valueToJsonObject(valueObj);

			final JSONDeviceValueChangedEvent event = JSONDeviceValueChangedEvent.of(deviceId, valueJson);
			return event;
		}
	}
}
