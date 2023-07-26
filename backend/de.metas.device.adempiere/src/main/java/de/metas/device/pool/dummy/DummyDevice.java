package de.metas.device.pool.dummy;

import java.util.Set;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.device.api.AbstractBaseDevice;
import de.metas.device.api.IDeviceRequestHandler;
import de.metas.device.api.IDeviceResponse;
import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.IDeviceResponseGetConfigParams;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.device.adempiere
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

public class DummyDevice extends AbstractBaseDevice
{
	private static final Logger logger = LogManager.getLogger(DummyDevice.class);

	public DummyDevice()
	{
		registerHandler(DummyDeviceRequest.class, this::handleRequest);
	}

	public static Set<String> getDeviceRequestClassnames()
	{
		return ImmutableSet.of(DummyDeviceRequest.class.getName());
	}

	@Override
	public IDeviceResponseGetConfigParams getRequiredConfigParams()
	{
		return ImmutableList::of;
	}

	@Override
	public IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse> getConfigureDeviceHandler()
	{
		return this::handleRequest;
	}

	private IDeviceResponse handleRequest(final DeviceRequestConfigureDevice request)
	{
		IDeviceResponse response = new IDeviceResponse()
		{
		};

		logger.info("handleRequest: {} => {}", request, response);
		return response;
	}

	private DummyDeviceResponse handleRequest(final DummyDeviceRequest request)
	{
		final DummyDeviceResponse response = DummyDeviceConfigPool.generateRandomResponse();
		logger.info("handleRequest: {} => {}", request, response);
		return response;
	}
}
