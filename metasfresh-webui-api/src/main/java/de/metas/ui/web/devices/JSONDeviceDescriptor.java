package de.metas.ui.web.devices;

import java.io.Serializable;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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
 * Describes a device access point used to acquire values via websocket.
 * 
 * The websocket message format is defined by {@link JSONDeviceValueChangedEvent}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONDeviceDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	@JsonProperty("deviceId")
	private final String deviceId;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("websocketEndpoint")
	private final String websocketEndpoint;

	private JSONDeviceDescriptor(final Builder builder)
	{
		super();
		deviceId = builder.getDeviceId();
		caption = builder.getCaption();
		websocketEndpoint = builder.getWebsocketEndpoint();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("deviceId", deviceId)
				.add("caption", caption)
				.add("websocketEndpoint", websocketEndpoint)
				.toString();
	}

	public String getId()
	{
		return deviceId;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getWebsocketEndpoint()
	{
		return websocketEndpoint;
	}

	public static final class Builder
	{
		private String _deviceId;
		private String _caption;
		private String _websocketEndpoint;

		private Builder()
		{
			super();
		}

		public JSONDeviceDescriptor build()
		{
			return new JSONDeviceDescriptor(this);
		}

		public Builder setDeviceId(final String deviceId)
		{
			_deviceId = deviceId;
			return this;
		}

		private String getDeviceId()
		{
			Check.assumeNotEmpty(_deviceId, "deviceId is not empty");
			return _deviceId;
		}

		public Builder setCaption(final String caption)
		{
			_caption = caption;
			return this;
		}

		private String getCaption()
		{

			return _caption;
		}

		public Builder setWebsocketEndpoint(final String websocketEndpoint)
		{
			_websocketEndpoint = websocketEndpoint;
			return this;
		}

		private String getWebsocketEndpoint()
		{
			Check.assumeNotEmpty(_websocketEndpoint, "websocketEndpoint is not empty");
			return _websocketEndpoint;
		}
	}

}
