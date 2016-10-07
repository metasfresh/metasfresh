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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.net.IHostIdentifier;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

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

	public static final String CFG_DEVICE_PREFIX = "de.metas.device";
	public static final String CFG_DEVICE_NAME_PREFIX = CFG_DEVICE_PREFIX + ".Name";

	private Map<ArrayKey, IDevice> configuredDevices = new HashMap<ArrayKey, IDevice>();

	private static final Logger logger = LogManager.getLogger(DeviceBL.class);

	@Override
	public List<String> getAllDeviceNamesForAttrAndHost(final I_M_Attribute attribute, final IHostIdentifier host)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);
		final int ad_Client_ID = Env.getAD_Client_ID(ctx);
		final int ad_Org_ID = Env.getAD_Org_ID(ctx);

		final List<String> result = new ArrayList<String>();

		for (final String currentDeviceName : getAllDeviceNames(ctx))
		{
			// now we check for each device if..

			// 1. is it configured to work with the given 'attribute'?
			final String attribSysConfigPrefix = CFG_DEVICE_PREFIX + "." + currentDeviceName + ".AttributeInternalName";
			final Map<String, String> attribsForCurrentDevice = sysConfigBL.getValuesForPrefix(attribSysConfigPrefix, ad_Client_ID, ad_Org_ID);

			if (!attribsForCurrentDevice.containsValue(attribute.getValue()))
			{
				logger.info(
						String.format("Found no SysConfig value for attribute %s; SysConfig-prefix=%s; Found values: ", attribute.getValue(), attribSysConfigPrefix)
								+ attribsForCurrentDevice);
				continue;
			}

			// 2. is there an "AvailableOn"-record with the given 'hostName'
			final String availableOnSysConfigPrefix = CFG_DEVICE_PREFIX + "." + currentDeviceName + ".AvailableOn";
			final Map<String, String> availiabilitesForCurrentDevice = sysConfigBL.getValuesForPrefix(availableOnSysConfigPrefix, ad_Client_ID, ad_Org_ID);

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
				logger.info(
						String.format("Found no SysConfig value for host %s; SysConfig-prefix=%s; Found values for: ", host, availableOnSysConfigPrefix)
								+ availiabilitesForCurrentDevice);
				continue;
			}

			result.add(currentDeviceName);
		}
		return result;
	}

	@Override
	public IDevice createAndConfigureDeviceOrReturnExisting(final Properties ctx, final String deviceName, final IHostIdentifier host)
	{
		final int ad_Client_ID = Env.getAD_Client_ID(ctx);
		final int ad_Org_ID = Env.getAD_Org_ID(ctx);

		final ArrayKey key = Util.mkKey(deviceName, host, ad_Client_ID, ad_Org_ID);
		final IDevice existingDevice = configuredDevices.get(key);
		if (existingDevice != null)
		{
			return existingDevice;
		}

		final String deviceClass = getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, host, "DeviceClass", ad_Client_ID, ad_Org_ID, null);

		Check.errorIf(deviceClass == null, "Missing AD_SysConfig record {}", CFG_DEVICE_PREFIX + "." + deviceName + ".<host-identifier '" + host + "'>.DeviceClass");

		final IDevice device = Util.getInstance(IDevice.class, deviceClass);

		final IDeviceResponseGetConfigParams deviceParamsResponse = device.accessDevice(DeviceRequestGetConfigParams.get());
		final List<IDeviceConfigParam> deviceParams = deviceParamsResponse.getParams();
		for (final IDeviceConfigParam param : deviceParams)
		{
			final String paramValue = getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, host, param.getSystemName(), ad_Client_ID, ad_Org_ID, param.getDefaultValue());
			if (paramValue != null)
			{
				param.setValue(paramValue);
			}
		}

		final DeviceRequestConfigureDevice cfgRequest = new DeviceRequestConfigureDevice(deviceParams);
		device.accessDevice(cfgRequest);

		return device;
	}

	/**
	 *
	 * @param prefix
	 * @param host
	 * @param suffix
	 * @param ad_Client_ID
	 * @param ad_Org_ID
	 * @param defaultStr
	 * @return
	 * @throws DeviceConfigException is a config parameter was not found and given <code>defaultStr</code> is <code>null</code>.
	 */
	private String getSysconfigValueWithHostNameFallback(final String prefix,
			final IHostIdentifier host,
			final String suffix,
			final int ad_Client_ID,
			final int ad_Org_ID,
			final String defaultStr)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final String deviceKeyWithHostName = prefix + "." + host.getHostName() + "." + suffix;
		String value = sysConfigBL.getValue(deviceKeyWithHostName, ad_Client_ID, ad_Org_ID);
		if (value != null)
		{
			return value;
		}

		final String deviceKeyWithIP = prefix + "." + host.getIP() + "." + suffix;
		value = sysConfigBL.getValue(deviceKeyWithIP, ad_Client_ID, ad_Org_ID);
		if (value != null)
		{
			return value;
		}

		final String deviceKeyWithWildCard = prefix + "." + "0.0.0.0" + "." + suffix;
		value = sysConfigBL.getValue(deviceKeyWithWildCard, ad_Client_ID, ad_Org_ID);

		if (value == null)
		{
			if (defaultStr != null)
			{
				return defaultStr;
			}
			final String msg = "@NotFound@: @AD_SysConfig@ " + deviceKeyWithHostName + ", " + deviceKeyWithIP + ", " + deviceKeyWithWildCard
					+ "; @AD_Client_ID@=" + ad_Client_ID + " @AD_Org_ID@=" + ad_Org_ID;
			throw new DeviceConfigException(msg);
		}

		return value;
	}

	@Override
	public List<String> getAllDeviceNames(final Properties ctx)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final Map<String, String> deviceNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_NAME_PREFIX, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));

		return new ArrayList<String>(new TreeMap<String, String>(deviceNames).values());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IDeviceResponse> List<IDeviceRequest<T>> getAllRequestsFor(
			final String deviceName,
			final I_M_Attribute attribute,
			final Class<T> responseClazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);
		final int ad_Client_ID = Env.getAD_Client_ID(ctx);
		final int ad_Org_ID = Env.getAD_Org_ID(ctx);

		final List<IDeviceRequest<T>> result = new ArrayList<IDeviceRequest<T>>();

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final Map<String, String> requestClassNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_PREFIX + "." + deviceName + "." + attribute.getValue(), ad_Client_ID, ad_Org_ID);
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
