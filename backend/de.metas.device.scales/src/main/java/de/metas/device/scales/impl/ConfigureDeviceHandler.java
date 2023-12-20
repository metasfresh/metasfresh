package de.metas.device.scales.impl;

import com.google.common.base.MoreObjects;
import de.metas.device.api.DeviceException;
import de.metas.device.api.IDeviceRequestHandler;
import de.metas.device.api.IDeviceResponse;
import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.IDeviceConfigParam;
import de.metas.device.scales.AbstractTcpScales;
import de.metas.device.scales.endpoint.TcpConnectionEndPoint;
import de.metas.device.scales.endpoint.TcpConnectionReadLineEndPoint;
import lombok.NonNull;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Map;

public class ConfigureDeviceHandler implements IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse>
{
	private final AbstractTcpScales device;

	public ConfigureDeviceHandler(final AbstractTcpScales device)
	{
		this.device = device;
	}

	@Override
	public IDeviceResponse handleRequest(@NonNull final DeviceRequestConfigureDevice request)
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
		catch (final ClassNotFoundException e)
		{
			throw new DeviceException("Caught a ClassNotFoundException: " + e.getLocalizedMessage(), e);
		}
		catch (final InstantiationException e)
		{
			throw new DeviceException("Caught an InstantiationException: " + e.getLocalizedMessage(), e);
		}
		catch (final IllegalAccessException e)
		{
			throw new DeviceException("Caught an IllegalAccessException: " + e.getLocalizedMessage(), e);
		}

		ep.setHost(epHost.getValue());
		ep.setPort(Integer.parseInt(epPort.getValue()));
		if (ep instanceof TcpConnectionReadLineEndPoint)
		{
			((TcpConnectionReadLineEndPoint)ep).setReturnLastLine(BooleanUtils.toBoolean(epReturnLastLine.getValue()));
		}
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
