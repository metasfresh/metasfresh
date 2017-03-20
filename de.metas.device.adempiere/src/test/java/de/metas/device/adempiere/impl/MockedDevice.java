package de.metas.device.adempiere.impl;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.device.api.AbstractBaseDevice;
import de.metas.device.api.IDeviceRequestHandler;
import de.metas.device.api.IDeviceResponse;
import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.IDeviceConfigParam;
import de.metas.device.api.request.IDeviceResponseGetConfigParams;

/*
 * #%L
 * de.metas.device.adempiere
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

public class MockedDevice extends AbstractBaseDevice
{

	@Override
	public IDeviceResponseGetConfigParams getRequiredConfigParams()
	{
		return new IDeviceResponseGetConfigParams()
		{
			@Override
			public List<IDeviceConfigParam> getParams()
			{
				return ImmutableList.of();
			}

		};
	}

	@Override
	public IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse> getConfigureDeviceHandler()
	{
		return new IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse>()
		{
			@Override
			public IDeviceResponse handleRequest(final DeviceRequestConfigureDevice request)
			{
				return null;
			}
		};
	}

}
