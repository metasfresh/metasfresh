package de.metas.ui.web.devices;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDeviceValueChangedEvent implements Serializable
{
	public static final JSONDeviceValueChangedEvent of(final String deviceId, final Object jsonValue)
	{
		final long timestampMillis = System.currentTimeMillis();
		return new JSONDeviceValueChangedEvent(deviceId, jsonValue, timestampMillis);
	}

	@JsonProperty("deviceId")
	private final String deviceId;

	@JsonProperty("value")
	private final Object value;

	@JsonProperty("timestampMillis")
	private final long timestampMillis;

	@JsonCreator
	private JSONDeviceValueChangedEvent( //
			@JsonProperty("deviceId") final String deviceId //
			, @JsonProperty("value") final Object value //
			, @JsonProperty("timestampMillis") final long timestampMillis //
	)
	{
		this.deviceId = deviceId;
		this.value = value;
		this.timestampMillis = timestampMillis;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("deviceId", deviceId)
				.add("value", value)
				.add("timestampMillis", timestampMillis)
				.toString();
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public Object getValue()
	{
		return value;
	}

	public long getTimestampMillis()
	{
		return timestampMillis;
	}
}
