package de.metas.device.api.request;

/*
 * #%L
 * de.metas.device.api
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.IDeviceResponse;

/**
 * Generic {@link IDevice} input parameter which can be used to pass a configuration to a {@link IDevice}.
 * 
 * @author ts
 * 
 */
public class DeviceRequestConfigureDevice implements IDeviceRequest<IDeviceResponse>
{
	private final List<IDeviceConfigParam> parameters;

	public DeviceRequestConfigureDevice(final List<IDeviceConfigParam> parameters)
	{
		this.parameters = Collections.unmodifiableList(parameters);
	}

	public Map<String, IDeviceConfigParam> getParameters()
	{
		final LinkedHashMap<String, IDeviceConfigParam> result = new LinkedHashMap<String, IDeviceConfigParam>();
		for(final IDeviceConfigParam currentParameter : parameters)
		{
			result.put(currentParameter.getSystemName(), currentParameter);
		}
		return result;
	}

	@Override
	public Class<IDeviceResponse> getResponseClass()
	{
		return IDeviceResponse.class;
	}

	@Override
	public String toString()
	{
		return String.format("DeviceRequestConfigureDevice [parameters=%s]", parameters);
	}

}
