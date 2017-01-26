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
import org.adempiere.util.net.IHostIdentifier;

import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.IDeviceResponse;

/**
 * Service to find and configure available devices. Currently the implementation uses <code>AD_SysConfig</code> to store the data.
 * <p>
 * Note that currently devices are only accessed in the context of attributes, but that might change and then there will probably be further <code>getAllDeviceNamesFor..()</code> methods, or some
 * sort of lookup query framework.
 * 
 * @author ts
 * 
 */
public interface IDeviceBL extends ISingletonService
{
	/**
	 * Returns the device names of all devices that are assigned to the given <code>hostName</code> (or to <code>0.0.0.0</code>) and the given attribute code.
	 * 
	 * @param attributeCode
	 * @param host
	 * @param adClientId
	 * @param adOrgId
	 */
	List<String> getAllDeviceNamesForAttrAndHost(String attributeCode, IHostIdentifier host, int adClientId, int adOrgId);

	/**
	 * Retrieves metasfresh's configuration parameters for the device identified by <code>deviceId</code> and initializes and configures it.
	 * If there is already a device for the given <code>deviceId</code>, then that device is returned.
	 * 
	 * @param deviceId
	 */
	IDevice createAndConfigureDeviceOrReturnExisting(DeviceId deviceId);

	/**
	 * Returns all requests that the given device supports for the given <code>attributeDeviceId</code>.
	 * 
	 * @param attributeDeviceId
	 * @param responseClazz optional, maybe be <code>null</code>. If set, then the result is filtered and only those requests are returned whose response is assignable from this parameter.
	 */
	<T extends IDeviceResponse> List<IDeviceRequest<T>> getAllRequestsFor(AttributeDeviceId attributeDeviceId, Class<T> responseClazz);
}
