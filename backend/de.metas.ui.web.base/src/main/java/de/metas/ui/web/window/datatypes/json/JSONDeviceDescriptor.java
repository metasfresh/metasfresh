package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import de.metas.device.websocket.JSONDeviceValueChangedEvent;
import de.metas.ui.web.process.adprocess.device_providers.DeviceDescriptor;
import de.metas.ui.web.process.adprocess.device_providers.DeviceDescriptorsList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

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
	String deviceId;
	String caption;
	String websocketEndpoint;

	@Builder
	@Jacksonized
	private JSONDeviceDescriptor(
			@NonNull final String deviceId,
			@Nullable final String caption,
			@NonNull final String websocketEndpoint)
	{
		Check.assumeNotEmpty(deviceId, "deviceId is not empty");
		Check.assumeNotEmpty(websocketEndpoint, "websocketEndpoint is not empty");

		this.deviceId = deviceId;
		this.caption = caption;
		this.websocketEndpoint = websocketEndpoint;
	}

	public static List<JSONDeviceDescriptor> ofList(
			@NonNull final DeviceDescriptorsList list,
			@NonNull final String adLanguage)
	{
		return list.stream()
				.map(descriptor -> of(descriptor, adLanguage))
				.collect(ImmutableList.toImmutableList());
	}

	private static JSONDeviceDescriptor of(
			@NonNull final DeviceDescriptor descriptor,
			@NonNull final String adLanguage)
	{
		return JSONDeviceDescriptor.builder()
				.deviceId(descriptor.getDeviceId().getAsString())
				.caption(descriptor.getCaption().translate(adLanguage))
				.websocketEndpoint(descriptor.getWebsocketEndpoint().getAsString())
				.build();
	}
}
