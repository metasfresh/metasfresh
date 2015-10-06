package de.metas.device.api;

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


import java.util.HashMap;
import java.util.Map;

import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.DeviceRequestGetConfigParams;
import de.metas.device.api.request.IDeviceResponseGetConfigParams;

public abstract class AbstractBaseDevice implements IDevice
{
	/**
	 * The constructor registers a handler for the {@link DeviceRequestGetConfigParams} request. That handler returns the result of {@link #getRequiredConfigParams()}. This way each subclass can be
	 * queried for its config params and can be configured with after is was instantiated.
	 */
	public AbstractBaseDevice()
	{
		// registering the the handler that returns out requered config info
		final IDeviceRequestHandler<DeviceRequestGetConfigParams, IDeviceResponseGetConfigParams> handler = new IDeviceRequestHandler<DeviceRequestGetConfigParams, IDeviceResponseGetConfigParams>()
		{
			@Override
			public IDeviceResponseGetConfigParams handleRequest(DeviceRequestGetConfigParams request)
			{
				return getRequiredConfigParams();
			}
		};
		registerHandler(DeviceRequestGetConfigParams.class, handler);
		
		registerHandler(DeviceRequestConfigureDevice.class, getConfigureDeviceHandler());
	}

	@SuppressWarnings("rawtypes")
	private final Map<Class<?>, IDeviceRequestHandler> requestType2Handler = new HashMap<Class<?>, IDeviceRequestHandler>();

	public <O extends IDeviceResponse, I extends IDeviceRequest<O>, H extends IDeviceRequestHandler<I, O>> void registerHandler(final Class<I> requestType, final H handler)
	{
		requestType2Handler.put(requestType, handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O extends IDeviceResponse, I extends IDeviceRequest<O>> O accessDevice(final I input)
	{
		final IDeviceRequestHandler<IDeviceRequest<IDeviceResponse>, IDeviceResponse> deviceRequestHandler = requestType2Handler.get(input.getClass());
		return (O)deviceRequestHandler.handleRequest((IDeviceRequest<IDeviceResponse>)input);
	}

	/**
	 * Currently this method is called from the device's default constructor, so it must work without making any assumption about the device's initialization state.
	 * 
	 * @return
	 */
	public abstract IDeviceResponseGetConfigParams getRequiredConfigParams();
	
	/**
	 * Returns that particular request handler which is in charge of configuring the device using parmeters from the "outside world".
	 * @return
	 */
	public abstract IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse>getConfigureDeviceHandler();
}
