package de.metas.device.adempiere;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.IDeviceResponse;

/**
 * Service to find and configure available devices
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDeviceBL extends ISingletonService
{
	/**
	 * Creates the {@link IDevice} instance for given configuration.
	 * 
	 * @param deviceConfig device configuration
	 * @return device instance
	 */
	IDevice createAndConfigureDevice(DeviceConfig deviceConfig);

	/**
	 * Returns all requests that the given device configuration and given <code>attributeCode</code>.
	 * 
	 * @param deviceConfig device configuration
	 * @param attributeCode attribute code
	 * @param responseClazz optional, maybe be <code>null</code>. If set, then the result is filtered and only those requests are returned whose response is assignable from this parameter.
	 */
	<T extends IDeviceResponse> List<IDeviceRequest<T>> getAllRequestsFor(DeviceConfig deviceConfig, String attributeCode, Class<T> responseClazz);

}
