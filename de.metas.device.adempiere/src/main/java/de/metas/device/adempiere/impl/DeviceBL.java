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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.net.IHostIdentifier;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.CCache;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.device.adempiere.AttributeDeviceId;
import de.metas.device.adempiere.DeviceConfigException;
import de.metas.device.adempiere.DeviceId;
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
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public static final String CFG_DEVICE_PREFIX = "de.metas.device";
	public static final String CFG_DEVICE_NAME_PREFIX = CFG_DEVICE_PREFIX + ".Name";

	private final transient CCache<DeviceId, IDevice> _configuredDevices = CCache.<DeviceId, IDevice> newCache("DevicesByDeviceId", 10, 0)
			.addResetForTableName(I_AD_SysConfig.Table_Name);

	@Override
	public List<String> getAllDeviceNamesForAttrAndHost(final String attributeCode, final IHostIdentifier host, final int adClientId, final int adOrgId)
	{
		final List<String> result = new ArrayList<>();

		for (final String currentDeviceName : getAllDeviceNames(adClientId, adOrgId))
		{
			// now we check for each device if..

			// 1. is it configured to work with the given 'attribute'?
			final String attribSysConfigPrefix = CFG_DEVICE_PREFIX + "." + currentDeviceName + ".AttributeInternalName";
			final Map<String, String> attribsForCurrentDevice = sysConfigBL.getValuesForPrefix(attribSysConfigPrefix, adClientId, adOrgId);

			if (!attribsForCurrentDevice.containsValue(attributeCode))
			{
				logger.info("Found no SysConfig value for attribute {}; SysConfig-prefix={}; Found values: {}", attributeCode, attribSysConfigPrefix, attribsForCurrentDevice);
				continue;
			}

			// 2. is there an "AvailableOn"-record with the given 'hostName'
			final String availableOnSysConfigPrefix = CFG_DEVICE_PREFIX + "." + currentDeviceName + ".AvailableOn";
			final Map<String, String> availiabilitesForCurrentDevice = sysConfigBL.getValuesForPrefix(availableOnSysConfigPrefix, adClientId, adOrgId);

			boolean hostMatches = false;
			for (final String currentAddressOrHostName : availiabilitesForCurrentDevice.values())
			{
				if (currentAddressOrHostName == null)
				{
					continue;
				}
				// note that instead of this, we might want to use commons-net in future, to be able to check for stuff like "10.10.10.0/255.255.255.128"
				if (currentAddressOrHostName.equals("0.0.0.0")
						|| currentAddressOrHostName.equalsIgnoreCase(host.getHostName())
						|| currentAddressOrHostName.equals(host.getIP()))
				{
					hostMatches = true;
					break;
				}
			}
			if (!hostMatches)
			{
				logger.info("Found no SysConfig value for host {}; SysConfig-prefix={}; Found values for: {}", host, availableOnSysConfigPrefix, availiabilitesForCurrentDevice);
				continue;
			}

			result.add(currentDeviceName);
		}
		return result;
	}

	@Override
	public IDevice createAndConfigureDeviceOrReturnExisting(final DeviceId deviceId)
	{
		return _configuredDevices.getOrLoad(deviceId, () -> createAndConfigureDevice(deviceId));
	}

	private final IDevice createAndConfigureDevice(final DeviceId deviceId)
	{
		final String deviceName = deviceId.getDeviceName();
		final int adClientId = deviceId.getAD_Client_ID();
		final int adOrgId = deviceId.getAD_Org_ID();
		final IHostIdentifier clientHost = deviceId.getClientHost();

		final String deviceClass = getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, clientHost, "DeviceClass", adClientId, adOrgId, null);

		//
		// Instantiate the device
		final IDevice device;
		try
		{
			device = Util.getInstance(IDevice.class, deviceClass);
		}
		catch (final Exception e)
		{
			throw DeviceConfigException.permanentFailure("Failed loading deviceClass: " + deviceClass, e);
		}

		//
		// Access the device, get it's configuration parameters and set them from sysconfig
		final IDeviceResponseGetConfigParams deviceParamsResponse = device.accessDevice(DeviceRequestGetConfigParams.get());
		final List<IDeviceConfigParam> deviceParams = deviceParamsResponse.getParams();
		for (final IDeviceConfigParam param : deviceParams)
		{
			final String paramValue = getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, clientHost, param.getSystemName(), adClientId, adOrgId, param.getDefaultValue());
			param.setValue(paramValue);
		}

		//
		// Configure the device
		final DeviceRequestConfigureDevice cfgRequest = new DeviceRequestConfigureDevice(deviceParams);
		device.accessDevice(cfgRequest);

		return device;
	}

	/**
	 *
	 * @param prefix
	 * @param host
	 * @param suffix
	 * @param adClientId
	 * @param adOrgId
	 * @param defaultStr
	 * @return value; never returns null
	 * @throws DeviceConfigException if configuration parameter was not found and given <code>defaultStr</code> is <code>null</code>.
	 */
	private String getSysconfigValueWithHostNameFallback(final String prefix,
			final IHostIdentifier host,
			final String suffix,
			final int adClientId,
			final int adOrgId,
			final String defaultStr)
	{
		//
		// Try by hostname
		final String deviceKeyWithHostName = prefix + "." + host.getHostName() + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithHostName, adClientId, adOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Try by host's IP
		final String deviceKeyWithIP = prefix + "." + host.getIP() + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithIP, adClientId, adOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Try by any host (i.e. 0.0.0.0)
		final String deviceKeyWithWildCard = prefix + "." + "0.0.0.0" + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithWildCard, adClientId, adOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Fallback to default
		if (defaultStr != null)
		{
			return defaultStr;
		}

		//
		// Throw exception
		final String msg = "@NotFound@: @AD_SysConfig@ " + deviceKeyWithHostName + ", " + deviceKeyWithIP + ", " + deviceKeyWithWildCard
				+ "; @AD_Client_ID@=" + adClientId + " @AD_Org_ID@=" + adOrgId;
		throw new DeviceConfigException(msg);
	}

	/**
	 * Returns all device names from ordered by their <code>AD_SysConfig</code> keys.
	 *
	 * @param adClientId
	 * @param adOrgId
	 * @return the list of values from AD_SysConfig, where <code>AD_SysConfig.Name</code> starts with {@value #CFG_DEVICE_NAME_PREFIX}. The values are ordered lexically be the key
	 */
	private List<String> getAllDeviceNames(final int adClientId, final int adOrgId)
	{
		final Map<String, String> deviceNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_NAME_PREFIX, adClientId, adOrgId);
		return new ArrayList<>(new TreeMap<String, String>(deviceNames).values());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IDeviceResponse> List<IDeviceRequest<T>> getAllRequestsFor(final AttributeDeviceId attributeDeviceId, final Class<T> responseClazz)
	{
		final String deviceName = attributeDeviceId.getDeviceName();
		final String attributeCode = attributeDeviceId.getAttributeCode();
		final int adClientId = attributeDeviceId.getAD_Client_ID();
		final int adOrgId = attributeDeviceId.getAD_Org_ID();

		final List<IDeviceRequest<T>> result = new ArrayList<>();

		final Map<String, String> requestClassNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_PREFIX + "." + deviceName + "." + attributeCode, adClientId, adOrgId);
		for (final String currentRequestClassName : requestClassNames.values())
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
