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


import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;

/**
 * Generic input which can be used to make any {@link IDevice} return its input parameters
 * 
 * @author ts
 * 
 */
public final class DeviceRequestGetConfigParams implements IDeviceRequest<IDeviceResponseGetConfigParams>
{
	private final static DeviceRequestGetConfigParams instance = new DeviceRequestGetConfigParams();

	private DeviceRequestGetConfigParams()
	{
	}

	public static DeviceRequestGetConfigParams get()
	{
		return instance;
	}

	@Override
	public Class<IDeviceResponseGetConfigParams> getResponseClass()
	{
		return IDeviceResponseGetConfigParams.class;
	}

	@Override
	public String toString()
	{
		return String.format("DeviceRequestGetConfigParams []");
	}
}
