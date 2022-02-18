package de.metas.device.websocket;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.device.accessor.DeviceId;
import lombok.NonNull;
import lombok.Value;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDeviceValueChangedEvent
{
	String deviceId;
	Object value;
	long timestampMillis;

	public static JSONDeviceValueChangedEvent of(@NonNull final DeviceId deviceId, @Nullable final Object jsonValue)
	{
		final long timestampMillis = System.currentTimeMillis();
		return new JSONDeviceValueChangedEvent(deviceId.getAsString(), jsonValue, timestampMillis);
	}
}
