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


import java.util.List;

import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.IDeviceResponse;

/**
 * Every {@link IDevice} implementation shall return an instance of this interface when its {@link IDevice#accessDevice(IDeviceRequest)} method is called with {@link DeviceRequestGetConfigParams} as
 * parameter.
 * 
 * @author ts
 * 
 */
public interface IDeviceResponseGetConfigParams extends IDeviceResponse
{
	/**
	 * 
	 * @return the config params {@link IDeviceConfigParam#getValue()} shall be <code>==</code> {@link IDeviceConfigParam#VALUE_UNSPECIFIED} for each alement of the list. The list can be empty, but not
	 *         <code>null</code>. Note that we return a list, because it might make sense to present the parameters to the user in a particular order.
	 */
	List<IDeviceConfigParam> getParams();
}
