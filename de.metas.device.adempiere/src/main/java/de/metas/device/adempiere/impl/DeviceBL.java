package de.metas.device.adempiere.impl;

/*
 * #%L
 * de.metas.device.adempiere
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
import java.util.Collection;
import java.util.List;

import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.device.adempiere.DeviceConfig;
import de.metas.device.adempiere.DeviceConfigException;
import de.metas.device.adempiere.IDeviceBL;
import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.IDeviceResponse;
import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.DeviceRequestGetConfigParams;
import de.metas.device.api.request.IDeviceConfigParam;
import de.metas.device.api.request.IDeviceResponseGetConfigParams;
import de.metas.logging.LogManager;

public class DeviceBL implements IDeviceBL
{
	private static final transient Logger logger = LogManager.getLogger(DeviceBL.class);

	@Override
	public IDevice createAndConfigureDevice(final DeviceConfig deviceConfig)
	{
		//
		// Instantiate the device
		final String deviceClassname = deviceConfig.getDeviceClassname();
		final IDevice device;
		try
		{
			device = Util.getInstance(IDevice.class, deviceClassname);
		}
		catch (final Exception e)
		{
			throw DeviceConfigException.permanentFailure("Failed loading deviceClass: " + deviceClassname, e);
		}

		//
		// Access the device, get it's configuration parameters and set them from sysconfig
		final IDeviceResponseGetConfigParams deviceParamsResponse = device.accessDevice(DeviceRequestGetConfigParams.get());
		final List<IDeviceConfigParam> deviceParams = deviceParamsResponse.getParams();
		for (final IDeviceConfigParam param : deviceParams)
		{
			final String paramValue = deviceConfig.getParameterValue(param.getSystemName(), param.getDefaultValue());
			param.setValue(paramValue);
		}

		//
		// Configure the device
		final DeviceRequestConfigureDevice cfgRequest = new DeviceRequestConfigureDevice(deviceParams);
		device.accessDevice(cfgRequest);

		return device;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IDeviceResponse> List<IDeviceRequest<T>> getAllRequestsFor(final DeviceConfig deviceConfig, final String attributeCode, final Class<T> responseClazz)
	{
		final Collection<String> requestClassnames = deviceConfig.getRequestClassnames(attributeCode);
		if(requestClassnames.isEmpty())
		{
			logger.warn("Possible configuration issue on {} for attribute '{}': no request classnames found", deviceConfig, attributeCode);
			return ImmutableList.of();
		}
		
		final List<IDeviceRequest<T>> result = new ArrayList<>();
		for (final String currentRequestClassName : requestClassnames)
		{
			@SuppressWarnings("rawtypes")
			final IDeviceRequest request = Util.getInstance(IDeviceRequest.class, currentRequestClassName);
			if (responseClazz == null || responseClazz.isAssignableFrom(request.getResponseClass()))
			{
				result.add(request);
			}
		}
		return result;
	}
}
