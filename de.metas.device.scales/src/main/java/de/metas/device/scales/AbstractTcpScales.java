package de.metas.device.scales;

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

import java.util.ArrayList;
import java.util.List;

import de.metas.device.api.AbstractBaseDevice;
import de.metas.device.api.IDeviceRequestHandler;
import de.metas.device.api.IDeviceResponse;
import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.IDeviceConfigParam;
import de.metas.device.api.request.IDeviceResponseGetConfigParams;
import de.metas.device.scales.endpoint.ITcpConnectionEndPoint;
import de.metas.device.scales.impl.ConfigureDeviceHandler;
import de.metas.device.scales.impl.ScalesGetGrossWeightHandler;
import de.metas.device.scales.util.DeviceConfigParam;

public abstract class AbstractTcpScales extends AbstractBaseDevice
{
	public static final String PARAM_ENDPOINT_CLASS = "Endpoint.Class";
	public static final String PARAM_ENDPOINT_PORT = "Endpoint.Port";
	public static final String PARAM_ENDPOINT_IP = "Endpoint.IP";
	public static final String PARAM_ENDPOINT_RETURN_LAST_LINE = "Endpoint.ReturnLastLine";

	/**
	 * Weight values coming from the device shall be rounded to this precision before they are forwarded to metasfresh.<br>
	 * Omit or set to less than zero to disable rounding.
	 */
	public static final String PARAM_ROUND_TO_PRECISION = "RoundToPrecision";

	private ITcpConnectionEndPoint endPoint;

	private int roundToPrecision;

	public abstract void configureStatic();

	public ITcpConnectionEndPoint getEndPoint()
	{
		return endPoint;
	}

	public void setEndPoint(final ITcpConnectionEndPoint endPoint)
	{
		this.endPoint = endPoint;
	}

	/**
	 * See {@link #setRoundToPrecision(int)}.
	 *
	 * @return
	 */
	public int getRoundToPrecision()
	{
		return roundToPrecision;
	}

	/**
	 * This value is used to configure the {@link ScalesGetGrossWeightHandler}.
	 *
	 * @param roundToPrecision may be <code>null</code> in that case, not rounding will be done.
	 * @see ScalesGetGrossWeightHandler#setroundWeightToPrecision(int)
	 */
	public void setRoundToPrecision(final Integer roundToPrecision)
	{
		this.roundToPrecision = roundToPrecision == null
				? -1
				: roundToPrecision;
	}

	@Override
	public IDeviceResponseGetConfigParams getRequiredConfigParams()
	{
		final List<IDeviceConfigParam> params = new ArrayList<IDeviceConfigParam>();
		// params.add(new DeviceConfigParamPojo("DeviceClass", "DeviceClass", "")); // if we can query this device for its params, then we already know the device class.
		params.add(new DeviceConfigParam(PARAM_ENDPOINT_CLASS, "Endpoint.Class", ""));
		params.add(new DeviceConfigParam(PARAM_ENDPOINT_IP, "Endpoint.IP", ""));
		params.add(new DeviceConfigParam(PARAM_ENDPOINT_PORT, "Endpoint.Port", ""));
		params.add(new DeviceConfigParam(PARAM_ENDPOINT_RETURN_LAST_LINE, PARAM_ENDPOINT_RETURN_LAST_LINE, "N"));
		params.add(new DeviceConfigParam(PARAM_ROUND_TO_PRECISION, "RoundToPrecision", "-1"));

		return new IDeviceResponseGetConfigParams()
		{
			@Override
			public List<IDeviceConfigParam> getParams()
			{
				return params;
			}
		};
	}

	@Override
	public IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse> getConfigureDeviceHandler()
	{
		return new ConfigureDeviceHandler(this);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " with Endpoint " + endPoint.toString();
	}
}
