package de.metas.device.config;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.WeakList;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_SysConfig;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

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

/**
 * {@link IDeviceConfigPool} implementation which reads all the configs from {@link I_AD_SysConfig}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class SysConfigDeviceConfigPool implements IDeviceConfigPool
{
	private static final Logger logger = LogManager.getLogger(SysConfigDeviceConfigPool.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String CFG_DEVICE_PREFIX = "de.metas.device";
	private static final String CFG_DEVICE_NAME_PREFIX = CFG_DEVICE_PREFIX + ".Name";
	private static final String DEVICE_PARAM_AvailableOn = "AvailableOn";
	private static final String DEVICE_PARAM_DeviceClass = "DeviceClass";
	private static final String DEVICE_PARAM_AttributeInternalName = "AttributeInternalName";
	private static final String DEVICE_PARAM_M_Warehouse_ID = "M_Warehouse_ID";
	private static final String DEVICE_PARAM_BeforeAcquireValueHook = "BeforeAcquireValueHook";
	public static final String DEVICE_PARAM_RoundingToQty = "RoundingToQty";
	public static final String DEVICE_PARAM_RoundingToQty_UOM_ID = "RoundingToQty_UOM_ID";

	/* package */static final String IPADDRESS_ANY = "0.0.0.0";

	private final IHostIdentifier clientHost;
	private final ClientAndOrgId clientAndOrgId;

	private final ExtendedMemorizingSupplier<ImmutableListMultimap<AttributeCode, DeviceConfig>> //
			deviceConfigsIndexedByAttributeCodeSupplier = ExtendedMemorizingSupplier.of(this::loadDeviceConfigsIndexedByAttributeCode);

	private final WeakList<IDeviceConfigPoolListener> listeners = new WeakList<>(true); // weakDefault=true

	public SysConfigDeviceConfigPool(
			@NonNull final IHostIdentifier clientHost,
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		this.clientHost = clientHost;
		this.clientAndOrgId = ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId);

		CacheMgt.get().addCacheResetListener(I_AD_SysConfig.Table_Name, request -> resetDeviceConfigs());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("clientHost", clientHost)
				.add("clientAndOrgId", clientAndOrgId)
				.toString();
	}

	@Override
	public void addListener(final IDeviceConfigPoolListener listener)
	{
		Check.assumeNotNull(listener, "Parameter listener is not null");
		listeners.addIfAbsent(listener); // NOTE: we assume weakDefault=true
	}

	@Override
	public List<DeviceConfig> getDeviceConfigsForAttributeCode(final AttributeCode attributeCode)
	{
		//noinspection ConstantConditions
		return deviceConfigsIndexedByAttributeCodeSupplier.get().get(attributeCode);
	}

	@Override
	public Set<AttributeCode> getAllAttributeCodes()
	{
		//noinspection ConstantConditions
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

	private ImmutableListMultimap<AttributeCode, DeviceConfig> loadDeviceConfigsIndexedByAttributeCode()
	{
		return getAllDeviceNames()
				.stream()
				.map(this::createDeviceConfigOrNull)
				.filter(Objects::nonNull)
				.flatMap(deviceConfig -> deviceConfig.getAssignedAttributeCodes()
						.stream()
						.map(attributeCode -> GuavaCollectors.entry(attributeCode, deviceConfig)))
				.collect(GuavaCollectors.toImmutableListMultimap());
	}

	private DeviceConfig createDeviceConfigOrNull(final String deviceName)
	{
		if (!isDeviceAvailableHost(deviceName))
		{
			return null;
		}

		final Set<AttributeCode> assignedAttributeCodes = getDeviceAssignedAttributeCodes(deviceName);
		if (assignedAttributeCodes.isEmpty())
		{
			// NOTE: a warning was already logged
			return null;
		}

		return DeviceConfig.builder(deviceName)
				.setDeviceClassname(getDeviceClassname(deviceName))
				.setAssignedAttributeCodes(assignedAttributeCodes)
				.setParameterValueSupplier(this::getDeviceParamValue)
				.setRequestClassnamesSupplier(this::getDeviceRequestClassnames)
				.setAssignedWarehouseIds(getDeviceWarehouseIds(deviceName))
				.setBeforeHooksClassname(getBeforeHooksClassname(deviceName))
				.setDeviceConfigParams(getDeviceConfigParams(deviceName))
				.build();
	}

	/**
	 * @return the list of values from AD_SysConfig, where <code>AD_SysConfig.Name</code> starts with {@value #CFG_DEVICE_NAME_PREFIX}. The values are ordered lexically be the key
	 */
	private List<String> getAllDeviceNames()
	{
		final Map<String, String> deviceNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_NAME_PREFIX, clientAndOrgId);
		return ImmutableList.copyOf(new TreeMap<>(deviceNames).values());
	}

	private boolean isDeviceAvailableHost(final String deviceName)
	{
		final String availableOnSysConfigPrefix = CFG_DEVICE_PREFIX + "." + deviceName + "." + DEVICE_PARAM_AvailableOn;
		final Collection<String> availableForHosts = sysConfigBL.getValuesForPrefix(availableOnSysConfigPrefix, clientAndOrgId).values();
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
		return getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, parameterName, defaultValue);
	}

	private ImmutableSet<AttributeCode> getDeviceAssignedAttributeCodes(final String deviceName)
	{
		final String attributeSysConfigPrefix = CFG_DEVICE_PREFIX + "." + deviceName + "." + DEVICE_PARAM_AttributeInternalName;
		final ImmutableSet<AttributeCode> assignedAttributeCodes = sysConfigBL
				.getValuesForPrefix(attributeSysConfigPrefix, clientAndOrgId)
				.values()
				.stream()
				.map(AttributeCode::ofString)
				.collect(ImmutableSet.toImmutableSet());

		if (assignedAttributeCodes.isEmpty())
		{
			logger.info("Found no SysConfig assigned attribute to device {}; SysConfig-prefix={}", deviceName, attributeSysConfigPrefix);
			return ImmutableSet.of();
		}
		else
		{
			return assignedAttributeCodes;
		}
	}

	@NonNull
	private ImmutableList<String> getBeforeHooksClassname(@NonNull final String deviceName)
	{
		final String sysconfigName = CFG_DEVICE_PREFIX + "." + deviceName + "." + DEVICE_PARAM_BeforeAcquireValueHook;
		return Optional.ofNullable(sysConfigBL.getValue(sysconfigName, clientAndOrgId))
				.map(value -> value.split(","))
				.map(Arrays::asList)
				.map(ImmutableList::copyOf)
				.orElseGet(ImmutableList::of);
	}

	@NonNull
	private ImmutableMap<String, String> getDeviceConfigParams(@NonNull final String deviceName)
	{
		return ImmutableMap.copyOf(sysConfigBL.getValuesForPrefix(CFG_DEVICE_PREFIX + "." + deviceName, clientAndOrgId));
	}
	
	private Set<WarehouseId> getDeviceWarehouseIds(final String deviceName)
	{
		final String sysconfigPrefix = CFG_DEVICE_PREFIX + "." + deviceName + "." + DEVICE_PARAM_M_Warehouse_ID;
		return sysConfigBL.getValuesForPrefix(sysconfigPrefix, clientAndOrgId)
				.values()
				.stream()
				.map(warehouseIdStr -> {
					try
					{
						return WarehouseId.ofRepoId(Integer.parseInt(warehouseIdStr));
					}
					catch (Exception ex)
					{
						logger.warn("Failed parsing {} for {}*", warehouseIdStr, sysconfigPrefix, ex);
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return device implementation classname; never returns null
	 * @throws DeviceConfigException if no classname was found
	 */
	private String getDeviceClassname(final String deviceName)
	{
		// note: assume not null because in that case, the method above would fail
		return getSysconfigValueWithHostNameFallback(CFG_DEVICE_PREFIX + "." + deviceName, DEVICE_PARAM_DeviceClass, null);
	}

	private Set<String> getDeviceRequestClassnames(final String deviceName, final AttributeCode attributeCode)
	{
		final Map<String, String> requestClassNames = sysConfigBL.getValuesForPrefix(CFG_DEVICE_PREFIX + "." + deviceName + "." + attributeCode.getCode(), clientAndOrgId);
		return ImmutableSet.copyOf(requestClassNames.values());
	}

	/**
	 * @return value; never returns null
	 * @throws DeviceConfigException if configuration parameter was not found and given <code>defaultStr</code> is <code>null</code>.
	 */
	private String getSysconfigValueWithHostNameFallback(final String prefix, final String suffix, final String defaultValue)
	{
		//
		// Try by hostname
		final String deviceKeyWithHostName = prefix + "." + clientHost.getHostName() + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithHostName, clientAndOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Try by host's IP
		final String deviceKeyWithIP = prefix + "." + clientHost.getIP() + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithIP, clientAndOrgId);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Try by any host (i.e. 0.0.0.0)
		final String deviceKeyWithWildCard = prefix + "." + IPADDRESS_ANY + "." + suffix;
		{
			final String value = sysConfigBL.getValue(deviceKeyWithWildCard, clientAndOrgId);
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
		throw new DeviceConfigException("@NotFound@: @AD_SysConfig@ " + deviceKeyWithHostName
				+ ", " + deviceKeyWithIP + ", " + deviceKeyWithWildCard
				+ "; @AD_Client_ID@=" + clientAndOrgId.getClientId()
				+ " @AD_Org_ID@=" + clientAndOrgId.getOrgId());
	}
}
