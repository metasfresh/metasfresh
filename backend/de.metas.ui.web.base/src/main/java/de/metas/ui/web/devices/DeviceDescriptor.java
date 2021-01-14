package de.metas.ui.web.devices;

import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
public class DeviceDescriptor
{
	String deviceId;
	ITranslatableString caption;
	String websocketEndpoint;

	@Builder
	private DeviceDescriptor(
			@NonNull final String deviceId,
			@NonNull final ITranslatableString caption,
			@NonNull final String websocketEndpoint)
	{
		Check.assumeNotEmpty(deviceId, "deviceId is not empty");
		Check.assumeNotEmpty(websocketEndpoint, "websocketEndpoint is not empty");

		this.deviceId = deviceId;
		this.caption = caption;
		this.websocketEndpoint = websocketEndpoint;
	}
}
