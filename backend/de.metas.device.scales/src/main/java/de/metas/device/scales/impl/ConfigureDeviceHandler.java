package de.metas.device.scales.impl;

import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.device.scales
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.device.api.DeviceException;
import de.metas.device.api.IDeviceRequestHandler;
import de.metas.device.api.IDeviceResponse;
import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.IDeviceConfigParam;
import de.metas.device.scales.AbstractTcpScales;
import de.metas.device.scales.endpoint.TcpConnectionEndPoint;

public class ConfigureDeviceHandler implements IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse>
{
	private final AbstractTcpScales device;

	public ConfigureDeviceHandler(final AbstractTcpScales device)
	{
		this.device = device;
	}

	@Override
	public IDeviceResponse handleRequest(final DeviceRequestConfigureDevice request)
	{
		final Map<String, IDeviceConfigParam> parameters = request.getParameters();

		final IDeviceConfigParam epClass = parameters.get(AbstractTcpScales.PARAM_ENDPOINT_CLASS);
		final IDeviceConfigParam epHost = parameters.get(AbstractTcpScales.PARAM_ENDPOINT_IP);
		final IDeviceConfigParam epPort = parameters.get(AbstractTcpScales.PARAM_ENDPOINT_PORT);
		final IDeviceConfigParam epReturnLastLine = parameters.get(AbstractTcpScales.PARAM_ENDPOINT_RETURN_LAST_LINE);
		final IDeviceConfigParam epReadTimeoutMillis = parameters.get(AbstractTcpScales.PARAM_ENDPOINT_READ_TIMEOUT_MILLIS);

		final IDeviceConfigParam roundToPrecision = parameters.get(AbstractTcpScales.PARAM_ROUND_TO_PRECISION); // task 09207

		TcpConnectionEndPoint ep = null;

		try
		{
			@SuppressWarnings("unchecked")
			final Class<TcpConnectionEndPoint> c = (Class<TcpConnectionEndPoint>)Class.forName(epClass.getValue());
			ep = c.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new DeviceException("Caught a ClassNotFoundException: " + e.getLocalizedMessage(), e);
		}
		catch (InstantiationException e)
		{
			throw new DeviceException("Caught an InstantiationException: " + e.getLocalizedMessage(), e);
		}
		catch (IllegalAccessException e)
		{
			throw new DeviceException("Caught an IllegalAccessException: " + e.getLocalizedMessage(), e);
		}

		ep.setHost(epHost.getValue());
		ep.setPort(Integer.parseInt(epPort.getValue()));
		ep.setReturnLastLine(BooleanUtils.toBoolean(epReturnLastLine.getValue()));
		ep.setReadTimeoutMillis(Integer.parseInt(epReadTimeoutMillis.getValue()));

		device.setEndPoint(ep);
		device.setRoundToPrecision(Integer.parseInt(roundToPrecision.getValue()));
		device.configureStatic();

		return new IDeviceResponse()
		{
		};
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("device", device)
				.toString();
	}
}
