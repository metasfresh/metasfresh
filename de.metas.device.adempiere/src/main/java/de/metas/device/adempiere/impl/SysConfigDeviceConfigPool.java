package de.metas.device.adempiere.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.WeakList;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.net.IHostIdentifier;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.CacheMgt;
import org.compiere.util.ICacheResetListener;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.device.adempiere.DeviceConfig;
import de.metas.device.adempiere.DeviceConfigException;
import de.metas.device.adempiere.IDeviceConfigPool;
import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link IDeviceConfigPool} implementation which reads all the configs from {@link I_AD_SysConfig}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class SysConfigDeviceConfigPool implements IDeviceConfigPool
{
	private static final transient Logger logger = LogManager.getLogger(SysConfigDeviceConfigPool.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String CFG_DEVICE_PREFIX = "de.metas.device";
	private static final String CFG_DEVICE_NAME_PREFIX = CFG_DEVICE_PREFIX + ".Name";
	private static final String IPADDRESS_ANY = "0.0.0.0";

	private final IHostIdentifier clientHost;
	private final int adClientId;
	private final int adOrgId;

	private final ExtendedMemorizingSupplier<ImmutableListMultimap<String, DeviceConfig>> //
	deviceConfigsIndexedByAttributeCodeSupplier = ExtendedMemorizingSupplier.of(() -> loadDeviceConfigsIndexedByAttributeCode());

	private final ICacheResetListener cacheResetListener = (tableName, key) -> resetDeviceConfigs();

	private final WeakList<IDeviceConfigPoolListener> listeners = new WeakList<>(true); // weakDefault=true

	public SysConfigDeviceConfigPool(final IHostIdentifier clientHost, final int adClientId, final int adOrgId)
	{
		super();

		Check.assumeNotNull(clientHost, "Parameter clientHost is not null");

		this.clientHost = clientHost;
		this.adClientId = adClientId <= 0 ? 0 : adClientId;
		this.adOrgId = adOrgId <= 0 ? 0 : adOrgId;

		CacheMgt.get().addCacheResetListener(I_AD_SysConfig.Table_Name, cacheResetListener);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("clientHost", clientHost)
				.add("adClientId", adClientId)
				.add("adOrgId", adOrgId)
				.toString();
	}

	@Override
	public void addListener(final IDeviceConfigPoolListener listener)
	{
		Check.assumeNotNull(listener, "Parameter listener is not null");
		listeners.addIfAbsent(listener); // NOTE: we assume weakDefault=true
	}

	@Override
	public List<DeviceConfig> getDeviceConfigsForAttributeCode(final String attributeCode)
	{
		return deviceConfigsIndexedByAttributeCodeSupplier.get()
				.get(attributeCode);
	}

	@Override
	public Set<String> getAllAttributeCodes()
	{
		return deviceConfigsIndexedByAttributeCodeSupplier.get().keySet();
	}

	private int resetDeviceConfigs()
	{
		deviceConfigsIndexedByAttributeCodeSupplier.forget();

		// Fire listeners
		for (final IDeviceConfigPoolListener listener : listeners)
		{
			try
			{
				listener.onConfigurationChanged(this);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed calling {} for {}. Skipped.", listener, this, ex);
			}
		}

		return 1;
	}

	private ImmutableListMultimap<String, DeviceConfig> loadDeviceConfigsIndexedByAttributeCode()
	{
		return getAllDeviceNames()
				.stream()
				.map(deviceName -> createDeviceConfigOrNull(deviceName))
				.filter(deviceConfig -> deviceConfig != null)
				.flatMap(deviceConfig -> deviceConfig.getAssignedAttributeCodes()
						.stream()
						.map(attributeCode -> GuavaCollectors.entry(attributeCode, deviceConfig)))
				.collect(GuavaCollectors.toImmutableListMultimap());
	}

	private final DeviceConfig createDeviceConfigOrNull(final String deviceName)
	{
		if (!isDeviceAvailableHost(deviceName))
		{
			return null;
		}

		final DeviceConfig.Builder deviceConfig = DeviceConfig.builder(deviceName)
				.setParameterValueSupplier(this::getDeviceParamValue)
				.setRequestClassnamesSupplier(this::getDeviceRequestClassnames);

		//
		// Fetch attribute codes assigned to current device
		final String attribSysConfigPrefix = CFG_DEVICE_PREFIX + "." + deviceName + ".AttributeInternalName";
		final Collection<String> assignedAttributeCodes = sysConfigBL.getValuesForPrefix(attribSysConfigPrefix, adClientId, adOrgId).values();
		if (assignedAttributeCodes.isEmpty())
		{
			logger.info("Found no SysConfig assigned attribute to device {}; SysConfig-prefix={}", deviceName, attribSysConfigPrefix);
			return null;
		}
		deviceConfig.setAssignedAttributeCodes(assignedAttributeCodes);

		//
		// Fetch device classname
		final String deviceClassname = getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, "DeviceClass", null);
		deviceConfig.setDeviceClassname(deviceClassname);

		return deviceConfig.build();
	}

	/**
	 * Returns all device names from ordered by their <code>AD_SysConfig</code> keys.
	 *
	 * @param adClientId
	 * @param adOrgId
	 * @return the list of values from AD_SysConfig, where <code>AD_SysConfig.Name</code> starts with {@value #CFG_DEVICE_NAME_PREFIX}. The values are ordered lexically be the key
	 */
	private List<String> getAllDeviceNames()
	{
		final Map<String, String> deviceNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_NAME_PREFIX, adClientId, adOrgId);
		return ImmutableList.copyOf(new TreeMap<>(deviceNames).values());
	}

	private boolean isDeviceAvailableHost(final String deviceName)
	{
		final String availableOnSysConfigPrefix = CFG_DEVICE_PREFIX + "." + deviceName + ".AvailableOn";
		final Collection<String> availableForHosts = sysConfigBL.getValuesForPrefix(availableOnSysConfigPrefix, adClientId, adOrgId).values();
		if (availableForHosts.isEmpty())
		{
			logger.info("Found no SysConfig available hosts for device {}; SysConfig-prefix={}", deviceName, availableOnSysConfigPrefix);
			return false;
		}

		final String hostName = clientHost.getHostName();
		final String hostIP = clientHost.getIP();

		for (final String currentAddressOrHostName : availableForHosts)
		{
			// note that instead of this, we might want to use commons-net in future, to be able to check for stuff like "10.10.10.0/255.255.255.128"
			if (currentAddressOrHostName.equals(IPADDRESS_ANY)
					|| currentAddressOrHostName.equalsIgnoreCase(hostName)
					|| currentAddressOrHostName.equalsIgnoreCase(hostIP))
			{
				return true;
			}
		}

		return false;
	}

	private String getDeviceParamValue(final String deviceName, final String parameterName, final String defaultValue)
	{
		final String paramValue = getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, parameterName, defaultValue);
		return paramValue;
	}

	private Set<String> getDeviceRequestClassnames(final String deviceName, final String attributeCode)
	{
		final Map<String, String> requestClassNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_PREFIX + "." + deviceName + "." + attributeCode, adClientId, adOrgId);
		return ImmutableSet.copyOf(requestClassNames.values());
	}

	/**
	 *
	 * @param prefix
	 * @param suffix
	 * @param defaultValue
	 * @return value; never returns null
	 * @throws DeviceConfigException if configuration parameter was not found and given <code>defaultStr</code> is <code>null</code>.
	 */
	private String getSysconfigValueWithHostNameFallback(final String prefix, final String suffix, final String defaultValue)
	{
		//
		// Try by hostname
		final String deviceKeyWithHostName = prefix + "." + clientHost.getHostName() + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithHostName, adClientId, adOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Try by host's IP
		final String deviceKeyWithIP = prefix + "." + clientHost.getIP() + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithIP, adClientId, adOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Try by any host (i.e. 0.0.0.0)
		final String deviceKeyWithWildCard = prefix + "." + IPADDRESS_ANY + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithWildCard, adClientId, adOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Fallback to default
		if (defaultValue != null)
		{
			return defaultValue;
		}

		//
		// Throw exception
		final String msg = "@NotFound@: @AD_SysConfig@ " + deviceKeyWithHostName + ", " + deviceKeyWithIP + ", " + deviceKeyWithWildCard
				+ "; @AD_Client_ID@=" + adClientId + " @AD_Org_ID@=" + adOrgId;
		throw new DeviceConfigException(msg);
	}
}
