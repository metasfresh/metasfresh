package de.metas.device.adempiere.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.device.adempiere.AttributeDeviceAccessor;
import de.metas.device.adempiere.AttributesDevicesHub;
import de.metas.util.collections.CollectionUtils;

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

@ExtendWith(AdempiereTestWatcher.class)
public class SysConfigDeviceConfigPoolTest
{
	private static final AttributeCode weightGrossAttribute = AttributeCode.ofString("WeightGross");

	private SysConfigDeviceConfigPoolTestHelper helper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new SysConfigDeviceConfigPoolTestHelper();
	}

	@Test
	public void testDeviceConfig_NoWarehouse()
	{
		helper.createDeviceConfigAndAssertValid("device1", weightGrossAttribute, ImmutableSet.of());

		//
		// Test device accessor
		{
			final AttributesDevicesHub devicesHub = helper.createDevicesHub();
			final List<AttributeDeviceAccessor> deviceAccessors = devicesHub.getAttributeDeviceAccessors(weightGrossAttribute)
					.stream((WarehouseId)null) // any
					.collect(ImmutableList.toImmutableList());

			final AttributeDeviceAccessor deviceAccessor = CollectionUtils.singleElement(deviceAccessors);

			// shall be available for ANY warehouse
			assertAvailableForWarehouse(deviceAccessor, null, true);
			assertAvailableForWarehouse(deviceAccessor, WarehouseId.ofRepoId(1), true);
			assertAvailableForWarehouse(deviceAccessor, WarehouseId.ofRepoId(2), true);
		}
	}

	@Test
	public void testDeviceConfig_WithWarehouse()
	{
		final Set<WarehouseId> warehouseIds = ImmutableSet.of(WarehouseId.ofRepoId(1000001), WarehouseId.ofRepoId(1000002));
		helper.createDeviceConfigAndAssertValid("device1", weightGrossAttribute, warehouseIds);
		// POJOLookupMap.get().dumpStatus(); // dump configuration

		//
		// Test device accessor
		final AttributesDevicesHub devicesHub = helper.createDevicesHub();
		for (final WarehouseId warehouseId : warehouseIds)
		{
			final List<AttributeDeviceAccessor> deviceAccessors = devicesHub.getAttributeDeviceAccessors(weightGrossAttribute)
					.stream(warehouseId)
					.collect(ImmutableList.toImmutableList());

			final AttributeDeviceAccessor deviceAccessor = CollectionUtils.singleElement(deviceAccessors);

			// shall be available only for configured warehouse
			assertAvailableForWarehouse(deviceAccessor, warehouseId, true);
			assertAvailableForWarehouse(deviceAccessor, (WarehouseId)null, false);
			assertAvailableForWarehouse(deviceAccessor, WarehouseId.ofRepoId(1), false);
			assertAvailableForWarehouse(deviceAccessor, WarehouseId.ofRepoId(2), false);
		}

	}

	private static final void assertAvailableForWarehouse(
			final AttributeDeviceAccessor deviceAccessor,
			final WarehouseId warehouseId,
			final boolean expectedAvailable)
	{
		assertThat(deviceAccessor.isAvailableForWarehouse(warehouseId))
				.as("accessor: " + deviceAccessor + ", warehoust: " + warehouseId)
				.isEqualTo(expectedAvailable);
	}
}
