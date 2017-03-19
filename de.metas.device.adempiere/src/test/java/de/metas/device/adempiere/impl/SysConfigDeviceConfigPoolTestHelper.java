package de.metas.device.adempiere.impl;

import java.util.List;
import java.util.Set;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.compiere.util.Env;
import org.junit.Assert;

import com.google.common.collect.ImmutableSet;

import de.metas.device.adempiere.AttributesDevicesHub;
import de.metas.device.adempiere.DeviceConfig;

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

public class SysConfigDeviceConfigPoolTestHelper
{
	private static final String MOCKED_DEVICE_DeviceClass = MockedDevice.class.getName();
	private static final String MOCKED_DEVICE_RequestClass = MockedDeviceRequest.class.getName();

	private final IHostIdentifier clientHost = NetUtils.getLocalHost();

	public final DeviceConfig createDeviceConfigAndAssertValid(final String deviceName, final String attributeCode, final Set<Integer> warehouseIds)
	{
		createMockedDeviceSysconfigs(deviceName, attributeCode, warehouseIds);

		final SysConfigDeviceConfigPool configPool = createSysConfigDeviceConfigPool();
		final List<DeviceConfig> deviceConfigs = configPool.getDeviceConfigsForAttributeCode(attributeCode);
		final DeviceConfig deviceConfig = ListUtils.singleElement(deviceConfigs);

		System.out.println("Checking " + deviceConfig);
		Assert.assertEquals("deviceName", deviceName, deviceConfig.getDeviceName());
		Assert.assertEquals("attributeCode", ImmutableSet.of(attributeCode), deviceConfig.getAssignedAttributeCodes());
		Assert.assertEquals("DeviceClass", MOCKED_DEVICE_DeviceClass, deviceConfig.getDeviceClassname());
		Assert.assertEquals("RequestClassname", ImmutableSet.of(MOCKED_DEVICE_RequestClass), deviceConfig.getRequestClassnames(attributeCode));
		Assert.assertEquals("M_Warehouse_ID", warehouseIds, deviceConfig.getAssignedWarehouseIds());

		return deviceConfig;
	}

	private final void createMockedDeviceSysconfigs(final String deviceName, final String attributeCode, final Set<Integer> warehouseIds)
	{
		Check.assumeNotEmpty(deviceName, "deviceName is not empty");
		Check.assumeNotEmpty(attributeCode, "attributeCode is not empty");

		final String host = SysConfigDeviceConfigPool.IPADDRESS_ANY;

		putSysConfig("de.metas.device.Name." + deviceName, deviceName);
		putSysConfig("de.metas.device." + deviceName + "." + host + ".DeviceClass", MOCKED_DEVICE_DeviceClass);
		putSysConfig("de.metas.device." + deviceName + "." + host + ".Endpoint.Class", "de.metas.device.scales.endpoint.MockedEndpoint");
		putSysConfig("de.metas.device." + deviceName + "." + host + ".Endpoint.IP", "DOES NOT MATTER");
		putSysConfig("de.metas.device." + deviceName + "." + host + ".Endpoint.Port", "0");
		putSysConfig("de.metas.device." + deviceName + "." + host + ".RoundToPrecision", "1");
		putSysConfig("de.metas.device." + deviceName + ".AttributeInternalName", attributeCode);
		putSysConfig("de.metas.device." + deviceName + ".AvailableOn1", host);
		putSysConfig("de.metas.device." + deviceName + "." + attributeCode, MOCKED_DEVICE_RequestClass);

		warehouseIds.forEach(warehouseId -> putSysConfig("de.metas.device." + deviceName + ".M_Warehouse_ID." + warehouseId, String.valueOf(warehouseId)));
	}

	private static final void putSysConfig(final String name, final String value)
	{
		final int AD_Org_ID = 0;
		Services.get(ISysConfigBL.class).setValue(name, value, AD_Org_ID);
	}

	private final SysConfigDeviceConfigPool createSysConfigDeviceConfigPool()
	{
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final int adOrgId = 0;
		final SysConfigDeviceConfigPool configPool = new SysConfigDeviceConfigPool(clientHost, adClientId, adOrgId);
		return configPool;
	}

	public AttributesDevicesHub createDevicesHub()
	{
		final SysConfigDeviceConfigPool configPool = createSysConfigDeviceConfigPool();
		return new AttributesDevicesHub(configPool);
	}

}
