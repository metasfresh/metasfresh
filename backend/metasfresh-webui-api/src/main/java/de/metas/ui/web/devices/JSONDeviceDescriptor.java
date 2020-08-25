package de.metas.ui.web.devices;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 * Describes a device access point used to acquire values via websocket.
 * 
 * The websocket message format is defined by {@link JSONDeviceValueChangedEvent}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDeviceDescriptor
{
	public static List<JSONDeviceDescriptor> ofList(
			@NonNull final DeviceDescriptorsList list,
			@NonNull final String adLanguage)
	{
		if (list.isEmpty())
		{
			return ImmutableList.of();
		}

		return list.stream()
				.map(descriptor -> of(descriptor, adLanguage))
				.collect(ImmutableList.toImmutableList());
	}

	public static JSONDeviceDescriptor of(
			@NonNull final DeviceDescriptor descriptor,
			@NonNull final String adLanguage)
	{
		return JSONDeviceDescriptor.builder()
				.deviceId(descriptor.getDeviceId())
				.caption(descriptor.getCaption().translate(adLanguage))
				.websocketEndpoint(descriptor.getWebsocketEndpoint())
				.build();
	}

	@JsonProperty("deviceId")
	private final String deviceId;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("websocketEndpoint")
	private final String websocketEndpoint;

	@Builder
	private JSONDeviceDescriptor(
			@JsonProperty("deviceId") @NonNull final String deviceId,
			@JsonProperty("caption") @Nullable final String caption,
			@JsonProperty("websocketEndpoint") @NonNull final String websocketEndpoint)
	{
		Check.assumeNotEmpty(deviceId, "deviceId is not empty");
		Check.assumeNotEmpty(websocketEndpoint, "websocketEndpoint is not empty");

		this.deviceId = deviceId;
		this.caption = caption;
		this.websocketEndpoint = websocketEndpoint;
	}
}
