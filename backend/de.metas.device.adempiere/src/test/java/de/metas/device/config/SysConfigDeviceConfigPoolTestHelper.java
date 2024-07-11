package de.metas.device.config;

import com.google.common.collect.ImmutableSet;
import de.metas.device.accessor.DeviceAccessorsHub;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

	public final void createDeviceConfigAndAssertValid(
			final String deviceName,
			final AttributeCode attributeCode,
			final Set<WarehouseId> warehouseIds)
	{
		createMockedDeviceSysconfigs(deviceName, attributeCode, warehouseIds);

		final SysConfigDeviceConfigPool configPool = createSysConfigDeviceConfigPool();
		final List<DeviceConfig> deviceConfigs = configPool.getDeviceConfigsForAttributeCode(attributeCode);
		final DeviceConfig deviceConfig = CollectionUtils.singleElement(deviceConfigs);

		// System.out.println("Checking " + deviceConfig);
		assertThat(deviceConfig.getDeviceName()).as("deviceName").isEqualTo(deviceName);
		assertThat(deviceConfig.getAssignedAttributeCodes()).as("attributeCode").isEqualTo(ImmutableSet.of(attributeCode));
		assertThat(deviceConfig.getDeviceClassname()).as("DeviceClass").isEqualTo(MOCKED_DEVICE_DeviceClass);
		assertThat(deviceConfig.getRequestClassnames(attributeCode)).as("RequestClassname").isEqualTo(ImmutableSet.of(MOCKED_DEVICE_RequestClass));
		assertThat(deviceConfig.getAssignedWarehouseIds()).as("M_Warehouse_ID").isEqualTo(warehouseIds);

	}

	private void createMockedDeviceSysconfigs(
			@NonNull final String deviceName,
			@NonNull final AttributeCode attributeCode,
			@NonNull final Set<WarehouseId> warehouseIds)
	{
		Check.assumeNotEmpty(deviceName, "deviceName is not empty");

		final String host = SysConfigDeviceConfigPool.IPADDRESS_ANY;

		putSysConfig("de.metas.device.Name." + deviceName, deviceName);
		putSysConfig("de.metas.device." + deviceName + "." + host + ".DeviceClass", MOCKED_DEVICE_DeviceClass);
		putSysConfig("de.metas.device." + deviceName + "." + host + ".Endpoint.Class", "de.metas.device.scales.endpoint.MockedEndpoint");
		putSysConfig("de.metas.device." + deviceName + "." + host + ".Endpoint.IP", "DOES NOT MATTER");
		putSysConfig("de.metas.device." + deviceName + "." + host + ".Endpoint.Port", "0");
		putSysConfig("de.metas.device." + deviceName + "." + host + ".RoundToPrecision", "1");
		putSysConfig("de.metas.device." + deviceName + ".AttributeInternalName", attributeCode.getCode());
		putSysConfig("de.metas.device." + deviceName + ".AvailableOn1", host);
		putSysConfig("de.metas.device." + deviceName + "." + attributeCode, MOCKED_DEVICE_RequestClass);

		warehouseIds.forEach(warehouseId -> putSysConfig("de.metas.device." + deviceName + ".M_Warehouse_ID." + warehouseId.getRepoId(), String.valueOf(warehouseId.getRepoId())));
	}

	private static void putSysConfig(final String name, final String value)
	{
		Services.get(ISysConfigBL.class).setValue(name, value, ClientId.SYSTEM, OrgId.ANY);
	}

	private SysConfigDeviceConfigPool createSysConfigDeviceConfigPool()
	{
		final ClientId adClientId = Env.getClientId(Env.getCtx());
		return new SysConfigDeviceConfigPool(clientHost, adClientId, OrgId.ANY);
	}

	public DeviceAccessorsHub createDevicesHub()
	{
		final SysConfigDeviceConfigPool configPool = createSysConfigDeviceConfigPool();
		return new DeviceAccessorsHub(configPool);
	}

}
