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
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;
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

	public static final String CFG_DEVICE_PREFIX = "de.metas.device";
	public static final String CFG_DEVICE_NAME_PREFIX = CFG_DEVICE_PREFIX + ".Name";

	/**
	 * Returns all device names from ordered by their <code>AD_SysConfig</code> keys.
	 * 
	 * @param ctx we use the <code>AD_Client_ID</code> and <code>AD_Org_ID</code> from this ctx.
	 * @return the list of values from AD_SysConfig, where <code>AD_SysConfig.Name</code> starts with {@value #CFG_DEVICE_NAME_PREFIX}. The values are ordered lexically be the key
	 */
	List<String> getAllDeviceNames(Properties ctx);

	/**
	 * Returns the device names of all devices that are assigned to the given <code>hostName</code> (or to <code>0.0.0.0</code>) and the given attribute's {@link I_M_Attribute#getValue()}.
	 * 
	 * @param attrib
	 * @param host the name and IP of the client for which we want to list all devices. Note that the code will look for devices assigned to the given host, IP <b>and also</b> for those that are
	 *            assigned to the IP <code>0.0.0.0</code>.
	 * @return
	 */
	List<String> getAllDeviceNamesForAttrAndHost(I_M_Attribute attrib, IHostIdentifier host);

	/**
	 * Retrieves ADempiere's config parameters for the device with the given name and initializes and configures it. If there is already a device for the given <code>AD_Client_ID</code>,
	 * <code>AD_Org_ID</code> (both fromm <code>ctx</code>), <code>deviceName</code> and <code>hostName</code>, then that device is returned.
	 * 
	 * @param ctx
	 * @param deviceName
	 * @param host the name and IP of the client for which we configure the device. Note that the code will first look for config params for the given host name, then for the IP and then (if there are none) fall back to
	 *            look for params for the IP <code>0.0.0.0</code>.
	 * @return
	 */
	IDevice createAndConfigureDeviceOrReturnExisting(Properties ctx, String deviceName, IHostIdentifier host);

	/**
	 * Returns all requests that the given device supports for the given <code>attribute</code>.
	 * 
	 * @param deviceName
	 * @param attribute
	 * @param responseClazz optional, maybe be <code>null</code>. If set, then the result is filtered and only those requests are returned whose response is assignable from this parameter.
	 * @return
	 * @see Class#isAssignableFrom(Class)
	 */
	<T extends IDeviceResponse> List<IDeviceRequest<T>> getAllRequestsFor(String deviceName, I_M_Attribute attribute, Class<T> responseClazz);
}
